package edu.gatech.cs2340.triforce;

/**
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong.
 * @version 1.0
 */
public class User {
	private String password, username;

	/**
	 * Constructor for User
	 * 
	 * @param password
	 * @param username
	 */
	public User(String password, String username) {
		this.password = password;
		this.username = username;
	}

	/**
	 * Getter for password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Getter for username
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
}
