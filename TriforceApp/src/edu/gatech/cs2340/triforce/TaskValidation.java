package edu.gatech.cs2340.triforce;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.test.suitebuilder.annotation.Smoke;

import com.jayway.android.robotium.solo.Solo;

/**
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 *
 * This class test the TaskListActivity page and connected pages 
 * that dal with task creation, deletion, and editing. 
 */
public class TaskValidation extends ActivityInstrumentationTestCase2<TriforceMain>{
	private Solo solo;
	
	@SuppressWarnings("deprecation")
	public TaskValidation() {
		super("edu.gatech.cs2340.triforce",TriforceMain.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(),getActivity());
	}
	/**
	 * tests the taskList seeing if it can add a task
	 */
	
	public void testButtons(){
		solo.clickOnMenuItem("New Task");
		solo.assertCurrentActivity("wrong page", NewTaskActivity.class);
		solo.goBackToActivity("TaskListActivity");
	}
	
	public void testAddTaskFail(){
		solo.enterText(0, "nepp");
		solo.enterText(1, "nepp");
		solo.clickOnButton(0);
	//	assertEquals("fail",false,true);
		solo.clickOnMenuItem("New Task");
		solo.enterText(0, "elf");
		solo.enterText(1, "Tallahassee");
		solo.enterText(2, "6017 Leigh Read Rd. Tallahssee FL.");
		solo.clickOnButton(3);
		boolean expected = false;
		boolean actual = solo.searchText("elf");
		assertEquals("Task wasn't created", expected,actual);
	}
	
	/*
	public void testAddTask(){
		solo.assertCurrentActivity("Expected login page", "TriforceMain");
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
		assertEquals("Task wasn't created", expected,actual);
	}
	*/
	/**
	 * Tests to see if editing tasks is working
	 */
	/*
	
	public void testEditTask(){
		solo.clickOnText("home");
	//	solo.clickOnScreen(5, 150);
		solo.clickOnMenuItem("Edit Task");
		solo.enterText(0, "home2");
		solo.clickOnButton(2);
		assertEquals("Task Wasn't Edited", true,solo.searchText("home2"));
		
	}
	
	public void testRemoveTask(){
		solo.clickInList(2);
		//solo.clickLongOnScreen(5000, 5000);
		solo.clickOnMenuItem("Delete Task");
		assertEquals("Task wasn't deleted", false,solo.searchText("home2"));
	}
	*/
	@SmallTest
	public void addTask2(){
		solo.clickOnMenuItem("New Task");
		solo.enterText(0, "school");
		solo.enterText(1, "GaTech");
		solo.enterText(2, "Georgia Institute of Technology");
		solo.pressSpinnerItem(0, 1);
		solo.clickOnButton(2);
		boolean expected = true;
		boolean actual = solo.searchText("school");
		assertEquals("Task wasn't created", expected,actual);
	}
	
	
	public void testGoogleM(){
		solo.clickOnMenuItem("Show Locations");
		assertEquals("Google Maps didn't open","gMap", solo.getCurrentActivity());
	}
	
	@Override
	public void tearDown() throws Exception {
		//Robotium will finish all the activities that have been opened
		solo.finishOpenedActivities();
	}
}
