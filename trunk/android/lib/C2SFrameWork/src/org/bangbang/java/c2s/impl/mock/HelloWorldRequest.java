package org.bangbang.java.c2s.impl.mock;

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
		try {
			Thread.sleep(5 * 1000);
			mCallback.onResponse(null);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
