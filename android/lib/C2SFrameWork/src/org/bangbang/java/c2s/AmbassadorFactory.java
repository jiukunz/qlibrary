package org.bangbang.java.c2s;

import org.bangbang.java.c2s.impl.Ambassador;

public class AmbassadorFactory {
	private static AmbassadorFactory sInstance;
	
	private AmbassadorFactory(){

	}
	
	public static AmbassadorFactory getInstance(){
		if (null == sInstance){
			sInstance = new AmbassadorFactory();
		}
		
		return sInstance;
	}
	
	public IAmbassador createAmbassador(int policy){
		return new Ambassador();
	}
	
	public Object createOOXX(int noop){
		return null;
	}
}
