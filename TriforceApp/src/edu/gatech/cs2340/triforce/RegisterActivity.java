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

public class RegisterActivity extends Activity implements OnClickListener {

	Button registerButton, cancelButton;
	EditText usernameField, passwordField, nameField, emailField;

	// Called when the activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		registerButton = (Button) findViewById(R.id.registerUserButton);
		cancelButton = (Button) findViewById(R.id.cancelRegButton);
		usernameField = (EditText) findViewById(R.id.newUsername);
		passwordField = (EditText) findViewById(R.id.newPassword);
		nameField = (EditText) findViewById(R.id.newName);
		emailField = (EditText) findViewById(R.id.newEmail);

		registerButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
	}

	// Called when the activity is first created
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registerUserButton:
			boolean didItWork = true;
			try {
				String username = usernameField.getText().toString();
				String password = passwordField.getText().toString();
				String name = nameField.getText().toString();
				String email = emailField.getText().toString();

				SQLiteDB entry = new SQLiteDB(RegisterActivity.this);
				entry.open();
				if (entry.isUserAvailable(username))
					entry.createUserEntry(username, password, name, email);
				entry.close();
			} catch (Exception e) {
				didItWork = false;
				String error = e.toString();
				Dialog dFail = new Dialog(this);
				dFail.setTitle("Error: Registration Failed");
				TextView tv = new TextView(this);
				tv.setText(error);
				dFail.setContentView(tv);
				dFail.show();
			} finally {
				if (didItWork) {
					Dialog dPass = new Dialog(this);
					dPass.setTitle("Registration Completed");
					dPass.show();
					finish();
				}
			}

			break;
		case R.id.cancelRegButton:
			finish();
			break;
		}
	}

}