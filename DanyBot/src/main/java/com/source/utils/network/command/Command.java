package com.source.utils.network.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.source.utils.logger.Logger;

public class Command {

	private static final Logger commandLogger = Logger.getInstace();

	public static String execute(String command){
		return execute(command, true);
	}

	public static String execute(String command, boolean synchronous){
		commandLogger.high("Executing the command " + command);
		StringBuilder output = new StringBuilder();
		Process commandProcess;
		try {

			commandProcess = Runtime.getRuntime().exec(command);

			if(synchronous){
				commandLogger.info("Waiting for response...");
				commandProcess.waitFor();
				commandLogger.info("Preparing the response");
			}

			BufferedReader responseReader = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()));
			String line;
			while((line = responseReader.readLine()) != null){
				output.append(line).append('\n');
			}
			commandLogger.info(command + " command executed and response created");
		} catch (IOException e) {
			commandLogger.error("Could not run the command " + command);
			commandLogger.error(e.getMessage());
		} catch (InterruptedException e){
			commandLogger.error("Could not Wait for the result " + command);
			commandLogger.error(e.getMessage());
		}
		return output.toString();
	}
}
