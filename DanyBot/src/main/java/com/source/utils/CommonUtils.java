package com.source.utils;

import java.io.Console;

import com.source.utils.logger.Logger;

public class CommonUtils {

	private static Console console = System.console();
	
	private static Logger commonUtilsLogger = Logger.getInstace();
	
	public static boolean getYesOrNoResponse(String message) {
		commonUtilsLogger.poll(message + " [Y/N] : ");
		String response = console.readLine().toLowerCase();
		if(response.equals("n")) {
			return false;
		} else if(response.equals("y")){
			return true;
		} else {
			return getYesOrNoResponse(message);
		}
	}
	
	public static String prepareCommand(String command, String[] arguments) {
		commonUtilsLogger.info("Preparing the command with args");
		for(String argument:arguments) {
			command = command.replaceFirst("%s%", argument);
		}
		return command;
	}
	
	public static void makeDelay(Long delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
