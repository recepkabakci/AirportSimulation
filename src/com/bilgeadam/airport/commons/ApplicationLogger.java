package com.bilgeadam.airport.commons;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ApplicationLogger {
	private static final String sourcePath = "C:\\Users\\babur.somer\\boost-02-workspace\\Airport\\data";
	private static Logger logger;
	
	/**
	 * Logger nesnesini "lazy initialization" ile yaratacağız
	 * Her zaman geçerli bir nesne varlığını garanti eder	
	 * @return Logger
	 */
	private static Logger getLogger() { 
		if (logger == null) {
			logger = Logger.getLogger("Benim Logger'ım");
			
			try {
				FileHandler handler = new FileHandler(sourcePath+"\\"+"log.txt", true); // logger'ın bir dosyaya kayıt yazabilmesi için. 
				handler.setFormatter(new SimpleFormatter());		// kayıtlar yazılırken tarih sayı gibi verilerin formatlı yazılması için. istenirse farklı formatlar tanımlanabilir	
				logger.addHandler(handler);	// dosya yazımı için yaratılan handler (iş yapıcı) logger'a ekleniyor
				logger.setLevel(Level.WARNING); // logger'ın kayıt tutma seviyesi belirleniyor
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
	
	/**
	 * bilgi vermek amaçlı kayılar için. Genelde "bu oldu, şu oldu" gibi kayıtlar
	 * @param msg
	 */
	public static void info (String msg) { 
		getLogger().info(msg);
	}
	
	/**
	 * uyarı mesajları: Programı çökertmeyen ama kullanıcının bir hata oluştuğuna dair bilgilendirilmesi gereken yerlerde
	 * @param msg
	 */
	public static void warning (String msg) {
		getLogger().warning(msg);
	}
	
	/**
	 * hata mesajları: program akışının bozulduğu veya programın çöktüğü durumlarda
	 * @param msg
	 */
	public static void error (String msg) {
		getLogger().severe(msg);
	}
	
	/**
	 * debug seviyesi: program yazan kişinin kendi kontrolleri için yazdığı mesajlar. En düşük seviyeli mesajlar
	 * @param msg
	 */
	public static void debug (String msg) {
		getLogger().fine(msg);
	}
}
