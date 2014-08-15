package cn.acadiatech.telecom.box.beans;

/**
 * 用户Bean
 * 
 * @author QUYONG
 * 
 */
public class UserBean {
	private String room_name;
	private String password;
	private String mobile;
	private String description;
	private RoleBean role;

	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RoleBean getRole() {
		return role;
	}

	public void setRole(RoleBean role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserBean [room_name=" + room_name + ", password=" + password
				+ ", mobile=" + mobile + ", description=" + description
				+ ", role=" + role + "]";
	}

	public UserBean(String room_name, String password, String mobile,
			String description, RoleBean role) {
		super();
		this.room_name = room_name;
		this.password = password;
		this.mobile = mobile;
		this.description = description;
		this.role = role;
	}

	public UserBean() {
		super();
	}

}
