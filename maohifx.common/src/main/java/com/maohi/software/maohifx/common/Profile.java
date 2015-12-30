/**
 *
 */
package com.maohi.software.maohifx.common;

/**
 * @author heifara
 *
 */
public class Profile {

	private String username;
	private String password;

	public Profile() {
	}

	public Profile(final String username, final String password) {
		super();
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

}
