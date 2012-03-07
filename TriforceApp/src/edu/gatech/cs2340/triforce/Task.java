package edu.gatech.cs2340.triforce;

/**
 * 
 * @author Nathan Eppinger, Mallory Wynn, Alex Wong. Task class that is
 *         basically just a constructor to make a class
 * 
 */
public class Task {
	public String name, location, date, hour, type;
	public boolean priority;

	/**
	 * 
	 * @param name
	 *            - name
	 * @param location
	 *            - geo location to be used with google maps
	 * @param date
	 *            - date month/day
	 * @param hour
	 *            - time of task specifically
	 * @param type
	 *            - to filter with
	 * @param priority
	 *            - is it a priority task or not?
	 */
	public Task(String name, String location, String date, String hour,
			String type, String priority) {
		this.name = name;
		this.location = location;
		this.date = date;
		this.hour = hour;
		this.type = type;
		if (priority.equalsIgnoreCase("true")) {
			this.priority = true;
		} else
			this.priority = false;
	}

	public boolean equals(Task task) {
		if (task.name.equals(name) && task.date.equals(date)
				&& task.hour.equals(hour) && task.location.equals(location))
			return true;
		else
			return false;
	}
}
