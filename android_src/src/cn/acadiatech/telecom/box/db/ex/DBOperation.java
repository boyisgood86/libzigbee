package cn.acadiatech.telecom.box.db.ex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBOperation extends MyDatabaseHelper {
	private static DBOperation instance;

	public DBOperation(Context context) {
		super(context);
	}

	public static DBOperation getInstance(Context context) {
		if (null == instance) {
			instance = new DBOperation(context);
		}
		return instance;
	}
	
	public static void clearInstance() {
		instance=null;
	}
	

	public void initInstance() {
		instance = null;
	}

	@Override
	public void createLocalTable(HashMap<String, String> tableHashMap) {
		Iterator<Entry<String, String>> iter = tableHashMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
//			String key = entry.getKey();
			String table = entry.getValue().toString();
			this.execSQL(table);
		}
	}

	/**
	 * 锟斤拷询锟斤拷洌拷锟斤拷丶锟斤拷锟�
	 * 
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public synchronized ArrayList<HashMap<String, Object>> selectRow(String sql, String[] selectionArgs) {
		ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		Cursor c = null;
		try {
			c = this.rawQuery(sql, selectionArgs);
			if (c != null) {
				for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					for (int i = 0; i < c.getColumnCount(); i++) {
						map.put(c.getColumnName(i), c.getString(i));
					}
					result.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	/**
	 * 锟斤拷询锟斤拷锟叫硷拷录
	 * 
	 * @param tableName
	 *            锟斤拷锟斤拷
	 * @param fieldList
	 *            锟街讹拷锟叫�使锟矫讹拷锟脚分革拷
	 * @param orderBy
	 *            锟斤拷锟斤拷锟街讹拷
	 * @return 锟斤拷锟叫憋拷锟铰�
	 */
	@SuppressWarnings("unchecked")
	public synchronized ArrayList<HashMap<String, Object>> selectAllRow(String tableName, String fieldList, String orderBy) {
		ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		Cursor cur = null;
		String[] arrFieldList = null;
		if (fieldList != "*" && fieldList.length() > 0)
			arrFieldList = fieldList.split(",");

		try {
			cur = this.query(tableName, arrFieldList, null, null, null, null, orderBy);
			HashMap<String, Object> map = new HashMap<String, Object>();
			cur.moveToFirst();
			while (!cur.isAfterLast()) {
				for (int i = 0; i < cur.getColumnCount(); i++)
					map.put(cur.getColumnName(i), cur.getString(i));
				result.add((HashMap<String, Object>) map.clone());
				cur.moveToNext();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
		}

		return result;
	}

	/**
	 * 锟叫讹拷锟角凤拷锟窖拷锟斤拷锟斤拷
	 * 
	 * @param sql
	 * @return
	 */
	public synchronized boolean isExists(String sql) {
		boolean result = false;
		Cursor c = null;
		try {
			c = this.rawQuery(sql, null);
			if (null != c) {
				if (c.moveToNext()) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	/**
	 * 锟斤拷莶锟斤拷锟斤拷锟斤拷
	 * 
	 * @param tableName
	 * @param values
	 * @return
	 */
	public synchronized boolean insertTableData(String tableName, ContentValues values) {
		boolean bool = false;
		long result = this.insert(tableName, null, values);
		if (result > 0) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 锟斤拷莞锟斤拷虏锟斤拷锟�
	 * 
	 * @param tableName
	 * @param key
	 * @param keyValue
	 * @param values
	 * @return
	 */
	public synchronized boolean updateTableData(String tableName, String whereClause, String[] whereArgs, ContentValues values) {
		boolean bool = false;
		int result = this.update(tableName, values, whereClause, whereArgs);
		if (result > 0) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 锟斤拷锟缴撅拷锟斤拷锟斤拷
	 * 
	 * @param id
	 * @return
	 */
	public synchronized boolean deleteTableData(String tableName, String whereClause, String[] whereArgs) {
		boolean bool = false;
		int result = this.delete(tableName, whereClause, whereArgs);
		if (result > 0) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 锟叫断该憋拷锟角凤拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @param tableName
	 * @param primaryKey
	 * @param primaryValue
	 * @return
	 */
	@SuppressWarnings("unused")
	public synchronized boolean isExist(String tableName, String primaryKey, String primaryValue) {
		boolean isExist = false;
		String sql = " select * from " + tableName + " where " + primaryKey + " = " + primaryValue;
		Cursor c = null;
		try {
			c = this.rawQuery(sql, null);
			if (c != null) {
				for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
					isExist = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return isExist;
	}
}
