package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EditTaskActivity extends Activity implements OnClickListener {

	Button cancelButton;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_task_page);
		
		cancelButton = (Button) findViewById(R.id.cancelButtonET);
		
		cancelButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.cancelButtonET:
			finish();
			break;
		}
	}

}
