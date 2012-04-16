package edu.gatech.cs2340.triforce;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

/**
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * 
 *         This class test the TaskListActivity page and connected pages that
 *         deal with task creation, deletion, and editing.
 */
public class TaskValidation extends
		ActivityInstrumentationTestCase2<TriforceMain> {
	private Solo solo;

	public TaskValidation() {
		super(TriforceMain.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	/**
	 * tests the taskList seeing if it can add a task
	 */

	public void testButtons() {
		// loggin in
		solo.enterText(0, "nepp");
		solo.enterText(1, "nepp");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("New Task");
		solo.assertCurrentActivity("wrong page", NewTaskActivity.class);
		solo.goBackToActivity("TaskListActivity");
	}

	/**
	 * tests to see if the cancel button works on the create task page
	 */
	public void testAddTaskFail() {
		solo.enterText(0, "nepp");
		solo.enterText(1, "nepp");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("New Task");
		solo.enterText(0, "elf");
		solo.enterText(1, "Tallahassee");
		solo.enterText(2, "6017 Leigh Read Rd. Tallahssee FL.");
		solo.clickOnButton(3);
		boolean expected = false;
		boolean actual = solo.searchText("elf");
		assertEquals("Task wasn't created", expected, actual);
	}

	/**
	 * 
	 */
	public void testAddTask() {
		solo.enterText(0, "nepp");
		solo.enterText(1, "nepp");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("New Task");
		solo.enterText(0, "home");
		solo.enterText(1, "Tallahassee");
		solo.enterText(2, "6017 Leigh Read Rd. Tallahssee FL.");
		solo.clickOnButton(2);
		boolean expected = true;
		boolean actual = solo.searchText("home");
		solo.goBackToActivity("TaskListActivity");
		assertEquals("Task wasn't created", expected, actual);
	}

	/**
	 * Tests to see if editing tasks is working
	 */
	public void testEditTask() {

		solo.enterText(0, "nepp");
		solo.enterText(1, "nepp");
		solo.clickOnButton(0);

		solo.clickOnText("home");
		solo.clickOnMenuItem("Edit Task");
		solo.enterText(0, "home2");
		solo.clickOnButton(2);
		solo.goBackToActivity("TaskListActivity");
		assertEquals("Task Wasn't Edited", true, solo.searchText("home2"));
	}

	/**
	 * makes sure the tasks can be deleted
	 */
	public void testRemoveTask() {
		// loggin in
		solo.enterText(0, "nepp");
		solo.enterText(1, "nepp");
		solo.clickOnButton(0);

		solo.clickOnText("home2");
		solo.clickOnMenuItem("Delete Task");
		solo.clickOnButton(0);
		assertEquals("Task wasn't deleted", false, solo.searchText("home2"));
	}

	/**
	 * Tests to make sure multiple tasks can be added to the list
	 */
	public void testAddTask2() {

		solo.enterText(0, "nepp");
		solo.enterText(1, "nepp");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("New Task");
		solo.enterText(0, "school");
		solo.enterText(1, "GaTech");
		solo.enterText(2, "Georgia Institute of Technology");
		solo.pressSpinnerItem(0, 1);
		solo.clickOnButton(2);
		boolean expected = true;
		boolean actual = solo.searchText("school");
		assertEquals("Task wasn't created", expected, actual);
	}

	/**
	 * Tests to make sure google maps open
	 * 
	 */
	public void testGoogleM() {

		solo.enterText(0, "nepp");
		solo.enterText(1, "nepp");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Show Locations");
		solo.assertCurrentActivity("google maps didn't open", gmap.class);
	}

	@Override
	public void tearDown() throws Exception {
		// Robotium will finish all the activities that have been opened
		solo.finishOpenedActivities();
	}
}
