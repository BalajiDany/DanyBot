package com.source.utils.network.wifi;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.source.session.system.SystemInfo;
import com.source.utils.CommonUtils;
import com.source.utils.logger.Logger;
import com.source.utils.network.command.Command;
import com.source.utils.propeties.GetProperties;
import com.source.utils.propeties.LoadProperties;

public class HostedNetwork {
	
	private Logger hostedNetworkLogger = Logger.getInstace();
	private SystemInfo systemInfo;
	private static final LoadProperties systemCommands = GetProperties.access("command");
	private static final LoadProperties resourceProperties = GetProperties.access("resource");
	private static final LoadProperties mapperProperties = GetProperties.access("mapper");
	private static HostedInfo hostedInfo = new HostedInfo();
	
	public static HostedInfo getHostedInfo() {
		return hostedInfo;
	}

	public static void setHostedInfo(HostedInfo hostedInfo) {
		HostedNetwork.hostedInfo = hostedInfo;
	}

	public HostedNetwork(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}
	
	public InetAddress prepareHostedInfo(String sessionID) {
		
		hostedNetworkLogger.info("Preparing the SSID");
		hostedInfo.setSsid(resourceProperties.getProperties().getProperty("wlan-ssid"));
		
		hostedNetworkLogger.info("Generating the Hostednetwork passord");
		hostedInfo.setPassword(resourceProperties.getProperties().getProperty("wlan-password"));
		
		hostedNetworkLogger.info("Stoping the hostedNetwork");
		HostedNetwork.stopHostedNetwork();
		CommonUtils.makeDelay((long) 500);
		
		hostedNetworkLogger.info("Initializing the hostedNetwork");
		createHostedNetwork();
		CommonUtils.makeDelay((long) 1000);
		
		hostedNetworkLogger.info("Strating the hostedNetwork");
		HostedNetwork.startHostedNetwork();
		CommonUtils.makeDelay((long) 1000);
		
		hostedNetworkLogger.info("Preparing the Address for spring boot");
		return prepareInetAddress();
	}

	
	
	private InetAddress prepareInetAddress() {
		InetAddress resultAddress = null;
		try {
			hostedNetworkLogger.info("Iterating the Network interfaces to get hosted network");
			Enumeration<NetworkInterface> enumeratedInterface = NetworkInterface.getNetworkInterfaces();
			while (enumeratedInterface.hasMoreElements()) {
				NetworkInterface networkInterface = enumeratedInterface.nextElement();
				if(networkInterface.getDisplayName().contains(
						mapperProperties.getProperties().getProperty("network-interface-wifi"))) {
					Enumeration<InetAddress> iteratableInetAddress = networkInterface.getInetAddresses();
					if(iteratableInetAddress.hasMoreElements()) {
						resultAddress = iteratableInetAddress.nextElement();
						break;
					}
				}
			}
			if(resultAddress == null) {
				resultAddress = InetAddress.getLocalHost();
			}
			hostedNetworkLogger.info("Available Ip : " + resultAddress.getHostAddress());
		} catch (UnknownHostException e) {
			hostedNetworkLogger.error("Unable to access the internet address");
			hostedNetworkLogger.error(e.getMessage());
		} catch (SocketException e) {
			hostedNetworkLogger.error("Unable to access the internet address");
			hostedNetworkLogger.error(e.getMessage());
		}
		return resultAddress;
	}

	public void createHostedNetwork() {
		hostedNetworkLogger.info("Creating the host network");
		String[] commandArgument = new String[2];
		commandArgument[0] = hostedInfo.getSsid();
		commandArgument[1] = hostedInfo.getPassword();
		Command.execute(CommonUtils.prepareCommand(systemCommands.getProperties().getProperty("wifi-set-host"),
				commandArgument));
	}
	
	public static void startHostedNetwork() {
		Command.execute(systemCommands.getProperties().getProperty("wifi-start-host"));
	}
	
	public static void stopHostedNetwork() {
		Command.execute(systemCommands.getProperties().getProperty("wifi-stop-host"));
	}
}
