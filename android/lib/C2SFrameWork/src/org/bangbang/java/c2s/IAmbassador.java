package org.bangbang.java.c2s;

public interface IAmbassador {
	public interface Callback{
		public void onResponse(IResponse res);
	}
	
	/**
	 * schedule a request to be executed in the future.
	 * when a response arrives, callback will NOT be called
	 * in the thread in which the request has been sent.
	 * 
	 * @param req
	 * @param callback
	 */
	public void scheduleRequest(IRequest req, Callback callback);
	public void unScheduleRequest(IRequest req);
}
