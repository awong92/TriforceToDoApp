package edu.gatech.cs2340.triforce;
import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.cs2340.r.R;
import edu.gatech.cs2340.triforce.TaskListActivity;

public class TaskValidation extends ActivityInstrumentationTestCase2<TaskListActivity>{
	private String mSelection;
	private Button testButton;
	private Button testButton2; 
	private TaskListActivity tlActivity;
	private TaskListActivity mActivity;
	private Spinner mSpinner;
	private Solo solo;
	
	@SuppressWarnings("deprecation")
	public TaskValidation() {
		super(TaskListActivity.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(),getActivity());
	}
	
	@SmallTest
	public void testViewsCreated(){
		solo.assertCurrentActivity("Expected Task List", "TaskListActivity");
		solo.clickOnMenuItem("New Task");
		//check to see if activity is opened
		solo.assertCurrentActivity("Expected New Task Page", "NewTaskActivity");
		solo.enterText(0, "nepp");
		solo.enterText(1, "nepp");
		solo.enterText(2, "6017 Leigh Read Rd.");
		solo.clickOnButton(3);
		solo.clickOnMenuItem("Show Map");
		solo.assertCurrentActivity("Expected Google maps Page", "gMaps");
		boolean expected = true;
		boolean actual = solo.searchText("nepp");
		assertEquals("Task wasn't created", expected,actual);
	}
	@SmallTest
	public void testViewsVisible(){
		
	}
	
	
	//@SmallTest
//	public void testViewsVisible(){
		//ViewAsserts.assertOnScreen(mSpinner.getRootView(),mSpinner);
	//}
	
	//@SmallTest
//	public void testButtonUI(){
	//	tlActivity.runOnUiThread(
		//		new Runnable(){
			//		public void run(){
				//		mSpinner.requestFocus();
				//}
			//}
		//);
		//this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		//String resultText = mSpinner
		//assertEquals(resultText,"edu.gatech.cs2340.taskListActivity");
	//}
	

}
