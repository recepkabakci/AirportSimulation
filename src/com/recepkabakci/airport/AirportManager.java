package com.recepkabakci.airport;

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

import com.recepkabakci.airport.commons.ApplicationLogger;
import com.recepkabakci.model.airport.Airport;
import com.recepkabakci.model.vehicle.Airplane;
import com.recepkabakci.model.vehicle.Helicopter;
import com.recepkabakci.model.vehicle.UnrecognizedVehicleException;
import com.recepkabakci.model.vehicle.Vehicle;

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

	private void readVehicleData() {

		final String sourcePath = "C:\\Users\\recep.kabakci\\boost-02-workspace\\Airport\\data";  
		final String CSVFile    = "vehicles.csv";  
		final String DATFile    = "vehicles.dat";	

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

	
	private void parseCSVFile(String sourcePath, String CSVFile) {
		File file = new File(sourcePath, CSVFile);
		if (!file.exists()) {
			ApplicationLogger.error("Eksik kaynak. Program çalışmaya devam edemez. Bye...");
			System.exit(-10);
		}

		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				else {
					System.out.println("==> " + line);
					if (line.startsWith("﻿VehicleType")) {  
						continue;
					}
					try {
						Vehicle vehicle = Vehicle.parse(line); 
																
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
