package org.bangbang.java.c2s;

/**
 * @author bysong@tudou.com
 *
 */
public interface IMessage {
	public static final int TYPE_SERVER2CLIENT = 1;
	public static final int TYPE_CLIENT2SERVER = 2;
	public int getId();
	
	/**
	 * @return traffic type
	 * 
	 * @see TYPE_CLIENT2SERVER
	 * @see TYPE_SERVER2CLIENT
	 */
	public int getTrafficType();
	public byte[] getData();
	public String getScript();
	public long getUTCTimeStamp();
}
