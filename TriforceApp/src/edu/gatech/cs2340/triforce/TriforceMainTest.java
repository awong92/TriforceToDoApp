package edu.gatech.cs2340.triforce;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

/**
 * JUNIT TESTING
 *
 */
public class TriforceMainTest extends ActivityInstrumentationTestCase2<TriforceMain> {
	
	private Solo solo;
	
	public TriforceMainTest() {
		super(TriforceMain.class);
	}

	/**
	 * SETS UP THE APP
	 */
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	/**
	 * TEST ALL THE BUTTONS
	 */
	public void testCreation(){
		solo.assertCurrentActivity("Expected Login Page", "TriforceMain");	
		solo.clickOnButton("Register");
		solo.assertCurrentActivity("Opens Register Page", "RegisterActivity");
		
		solo.clickOnButton("Cancel");
		
		solo.clickOnButton("Enter");
		assertTrue(solo.searchText("Username and/or Password is incorrect"));
	}
	
	/**
	 * TEST IF LOGIN SUCCESSFUL
	 */
	public void testGoodLogin(){
		solo.assertCurrentActivity("Expect Login Screen", "TriforceMain");
		solo.enterText(0, "USERNAME");
		solo.enterText(1, "PASSWORD");
		solo.clickOnButton("Enter");
		
		assertTrue(solo.searchText("Logging in..."));
		
	}
	
	/**
	 * TEST IF LOGIN NOT SUCCESFFUL
	 */
	public void testBadLogin(){
		solo.assertCurrentActivity("Expect Login Screen", "TriforceMain");
		solo.enterText(0, "bad");
		solo.enterText(1, "bad");
		
		solo.clickOnButton("Enter");
		assertTrue(solo.searchText("Username and/or Password is incorrect"));
	}

	/**
	 * TEST IF REGISTRATION WORKS
	 */
	public void testGoodRegistration(){
		solo.assertCurrentActivity("Expect Login Screen", "TriforceMain");
		solo.clickOnButton("Register");
		solo.assertCurrentActivity("Expect Register Screen", "RegisterActivity");
		
		solo.enterText(0, "USERNAME0");
		solo.enterText(1, "PASSWORD");
		solo.enterText(2, "NEWNAME");
		solo.enterText(3, "email@email.com");
		
		solo.clickOnButton("Register");
		
		assertTrue(solo.searchText("Registration complete"));
	
	}
	
	/**
	 * TEST IF REGISTRATION FAILS
	 */
	public void testBadUsername(){
		solo.assertCurrentActivity("Expect Login Screen", "TriforceMain");
		solo.clickOnButton("Register");
		solo.assertCurrentActivity("Expect Register Screen", "RegisterActivity");
		
		solo.enterText(0, "meallory");
		solo.enterText(1, "PASSWORD");
		solo.enterText(2, "NEWNAME");
		solo.enterText(3, "email@email.com");
		
		solo.clickOnButton("Register");
		
		assertTrue(solo.searchText("The username is already taken"));
	}
	
	/**
	 * TEST IS LOGIN NAME IS MISSING
	 */
	public void testEmptyField(){
		solo.assertCurrentActivity("Expect Login Screen", "TriforceMain");
		solo.clickOnButton("Register");
		solo.assertCurrentActivity("Expect Register Screen", "RegisterActivity");
		
		solo.enterText(1, "PASSWORD");
		solo.enterText(2, "NEWNAME");
		solo.enterText(3, "email@email.com");
		solo.clickOnButton("Register");
		
		assertTrue(solo.searchText("Fill in all the fields"));
	}
	
	/**
	 * EXIT THE APP
	 */
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
