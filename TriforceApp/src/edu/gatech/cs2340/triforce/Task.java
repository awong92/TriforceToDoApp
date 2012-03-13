package edu.gatech.cs2340.triforce;

/**
 * Team Triforce (36)
 * Object class of a task
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong
 * @version 1.0
 */
public class Task {

	private String name;
	private boolean selected;

	/**
	 * Constructor for Task
	 * 
	 * @param name Name of the task
	 */
	public Task(String name) {
		this.name = name;
		selected = false;
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
	 * @param name New name of task
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param selected New selected state
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}