package de.htw.ws.kbe.runMeRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;

public class Utility {

	public String getClassName(File config) {
		
		Properties configLoader = new java.util.Properties();
		try {
			configLoader.load(new FileInputStream(config));

		// config failures

		if (!configLoader.isEmpty()) {
			if (!configLoader.containsKey("classWithRunMeAnnos")) {

				System.out.println("Invalid Key in the config file");

			}

		} else {
			   System.out.println("File is empty");
		}
		
		} catch (FileNotFoundException e) {
            System.out.println("Config file not found");
 
            
		} catch (IOException i) {
            System.out.println(i.getMessage());
		}

		return configLoader.getProperty("classWithRunMeAnnos");

	}

}
