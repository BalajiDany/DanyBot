package com.source.utils.logger;

public enum LoggerFactoryLevel {

	HIGH,
	WARNING,
	ERROR,
	INFO,
	POLL;

	@Override
	public String toString(){
		if(this.equals(LoggerFactoryLevel.HIGH)){
			return "HIGH";
		} else if(this.equals(LoggerFactoryLevel.WARNING)){
			return "WARNING";
		} else if(this.equals(LoggerFactoryLevel.ERROR)){
			return "ERROR";
		} else if(this.equals(LoggerFactoryLevel.INFO)){
			return "INFO";
		} else if(this.equals(LoggerFactoryLevel.POLL)){
			return "POLL";
		}else{
			return "NO LEVEL";
		}
	}
}
