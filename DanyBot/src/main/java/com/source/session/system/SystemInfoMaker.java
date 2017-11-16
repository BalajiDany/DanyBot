package com.source.session.system;

import com.source.utils.logger.Logger;
import com.source.utils.network.command.Command;
import com.source.utils.propeties.GetProperties;
import com.source.utils.propeties.LoadProperties;

public class SystemInfoMaker {

	public static SystemInfo systemInfo = new SystemInfo();

	private Logger systemLogger = Logger.getInstace();

	private String wifiAdapter = "";

	private LoadProperties systemCommands = GetProperties.access("command");
	private LoadProperties mapperProperties = GetProperties.access("mapper");
	private LoadProperties resourceProperties = GetProperties.access("resource");

	public final static String NULL_RESPONSE = "";

	public SystemInfoMaker() {
		systemLogger.info("Extracting the System Info");

		String systemInformation = Command.execute(systemCommands.getProperties().getProperty("system-info"));

		systemInfo.setHostName(extractStringFromLine(systemInformation,
				mapperProperties.getProperties().getProperty("host-name")));
		systemInfo.setInstalledRam(extractStringFromLine(systemInformation,
				mapperProperties.getProperties().getProperty("installed-ram")));
		systemInfo.setAvailableRam(extractStringFromLine(systemInformation,
				mapperProperties.getProperties().getProperty("available-ram")));

		String networkInterfaces = Command.execute(systemCommands.getProperties().getProperty("wifi-interface"));

		addInterfaceList(networkInterfaces);

		systemLogger.info("System information extracted");
		systemLogger.high(systemInfo.toString());
	}

	private void addInterfaceList(String networkInterfaces) {
		String[] splitedString = networkInterfaces.split(
				mapperProperties.getProperties().getProperty("wifi-interface-name"));
		for(int stringCount = 1; stringCount < splitedString.length; stringCount++) {
			if(!splitedString[stringCount].contains(mapperProperties.getProperties().getProperty("wifi-hardware-off")))
				systemInfo.getWifiDriverList().add(splitedString[stringCount].substring(
						splitedString[stringCount].indexOf(": ") + 2, splitedString[stringCount].indexOf("\n")));
		}
	}

	private String extractStringFromLine(String systemInfo, String key) {
		String[] resultString = systemInfo.split(key);
		if(resultString.length > 1){
			String extractedLine = resultString[1];
			int startIndex, endIndex = extractedLine.indexOf('\n');
			for(startIndex = 0; startIndex < endIndex; startIndex++){
				if(extractedLine.charAt(startIndex) != ' '){
					break;
				}
			}
			return extractedLine.substring(startIndex, endIndex);
		}
		return NULL_RESPONSE;
	}

	public boolean validateWiFi() {
		systemLogger.info("Avaliable Driver" + systemInfo.getWifiDriverList().size());
		if(systemInfo.getWifiDriverList().size() > 0) {
			return true;
		}
		return false;
	}

	public void analyseTheWiFiDriver() {
		String mainDriver = resourceProperties.getProperties().getProperty("wifi-module");
		for(String driver:systemInfo.getWifiDriverList()) {
			if(driver.equals(mainDriver)) {
				this.wifiAdapter = driver;
			}
		}
	}

	public String getWifiAdapter() {
		return wifiAdapter;
	}

	public void setWifiAdapter(String wifiAdapter) {
		this.wifiAdapter = wifiAdapter;
	}
}
