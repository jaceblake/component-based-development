package de.htw.ws.kbe.EmailChecker;

public class MyEmailSyntaxChecker {
    
	public String email;
	
	public boolean isValidEmailAddress(String emailAddress) {
		this.email = emailAddress;
		if(emailAddress == null) {
			return false;
		}
		
		else if(emailAddress.equals("")) {
			return false;
		}
		
		else if(!emailAddress.contains("@")) {
			return false;
		}

		else if(!emailAddress.substring(emailAddress.indexOf("@"), emailAddress.length()-1).contains(".")) {
			return false;
		}
		
		else if(emailAddress.substring(emailAddress.indexOf("."),emailAddress.length()-1).length()<2) {
			return false;	
		}
		
		else if(emailAddress.contains(" ")) {
			return false;
		}
		
		
		return true;
		
	}
	

	
}
