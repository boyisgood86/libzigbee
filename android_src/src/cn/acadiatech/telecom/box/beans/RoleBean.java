package cn.acadiatech.telecom.box.beans;

/**
 * 角色Bean
 * 
 * @author QUYONG
 * 
 */
public class RoleBean {

	private Integer role_id;
	private String description;

	public RoleBean() {
		super();
	}

	public RoleBean(int role_id, String description) {
		this.role_id = role_id;
		this.description = description;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [role_id=" + role_id + ", description=" + description
				+ "]";
	}
	
	
	

}
