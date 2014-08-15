package cn.acadiatech.telecom.box.db.ex.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import cn.acadiatech.telecom.box.db.ex.DBOperation;
import cn.acadiatech.telecom.box.db.ex.DBProperty;
import cn.acadiatech.telecom.box.db.ex.bean.DeviceGroup;

/***
* @ClassName: DeviceGroupDBAction 
* @Description:(璁惧缁� 
* @author TangYunXiang
* @date 2014-5-15 涓婂崍9:37:53 
* @version 1.0
 */
public class DeviceGroupDBAction extends DBOperation {
	public static DeviceGroupDBAction instance = null;
	Context context;

	public DeviceGroupDBAction(Context context) {
		super(context);
		this.context = context;
	}

	public static synchronized DeviceGroupDBAction getInstance(Context context) {
		if (instance == null) {
			instance = new DeviceGroupDBAction(context);
		}
		return instance;
	}
	
	
	
	public boolean addDeviceGroup(String device_group_name,int room_type_id) {
		ContentValues values = new ContentValues();
		values.put("device_group_name", device_group_name);
		values.put("room_type_id", room_type_id);
		return this.insertTableData(DBProperty.TABLE_AD_DEVICE_GROUP, values);
	}
	
	public boolean deleteDeviceGroup() {
		 this.getDB().beginTransaction();
		 this.deleteTableData(DBProperty.TABLE_AD_DEVICE_GROUP, null, new String[] {});
		 try{
			this.deleteTableData(DBProperty.TABLE_AD_DEVICE, null, new String[] {});
			this.getDB().setTransactionSuccessful();
		 }catch(Exception e){
			 
		 }finally{
			 this.getDB().endTransaction();
		 }
		 return this.getDB().inTransaction();
	}
	public boolean deleteDeviceGroupById(int id) {
		 this.getDB().beginTransaction();
		 boolean isSuccess=this.deleteTableData(DBProperty.TABLE_AD_DEVICE_GROUP, "device_group_id="+id, new String[] {});
		 try{
			 	this.deleteTableData(DBProperty.TABLE_AD_DEVICE, "device_group_id="+id, new String[] {});
			 	this.getDB().setTransactionSuccessful();
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 this.getDB().endTransaction();
		 }
		return isSuccess;
	}
	
	public boolean deleteDeviceGroupByRoomTypeID(int roomTypeId) {
		boolean isSuccess=false;
		List<DeviceGroup> devicelist=getDeviceGroupByRoomTypeId(roomTypeId);
		for(DeviceGroup deviceGroup:devicelist){
			DeviceDBAction.getInstance(context).deleteDeviceByDeviceGroupId(deviceGroup.getDevice_group_id());
			this.deleteTableData(DBProperty.TABLE_AD_DEVICE_GROUP, "room_type_id="+deviceGroup.getRoomType().getRoom_type_id(), new String[] {});
		}
		return isSuccess;
	}
	
	public boolean updateDeviceGroupById(int id,String device_group_name) {
		ContentValues values = new ContentValues();
		values.put("device_group_name", device_group_name);
		return this.updateTableData(DBProperty.TABLE_AD_DEVICE_GROUP, "device_group_id="+id, new String[] {}, values);
	}
	
	public List<DeviceGroup> getDeviceGroupAll() {
		List<DeviceGroup> list = new ArrayList<DeviceGroup>();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE_GROUP ;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			DeviceGroup deviceGroup = new DeviceGroup();
			deviceGroup.setDevice_group_id(Integer.parseInt(hashMap.get("device_group_id")+""));
			deviceGroup.setDevice_group_name(hashMap.get("device_group_name")+"");
			int room_type_id= Integer.parseInt(hashMap.get("room_type_id")+"");
			deviceGroup.setRoomType(RoomTypeDBAction.getInstance(context).getRoomTypeById(room_type_id));
			list.add(deviceGroup);
		}
		return list;
	}
	public DeviceGroup getDeviceGroupById(int id) {
		DeviceGroup deviceGroup = new DeviceGroup();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE_GROUP  +" where device_group_id="+id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			deviceGroup.setDevice_group_id(Integer.parseInt(hashMap.get("device_group_id")+""));
			deviceGroup.setDevice_group_name(hashMap.get("device_group_name")+"");
			int room_type_id= Integer.parseInt(hashMap.get("room_type_id")+"");
			deviceGroup.setRoomType(RoomTypeDBAction.getInstance(context).getRoomTypeById(room_type_id));
		}
		return deviceGroup;
	}
	public List<DeviceGroup> getDeviceGroupByRoomTypeId(int id) {
		List<DeviceGroup> list = new ArrayList<DeviceGroup>();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE_GROUP +" where room_type_id="+id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			DeviceGroup deviceGroup = new DeviceGroup();
			deviceGroup.setDevice_group_id(Integer.parseInt(hashMap.get("device_group_id")+""));
			deviceGroup.setDevice_group_name(hashMap.get("device_group_name")+"");
			int room_type_id= Integer.parseInt(hashMap.get("room_type_id")+"");
			deviceGroup.setRoomType(RoomTypeDBAction.getInstance(context).getRoomTypeById(room_type_id));
			list.add(deviceGroup);
		}
		return list;
	}
}
