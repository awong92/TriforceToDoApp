package edu.gatech.cs2340.triforce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_USERNAME = "user_username";
	public static final String KEY_PASSWORD = "user_pw";
	public static final String KEY_NAME = "user_name";
	public static final String KEY_EMAIL = "user_email";

	private static final String DATABASE_NAME = "UserManagerdb";
	private static final String DATABASE_TABLE = "userTable";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + 
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					KEY_USERNAME + " TEXT NOT NULL, " + 
					KEY_PASSWORD + " TEXT NOT NULL, " +
					KEY_NAME + " TEXT NOT NULL, " + 
					KEY_EMAIL + " TEXT NOT NULL)"
			);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public SQLiteDB(Context c) {
		ourContext = c;
	}
	
	public SQLiteDB open() throws SQLException{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		ourHelper.close();
	}
	
	public long createEntry(String username, String password, String name, String email) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_USERNAME, username);
		cv.put(KEY_PASSWORD, password);
		cv.put(KEY_NAME, name);
		cv.put(KEY_EMAIL, email);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public boolean isValid(String username, String password) {
		String[] columns = new String[] { KEY_ROWID, KEY_USERNAME, KEY_PASSWORD };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);

		int iUsername = c.getColumnIndex(KEY_USERNAME);
		int iPassword = c.getColumnIndex(KEY_PASSWORD);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			if (c.getString(iUsername).equals(username))
				if (c.getString(iPassword).equals(password))
					return true;
		}
		return false;
	}

	public String getData() {
		String[] columns = new String[] { KEY_ROWID, KEY_USERNAME,
				KEY_PASSWORD, KEY_NAME, KEY_EMAIL};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		String result = "";

		int iRow = c.getColumnIndex(KEY_ROWID);
		int iUsername = c.getColumnIndex(KEY_USERNAME);
		int iPassword = c.getColumnIndex(KEY_PASSWORD);
		int iName = c.getColumnIndex(KEY_NAME);
		int iEmail = c.getColumnIndex(KEY_EMAIL);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iRow) + " / "
					+ c.getString(iUsername) + " / " + c.getString(iPassword)
					+ " / " + c.getString(iName) + " / " + c.getString(iEmail)
					+ "\n";
		}
		return result;
	}
	
}
