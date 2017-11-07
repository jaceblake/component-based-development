package de.htw.ws.kbe.runMeRunner;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class UtillityTest {

	@After
	public void cleanUp() {
		System.out.println("clean up");
	}

	/**
	 * Rigourous Test :-)
	 */
	@Test
	public void configNotEmpty() {
		Assert.assertEquals("de.htw.ws.kbe.runMeRunner.AnnotationKlasse", Utility.getClass(new File("config.properties")));

	}

}
