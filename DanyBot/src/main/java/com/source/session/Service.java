package com.source.session;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.boot.context.embedded.PortInUseException;

import com.source.utils.logger.Logger;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;

public class Service {
	
	public static void main(String[] args) {
		
		Logger serviceLogger = Logger.getInstace();
//		serviceLogger.info("starting your service");
//
//		Publisher publisher = new Publisher(args);
//		publisher.propagateService();
		
		Iterator <CommPortIdentifier> port = getAvailableSerialPorts().iterator();
		while(port.hasNext()) {
			serviceLogger.info(port.next().getName());
		}
		serviceLogger.info("Scan end");
	}

	 public static HashSet<CommPortIdentifier> getAvailableSerialPorts() {
	        HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
	        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
	        while (thePorts.hasMoreElements()) {
	            CommPortIdentifier com = (CommPortIdentifier) thePorts.nextElement();
	            switch (com.getPortType()) {
	            case CommPortIdentifier.PORT_SERIAL:
	                try {
	                    CommPort thePort = com.open("CommUtil", 50);
	                    thePort.close();
	                    h.add(com);
	                } catch (PortInUseException e) {
	                    System.out.println("Port, "  + com.getName() + ", is in use.");
	                } catch (Exception e) {
	                    System.err.println("Failed to open port " +  com.getName());
	                    e.printStackTrace();
	                }
	            }
	        }
	        return h;
	    }
}
