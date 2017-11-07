package de.htw.ws.kbe.runMeRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Utility {

	public static String getClass(File config) {

		Properties configLoader = new java.util.Properties();
		try {
			configLoader.load(new FileInputStream(config));

		} catch (IllegalArgumentException a) {
			System.out.println("Config file name missing as parameter");

		} catch (FileNotFoundException e) {

			System.out.println("Config file not found");
		} catch (IOException i) {

		}

		// config failures

		if (!configLoader.isEmpty()) {
			if (!configLoader.containsKey("classWithRunMeAnnos")) {

				System.out.println("Invalid Key in the config file");

			}

		}

		return configLoader.getProperty("classWithRunMeAnnos");

	}

}
