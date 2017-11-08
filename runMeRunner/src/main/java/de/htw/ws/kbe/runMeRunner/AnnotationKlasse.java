package de.htw.ws.kbe.runMeRunner;

import de.htw.ws.kbe.EmailChecker.MyEmailSyntaxChecker;

public class AnnotationKlasse {

	@RunMe(input = "myName@domain.com")
	public boolean method22(String input) {
		System.out.println("In method22");
		System.out.println(input);
		return new MyEmailSyntaxChecker().isValidEmailAddress(input);
	}
	
	@RunMe(input = "test@test.com")
	public boolean method11(String input) {
		System.out.println("In method11");
		System.out.println(input);
		return new MyEmailSyntaxChecker().isValidEmailAddress(input);
	}
	
	
	@RunMe(input = "33@test.com")
	public boolean method33(String input) {
		System.out.println("In method33");
		System.out.println(input);
		return new MyEmailSyntaxChecker().isValidEmailAddress(input);
	}
	
	@Source
	public boolean methodSource(String input) {
		System.out.println("In methodSource");
		return new MyEmailSyntaxChecker().isValidEmailAddress(input);
	}
	

	public void method55(String input) {
		System.out.println("In method55");
		
	}

}
