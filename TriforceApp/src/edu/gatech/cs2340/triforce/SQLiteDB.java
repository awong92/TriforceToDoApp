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
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER AUTOINCREMENT, " + KEY_USERNAME
					+ " TEXT NOT NULL PRIMARY KEY, " + KEY_PASSWORD + " TEXT NOT NULL, "
					+ KEY_NAME + " TEXT NOT NULL, " + KEY_EMAIL
					+ " TEXT NOT NULL)");

		}

		/**
		 * Updates database
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
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
	public long createEntry(String username, String password, String name,
			String email) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_USERNAME, username);
		cv.put(KEY_PASSWORD, password);
		cv.put(KEY_NAME, name);
		cv.put(KEY_EMAIL, email);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	/**
	 * Checks if a username is already taken or not
	 * 
	 * @param username
	 *            new username to be checked against the db
	 * @return true if available, false otherwise
	 */
	public boolean isAvailable(String username) {
		String[] columns = new String[] { KEY_USERNAME };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);

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

	/**
	 * Getter for a table view of all the data in the db
	 * 
	 * @return a String/table of all the data in the db
	 */
	public String getData() {
		String[] columns = new String[] { KEY_ROWID, KEY_USERNAME,
				KEY_PASSWORD, KEY_NAME, KEY_EMAIL };
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
