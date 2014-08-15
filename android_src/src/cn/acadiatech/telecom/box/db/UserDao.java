package cn.acadiatech.telecom.box.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import cn.acadiatech.telecom.box.beans.RoleBean;
import cn.acadiatech.telecom.box.beans.UserBean;
import cn.acadiatech.telecom.box.provider.UserProvider;

/**
 * 用户表操作Dao
 * 
 * @author QUYONG
 * 
 */
public class UserDao {

	public SQLiteOpenHelper helper;
	private static UserDao instance;
	private Context context;

	public UserDao(Context context) {
		this.helper = DBHelper.getInstance(context);
		this.context = context;
	}

	public static UserDao getInstance(Context context) {
		if (instance == null) {
			instance = new UserDao(context);
		}
		return instance;
	}

	/**
	 * 获得所有用户记录
	 */
	public synchronized List<UserBean> queryUsers() {
		String uri = UserProvider.USERURI;
		List<UserBean> userList = new ArrayList<UserBean>();
		ContentResolver cr = context.getContentResolver();

		Cursor cursor = cr.query(Uri.parse(uri), UserProvider.USERCOLUMNS,
				null, null, null);
		if (cursor != null) {

			while (cursor.moveToNext()) {
				UserBean userBean = new UserBean();
				userBean.setRoom_name(cursor
						.getString(UserProvider.TAG_ROOM_NAME));
				userBean.setPassword(cursor
						.getString(UserProvider.TAG_PASSWORD));
				userBean.setMobile(cursor.getString(UserProvider.TAG_MOBILE));
				userBean.setDescription(cursor
						.getString(UserProvider.TAG_DESCRIPTION));
				Integer role_id = cursor.getInt(UserProvider.TAG_ROLE_ID);
				RoleDao roleDao = RoleDao.getInstance(context);
				RoleBean role = roleDao.getRole(role_id);
				userBean.setRole(role);

				userList.add(userBean);

			}

			cursor.close();
		}

		return userList;
	}

	/**
	 * 插入用户数据
	 * 
	 * @param userBean
	 */
	public synchronized void insert(UserBean userBean) {
		String uri = UserProvider.USERURI;
		ContentResolver cr = context.getContentResolver();

		ContentValues values = new ContentValues();
		values.put(UserProvider.COLUMN_ROOM_NAME, userBean.getRoom_name());
		values.put(UserProvider.COLUMN_PASSWORD, userBean.getPassword());
		values.put(UserProvider.COLUMN_MOBILE, userBean.getMobile());
		values.put(UserProvider.COLUMN_DESCRIPTION, userBean.getDescription());
		values.put(UserProvider.COLUMN_ROLE_ID, userBean.getRole().getRole_id());

		cr.insert(Uri.parse(uri), values);

	}

	/**
	 * 更新用户数据
	 * 
	 * @param userBean
	 */
	public synchronized void update(UserBean userBean) {
		String uri = UserProvider.USERURI;
		ContentResolver cr = context.getContentResolver();

		ContentValues values = new ContentValues();
		values.put(UserProvider.COLUMN_ROOM_NAME, userBean.getRoom_name());
		values.put(UserProvider.COLUMN_PASSWORD, userBean.getPassword());
		values.put(UserProvider.COLUMN_MOBILE, userBean.getMobile());
		values.put(UserProvider.COLUMN_DESCRIPTION, userBean.getDescription());
		values.put(UserProvider.COLUMN_ROLE_ID, userBean.getRole().getRole_id());

		String where = "room_name = ?";
		String[] selectionArgs = new String[] { userBean.getRoom_name() };
		cr.update(Uri.parse(uri), values, where, selectionArgs);

	}

	/**
	 * 查询登陆用户是否存在
	 * 
	 * @param room_name
	 *            登陆用户名
	 * @param password
	 *            登陆密码
	 * @return
	 */
	public synchronized boolean getUser(String room_name, String password) {
		String uri = UserProvider.USERURI;
		ContentResolver cr = context.getContentResolver();

		String selection = "room_name = ? AND password = ?";
		String[] selectionArgs = new String[] { room_name, password };

		Cursor cursor = cr.query(Uri.parse(uri), UserProvider.USERCOLUMNS,
				selection, selectionArgs, null);

		if (cursor != null) {

			int count = cursor.getCount();

			if (count != 0) {
				cursor.close();
				return true;
			}

		}

		return false;
	}

	/**
	 * 通过User删除用户
	 * @param user
	 * user
	 */
	public synchronized void delete(UserBean user) {
		String uri = UserProvider.USERURI;
		ContentResolver cr = context.getContentResolver();

		String where = "room_name = ?";
		String[] selectionArgs = new String[] { user.getRoom_name() };

		cr.delete(Uri.parse(uri), where, selectionArgs);
	}
	
	/**
	 * 通过角色删除
	 * @param role
	 */
	public synchronized void delete(RoleBean role){
		String uri = UserProvider.USERURI;
		ContentResolver cr = context.getContentResolver();

		String where = "role_id == ? ";
		String[] selectionArgs = new String[] { role.getRole_id()+"" };

		cr.delete(Uri.parse(uri), where, selectionArgs);
	}
}
