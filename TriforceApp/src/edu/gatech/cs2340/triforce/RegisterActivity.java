package edu.gatech.cs2340.triforce;

import edu.gatech.cs2340.r.R;
import edu.gatech.cs2340.r.R.id;
import edu.gatech.cs2340.r.R.layout;
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
			} finally {
				if (didItWork) {
					Dialog d = new Dialog(this);
					d.setTitle("The following data was entered!");
					TextView tv = new TextView(this);
					tv.setText("You're the best!");
					d.setContentView(tv);
					d.show();
				}
			}

			break;
		case R.id.cancelRegButton:

			break;
		}
	}

}