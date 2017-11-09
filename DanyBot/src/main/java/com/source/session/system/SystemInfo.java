package com.source.session.system;

import java.util.ArrayList;
import java.util.List;

public class SystemInfo {
	
    private String hostName = "";

    private String installedRam = "";

    private String availableRam = "";

    private List<String> wifiDriverList = new ArrayList<String>();

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getInstalledRam() {
		return installedRam;
	}

	public void setInstalledRam(String installedRam) {
		this.installedRam = installedRam;
	}

	public String getAvailableRam() {
		return availableRam;
	}

	public void setAvailableRam(String availableRam) {
		this.availableRam = availableRam;
	}

	public List<String> getWifiDriverList() {
		return wifiDriverList;
	}

	public void setWifiDriverList(List<String> wifiDriverList) {
		this.wifiDriverList = wifiDriverList;
	}
	
	@Override
	public String toString() {
		StringBuilder interfaceBuilder = new StringBuilder();
		for(String interfaceString:this.wifiDriverList) {
			interfaceBuilder.append(" -" + interfaceString);
		}
		return this.hostName + " " + this.availableRam + "/" + this.installedRam + interfaceBuilder.toString();
	}

}
