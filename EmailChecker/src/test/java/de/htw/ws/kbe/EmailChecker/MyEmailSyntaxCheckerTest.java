package de.htw.ws.kbe.EmailChecker;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyEmailSyntaxCheckerTest {

	MyEmailSyntaxChecker tester;

	@Before
	public void setUp() {
		tester = new MyEmailSyntaxChecker();
	}

	@After
	public void cleanUp() {
		System.out.println("clean up");
	}

	/**
	 * Rigourous Test :-)
	 */

	@Test
	public void ifEmailAddressIsNullValue() {

		Assert.assertEquals(false, tester.isValidEmailAddress(null));

	}

	@Test
	public void ifEmailAddressIsEmptyString() {
		Assert.assertEquals(false, tester.isValidEmailAddress(" "));
	}

	@Test
	public void notContainsAtCharacter() {
		Assert.assertEquals(false, tester.isValidEmailAddress("ssgmail.com"));
	}

	@Test
	public void notContainsDotCharacter() {
		Assert.assertEquals(false, tester.isValidEmailAddress("ss@gmailcom"));
	}

	@Test
	public void isValidDomain() {
		Assert.assertEquals(false, tester.isValidEmailAddress("ss@gmail.c"));
	}

	@Test
	public void ifEmailAddressContainsBlankCharactrs() {
		Assert.assertEquals(false, tester.isValidEmailAddress("ss@gmail. com"));
	}

}
