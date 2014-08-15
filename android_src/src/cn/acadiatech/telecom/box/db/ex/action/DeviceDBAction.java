package cn.acadiatech.telecom.box.db.ex.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import cn.acadiatech.telecom.box.db.ex.DBOperation;
import cn.acadiatech.telecom.box.db.ex.DBProperty;
import cn.acadiatech.telecom.box.db.ex.bean.Device;
import cn.acadiatech.telecom.box.db.ex.bean.DeviceType;

public class DeviceDBAction extends DBOperation {

	public static DeviceDBAction instance = null;
	Context context;

	public DeviceDBAction(Context context) {
		super(context);
		this.context = context;
	}

	public static synchronized DeviceDBAction getInstance(Context context) {
		if (instance == null) {
			instance = new DeviceDBAction(context);
		}
		return instance;
	}
	
	public static void clearInstance(){
		
		instance = null;
		
	}

	
	public synchronized boolean addDevice(String device_name, int device_group_id,
			DeviceType deviceType) {
		ContentValues values = new ContentValues();
		values.put("device_name", device_name);
		values.put("device_group_id", device_group_id);
		values.put("device_type_id", deviceType.getDevice_type_id());
		return this.insertTableData(DBProperty.TABLE_AD_DEVICE, values);
	}

	public synchronized boolean addDevice(String device_name, int device_group_id,
			DeviceType deviceType, String room_name) {
		ContentValues values = new ContentValues();
		values.put("device_name", device_name);
		values.put("device_group_id", device_group_id);
		values.put("device_type_id", deviceType.getDevice_type_id());
		values.put("room_name", room_name);
		return this.insertTableData(DBProperty.TABLE_AD_DEVICE, values);
	}

	public synchronized boolean deleteDevice() {
		return this.deleteTableData(DBProperty.TABLE_AD_DEVICE, null,
				new String[] {});
	}

	public synchronized boolean deleteDeviceById(int device_id) {
		return this.deleteTableData(DBProperty.TABLE_AD_DEVICE, "device_id="
				+ device_id, new String[] {});
	}

	public synchronized boolean deleteDeviceByDeviceGroupId(int deviceGroup_id) {
		return this.deleteTableData(DBProperty.TABLE_AD_DEVICE,
				"device_group_id=" + deviceGroup_id, new String[] {});
	}

	public synchronized boolean updateMacById(int id, String device_mac) {
		ContentValues values = new ContentValues();
		values.put("device_mac", device_mac);
		return this.updateTableData(DBProperty.TABLE_AD_DEVICE, "device_id="
				+ id, new String[] {}, values);
	}

	public synchronized boolean updateProgressById(int id, int device_progress) {

		ContentValues values = new ContentValues();
		values.put("device_progress", device_progress);
		String table = DBProperty.TABLE_AD_DEVICE;
		String whereClause = "device_id= ?";
		String[] whereArgs = new String[] { id + "" };
		return this.updateTableData(table, whereClause, whereArgs, values);
	}

