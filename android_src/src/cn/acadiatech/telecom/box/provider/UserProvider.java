package cn.acadiatech.telecom.box.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import cn.acadiatech.telecom.box.db.DBHelper;

/**
 * User表内容提供者
 * @author QUYONG
 *
 */
public class UserProvider extends ContentProvider {

	public final static String USERAUTHORITY = "cn.acadiatech.telecom.box.provider.userProvider";
	public final static String USERURI = "content://cn.acadiatech.telecom.box.provider.userProvider/user";

	public final static String USERTABLE = "ad_user";
	public final static String[] USERCOLUMNS = { "room_name", "password",
			"mobile", "description", "role_id", "status" };

	public static final String COLUMN_ROOM_NAME = "room_name";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_MOBILE = "mobile";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_ROLE_ID = "role_id";
	public static final String COLUMN_STATUS = "status";

	public static final int TAG_ROOM_NAME = 0;
	public static final int TAG_PASSWORD = 1;
	public static final int TAG_MOBILE = 2;
	public static final int TAG_DESCRIPTION = 3;
	public static final int TAG_ROLE_ID = 4;
	public static final int TAG_STATUS = 5;

	private static final int URL_NO_ID = 1;
	private static final int URL_ID = 2;
	// 先定义能够访问的uri root : content://authority
	private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		matcher.addURI(USERAUTHORITY, "user", URL_NO_ID);
		// content://cn.acadiatech.adclient.provider.userinfoprovider/user
		matcher.addURI(USERAUTHORITY, "user/#", URL_ID);
		// content://cn.acadiatech.adclient.provider.userinfoprovider/user/2
	}

	private SQLiteOpenHelper mOpenHelper;
	private SQLiteDatabase db;

	@Override
	public boolean onCreate() {
		mOpenHelper = DBHelper.getInstance(getContext());
		db = mOpenHelper.getWritableDatabase();
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projectionIn, String selection,
			String[] selectionArgs, String sortOrder) {

		Cursor ret = db.query(USERTABLE, USERCOLUMNS, selection, selectionArgs,
				null, null, null);

		ret.setNotificationUri(getContext().getContentResolver(), uri);
		return ret;
	}

	@Override
	public String getType(Uri uri) {

		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		long id = db.insert(USERTABLE, null, values);
		Uri result_uri = ContentUris.withAppendedId(uri, id);

		getContext().getContentResolver().notifyChange(uri, null);
		return result_uri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		getContext().getContentResolver().notifyChange(uri, null);
		return db.delete(USERTABLE, selection, selectionArgs);

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		getContext().getContentResolver().notifyChange(uri, null);
		return db.update(USERTABLE, values, selection, selectionArgs);
	}

}
