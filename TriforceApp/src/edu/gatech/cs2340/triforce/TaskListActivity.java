package edu.gatech.cs2340.triforce;

/**
 * Team Triforce (36)
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
import edu.gatech.cs2340.r.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TaskListActivity extends Activity implements OnClickListener {

	Button newTaskButton, logoutButton;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_tasklist);
		
		newTaskButton = (Button) findViewById(R.id.newTaskButton);
		logoutButton = (Button) findViewById(R.id.logoutButton);
		
		newTaskButton.setOnClickListener(this);
		logoutButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.newTaskButton:
			Intent createNewTask = new Intent("edu.gatech.cs2340.triforce.NEWTASKACTIVITY");
			startActivity(createNewTask);
			break;
		case R.id.logoutButton:
			Intent loginPage = new Intent("edu.gatech.cs2340.triforce.MAIN");
			startActivity(loginPage);
			break;
		}
	}

}
