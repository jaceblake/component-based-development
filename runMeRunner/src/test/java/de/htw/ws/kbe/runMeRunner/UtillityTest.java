package de.htw.ws.kbe.runMeRunner;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UtillityTest {
	
	
	Utility tester;

	@Rule
	public ExpectedException rule = ExpectedException.none();
	
	
	@Before
	public void setUp() {
		tester = new Utility();
	}

	@After
	public void cleanUp() {
		System.out.println("clean up");
	}



	/**
	 * Rigourous Test :-)
	 */

	
	@Test
	public void configNotEmpty() {
		Assert.assertEquals("de.htw.ws.kbe.runMeRunner.AnnotationKlasse", tester.getClassName(new File("config.properties")));

	}

}
