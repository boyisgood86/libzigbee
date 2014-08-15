package cn.acadiatech.telecom.box.db.ex.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import cn.acadiatech.telecom.box.db.ex.DBOperation;
import cn.acadiatech.telecom.box.db.ex.DBProperty;
import cn.acadiatech.telecom.box.db.ex.bean.ModeDeviceGroup;



//FIXME @TangYunXiang 鍥犱负Mode璺烺oomType鍏宠仈锛孯oomType璺烡eviceGroup鍏宠仈锛屾墍浠ユ绫绘殏鏃朵笉鐢�
//FIXME @TangYunXiang 鍥犱负Mode璺烺oomType鍏宠仈锛孯oomType璺烡eviceGroup鍏宠仈锛屾墍浠ユ绫绘殏鏃朵笉鐢�
//FIXME @TangYunXiang 鍥犱负Mode璺烺oomType鍏宠仈锛孯oomType璺烡eviceGroup鍏宠仈锛屾墍浠ユ绫绘殏鏃朵笉鐢�
/***
* @ClassName: ModeDeviceGroupDBAction 
* @Description:(妯″紡涓庤澶囩粍鍏宠仈琛� 
* @author TangYunXiang
* @date 2014-5-15 涓婂崍9:37:53 
* @version 1.0
 */
public class ModeDeviceGroupDBAction extends DBOperation {
	public static ModeDeviceGroupDBAction instance = null;
	Context context;

	public ModeDeviceGroupDBAction(Context context) {
		super(context);
		this.context = context;
	}

	public static synchronized ModeDeviceGroupDBAction getInstance(Context context) {
		if (instance == null) {
			instance = new ModeDeviceGroupDBAction(context);
		}
		return instance;
	}
	
	public boolean addModeDeviceGroup(int mode_id,int device_group_id) {
		ContentValues values = new ContentValues();
		values.put("mode_id", mode_id);
		values.put("device_group_id", device_group_id);
		return this.insertTableData(DBProperty.TABLE_AD_MODE_DEVICE_GROUP, values);
	}
	
	public boolean deleteAll() {
		 return this.deleteTableData(DBProperty.TABLE_AD_MODE_DEVICE_GROUP, null, new String[] {});
	}
	public boolean deleteByModeId(int mode_id) {
		 boolean isSuccess=this.deleteTableData(DBProperty.TABLE_AD_MODE_DEVICE_GROUP, "mode_id="+mode_id, new String[] {});
		 return isSuccess;
	}
	public boolean deleteByDeviceGroupId(int device_group_id) {
		 boolean isSuccess=this.deleteTableData(DBProperty.TABLE_AD_MODE_DEVICE_GROUP, "device_group_id="+device_group_id, new String[] {});
		 return isSuccess;
	}
	
	public boolean updateDeviceGroupById(int id,String device_group_name) {
//		ContentValues values = new ContentValues();
//		values.put("device_group_name", device_group_name);
//		return this.updateTableData(DBProperty.TABLE_AD_DEVICE_GROUP, "device_group_id="+id, new String[] {}, values);
		return false;
	}
	
	public List<ModeDeviceGroup> getAll() {
		List<ModeDeviceGroup> list = new ArrayList<ModeDeviceGroup>();
		String sql = "select * from " + DBProperty.TABLE_AD_MODE_DEVICE_GROUP ;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			ModeDeviceGroup modeDeviceGroup = new ModeDeviceGroup();
			int device_group_id= Integer.parseInt(hashMap.get("device_group_id")+"");
			int mode_id=Integer.parseInt(hashMap.get("mode_id")+"");
			modeDeviceGroup.setMode(ModeDBAction.getInstance(context).getModeById(mode_id));
			modeDeviceGroup.setDeviceGroup(DeviceGroupDBAction.getInstance(context).getDeviceGroupById(device_group_id));
			list.add(modeDeviceGroup);
		}
		return list;
	}
	public List<ModeDeviceGroup>  getByModeId(int id) {
		List<ModeDeviceGroup> list = new ArrayList<ModeDeviceGroup>();
		String sql = "select * from " + DBProperty.TABLE_AD_MODE_DEVICE_GROUP  +" where mode_id="+id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			ModeDeviceGroup modeDeviceGroup = new ModeDeviceGroup();
			int device_group_id= Integer.parseInt(hashMap.get("device_group_id")+"");
			int mode_id=Integer.parseInt(hashMap.get("mode_id")+"");
			modeDeviceGroup.setMode(ModeDBAction.getInstance(context).getModeById(mode_id));
			modeDeviceGroup.setDeviceGroup(DeviceGroupDBAction.getInstance(context).getDeviceGroupById(device_group_id));
			list.add(modeDeviceGroup);
		}
		return list;
	}
	
	public List<ModeDeviceGroup> getByDeviceGroupId(int id) {
		List<ModeDeviceGroup> list = new ArrayList<ModeDeviceGroup>();
		String sql = "select * from " + DBProperty.TABLE_AD_MODE_DEVICE_GROUP  +" where device_group_id="+id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			ModeDeviceGroup modeDeviceGroup = new ModeDeviceGroup();
			int device_group_id= Integer.parseInt(hashMap.get("device_group_id")+"");
			int mode_id=Integer.parseInt(hashMap.get("mode_id")+"");
			modeDeviceGroup.setMode(ModeDBAction.getInstance(context).getModeById(mode_id));
			modeDeviceGroup.setDeviceGroup(DeviceGroupDBAction.getInstance(context).getDeviceGroupById(device_group_id));
			list.add(modeDeviceGroup);
		}
		return list;
	}
}
