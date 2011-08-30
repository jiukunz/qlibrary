package org.bangbang.song.android.mediaplayer.compatibility;

import java.lang.reflect.InvocationTargetException;

import android.media.MediaPlayer;

/**
 * @author bysong
 * 
 * to support compatibility suspend() & resume() methods.
 *
 */
public class CompatibilityMediaPlayer extends MediaPlayer {
	private static final String METHOD_NAME_SUSPEND = "suspend";
	private static final String METHOD_NAME_RESUME = "resume";
	
	public boolean suspend(){
		try {
			return (Boolean)getClass().getMethod(METHOD_NAME_SUSPEND, null).invoke(this, null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean resume(){
		try {
			return (Boolean)getClass().getMethod(METHOD_NAME_RESUME, null).invoke(this, null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
