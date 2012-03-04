package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity implements OnClickListener {

	Button registerButton, cancelButton;
	EditText usernameField, passwordField, nameField, emailField;
	
	/** Called when the activity is first created. */
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
				entry.createEntry(username, password, name, email);
				entry.close();
			} catch (Exception e) {
				didItWork = false;
				Dialog dFail = new Dialog(this);
				dFail.setTitle("Error: Registration Failed");
				TextView tv = new TextView(this);
				tv.setText("DEBUG NOW");
				dFail.setContentView(tv);
				dFail.show();
			} finally {
				if (didItWork) {
					Dialog dPass = new Dialog(this);
					dPass.setTitle("Registration Completed");
					TextView tv = new TextView(this);
					tv.setText("You're the best!");
					dPass.setContentView(tv);
					dPass.show();
				}
			}

			break;
		case R.id.cancelRegButton:
			//Intent openLoginPage = new Intent("edu.gatech.cs2340.triforce.MAIN");
			//startActivity(openLoginPage);
			break;
		}
	}

}