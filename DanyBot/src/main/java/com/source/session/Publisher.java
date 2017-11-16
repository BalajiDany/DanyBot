package com.source.session;

import java.net.InetAddress;
import java.util.Random;

import com.source.session.system.SystemInfoMaker;
import com.source.spring.Server;
import com.source.utils.CommonUtils;
import com.source.utils.logger.Logger;
import com.source.utils.network.Authenticate;
import com.source.utils.network.wifi.HostedNetwork;

public class Publisher {

	private String sessionId;

	private SystemInfoMaker systemInfoMaker;

	private static Logger publisherLogger = Logger.getInstace();

	private final String[] args;

	public static boolean isHosted = false;
	
	public static InetAddress inetAddress;
	
	public Publisher(String[] args) {
		this.args = args;
		publisherLogger.info("Creating the New session");
		sessionId = createNewSessionId();
		publisherLogger.info("Session Id created");

		systemInfoMaker = new SystemInfoMaker();
	}

	private String createNewSessionId() {
		return String.format("%04d", new Random().nextInt(10000));
	}

	public void propagateService() {
		Authenticate authenticate = new Authenticate();
		boolean makePublish = true;
		if(authenticate.authenticate()) {
			if(systemInfoMaker.validateWiFi()) {
				publisherLogger.info("System meets requirements");
				systemInfoMaker.analyseTheWiFiDriver();
				Publisher.isHosted = true;
			} else {
				publisherLogger.warning("Could not found WiFi Modules");
				makePublish = CommonUtils.getYesOrNoResponse("Do you want to continue?");
			}
			if(makePublish) {
				inetAddress = createNewHostedNetwork();
				this.publishServer(systemInfoMaker.getWifiAdapter());
			}
		}
	}

	private InetAddress createNewHostedNetwork() {
		HostedNetwork hostedNetwork = new HostedNetwork(SystemInfoMaker.systemInfo);
		return hostedNetwork.prepareHostedInfo(this.sessionId);
	}

	private void publishServer(String wifiAdapter) {
		publisherLogger.info("Leting the Spirng to initialize the web");
		Server.startServer(this.args);
		publisherLogger.info("Spring Booted Successfully");
		postImplement();
	}

	private void postImplement() {
		if(Publisher.isHosted) {
			publisherLogger.info("Host Created Successfully");
			publisherLogger.info("Wifi : " + HostedNetwork.getHostedInfo().getSsid() +
					" Password : " + HostedNetwork.getHostedInfo().getPassword());
			publisherLogger.info("Spring is injected in : " +  Publisher.inetAddress.getHostAddress());
		} else {
			publisherLogger.info("Spring is injected in : localhost");
		}
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
