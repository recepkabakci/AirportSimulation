package com.bilgeadam.model.vehicle;

import java.io.Serializable;

public class Airplane extends AirVehicle implements Serializable {
	private static final long serialVersionUID = 1L;
	private String company;
	private int    passengerCapacity;
	private int    wingLength;
	private double weight;
	private double maxWeight;

	public Airplane(String brand, String engineType, int wingLength, double weight, double maxWeight) {
		super(brand, engineType);
		this.maxWeight  = maxWeight;
		this.wingLength = wingLength;
		this.weight     = weight;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getPassengerCapacity() {
		return this.passengerCapacity;
	}

	public void setPassengerCapacity(int passengerCapacity) {
		this.passengerCapacity = passengerCapacity;
	}

	public int getWingLength() {
		return this.wingLength;
	}

	public double getWeight() {
		return this.weight;
	}

	public double getMaxWeight() {
		return this.maxWeight;
	}

	@Override
	/**
	 * Compares two airplanes by their wing length
	 */
	public int compareTo(Vehicle o) {
		Airplane other = (Airplane)o;
		return Integer.compare(this.wingLength, other.wingLength);
	}
}

