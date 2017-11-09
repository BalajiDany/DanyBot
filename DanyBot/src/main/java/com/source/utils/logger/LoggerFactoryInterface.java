package com.source.utils.logger;

public interface LoggerFactoryInterface {

	void info(String information);

	void warning(String warning);

	void error(String error);

	void poll(String poll);

	void high(String high);
}
