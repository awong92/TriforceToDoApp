package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import android.app.Activity;
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

		name.setText(task.getName());
		desc.setText(task.getDescription());
		type.setText(task.getType());
		date.setText(task.getDueDate());
		time.setText(task.getDueTime());
		location.setText(task.getLocation());
/*		name.setText("sample");
		desc.setText("sample");
		type.setText("sample");
		date.setText("sample");
		time.setText("sample");
		location.setText("sample"); */
		
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
			finish();
			break;
		case R.id.backVT:
			finish();
			break;
		}
	}

}
