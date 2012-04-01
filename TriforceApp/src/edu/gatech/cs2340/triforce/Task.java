package edu.gatech.cs2340.triforce;

import android.content.Context;

/**
 * Team Triforce (36) Object class of a task
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class Task {

	private int taskId, complete;
	private String name, description, type, dueDate, dueTime, location;
	private boolean selected;
	private Context context;

	/**
	 * Constructor for Task
	 * 
	 * @param name
	 *            Name of the task
	 */
	public Task(int taskId, String name, String description, String type,
			String dueDate, String dueTime, String location, int complete,
			Context c) {
		this.taskId = taskId;
		this.name = name;
		this.setDescription(description);
		this.setType(type);
		this.dueDate = dueDate;
		this.setDueTime(dueTime);
		this.setLocation(location);
		selected = (complete == 1 ? true : false);
		this.complete = complete;
		context = c;
	}

	/**
	 * Getter for task Id
	 * 
	 * @return Task Id
	 */
	public int getTaskId() {
		return taskId;
	}

	/**
	 * Getter for name of task
	 * 
	 * @return Name of task
	 */
	public String getName() {
		return name;
	}

	/**
	 * Mutator for name of task
	 * 
	 * @param name
	 *            New name of task
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for due date of task
	 * 
	 * @return Name of task
	 */
	public String getDueDate() {
		return dueDate;
	}

	/**
	 * Mutator for due date of task
	 * 
	 * @param name
	 *            New name of task
	 */
	public void setDueDate(String date) {
		dueDate = date;
	}

	/**
	 * Checks if the task is selected
	 * 
	 * @return True if selected, false otherwise
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Mutator to set task as selected or not selected
	 * 
	 * @param selected
	 *            New selected state
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		complete = (selected ? 1 : 0);
		SQLiteDB db = new SQLiteDB(context);
		db.open();
		db.setComplete(taskId, complete);
		db.close();
	}

	/**
	 * Checks if the task is completed
	 * 
	 * @return True if selected, false otherwise
	 */
	public boolean isComplete() {
		return (complete == 1) ? true : false;
	}

	/**
	 * Mutator to set task as completed or not
	 * 
	 * @param selected
	 *            New selected state
	 */
	public void setComplete(int complete) {
		this.complete = complete;

	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the dueTime
	 */
	public String getDueTime() {
		return dueTime;
	}

	/**
	 * @param dueTime
	 *            the dueTime to set
	 */
	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
}