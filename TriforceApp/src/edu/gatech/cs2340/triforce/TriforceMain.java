package edu.gatech.cs2340.triforce;

/**
 * Team Triforce (36)
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
import edu.gatech.cs2340.r.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class TriforceMain extends Activity implements OnClickListener {

	static String currentUser = null;
	Button loginButton, regButton;
	EditText loginName, loginPassword;

	/**
	 * Called when the activity is first created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// capture our View elements
		loginButton = (Button) findViewById(R.id.loginButton);
		regButton = (Button) findViewById(R.id.GotoRegisterButton);
		loginName = (EditText) findViewById(R.id.usernameField);
		loginPassword = (EditText) findViewById(R.id.passwordField);

		// set OnClickListeners for buttons
		loginButton.setOnClickListener(this);
		regButton.setOnClickListener(this);
	}

	/**
	 * Handles accordingly when the Login, Register, or ViewDB button is clicked
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginButton:
			String loginNameStr = loginName.getText().toString();
			String loginPasswordStr = loginPassword.getText().toString();
			SQLiteDB info = new SQLiteDB(this);
			info.open();
			boolean validLogin = info.isValidUser(loginNameStr,
					loginPasswordStr);
			info.close();
			if (validLogin) { // If login is valid, currentUser is set
				currentUser = loginNameStr;
				loginPassword.setText("");
				Intent viewTaskList = new Intent(
						"edu.gatech.cs2340.triforce.TASKLISTACTIVITY");
				startActivity(viewTaskList);
			} else { // If login is invalid, show prompt
				Dialog dPass = new Dialog(this);
				dPass.setTitle("Login Failed");
				TextView tv = new TextView(this);
				tv.setText("Username and/or Password is incorrect");
				dPass.setContentView(tv);
				dPass.show();
			}
			break;
		case R.id.GotoRegisterButton:
			Intent openRegPage = new Intent(
					"edu.gatech.cs2340.triforce.REGISTERACTIVITY");
			startActivity(openRegPage);
			break;
		}
	}
}
