package org.bangbang.song.android.fileman.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import org.bangbang.song.android.common.debug.Log;
import org.bangbang.song.android.fileman.FileManApplication;
import org.bangbang.song.android.fileman.R;

import android.content.Context;

/**
 * @author bangbang.song@gmail.com
 *  2010 12/09
 *
 */
public class Ext2Mime {
	private static final String TAG = Ext2Mime.class.getSimpleName();
	
	public static Hashtable<String, String> sSysExt2MimeMaps =  new Hashtable<String, String>();
	public static Hashtable<String, String> sUsrExt2MimeMaps =  new Hashtable<String, String>();
	
	public static void init(Context context){
		sSysExt2MimeMaps = parse(context.getResources().openRawResource(R.raw.ext2mimetype));
		parseUseMap();
	}
	
	public static void parseUseMap(){
		try {
			sUsrExt2MimeMaps = parse(new FileInputStream(FileManApplication.APP_EXTERNAL_EXT_2_MIME_MAP_FILE));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
		}
	}
	
	private static Hashtable<String, String> parse(InputStream in){
		Hashtable<String, String> maps = new Hashtable<String, String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = reader.readLine()) != null	){
				line = line.trim();
				
				// do my best
				if (!line.startsWith("#")){
					String[] parts = line.split("\\s+");
					if (null != parts && parts.length > 1){
						maps.put(parts[0], parts[1]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
		}
		return maps;
	}
}
