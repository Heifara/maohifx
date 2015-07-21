/**
 * 
 */
package com.maohi.software.maohifx.server.beans;

/**
 * @author heifara
 *
 */
public class Person {

	private String firstName;
	private String lastName;

	public Person() {
	}

	public Person(String firstName) {
		super();
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
