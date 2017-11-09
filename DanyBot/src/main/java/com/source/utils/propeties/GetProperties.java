package com.source.utils.propeties;

import java.util.HashMap;
import java.util.Map;

public class GetProperties {
	
	private static final String[] propertyFiles = new String[] {"authenticate", "command", "mapper", "resource"};
	private static Map<String, LoadProperties> propertyMap = new HashMap<String, LoadProperties>();
	private static GetProperties getProperty = null;
	
	public static LoadProperties access(String fileName) {
		if(getProperty == null) {
			getProperty = new GetProperties();
		}
		return propertyMap.get(fileName);
	}
	
	private GetProperties() {
		for(String fileName:propertyFiles) {
			propertyMap.put(fileName, new LoadProperties(fileName + ".properties"));
		}
	}
}
