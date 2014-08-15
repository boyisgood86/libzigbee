package cn.acadiatech.telecom.box.db.ex.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import cn.acadiatech.telecom.box.db.ex.DBOperation;
import cn.acadiatech.telecom.box.db.ex.DBProperty;
import cn.acadiatech.telecom.box.db.ex.bean.RoomType;

/***
* @ClassName: RoomTypeDBAction 
* @Description:(����ҵ��) 
* @author TangYunXiang
* @date 2014-5-12 ����5:36:45 
* @version 1.0
 */
public class RoomTypeDBAction extends DBOperation {

	public static RoomTypeDBAction instance = null;
	Context context;

	public RoomTypeDBAction(Context context) {
		super(context);
		this.context = context;
	}

	public static synchronized RoomTypeDBAction getInstance(Context context) {
		if (instance == null) {
			instance = new RoomTypeDBAction(context);
		}
		return instance;
	}
	
	public boolean addRoomType(String description) {
		ContentValues values = new ContentValues();
		values.put("description", description);
		return this.insertTableData(DBProperty.TABLE_AD_ROOM_TYPE, values);
	}
	
	public boolean deleteRoomTypeAll() {
		List<RoomType> roomTypes=getRoomTypeAll();
		boolean isSuccess=false;
		for(RoomType roomType:roomTypes){
			isSuccess=deleteRoomTypeById(roomType.getRoom_type_id());
		}
		return isSuccess;
	}
	public boolean deleteRoomTypeById(int room_type_id) {
		boolean isSuccess=this.deleteTableData(DBProperty.TABLE_AD_ROOM_TYPE, "room_type_id="+room_type_id, new String[] {});
		if(isSuccess){
			isSuccess=DeviceGroupDBAction.getInstance(context).deleteDeviceGroupByRoomTypeID(room_type_id);
			ModeDBAction.getInstance(context).deleteModeByRoomTypeId(room_type_id);
		}
		return isSuccess;
	}
	
	public boolean updateRoomTypeById(int id,String description) {
		ContentValues values = new ContentValues();
		values.put("description", description);
		return this.updateTableData(DBProperty.TABLE_AD_ROOM_TYPE, "room_type_id="+id, new String[] {}, values);
	}
	
	public List<RoomType> getRoomTypeAll() {
		List<RoomType> list = new ArrayList<RoomType>();
		String sql = "select * from " + DBProperty.TABLE_AD_ROOM_TYPE ;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			RoomType roomType = new RoomType();
			roomType.setRoom_type_id(Integer.parseInt(hashMap.get("room_type_id")+""));
			roomType.setDescription(hashMap.get("description")+"");
			list.add(roomType);
		}
		return list;
	}
	public RoomType getRoomTypeById(int id) {
		RoomType roomType = null;
		String sql = "select * from " + DBProperty.TABLE_AD_ROOM_TYPE +" where room_type_id="+id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			roomType= new RoomType();
			roomType.setRoom_type_id(Integer.parseInt(hashMap.get("room_type_id")+""));
			roomType.setDescription(hashMap.get("description")+"");
		}
		return roomType;
	}

}
