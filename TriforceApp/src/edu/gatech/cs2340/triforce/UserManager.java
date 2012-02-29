package edu.gatech.cs2340.triforce;

/**
 * Team 36 -- Triforce
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong.
 * @version 1.0
 */
import java.util.ArrayList;

public class UserManager {
	ArrayList<User> userList;
	User currentUser;

	/**
	 * Constructor for UserManager
	 */
	public UserManager() {
		userList = new ArrayList<User>();
		currentUser = null;
	}

	/**
	 * 
	 * @param newUser
	 *            new user to be added to list of users
	 */
	public void addUser(User newUser) {
		userList.add(newUser);
	}

	/**
	 * Getter for current user
	 * 
	 * @return current user
	 */
	public User getCurrent() {
		return currentUser;
	}

	/**
	 * 
	 * 
	 * @param logUser
	 *            user logging in
	 * @return The password to be checked against the entered password
	 */
	public boolean getPassword(String logUser, String password) {
		User temp = new User(logUser, "", "", "");
		return userList.get(userList.indexOf(temp)).getPassword()
				.equals(password);
	}

	/**
	 * Setter for current user
	 * 
	 * @param u
	 *            new current user
	 */
	public void setCurrent(User u) {
		currentUser = u;
	}

	/**
	 * Checks if the user trying to log in is in the list
	 * 
	 * @param logUser
	 *            user trying to log in
	 * @return true if username is valid
	 */
	public boolean isUser(String logUser) {
		User temp = new User(logUser, "", "", "");
		return userList.contains(temp);
	}
}
