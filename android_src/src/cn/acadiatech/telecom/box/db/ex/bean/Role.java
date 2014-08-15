package cn.acadiatech.telecom.box.db.ex.bean;

public class Role {
	private int role_id;
	private String description;
	
	public Role() {
	}

	public Role(int role_id, String description) {
		super();
		this.role_id = role_id;
		this.description = description;
	}

	/** 
	 * @return role_id 
	 */
	public int getRole_id() {
		return role_id;
	}

	/**
	 * @param role_id the role_id to set
	 */
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	/** 
	 * @return description 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
}
