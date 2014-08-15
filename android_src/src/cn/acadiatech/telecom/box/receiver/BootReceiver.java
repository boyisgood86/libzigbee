package cn.acadiatech.telecom.box.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.acadiatech.telecom.box.service.ReadDeviceService;
import cn.acadiatech.telecom.box.service.SmartControlService;
import cn.acadiatech.telecom.box.utils.CommonUtil;

/**
 * 开机启动广播
 * 
 * @author QUYONG
 * 
 */
public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		// 拦截到开机广播后
		
		//1. 开启后台控制service
		
		CommonUtil.startService(context, SmartControlService.class);

		//2. 开启后台更新灯状态方法

		//CommonUtil.startService(context, ReadDeviceService.class);
		

	}

}
