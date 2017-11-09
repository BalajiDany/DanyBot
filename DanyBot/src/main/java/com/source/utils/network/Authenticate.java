package com.source.utils.network;

import java.io.Console;

import com.source.utils.logger.Logger;
import com.source.utils.propeties.GetProperties;
import com.source.utils.propeties.LoadProperties;

public class Authenticate {

	private Logger authenticateLogger = Logger.getInstace();

	private Console console = System.console();

	private static final String FILE_NAME = "authenticate";

	public boolean authenticate() {
		authenticateLogger.info("Requesting for Access");
		authenticateLogger.poll("User Name : ");
		String userName = console.readLine();
		return checkAndAuthenticate(userName);
	}

	private boolean checkAndAuthenticate(String userName) {
		authenticateLogger.poll("Password : ");
		String userPassword = String.valueOf(console.readPassword());
		return validateUserAndPassword(userName, userPassword);
	}

	private boolean validateUserAndPassword(String userName, String userPassword) {
		LoadProperties authenticateProperty = GetProperties.access(FILE_NAME);
		authenticateLogger.info("Validating user");
		if (authenticateProperty.getProperties().containsKey("user." + userName)) {
			if (authenticateProperty.getProperties().getProperty("user." + userName).equals(userPassword)) {
				authenticateLogger.info("Access granted");
				return true;
			}
		}
		authenticateLogger.error("Access Denied");
		return false;
	}
}
