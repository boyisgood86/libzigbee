package cn.acadiatech.telecom.box.db.ex.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import cn.acadiatech.telecom.box.db.ex.DBOperation;
import cn.acadiatech.telecom.box.db.ex.DBProperty;
import cn.acadiatech.telecom.box.db.ex.bean.ModeRecord;

/***
* @ClassName: ModeRecordDBAction 
* @Description:(模式记录) 
* @author TangYunXiang
* @date 2014-5-16 下午3:46:15 
* @version 1.0
 */
public class ModeRecordDBAction extends DBOperation {
	public static ModeRecordDBAction instance = null;
	Context context;

	public ModeRecordDBAction(Context context) {
		super(context);
		this.context = context;
	}

	public static synchronized ModeRecordDBAction getInstance(Context context) {
		if (instance == null) {
			instance = new ModeRecordDBAction(context);
		}
		return instance;
	}
	
	public boolean addModeRecord(int mode_id,int  device_id,int device_status,int device_progress) {
		if(getByModeIdAndDeviceId(mode_id, device_id)==null){
			ContentValues values = new ContentValues();
			values.put("mode_id", mode_id);
			values.put("device_id", device_id);
			values.put("device_status", device_status);
			values.put("device_progress", device_progress);
			return this.insertTableData(DBProperty.TABLE_AD_MODE_RECORD, values);
		}else {
			return updateModeRecord(mode_id, device_id, device_status, device_progress);
		}
	}
	
	public void addModeRecordList(List<ModeRecord> modeRecords) {
		for(ModeRecord modeRecord:modeRecords){
			addModeRecord(modeRecord.getMode().getMode_id(), modeRecord.getDevice().getDevice_id(),
					modeRecord.getDevice_status(), modeRecord.getDevice_progress());
		}
	}
	
	public boolean deleteModeRecord() {
		return this.deleteTableData(DBProperty.TABLE_AD_MODE_RECORD, null, new String[] {});
	}
	
	public boolean updateModeRecord(int mode_id,int  device_id,int device_status,int device_progress) {
		ContentValues values = new ContentValues();
		values.put("device_status", device_status);
		values.put("device_progress", device_progress);
		return this.updateTableData(DBProperty.TABLE_AD_MODE_RECORD, "mode_id="+mode_id+" and device_id="+device_id, new String[] {}, values);
	}
	
	public ModeRecord getByModeIdAndDeviceId(int mode_id,int device_id) {
		ModeRecord modeRecord = null ;
		String sql = "select * from " + DBProperty.TABLE_AD_MODE_RECORD +" where mode_id="+mode_id+" and device_id="+device_id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			modeRecord= new ModeRecord();
			modeRecord.setMode(ModeDBAction.getInstance(context).getModeById(mode_id));
			modeRecord.setDevice(DeviceDBAction.getInstance(context).getDeviceById(device_id));
			modeRecord.setDevice_progress(Integer.parseInt(hashMap.get("device_progress")+""));
			modeRecord.setDevice_status(Integer.parseInt(hashMap.get("device_status")+""));
		}
		return modeRecord;
	}
	
	public List<ModeRecord> getByModeId(int mode_id) {
		List<ModeRecord> list = new ArrayList<ModeRecord>();
		ModeRecord modeRecord = null ;
		String sql = "select * from " + DBProperty.TABLE_AD_MODE_RECORD +" where mode_id="+mode_id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			modeRecord= new ModeRecord();
			modeRecord.setMode(ModeDBAction.getInstance(context).getModeById(mode_id));
			int device_id=Integer.parseInt(hashMap.get("device_id")+"");
			modeRecord.setDevice(DeviceDBAction.getInstance(context).getDeviceById(device_id));
			modeRecord.setDevice_progress(Integer.parseInt(hashMap.get("device_progress")+""));
			modeRecord.setDevice_status(Integer.parseInt(hashMap.get("device_status")+""));
			list.add(modeRecord);
		}
		return list;
	}
	

}
