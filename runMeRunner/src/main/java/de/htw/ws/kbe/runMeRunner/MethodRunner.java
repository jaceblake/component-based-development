
package de.htw.ws.kbe.runMeRunner;

import java.io.File;
import java.lang.reflect.Method;

public class MethodRunner {

	public static void main(String[] args) {

		// System.out.println(System.getProperty("user.dir"));
            try {
			String cl = new Utility().getClassName(new File(args[0]));
			callMethods(cl);
            }catch(ArrayIndexOutOfBoundsException e) {
            	System.out.println("Config file name missing as parameter");
            }
	}

	private static void callMethods(String cl) {

		try {
			Object x = Class.forName(cl).newInstance();
			for (Method m : x.getClass().getMethods()) {

				if (m.isAnnotationPresent(RunMe.class)) {
					System.out.println(m.invoke(x, m.getAnnotation(RunMe.class).input())+"\n");
				}

			}

		} catch (Exception e) {
			
		}
	}

}
