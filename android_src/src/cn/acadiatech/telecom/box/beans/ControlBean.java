package cn.acadiatech.telecom.box.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ControlBean implements Serializable {

	private String macAdd;
	private int deviceType;
	private int brightness;

	public ControlBean() {
	}

	/**
	 * @return macAdd
	 */
	public String getMacAdd() {
		return macAdd;
	}

	/**
	 * @param macAdd
	 *            the macAdd to set
	 */
	public void setMacAdd(String macAdd) {
		this.macAdd = macAdd;
	}

	/**
	 * @return deviceType
	 */
	public int getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return brightness
	 */
	public int getBrightness() {
		return brightness;
	}

	/**
	 * @param brightness
	 *            the brightness to set
	 */
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	public ControlBean(String macAdd, int deviceType, int brightness) {
		super();
		this.macAdd = macAdd;
		this.deviceType = deviceType;
		this.brightness = brightness;
	}

	@Override
	public String toString() {
		return "ControlBean [macAdd=" + macAdd + ", deviceType=" + deviceType
				+ ", brightness=" + brightness + "]";
	}
	
	
	

}
