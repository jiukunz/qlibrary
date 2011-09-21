
package org.bangbang.java.c2s;

import org.bangbang.java.c2s.IAmbassador.Callback;

public interface IAmbassador {
    public interface Callback {
        /**
         * a response has arrived. 
         * <pre>
         * <strong>NOTE</strong>: do NOT assume that
         * callback will be called in the same thread in which you schedule this
         * request. As a result you must be prepared to sync threading-stuff.
         * 
         * @param res
         * @see IAmbassador#scheduleRequest(IRequest, Callback)
         */
        public void onResponse(IResponse res);
    }

    /**
     * schedule a request to be executed in the future. when a response arrives,
     * callback will be called
     * <p>
     * <strong>NOTE</strong>: do NOT assume that callback will be called in the
     * same thread in which you schedule this request. As a result you must be
     * prepared to sync threading-stuff.
     * 
     * @param req
     * @param callback
     * @return request id.
     * @see #unScheduleRequest(long)
     * @see Callback#onResponse(IResponse)
     */
    public long scheduleRequest(IRequest req, Callback callback);

    /**
     * @param requestId id of the scheduled request.
     * @see #scheduleRequest(IRequest, Callback)
     */
    public void unScheduleRequest(long requestId);
}
