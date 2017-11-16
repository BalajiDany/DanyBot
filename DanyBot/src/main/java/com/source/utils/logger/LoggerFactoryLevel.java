package com.source.utils.logger;

public enum LoggerFactoryLevel {

	HIGH,
	WARNING,
	ERROR,
	INFO,
	POLL;

	@Override
	public String toString(){
		return this.name();
	}
}
