package cn.acadiatech.telecom.box.db.ex.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ModeRecord implements Serializable{
	private Mode mode;
	private Device device;
	private int device_status;
	private int device_progress;
	public ModeRecord() {
	}
	/** 
	 * @return mode 
	 */
	public Mode getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	/** 
	 * @return device 
	 */
	public Device getDevice() {
		return device;
	}
	/**
	 * @param device the device to set
	 */
	public void setDevice(Device device) {
		this.device = device;
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
	public ModeRecord(Mode mode, Device device, int device_status,
			int device_progress) {
		super();
		this.mode = mode;
		this.device = device;
		this.device_status = device_status;
		this.device_progress = device_progress;
	}
	
	
	
}
