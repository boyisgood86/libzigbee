package cn.acadiatech.telecom.box.utils;

import android.content.Context;
import android.content.SharedPreferences;



public class PreferencesUtil {
	private static SharedPreferences mSharedPreferences;
	private static SharedPreferences.Editor mEditor;
	
	public static void putString(Context context, String key, String value) {
		if (null == mEditor)
			mEditor = context.getSharedPreferences(
					Constant.KEY_MONETWARE_PREFERENCES,
					Context.MODE_PRIVATE).edit();

		mEditor.putString(key, value);
		mEditor.commit();
	 }

	public static String getStringByKey(Context context, String key) {
		if (null == mSharedPreferences)
			mSharedPreferences = context.getSharedPreferences(
					Constant.KEY_MONETWARE_PREFERENCES,
					Context.MODE_PRIVATE);

		return mSharedPreferences.getString(key, null);
	}
	public static String getStringByKey(Context context, String key,String defValue) {
		if (null == mSharedPreferences)
			mSharedPreferences = context.getSharedPreferences(
					Constant.KEY_MONETWARE_PREFERENCES,
					Context.MODE_PRIVATE);

		return mSharedPreferences.getString(key, defValue);
	}
	
	public static void putInt(Context context, String key, int value) {
		if (null == mEditor)
			mEditor = context.getSharedPreferences(
					Constant.KEY_MONETWARE_PREFERENCES,
					Context.MODE_PRIVATE).edit();

		mEditor.putInt(key, value);
		mEditor.commit();
	 }

	public static int getIntByKey(Context context, String key) {
		if (null == mSharedPreferences)
			mSharedPreferences = context.getSharedPreferences(
					Constant.KEY_MONETWARE_PREFERENCES,
					Context.MODE_PRIVATE);

		return mSharedPreferences.getInt(key, 0);
	}
	
	public static int getIntByKey(Context context, String key,int defValue) {
		if (null == mSharedPreferences)
			mSharedPreferences = context.getSharedPreferences(
					Constant.KEY_MONETWARE_PREFERENCES,
					Context.MODE_PRIVATE);

		return mSharedPreferences.getInt(key, defValue);
	}
	
	public static void putBoolean(Context context, String key, Boolean value) {
		if (null == mEditor)
			mEditor = context.getSharedPreferences(
					Constant.KEY_MONETWARE_PREFERENCES,
					Context.MODE_PRIVATE).edit();
 
		mEditor.putBoolean(key, value);
		mEditor.commit();
	 }

	public static boolean getBooleanByKey(Context context, String key) {
		if (null == mSharedPreferences)
			mSharedPreferences = context.getSharedPreferences(
					Constant.KEY_MONETWARE_PREFERENCES,
					Context.MODE_PRIVATE);

		return mSharedPreferences.getBoolean(key, false);
	}
	
	public static boolean getBooleanByKey(Context context, String key,boolean defValue) {
		if (null == mSharedPreferences)
			mSharedPreferences = context.getSharedPreferences(
					Constant.KEY_MONETWARE_PREFERENCES,
					Context.MODE_PRIVATE);

		return mSharedPreferences.getBoolean(key, defValue);
	}
}
