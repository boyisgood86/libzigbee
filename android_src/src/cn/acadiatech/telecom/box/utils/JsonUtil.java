package cn.acadiatech.telecom.box.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.acadiatech.telecom.box.beans.ControlBean;
import cn.acadiatech.telecom.box.beans.HeadBean;
import cn.acadiatech.telecom.box.beans.MessageArray;
import cn.acadiatech.telecom.box.beans.MessageObject;
import cn.acadiatech.telecom.box.beans.UserBean;
import cn.acadiatech.telecom.box.db.ex.bean.Device;
import cn.acadiatech.telecom.box.engine.SocKetBean;

/**
 * 解析封装json方法
 * 
 * @author QUYONG
 * 
 */
public class JsonUtil {

	/**
	 * 解析json获得业务请求码
	 * 
	 * @param jsonStr
	 * @return
	 */

	private static final JsonUtil instance = new JsonUtil();
	private static final String TAG = null;

	public static JsonUtil getInstance() {
		if (instance != null) {
			return new JsonUtil();
		}
		return instance;
	}

	public int getMethod(String jsonStr) {

		try {

			JSONObject mJsonObject = new JSONObject(jsonStr);
			LogUtil.d(TAG, mJsonObject.toString());
			JSONObject headObject = mJsonObject.getJSONObject("head");
			int method = headObject.getInt("method");

			return method;

		} catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 获得登录请求Bean
	 * 
	 * @param operation
	 */
	public UserBean getUserBean(String operation) {
		UserBean mUserBean;
		try {
			if (!operation.contains("body")) {
				return null;
			} else {

				mUserBean = new UserBean();

				JSONObject mJsonObject = new JSONObject(operation)
						.getJSONObject("body");
				String name = mJsonObject.getString("name");
				String password = mJsonObject.getString("password");
				mUserBean.setRoom_name(name);
				mUserBean.setPassword(password);

				return mUserBean;
			}

		} catch (JSONException e) {

			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获得需要控制的设备
	 */
	public List<ControlBean> getControlDeviceList(String operation) {

		if (!operation.contains("body")) {
			return null;
		}

		if (null == operation || "".equals(operation.trim())) {
			return null;
		}

		// 解析多个数据的Json
		List<ControlBean> list = new ArrayList<ControlBean>();

		try {
			JSONObject mJsonObject = new JSONObject(operation);

			JSONArray jsonArray2 = mJsonObject.getJSONArray("body");

			JSONArray jsonArray = new JSONArray(jsonArray2.toString());
			// 数据直接为一个数组形式，所以可以直接
			// 用android提供的框架JSONArray读取JSON数据，转换成Array

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i); // 每条记录又由几个Object对象组成
				int brightness = item.getInt("brightness"); // 获取对象对应的值
				int deviceType = item.getInt("deviceType");
				String macAdd = item.getString("macAdd");

				ControlBean controlBean = new ControlBean(macAdd, deviceType,
						brightness);

				list.add(controlBean);

			}

			return list;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getBackJson(HeadBean head) {

		MessageObject<UserBean> message = new MessageObject<UserBean>();
		message.setHead(head);
		message.setBody(null);
		return com.acadiatech.json.JSONObject.toJSONString(message);

	}

	/**
	 * 获得Socket下载的头文件
	 * 
	 * @return
	 */
	public String getSocketHead(File uploadFile, String souceid, int method) {
		SocKetBean socKetBean = new SocKetBean();
		socKetBean.setFilename(uploadFile.getName());
		socKetBean.setSize(uploadFile.length());
		socKetBean.setSourceid((souceid == null ? "" : souceid));

		HeadBean headBean = new HeadBean(1, "Socket下载", method);
		socKetBean.setHead(headBean);

		String jsonString = com.acadiatech.json.JSONObject
				.toJSONString(socKetBean);
		String head = jsonString + "\r\n";
		return head;
	}

	public String getModeName(String operation) {
		try {
			if (!operation.contains("body")) {
				return null;
			} else {

				JSONObject mJsonObject = new JSONObject(operation)
						.getJSONObject("body");
				String modeName = mJsonObject.getString("modeName");

				return modeName;
			}

		} catch (JSONException e) {

			e.printStackTrace();
			return null;
		}

	}

	public String getDeviceTypeId(String operation) {
		try {
			if (!operation.contains("body")) {
				return null;
			} else {

				JSONObject mJsonObject = new JSONObject(operation)
						.getJSONObject("body");
				String deviceTypeid = mJsonObject.getString("deviceTypeid");

				return deviceTypeid;
			}

		} catch (JSONException e) {

			e.printStackTrace();
			return null;
		}

	}

	public String getRoomName(String operation) {
		try {
			if (!operation.contains("body")) {
				return null;
			} else {

				JSONObject mJsonObject = new JSONObject(operation)
						.getJSONObject("body");
				String roomName = mJsonObject.getString("roomName");

				return roomName;
			}

		} catch (JSONException e) {

			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获取设备读取状态
	 * 
	 * @param head
	 * @param listStatus
	 * @return
	 */
	public String getBackStatusJson(HeadBean head, List<Device> listStatus) {
		MessageArray<Device> messageArray = new MessageArray<Device>();
		messageArray.setHead(head);
		messageArray.setBody(listStatus);

		String jsonString = com.acadiatech.json.JSONObject
				.toJSONString(messageArray);

		return jsonString;
	}

}
