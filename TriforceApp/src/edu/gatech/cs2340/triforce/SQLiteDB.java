package edu.gatech.cs2340.triforce;

/**
 * Team Triforce (36)
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB {

	// Attributes of User
	public static final String KEY_USERNAME = "user_username";
	public static final String KEY_PASSWORD = "user_pw";
	public static final String KEY_NAME = "user_name";
	public static final String KEY_EMAIL = "user_email";

	// Attributes of Task
	public static final String KEY_TASK_ID = "task_id";
	public static final String KEY_TASKNAME = "task_name";
	public static final String KEY_DESCRIPTION = "task_descript";
	public static final String KEY_TASKTYPE = "task_type";
	public static final String KEY_PRIORITY = "task_priority";
	public static final String KEY_TASKDATE = "task_date";
	public static final String KEY_TASKTIME = "task_time";
	public static final String KEY_LOCATION = "task_location";

	// DB and Table Names
	private static final String DATABASE_NAME = "UserManagerdb";
	private static final String DATABASE_USERTABLE = "userTable";
	private static final String DATABASE_TASKTABLE = "taskTable";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		/**
		 * Constructor for Helper
		 * 
		 * @param context
		 */
		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		/**
		 * Initialize Activity
		 * 
		 * @param db Database of where to store data
		 */
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_USERTABLE + " ("
					+ KEY_USERNAME + " TEXT NOT NULL PRIMARY KEY, "
					+ KEY_PASSWORD + " TEXT NOT NULL, " + KEY_NAME
					+ " TEXT NOT NULL, " + KEY_EMAIL + " TEXT NOT NULL)");
			db.execSQL("CREATE TABLE "
					+ DATABASE_TASKTABLE
					+ " ("
					+ KEY_TASK_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_USERNAME
					+ " TEXT NOT NULL FOREIGN KEY REFERENCES DATABASE_USERTABLE (KEY_USERNAME), "
					+ KEY_TASKNAME + " TEXT NOT NULL, " + KEY_DESCRIPTION
					+ " TEXT, " + KEY_TASKTYPE + " TEXT NOT NULL, "
					+ KEY_PRIORITY + " INTEGER NOT NULL, " + KEY_TASKDATE
					+ " TEXT NOT NULL, " + KEY_TASKTIME + " TEXT NOT NULL, "
					+ KEY_LOCATION + " TEXT NOT NULL)");

		}

		/**
		 * Updates database
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_USERTABLE);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TASKTABLE);
			onCreate(db);
		}

	}

	/**
	 * Constructor for database
	 * 
	 * @param c
	 */
	public SQLiteDB(Context c) {
		ourContext = c;
	}

	/**
	 * Make database writable
	 * 
	 * @return database
	 * @throws SQLException
	 */
	public SQLiteDB open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	/**
	 * Close database
	 */
	public void close() {
		ourHelper.close();
	}

	/**
	 * Creates a new entry/row in the database
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @param email
	 * @return new row of data in DATABASE_TABLE
	 */
	public long createUserEntry(String username, String password, String name,
			String email) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_USERNAME, username);
		cv.put(KEY_PASSWORD, password);
		cv.put(KEY_NAME, name);
		cv.put(KEY_EMAIL, email);
		return ourDatabase.insert(DATABASE_USERTABLE, null, cv);
	}

	/**
	 * Checks if a username is already taken or not
	 * 
	 * @param username
	 *            new username to be checked against the db
	 * @return true if available, false otherwise
	 */
	public boolean isUserAvailable(String username) {
		String[] columns = new String[] { KEY_USERNAME };
		Cursor c = ourDatabase.query(DATABASE_USERTABLE, columns, null, null,
				null, null, null);

		int iUsername = c.getColumnIndex(KEY_USERNAME);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			if (c.getString(iUsername).equals(username))
				return false;
		}
		return true;
	}

	/**
	 * Checks if the input username is available and its password matches
	 * 
	 * @param username
	 * @param password
	 * @return true if the username exists and the password belongs, false
	 *         otherwise
	 */
	public boolean isValidUser(String username, String password) {
		String[] columns = new String[] { KEY_USERNAME, KEY_PASSWORD };
		Cursor c = ourDatabase.query(DATABASE_USERTABLE, columns, null, null,
				null, null, null);

		int iUsername = c.getColumnIndex(KEY_USERNAME);
		int iPassword = c.getColumnIndex(KEY_PASSWORD);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			if (c.getString(iUsername).equals(username))
				if (c.getString(iPassword).equals(password))
					return true;
		}
		return false;
	}

	/**
	 * Getter for a table view of all the data in the db
	 * 
	 * @return a String/table of all the data in the db
	 */
	public String getUserData() {
		String[] columns = new String[] { KEY_USERNAME, KEY_PASSWORD, KEY_NAME,
				KEY_EMAIL };
		Cursor c = ourDatabase.query(DATABASE_USERTABLE, columns, null, null,
				null, null, null);
		String result = "";

		int iUsername = c.getColumnIndex(KEY_USERNAME);
		int iPassword = c.getColumnIndex(KEY_PASSWORD);
		int iName = c.getColumnIndex(KEY_NAME);
		int iEmail = c.getColumnIndex(KEY_EMAIL);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iUsername) + " / "
					+ c.getString(iPassword) + " / " + c.getString(iName)
					+ " / " + c.getString(iEmail) + "\n";
		}
		return result;
	}

	public long createTaskEntry(String username, String taskName,
			String descript, String type, int priority, String date,
			String time, String location) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_USERNAME, username);
		cv.put(KEY_TASKNAME, taskName);
		cv.put(KEY_DESCRIPTION, descript);
		cv.put(KEY_TASKTYPE, type);
		cv.put(KEY_PRIORITY, priority);
		cv.put(KEY_TASKDATE, date);
		cv.put(KEY_TASKTIME, time);
		cv.put(KEY_LOCATION, location);
		return ourDatabase.insert(DATABASE_TASKTABLE, null, cv);
	}

	public void updateTask(int id, String taskName, String descript,
			String type, int priority, String date, String time, String location) {
		ourDatabase.execSQL("UPDATE " + DATABASE_TASKTABLE
				+ " SET KEY_TASKNAME=" + taskName + " SET KEY_DESCRIPTIOIN="
				+ descript + " SET KEY_TASKTYPE=" + type + " SET KEY_PRIORITY="
				+ priority + " SET KEY_TASKDATE=" + date + " SET KEY_TASKTIME="
				+ time + " SET KEY_LOCATION=" + location);
	}

	public void deleteTask(int id) {
		ourDatabase.execSQL("DELETE FROM " + DATABASE_TASKTABLE + " WHERE "
				+ KEY_TASK_ID + "=" + id);
	}
}
