package cn.acadiatech.telecom.box.db.ex.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import cn.acadiatech.telecom.box.db.ex.DBOperation;
import cn.acadiatech.telecom.box.db.ex.DBProperty;
import cn.acadiatech.telecom.box.db.ex.bean.Mode;

/***
* @ClassName: ModeDBAction 
* @Description:(模式锟斤拷锟斤拷锟� 
* @author TangYunXiang
* @date 2014-5-13 锟斤拷锟斤拷9:45:13 
* @version 1.0
 */
public class ModeDBAction extends DBOperation {

	public static ModeDBAction instance = null;
	Context context;

	public ModeDBAction(Context context) {
		super(context);
		this.context = context;
	}

	public static synchronized ModeDBAction getInstance(Context context) {
		if (instance == null) {
			instance = new ModeDBAction(context);
		}
		return instance;
	}
	
	/***
	* @Title: initMode 
	* @Description: TODO(鍥哄畾绋嬪簭浜斾釜妯″紡) 
	* @param     璁惧畾鏂囦欢 
	* @return void    杩斿洖绫诲瀷 
	* @throws
	 */
	public void initMode(int  room_type_id) {
		if(getModeByRoomTypeId(room_type_id).size()==0){ //娌℃湁璁板綍鏃讹紝鎻掑叆鏁版嵁
			addMode("鍏ョ潯妯″紡",room_type_id);
			addMode("璧峰簥妯″紡",room_type_id);
			addMode("濞变箰妯″紡",room_type_id);
			addMode("鍔炲叕妯″紡",room_type_id);
			addMode("娲楁荡妯″紡",room_type_id);
		}
	}
	
	public boolean addMode(String mode_name,int  room_type_id) {
		ContentValues values = new ContentValues();
		values.put("mode_name", mode_name);
		values.put("room_type_id", room_type_id);
		return this.insertTableData(DBProperty.TABLE_AD_MODE, values);
	}
	
	public boolean deleteMode() {
		return this.deleteTableData(DBProperty.TABLE_AD_MODE, null, new String[] {});
	}
	public boolean deleteModeById(int mode_id) {
		return this.deleteTableData(DBProperty.TABLE_AD_MODE, "mode_id="+mode_id, new String[] {});
	}
	public boolean deleteModeByRoomTypeId(int room_type_id) {
		return this.deleteTableData(DBProperty.TABLE_AD_MODE, "room_type_id="+room_type_id, new String[] {});
	}
	
	public boolean updateMacById(int id,String mode_name) {
		ContentValues values = new ContentValues();
		values.put("mode_name", mode_name);
		return this.updateTableData(DBProperty.TABLE_AD_MODE, "mode_id="+id, new String[] {}, values);
	}
	
	public List<Mode> getModeAll() {
		List<Mode> list = new ArrayList<Mode>();
		String sql = "select * from " + DBProperty.TABLE_AD_MODE ;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Mode mode = new Mode();
			mode.setMode_id(Integer.parseInt(hashMap.get("mode_id")+""));
			mode.setMode_name(hashMap.get("mode_name")+"");
			int room_type_id= Integer.parseInt(hashMap.get("room_type_id")+"");
			mode.setRoomType(RoomTypeDBAction.getInstance(context).getRoomTypeById(room_type_id));
			list.add(mode);
		}
		return list;
	}
	public Mode getModeById(int id) {
		Mode mode = new Mode();
		String sql = "select * from " + DBProperty.TABLE_AD_MODE +" where mode_id="+id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			mode.setMode_id(Integer.parseInt(hashMap.get("mode_id")+""));
			mode.setMode_name(hashMap.get("mode_name")+"");
			int room_type_id= Integer.parseInt(hashMap.get("room_type_id")+"");
			mode.setRoomType(RoomTypeDBAction.getInstance(context).getRoomTypeById(room_type_id));
		}
		return mode;
	}
	
	public Mode getModeByName(String mode_name,int room_type) {
		Mode mode = null;
		String sql = "select * from " + DBProperty.TABLE_AD_MODE +" where mode_name='"+mode_name+"'"+" and 	room_type_id="+room_type;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			mode= new Mode();
			mode.setMode_id(Integer.parseInt(hashMap.get("mode_id")+""));
			mode.setMode_name(hashMap.get("mode_name")+"");
			int room_type_id= Integer.parseInt(hashMap.get("room_type_id")+"");
			mode.setRoomType(RoomTypeDBAction.getInstance(context).getRoomTypeById(room_type_id));
		}
		return mode;
	}
	
	public List<Mode> getModeByRoomTypeId(int id) {
		List<Mode> list = new ArrayList<Mode>();
		String sql = "select * from " + DBProperty.TABLE_AD_MODE +" where room_type_id="+id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Mode mode = new Mode();
			mode.setMode_id(Integer.parseInt(hashMap.get("mode_id")+""));
			mode.setMode_name(hashMap.get("mode_name")+"");
			int room_type_id= Integer.parseInt(hashMap.get("room_type_id")+"");
			mode.setRoomType(RoomTypeDBAction.getInstance(context).getRoomTypeById(room_type_id));
			list.add(mode);
		}
		return list;
	}

}
