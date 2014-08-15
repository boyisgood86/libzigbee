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

public class RoleProvider extends ContentProvider {

	public final static String ROLEAUTHORITY = "cn.acadiatech.telecom.box.provider.roleProvider";
	public final static String ROLEURI = "content://cn.acadiatech.telecom.box.provider.roleProvider/role";

	public final static String ROLETABLE = "ad_role";
	public final static String[] ROLECOLUMNS = { "role_id", "description"};
	
	public final static String COLUMN_ROLE_ID = "role_id";
	public final static String COLUMN_DESCRIPTION = "description";

	public static final int TAG_ROLE_ID = 0;
	public static final int TAG_DESCRIPTION = 1;
	
	private static final int URL_NO_ID = 1;
	private static final int URL_ID = 2;
	// 先定义能够访问的uri root : content://authority
	private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		matcher.addURI(ROLEAUTHORITY, "role", URL_NO_ID);
		// content://cn.acadiatech.adclient.provider.userinfoprovider/user
		matcher.addURI(ROLEAUTHORITY, "role/#", URL_ID);
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

		Cursor ret = db.query(ROLETABLE, ROLECOLUMNS, selection, selectionArgs,
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
		long id = db.insert(ROLETABLE, null, values);
		Uri result_uri = ContentUris.withAppendedId(uri, id);

		getContext().getContentResolver().notifyChange(uri, null);
		return result_uri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		getContext().getContentResolver().notifyChange(uri, null);
		return db.delete(ROLETABLE, selection, selectionArgs);

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		getContext().getContentResolver().notifyChange(uri, null);
		return db.update(ROLETABLE, values, selection, selectionArgs);
	}

}
