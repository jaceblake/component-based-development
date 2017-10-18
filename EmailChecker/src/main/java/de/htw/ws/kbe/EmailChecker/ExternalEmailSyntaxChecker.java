package de.htw.ws.kbe.EmailChecker;
import org.apache.commons.validator.routines.*;

public class ExternalEmailSyntaxChecker {

	EmailValidator ev = EmailValidator.getInstance();
	
	public boolean isValidEmailAddress(String emailAddress) {
        return  ev.isValid(emailAddress);
	}
}
