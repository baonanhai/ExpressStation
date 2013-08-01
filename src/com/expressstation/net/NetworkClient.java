package com.expressstation.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class NetworkClient {
	private static NetworkClient mNetworkClient;
	private static NetworkObserver mNetworkObserver;

	private NetworkClient() {

	}

	public synchronized static NetworkClient getInstance(NetworkObserver networkObserver) {
		if (mNetworkClient == null) {
			mNetworkClient = new NetworkClient();
		}
		mNetworkObserver = networkObserver;
		return mNetworkClient;
	}

	/**
	 * 通过服务器验证取件信息
	 * @param personId 身份证号码
	 * @param verification 验证码
	 * @param mobileNumber 手机号码
	 */
	public void verifyInfo(String personId, String verification, String mobileNumber) {
		new Thread() {

			@Override
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mNetworkObserver.onNetOperateEnd("true");
			}
		}.start();
	}

	/**
	 * 员工登录验证
	 */
	public void login(final String account, final String password) {
		new Thread() {

			@Override
			public void run() {
				Map<String, String> loginMap = new HashMap<String, String>();
				loginMap.put(JsonTag.REQUEST_ID, RequestIdDefine.REQUESTID_LOGIN + "");
				loginMap.put(JsonTag.ACCOUNT, account);
				loginMap.put(JsonTag.PASSWORD, password);
				JSONObject loginJson = new JSONObject(loginMap);
				try {
					String jsonStr = HttpRequest.ConnectByPost(HttpRequest.DEFAULT_POST_URL, loginJson.toString());
					mNetworkObserver.onNetOperateEnd(jsonStr);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
