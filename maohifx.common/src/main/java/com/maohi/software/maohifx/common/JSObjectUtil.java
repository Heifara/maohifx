/**
 * 
 */
package com.maohi.software.maohifx.common;

import java.util.HashMap;
import java.util.Map;

import jdk.nashorn.api.scripting.JSObject;

/**
 * @author heifara
 *
 */
@SuppressWarnings("restriction")
public class JSObjectUtil {

	public static String parse(JSObject aJsObject) {
		Map<String, Object> iMap = new HashMap<>();
		for (String iKey : aJsObject.keySet()) {
			iMap.put(iKey, aJsObject.getMember(iKey));
		}
		return iMap.toString();
	}

}
