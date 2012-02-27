package edu.gatech.cs2340.triforce;

/**
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong.
 * @version 1.0
 */
public class User {
	private String password, username;

	/**
	 * 
	 * @param password
	 *            -new User's password
	 * @param username
	 *            -new User's username
	 */
	public User(String password, String username) {
		this.password = password;
		this.username = username;
	}

	/**
	 * getter for password
	 * 
	 * @return password of User
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
}
