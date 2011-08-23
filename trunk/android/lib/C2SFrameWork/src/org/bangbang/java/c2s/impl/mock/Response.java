package org.bangbang.java.c2s.impl.mock;

import org.bangbang.java.c2s.IRequest;
import org.bangbang.java.c2s.IResponse;

abstract public class Response implements IResponse{
	protected boolean mCanceld;
	protected long mUTCTimeStamp;
	protected String mScript;
	protected Object mData;
	protected int mType;
	protected int mTrafficType;
	protected int mId;

	/**
	 * do a deep-copy.
	 * 
	 * @param target
	 */
	public Response(IRequest target) {
		mUTCTimeStamp = target.getUTCTimeStamp();
		mScript = target.getScript();
		mData = target.getData();
		mTrafficType = target.getTrafficType();
		mType = target.getType();
		mId = target.getId();
		mCanceld = false;
	}
	
	public Response(){
		mUTCTimeStamp = -1;
		mScript = "";
		mData = null;
		mTrafficType = TYPE_SERVER2CLIENT;
		mType = TYPE_UNKOWN;
		mId = TYPE_UNKOWN;
		mCanceld = false;
	}

	public long getmUTCTimeStamp() {
		return mUTCTimeStamp;
	}

	public void setUTCTimeStamp(long mUTCTimeStamp) {
		this.mUTCTimeStamp = mUTCTimeStamp;
	}

	public void setmScript(String mScript) {
		this.mScript = mScript;
	}

	public void setData(byte[] mData) {
		this.mData = mData;
	}

	public void setType(int mType) {
		this.mType = mType;
	}

	public void setTrafficType(int mTrafficType) {
		this.mTrafficType = mTrafficType;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return mId;
	}

	@Override
	public int getTrafficType() {
		// TODO Auto-generated method stub
		return mTrafficType;
	}

	@Override
	public int getType() {
		return mType;
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return mData;
	}

	@Override
	public String getScript() {
		// TODO Auto-generated method stub
		return mScript;
	}

	@Override
	public long getUTCTimeStamp() {
		// TODO Auto-generated method stub
		return mUTCTimeStamp;
	}
}
