package edu.gatech.cs2340.triforce;

import java.util.Hashtable;

/**
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong.
 * 
 *         Holds a bunch of tasks based on type and/or priority
 */
public class List {
	private String name;
	private Hashtable<Task, Boolean> tasks = new Hashtable<Task, Boolean>();

	/**
	 * Construct
	 * 
	 * @param name
	 *            - List will be named - name
	 */
	public List(String name) {
		this.name = name;
	}

	/**
	 * @param task
	 *            - task to be added to list adds to list
	 */
	public void add(Task task) {
		tasks.put(task, task.priority);
	}

	/**
	 * @param task
	 *            - task to be removed from list
	 */
	public void remove(Task task) {
		tasks.remove(task);
	}

	public String getName() {
		return name;
	}

	public boolean equals(List list) {
		if (list.getName().equals(name))
			return true;
		else
			return false;

	}
}
