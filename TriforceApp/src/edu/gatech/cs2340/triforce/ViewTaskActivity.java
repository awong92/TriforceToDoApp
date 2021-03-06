package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Team Triforce (36) Back-end for view_task_page.xml. Allows users view a task
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class ViewTaskActivity extends Activity {

	ImageButton editTaskBtn;

	Task task;
	TextView taskComplete, name, desc, type, date, time, location;
	String hourStr, minuteStr, timeStr;
	int hour, minute, indexOfColon;
	SQLiteDB db = new SQLiteDB(this);

	/**
	 * Initializes variables and functionality
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_task_page);

		// retrieve task to be viewed from db
		db.open();
		task = db.getTask(ListArrayAdapter.currTaskId, this);
		db.close();

		// capture our View elements
		editTaskBtn = (ImageButton) findViewById(R.id.editTaskBtn);
		taskComplete = (TextView) findViewById(R.id.taskCompletedTxt);
		name = (TextView) findViewById(R.id.viewNameTxt);
		desc = (TextView) findViewById(R.id.viewDescTxt);
		type = (TextView) findViewById(R.id.viewTypeTxt);
		date = (TextView) findViewById(R.id.viewDateTxt);
		time = (TextView) findViewById(R.id.viewTimeTxt);
		location = (TextView) findViewById(R.id.viewLocationTxt);

		// set elements to Task attributes
		editTaskBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent editViewTask = new Intent(
						"edu.gatech.cs2340.triforce.EDITTASKACTIVITY");
				startActivity(editViewTask);
			}
		});
		if (task.isComplete())
			taskComplete.setText("[ task completed ]");
		else
			taskComplete.setText("[ task incomplete ]");

		indexOfColon = task.getDueTime().indexOf(":");
		hourStr = task.getDueTime().substring(0, indexOfColon);
		hour = Integer.parseInt(hourStr);
		minuteStr = task.getDueTime().substring(indexOfColon + 1);
		minute = Integer.parseInt(minuteStr);

		if (minute < 10)
			minuteStr = "0" + minute;
		else
			minuteStr = "" + minute;
		if (hour > 12)
			timeStr = (hour - 12) + ":" + minuteStr + " PM";
		else if (hour == 12)
			timeStr = hour + ":" + minuteStr + " PM";
		else if (hour == 0)
			timeStr = (hour + 12) + ":" + minuteStr + " AM";
		else
			timeStr = hour + ":" + minuteStr + " AM";

		name.setText(task.getName());
		desc.setText(task.getDescription());
		type.setText(task.getType());
		date.setText(task.getDueDate());
		time.setText(timeStr);
		location.setText(task.getLocation());
	}

	/**
	 * Method for redisplaying the TaskView page when returned to
	 */
	@Override
	public void onRestart() {
		super.onRestart();
		setContentView(R.layout.view_task_page);

		// retrieve task to be viewed from db
		db.open();
		task = db.getTask(ListArrayAdapter.currTaskId, this);
		db.close();

		// recapture our View elements
		editTaskBtn = (ImageButton) findViewById(R.id.editTaskBtn);
		taskComplete = (TextView) findViewById(R.id.taskCompletedTxt);
		name = (TextView) findViewById(R.id.viewNameTxt);
		desc = (TextView) findViewById(R.id.viewDescTxt);
		type = (TextView) findViewById(R.id.viewTypeTxt);
		date = (TextView) findViewById(R.id.viewDateTxt);
		time = (TextView) findViewById(R.id.viewTimeTxt);
		location = (TextView) findViewById(R.id.viewLocationTxt);

		// reset elements to Task attributes
		editTaskBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent editViewTask = new Intent(
						"edu.gatech.cs2340.triforce.EDITTASKACTIVITY");
				startActivity(editViewTask);
			}
		});
		if (task.isComplete())
			taskComplete.setText("[ task completed ]");
		else
			taskComplete.setText("[ task incomplete ]");

		indexOfColon = task.getDueTime().indexOf(":");
		hourStr = task.getDueTime().substring(0, indexOfColon);
		hour = Integer.parseInt(hourStr);
		minuteStr = task.getDueTime().substring(indexOfColon + 1);
		minute = Integer.parseInt(minuteStr);

		if (minute < 10)
			minuteStr = "0" + minute;
		else
			minuteStr = "" + minute;
		if (hour > 12)
			timeStr = (hour - 12) + ":" + minuteStr + " PM";
		else if (hour == 12)
			timeStr = hour + ":" + minuteStr + " PM";
		else if (hour == 0)
			timeStr = (hour + 12) + ":" + minuteStr + " AM";
		else
			timeStr = hour + ":" + minuteStr + " AM";

		name.setText(task.getName());
		desc.setText(task.getDescription());
		type.setText(task.getType());
		date.setText(task.getDueDate());
		time.setText(timeStr);
		location.setText(task.getLocation());
	}

	/**
	 * Menu will pop up and inflate the task list menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_viewtask, menu);
		return true;
	}

	/**
	 * Handles when the Edit, Delete, or Back button is clicked on the menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menuDeleteTask:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to delete?");
			builder.setCancelable(false);
			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						/**
						 * OnClickListener for Dialog option "Yes" to delete
						 * task
						 */
						@Override
						public void onClick(DialogInterface dialog, int id) {
							db.open();
							db.deleteTask(ListArrayAdapter.currTaskId);
							db.close();
							Toast.makeText(getBaseContext(),
									"Deleting task...", Toast.LENGTH_SHORT)
									.show();
							finish();
						}
					});
			builder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						/**
						 * OnClickListener for Dialog option "No" to cancel
						 * delete
						 */
						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			builder.create().show();
			return true;
		case R.id.menuBackVT:
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
