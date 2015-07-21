package com.maohi.software.samples.tableview.json;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jdk.nashorn.api.scripting.JSObject;

@SuppressWarnings({ "restriction" })
public class JSONItem {

	private Map<String, Object> data = new HashMap<String, Object>();

	public JSONItem() {
	}

	public JSONItem(JSObject aObject) {
		JSObject iJsObject = (JSObject) aObject;
		for (String iKey : iJsObject.keySet()) {
			this.data.put(iKey, iJsObject.getMember(iKey));
		}
	}

	public JSONItem(Object[]... items) {
		for (Object[] item : items) {
			this.data.put((String) item[0], item[1]);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(String aProperty) {
		Object iData = (T) data.get(aProperty);
		if (iData instanceof Date) {
			return (T) ((Date) iData).toString();
		} else if (iData instanceof JSObject) {
			JSONItem iJsonItem = new JSONItem((JSObject) iData);
			return (T) iJsonItem.toString();
		}
		return iData != null ? (T) iData.toString() : null;
	}

	public void setValue(String aKey, Object aValue) {
		data.put(aKey, aValue);
	}

}