	public synchronized List<Device> getDeviceAll() {
		List<Device> list = new ArrayList<Device>();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Device device = new Device();
			device.setDevice_name(hashMap.get("device_name") + "");
			device.setDevice_mac(hashMap.get("device_mac") + "");
			device.setDevice_progress(Integer.parseInt(hashMap
					.get("device_progress") + ""));
			// device.setDevice_status(Integer.parseInt(hashMap.get("device_mac")+""));
			int device_group_id = Integer.parseInt(hashMap
					.get("device_group_id") + "");
			device.setDeviceGroup(DeviceGroupDBAction.getInstance(context)
					.getDeviceGroupById(device_group_id));
			int device_type_id = Integer.parseInt(hashMap.get("device_type_id")
					+ "");
			device.setDeviceType(DeviceTypeDBAction.getInstance(context)
					.getById(device_type_id));
			String room_name = hashMap.get("room_name") + "";
			device.setRoom(RoomDBAction.getInstance(context).getRoomByName(
					room_name));
			list.add(device);
		}
		return list;
	}

	public synchronized Device getDeviceById(int id) {
		Device device = new Device();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE
				+ " where device_id=" + id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			device.setDevice_id(Integer.parseInt(hashMap.get("device_id") + ""));
			device.setDevice_name(hashMap.get("device_name") + "");
			device.setDevice_mac(hashMap.get("device_mac") + "");
			device.setDevice_progress(Integer.parseInt(hashMap
					.get("device_progress") + ""));
			int device_group_id = Integer.parseInt(hashMap
					.get("device_group_id") + "");
			device.setDeviceGroup(DeviceGroupDBAction.getInstance(context)
					.getDeviceGroupById(device_group_id));
			int device_type_id = Integer.parseInt(hashMap.get("device_type_id")
					+ "");
			device.setDeviceType(DeviceTypeDBAction.getInstance(context)
					.getById(device_type_id));
		}
		return device;
	}

	public synchronized List<Device> getDeviceByDeviceGroupId(int id) {
		List<Device> list = new ArrayList<Device>();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE
				+ " where device_group_id=" + id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Device device = new Device();
			device.setDevice_id(Integer.parseInt(hashMap.get("device_id") + ""));
			device.setDevice_name(hashMap.get("device_name") + "");
			device.setDevice_mac(hashMap.get("device_mac") + "");
			device.setDevice_progress(Integer.parseInt(hashMap
					.get("device_progress") + ""));
			int device_group_id = Integer.parseInt(hashMap
					.get("device_group_id") + "");
			device.setDeviceGroup(DeviceGroupDBAction.getInstance(context)
					.getDeviceGroupById(device_group_id));
			int device_type_id = Integer.parseInt(hashMap.get("device_type_id")
					+ "");
			device.setDeviceType(DeviceTypeDBAction.getInstance(context)
					.getById(device_type_id));
			list.add(device);
		}
		return list;
	}

	/*
	 * 鏍规嵁鎴块棿瀛楁涓虹┖鐨勬ā鏉挎暟鎹� 鍜� 璁惧缁勬煡璇�
	 */
	public synchronized List<Device> getDeviceByDeviceGroupIdAndRoomNull(int id) {
		List<Device> list = new ArrayList<Device>();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE
				+ " where device_group_id=" + id + " and room_name is null";
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Device device = new Device();
			device.setDevice_id(Integer.parseInt(hashMap.get("device_id") + ""));
			device.setDevice_name(hashMap.get("device_name") + "");
			device.setDevice_mac(hashMap.get("device_mac") + "");
			device.setDevice_progress(Integer.parseInt(hashMap
					.get("device_progress") + ""));
			int device_group_id = Integer.parseInt(hashMap
					.get("device_group_id") + "");
			device.setDeviceGroup(DeviceGroupDBAction.getInstance(context)
					.getDeviceGroupById(device_group_id));
			int device_type_id = Integer.parseInt(hashMap.get("device_type_id")
					+ "");
			device.setDeviceType(DeviceTypeDBAction.getInstance(context)
					.getById(device_type_id));
			list.add(device);
		}
		return list;
	}

	/*
	 * 鏍规嵁鎴块棿瀛楁涓虹┖鐨勬ā鏉挎暟鎹� 鍜� 璁惧缁勬煡璇�
	 */
	public synchronized List<Device> getDeviceByDeviceGroupIdAndRoomName(int id,
			String roomName) {
		List<Device> list = new ArrayList<Device>();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE
				+ " where device_group_id=" + id + " and room_name= '"
				+ roomName+"'";
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Device device = new Device();
			device.setDevice_id(Integer.parseInt(hashMap.get("device_id") + ""));
			device.setDevice_name(hashMap.get("device_name") + "");
			device.setDevice_mac(hashMap.get("device_mac") + "");
			device.setDevice_progress(Integer.parseInt(hashMap
					.get("device_progress") + ""));
			int device_group_id = Integer.parseInt(hashMap
					.get("device_group_id") + "");
			device.setDeviceGroup(DeviceGroupDBAction.getInstance(context)
					.getDeviceGroupById(device_group_id));
			int device_type_id = Integer.parseInt(hashMap.get("device_type_id")
					+ "");
			device.setDeviceType(DeviceTypeDBAction.getInstance(context)
					.getById(device_type_id));
			list.add(device);
		}
		return list;
	}

	/*
	 * 根据房间字段和 设备类型
	 */
	public synchronized List<Device> getDeviceByTypeAndRoomName(int type, String roomName) {
		List<Device> list = new ArrayList<Device>();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE
				+ " where device_type_id=" + type + " and room_name= '"
				+ roomName+"'";
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Device device = new Device();
			device.setDevice_id(Integer.parseInt(hashMap.get("device_id") + ""));
			device.setDevice_name(hashMap.get("device_name") + "");
			device.setDevice_mac(hashMap.get("device_mac") + "");
			device.setDevice_progress(Integer.parseInt(hashMap
					.get("device_progress") + ""));
			int device_group_id = Integer.parseInt(hashMap
					.get("device_group_id") + "");
			device.setDeviceGroup(DeviceGroupDBAction.getInstance(context)
					.getDeviceGroupById(device_group_id));
			int device_type_id = Integer.parseInt(hashMap.get("device_type_id")
					+ "");
			device.setDeviceType(DeviceTypeDBAction.getInstance(context)
					.getById(device_type_id));
			list.add(device);
		}
		return list;
	}

	public synchronized int getCountByDeviceGroupId(int id, String room_name) {
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE
				+ " where room_name=" + room_name + " and device_group_id="
				+ id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		return listHashMaps.size();
	}

	/*
	 * 宸插綍鍏ユ暟
	 */
	public synchronized int getEntryCountByDeviceGroupId(int id, String room_name) {
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE
				+ " where room_name=" + room_name + " and device_group_id="
				+ id + " and device_mac is not null";
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		return listHashMaps.size();
	}

	public synchronized Device getDeviceByName(String name) {
		Device device = new Device();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE
				+ " where device_name='" + name + "' and room_name is not null";
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			device.setDevice_id(Integer.parseInt(hashMap.get("device_id") + ""));
			device.setDevice_name(hashMap.get("device_name") + "");
			device.setDevice_mac(hashMap.get("device_mac") + "");
			device.setDevice_progress(Integer.parseInt(hashMap
					.get("device_progress") + ""));
			int device_group_id = Integer.parseInt(hashMap
					.get("device_group_id") + "");
			device.setDeviceGroup(DeviceGroupDBAction.getInstance(context)
					.getDeviceGroupById(device_group_id));
			int device_type_id = Integer.parseInt(hashMap.get("device_type_id")
					+ "");
			device.setDeviceType(DeviceTypeDBAction.getInstance(context)
					.getById(device_type_id));
		}
		return device;
	}

	public synchronized Device getDeviceByName(String name, String roomName) {
		Device device = new Device();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE
				+ " where device_name='" + name + "' and room_name= '" + roomName+"'";
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			device.setDevice_id(Integer.parseInt(hashMap.get("device_id") + ""));
			device.setDevice_name(hashMap.get("device_name") + "");
			device.setDevice_mac(hashMap.get("device_mac") + "");
			device.setDevice_progress(Integer.parseInt(hashMap
					.get("device_progress") + ""));
			int device_group_id = Integer.parseInt(hashMap
					.get("device_group_id") + "");
			device.setDeviceGroup(DeviceGroupDBAction.getInstance(context)
					.getDeviceGroupById(device_group_id));
			int device_type_id = Integer.parseInt(hashMap.get("device_type_id")
					+ "");
			device.setDeviceType(DeviceTypeDBAction.getInstance(context)
					.getById(device_type_id));
		}
		return device;
	}

	/**
	 * 返回房间所有设备信息
	 * 
	 * @param roomName
	 * @return
	 */
	public synchronized List<Device> getDeviceByRoomName(String roomName) {
		List<Device> devices = new ArrayList<Device>();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE
				+ " where room_name= '" + roomName+"'";

		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Device device = new Device();
			device.setDevice_id(Integer.parseInt(hashMap.get("device_id") + ""));
			device.setDevice_name(hashMap.get("device_name") + "");
			device.setDevice_mac(hashMap.get("device_mac") + "");
			device.setDevice_progress(Integer.parseInt(hashMap
					.get("device_progress") + ""));
			int device_group_id = Integer.parseInt(hashMap
					.get("device_group_id") + "");
			device.setDeviceGroup(DeviceGroupDBAction.getInstance(context)
					.getDeviceGroupById(device_group_id));
			int device_type_id = Integer.parseInt(hashMap.get("device_type_id")
					+ "");
			device.setDeviceType(DeviceTypeDBAction.getInstance(context)
					.getById(device_type_id));
			devices.add(device);
		}
		return devices;

	}

	/**
	 * 返回房间所有设备信息
	 * 
	 * @param roomName
	 * @return
	 */
	public synchronized List<Device> getDevice() {
		List<Device> devices = new ArrayList<Device>();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE;

		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Device device = new Device();
			device.setDevice_id(Integer.parseInt(hashMap.get("device_id") + ""));
			device.setDevice_name(hashMap.get("device_name") + "");
			device.setDevice_mac(hashMap.get("device_mac") + "");
			device.setDevice_progress(Integer.parseInt(hashMap
					.get("device_progress") + ""));
			int device_group_id = Integer.parseInt(hashMap
					.get("device_group_id") + "");
			device.setDeviceGroup(DeviceGroupDBAction.getInstance(context)
					.getDeviceGroupById(device_group_id));
			int device_type_id = Integer.parseInt(hashMap.get("device_type_id")
					+ "");
			device.setDeviceType(DeviceTypeDBAction.getInstance(context)
					.getById(device_type_id));
			devices.add(device);
		}
		return devices;

	}

	/**
	 * 更新数据库设备状态记录
	 * 
	 * @param deviceMac
	 *            设备Mac
	 * @param value
	 * @param brightness
	 *            状态值
	 */
	public synchronized boolean updateProgressByMac(String deviceMac, int type,
			int device_progress) {
		ContentValues values = new ContentValues();
		values.put("device_progress", device_progress);
		String table = DBProperty.TABLE_AD_DEVICE;
		String whereClause = "device_mac = ? and device_type_id =?";
		String[] whereArgs = new String[] { deviceMac, type + "" };
		return this.updateTableData(table, whereClause, whereArgs, values);

	}

	/**
	 * 更新数据库设备状态记录
	 * 
	 * @param deviceMac
	 *            设备Mac
	 * @param value
	 * @param brightness
	 *            状态值
	 */
	public synchronized boolean updateProgressByMac(String deviceMac,
			int device_progress) {
		ContentValues values = new ContentValues();
		values.put("device_progress", device_progress);
		String table = DBProperty.TABLE_AD_DEVICE;
		String whereClause = "device_mac = ?";
		String[] whereArgs = new String[] { deviceMac };
		return this.updateTableData(table, whereClause, whereArgs, values);

	}
	
	
	/**
	 * 更新数据库设备状态记录
	 * 
	 * @param deviceMac
	 *            设备Mac
	 * @param value
	 * @param brightness
	 *            状态值
	 */
	public synchronized boolean updateProgressByMac(String deviceMac,
			int device_progress,String roomName) {
		ContentValues values = new ContentValues();
		values.put("device_progress", device_progress);
		String table = DBProperty.TABLE_AD_DEVICE;
		String whereClause = "device_mac = ? and room_name = ?";
		String[] whereArgs = new String[] { deviceMac, roomName};
		return this.updateTableData(table, whereClause, whereArgs, values);
		
	}

	/**
	 * 
	 * @param deviceMac
	 * @return
	 */
	public synchronized int getBrightnessByMac(String deviceMac) {
		
		String table = DBProperty.TABLE_AD_DEVICE;
		String[] columns = new String[]{"device_progress"};
		String selection = "device_mac = ?";
		String[] selectionArgs = new String[] { deviceMac };
		
		Cursor cursor = this.query(table, columns, selection, selectionArgs, null, null, null);
		int brightness = 0;
		while(cursor.moveToFirst()){
			brightness = cursor.getInt(cursor.getColumnIndex("device_progress"));
			break;
		}
		
		
		return brightness;
	}
	
	/**
	 * 
	 * @param deviceMac
	 * @return
	 */
	public synchronized String getDeviceNameByMac(String deviceMac,String roomName) {
		
		String table = DBProperty.TABLE_AD_DEVICE;
		String[] columns = new String[]{"device_name"};
		String selection = "device_mac = ? and room_name = ?";
		String[] selectionArgs = new String[] { deviceMac,roomName };
		
		Cursor cursor = this.query(table, columns, selection, selectionArgs, null, null, null);
		String deviceName = null;
		while(cursor.moveToFirst()){
			deviceName = cursor.getString(cursor.getColumnIndex("device_name"));
			break;
		}
		
		
		return deviceName;
	}

	

}
