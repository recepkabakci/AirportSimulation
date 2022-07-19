package com.bilgeadam.model.vehicle;

import java.io.Serializable;

public abstract class AirVehicle extends Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;
	public AirVehicle(String brand, String engineType) {
		super(brand, engineType);
	}

}
