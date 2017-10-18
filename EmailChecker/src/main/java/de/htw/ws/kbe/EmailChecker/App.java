package de.htw.ws.kbe.EmailChecker;

import org.junit.runner.JUnitCore;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        String email="smosgmail.com";
        System.out.println( new MyEmailSyntaxChecker().isValidEmailAddress(email));
        JUnitCore.runClasses(EmailCheckerTest.class);
    }
}
