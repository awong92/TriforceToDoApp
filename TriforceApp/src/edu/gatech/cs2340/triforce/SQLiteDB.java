package edu.gatech.cs2340.triforce;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "users_name";
	public static final String KEY_PASSWORD = "users_pw";

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
					KEY_NAME + " TEXT NOT NULL, " + 
					KEY_PASSWORD + " TEXT NOT NULL)"
			);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

	public SQLiteDB(Context c) {
		ourContext = c;
	}

}
