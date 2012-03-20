package edu.gatech.cs2340.triforce;

import java.util.Calendar;

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
 * Team Triforce (36) Back-end for new_task_page.xml. Allows users to add a new
 * task to the database
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class NewTaskActivity extends Activity implements OnClickListener {

	EditText nameField, descField, locationField;
	Button mPickDate, mPickTime, saveButton, cancelButton;
	int mYear, mMonth, mDay, mHour, mMinute;
	String taskType = "Personal";
	Spinner typeSpinner;

	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	/**
	 * Initializes variables and functionality
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_task_page);

		// capture our View elements
		nameField = (EditText) findViewById(R.id.editTaskName);
		descField = (EditText) findViewById(R.id.editTaskDescript);
		mPickDate = (Button) findViewById(R.id.pickDateNT);
		mPickTime = (Button) findViewById(R.id.pickTime);
		locationField = (EditText) findViewById(R.id.editLocation);
		saveButton = (Button) findViewById(R.id.saveButtonNT);
		cancelButton = (Button) findViewById(R.id.cancelButtonNT);

		// add a click listener to the buttons
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

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// get the current time
		final Calendar t = Calendar.getInstance();
		mHour = t.get(Calendar.HOUR_OF_DAY);
		mMinute = t.get(Calendar.MINUTE);

		// display the current date and time
		updateDateDisplay();
		updateTimeDisplay();

		typeSpinner = (Spinner) findViewById(R.id.newTypeOfTask);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter
				.createFromResource(this, R.array.types_array,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(adapter);

		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		typeSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
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

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
		}
	};

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

	/**
	 * Functionality for clicking either the save or cancel buttons
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveButtonNT:
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
			task.createTaskEntry(TriforceMain.currentUser, taskName, taskDesc,
					taskType, taskDate, taskTime, taskLocation);
			task.close();
			finish();
			break;
		case R.id.cancelButtonNT:
			finish();
			break;
		}
	}

	/**
	 * Class for when clicking the Task Types spinner
	 */
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			taskType = parent.getItemAtPosition(pos).toString();
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// Do nothing.
		}
	}
}
