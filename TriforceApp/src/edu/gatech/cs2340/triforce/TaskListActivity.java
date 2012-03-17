package edu.gatech.cs2340.triforce;

import java.util.List;

import edu.gatech.cs2340.r.R;
import android.app.AlertDialog;
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
	ImageButton newTaskButton, editTaskButton;
	Button logoutButton, filterTasksButton;
	String filterBy = "All";
	List<Task> list;

	/**
	 * Called when the activity is first created
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_tasklist);

		newTaskButton = (ImageButton) findViewById(R.id.newTaskButton);
		logoutButton = (Button) findViewById(R.id.logoutButton);
		filterTasksButton = (Button) findViewById(R.id.filterTasksButton);

		newTaskButton.setOnClickListener(this);
		logoutButton.setOnClickListener(this);
		filterTasksButton.setOnClickListener(this);

		getModel();
		listAdapter = new ListArrayAdapter(this, list);
		setListAdapter(listAdapter);
	}

	public void onRestart() {
		super.onRestart();
		setContentView(R.layout.user_tasklist);

		newTaskButton = (ImageButton) findViewById(R.id.newTaskButton);
		logoutButton = (Button) findViewById(R.id.logoutButton);
		filterTasksButton = (Button) findViewById(R.id.filterTasksButton);

		newTaskButton.setOnClickListener(this);
		logoutButton.setOnClickListener(this);
		filterTasksButton.setOnClickListener(this);

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
			OpenScreenDialog();
			break;
		}
	}

	private void OpenScreenDialog() {

		AlertDialog.Builder screenDialog = new AlertDialog.Builder(
				TaskListActivity.this);
		screenDialog.setTitle("FILTER BY...");

		TextView TextOut = new TextView(TaskListActivity.this);
		TextOut.setText("Type: ");
		LayoutParams textOutLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		TextOut.setLayoutParams(textOutLayoutParams);

		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this, R.array.filter_array,
						android.R.layout.simple_spinner_item);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner typeSpinner = new Spinner(TaskListActivity.this);
		typeSpinner.setAdapter(spinnerAdapter);
		typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				filterBy = parent.getItemAtPosition(pos).toString();
			}

			public void onNothingSelected(AdapterView parent) {
				// Do nothing.
			}
		});

		LinearLayout dialogLayout = new LinearLayout(TaskListActivity.this);
		dialogLayout.setOrientation(LinearLayout.VERTICAL);
		dialogLayout.addView(TextOut);
		dialogLayout.addView(typeSpinner);
		screenDialog.setView(dialogLayout);

		screenDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					// do something when the button is clicked
					public void onClick(DialogInterface arg0, int arg1) {
						getModel();
						listAdapter = new ListArrayAdapter(
								TaskListActivity.this, list);
						setListAdapter(listAdapter);
					}
				});
		screenDialog.show();
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
