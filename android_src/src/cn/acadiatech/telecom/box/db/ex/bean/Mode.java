package cn.acadiatech.telecom.box.db.ex.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Mode implements Serializable{
	private int mode_id;
	private String mode_name;
	private RoomType roomType;
	
	public Mode() {
	}

	public Mode(int mode_id, String mode_name, RoomType roomType) {
		super();
		this.mode_id = mode_id;
		this.mode_name = mode_name;
		this.roomType = roomType;
	}

	/** 
	 * @return mode_id 
	 */
	public int getMode_id() {
		return mode_id;
	}

	/**
	 * @param mode_id the mode_id to set
	 */
	public void setMode_id(int mode_id) {
		this.mode_id = mode_id;
	}

	/** 
	 * @return mode_name 
	 */
	public String getMode_name() {
		return mode_name;
	}

	/**
	 * @param mode_name the mode_name to set
	 */
	public void setMode_name(String mode_name) {
		this.mode_name = mode_name;
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
	
	
}
