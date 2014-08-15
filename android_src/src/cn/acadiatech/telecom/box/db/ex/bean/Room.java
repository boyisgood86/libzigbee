package cn.acadiatech.telecom.box.db.ex.bean;


public class Room {
	private String  room_name;
	private RoomType roomType;
	private int room_entry_status;
	private String description;
	public Room() {
	}
	
	public Room(String room_name, RoomType roomType, int room_entry_status,
			String description) {
		super();
		this.room_name = room_name;
		this.roomType = roomType;
		this.room_entry_status = room_entry_status;
		this.description = description;
	}

	/** 
	 * @return room_entry_status 
	 */
	public int getRoom_entry_status() {
		return room_entry_status;
	}

	/**
	 * @param room_entry_status the room_entry_status to set
	 */
	public void setRoom_entry_status(int room_entry_status) {
		this.room_entry_status = room_entry_status;
	}

	/** 
	 * @return room_name 
	 */
	public String getRoom_name() {
		if(room_name!=null&&room_name.equals("")&&room_name.equals("null")){
			return room_name;
		}else
			return "";
		 
	}
	/**
	 * @param room_name the room_name to set
	 */
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	/** 
	 * @return roomType 
	 */
	public RoomType getRoomType() {
		return roomType;
	}
	/**
	 * @param roomType the roomType to set
	 */
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	/** 
	 * @return description 
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
