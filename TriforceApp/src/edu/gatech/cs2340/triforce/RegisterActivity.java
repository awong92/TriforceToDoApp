package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Team Triforce (36) Back-end for register.xml. Registers new users or cancels
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class RegisterActivity extends Activity implements OnClickListener {

	Button registerButton, cancelButton;
	EditText usernameField, passwordField, nameField, emailField;
	ImageView usernameError, passwordError, nameError, emailError;

	/**
	 * Called when the activity is first created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		registerButton = (Button) findViewById(R.id.registerUserButton);
		cancelButton = (Button) findViewById(R.id.cancelRegButton);
		usernameField = (EditText) findViewById(R.id.newUsername);
		usernameError = (ImageView) findViewById(R.id.usernameError);
		passwordField = (EditText) findViewById(R.id.newPassword);
		passwordError = (ImageView) findViewById(R.id.passwordError);
		nameField = (EditText) findViewById(R.id.newName);
		nameError = (ImageView) findViewById(R.id.nameError);
		emailField = (EditText) findViewById(R.id.newEmail);
		emailError = (ImageView) findViewById(R.id.emailError);

		usernameError.setVisibility(View.INVISIBLE);
		passwordError.setVisibility(View.INVISIBLE);
		nameError.setVisibility(View.INVISIBLE);
		emailError.setVisibility(View.INVISIBLE);
		registerButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
	}

	/**
	 * Called when the activity is first created
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registerUserButton:
			boolean didItWork = true;
			usernameError.setVisibility(View.INVISIBLE);
			passwordError.setVisibility(View.INVISIBLE);
			nameError.setVisibility(View.INVISIBLE);
			emailError.setVisibility(View.INVISIBLE);
			try {
				String username = usernameField.getText().toString();
				String password = passwordField.getText().toString();
				String name = nameField.getText().toString();
				String email = emailField.getText().toString();

				if (!(username.equals("")) && !(password.equals(""))
						&& !(name.equals("")) && !(email.equals(""))) {
					SQLiteDB entry = new SQLiteDB(RegisterActivity.this);
					entry.open();
					if (entry.isUserAvailable(username))
						entry.createUserEntry(username, password, name, email);
					else {
						didItWork = false;
						usernameError.setVisibility(View.VISIBLE);
						Dialog usernameTaken = new Dialog(this);
						usernameTaken.setTitle("The username is already taken");
						usernameTaken.show();
					}
					entry.close();
				} else {
					didItWork = false;
					if (username.equals(""))
						usernameError.setVisibility(View.VISIBLE);
					if (password.equals(""))
						passwordError.setVisibility(View.VISIBLE);
					if (name.equals(""))
						nameError.setVisibility(View.VISIBLE);
					if (email.equals(""))
						emailError.setVisibility(View.VISIBLE);
					Dialog missingFields = new Dialog(this);
					missingFields.setTitle("Fill in all the fields");
					missingFields.show();
				}
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