package cn.acadiatech.telecom.box.db.ex.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DeviceGroup implements Serializable{
	private int device_group_id;
	private String device_group_name;
	private RoomType roomType;
	
	public DeviceGroup() {
	}
	
	public DeviceGroup(int device_group_id, String device_group_name,
			RoomType roomType) {
		super();
		this.device_group_id = device_group_id;
		this.device_group_name = device_group_name;
		this.roomType = roomType;
	}
	/** 
	 * @return device_group_id 
	 */
	public int getDevice_group_id() {
		return device_group_id;
	}
	/**
	 * @param device_group_id the device_group_id to set
	 */
	public void setDevice_group_id(int device_group_id) {
		this.device_group_id = device_group_id;
	}
	/** 
	 * @return device_group_name 
	 */
	public String getDevice_group_name() {
		return device_group_name;
	}
	/**
	 * @param device_group_name the device_group_name to set
	 */
	public void setDevice_group_name(String device_group_name) {
		this.device_group_name = device_group_name;
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
