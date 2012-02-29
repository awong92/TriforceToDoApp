package edu.gatech.cs2340.triforce;

/**
 * Team 36 -- Triforce
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong.
 * @version 1.0
 */
import java.util.ArrayList;

public class UserManager {
	ArrayList<User> users;

	/**
	 * constructor
	 */
	public UserManager() {
		users = new ArrayList<User>();
	}

	/**
	 * 
	 * @param newUser
	 *            new user to be added to list of users
	 */
	public void addUser(User newUser) {
		users.add(newUser);
	}

	/**
	 * 
	 * @param logUser
	 *            user logging in
	 * @return The password to be checked against the entered password
	 */
	public String getPassword(User logUser) {
		return users.get(users.indexOf(logUser)).getPassword();
	}

	/**
	 * 
	 * @param logUser
	 *            user trying to log in
	 * @return true if the username is a valid user
	 */
	public boolean isUser(User logUser) {
		return users.contains(logUser);
	}
}
