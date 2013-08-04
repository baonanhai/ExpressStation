package com.expressstation.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.expressstation.net.JsonTag;

/**
 * 网络请求封装,处理请求通用部分，比如区域id和小站id
 * @author 狄贤俊
 *
 * 2013-8-4
 */
public class Request {
	private Map<String, String> infoMap = new HashMap<String, String>();

	public Request() {
		infoMap.put(JsonTag.LOCALES_ID, Commondefine.LOCALES_ID);
		infoMap.put(JsonTag.STATION_ID, Commondefine.STATION_ID);
	}
	
	public void put(String key, String value) {
		infoMap.put(key, value);
	}
	
	public String getRequestJson() {
		return new JSONObject(infoMap).toString();
	}
}
