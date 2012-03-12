package edu.gatech.cs2340.triforce;

import java.util.Calendar;

import edu.gatech.cs2340.r.R;
import edu.gatech.cs2340.triforce.TaskListActivity.MyOnItemSelectedListener;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class NewTaskActivity extends Activity implements OnClickListener {

	TextView mDateDisplay, mTimeDisplay;
	EditText nameField, descField, locationField;
	Button mPickDate, mPickTime, saveButton, cancelButton;
	int mYear, mMonth, mDay, mHour, mMinute;
	String taskType;
	Spinner typeSpinner;

	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_task_page);

		// capture our View elements
		nameField = (EditText) findViewById(R.id.editTaskName);
		descField = (EditText) findViewById(R.id.editTaskDescript);
		mDateDisplay = (TextView) findViewById(R.id.dateDisplayNT);
		mPickDate = (Button) findViewById(R.id.pickDateNT);
		mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
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

	private void updateDateDisplay() {
		mDateDisplay.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
	}

	// the callback received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
		}
	};

	protected Dialog onCreateDateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	protected Dialog onCreateTimeDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);
		}
		return null;
	}

	// updates the time we display in the TextView
	private void updateTimeDisplay() {
		mTimeDisplay.setText(new StringBuilder().append(pad(mHour)).append(":")
				.append(pad(mMinute)));
	}

	private static String pad(int t) {
		if (t >= 10)
			return String.valueOf(t);
		else
			return "0" + String.valueOf(t);
	}

	// the callback received when the user "sets" the time in the dialog
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateTimeDisplay();
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveButtonNT:
			String taskName = nameField.getText().toString();
			String taskDesc = descField.getText().toString();
			String taskDate = mMonth + "-" + mDay + "-" + mYear;
			String taskTime = mHour + ":" + mMinute;
			String taskLocation = locationField.getText().toString();
			int priority = 0;
			SQLiteDB task = new SQLiteDB(this);
			task.open();
			task.createTaskEntry(TriforceMain.currentUser, taskName, taskDesc, taskType, priority, taskDate, taskTime, taskLocation);
			task.close();
			break;
		case R.id.cancelButtonNT:
			break;
		}
	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			taskType = parent.getItemAtPosition(pos).toString();
		}

		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}
}
