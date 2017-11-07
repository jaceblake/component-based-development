package de.htw.ws.kbe.runMeRunner;

import java.io.File;
import java.lang.reflect.Method;

public class MethodRunner {

	public static void main(String[] args) {

		// System.out.println(System.getProperty("user.dir"));

		try {
			String cl = Utility.getClass(new File(args[0]));


			callMethods(cl);
		} catch (IllegalArgumentException a) {
			System.out.println("Config file name missing as parameter");

		} catch(ArrayIndexOutOfBoundsException i) {
			System.out.println("config file as Parameter missing");
			
		}

	}

	private static void callMethods(String cl) {

		try {
			Object x = Class.forName(cl).newInstance();
			for (Method m : x.getClass().getMethods()) {

				if (m.isAnnotationPresent(RunMe.class)) {
					m.invoke(x, m.getAnnotation(RunMe.class).input());
				}

			}

		} catch (Exception e) {
		}
	}

}
