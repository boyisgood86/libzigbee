package cn.acadiatech.telecom.box.service;

import cn.acadiatech.telecom.box.engine.DeviceReadThread;
import cn.acadiatech.telecom.box.utils.LogUtil;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class ReadDeviceService extends Service {

	private static final String TAG = "ReadDeviceService";
	private DeviceReadThread readThread;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Toast.makeText(this, "读取设备状态服务启动！", Toast.LENGTH_LONG).show();
		LogUtil.d(TAG, "读取设备状态服务启动了！");


		// 开启线程读取设备状态
		
		if(!readThread.isAlive()){
			//如果读线程被杀死后需要重新启动的
			readThread.start();
			
		}

		return START_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		readThread = DeviceReadThread.getInstance();
	}

}
