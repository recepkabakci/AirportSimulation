package com.bilgeadam.model.vehicle;

import java.io.Serializable;
import java.util.StringTokenizer;

import com.bilgeadam.airport.commons.ApplicationLogger;

public abstract class Vehicle implements Comparable<Vehicle>, Serializable {
	private static final long serialVersionUID = 1L;
	private String brand;
	private String name;
	private String color;
	private String engineType;
	private int    maxSpeed;

	public Vehicle(String brand, String engineType) {
		this.brand      = brand;
		this.engineType = engineType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getMaxSpeed() {
		return this.maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public String getBrand() {
		return this.brand;
	}

	public String getEngineType() {
		return this.engineType;
	}

	@Override
	public String toString() {
		return "Vehicle [brand=" + this.brand + ", name=" + this.name + ", color=" + this.color + ", engineType="
				+ this.engineType + ", maxSpeed=" + this.maxSpeed + "]";
	}

	/**
	 * This method accepts a string consisting of the definition of a vehicle data. Depending on the type it creates a
	 * corresponding object.
	 * 
	 * @param line The line to be parsed
	 * @return A concrete object of type Vehicle (Airplane, Helicopter, FireTruck, ServiceCar
	 * @throws UnrecognizedVehicleException If line starts with an unexpected value listed below
	 */
	public static Vehicle parse(String line) throws UnrecognizedVehicleException {
		// HELI;Bell;Heli 01;Black;Gasoline;150;;;;;;;
		StringTokenizer  tokenizer = new StringTokenizer(line, ";");  // StringTokenizer öğeleri bir ayraç ile ayrılmış bir String'i
																	  // tekrar bireysel öğelere ayırıyor. Bu şekilde her bir parçayı
																		// ayrı ayrı tanımlayıp işleyebiliyoruz

		String type = tokenizer.nextToken().toUpperCase(); 		// VehicleType ==> HELI   // nextToken sırayla öğeleri veriyor
		String brand = tokenizer.nextToken(); 					// Brand ==> Bell 
		String name = tokenizer.nextToken(); 					// Name ==> Heli 01 
		String color = tokenizer.nextToken(); 					// Color ==> Black
		String engine = tokenizer.nextToken(); 					// Engine ==> Gasoline
		int speed = parseIntegerValue(tokenizer.nextToken()); 	// Speed ==> 150
		
		if (type.startsWith("HEL")) {
			Helicopter vehicle = new Helicopter(brand, engine);
			vehicle.setColor(color);
			vehicle.setMaxSpeed(speed);
			vehicle.setName(name);
			return vehicle;
		}
		if (type.startsWith("AIR")) {
			//Company	Capacity	Length	Weight	MaxWeight	Service	ServiceType
			String company = tokenizer.nextToken();
			int capacity = parseIntegerValue(tokenizer.nextToken());
			int length = parseIntegerValue(tokenizer.nextToken());
			int weight = parseIntegerValue(tokenizer.nextToken());
			int maxWeight = parseIntegerValue(tokenizer.nextToken());
			Airplane vehicle = new Airplane(brand, engine, length, weight, maxWeight);
			vehicle.setColor(color);
			vehicle.setMaxSpeed(speed);
			vehicle.setName(name);
			vehicle.setCompany(company);
			vehicle.setPassengerCapacity(capacity);
			return vehicle;
		}
		if (type.startsWith("FIR")) {
			int capacity = parseIntegerValue(tokenizer.nextToken());
			boolean onDuty = tokenizer.nextToken().equalsIgnoreCase("yes");
			Firetruck truck = new Firetruck(brand, engine, capacity);
			truck.setColor(color);
			truck.setMaxSpeed(speed);
			truck.setName(name);
			truck.setOnDuty(onDuty);
			return truck;
		}
		if (type.startsWith("SER")){
			boolean onDuty = tokenizer.nextToken().equalsIgnoreCase("yes");
			String serviceType = tokenizer.nextToken();
			ServiceCar truck = new ServiceCar(brand, engine, serviceType);
			truck.setColor(color);
			truck.setMaxSpeed(speed);
			truck.setName(name);
			truck.setOnDuty(onDuty);
			return truck;
		}
		else {
			throw new UnrecognizedVehicleException(line);
		}
	}

	/**
	 * Bilgileri yorumlarken özellikle String'ten sayı şekline dönüştürken hatalı veri sonucu sayıya döndürme işlemi
	 * yapılamayıp program çökebilir. Bu durumu merkezi bir şekilde kontrol etmek için bu yöntem kullanılıyor
	 * @param token  Yorumlanması gereken bir bilgi
	 * @return
	 */
	private static int parseIntegerValue(String token) {
		int retVal = 0;
		try {
			retVal = Integer.parseInt(token);
		}
		catch (NumberFormatException ex) {
			ApplicationLogger.warning("Unexpected token <<< " + token + " >>>");
			retVal = Integer.MIN_VALUE;   // olabilecek en küçük Integer sayı. Uygulamanın diğer bölümlerinde sayının geçersiz bir değer aldığını fark etmek için
		}
		return retVal;
	}
}
