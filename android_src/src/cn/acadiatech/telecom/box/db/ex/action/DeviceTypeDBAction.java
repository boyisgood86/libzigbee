package cn.acadiatech.telecom.box.db.ex.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import cn.acadiatech.telecom.box.db.ex.DBOperation;
import cn.acadiatech.telecom.box.db.ex.DBProperty;
import cn.acadiatech.telecom.box.db.ex.bean.DeviceType;
import cn.acadiatech.telecom.box.utils.Constant;

/***
* @ClassName: DeviceTypeDBAction 
* @Description:(设备类型) 
* @author TangYunXiang
* @date 2014-5-15 下午4:28:27 
* @version 1.0
 */
public class DeviceTypeDBAction extends DBOperation {

	public static DeviceTypeDBAction instance = null;
	Context context;

	public DeviceTypeDBAction(Context context) {
		super(context);
		this.context = context;
	}

	public static synchronized DeviceTypeDBAction getInstance(Context context) {
		if (instance == null) {
			instance = new DeviceTypeDBAction(context);
		}
		return instance;
	}
	
	/***
	* @Title: initDeviceType 
	* @Description: TODO(固定程序三个个类型) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void initDeviceType() {
		if(getAll().size()==0){ //没有记录时，插入数据
			addDeviceType("开关类型",100,1,Constant.SWITCH_VIEW);
			addDeviceType("灯类型",100,1,Constant.LIGHT_VIEW);
			addDeviceType("窗帘类型",100,1,Constant.CURTAIN_VIEW);
		}
	}
	
	public boolean addDeviceType(String device_type_name,int  device_max,int device_progress,int device_type) {
		ContentValues values = new ContentValues();
		values.put("device_type_name", device_type_name);
		values.put("device_max", device_max);
		values.put("device_type", device_type);
		values.put("device_progress", device_progress);
		return this.insertTableData(DBProperty.TABLE_AD_DEVICE_TYPE, values);
	}
	public boolean addDeviceType(String device_type_name) {
		ContentValues values = new ContentValues();
		values.put("device_type_name", device_type_name);
		return this.insertTableData(DBProperty.TABLE_AD_DEVICE_TYPE, values);
	}
	
	
	public boolean deleteDeviceType() {
		return this.deleteTableData(DBProperty.TABLE_AD_DEVICE_TYPE, null, new String[] {});
	}
	
	
	public List<DeviceType> getAll() {
		List<DeviceType> list = new ArrayList<DeviceType>();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE_TYPE ;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			DeviceType deviceType = new DeviceType();
			deviceType.setDevice_type_id(Integer.parseInt(hashMap.get("device_type_id")+""));
			deviceType.setDevice_max(Integer.parseInt(hashMap.get("device_max")+""));
			deviceType.setDevice_progress(Integer.parseInt(hashMap.get("device_progress")+""));
			deviceType.setDevice_type(Integer.parseInt(hashMap.get("device_type")+""));
			
			deviceType.setDevice_type_name(hashMap.get("device_type_name")+"");
			list.add(deviceType);
		}
		return list;
	}
	public DeviceType getById(int id) {
		DeviceType deviceType = new DeviceType();
		String sql = "select * from " + DBProperty.TABLE_AD_DEVICE_TYPE +" where device_type_id="+id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			deviceType.setDevice_type_id(Integer.parseInt(hashMap.get("device_type_id")+""));
			deviceType.setDevice_max(Integer.parseInt(hashMap.get("device_max")+""));
			deviceType.setDevice_type(Integer.parseInt(hashMap.get("device_type")+""));
			deviceType.setDevice_progress(Integer.parseInt(hashMap.get("device_progress")+""));
			deviceType.setDevice_type_name(hashMap.get("device_type_name")+"");
		}
		return deviceType;
	}

}
