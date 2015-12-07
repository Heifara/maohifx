/**
 *
 */
package com.maohi.software.maohifx.control.enumerations;

/**
 * @author heifara
 *
 */
public enum HrefTarget {
	/**
	 * Opens the linked document in a new window or tab
	 */
	BLANK,

	/**
	 * Opens the linked document in the same frame as it was clicked (this is default)
	 */
	SELF,

	/**
	 * Opens the linked document in the parent frame
	 */
	PARENT,

	/**
	 * Opens the linked document in the full body of the window
	 */
	TOP,

	/**
	 * Opens the linked document in a named frame
	 */
	FRAMENAME;
}
