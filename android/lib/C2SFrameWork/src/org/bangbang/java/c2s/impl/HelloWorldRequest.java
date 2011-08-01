package org.bangbang.java.c2s.impl;

import org.bangbang.java.c2s.IAmbassador;
import org.bangbang.java.c2s.IRequest;
import org.bangbang.java.c2s.Request;

public class HelloWorldRequest extends Request {
	private IAmbassador.Callback mCallback;

	public HelloWorldRequest(IRequest target, IAmbassador.Callback callback) {
		super(target);
		mCallback = callback;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doRequest() {
		// TODO Auto-generated method stub
		
	}

}
