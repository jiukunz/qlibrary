package org.bangbang.java.c2s;

/**
 * @author bysong@tudou.com
 *
 */
public interface IMessage {
	public static final int TYPE_UNKOWN = -1;
	public static final int TYPE_SERVER2CLIENT = 1;
	public static final int TYPE_CLIENT2SERVER = 2;	
	/**
	 * @return traffic type
	 * 
	 * @see TYPE_CLIENT2SERVER
	 * @see TYPE_SERVER2CLIENT
	 */
	public int getTrafficType();
	
	public int getType();
	
	/**
	 * @return
	 */
	public int getId();

	/**
	 * @return opaque data associated with this message.
	 */
	public Object getData();
	/**
	 * @return human readable description.
	 */
	public String getDescription();
	public long getUTCTimeStamp();
}
