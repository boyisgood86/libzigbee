package cn.acadiatech.telecom.box.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import cn.acadiatech.telecom.box.beans.RoleBean;
import cn.acadiatech.telecom.box.provider.RoleProvider;

/**
 * 角色表操作Dao
 * 
 * @author QUYONG
 * 
 */
public class RoleDao {

	public SQLiteOpenHelper helper;
	private static RoleDao instance;
	private Context context;

	public RoleDao(Context context) {
		this.helper = DBHelper.getInstance(context);
		this.context = context;
	}

	public static RoleDao getInstance(Context context) {
		if (instance == null) {
			instance = new RoleDao(context);
		}
		return instance;
	}

	/**
	 * 查询角色
	 * 
	 * @param role_id
	 * @return
	 */
	public synchronized RoleBean getRole(int role_id) {

		String uri = RoleProvider.ROLEURI;
		ContentResolver cr = context.getContentResolver();
		RoleBean role = new RoleBean();

		String selection = "role_id == ?";

		String[] selectionArgs = new String[] { role_id + "" };

		Cursor cursor = cr.query(Uri.parse(uri), RoleProvider.ROLECOLUMNS,
				selection, selectionArgs, null);
		if (cursor != null) {

			while (cursor.moveToNext()) {

				role.setRole_id(role_id);
				role.setDescription(cursor
						.getString(RoleProvider.TAG_DESCRIPTION));

				break;

			}

		}
		cursor.close();
		return role;

	}

	/**
	 * 插入角色数据
	 * 
	 * @param role
	 */
	public synchronized void insert(RoleBean role) {

		String uri = RoleProvider.ROLEURI;
		ContentResolver cr = context.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(RoleProvider.COLUMN_ROLE_ID, role.getRole_id());
		values.put(RoleProvider.COLUMN_DESCRIPTION, role.getDescription());

		cr.insert(Uri.parse(uri), values);

	}

	/**
	 * 更新角色数据
	 * 
	 * @param role
	 */
	public synchronized void update(RoleBean role) {
		String uri = RoleProvider.ROLEURI;
		ContentResolver cr = context.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(RoleProvider.COLUMN_ROLE_ID, role.getRole_id());
		values.put(RoleProvider.COLUMN_DESCRIPTION, role.getDescription());

		String selection = "role_id == ?";

		String[] selectionArgs = new String[] { role.getRole_id() + "" };

		cr.update(Uri.parse(uri), values, selection, selectionArgs);
	}

	/**
	 * 删除角色
	 * @param role
	 */
	public synchronized void delete(RoleBean role) {
		String uri = RoleProvider.ROLEURI;
		ContentResolver cr = context.getContentResolver();

		String where = "role_id == ?";

		String[] selectionArgs = new String[] { role.getRole_id() + "" };
		
		UserDao userDao = UserDao.getInstance(context);
		userDao.delete(role);
		cr.delete(Uri.parse(uri), where, selectionArgs);
	}

}
