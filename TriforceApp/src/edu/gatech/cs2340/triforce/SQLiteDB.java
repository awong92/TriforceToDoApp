package edu.gatech.cs2340.triforce;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Team Triforce (36) Database storing users and their tasks
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class SQLiteDB {

	// Attributes of User
	public static final String KEY_USERNAME = "user_username";
	public static final String KEY_PASSWORD = "user_pw";
	public static final String KEY_NAME = "user_name";
	public static final String KEY_EMAIL = "user_email";

	// Attributes of Task
	public static final String KEY_TASK_ID = "task_id";
	public static final String KEY_CURRUSER = "task_curruser";
	public static final String KEY_TASKNAME = "task_name";
	public static final String KEY_DESCRIPTION = "task_descript";
	public static final String KEY_TASKTYPE = "task_type";
	public static final String KEY_TASKDATE = "task_date";
	public static final String KEY_TASKTIME = "task_time";
	public static final String KEY_LOCATION = "task_location";
	public static final String KEY_TASKDONE = "task_complete";

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
			db.execSQL("CREATE TABLE " + DATABASE_USERTABLE + " ("
					+ KEY_USERNAME + " TEXT NOT NULL PRIMARY KEY, "
					+ KEY_PASSWORD + " TEXT NOT NULL, " + KEY_NAME
					+ " TEXT NOT NULL, " + KEY_EMAIL + " TEXT NOT NULL);");
			db.execSQL("CREATE TABLE " + DATABASE_TASKTABLE + " ("
					+ KEY_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_CURRUSER + " TEXT NOT NULL, " + KEY_TASKNAME
					+ " TEXT NOT NULL, " + KEY_DESCRIPTION + " TEXT, "
					+ KEY_TASKTYPE + " TEXT NOT NULL, " + KEY_TASKDATE
					+ " TEXT NOT NULL, " + KEY_TASKTIME + " TEXT NOT NULL, "
					+ KEY_LOCATION + " TEXT, " + KEY_TASKDONE
					+ " INTEGER NOT NULL, " + "FOREIGN KEY (" + KEY_CURRUSER
					+ ") REFERENCES " + DATABASE_USERTABLE + " ("
					+ KEY_USERNAME + "));");

		}

		/**
		 * Updates database
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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

	/**
	 * Creates a new task for a user
	 * 
	 * @param username
	 *            User wanting to add a task
	 * @param taskName
	 *            Name of the new task
	 * @param descript
	 *            Description of the new task
	 * @param type
	 *            Type of the new task
	 * @param priority
	 *            Priority of the new task
	 * @param date
	 *            Due Date of the new task
	 * @param time
	 *            Time Due of the new task
	 * @param location
	 *            Location of the new task
	 * @return Insert entry into DATABASE_TASKTABLE
	 */
	public long createTaskEntry(String username, String taskName,
			String descript, String type, String date, String time,
			String location) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_CURRUSER, username);
		cv.put(KEY_TASKNAME, taskName);
		cv.put(KEY_DESCRIPTION, descript);
		cv.put(KEY_TASKTYPE, type);
		cv.put(KEY_TASKDATE, date);
		cv.put(KEY_TASKTIME, time);
		cv.put(KEY_LOCATION, location);
		cv.put(KEY_TASKDONE, 0);
		return ourDatabase.insert(DATABASE_TASKTABLE, null, cv);
	}

	/**
	 * Updating the task by editting its entries
	 * 
	 * @param id
	 *            Id of the task being editted
	 * @param taskName
	 *            New name of the task being editted
	 * @param descript
	 *            New description of the task being editted
	 * @param type
	 *            New type of the task being editted
	 * @param date
	 *            New due date of the task being editted
	 * @param time
	 *            New time due of the task being editted
	 * @param location
	 *            New location of the task being editted
	 */
	public void updateTask(int id, String taskName, String descript,
			String type, int priority, String date, String time, String location) {
		ourDatabase.execSQL("UPDATE " + DATABASE_TASKTABLE
				+ " SET KEY_TASKNAME=" + taskName + " SET KEY_DESCRIPTIOIN="
				+ descript + " SET KEY_TASKTYPE=" + type + " SET KEY_TASKDATE="
				+ date + " SET KEY_TASKTIME=" + time + " SET KEY_LOCATION="
				+ location);
	}

	/**
	 * Deleting an existing task
	 * 
	 * @param id
	 *            Id of the task being deleted
	 */
	public void deleteTask(int id) {
		ourDatabase.execSQL("DELETE FROM " + DATABASE_TASKTABLE + " WHERE "
				+ KEY_TASK_ID + "=" + id);
	}

	/**
	 * Getter of whether a task with a certain id is complete
	 * 
	 * @param id
	 *            Task to check
	 * @return 0 for incomplete, 1 otherwise
	 */
	public int getComplete(int id) {
		int complete = 0;
		String[] columns = new String[] { KEY_TASK_ID, KEY_TASKDONE };
		Cursor c = ourDatabase.query(DATABASE_TASKTABLE, columns, null, null,
				null, null, null);

		int iTaskId = c.getColumnIndex(KEY_TASK_ID);
		int iTaskDone = c.getColumnIndex(KEY_TASKDONE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			if (c.getString(iTaskId).equals(id)) {
				complete = c.getInt(iTaskDone);
			}
		}
		return complete;
	}

	/**
	 * Sets a task with a certain Id to be complete or not
	 * 
	 * @param complete
	 *            0 for incomplete and 1 for complete
	 */
	public void setComplete(int id, int complete) {
		ourDatabase.execSQL("UPDATE " + DATABASE_TASKTABLE + " SET "
				+ KEY_TASKDONE + "=" + complete + " WHERE " + KEY_TASK_ID + "="
				+ id);
	}

	/**
	 * Retrieves all the tasks belonging to the user based on the filter
	 * 
	 * @param user
	 *            User wanting to see its tasks
	 * @param filter
	 *            Tasks to be seen
	 * @return ArrayList of tasks
	 * @throws ParseException
	 */
	public List<Task> getUserTasks(String user, String filterType,
			String filterDate, String filterDone, Context context)
			throws ParseException {
		List<Task> list = new ArrayList<Task>();
		String[] columns = new String[] { KEY_CURRUSER, KEY_TASK_ID,
				KEY_TASKNAME, KEY_TASKTYPE, KEY_TASKDATE, KEY_TASKDONE };
		Cursor c = ourDatabase.query(DATABASE_TASKTABLE, columns, null, null,
				null, null, KEY_TASKDATE + " ASC");

		int iUsername = c.getColumnIndex(KEY_CURRUSER);
		int iTaskId = c.getColumnIndex(KEY_TASK_ID);
		int iTaskName = c.getColumnIndex(KEY_TASKNAME);
		int iTaskType = c.getColumnIndex(KEY_TASKTYPE);
		int iTaskDate = c.getColumnIndex(KEY_TASKDATE);
		int iTaskDone = c.getColumnIndex(KEY_TASKDONE);

		SimpleDateFormat curFormater = new SimpleDateFormat("MM-dd-yyyy");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			// Pull tasks for specific username
			if (c.getString(iUsername).equals(user)) {
				// Check if filter is specific
				if (!(filterType.equals("All"))) {
					// Pull tasks specific to filter
					if (c.getString(iTaskType).equals(filterType)) {
						// Check if filterDate is needed
						if (filterDate.equals("no date filter")) {
							// Check if filterDone is needed
							if (filterDone.equals("Both"))
								list.add(new Task(c.getInt(iTaskId), c
										.getString(iTaskName), c
										.getString(iTaskDate), c
										.getInt(iTaskDone), context));
							// Check if filterDone is for unchecked
							else if (filterDone.equals("Unchecked Items")
									&& c.getInt(iTaskDone) == 0)
								list.add(new Task(c.getInt(iTaskId), c
										.getString(iTaskName), c
										.getString(iTaskDate), c
										.getInt(iTaskDone), context));
							// Check if filterDone is for checked
							else if (filterDone.equals("Checked Items")
									&& c.getInt(iTaskDone) == 1)
								list.add(new Task(c.getInt(iTaskId), c
										.getString(iTaskName), c
										.getString(iTaskDate), c
										.getInt(iTaskDone), context));
						} else { // Pull tasks specific to date
							Date filterDateObj = curFormater.parse(filterDate);
							Date taskDateObj = curFormater.parse(c
									.getString(iTaskDate));
							if (filterDateObj.before(taskDateObj)) {
								// Check if filterDone is needed
								if (filterDone.equals("Both"))
									list.add(new Task(c.getInt(iTaskId), c
											.getString(iTaskName), c
											.getString(iTaskDate), c
											.getInt(iTaskDone), context));
								// Check if filterDone is for unchecked
								else if (filterDone.equals("Unchecked Items")
										&& c.getInt(iTaskDone) == 0)
									list.add(new Task(c.getInt(iTaskId), c
											.getString(iTaskName), c
											.getString(iTaskDate), c
											.getInt(iTaskDone), context));
								// Check if filterDone is for checked
								else if (filterDone.equals("Checked Items")
										&& c.getInt(iTaskDone) == 1)
									list.add(new Task(c.getInt(iTaskId), c
											.getString(iTaskName), c
											.getString(iTaskDate), c
											.getInt(iTaskDone), context));
							}
						}
					}

				} else { // For when filter is set to "All"
					// Check if filterDate is needed
					if (filterDate.equals("no date filter")) {
						// Check if filterDone is needed
						if (filterDone.equals("Both"))
							list.add(new Task(c.getInt(iTaskId), c
									.getString(iTaskName), c
									.getString(iTaskDate), c.getInt(iTaskDone),
									context));
						// Check if filterDone is for unchecked
						else if (filterDone.equals("Unchecked Items")
								&& c.getInt(iTaskDone) == 0)
							list.add(new Task(c.getInt(iTaskId), c
									.getString(iTaskName), c
									.getString(iTaskDate), c.getInt(iTaskDone),
									context));
						// Check if filterDone is for checked
						else if (filterDone.equals("Checked Items")
								&& c.getInt(iTaskDone) == 1)
							list.add(new Task(c.getInt(iTaskId), c
									.getString(iTaskName), c
									.getString(iTaskDate), c.getInt(iTaskDone),
									context));
					} else { // Pull tasks specific to date
						Date filterDateObj = curFormater.parse(filterDate);
						Date taskDateObj = curFormater.parse(c
								.getString(iTaskDate));
						if (filterDateObj.before(taskDateObj)) {
							// Check if filterDone is needed
							if (filterDone.equals("Both"))
								list.add(new Task(c.getInt(iTaskId), c
										.getString(iTaskName), c
										.getString(iTaskDate), c
										.getInt(iTaskDone), context));
							// Check if filterDone is for unchecked
							else if (filterDone.equals("Unchecked Items")
									&& c.getInt(iTaskDone) == 0)
								list.add(new Task(c.getInt(iTaskId), c
										.getString(iTaskName), c
										.getString(iTaskDate), c
										.getInt(iTaskDone), context));
							// Check if filterDone is for checked
							else if (filterDone.equals("Checked Items")
									&& c.getInt(iTaskDone) == 1)
								list.add(new Task(c.getInt(iTaskId), c
										.getString(iTaskName), c
										.getString(iTaskDate), c
										.getInt(iTaskDone), context));
						}
					}
				}
			}
		}

		return list;
	}
}
