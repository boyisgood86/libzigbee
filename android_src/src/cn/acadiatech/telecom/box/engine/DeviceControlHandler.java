package cn.acadiatech.telecom.box.engine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;
import cn.acadiatech.telecom.box.ControlApplication;
import cn.acadiatech.telecom.box.beans.ControlBean;
import cn.acadiatech.telecom.box.beans.HeadBean;
import cn.acadiatech.telecom.box.beans.UserBean;
import cn.acadiatech.telecom.box.db.UserDao;
import cn.acadiatech.telecom.box.db.ex.Controller;
import cn.acadiatech.telecom.box.db.ex.DBOperation;
import cn.acadiatech.telecom.box.db.ex.DBProperty;
import cn.acadiatech.telecom.box.db.ex.action.DeviceDBAction;
import cn.acadiatech.telecom.box.db.ex.action.RoomDBAction;
import cn.acadiatech.telecom.box.db.ex.bean.Device;
import cn.acadiatech.telecom.box.utils.CommonUtil;
import cn.acadiatech.telecom.box.utils.Constant;
import cn.acadiatech.telecom.box.utils.JsonUtil;
import cn.acadiatech.telecom.box.utils.LogUtil;
import cn.acadiatech.telecom.box.utils.NetUtil;
import cn.acadiatech.telecom.box.utils.PreferencesUtil;

import com.acadiatech.json.JSONObject;

/**
 * 
 * @ClassName: DeviceControlHandler
 * @Description: 处理业务请求
 * @author quyong1986@gmail.com
 * @date 2014年7月25日 下午1:32:46
 * 
 */

@SuppressWarnings("unused")
public class DeviceControlHandler implements Runnable {

	private static final String TAG = "DeviceControlHandler";

	private Socket incoming;
	private Context mContext;

	private Handler handler;

	private PushbackInputStream inStream;
	private OutputStream outStream;

	private JsonUtil jsonUtilInstance;

	private HeadBean head;

	private int method;

	private String operation;

	private DataProvider provider;

	private DeviceReadThread readThread;// 读物设备状态线程

	public DeviceControlHandler(Socket incoming, Handler handler) {
		this.incoming = incoming;
		this.handler = handler;
		readThread = DeviceReadThread.getInstance();
		this.mContext = ControlApplication.getInstance();
		jsonUtilInstance = JsonUtil.getInstance();
		provider = DataProvider.getInstance();

	}

