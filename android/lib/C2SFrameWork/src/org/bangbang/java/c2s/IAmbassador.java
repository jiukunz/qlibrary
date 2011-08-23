package org.bangbang.java.c2s;

import org.bangbang.java.c2s.IAmbassador.Callback;

public interface IAmbassador {
	public interface Callback{
		public void onResponse(IResponse res);
	}
	
	/**
	 * schedule a request to be executed in the future.
	 * when a response arrives, callback will be called
	 * in the thread in which the request has been sent.
	 * 
	 * 
	 * @param req
	 * @param callback
	 * 
	 * @return request id.
	 * 
	 * @see #unScheduleRequest(long)
	 */
	public long scheduleRequest(IRequest req, Callback callback);
	
	/**
	 * @param requestId id of the scheduled request.
	 * 
	 * @see #scheduleRequest(int, String, String, Object, Callback)
	 */
	public void unScheduleRequest(long requestId);
}
