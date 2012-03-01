package edu.gatech.cs2340.triforce;

/**
 * Team 36 -- Triforce
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
import edu.gatech.cs2340.r.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import android.widget.Toast;

public class TriforceMain extends Activity {

	Button loginButton;
	EditText loginText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); // loads the main view into the app when
										// its opened
	}

	public void loginButton(View v) {
		Toast.makeText(this, "ENTER!", Toast.LENGTH_LONG).show();
		
	}

	public void registerButton(View v) {
		Toast.makeText(this, "REGISTER!", Toast.LENGTH_LONG).show();
	}
	
}
