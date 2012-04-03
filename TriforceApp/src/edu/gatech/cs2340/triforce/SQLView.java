package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Team Triforce (36) Back-end for view_db.xml. Views all the users in the
 * database
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class SQLView extends Activity implements OnClickListener {

	Button exit;

	/**
	 * Called when the activity is first created
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_db);
		exit = (Button) findViewById(R.id.exitViewDB);
		exit.setOnClickListener(this);

		TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
		SQLiteDB info = new SQLiteDB(this);
		info.open();
		String data = info.getUserData();
		info.close();
		tv.setText(data);
	}

	/**
	 * Functionality for exit button
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exitViewDB:
			finish();
			break;
		}
	}
}
