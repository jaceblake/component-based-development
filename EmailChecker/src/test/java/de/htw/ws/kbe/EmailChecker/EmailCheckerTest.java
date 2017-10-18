package de.htw.ws.kbe.EmailChecker;

import static org.junit.Assert.*;

import org.junit.Test;



public class EmailCheckerTest {
	
	
	public MyEmailSyntaxChecker tester;
	 /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public EmailCheckerTest(  )
    {
        tester = new MyEmailSyntaxChecker();
    }

    /**
     * Rigourous Test :-)
     */
  
    @Test
    public void notNull() {
    	System.out.println(tester.email);
    	assertEquals(false, tester.isValidEmailAddress(null) );
    	
    }
    
    @Test
    public void notEmptyEmail() {
    	assertEquals(false, tester.isValidEmailAddress(" "));
    }
    
    @Test
    public void notWithoutAt() {
    	assertEquals(false, tester.isValidEmailAddress("ss@gmail.com"));
    }
    
 
    
    @Test
    public void containsDot() {
    	assertEquals(false, tester.isValidEmailAddress("ss@gmailcom"));
    }
    @Test
    public void isValidDomain() {
    	assertEquals(false, tester.isValidEmailAddress("ss@gmail.c"));
    }
    @Test
    public void notContainsEmptySpace() {
    	assertEquals(false, tester.isValidEmailAddress("ss@gmail.com "));
    }
    

}
