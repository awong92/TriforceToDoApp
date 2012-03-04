package edu.gatech.cs2340.triforce;

import android.content.ContentValues;
import android.content.Context;
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
					KEY_PASSWORD + " TEXT NOT NULL)" +
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
	
}
