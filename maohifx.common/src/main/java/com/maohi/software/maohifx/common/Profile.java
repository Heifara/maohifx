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
	private String token;
	private String role;

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
	 * @return the role
	 */
	public String getRole() {
		return this.role;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return this.token;
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
	 * @param role
	 *            the role to set
	 */
	public void setRole(final String role) {
		this.role = role;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(final String token) {
		this.token = token;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

}
