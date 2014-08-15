package cn.acadiatech.telecom.box.db.ex.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import cn.acadiatech.telecom.box.db.ex.DBOperation;
import cn.acadiatech.telecom.box.db.ex.DBProperty;
import cn.acadiatech.telecom.box.db.ex.bean.Role;

/***
* @ClassName: RoleDBAction 
* @Description:(锟斤拷色锟斤拷锟斤拷锟� 
* @author TangYunXiang
* @date 2014-5-13 锟斤拷锟斤拷9:45:13 
* @version 1.0
 */
public class RoleDBAction extends DBOperation {

	public static RoleDBAction instance = null;
	Context context;

	public RoleDBAction(Context context) {
		super(context);
		this.context = context;
	}

	public static synchronized RoleDBAction getInstance(Context context) {
		if (instance == null) {
			instance = new RoleDBAction(context);
		}
		return instance;
	}
	
	public boolean addRole(String description) {
		ContentValues values = new ContentValues();
		values.put("description", description);
		return this.insertTableData(DBProperty.TABLE_AD_ROLE, values);
	}
	
	public boolean deleteRole() {
		return this.deleteTableData(DBProperty.TABLE_AD_ROLE, null, new String[] {});
	}
	public boolean deleteModeById(int mode_id) {
		return this.deleteTableData(DBProperty.TABLE_AD_ROLE, "role_id="+mode_id, new String[] {});
	}
	
	public boolean updateRoleById(int id,String description) {
		ContentValues values = new ContentValues();
		values.put("description", description);
		return this.updateTableData(DBProperty.TABLE_AD_ROLE, "role_id="+id, new String[] {}, values);
	}
	
	public List<Role> getRoleAll() {
		List<Role> list = new ArrayList<Role>();
		String sql = "select * from " + DBProperty.TABLE_AD_ROLE ;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			Role role = new Role();
			role.setRole_id(Integer.parseInt(hashMap.get("role_id")+""));
			role.setDescription(hashMap.get("description")+"");
			list.add(role);
		}
		return list;
	}
	public Role getRoleById(int id) {
		Role role = new Role();
		String sql = "select * from " + DBProperty.TABLE_AD_ROLE +" where role_id="+id;
		List<HashMap<String, Object>> listHashMaps = this.selectRow(sql, null);
		for (HashMap<String, Object> hashMap : listHashMaps) {
			role.setRole_id(Integer.parseInt(hashMap.get("role_id")+""));
			role.setDescription(hashMap.get("description")+"");
		}
		return role;
	}

}
