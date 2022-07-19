package com.bilgeadam.model.vehicle;

import java.io.Serializable;

public class ServiceCar extends LandVehicle implements Serializable {
	private static final long serialVersionUID = 1L;
	private String serviceType; // catering, çekici araç, temizlik aracı
	private boolean onDuty;

	public ServiceCar(String brand, String engineType, String serviceType) {
		super(brand, engineType);
		this.serviceType = serviceType;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setOnDuty(boolean onDuty) {
		this.onDuty = onDuty;
	}

	public boolean isOnDuty() {
		return this.onDuty;
	}

	@Override
	public int compareTo(Vehicle o) {
		return this.serviceType.compareTo(((ServiceCar)o).serviceType);
	}
}
