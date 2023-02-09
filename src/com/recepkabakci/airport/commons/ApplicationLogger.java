package com.recepkabakci.airport.commons;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ApplicationLogger {
	private static final String sourcePath = "C:\\Users\\recep.kabakci\\boost-02-workspace\\Airport\\data";
	private static Logger logger;
	
	
	private static Logger getLogger() { 
		if (logger == null) {
			logger = Logger.getLogger("Benim Logger'ım");
			
			try {
				FileHandler handler = new FileHandler(sourcePath+"\\"+"log.txt", true); 
				handler.setFormatter(new SimpleFormatter());		
				logger.addHandler(handler);	
				logger.setLevel(Level.WARNING); 
			}
			catch (SecurityException ex) {
				ex.printStackTrace();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return logger;
	}
	
	
	public static void info (String msg) { 
		getLogger().info(msg);
	}
	
	
	public static void warning (String msg) {
		getLogger().warning(msg);
	}
	
	
	public static void error (String msg) {
		getLogger().severe(msg);
	}
	
	
	public static void debug (String msg) {
		getLogger().fine(msg);
	}
}
