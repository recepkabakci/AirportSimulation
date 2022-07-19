package com.bilgeadam.model.vehicle;

public class UnrecognizedVehicleException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UnrecognizedVehicleException (String string) {
		super("Veri tanımlı olmayan bir içerik içeriyor: <<<" + string + ">>>");
	}
}
