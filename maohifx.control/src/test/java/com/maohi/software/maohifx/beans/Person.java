package com.maohi.software.maohifx.beans;

import java.util.Date;

import com.maohi.software.maohifx.enumeration.Gender;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Person {
	private SimpleStringProperty firstName = new SimpleStringProperty();
	private SimpleStringProperty lastName = new SimpleStringProperty();
	private SimpleStringProperty email = new SimpleStringProperty();
	private SimpleObjectProperty age = new SimpleObjectProperty();
	private Date birthdate = new Date();
	private Gender gender;

	public Person() {
	}

	public Person(String firstName, String lastName, String email) {
		super();
		this.firstName.set(firstName);
		this.lastName.set(lastName);
		this.email.set(email);
		this.setAge("10");
	}

	public Object getAge() {
		return age.get();
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public String getEmail() {
		return email.get();
	}

	public String getFirstName() {
		return firstName.get();
	}

	public Gender getGender() {
		return gender;
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setAge(Object age) {
		this.age.set(age);
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
}