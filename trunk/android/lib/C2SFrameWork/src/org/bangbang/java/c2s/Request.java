package org.bangbang.java.c2s;


abstract public class Request implements IRequest, Runnable, Cancelable{
	private IRequest mTarget;
	private boolean mCanceld;
	
	public Request(IRequest target){
		mTarget = target;
		mCanceld = false;
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return mTarget.getId();
	}

	@Override
	public int getTrafficType() {
		// TODO Auto-generated method stub
		return mTarget.getTrafficType();
	}
	
	@Override
	public int getType(){
		return mTarget.getType();
	}

	@Override
	public byte[] getData() {
		// TODO Auto-generated method stub
		return mTarget.getData();
	}

	@Override
	public String getScript() {
		// TODO Auto-generated method stub
		return mTarget.getScript();
	}

	@Override
	public long getUTCTimeStamp() {
		// TODO Auto-generated method stub
		return mTarget.getUTCTimeStamp();
	}


	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		mCanceld = true;
	}

	@Override
	final public void run() {
		// TODO Auto-generated method stub
		if (!mCanceld){
			doRequest();
		}
	}

	abstract protected void doRequest();

}
