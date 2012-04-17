package edu.gatech.cs2340.triforce;

import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Tests various filters and filter combinations in TriforceApp
 * 
 * @author Mallory Wynn, Alex Wong, Nathan Eppinger
 * 
 */
public class CheckTest extends ActivityInstrumentationTestCase2<TriforceMain> {

	private Solo solo;

	/**
	 * Constructor; makes sure we're testing TriforceApp
	 */
	public CheckTest() {
		super(TriforceMain.class);
	}

	/**
	 * Sets up the app for testing using Robotium
	 */
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	/**
	 * Checks that an item is made and checked when clicked
	 */
	public void testCheckmark() {
		// logs in
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		// creates new task with a description and date
		solo.clickOnMenuItem("New Task");
		solo.enterText(0, "Check Checkboxes Task");
		solo.enterText(1, "This will check that a checkbox gets checked");

		solo.clickOnButton(0);
		solo.setDatePicker(0, 2012, 3, 26);
		solo.clickOnButton(0);

		solo.clickOnButton(3);

		// checks checkbox
		solo.clickOnCheckBox(6);
		boolean cbState = solo.isCheckBoxChecked(6);
		assertEquals("CB is " + cbState + " supposed to be true", true, cbState);

		// looks at task page to see if marked as completed
		solo.clickOnText("Check Checkboxes Task");
		assertTrue(solo.searchText("[ task completed ]"));
	}

	/**
	 * ends all currently running tasks; restarts the application
	 */
	public void tearDown() throws Exception {
		// deltes the new task for later testing
		solo.clickOnText("Check Checkboxes Task");
		solo.clickOnMenuItem("Delete Task");
		solo.clickOnButton(0);

		solo.finishOpenedActivities();
	}

}