	@Override
	public void run() {

		// LogUtil.d(TAG, "Client(" + incoming.getRemoteSocketAddress()
		// + ") come in...");

		try {

			inStream = new PushbackInputStream(incoming.getInputStream());
			// InputStream inStream = incoming.getInputStream();
			outStream = incoming.getOutputStream();
			byte[] buf = new byte[1024 * 2];

			int len = inStream.read(buf);

			operation = new String(buf, 0, len);

			// LogUtil.d(TAG, "接收到的头文件为	" + operation);

			// 解析获得请求码
			if (null != operation && !"".equals(operation.trim())) {

				method = jsonUtilInstance.getMethod(operation);// 业务请求码

				switch (method) {
				case Constant.USERLOGIN:

					// 用户登录
					UserBean userBean = jsonUtilInstance.getUserBean(operation);// 登陆的用户bean

					// 判断用户是否存在
					boolean user = UserDao.getInstance(mContext).getUser(
							userBean.getRoom_name(), userBean.getPassword());
					if (user) {
						// 登录成功
						int status = Constant.SUCCESS;
						String message = "登陆成功";
						head = new HeadBean(status, message, method);

					} else {
						int status = Constant.FAIL;
						String message = "登陆失败";
						head = new HeadBean(status, message, method);
					}

					// 获得返回的数据
					String backJson = jsonUtilInstance.getBackJson(head);

					// 发送数据给手机
					outStream.write(backJson.getBytes("utf-8"));

					break;
				case Constant.DEVICECONTROL:
					// 组设备控制

					try {
						readThread.stopThread();
						// 获得需要控制的设备,Mac,类型,状态
						// List<ControlBean> list = JsonUtil.getInstance()
						// .getControlDeviceList(operation);
						String modeName = jsonUtilInstance
								.getModeName(operation);
						String roomName = jsonUtilInstance
								.getRoomName(operation);

						List<ControlBean> list = Controller.getInstance(
								mContext).controlMode(modeName, roomName);

						int flag = 0;
						int count = 0;
						// 遍历设备，调用接口方法控制组设备
						long beforeTimeMillis = System.currentTimeMillis();
						LogUtil.d(TAG, "			调用控制组设备方法前" + "   client: "
								+ incoming.getRemoteSocketAddress());
						// 遍历设备，调用接口方法控制设备
						for (ControlBean controlBean : list) {
							String deviceMac = controlBean.getMacAdd();
							int deviceType = controlBean.getDeviceType();
							int brightness = controlBean.getBrightness();

							LogUtil.d(
									TAG,
									"			收到的信息: " + controlBean.toString()
											+ "   client: "
											+ incoming.getRemoteSocketAddress());

							// int operator = provider.OperatorCmd(deviceMac,
							// deviceType, Constant.YM_WRITE, brightness);

							String recevData = provider.WriteDevice(deviceMac,
									deviceType, Constant.YM_WRITE, brightness);

							LogUtil.d(TAG, "recevData:     " + recevData);

							int controlStatus = CommonUtil.getControlStatus(
									deviceMac, recevData);

							LogUtil.d(
									TAG,
									"			调用控制设备方法：" + controlStatus
											+ "   client: "
											+ incoming.getRemoteSocketAddress());

							if (controlStatus == -1) {
								flag = -1;
								// 更新数据库设备状态

								boolean updateProgressByMac = DeviceDBAction
										.getInstance(mContext)
										.updateProgressByMac(deviceMac,
												brightness, roomName);

								LogUtil.d(TAG, "			更新数据库设备状态："
										+ updateProgressByMac + "   client: "
										+ incoming.getRemoteSocketAddress());
							} else {
								// 更新数据库设备状态

								boolean updateProgressByMac = DeviceDBAction
										.getInstance(mContext)
										.updateProgressByMac(deviceMac,
												controlStatus, roomName);

								LogUtil.d(TAG, "			更新数据库设备状态："
										+ updateProgressByMac + "   client: "
										+ incoming.getRemoteSocketAddress());

							}

							Thread.sleep(Constant.SLEEPTIME);

							count++;

						}

						long currentTimeMillis = System.currentTimeMillis();
						long time = currentTimeMillis - beforeTimeMillis;
						LogUtil.d(TAG,
								"			调用控制组设备方法后,共花费时间：" + time + "   client: "
										+ incoming.getRemoteSocketAddress());

						LogUtil.d(TAG, "主設備控制時調用JNI方法" + count + "次");

						count = 0;

						if (flag == -1) {
							// 发送操作结果给手机
							HeadBean head = new HeadBean(Constant.FAIL,
									"组设备控制失败", method);
							String backControlJson = jsonUtilInstance
									.getBackJson(head);

							outStream.write(backControlJson.getBytes("utf-8"));

						} else {
							// 发送操作结果给手机
							HeadBean head = new HeadBean(Constant.SUCCESS,
									"组设备控制成功", method);
							String backControlJson = jsonUtilInstance
									.getBackJson(head);

							outStream.write(backControlJson.getBytes("utf-8"));
						}

					} catch (Exception e) {

						e.printStackTrace();

						// 发送操作结果给手机
						HeadBean head = new HeadBean(Constant.FAIL, "组设备控制失败",
								method);
						String backControlJson = jsonUtilInstance
								.getBackJson(head);

						outStream.write(backControlJson.getBytes("utf-8"));

					}

					// readThread.sleep();
					readThread.startThread();

					break;
				case Constant.DEVICEREAD:
					// 读取设备状态

					try {
						long beforeTimeMillis = System.currentTimeMillis();
						LogUtil.d(TAG, "			开始读取设备状态：    " + "   client: "
								+ incoming.getRemoteSocketAddress());
						readThread.stopThread();
						// 获得需要控制的设备,Mac,类型,状态
						String deviceTypeidStr = jsonUtilInstance
								.getDeviceTypeId(operation);
						int deviceTypeid = Integer.parseInt(deviceTypeidStr);
						String roomName = jsonUtilInstance
								.getRoomName(operation);

						LogUtil.d(TAG, "开始从数据库读取设备状态");
						List<Device> list = Controller.getInstance(mContext)
								.controlMode(deviceTypeid, roomName);

						LogUtil.d(TAG, "从数据库读取设备状态后" + list.size());

						// 发送操作结果给手机
						HeadBean head = new HeadBean(Constant.SUCCESS,
								"设备状态读取成功", method);
						String backControlJson = jsonUtilInstance
								.getBackStatusJson(head, list);

						LogUtil.d(TAG, "backControlJson=" + backControlJson);

						outStream.write(backControlJson.getBytes("utf-8"));

						long currentTimeMillis = System.currentTimeMillis();
						long time = currentTimeMillis - beforeTimeMillis;
						LogUtil.d(TAG,
								"			读取设备状态后,共花费时间：" + time + "   client: "
										+ incoming.getRemoteSocketAddress());

					} catch (Exception e) {

						e.printStackTrace();

						// 发送操作结果给手机
						HeadBean head = new HeadBean(Constant.FAIL, "设备状态读取失败",
								method);
						String backControlJson = jsonUtilInstance
								.getBackJson(head);
						LogUtil.d(TAG, "Exception backControlJson="
								+ backControlJson);
						outStream.write(backControlJson.getBytes("utf-8"));

					}

					readThread.startThread();

					break;
				case Constant.SINGLEDEVICECONTROL:
					// 单个设备控制

					try {
						readThread.stopThread();
						// 获得需要控制的设备,Mac,类型,状态
						List<ControlBean> list = JsonUtil.getInstance()
								.getControlDeviceList(operation);

						int flag = 0;

						// 遍历设备，调用接口方法控制设备
						for (ControlBean controlBean : list) {
							String deviceMac = controlBean.getMacAdd();
							int deviceType = controlBean.getDeviceType();
							int brightness = controlBean.getBrightness();

							LogUtil.d(
									TAG,
									"			收到的信息: " + controlBean.toString()
											+ "   client: "
											+ incoming.getRemoteSocketAddress());

							long beforeTimeMillis = System.currentTimeMillis();

							LogUtil.d(TAG, "			调用控制单个设备方法前" + "   client: "
									+ incoming.getRemoteSocketAddress());

							// int operator = provider.OperatorCmd(deviceMac,
							// deviceType, Constant.YM_WRITE, brightness);

							String recevData = provider.WriteDevice(deviceMac,
									deviceType, Constant.YM_WRITE, brightness);
							long currentTimeMillis = System.currentTimeMillis();
							long time = currentTimeMillis - beforeTimeMillis;
							LogUtil.d(
									TAG,
									"			调用控制单个设备方法后,共花费时间：" + time
											+ "   client: "
											+ incoming.getRemoteSocketAddress());

							LogUtil.d(TAG, "recevData:     " + recevData);

							int controlStatus = CommonUtil.getControlStatus(
									deviceMac, recevData);

							LogUtil.d(
									TAG,
									"			调用控制设备方法：" + controlStatus
											+ "   client: "
											+ incoming.getRemoteSocketAddress());

							if (controlStatus == -1) {
								flag = -1;
								// 更新数据库设备状态

								boolean updateProgressByMac = DeviceDBAction
										.getInstance(mContext)
										.updateProgressByMac(deviceMac,
												brightness);

								LogUtil.d(TAG, "			更新数据库设备状态："
										+ updateProgressByMac + "   client: "
										+ incoming.getRemoteSocketAddress());
							} else {
								// 更新数据库设备状态

								boolean updateProgressByMac = DeviceDBAction
										.getInstance(mContext)
										.updateProgressByMac(deviceMac,
												controlStatus);

								LogUtil.d(TAG, "			更新数据库设备状态："
										+ updateProgressByMac + "   client: "
										+ incoming.getRemoteSocketAddress());

							}

							Thread.sleep(Constant.SLEEPTIME);

						}

						if (flag == -1) {
							// 发送操作结果给手机
							HeadBean head = new HeadBean(Constant.FAIL,
									"设备控制失败", method);
							String backControlJson = jsonUtilInstance
									.getBackJson(head);

							outStream.write(backControlJson.getBytes("utf-8"));

						} else {

							// 发送操作结果给手机
							HeadBean head = new HeadBean(Constant.SUCCESS,
									"设备控制成功", method);
							String backControlJson = jsonUtilInstance
									.getBackJson(head);

							outStream.write(backControlJson.getBytes("utf-8"));
						}

					} catch (Exception e) {

						e.printStackTrace();

						// 发送操作结果给手机
						HeadBean head = new HeadBean(Constant.FAIL, "设备控制失败",
								method);
						String backControlJson = jsonUtilInstance
								.getBackJson(head);

						outStream.write(backControlJson.getBytes("utf-8"));

					}

					// readThread.sleep();
					readThread.startThread();
					break;
				case Constant.DEVICENETWORK:
					// 设备组网

					try {
						readThread.stopThread();

						// 获得需要控制的设备,Mac,类型,状态
						String roomName = jsonUtilInstance
								.getRoomName(operation);

						PreferencesUtil.putString(mContext,
								Constant.ROOMNAMEKEY, roomName);

						List<ControlBean> list = Controller.getInstance(
								mContext).controlMode(roomName);

						int flag = 0;

						// 遍历设备，调用接口方法控制设备
						long beforeTimeMillis = System.currentTimeMillis();

						// 清空COO的所有白名单
						provider.OperatorCmd("00:00:00:00:00:00:00:00", 0,
								Constant.YM_RM_ED_ALL, 0);

						LogUtil.d(
								TAG,
								"			调用组网方法前" + "   client: "
										+ incoming.getRemoteSocketAddress());

						for (ControlBean controlBean : list) {
							String deviceMac = controlBean.getMacAdd();
							int deviceType = controlBean.getDeviceType();
							int brightness = controlBean.getBrightness();

							LogUtil.d(
									TAG,
									"			收到的信息: " + controlBean.toString()
											+ "   client: "
											+ incoming.getRemoteSocketAddress());

							int operator = provider.OperatorCmd(deviceMac,
									deviceType, Constant.YM_ADD_ED, brightness);

							LogUtil.d(TAG,
									"			调用组网设备方法：" + operator + "   client: "
											+ incoming.getRemoteSocketAddress());

							// Thread.sleep(Constant.SLEEPTIME);

							if (operator == -1) {
								flag = -1;
							}
						}

						long currentTimeMillis = System.currentTimeMillis();
						long time = currentTimeMillis - beforeTimeMillis;
						LogUtil.d(TAG,
								"			调用组网方法后,共花费时间：" + time + "   client: "
										+ incoming.getRemoteSocketAddress());

						if (flag == -1) {
							// 发送操作结果给手机
							HeadBean head = new HeadBean(Constant.FAIL,
									"设备组网失败", method);
							String backControlJson = jsonUtilInstance
									.getBackJson(head);

							outStream.write(backControlJson.getBytes("utf-8"));

						} else {
							// 发送操作结果给手机
							HeadBean head = new HeadBean(Constant.SUCCESS,
									"设备组网成功", method);
							String backControlJson = jsonUtilInstance
									.getBackJson(head);

							outStream.write(backControlJson.getBytes("utf-8"));
						}

					} catch (Exception e) {

						e.printStackTrace();

						// 发送操作结果给手机
						HeadBean head = new HeadBean(Constant.FAIL, "设备组网失败",
								method);
						String backControlJson = jsonUtilInstance
								.getBackJson(head);

						outStream.write(backControlJson.getBytes("utf-8"));

					}
					readThread.startThread();

					break;
				case Constant.DATABACKUP:
					long beforeTimeMillis = System.currentTimeMillis();
					// 数据备份
					readThread.stopThread();
					SocKetBean bean = JSONObject.parseObject(operation,
							SocKetBean.class);

					// 把文件放到应用缓存目录
					backup(bean);

					readThread.startThread();

					long currentTimeMillis = System.currentTimeMillis();
					long time = currentTimeMillis - beforeTimeMillis;
					LogUtil.d(TAG, "			 备份数据库到盒子后,共花费时间：" + time
							+ "   client: " + incoming.getRemoteSocketAddress());
					break;

				case Constant.DATARESTORE:
					// 数据还原
					String cpuUsed = CommonUtil.getCpuUsed();

					LogUtil.d(TAG, "			cpu使用率：" + cpuUsed);
					long beforeTimeMillis1 = System.currentTimeMillis();
					readThread.stopThread();
					// 拷贝文件到缓存目录
					restore();

					readThread.startThread();
					long currentTimeMillis1 = System.currentTimeMillis();
					long time1 = currentTimeMillis1 - beforeTimeMillis1;
					LogUtil.d(TAG, "			 还原数据库到手机后,共花费时间：" + time1
							+ "   client: " + incoming.getRemoteSocketAddress());
					break;

				default:
					break;
				}

			}

			outStream.close();
			inStream.close();
			incoming.close();

		} catch (Exception e) {
			e.printStackTrace();
			int status = Constant.FAIL;
			String message = "连接错误";
			head = new HeadBean(status, message, method);
			// 获得返回的数据
			String backJson = jsonUtilInstance.getBackJson(head);

			try {
				outStream.write(backJson.getBytes("utf-8"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}

	}

	/**
	 * 备份配置文件文件
	 * 
	 * @param bean
	 * @param uploadPath
	 */
	private void uploadFile(SocKetBean bean, String uploadPath) {

		LogUtil.d(TAG,
				"			accepted connenction from " + incoming.getInetAddress()
						+ " @ " + incoming.getPort());
		try {

			if (operation != null) {

				// 下面从协议数据中读取各种参数值
				String filelength = bean.getSize() + "";
				// String filename = bean.getFilename();
				String filename = DBProperty.DB_NAME;
				String sourceid = bean.getSourceid();
				Long id = System.currentTimeMillis();

				// 记住文件名
				// PreferencesUtil.putString(mContext, Constant.DB_NAME,
				// Constant.DB_NAME);

				if (null != sourceid && !"".equals(sourceid)) {
					id = Long.valueOf(sourceid);
				}

				File file = null;
				int position = 0;
				File dir = new File(uploadPath + File.separator);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				file = new File(dir, filename);

				String response = "sourceid=" + id + ";position=" + position
						+ "\r\n";
				// 服务器收到客户端的请求信息后，给客户端返回响应信息：sourceid=1274773833264;position=0
				// sourceid由服务生成，唯一标识上传的文件，position指示客户端从文件的什么位置开始上传
				outStream.write(response.getBytes("utf-8"));

				RandomAccessFile randomAccessFile = new RandomAccessFile(file,
						"rwd");
				if (position == 0) {
					randomAccessFile.setLength(Integer.valueOf(filelength));// 设置文件长度
				}
				randomAccessFile.seek(position);// 移动文件指定的位置开始写入数据
				byte[] buffer = new byte[1024];
				int len = -1;
				int length = position;
				while ((len = inStream.read(buffer)) != -1) {// 从输入流中读取数据写入到文件中
					randomAccessFile.write(buffer, 0, len);
					length += len;

				}

				if (length == randomAccessFile.length()) {
					// 上传完成

					HeadBean head = new HeadBean(Constant.SUCCESS, "备份配置文件成功",
							method);
					String backJson = JSONObject.toJSONString(head) + "\r\n";

					outStream.write(backJson.getBytes("utf-8"));

				}

				randomAccessFile.close();
				inStream.close();
				outStream.close();
				incoming.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Socket下载
	 * 
	 * @param bean
	 */
	private void restore() {
		LogUtil.d(TAG,
				"			accepted connenction from " + incoming.getInetAddress()
						+ " @ " + incoming.getPort());

		try {
			if (null != operation) {

				// 下面从协议数据中读取各种参数值
				// String dbname = PreferencesUtil.getStringByKey(mContext,
				// Constant.DB_NAME, null);
				String dbname = DBProperty.DB_NAME;
				if (null == dbname) {
					HeadBean head = new HeadBean(Constant.FAIL, "还原的文件不存在",
							method);
					String backJson = jsonUtilInstance.getBackJson(head);
					outStream.write(backJson.getBytes("utf-8"));
				}

				// 文件名
				String filename = ControlApplication.getFileCache()
						+ File.separator + dbname;

				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {

					File downloadFile = new File(filename);
					// 判断文件是否存在
					if (downloadFile.exists()) {
						// 如果存在
						downloadFile(downloadFile);
					} else {
						// 不存在
						Toast.makeText(mContext, "下载的文件不存在", Toast.LENGTH_LONG)
								.show();
					}
				} else {
					Toast.makeText(mContext, "SD卡不存在", Toast.LENGTH_LONG)
							.show();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * Socket上传
	 * 
	 * @param bean
	 */
	public void backup(SocKetBean bean) {

		String filePath = ControlApplication.getFileCache();

		uploadFile(bean, filePath);

		// 重新使用数据库
		DBOperation.clearInstance();
		DeviceDBAction.clearInstance();

	}

	/**
	 * 下载文件
	 * 
	 * @param downloadFile
	 */
	private void downloadFile(final File downloadFile) {

		try {
			String souceid = "00";
			String socketHeadStr = jsonUtilInstance.getSocketHead(downloadFile,
					souceid, method);

			outStream.write(socketHeadStr.getBytes("utf-8"));

			String response = StreamTool.readLine(inStream);
			String[] items = response.split(";");
			String responseid = items[0].substring(items[0].indexOf("=") + 1);
			String position = items[1].substring(items[1].indexOf("=") + 1);

			RandomAccessFile randomAccessFile = new RandomAccessFile(
					downloadFile, "r");
			randomAccessFile.seek(Integer.valueOf(position));
			byte[] buffer = new byte[1024 * 2];
			int len = -1;
			int length = Integer.valueOf(position);
			while ((len = randomAccessFile.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
				length += len;

			}
			randomAccessFile.close();
			outStream.close();
			inStream.close();
			incoming.close();
			if (length == downloadFile.length()) {

				HeadBean head = new HeadBean(Constant.SUCCESS, "还原配置文件成功",
						method);
				String backJson = JSONObject.toJSONString(head) + "\r\n";

				outStream.write(backJson.getBytes("utf-8"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}