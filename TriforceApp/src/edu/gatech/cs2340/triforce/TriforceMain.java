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
import android.content.Intent;

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

	/**
	 * Makes Enter button do stuff; Password turned into a string
	 * 
	 * @param v
	 */
	public void loginButton(View v) {
		final EditText usernameText = (EditText) findViewById(R.id.usernameField);
		String usernameStr = usernameText.getText().toString();

		// Password EditText to String object
		final EditText passwordText = (EditText) findViewById(R.id.passwordField);
		String passwordStr = passwordText.getText().toString();

		// makes a toast of the Username String
		Toast.makeText(this, usernameStr, Toast.LENGTH_SHORT).show();

		Button regBtn = (Button) findViewById(R.id.registerButton);
		regBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent nextScreen = new Intent(getApplicationContext(),
						RegisterActivity.class);

				startActivity(nextScreen);
			}

		});

	}
}
