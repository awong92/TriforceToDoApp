package edu.gatech.cs2340.triforce;

public class Task {

	private String name;
	private boolean selected;

	public Task(String name) {
		this.name = name;
		selected = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}