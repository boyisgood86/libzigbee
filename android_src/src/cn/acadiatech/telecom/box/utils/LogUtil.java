package cn.acadiatech.telecom.box.utils;

import android.content.Context;
import android.util.Log;
import cn.acadiatech.telecom.box.ControlApplication;

/**
 * 日志工具类
 * 
 * @author QUYONG
 * 
 */
public final class LogUtil {

	/** 日志级别 显示级别参考 android.util.Log的级别 配置0全部显示，配置大于7全不显示 */
	public static final int LEVLE = 7;

	// Extra
	private static Context context = ControlApplication.getInstance();

	public static void v(String tag, String msg) {
		if (LEVLE <= Log.VERBOSE)
			Log.v(tag, msg);
	}

	public static void v(String tag, String msg, Throwable tr) {
		if (LEVLE <= Log.VERBOSE)
			Log.v(tag, msg, tr);
	}

	public static void d(String tag, String msg) {
		if (LEVLE <= Log.DEBUG) {
			Log.d(tag, msg);

			// Extra
			//FileUtil.save(context, "logcat_test.txt", msg);
		}

	}

	public static void d(String tag, String filename, String msg) {
		if (LEVLE <= Log.DEBUG) {
			Log.d(tag, msg);

			// Extra
			//FileUtil.save(context, filename, msg);
		}

	}

	public static void d(String tag, String msg, Throwable tr) {
		if (LEVLE <= Log.DEBUG)
			Log.d(tag, msg, tr);
	}

	public static void i(String tag, String msg) {
		if (LEVLE <= Log.INFO)
			LogUtil.d(tag, msg);
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (LEVLE <= Log.INFO)
			LogUtil.d(tag, msg, tr);
	}

	public static void w(String tag, String msg) {
		if (LEVLE <= Log.WARN)
			Log.w(tag, msg);
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (LEVLE <= Log.WARN)
			Log.w(tag, msg, tr);
	}

	public static void w(String tag, Throwable tr) {
		if (LEVLE <= Log.WARN)
			Log.w(tag, tr.getMessage(), tr);
	}

	public static void e(String tag, String msg) {
		if (LEVLE <= Log.ERROR)
			Log.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (LEVLE <= Log.ERROR)
			Log.e(tag, msg, tr);
	}

	public static void e(String tag, Throwable tr) {
		if (LEVLE <= Log.ERROR)
			Log.e(tag, tr.getMessage(), tr);
	}

	public static void wtf(String tag, String msg) {
		if (LEVLE <= Log.ASSERT)
			Log.wtf(tag, msg);
	}

	public static void wtf(String tag, Throwable tr) {
		if (LEVLE <= Log.ASSERT)
			Log.wtf(tag, tr);
	}

	public static void wtf(String tag, String msg, Throwable tr) {
		if (LEVLE <= Log.ASSERT)
			LogUtil.wtf(tag, msg, tr);
	}

}
