package edu.gatech.cs2340.triforce;

import java.util.ArrayList;

/**
 * Team 36 -- Triforce
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong.
 * @version 1.0
 */
public class User {
	private String username, password, name, email;
	private ArrayList<List> lists = new ArrayList<List>();
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

	/**
	 * Setter for email
	 * 
	 * @param p
	 *            new password
	 */
	public void setPassword(String p) {
		password = p;
	}

	/**
	 * Setter for password
	 * 
	 * @param e
	 *            new email
	 */
	public void setEmail(String e) {
		email = e;
	}

	public void add(List list){
		lists.add(list);
	}
	/**
	 * Equals method
	 */
	public boolean equals(User use) {
		if (use.getUsername().equals(username))
			return true;
		else
			return false;
	}
}
