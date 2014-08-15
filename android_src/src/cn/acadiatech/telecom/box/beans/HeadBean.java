package cn.acadiatech.telecom.box.beans;

import java.io.Serializable;

/**
 * Json头文件
 * 
 * @author QUYONG
 * 
 */
public class HeadBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private int status;
	private String message;
	private int method;

	public HeadBean() {
		super();
	}

	public HeadBean(int status, String message, int method) {
		super();
		this.status = status;
		this.message = message;
		this.method = method;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "HeadBean [status=" + status + ", message=" + message
				+ ", method=" + method + "]";
	}

}
