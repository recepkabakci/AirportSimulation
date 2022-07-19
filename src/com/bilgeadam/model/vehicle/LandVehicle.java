package com.bilgeadam.model.vehicle;

import java.io.Serializable;

public abstract class LandVehicle extends Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	public LandVehicle(String brand, String engineType) {
		super(brand, engineType);
	}

}
