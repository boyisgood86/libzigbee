package cn.acadiatech.telecom.box.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * 数据库帮助表
 * 
 * @author QUYONG
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "TELECOM_BOX";
	private static final int DB_VERSION = 1;
	private static SQLiteOpenHelper mInstance;

	public static final String TABLE_AD_USER = "ad_user";

	public static final String TABLE_AD_ROLE = "ad_role";

	// Role角色
	private static final String TABLE_AD_ROLE_DB_SQL = "CREATE TABLE if not exists "
			+ TABLE_AD_ROLE
			+ " ("
			+ " role_id INTEGER PRIMARY KEY autoincrement, "
			+ "description text not null)";

	// 用户表
	private static final String TABLE_AD_USER_DB_SQL = "CREATE TABLE if not exists "
			+ TABLE_AD_USER
			+ " ("
			+ "room_name text null,"
			+ "password text null,"
			+ "mobile text null,"
			+ "description text null," + "role_id INTEGER not null," // role外键
			+ "status INTEGER null)";

	private DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, factory, DB_VERSION);
	}

	public synchronized static SQLiteOpenHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DBHelper(context, DB_NAME, null, DB_VERSION);
		}
		return mInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(TABLE_AD_ROLE_DB_SQL);
		db.execSQL(TABLE_AD_USER_DB_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	// private static SQLiteDatabase database;
	// public static final String DATABASE_FILENAME = "city.db"; // 这个是DB文件名字
	// public static final String PACKAGE_NAME = "com.diz.db"; // 这个是自己项目包路径
	// public static final String DATABASE_PATH = "/data"
	// + Environment.getDataDirectory().getAbsolutePath() + "/"
	// + PACKAGE_NAME; // 获取存储位置地址
	//
	// public static SQLiteDatabase openDatabase(Context context) {
	// try {
	// String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
	// File dir = new File(DATABASE_PATH);
	// if (!dir.exists()) {
	// dir.mkdir();
	// }
	// if (!(new File(databaseFilename)).exists()) {
	// InputStream is = context.getResources().openRawResource(
	// R.raw.city);
	// FileOutputStream fos = new FileOutputStream(databaseFilename);
	// byte[] buffer = new byte[8192];
	// int count = 0;
	// while ((count = is.read(buffer)) > 0) {
	// fos.write(buffer, 0, count);
	// }
	//
	// fos.close();
	// is.close();
	// }
	// database = SQLiteDatabase.openOrCreateDatabase(databaseFilename,
	// null);
	// return database;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

}
