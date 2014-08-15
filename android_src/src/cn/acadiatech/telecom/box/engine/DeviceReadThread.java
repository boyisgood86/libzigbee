package cn.acadiatech.telecom.box.engine;

import java.util.List;

import android.content.Context;
import cn.acadiatech.telecom.box.ControlApplication;
import cn.acadiatech.telecom.box.beans.ControlBean;
import cn.acadiatech.telecom.box.db.ex.Controller;
import cn.acadiatech.telecom.box.db.ex.action.DeviceDBAction;
import cn.acadiatech.telecom.box.utils.CommonUtil;
import cn.acadiatech.telecom.box.utils.Constant;
import cn.acadiatech.telecom.box.utils.LogUtil;
import cn.acadiatech.telecom.box.utils.PreferencesUtil;

public class DeviceReadThread extends Thread {

	private static final String TAG = "DeviceReadThread";
	private DataProvider provider;
	private Context mContext;
	private boolean threadflag = true;
	private static final String FILENAME = "deviceReadThread.txt";

	/**
	 * 暂停线程
	 */
	public void stopThread() {
		threadflag = false;
	}

	/**
	 * 开启线程
	 */
	public void startThread() {

		threadflag = true;
	}

	public void sleep() {
		try {
			Thread.sleep(15 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private DeviceReadThread() {
		provider = DataProvider.getInstance();
		mContext = ControlApplication.getInstance();

	};

	private static final class DeviceReadThreadHolder {

		private static final DeviceReadThread instance = new DeviceReadThread();

	}

	public static DeviceReadThread getInstance() {

		return DeviceReadThreadHolder.instance;

	}

	@Override
	public void run() {

		// while循环读取设备状态

		

		while (true) {

			try {
				
				String roomName = PreferencesUtil.getStringByKey(mContext,
						Constant.ROOMNAMEKEY, "");

				List<ControlBean> list = Controller.getInstance(mContext).controlModeRead(
						roomName);

				if(null == list && list.size() == 0){
					continue;
				}else if (threadflag) {

					
//					  // 1.调用JNI方法，返回当前某个设备的状态
//					long beforeTimeMillis =System.currentTimeMillis(); String deviceStatus =
//					 provider.ListDevice(); long currentTimeMillis =
//					  System.currentTimeMillis(); long time = currentTimeMillis - beforeTimeMillis;
//					  
//					  if (!deviceStatus.equalsIgnoreCase("null")) { 
//						  //读取的状态不为空，解析数据插入到数据库
//						  // LogUtil.d(TAG, "读取设备状态返回字符串：" + deviceStatus);
//					  
//					  // 解析出字符串，各个字段
//						  String[] deviceStr =deviceStatus.split("_");
//					  
//					  // deviceMac_deviceType_cmd_val
//					  
//					  String deviceMac = deviceStr[0].trim(); int deviceType =
//					  Integer.parseInt(deviceStr[1]); int cmd =
//					  Integer.parseInt(deviceStr[2]); int value =
//					  Integer.parseInt(deviceStr[3]);
//					  
//					  // 获取数据库表中该设备的值
//					  int brightness =
//					  DeviceDBAction.getInstance(mContext)
//					  .getBrightnessByMac(deviceMac); String deviceName =
//					  DeviceDBAction .getInstance(mContext).getDeviceNameByMac(
//					  deviceMac,"1111");
//					  
//					  LogUtil.d(TAG, FILENAME, "  调用ListDevice方法后,共花费时间：" +
//					  time  + "   " + deviceName + "	" +
//					 deviceStatus);
//					 
//					  // 通过mac和字符串更新数据库
//					  if (cmd == Constant.YM_RETURN) { 
//						  if(brightness != value) { boolean updateProgressByMac =
//					  DeviceDBAction .getInstance(mContext)
//					  .updateProgressByMac(deviceMac, value);
//					  
//					  LogUtil.d(TAG, FILENAME, "更新设备状态：" + deviceMac + "___" +
//					  updateProgressByMac + "   " );
//					  
//					  }
//					  
//					  } else if (cmd == Constant.YM_ONLINE) {
//					  
//					  if (brightness != value) {
//					  
//					  boolean updateProgressByMac = DeviceDBAction
//					 .getInstance(mContext) .updateProgressByMac(deviceMac,
//					  value);
//					  
//					  LogUtil.d(TAG, FILENAME, "更新设备状态：" + deviceMac + "___" +
//					  updateProgressByMac + "   " ); }
//					  
//					  } }
					 

					for (ControlBean controlBean : list) {
						
						if(threadflag){
							
							// 获取数据库表中该设备的值
							String deviceMac = controlBean.getMacAdd();
							int deviceType = controlBean.getDeviceType();
							int brightness = controlBean.getBrightness();
							String deviceName = DeviceDBAction .getInstance(mContext).getDeviceNameByMac(deviceMac,roomName);

							String recevData = provider.ListDevice_new(deviceMac,deviceType, Constant.YM_READ, brightness);


							int controlStatus = CommonUtil.getControlStatus(
									deviceMac, recevData);
							
							LogUtil.d(TAG, "recevData:     " + recevData+"     "+deviceName+"controlStatus:          "+controlStatus);
							  // 通过mac和字符串更新数据库					  
							  
							  if (controlStatus != -1 && brightness != controlStatus) {
						
									// 更新数据库设备状态
									DeviceDBAction.getInstance(mContext).updateProgressByMac(deviceMac,controlStatus, roomName);
								
							  }

							Thread.sleep(Constant.SLEEPTIME);
							
						}
						
						

					}
				}

				Thread.sleep(5 * 1000);


			} catch (Exception e) {

				e.printStackTrace();
				LogUtil.d(TAG, FILENAME, Thread.currentThread().getName()
						+ "==>" + "出错了！    " + e.toString());

			}

		}

	}

}
