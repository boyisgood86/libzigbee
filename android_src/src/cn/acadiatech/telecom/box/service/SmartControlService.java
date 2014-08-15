package cn.acadiatech.telecom.box.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;
import cn.acadiatech.telecom.box.engine.ServerTCP;
import cn.acadiatech.telecom.box.engine.ServerUDP;
import cn.acadiatech.telecom.box.utils.CommonUtil;
import cn.acadiatech.telecom.box.utils.LogUtil;

/**
 * 后台控制服务
 * 
 * @author QUYONG
 * 
 */
public class SmartControlService extends Service {

	protected static final String TAG = "SmartControlService";

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();

			// 获得客户端传送过来的字符数据
			String operation = data.getString("operation");

			LogUtil.d(TAG, operation);
			Toast.makeText(SmartControlService.this, operation,
					Toast.LENGTH_SHORT).show();

		}
	};

	/*****************************************************************************************/

	@Override
	public IBinder onBind(Intent intent) {

		return null;

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Toast.makeText(this, "智能控制服务启动！", Toast.LENGTH_LONG).show();
		LogUtil.d(TAG, "智能控制服务端服务启动了！");

		// 开启UDP服务器
		new Thread(new Runnable() {

			@Override
			public void run() {

				// 获得服务器IP
				ServerUDP.serverRun();

			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// 获得客户端的消息并返回给ServerService
				new ServerTCP(handler).serverRun();

			}
		}).start();

		return START_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// 关闭串口
		//CommonUtil.closeTtyPort();

		// 打开并且设置串口信息
		CommonUtil.setTtyPort();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// 关闭串口
		CommonUtil.closeTtyPort();
	}

}
