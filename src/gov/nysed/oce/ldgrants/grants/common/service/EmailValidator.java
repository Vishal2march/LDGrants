
/******
 * @author  ghudson2
 * @version 1.0
 *
 * Development Environment        :  JDeveloper 12c
 * Name of the Application        :  EmailValidator.java
 * Creation/Modification History  :  ghudson2       6/29/16     Created
 * 
 * Description
 * Validate FROM email address and domain of address
 * 
*****/
package gov.nysed.oce.ldgrants.grants.common.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;

public class EmailValidator {
	final static Logger logger = Logger.getLogger(EmailValidator.class);

	public EmailValidator() {
		// TODO Auto-generated constructor stub
	}

	public boolean isEmailAddressValid(String emailAddress) {
		// Find the separator for the domain name
		int pos = emailAddress.indexOf('@');

		// If the address does not contain an '@', it's not valid
		if (pos == -1) {
			logger.error("invalid email address entered");
			return false;
		}

		// Isolate the domain/machine name and get a list of mail exchangers
		String domain = emailAddress.substring(++pos);

		try {
			InetAddress.getByName(domain);
		} catch (UnknownHostException e) {
			logger.error("UnknownHostException ----- Most likely caused by invalid domain");
			return false;
		}

		return true;
	}
}
