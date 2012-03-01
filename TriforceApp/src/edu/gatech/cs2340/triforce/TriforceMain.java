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
import android.view.View.OnClickListener;
import android.widget.Button;


public class TriforceMain extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); //loads the main view into the app when its opened 
        
        Button loginButton = (Button) this.findViewById(R.id.login);
        loginButton.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            finish();
          }
        });
        
        Button registerButton = (Button) this.findViewById(R.id.register);
        registerButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View w){
        		finish();
        	}
        });
        }
    }

