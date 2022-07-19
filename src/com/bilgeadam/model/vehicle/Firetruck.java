package com.bilgeadam.model.vehicle;

import java.io.Serializable;

public class Firetruck extends LandVehicle implements Serializable {
	private static final long serialVersionUID = 1L;
	private double  waterCapacity;
	private boolean onDuty;

	public Firetruck(String brand, String engineType, double waterCapacity) {
		super(brand, engineType);
		this.waterCapacity = waterCapacity;
		this.onDuty        = false;
	}

	public boolean isOnDuty() {
		return this.onDuty;
	}

	public void setOnDuty(boolean onDuty) {
		this.onDuty = onDuty;
	}

	public double getWaterCapacity() {
		return this.waterCapacity;
	}

	@Override
	public int compareTo(Vehicle o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}
}
