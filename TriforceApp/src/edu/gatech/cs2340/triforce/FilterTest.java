package edu.gatech.cs2340.triforce;

import java.util.ArrayList;

import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Tests various filters and filter combinations in TriforceApp
 * 
 * @author Mallory Wynn, Alex Wong, Nathan Eppinger
 * 
 */
public class FilterTest extends ActivityInstrumentationTestCase2<TriforceMain> {

	private Solo solo;

	/**
	 * Constructor; makes sure we're testing TriforceApp
	 */
	public FilterTest() {
		super(TriforceMain.class);
	}

	/**
	 * Sets up the app for testing using Robotium
	 */
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	/**
	 * Tests Personal filter
	 */
	public void testPersonalFilter() {
		// login
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		// start filter
		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 1);
		solo.clickOnButton(1);

		assertFalse(solo.searchText("Work"));
		assertFalse(solo.searchText("School"));
	}

	/**
	 * Tests school filter
	 */
	public void testSchoolFilter() {

		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 2);
		solo.clickOnButton(1);

		assertFalse(solo.searchText("Personal"));
		assertFalse(solo.searchText("Work"));

	}

	/**
	 * Tests Work filter
	 */
	public void testWorkFilter() {
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 3);
		solo.clickOnButton(1);

		assertFalse(solo.searchText("Personal"));
		assertFalse(solo.searchText("School"));
	}

	/**
	 * Tests Date Filter
	 */
	public void testDateFilter() {
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Filter Tasks");
		solo.clickOnButton(0);
		solo.setDatePicker(0, 2012, 3, 12);
		solo.clickOnButton(0);
		solo.clickOnButton(1);

		assertTrue(solo.searchText("4-12-2012", 1));

	}

	/**
	 * Another test for the date filter
	 */
	public void testDateFiler2() {
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Filter Tasks");
		solo.clickOnButton(0);
		solo.setDatePicker(0, 2012, 3, 15);
		solo.clickOnButton(0);
		solo.clickOnButton(1);

		assertFalse(solo.searchText("4-12-2012"));
		assertTrue(solo.searchText("4-15-2012", 1));

	}

	/**
	 * Tests Incomplete Filter
	 */
	public void testUncheckedFilter() {
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(1, 1);
		solo.clickOnButton(1);

		assertFalse(solo.isCheckBoxChecked(0));
	}

	/**
	 * Tests Complete filter
	 */
	public void testCheckedFilter() {

		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(1, 2);
		solo.clickOnButton(1);

		assertTrue(solo.isCheckBoxChecked(0));

	}

	/**
	 * Tests Personal + Checked/Complete
	 */
	public void testPersonalChecked() {
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 1);
		solo.pressSpinnerItem(1, 2);
		solo.clickOnButton(1);

		assertTrue(solo.isCheckBoxChecked(0));
		assertFalse(solo.searchText("Work"));
		assertFalse(solo.searchText("School"));
	}

	/**
	 * Tests Personal + Unchecked/Incomplete
	 */
	public void testPersonalUnchecked() {
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 1);
		solo.pressSpinnerItem(1, 1);
		solo.clickOnButton(1);

		assertFalse(solo.searchText("Work"));
		assertFalse(solo.searchText("School"));
		assertFalse(solo.isCheckBoxChecked(0));
	}

	/**
	 * Tests Work + Complete/Checked
	 */
	public void testWorkChecked() {
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 3);
		solo.pressSpinnerItem(1, 2);
		solo.clickOnButton(1);

		assertTrue(solo.isCheckBoxChecked(0));
		assertFalse(solo.searchText("Personal"));
		assertFalse(solo.searchText("School"));
	}

	/**
	 * Tests Work + Unchecked/Incomplete
	 */
	public void testWorkUnchecked() {
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 3);
		solo.pressSpinnerItem(1, 1);
		solo.clickOnButton(1);

		assertFalse(solo.isCheckBoxChecked(0));
		assertFalse(solo.searchText("Personal"));
		assertFalse(solo.searchText("School"));
	}

	/**
	 * Tests School + Checked/Complete
	 */
	public void testSchoolChecked() {
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 2);
		solo.pressSpinnerItem(1, 2);
		solo.clickOnButton(1);

		assertTrue(solo.isCheckBoxChecked(0));
		assertFalse(solo.searchText("Personal"));
		assertFalse(solo.searchText("Work"));
	}

	/**
	 * Tests School + Unchecked/Incomplete
	 */
	public void testSchoolUnchecked() {
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 2);
		solo.pressSpinnerItem(1, 2);
		solo.clickOnButton(1);

		assertTrue(solo.isCheckBoxChecked(0));
		assertFalse(solo.searchText("Personal"));
		assertFalse(solo.searchText("Work"));
	}

	/**
	 * Tests Show All button
	 */
	public void testShowAll() {
		// logs in
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);

		// applies two different filters
		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 1);
		solo.pressSpinnerItem(1, 1);
		solo.clickOnButton(1);

		// should show all tasks
		solo.clickOnMenuItem("Show All Tasks");

		assertTrue(solo.isCheckBoxChecked(0));
		assertFalse(solo.isCheckBoxChecked(3));

		assertTrue(solo.searchText("Work"));
		assertTrue(solo.searchText("Personal"));
		assertTrue(solo.searchText("School"));
		assertTrue(solo.searchText("4-12-2012"));
		assertTrue(solo.searchText("4-15-2012"));
		assertTrue(solo.searchText("4-24-2012"));

	}

	/**
	 * Ends currently running activities after running tests
	 */
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
