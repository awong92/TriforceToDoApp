package edu.gatech.cs2340.triforce;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import edu.gatech.cs2340.r.R;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Team Triforce (36) Back-end for user_tasklist.xml. Displays new tasks and
 * allows users to add, edit, or delete them
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class TaskListActivity extends ListActivity implements OnClickListener {

	ArrayAdapter<Task> listAdapter;
	ImageButton newTaskButton, editTaskButton, locationsButton;
	Button logoutButton, filterTasksButton;
	String filterBy = "All";
	String oldFilterBy = filterBy;
	List<Task> list;

	final int DATE_ID = 0;
	Button mPickDate;
	int mMonth = 0;
	int mDay = 0;
	int mYear = 0;
	int oldMonth = mMonth;
	int oldDay = mDay;
	int oldYear = mYear;

	String filterChecked = "Both";
	String oldChecked = filterChecked;

	/**
	 * Called when the activity is first created
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_tasklist);

		newTaskButton = (ImageButton) findViewById(R.id.newTaskButton);
		logoutButton = (Button) findViewById(R.id.logoutButton);
		filterTasksButton = (Button) findViewById(R.id.filterTasksButton);
		locationsButton = (ImageButton) findViewById(R.id.showLocationsButton);

		newTaskButton.setOnClickListener(this);
		logoutButton.setOnClickListener(this);
		filterTasksButton.setOnClickListener(this);
		locationsButton.setOnClickListener(this);

		try {
			getModel();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		listAdapter = new ListArrayAdapter(this, list);
		setListAdapter(listAdapter);
	}

	/**
	 * Method for redisplaying the Task List page when returned to
	 */
	public void onRestart() {
		super.onRestart();
		setContentView(R.layout.user_tasklist);

		newTaskButton = (ImageButton) findViewById(R.id.newTaskButton);
		logoutButton = (Button) findViewById(R.id.logoutButton);
		filterTasksButton = (Button) findViewById(R.id.filterTasksButton);

		newTaskButton.setOnClickListener(this);
		logoutButton.setOnClickListener(this);
		filterTasksButton.setOnClickListener(this);

		try {
			getModel();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		listAdapter = new ListArrayAdapter(this, list);
		setListAdapter(listAdapter);
	}

	/**
	 * Handles when the New Task or Logout button is clicked
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.newTaskButton:
			Intent createNewTask = new Intent(
					"edu.gatech.cs2340.triforce.NEWTASKACTIVITY");
			startActivity(createNewTask);
			break;
		case R.id.logoutButton:
			TriforceMain.currentUser = null;
			ListArrayAdapter.currTaskId = -1;
			finish();
			break;
		case R.id.filterTasksButton:
			OpenScreenDialog();
			break;
		case R.id.showLocationsButton:
			Intent showLocations = new Intent("edu.gatech.cs2340.triforce.GMAP");
			startActivity(showLocations);
			break;
		}
	}

	/**
	 * Displays the dialog for filtering tasks
	 */
	private void OpenScreenDialog() {

		// TITLE
		AlertDialog.Builder screenDialog = new AlertDialog.Builder(
				TaskListActivity.this);
		screenDialog.setTitle("FILTER BY...");

		// "Type: "
		TextView textType = new TextView(TaskListActivity.this);
		textType.setText("Type: ");
		LayoutParams textTypeLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textType.setLayoutParams(textTypeLayoutParams);

		// FILTER BY TYPE SPINNER
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this, R.array.filter_array,
						android.R.layout.simple_spinner_item);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner typeSpinner = new Spinner(TaskListActivity.this);
		typeSpinner.setPromptId(R.string.filter_by_prompt);
		typeSpinner.setAdapter(spinnerAdapter);
		typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			// TYPE SPINNER LISTENER TO CHANGE FILTER
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				oldFilterBy = filterBy;
				filterBy = parent.getItemAtPosition(pos).toString();
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// Do nothing.
			}
		});

		// "Date: "
		TextView textDate = new TextView(TaskListActivity.this);
		textDate.setText("Date: ");
		LayoutParams textDateLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textDate.setLayoutParams(textDateLayoutParams);

		// FILTER BY DATE BUTTON
		mYear = 0;
		mMonth = 0;
		mDay = 0;
		mPickDate = new Button(TaskListActivity.this);
		mPickDate.setText("mm-dd-yyyy");

		mPickDate.setOnClickListener(new OnClickListener() {
			// SHOWS DATE DIALOG WHEN CLICKED
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				showDialog(DATE_ID);
			}
		});

		// "Checked: "
		TextView textChecked = new TextView(TaskListActivity.this);
		textChecked.setText("Checked: ");
		LayoutParams textCheckedLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textChecked.setLayoutParams(textCheckedLayoutParams);

		// FILTER BY CHECKED SPINNER
		ArrayAdapter<CharSequence> checkedSpinnerAdapter = ArrayAdapter
				.createFromResource(this, R.array.filter_checks_array,
						android.R.layout.simple_spinner_item);
		checkedSpinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner checkedSpinner = new Spinner(TaskListActivity.this);
		checkedSpinner.setPromptId(R.string.filter_checks_prompt);
		checkedSpinner.setAdapter(checkedSpinnerAdapter);
		checkedSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			// CHECKED SPINNER LISTENER
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				oldChecked = filterChecked;
				filterChecked = parent.getItemAtPosition(pos).toString();
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// Do nothing.
			}
		});

		// SETTING UP THE DIALOG
		LinearLayout dialogLayout = new LinearLayout(TaskListActivity.this);
		dialogLayout.setOrientation(LinearLayout.VERTICAL);
		dialogLayout.addView(textType);
		dialogLayout.addView(typeSpinner);
		dialogLayout.addView(textDate);
		dialogLayout.addView(mPickDate);
		dialogLayout.addView(textChecked);
		dialogLayout.addView(checkedSpinner);
		screenDialog.setView(dialogLayout);

		// OK button changes filtering of tasks
		screenDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					// do something when the button is clicked
					public void onClick(DialogInterface arg0, int arg1) {
						try {
							getModel();
						} catch (ParseException e) {
							e.printStackTrace();
						}
						listAdapter = new ListArrayAdapter(
								TaskListActivity.this, list);
						setListAdapter(listAdapter);
					}
				});
		// Cancel button restores filters as previous
		screenDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						filterBy = oldFilterBy;
						mYear = oldYear;
						mMonth = oldMonth;
						mDay = oldDay;
						filterChecked = oldChecked;
					}
				});
		screenDialog.show();
	}

	/**
	 * Pops-up the prompt for changing the date or time
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_ID:
			Calendar c = Calendar.getInstance();
			return new DatePickerDialog(TaskListActivity.this,
					mDateSetListener, c.get(Calendar.YEAR),
					c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		}
		return null;
	}

	/**
	 * The callback received when the user "sets" the date in the dialog
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			oldYear = mYear;
			oldMonth = mMonth;
			oldDay = mDay;
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
		}
	};

	/**
	 * Update the Date button text in dialog
	 */
	private void updateDateDisplay() {
		mPickDate.setText((mMonth + 1) + "-" + mDay + "-" + mYear);
	}

	/**
	 * Getter for the list of tasks
	 * 
	 * @return List of tasks
	 * @throws ParseException
	 */
	private void getModel() throws ParseException {
		SQLiteDB tasks = new SQLiteDB(this);
		String currentUser = TriforceMain.currentUser;
		String dateStr;
		if (mDay == 0)
			dateStr = "no date filter";
		else
			dateStr = (mMonth + 1) + "-" + mDay + "-" + mYear;
		tasks.open();
		list = tasks.getUserTasks(currentUser, filterBy, dateStr, filterChecked, this);
		tasks.close();
	}
}
