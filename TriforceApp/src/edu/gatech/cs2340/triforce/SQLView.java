package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SQLView extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_db);
		TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
		SQLiteDB info = new SQLiteDB(this);
		info.open();
		String data = info.getData();
		info.close();
		tv.setText(data);
	}
}
