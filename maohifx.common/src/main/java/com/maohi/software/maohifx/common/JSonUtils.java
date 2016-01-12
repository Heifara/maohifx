/**
 *
 */
package com.maohi.software.maohifx.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jdk.nashorn.api.scripting.JSObject;

/**
 * @author heifara
 *
 */
@SuppressWarnings("restriction")
public class JSonUtils {

	public static String toString(final JSObject aObject) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(aObject);
	}

}
