package edu.gatech.oad.antlab.person;

/**
 * A simple class for person 2 returns their name and a modified string
 * 
 * @author Nathan Eppinger
 * @version 1.2
 */
public class Person2 {
	/** Holds the persons real name */
	private String name;

	/**
	 * The constructor, takes in the persons name
	 * 
	 * @param pname the person's real name
	 */
	public Person2(String pname) {
		name = pname;
	}

	/**
	 * This method should take the string input and return its characters in
	 * random order. given "gtg123b" it should return something like "g3tb1g2".
	 * 
	 * @param input the string to be modified
	 * @return the modified string
	 */
	private String calc(String input) {
		String randomOut = "";
		int i = 0;
		if (input.length() % 2 == 0) {
			while (i < input.length() - 1) {
				randomOut = randomOut.concat(input.substring(i + 1, i + 2)
						.concat(input.substring(i, i + 1)));
				i += 2;
			}
		} else {
			randomOut = randomOut.concat(input.substring(input.length() / 2)
					.concat(input.substring(0, input.length() / 2)));
		}
		return randomOut;
	}

	/**
	 * Return a string rep of this object that varies with an input string
	 * 
	 * @param input the varying string
	 * @return the string representing the object
	 */
	public String toString(String input) {
		return name + calc(input);
	}
}
