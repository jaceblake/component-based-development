package de.htw.ws.kbe.EmailChecker;
import org.apache.commons.validator.routines.EmailValidator;

public class ExternalEmailSyntaxChecker {
	
	public boolean isValidEmailAddress(String emailAddress) {
        return  EmailValidator.getInstance().isValid(emailAddress);
	}
}
