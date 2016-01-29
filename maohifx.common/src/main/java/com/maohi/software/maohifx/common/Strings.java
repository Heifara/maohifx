/**
 *
 */
package com.maohi.software.maohifx.common;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author heifara
 *
 */
public class Strings {

	private static String format(final File aElement) {
		final StringBuilder iString = new StringBuilder();
		iString.append(aElement.getClass());
		iString.append(", path=");
		iString.append(aElement.getPath());
		iString.append("]");
		return iString.toString();
	}

	public static String format(final List<?> aList) {
		return format(aList.toArray());
	}

	@SuppressWarnings("rawtypes")
	public static String format(final Map<?, ?> aMap) {
		final StringBuilder iFormatted = new StringBuilder();
		iFormatted.append("{");
		for (final Entry<?, ?> iEntry : aMap.entrySet()) {
			final Object iKey = iEntry.getKey();
			final Object iValue = iEntry.getValue();

			iFormatted.append(iKey.toString());
			iFormatted.append("=");
			if (iValue instanceof Collection) {
				iFormatted.append(format(((Collection) iValue).toArray()));
			} else {
				iFormatted.append(iValue.toString());
			}
		}
		iFormatted.append("}");
		return iFormatted.toString();
	}

	public static String format(final Object[] aArray) {
		return format(aArray, "[", "]");
	}

	@SuppressWarnings("rawtypes")
	public static String format(final Object[] aArray, final String aStartDelimiter, final String aEndDelimiter) {
		final StringBuilder iFormatted = new StringBuilder();
		long iCount = 1;
		for (final Object iElement : aArray) {
			iFormatted.append(aStartDelimiter);
			if (iElement instanceof Collection) {
				iFormatted.append(format(((Collection) iElement).toArray()));
			} else if (iElement instanceof File) {
				iFormatted.append(format((File) iElement));
			} else {
				iFormatted.append(iElement.toString());
			}
			iFormatted.append(aEndDelimiter);
			if (iCount == aArray.length) {
				break;
			} else {
				iFormatted.append(",");
				iCount++;
			}
		}
		return iFormatted.toString();
	}

	/**
	 * Return the field name for aText
	 *
	 * @param aText
	 * @return the field name
	 */
	public static String getFieldName(final String aText) {
		final StringBuilder iResult = new StringBuilder();
		final String[] iSplittedText = aText.split("_");
		for (final String iText : iSplittedText) {
			iResult.append(iText.substring(0, 1).toUpperCase());
			iResult.append(iText.substring(1, iText.length()).toLowerCase());
		}
		return iResult.toString().substring(0, 1).toLowerCase() + iResult.toString().substring(1, iResult.toString().length());
	}

	/**
	 * Return the method name of the getter for aFieldName
	 *
	 * @param aClass
	 *            the class
	 * @param aType
	 *            the type
	 * @param aFieldName
	 *            the field name
	 * @return the method name
	 */
	public static String getGetterMethodName(final Class<?> aType, final String aFieldName) {
		return getGetterMethodName(aType, aFieldName, false);
	}

	/**
	 * Return the method name of the getter for aFieldName
	 *
	 * @param aClass
	 *            the class
	 * @param aType
	 *            the type
	 * @param aFieldName
	 *            the field name
	 * @param aIs
	 *            true if the boolean/Boolean getter is supposed to be isMyField() or false if is supposed to be getMyField()
	 * @return the method name
	 */
	public static String getGetterMethodName(final Class<?> aType, final String aFieldName, final boolean aIs) {
		final StringBuilder iMethodName = new StringBuilder();

		if ((aType != null) && (aType.isAssignableFrom(Boolean.class) || aType.isAssignableFrom(boolean.class))) {
			iMethodName.append(aIs ? "is" : "get");
		} else if (aIs) {
			iMethodName.append("is");
		} else {
			iMethodName.append("get");
		}

		iMethodName.append(aFieldName.substring(0, 1).toUpperCase());
		iMethodName.append(aFieldName.substring(1, aFieldName.length()));
		return iMethodName.toString();
	}

	/**
	 * Return the method name of the getter for aFieldName
	 *
	 * @param aFieldName
	 *            the field name
	 * @return the method name
	 */
	public static String getGetterMethodName(final String aFieldName) {
		return getGetterMethodName(null, aFieldName);
	}

	/**
	 * Return the method name of the getter for aFieldName and aIs
	 *
	 * @param aFieldName
	 *            the field name
	 * @param aIs
	 *            true to start with is... else false
	 * @return the method name
	 */
	public static String getGetterMethodName(final String aFieldName, final boolean aIs) {
		return getGetterMethodName(null, aFieldName, aIs);
	}

	/**
	 * Return the method name of the setter for aFieldName
	 *
	 * @param aClass
	 *            the class
	 * @param aFieldName
	 *            the name
	 * @return
	 */
	public static String getSetterMethodName(final String aFieldName) {
		final StringBuilder iMethodName = new StringBuilder("set");

		iMethodName.append(aFieldName.substring(0, 1).toUpperCase());
		iMethodName.append(aFieldName.substring(1, aFieldName.length()));
		return iMethodName.toString();
	}

	/**
	 * Generate a random string from all aplha-numeric characteres
	 *
	 * @param aLength
	 * @return
	 */
	public static String random(final int aLength) {
		return random(aLength, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
	}

	/**
	 * Generate a random string from chars given in parameters
	 *
	 * @param aLength
	 * @param aChars
	 * @return
	 */
	public static String random(final int aLength, final String aChars) {
		String iResult = "";
		for (int x = 0; x < aLength; x++) {
			final int i = (int) Math.floor(Math.random() * aChars.length());
			iResult += aChars.charAt(i);
		}
		return iResult;
	}

	/**
	 * Generate a random string from all numeric characteres
	 *
	 * @param aLength
	 * @return
	 */
	public static String randomNumbers(final int aLength) {
		return random(aLength, "1234567890");
	}

	/**
	 * under_score to camelCase
	 *
	 * @param aString
	 * @return
	 */
	public static String toCamelCase(final String aString) {
		final String[] parts = aString.split("_");
		String camelCaseString = "";
		for (final String part : parts) {
			camelCaseString = camelCaseString + part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase();
		}
		return camelCaseString;
	}

	public static String toUnderscore(final String aString) {
		String iResult = "";
		for (int iIndex = 0; iIndex < aString.length(); iIndex++) {
			final char iChar = aString.charAt(iIndex);
			if (Character.isUpperCase(iChar)) {
				if (iIndex > 0) {
					iResult += '_';
				}
				iResult += Character.toLowerCase(iChar);
			} else {
				iResult += iChar;
			}
		}
		return iResult;
	}

}
