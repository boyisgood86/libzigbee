package cn.acadiatech.telecom.box.db.ex.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ModeDeviceGroup implements Serializable{
	private DeviceGroup deviceGroup;
	private Mode mode;
	
	public ModeDeviceGroup() {
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

	public ModeDeviceGroup(DeviceGroup deviceGroup, Mode mode) {
		super();
		this.deviceGroup = deviceGroup;
		this.mode = mode;
	}


	
}
