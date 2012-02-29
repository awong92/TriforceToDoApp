package edu.gatech.cs2340.triforce;

/**
 * Team 36 -- Triforce
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong.
 * @version 1.0
 */
public class User {
	private String username, password, name, email;

	/**
	 * Constructor for User
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @Param email
	 */
	public User(String username, String password, String name, String email) {
		
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	/**
	 * Getter for username
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
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
	 * Getter for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for email
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
}
