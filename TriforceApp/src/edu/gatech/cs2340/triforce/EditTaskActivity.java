package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Team Triforce (36) Back-end for edit_task_page.xml. Allows users to edit a
 * task
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class EditTaskActivity extends Activity implements OnClickListener {

	Task task;
	EditText nameField, descField, locationField;
	Button mPickDate, mPickTime, saveButton, cancelButton;
	int mYear, mMonth, mDay, mHour, mMinute, typePosition;
	String taskType, hourStr, minuteStr, timeStr, monthStr, dayStr, yearStr;
	int indexOfColon, firstHyphen, secondHyphen;
	Spinner typeSpinner;

	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	/**
	 * Initializes variables and functionality
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_task_page);

		// retrieve task to be edited from db
		SQLiteDB db = new SQLiteDB(this);
		db.open();
		task = db.getTask(ListArrayAdapter.currTaskId, this);
		db.close();

		// capture our View elements
		nameField = (EditText) findViewById(R.id.editTaskNameET);
		descField = (EditText) findViewById(R.id.editTaskDescriptET);
		typeSpinner = (Spinner) findViewById(R.id.newTypeOfTaskET);
		mPickDate = (Button) findViewById(R.id.pickDateET);
		mPickTime = (Button) findViewById(R.id.pickTimeET);
		locationField = (EditText) findViewById(R.id.editLocationET);
		saveButton = (Button) findViewById(R.id.saveButtonET);
		cancelButton = (Button) findViewById(R.id.cancelButtonET);

		// set elements to Task attributes
		nameField.setText(task.getName());
		descField.setText(task.getDescription());
		taskType = task.getType();

		if (taskType.equals("Personal"))
			typePosition = 0;
		else if (taskType.equals("School"))
			typePosition = 1;
		else
			typePosition = 2;

		ArrayAdapter<CharSequence> adapter = ArrayAdapter
				.createFromResource(this, R.array.types_array,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(adapter);
		typeSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		typeSpinner.setSelection(typePosition);

		firstHyphen = task.getDueDate().indexOf("-");
		monthStr = task.getDueDate().substring(0, firstHyphen);
		secondHyphen = task.getDueDate().indexOf("-", firstHyphen + 1);
		dayStr = task.getDueDate().substring(firstHyphen + 1, secondHyphen);
		yearStr = task.getDueDate().substring(secondHyphen + 1);
		mMonth = Integer.parseInt(monthStr) - 1;
		mDay = Integer.parseInt(dayStr);
		mYear = Integer.parseInt(yearStr);

		indexOfColon = task.getDueTime().indexOf(":");
		hourStr = task.getDueTime().substring(0, indexOfColon);
		mHour = Integer.parseInt(hourStr);
		minuteStr = task.getDueTime().substring(indexOfColon + 1);
		mMinute = Integer.parseInt(minuteStr);

		updateDateDisplay();
		updateTimeDisplay();

		// add a click listener to the date and time buttons
		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		mPickTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});

		locationField.setText(task.getLocation());
		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
	}

	/**
	 * Handles when the Save or Cancel button is clicked
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveButtonET:
			String taskName = nameField.getText().toString();
			String taskDesc = descField.getText().toString();
			String dayStr = "";
			if (mDay < 10)
				dayStr = "0" + mDay;
			else
				dayStr = "" + mDay;
			String taskDate = (mMonth + 1) + "-" + dayStr + "-" + mYear;
			String minute = "";
			if (mMinute < 10)
				minute = "0" + mMinute;
			else
				minute = "" + mMinute;
			String taskTime = mHour + ":" + minute;
			String taskLocation = locationField.getText().toString();
			SQLiteDB task = new SQLiteDB(this);
			task.open();
			task.updateTask(ListArrayAdapter.currTaskId, taskName, taskDesc,
					taskType, taskDate, taskTime, taskLocation);
			task.close();
			finish();
			break;
		case R.id.cancelButtonET:
			finish();
			break;
		}
	}

	/**
	 * Class for when clicking the Task Types spinner
	 */
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		/**
		 * Method for changing Task Type to selected spinner at row pos
		 */
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			taskType = parent.getItemAtPosition(pos).toString();
		}

		/**
		 * Empty method for when nothing in the spinner is selected
		 */
		public void onNothingSelected(AdapterView<?> parent) {
			// Do nothing.
		}
	}

	/**
	 * Pops-up the prompt for changing the date or time
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);
		}
		return null;
	}

	/**
	 * Update the button display for date
	 */
	private void updateDateDisplay() {
		mPickDate.setText((mMonth + 1) + "-" + mDay + "-" + mYear);
	}

	/**
	 * The callback received when the user "sets" the date in the dialog
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		/**
		 * Sets the date to the new date values
		 * 
		 * @param view
		 * @param year
		 *            year to be set to
		 * @param monthOfYear
		 *            month to be set to
		 * @param dayOfMonth
		 *            day to be set to
		 */
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
		}
	};

	/**
	 * Updates the time we display in the TextView
	 */
	private void updateTimeDisplay() {
		int hour = (int) mHour;
		String minute = "";
		if (mMinute < 10)
			minute = "0" + mMinute;
		else
			minute = "" + mMinute;
		if (hour > 12) {
			hour -= 12;
			mPickTime.setText(hour + ":" + minute + " PM");
		} else if (hour == 12)
			mPickTime.setText(hour + ":" + minute + " PM");
		else if (hour == 0)
			mPickTime.setText((hour + 12) + ":" + minute + " AM");
		else
			mPickTime.setText(hour + ":" + minute + " AM");
	}

	/**
	 * The callback received when the user "sets" the time in the dialog
	 */
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateTimeDisplay();
		}
	};

}
