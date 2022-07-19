package com.bilgeadam.airport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.bilgeadam.airport.commons.ApplicationLogger;
import com.bilgeadam.model.airport.Airport;
import com.bilgeadam.model.vehicle.Airplane;
import com.bilgeadam.model.vehicle.Helicopter;
import com.bilgeadam.model.vehicle.UnrecognizedVehicleException;
import com.bilgeadam.model.vehicle.Vehicle;

public class AirportManager {
	private Airport            airport;
	private ArrayList<Vehicle> vehicles;

	public AirportManager() {
		super();
		this.vehicles = new ArrayList<>();
		ApplicationLogger.info("Airport nesnesi oluşturuldu");
	}

	public static void main(String[] args) {
		System.out.println("Havaalanı Yönetim Uygulaması");

		AirportManager manager = new AirportManager();
		
		manager.airport = new Airport("İstanbul Airport", "İstanbul");
//		manager.registerVehicles();
		manager.readVehicleData();
		System.out.println(manager);

		System.out.println("Havaalanı Yönetim Uygulamasını kullandığınız için teşekkürler");
	}

	/**
	 * Araç bilgilerini okuma kısmı. Ya oluşturulmuş binary-verilerden okunuyor (deseriyalize ediliyor) ya da bir metin dosyasından okunup
	 * parse edilip yorumlanıyor. Gerekli nesneler üretiliyor ve okuma işlemi bittikten sonra binary-dosya içine 
	 * bir daha bu masraflı metin okuma işi yapılmasın diye yazılıyor (seriyalize ediliyor)
	 */
	private void readVehicleData() {

		final String sourcePath = "C:\\Users\\babur.somer\\boost-02-workspace\\Airport\\data";  // dosyaların olması gereken dizin
		final String CSVFile    = "vehicles.csv";  // metin dosya adı
		final String DATFile    = "vehicles.dat";	// binary-dosya adı

		File datFile = new File(sourcePath, DATFile);
		if (datFile.exists()) {
			this.deserialize(datFile);
		}
		else {
			this.parseCSVFile(sourcePath, CSVFile);
			this.serializeVehicleData(datFile);
		}
	}

	private void serializeVehicleData(File datFile) {
		/* 
		 * Bir binary dosyaya yazmak için öncelikle binary verileri yazabilecek bir mekanizmaya ihtiyaç var. Bu mekanizma 
		 * Stream'lerle çalışıyor. Bir dosyaya yazdığımız için FileOutputStream oluşturmamız gerekli. Bunu yaparken kullanacağımız dosya
		 * bilgilerini veriyoruz. Sonra verileri Object olarakyazdığımızdan bu FileOutputStream'i bir ObjectouputStream ile sarmallıyoruz.
		 * Bu şekilde  verileri serileştirilmiş şekilde dosyaya yazabiliyoruz
		 */

		try (FileOutputStream   fos = new FileOutputStream(datFile);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(this.vehicles);
			oos.flush();
			ApplicationLogger.info("Nesne yazıldı");
		}
		catch (IOException ex) {
			ApplicationLogger.error("Hata: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void deserialize(File datFile) {
		/* 
		 * Bir binary dosya okumak için öncelikle binary verileri okuyabilecek mekanizmaya ihtiyaç var. Bu mekanizma 
		 * Stream'lerle çalışıyor. Bir dosyadan okuduğumuz için FileInputStream oluşturmamız gerekli. Bunu yaparken kullanacağımız dosya
		 * bilgilerini veriyoruz. Sonra verileri Object olarak okuduğumuzdan bu FileInputStream'i bir ObjectInputStream ile sarmallıyoruz.
		 * Bu şekilde serileştirilmiş verileri tekrar nesne şeklinde okuyabiliyoruz 
		 */
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(datFile))) {

			Object o = ois.readObject();

			this.vehicles = (ArrayList<Vehicle>) o;

			ApplicationLogger.info("Obje hali: " + o);
		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * Metin dosyasının her bir satırını okuyarak yorumlayıp pars etmemiz gerekiyor
	 */
	private void parseCSVFile(String sourcePath, String CSVFile) {
		File file = new File(sourcePath, CSVFile);
		if (!file.exists()) {
			ApplicationLogger.error("Eksik kaynak. Program çalışmaya devam edemez. Bye...");
			System.exit(-10);
		}

		/*
		 * Bir metin dosyası okuduğumuzdan File reader şeklinde bir yapı kullanmamız gerekli. Buna okumak istediğimiz dosya bilgilerini 
		 * parametre olarak veriyoruz. Okuma işlemni hızlandırıp optimize etmek için BufferedReader kullanıyoruz
		 */
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				else {
					System.out.println("==> " + line);
					if (line.startsWith("﻿VehicleType")) {  // ilk satır başlık bilgisi içerdiği için atla
						continue;
					}
					try {
						Vehicle vehicle = Vehicle.parse(line); // dosyadan her okunan satırı yorumlayıp doğru türden bir
																// obje
																// yaratmamız gerekiyor. Vehicle.parse(line) yorumlama
																// ve obje
																// yaratma işini yapacak
						vehicles.add(vehicle);
					}
					catch (UnrecognizedVehicleException ex) {
						ApplicationLogger.warning("Tanımsız araç: " + ex.getMessage());
						continue;
					}
				}
			}
		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ÖDEV: bu yöntemi doğru çalışır şekle sokup istenirse dosyadan okunan verilere eklemeler
	 * yapmayı imkanlı kılın. Bu yöntem ile oluşturulan her aracı da serialize edin
	 * 
	 * TÜYO: vehicles'ı her serialize ettiğinizde tüm vehicle'lar dosyaya yazılır eski bilgiler ezilir 
	 */
	private void registerVehicles() {
		System.out.println("Lütfen araç bilgilerini giriniz");
		System.out.println("===============================\n");
		Scanner in   = new Scanner(System.in);
		boolean exit = false;

		Vehicle vehicle;
		while (!exit) {
			System.out.print("Lütfen araç tipini giriniz (heli, air, fire, service, exit): ");
			String type = in.nextLine();
			if (type.equalsIgnoreCase("exit")) {
				exit = true;
				continue;
			}
			System.out.print("Lütfen markayı giriniz: ");
			String brand = in.nextLine();
			System.out.print("Lütfen ismini giriniz: ");
			String name = in.nextLine();
			System.out.print("Lütfen motor tipini giriniz: ");
			String engineType = in.nextLine();

			if (type.equalsIgnoreCase("heli")) {
				vehicle = new Helicopter(brand, engineType);
			}
			else { // if (type.equalsIgnoreCase("air")) {
				System.out.print("Lütfen kanat uzunluğunu giriniz: ");
				int wingLength = in.nextInt();
				System.out.print("Lütfen ağırlık giriniz: ");
				int weight = in.nextInt();
				System.out.print("Lütfen maksimum ağırlık giriniz: ");
				int maxWeight = in.nextInt();
				in.nextLine();
				vehicle = new Airplane(brand, engineType, wingLength, weight, maxWeight);
			}
			// else if (type.equalsIgnoreCase("fire")) {
			//
			// }
			// else {
			//
			// }
			vehicle.setName(name);
			this.vehicles.add(vehicle);
		}
		in.close();
	}

	@Override
	public String toString() {
		return "AirportManager [airport=" + this.airport + ", vehicles=" + this.vehicles + "]";
	}
}
