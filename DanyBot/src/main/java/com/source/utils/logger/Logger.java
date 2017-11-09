package com.source.utils.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Logger implements LoggerFactoryInterface {

	private static final Logger logger = new Logger();
	
	public static Logger getInstace() {
		return Logger.logger;
	}

	private Logger() {}
	
	private static DateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss.SSSS");

	@Override
	public void info(String information) {
		printLog(information, LoggerFactoryLevel.INFO);
	}

	@Override
	public void warning(String warning) {
		printLog(warning, LoggerFactoryLevel.WARNING);
	}

	@Override
	public void error(String error) {
		printLog(error, LoggerFactoryLevel.ERROR);
	}

	@Override
	public void poll(String poll) {
		printLog(poll, LoggerFactoryLevel.POLL);
	}

	@Override
	public void high(String high) {
		printLog(high, LoggerFactoryLevel.HIGH);
	}

	private static void printLog(String value, LoggerFactoryLevel level){
		if(!value.isEmpty()){
			System.out.print(prepareThePrintString(value, level.toString())
					+ (level.equals(LoggerFactoryLevel.POLL) ? "" : "\n"));
		}
	}

	private static String prepareThePrintString(String printValue, String level){
		Date date = Calendar.getInstance().getTime();
		return String.format("%-20s ", dateFormatter.format(date))
				+ String.format("%-10s", "[" + level + "]") + ": " +printValue;
	}
}