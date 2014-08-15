package cn.acadiatech.telecom.box.db.ex.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import cn.acadiatech.telecom.box.db.ex.DBOperation;
import cn.acadiatech.telecom.box.db.ex.DBProperty;
import cn.acadiatech.telecom.box.db.ex.bean.Room;
import cn.acadiatech.telecom.box.utils.Constant;

/***
* @ClassName: RoomDBAction 
* @Description:(鎴块棿鏁版嵁绫� 
* @author TangYunXiang
* @date 2014-5-17 涓嬪崍1:24:22 
* @version 1.0
 */
public class RoomDBAction extends DBOperation {

	public static RoomDBAction instance = null;
	Context context;

	public RoomDBAction(Context context) {
		super(context);
		this.context = context;
	}

	public static synchronized RoomDBAction getInstance(Context context) {
		if (instance == null) {
			instance = new RoomDBAction(context);
		}
		return instance;
	}
	
	public boolean addRoom(String room_name,int  room_type_id,String description) {
		ContentValues values = new ContentValues();
		values.put("room_name", room_name);
		values.put("room_type_id", room_type_id);
		values.put("room_entry_status", Constant.ROOM_ENTRY_STATUS_NO);//榛樿涓烘湭褰曞叆
		values.put("description", description);
		
		Room room=getRoomByName(room_name);  
		if(room==null){
			return this.insertTableData(DBProperty.TABLE_AD_ROOM, values);
		}else {
			return false;
		}
	}
	
	public boolean deleteRoom() {
		return this.deleteTableData(DBProperty.TABLE_AD_ROOM, null, new String[] {});
	}
	public boolean deleteRoomById(String room_name) {
		return this.deleteTableData(DBProperty.TABLE_AD_ROOM, "room_name="+room_name, new String[] {});
	}
	public boolean deleteRoomByRoomTypeId(int room_type_id) {
		return this.deleteTableData(DBProperty.TABLE_AD_ROOM, "room_type_id="+room_type_id, new String[] {});
	}
	
	public boolean updateRoomTypeByName(String room_name,int room_type_id) {
		ContentValues values = new ContentValues();
		values.put("room_type_id", room_type_id);
		Room room=getRoomByName(room_name);  
		if(room.getRoom_name()!=null){
			return this.updateTableData(DBProperty.TABLE_AD_ROOM, "room_name= '"+ room_name+"'", new String[] {}, values);
		}else {
			return false;
		}
	}
	
	
	public boolean updateEntryStatusByName(String room_name,int room_entry_status) {
		ContentValues values = new ContentValues();
		values.put("room_entry_status",room_entry_status);
		Room room=getRoomByName(room_name);  
		if(room.getRoom_name()!=null){
			return this.updateTableData(DBProperty.TABLE_AD_ROOM, "room_name= '"+room_name+"'", new String[] {}, values);
		}else {
			return false;
		}
	}
	
	public List<Room> getRoomAll() {
		List<Room> list = new ArrayList<Room>();
		String sql = "select * from " + DBProperty.TABLE_AD_ROOM +" order by room_entry_status,room_name";
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Room room = new Room();
			room.setRoom_name(hashMap.get("room_name")+"");
			room.setDescription(hashMap.get("description")+"");
			room.setRoom_entry_status(Integer.parseInt(hashMap.get("room_entry_status")+""));
			int room_type_id= Integer.parseInt(hashMap.get("room_type_id")+"");
			room.setRoomType(RoomTypeDBAction.getInstance(context).getRoomTypeById(room_type_id));
			list.add(room);
		}
		return list;
	}
	public Room getRoomByName(String  room_name) {
		Room room =null;
		String sql = "select * from " + DBProperty.TABLE_AD_ROOM +" where room_name= '"+room_name+"'";
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			room= new Room();
			room.setRoom_name(hashMap.get("room_name")+"");
			room.setDescription(hashMap.get("description")+"");
			room.setRoom_entry_status(Integer.parseInt(hashMap.get("room_entry_status")+""));
			int room_type_id= Integer.parseInt(hashMap.get("room_type_id")+"");
			room.setRoomType(RoomTypeDBAction.getInstance(context).getRoomTypeById(room_type_id));
		}
		return room;
	}
	
	public List<Room> getRoomByRoomTypeId(int id) {
		List<Room> list = new ArrayList<Room>();
		String sql = "select * from " + DBProperty.TABLE_AD_ROOM +" where room_type_id="+id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Room room = new Room();
			room.setRoom_name(hashMap.get("room_name")+"");
			room.setDescription(hashMap.get("description")+"");
			room.setRoom_entry_status(Integer.parseInt(hashMap.get("room_entry_status")+""));
			int room_type_id= Integer.parseInt(hashMap.get("room_type_id")+"");
			room.setRoomType(RoomTypeDBAction.getInstance(context).getRoomTypeById(room_type_id));
		}
		return list;
	}
	
	public List<Room> getRoomByEntryStatus(int room_entry_status) {
		List<Room> list = new ArrayList<Room>();
		String sql = "select * from " + DBProperty.TABLE_AD_ROOM +" where room_entry_status="+room_entry_status;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Room room = new Room();
			room.setRoom_name(hashMap.get("room_name")+"");
			room.setDescription(hashMap.get("description")+"");
			room.setRoom_entry_status(Integer.parseInt(hashMap.get("room_entry_status")+""));
			int room_type_id= Integer.parseInt(hashMap.get("room_type_id")+"");
			room.setRoomType(RoomTypeDBAction.getInstance(context).getRoomTypeById(room_type_id));
		}
		return list;
	}

}
