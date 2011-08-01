package org.bangbang.java.c2s.impl.mock;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bangbang.java.c2s.IAmbassador;
import org.bangbang.java.c2s.IRequest;

public class Ambassador implements IAmbassador {
	private Executor mExecutor;
	
	public Ambassador(){
		mExecutor = new ThreadPoolExecutor(5, 20, Long.MAX_VALUE, TimeUnit.MILLISECONDS, new LinkedBlockingDeque());
	}

	@Override
	public void scheduleRequest(IRequest req, Callback callback) {
		// TODO Auto-generated method stub
		mExecutor.execute(new HelloWorldRequest(req, callback));
	}

	@Override
	public void unScheduleRequest(IRequest req) {
		// TODO Auto-generated method stub

	}

}
