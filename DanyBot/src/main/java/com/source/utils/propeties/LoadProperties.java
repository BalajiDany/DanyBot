package com.source.utils.propeties;

import java.io.FileReader;
import java.util.Properties;

import com.source.utils.logger.Logger;

public class LoadProperties {

	private Properties properties = new Properties();

	private static final String propertyPath = "./properties/";

	private Logger propertyLogger = Logger.getInstace();

	public LoadProperties(String fileName) {
		propertyLogger.info("Loading properties " + propertyPath + fileName);
		try {
			FileReader fileReader = new FileReader(propertyPath + fileName);
			properties.load(fileReader);
			fileReader.close();
			propertyLogger.info("Loading complete.");
		} catch (java.io.IOException e) {
			propertyLogger.error("Cannot read the file form the specified path : " + propertyPath + fileName);
			propertyLogger.error(e.getMessage());
		}
		if (properties.size() != 0) {
			propertyLogger.info("Property Size " + properties.size());
		} else {
			propertyLogger.warning(fileName + " is an empty file");
		}
	}

	public Properties getProperties() {
		return properties;
	}
}
