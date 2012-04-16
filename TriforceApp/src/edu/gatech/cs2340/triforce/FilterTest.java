package edu.gatech.cs2340.triforce;
import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.test.suitebuilder.annotation.Smoke;



public class FilterTest extends ActivityInstrumentationTestCase2 {

	private Solo solo;
	
	public FilterTest() {
		super("edu.gatech.cs2340.triforce", TriforceMain.class);
	}
	
	protected void setUp() throws Exception{
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testPersonalFilter(){
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);
		
		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 1);
		solo.clickOnButton(1);
		
		assertFalse(solo.searchText("Work"));
		assertFalse(solo.searchText("School"));
	}
	
	public void testSchoolFilter(){
		
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);
		
		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 2);
		solo.clickOnButton(1);
		
		assertFalse(solo.searchText("Personal"));
		assertFalse(solo.searchText("Work"));

	}

	public void testWorkFilter(){
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);
		
		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(0, 3);
		solo.clickOnButton(1);
		
		assertFalse(solo.searchText("Personal"));
		assertFalse(solo.searchText("School"));
	}

//	public void testDateFilter(){
//		solo.enterText(0, "meallory");
//		solo.enterText(1, "PASSWORD");
//		solo.clickOnButton(0);
//		
//		solo.clickOnMenuItem("Filter Tasks");
//		solo.clickOnButton(0);
//		solo.setDatePicker(0, 2012, 4, 12);
//		
//		assertFalse(solo.searchText("4-12-2012"));
//		
//		solo.enterText(0, "meallory");
//		solo.enterText(1, "PASSWORD");
//		solo.clickOnButton(0);
//		
//		solo.clickOnMenuItem("FilterTasks");
//		solo.clickOnButton(0);
//		solo.setDatePicker(0, 2012, 4, 15);
//		solo.clickOnButton(0)
//		solo.clickOnButton(1);
	
//		assertFalse(solo.searchText("4-12-2012"));
//		assertFalse(solo.searchText("4-15-2012"));
//		
//	}
	
	public void testUncheckedFilter(){
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);
		
		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(1, 1);
		solo.clickOnButton(1);
		
		assertFalse(solo.isCheckBoxChecked(0));
	}
	
	public void testCheckedFilter(){
		
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton(0);
		
		solo.clickOnMenuItem("Filter Tasks");
		solo.pressSpinnerItem(1, 2);
		solo.clickOnButton(1);
		
		assertTrue(solo.isCheckBoxChecked(0));
		
	}
	
	public void testPersonalChecked(){
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
	
	public void testPersonalUnchecked(){
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
	
	public void testWorkChecked(){
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
	
	public void testWorkUnchecked(){
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
	
	public void testSchoolChecked(){
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

	public void testSchoolUnchecked(){
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
		
	public void tearDown() throws Exception{
		solo.finishOpenedActivities();
	}
	

	

}
