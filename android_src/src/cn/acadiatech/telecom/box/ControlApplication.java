package cn.acadiatech.telecom.box;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import cn.acadiatech.telecom.box.utils.CommonUtil;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * 全局类
 * 
 * @author QUYONG
 * 
 */
public class ControlApplication extends Application {
	
	private static final String FILECACHE = "telecom";

	static Context context;

	private static ControlApplication instance = new ControlApplication();

	public static ControlApplication getInstance() {
		if (instance == null) {
			return new ControlApplication();
		} else {
			return instance;
		}
	}

	@Override
	public void onCreate() {
		// 注册crashHandler
		instance = this;
		context = this;
		try {
			// FIXME 暂时不做处理----会拦截LogCat信息
			// CrashHandler crashHandler = CrashHandler.getInstance();
			// crashHandler.init(getApplicationContext());
		} catch (Throwable e) {
			System.out.println(e.toString());
		}
		super.onCreate();
	}

	public static Context getContext() {
		return context;
	}

	// 管理所有的 Activity
	private List<Activity> activityList = new LinkedList<Activity>();

	/**
	 * @Title: addActivity
	 * @Description:(添加Activity到容器中 )
	 * @param @param activity
	 * @return void 返回类型
	 * @throws
	 */
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	/**
	 * @Title: finishActivity
	 * @Description(关闭某个 activity)
	 * @param @param finishActivity
	 * @return void 返回类型
	 * @throws
	 */
	public void finishActivity(Activity finishActivity) {
		for (int i = 0; i < activityList.size(); i++) {
			if (activityList.get(i).getClass().getName()
					.equals(finishActivity.getClass().getName())) {
				activityList.remove(i);
				finishActivity.finish();
			}
		}
	}

	@Override
	public void onTerminate() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}

	/**
	 * 
	 * @Title: exit
	 * @Description: (遍历所有Activity并finish)
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	public void exit() {
		for (Activity act : activityList) {
			if (!act.isFinishing()) {
				act.finish();
			}
		}
		System.exit(0);
	}
	;
	
	/**
	 * 获得应用缓存路径
	 * @return
	 */
	public static String getFileCache(){
		
		return CommonUtil.getExternalPath()+File.separator+FILECACHE;
	}
	

}
