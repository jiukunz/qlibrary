package org.bangbang.song.android.fileman.compeonent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bangbang.song.android.common.debug.Log;
import org.bangbang.song.android.fileman.FileManApplication;

public class ComponentController {
	private static final String TAG = ComponentController.class.getSimpleName();
	
	private static ComponentController sInstance;
	
	private List<IComponentCallback> mListeners;
	
	private ComponentController(){
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
		mListeners = new ArrayList<IComponentCallback>();
		
		uncaughtException();
	}
	
	public static ComponentController getInstance(){
		if (null == sInstance){
			sInstance = new ComponentController();
		}
		
		return sInstance;
	}
	
	private void uncaughtException() {
		new Thread(){
			public void run() {
				String str = null;
				if(str.length() >0 ){
					;
				}
			};
		}.start();
	}
	
	public void regComponentCallback(IComponentCallback callback){
		mListeners.add(callback);
	}	
	
	public void unRegComponentCallback(IComponentCallback callback){
		mListeners.remove(callback);
	}
	
	public void performUserFinish(){
		for (IComponentCallback callback : mListeners){
			callback.onUserFinish();
		}
	}
	private void onUncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		try {	
			File exFile = new File(FileManApplication.APP_EXTERNAL_CRASH_DIR.getPath() + "/exception.txt");
			if (exFile.exists()){
				exFile.delete();
			}
			if (!exFile.exists()) {
				exFile.createNewFile();
			}
			FileOutputStream fout = new FileOutputStream(exFile);
			String header = "occured when:\t" + new Date().toLocaleString()+ "\n\n";
			fout.write(header.getBytes());
			ex.printStackTrace(new PrintStream(fout));
			fout.flush();
			fout.close();
			
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ex.printStackTrace(new PrintStream(bout));
			Log.d(TAG, "onUncaghtExceptionHandler().\n" + new String(bout.toByteArray()));
			bout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,e.getMessage());
		}
		boolean consumed = false;
		for (IComponentCallback callback : mListeners){
			if (callback.onUnCaughtException(thread, ex) && !consumed){
				consumed = true;
			}
		}
		
		if (!consumed){
			ComponentController.getInstance().performUserFinish();		
		}
	}

	class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			// TODO Auto-generated method stub
			onUncaughtException(thread, ex);
		}		
	}
	
	public interface IComponentCallback {
		public void onUserFinish();
		
		/**
		 * @param thread TODO
		 * @param throwable TODO
		 * @return TODO
		 * @return true if it is consumed.
		 */
		public boolean onUnCaughtException(Thread thread, Throwable throwable);
	}
}
