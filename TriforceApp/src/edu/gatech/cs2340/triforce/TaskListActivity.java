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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class TaskListActivity extends Activity implements OnClickListener {

	ImageButton newTaskButton;
	Button logoutButton;
	String filterBy = "All";
	Spinner typeSpinner;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_tasklist);
		
		typeSpinner = (Spinner) findViewById(R.id.typeOfTask);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.filter_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    typeSpinner.setAdapter(adapter);
	    
		newTaskButton = (ImageButton) findViewById(R.id.newTaskButton);
		logoutButton = (Button) findViewById(R.id.logoutButton);
		
		typeSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
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
			TriforceMain.currentUser = null;
			finish();
			break;
		}
	}
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	      filterBy = parent.getItemAtPosition(pos).toString();
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}

}
