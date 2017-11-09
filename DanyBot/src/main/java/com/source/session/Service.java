package com.source.session;

import com.source.utils.logger.Logger;

public class Service {
	
	public static void main(String[] args) {
		
		Logger serviceLogger = Logger.getInstace();
		serviceLogger.info("starting your service");

		Publisher publisher = new Publisher(args);
		publisher.propagateService();
	}
}
