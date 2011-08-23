package org.bangbang.java.c2s.impl.mock;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bangbang.java.c2s.IAmbassador;
import org.bangbang.java.c2s.IRequest;

public class Ambassador implements IAmbassador {
	private static int sId;
	private Executor mExecutor;
	
	public Ambassador(){
		mExecutor = new ThreadPoolExecutor(5, 20, Long.MAX_VALUE, TimeUnit.MILLISECONDS, new LinkedBlockingDeque());
	}

	@Override
	public long scheduleRequest(IRequest req, Callback callback) {
		// TODO Auto-generated method stub
		
		mExecutor.execute(new HelloWorldRequest(callback));
		return sId++;
	}

	@Override
	public void unScheduleRequest(long requestId) {
		// TODO Auto-generated method stub
	}
}
