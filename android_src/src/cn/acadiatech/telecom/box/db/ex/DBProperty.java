package cn.acadiatech.telecom.box.db.ex;

import java.util.HashMap;

/***
* @ClassName: DBProperty 
* @Description:(锟斤拷菘饨拷锟� 
* @author TangYunXiang
* @date 2014-5-12 锟斤拷锟斤拷4:57:44 
* @version 1.0
 */
public class DBProperty {
	public static final String TAG = "DBProperty";
	public static final String DB_NAME = "acadiatech_telecom.db";
	public static final int DB_VERSION = 1;

	
	public static final String TABLE_AD_USER = "ad_user"; 
	public static final String TABLE_AD_ROOM_TYPE = "ad_room_type"; 
	public static final String TABLE_AD_DEVICE_GROUP = "ad_device_group"; 
	public static final String TABLE_AD_DEVICE = "ad_device"; 
	public static final String TABLE_AD_MODE = "ad_mode"; 
	public static final String TABLE_AD_MODE_DEVICE_GROUP = "ad_mode_device_group"; 
	public static final String TABLE_AD_ROLE  = "ad_role"; 
	public static final String TABLE_AD_ROOM= "ad_room"; 
	public static final String TABLE_AD_DEVICE_TYPE= "ad_device_type"; 
	public static final String TABLE_AD_MODE_RECORD= "ad_mode_record"; 
	
	// 鎴垮瀷
	private static final String TABLE_AD_ROOM_TYPE_DB_SQL = "CREATE TABLE if not exists " + TABLE_AD_ROOM_TYPE + " ("
				+" room_type_id INTEGER primary key autoincrement,"
				+ "description text null)";
	
	// 璁惧缁�
		private static final String TABLE_AD_DEVICE_GROUP_DB_SQL = "CREATE TABLE if not exists " + TABLE_AD_DEVICE_GROUP + " ("
				+" device_group_id INTEGER PRIMARY KEY autoincrement,"
				+ "device_group_name text null," 
				+ "room_type_id INTEGER null)";		
	// 璁惧
				private static final String TABLE_AD_DEVICE_DB_SQL = "CREATE TABLE if not exists " + TABLE_AD_DEVICE + " ("
						+" device_id INTEGER PRIMARY KEY autoincrement, "
						+ "device_name text null," 
						+ "device_group_id INTEGER null,"
						+ "device_mac text null,"
						+ "device_type_id INTEGER null,"
						+ "room_name text null,"
						+ "device_status INTEGER null,"
						+ "device_progress INTEGER  DEFAULT 0)";					
		// 妯″紡
		private static final String TABLE_AD_MODE_DB_SQL = "CREATE TABLE if not exists " + TABLE_AD_MODE + " ("
				+" mode_id INTEGER PRIMARY KEY autoincrement, "
				+ "mode_name text null," 
				+ "room_type_id INTEGER  null)";		
		// 妯″紡-璁惧缁�
		private static final String TABLE_AD_MODE_DEVICE_GROUP_DB_SQL = "CREATE TABLE if not exists " + TABLE_AD_MODE_DEVICE_GROUP + " ("
				+" mode_id INTEGER not null, "
				+ "device_group_id INTEGER not null)";	
		
		// 璁惧绫诲瀷
		private static final String TABLE_AD_DEVICE_TYPE_DB_SQL = "CREATE TABLE if not exists " + TABLE_AD_DEVICE_TYPE + " ("
				+" device_type_id INTEGER PRIMARY KEY autoincrement, "
				+ "device_type INTEGER null," 
				+ "device_type_name text null," 
				+ "device_max INTEGER null," 
				+ "device_progress INTEGER  null)";					
		
		// 妯″紡 璁惧璁板綍
				private static final String TABLE_AD_MODE_RECORD_DB_SQL = "CREATE TABLE if not exists " + TABLE_AD_MODE_RECORD + " ("
						+" mode_id INTEGER not null, "
						+ "device_id INTEGER null," 
						+ "device_status INTEGER null," 
						+ "device_progress INTEGER  null)";		
		
		
		//Role瑙掕壊
		private static final String TABLE_AD_ROLE_DB_SQL = "CREATE TABLE if not exists " + TABLE_AD_ROLE + " ("
				+" role_id INTEGER PRIMARY KEY autoincrement, "
				+ "description text not null)";					
		//鎴块棿
		private static final String TABLE_AD_ROOM_DB_SQL = "CREATE TABLE if not exists " + TABLE_AD_ROOM + " ("
				+" room_name text PRIMARY KEY, "
				+ "room_type_id INTEGER  null,"		
				+ "room_entry_status INTEGER  null,"	
				+ "description text not null)";					
		
	    // 鐢ㄦ埛
		private static final String TABLE_AD_USER_DB_SQL = "CREATE TABLE if not exists " + TABLE_AD_USER + " ("
			+ "room_name text null,"			
			+ "password text null," 
			+ "mobile text null," 
			+ "description text null,"
			+ "role_id INTEGER not null,"	//role锟斤拷锟�
			+ "status INTEGER null)";

		
	static HashMap<String, String> tableMap = new HashMap<String, String>(); // 鏁版嵁琛ㄩ泦锟�

	/**
	 * 
	 * @Title: load
	 * @Description: TODO(锟斤拷锟截憋拷锟接︼拷锟�SQL 锟斤拷锟�
	 * @param @return 锟借定锟侥硷拷
	 * @return HashMap<String,String> 锟斤拷锟斤拷锟斤拷锟斤拷
	 * @throws
	 */
	public static HashMap<String, String> load() {
		tableMap.put(TABLE_AD_ROLE, TABLE_AD_ROLE_DB_SQL);
		tableMap.put(TABLE_AD_USER, TABLE_AD_USER_DB_SQL);
		tableMap.put(TABLE_AD_ROOM_TYPE, TABLE_AD_ROOM_TYPE_DB_SQL);
		tableMap.put(TABLE_AD_DEVICE_GROUP, TABLE_AD_DEVICE_GROUP_DB_SQL);
		tableMap.put(TABLE_AD_DEVICE, TABLE_AD_DEVICE_DB_SQL);
		tableMap.put(TABLE_AD_MODE_DEVICE_GROUP, TABLE_AD_MODE_DEVICE_GROUP_DB_SQL);
		tableMap.put(TABLE_AD_MODE, TABLE_AD_MODE_DB_SQL);
		tableMap.put(TABLE_AD_ROOM, TABLE_AD_ROOM_DB_SQL);
		tableMap.put(TABLE_AD_DEVICE_TYPE, TABLE_AD_DEVICE_TYPE_DB_SQL);
		tableMap.put(TABLE_AD_MODE_RECORD, TABLE_AD_MODE_RECORD_DB_SQL);
		
		return tableMap;
	}
}
