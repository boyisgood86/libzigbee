package cn.acadiatech.telecom.box.db.ex.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RoomType implements Serializable{
	
	private int room_type_id;
	private String description;
	
	
	public RoomType() {
	}
	public RoomType(int room_type_id, String description) {
		super();
		this.room_type_id = room_type_id;
		this.description = description;
	}
	/** 
	 * @return room_type_id 
	 */
	public int getRoom_type_id() {
		return room_type_id;
	}
	/**
	 * @param room_type_id the room_type_id to set
	 */
	public void setRoom_type_id(int room_type_id) {
		this.room_type_id = room_type_id;
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
