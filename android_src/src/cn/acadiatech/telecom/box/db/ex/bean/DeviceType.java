package cn.acadiatech.telecom.box.db.ex.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DeviceType implements Serializable{
	private int device_type_id;
	private String device_type_name;
	private int device_max;
	private int device_type;
	private int device_progress;
	
	public DeviceType() {
	}


	public DeviceType(int device_type_id, String device_type_name,
			int device_max, int device_type, int device_progress) {
		super();
		this.device_type_id = device_type_id;
		this.device_type_name = device_type_name;
		this.device_max = device_max;
		this.device_type = device_type;
		this.device_progress = device_progress;
	}

	/** 
	 * @return device_type 
	 */
	public int getDevice_type() {
		return device_type;
	}

	/**
	 * @param device_type the device_type to set
	 */
	public void setDevice_type(int device_type) {
		this.device_type = device_type;
	}

	/** 
	 * @return device_type_id 
	 */
	public int getDevice_type_id() {
		return device_type_id;
	}

	/**
	 * @param device_type_id the device_type_id to set
	 */
	public void setDevice_type_id(int device_type_id) {
		this.device_type_id = device_type_id;
	}

	/** 
	 * @return device_type_name 
	 */
	public String getDevice_type_name() {
		return device_type_name;
	}

	/**
	 * @param device_type_name the device_type_name to set
	 */
	public void setDevice_type_name(String device_type_name) {
		this.device_type_name = device_type_name;
	}

	/** 
	 * @return device_max 
	 */
	public int getDevice_max() {
		return device_max;
	}

	/**
	 * @param device_max the device_max to set
	 */
	public void setDevice_max(int device_max) {
		this.device_max = device_max;
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

}
