package edu.gatech.cs2340.triforce;

/**
 * @Author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @Version 1.0 
 * 
 * Team 36--Triforce
 */
import edu.gatech.cs2340.r.R;
import android.app.Activity;
import android.os.Bundle;

public class TriforceMain extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); //loads the main view into the app when its opened 
    }
}