package com.maohi.software.maohifx.samples.beans;

import java.util.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Person {
	private final SimpleStringProperty firstName = new SimpleStringProperty();
	private final SimpleStringProperty lastName = new SimpleStringProperty();
	private final SimpleStringProperty email = new SimpleStringProperty();
	private final SimpleObjectProperty age = new SimpleObjectProperty();
	private Date birthdate = new Date();

	public Person() {
	}

	public Person(final String firstName, final String lastName, final String email) {
		super();
		this.firstName.set(firstName);
		this.lastName.set(lastName);
		this.email.set(email);
		this.setAge("10");
	}

	public Object getAge() {
		return this.age.get();
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public String getEmail() {
		return this.email.get();
	}

	public String getFirstName() {
		return this.firstName.get();
	}

	public String getLastName() {
		return this.lastName.get();
	}

	public void setAge(final Object age) {
		this.age.set(age);
	}

	public void setBirthdate(final Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setEmail(final String email) {
		this.email.set(email);
	}

	public void setFirstName(final String firstName) {
		this.firstName.set(firstName);
	}

	public void setLastName(final String lastName) {
		this.lastName.set(lastName);
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.firstName.get(), this.lastName.get());
	}
}