package edu.gatech.cs2340.triforce;

import java.util.List;

import edu.gatech.cs2340.r.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

/**
 * Team Triforce (36) Back-end for user_tasklist.xml. Displays new tasks and
 * allows users to add, edit, or delete them
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class TaskListActivity extends ListActivity implements OnClickListener {

	ArrayAdapter<Task> listAdapter;
	ImageButton newTaskButton, editTaskButton;
	Button logoutButton, filterTasksButton;
	String filterBy = "All";
	Spinner typeSpinner;
	List<Task> list;

	AlertDialog.Builder builder;
	AlertDialog alertDialog;

	Context mContext;
	LayoutInflater inflater;
	View layout;

	/**
	 * Called when the activity is first created
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_tasklist);

		mContext = getApplicationContext();
		inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.filter_dialog,
				(ViewGroup) findViewById(R.id.layout_filter_dialog));

		typeSpinner = (Spinner) findViewById(R.id.typeOfTask);
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this, R.array.filter_array,
						android.R.layout.simple_spinner_item);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(spinnerAdapter);

		newTaskButton = (ImageButton) findViewById(R.id.newTaskButton);
		logoutButton = (Button) findViewById(R.id.logoutButton);
		filterTasksButton = (Button) findViewById(R.id.filterTasksButton);

		typeSpinner.setOnItemSelectedListener(new TypeSelectedListener());
		newTaskButton.setOnClickListener(this);
		logoutButton.setOnClickListener(this);
		filterTasksButton.setOnClickListener(this);

		builder = new AlertDialog.Builder(mContext); 
		builder.setView(layout);
		alertDialog = builder.create();
		
		getModel();
		listAdapter = new ListArrayAdapter(this, list);
		setListAdapter(listAdapter);
	}

	public void onRestart() {
		super.onRestart();
		setContentView(R.layout.user_tasklist);

		typeSpinner = (Spinner) findViewById(R.id.typeOfTask);
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this, R.array.filter_array,
						android.R.layout.simple_spinner_item);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(spinnerAdapter);

		newTaskButton = (ImageButton) findViewById(R.id.newTaskButton);
		logoutButton = (Button) findViewById(R.id.logoutButton);

		typeSpinner.setOnItemSelectedListener(new TypeSelectedListener());
		newTaskButton.setOnClickListener(this);
		logoutButton.setOnClickListener(this);

		getModel();
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
			finish();
			break;
		case R.id.filterTasksButton:
			alertDialog.show();
			break;
		}
	}

	/**
	 * Listener for selecting Task Type
	 */
	public class TypeSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			filterBy = parent.getItemAtPosition(pos).toString();
			getModel();
			listAdapter = new ListArrayAdapter(TaskListActivity.this, list);
			setListAdapter(listAdapter);
		}

		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}

	/**
	 * Getter for the list of tasks
	 * 
	 * @return List of tasks
	 */
	private void getModel() {
		SQLiteDB tasks = new SQLiteDB(this);
		String currentUser = TriforceMain.currentUser;
		tasks.open();
		list = tasks.getUserTasks(currentUser, filterBy, this);
		tasks.close();
	}
}
