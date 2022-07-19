package com.bilgeadam.model.vehicle;

import java.io.Serializable;

public class Helicopter extends AirVehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	public Helicopter(String brand, String engineType) {
		super(brand, engineType);
	}

	@Override
	public int compareTo(Vehicle o) {
		return this.getBrand().compareTo(o.getBrand());
	}
}
