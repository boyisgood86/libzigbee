package cn.acadiatech.telecom.box.db.ex;

import java.io.File;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public abstract class MyDatabaseHelper extends SQLiteOpenHelper {
	public static final String TAG = "MyDatabaseHelper";
	Context context; 
	private SQLiteDatabase db = null;
	private final Object dbLock = new Object();
	boolean autoOpenClose = false;
	
	public MyDatabaseHelper(Context context) {
		super(context, DBProperty.DB_NAME, null, 1);
		this.context = context;
		Log.i(TAG, "dbName:" + DBProperty.DB_NAME);
		open();
	}
	
	public abstract void createLocalTable(HashMap<String,String> tableMap);
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}
	public void open() throws SQLiteException {
//		db = context.openOrCreateDatabase(DBProperty.DB_NAME, Context.MODE_APPEND,null);
		try {
            String databaseFilename = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"telecom"+File.separator + DBProperty.DB_NAME;
            db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	public void close() {
		if (null != db) {
			db.close();
			db = null;
		}
	}
	
	public SQLiteDatabase getDB(){
		if(db == null){
			open();
		}
		return db;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

	/**
	 * 执锟斤拷SQL锟斤拷锟�
	 * @param sql
	 */
	public boolean execSQL(String sql){
		boolean isSuccess = false;
		synchronized (dbLock) {
			try {
				if (autoOpenClose){
					open();
				}
				if(db == null){
					open();
				}
				db.execSQL(sql);
				isSuccess = true;
			} catch (Exception e) {
				isSuccess = false;
				Log.e(TAG + ",execSQL:",  e==null?"":e.getMessage());
			} finally {
				if (autoOpenClose){
					close();
				}
			}
		}
		return isSuccess;
	}
	
	/**
	 * 锟斤拷莶锟斤拷氲斤拷锟捷匡拷
	 * @param table
	 * @param nullColumnHack
	 * @param values
	 * @return
	 */
	public long insert(String table, String nullColumnHack, ContentValues values) {
		synchronized (dbLock) {
			long id = -1;
			try {
				if (autoOpenClose){
					open();
				}
				if(db == null){
					open();
				}
				id = db.insertOrThrow(table, nullColumnHack, values);
			} catch (Exception e) {
				Log.e(TAG + ",insert:",  e==null?"":e.getMessage());
			} finally {
				if (autoOpenClose){
					close();
				}
			}
			return id;
		}
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟缴撅拷锟斤拷锟捷匡拷锟斤拷锟斤拷
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int delete(String table, String whereClause, String[] whereArgs) {
		synchronized (dbLock) {
			int rows = 0;
			try {
				if (autoOpenClose){
					open();
				}
				if(db == null){
					open();
				}
				rows = db.delete(table, whereClause, whereArgs);
			} catch (Exception e) {
				Log.e(TAG + ",delete:",  e==null?"":e.getMessage());
			} finally {
				if (autoOpenClose){
					close();
				}
			}
			return rows;
		}
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷菘锟斤拷锟斤拷锟�
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int update(String table, ContentValues values, String whereClause,
			String[] whereArgs) {
		synchronized (dbLock) {
			int rows = 0;
			try {
				if (autoOpenClose){
					open();
				}
				if(db == null){
					open();
				}
				rows = db.update(table, values, whereClause, whereArgs);
			} catch (Exception e) {
				Log.e(TAG + ",update:",  e==null?"":e.getMessage());
			} finally {
				if (autoOpenClose){
					close();
				}
			}
			return rows;
		}
	}

	/**
	 * 锟斤拷莶锟窖拷锟斤拷锟絊elect锟斤拷锟斤拷锟斤拷息
	 * @param table
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {

		synchronized (dbLock) {
			Cursor cur = null;
			try {
				if (autoOpenClose){
					open();
				}
				if(db == null){
					open();
				}
				cur = db.query(table, columns, selection, selectionArgs,
						groupBy, having, orderBy);
			} catch (Exception e) {
				Log.e(TAG + ",query:", e==null?"":e.getMessage());
			} finally {
				if (autoOpenClose){
					close();
				}
			}
			return cur;
		}
	}

	/**
	 * 锟斤拷锟絊ql select锟斤拷锟斤拷锟�
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public Cursor rawQuery(String sql, String[] selectionArgs) {
		synchronized (dbLock) {
			Cursor cur = null;
			try {
				if (autoOpenClose){
					open();
				}
				if(db == null){
					open();
				}
				cur = db.rawQuery(sql, selectionArgs);
			} catch (Exception e) {
				Log.e(TAG + ",rawQuery:",  e==null?"":e.getMessage());
			} finally {
				if (autoOpenClose){
					close();
				}
			}
			return cur;
		}
	}

	
}
