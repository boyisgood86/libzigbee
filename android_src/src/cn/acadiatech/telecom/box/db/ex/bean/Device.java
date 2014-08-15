package cn.acadiatech.telecom.box.db.ex.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Device implements Serializable{
	private int device_id;
	private String device_name;
	private DeviceGroup deviceGroup;
	private String device_mac;
	private int device_status;
	private Room room;
	private int device_progress;
	private DeviceType deviceType;
	public Device() {
		
	}
	
	
	

	public Device(int device_id, String device_name, DeviceGroup deviceGroup,
			String device_mac, int device_status, Room room,
			int device_progress, DeviceType deviceType) {
		super();
		this.device_id = device_id;
		this.device_name = device_name;
		this.deviceGroup = deviceGroup;
		this.device_mac = device_mac;
		this.device_status = device_status;
		this.room = room;
		this.device_progress = device_progress;
		this.deviceType = deviceType;
	}


	/** 
	 * @return device_progress 
	 */
	public int getDevice_progress() {
		return device_progress;
	}




	/**
	 * @param device_progress the device_progress to set
	 */
	public void setDevice_progress(int device_progress) {
		this.device_progress = device_progress;
	}

	/** 
	 * @return room 
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}




	/** 
	 * @return deviceType 
	 */
	public DeviceType getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	/** 
	 * @return device_id 
	 */
	public int getDevice_id() {
		return device_id;
	}
	/**
	 * @param device_id the device_id to set
	 */
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	/** 
	 * @return device_name 
	 */
	public String getDevice_name() {
		return device_name;
	}
	/**
	 * @param device_name the device_name to set
	 */
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	/** 
	 * @return deviceGroup 
	 */
	public DeviceGroup getDeviceGroup() {
		return deviceGroup;
	}
	/**
	 * @param deviceGroup the deviceGroup to set
	 */
	public void setDeviceGroup(DeviceGroup deviceGroup) {
		this.deviceGroup = deviceGroup;
	}
	/** 
	 * @return device_mac 
	 */
	public String getDevice_mac() {
		return device_mac;
	}
	/**
	 * @param device_mac the device_mac to set
	 */
	public void setDevice_mac(String device_mac) {
		this.device_mac = device_mac;
	}
	/** 
	 * @return device_status 
	 */
	public int getDevice_status() {
		return device_status;
	}
	/**
	 * @param device_status the device_status to set
	 */
	public void setDevice_status(int device_status) {
		this.device_status = device_status;
	}




	@Override
	public String toString() {
		return "Device [device_id=" + device_id + ", device_name="
				+ device_name + ", deviceGroup=" + deviceGroup
				+ ", device_mac=" + device_mac + ", device_status="
				+ device_status + ", room=" + room + ", device_progress="
				+ device_progress + ", deviceType=" + deviceType + "]";
	}
	
	
	
	
}
