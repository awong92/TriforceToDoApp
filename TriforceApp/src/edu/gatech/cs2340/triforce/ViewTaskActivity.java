package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Team Triforce (36) Back-end for view_task_page.xml. Allows users view a task
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class ViewTaskActivity extends Activity implements OnClickListener {

	Task task;
	TextView name, desc, type, date, time, location;
	String hourStr, minuteStr, timeStr;
	int hour, minute, indexOfColon;
	ImageButton editButton, backButton, deleteButton;

	/**
	 * Initializes variables and functionality
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_task_page);

		SQLiteDB db = new SQLiteDB(this);
		db.open();
		task = db.getTask(ListArrayAdapter.currTaskId, this);
		db.close();

		editButton = (ImageButton) findViewById(R.id.editVT);
		deleteButton = (ImageButton) findViewById(R.id.delVT);
		backButton = (ImageButton) findViewById(R.id.backVT);

		name = (TextView) findViewById(R.id.viewNameTxt);
		desc = (TextView) findViewById(R.id.viewDescTxt);
		type = (TextView) findViewById(R.id.viewTypeTxt);
		date = (TextView) findViewById(R.id.viewDateTxt);
		time = (TextView) findViewById(R.id.viewTimeTxt);
		location = (TextView) findViewById(R.id.viewLocationTxt);

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
			timeStr = hour + ":" + minute + " PM";
		else if (hour == 0)
			timeStr = (hour + 12) + ":" + minute + " AM";
		else
			timeStr = hour + ":" + minute + " AM";
		
		
		name.setText(task.getName());
		desc.setText(task.getDescription());
		type.setText(task.getType());
		date.setText(task.getDueDate());
		time.setText(timeStr);
		location.setText(task.getLocation());

		editButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editVT:
			finish();
			break;
		case R.id.delVT:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to delete?");
			builder.setCancelable(false);
			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							SQLiteDB db = new SQLiteDB(ViewTaskActivity.this);
							db.open();
							db.deleteTask(ListArrayAdapter.currTaskId);
							db.close();
							finish();
						}
					});
			builder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			builder.create().show();
			break;
		case R.id.backVT:
			finish();
			break;
		}
	}

}
