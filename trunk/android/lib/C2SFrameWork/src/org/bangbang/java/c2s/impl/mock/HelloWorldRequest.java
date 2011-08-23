package org.bangbang.java.c2s.impl.mock;

import org.bangbang.java.c2s.IAmbassador;
import org.bangbang.java.c2s.IRequest;

public class HelloWorldRequest extends Request {
	private IAmbassador.Callback mCallback;

	public HelloWorldRequest(IAmbassador.Callback callback) {
		super();
		mCallback = callback;
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	protected void doRequest() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5 * 1000);
			mCallback.onResponse(new HelloWorldResponse());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
