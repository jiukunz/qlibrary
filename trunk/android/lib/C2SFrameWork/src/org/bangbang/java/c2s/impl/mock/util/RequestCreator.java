package org.bangbang.java.c2s.impl.mock.util;

import org.bangbang.java.c2s.impl.mock.HelloWorldRequest;
import org.bangbang.java.c2s.impl.mock.Request;

public class RequestCreator {
	public static Request create(int type, String desc, Object data){
		Request req = new HelloWorldRequest(null);
		
		return req;
	}
}
