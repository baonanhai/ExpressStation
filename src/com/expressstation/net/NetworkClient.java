package com.expressstation.net;

import java.io.IOException;

import com.expressstation.model.Commondefine;
import com.expressstation.model.ExpresstationInfo;
import com.expressstation.model.Request;

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
				Request loginRequest = new Request();
				loginRequest.put(JsonTag.REQUEST_ID, Commondefine.REQUESTID_LOGIN + "");
				loginRequest.put(JsonTag.ACCOUNT, account);
				loginRequest.put(JsonTag.PASSWORD, password);
				try {
					String jsonStr = HttpRequest.ConnectByPost(HttpRequest.DEFAULT_POST_URL, loginRequest.getRequestJson());
					mNetworkObserver.onNetOperateEnd(jsonStr);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 在揽件成功后提交揽件信息
	 */
	public void commitBagInfo(final ExpresstationInfo info) {
		new Thread() {

			@Override
			public void run() {
				Request commitRequest = new Request();
				commitRequest.put(JsonTag.REQUEST_ID, Commondefine.REQUESTID_COMMIT_BAG + "");
				commitRequest.put("weight", info.getWeight());
				commitRequest.put("toPerson", info.getToPerson());
				commitRequest.put("toLocation", info.getToLocation());
				commitRequest.put("toMobile", info.getToMobile());
				commitRequest.put("fromPerson", info.getFromPerson());
				commitRequest.put("fromLocation", info.getFromLocation());
				commitRequest.put("fromMobile", info.getFromMobile());
				commitRequest.put("price", info.getPrice());
				try {
					String jsonStr = HttpRequest.ConnectByPost(HttpRequest.DEFAULT_POST_URL, commitRequest.getRequestJson());
					mNetworkObserver.onNetOperateEnd(jsonStr);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 在扫件的时候以及包裹被取走后需要调用这个方法，更新快递包裹在数据库中的状态
	 * @param info 这个参数中要包括快递id以及要更新到什么状态，其他参数不用赋值。
	 */
	public void updateState(final ExpresstationInfo info) {
		new Thread() {

			@Override
			public void run() {
				Request commitRequest = new Request();
				commitRequest.put(JsonTag.REQUEST_ID, Commondefine.REQUESTID_UPDTAE_STATE + "");
				commitRequest.put("state", info.getState() + "");
				try {
					String jsonStr = HttpRequest.ConnectByPost(HttpRequest.DEFAULT_POST_URL, commitRequest.getRequestJson());
					mNetworkObserver.onNetOperateEnd(jsonStr);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 请求下发派送给当前小站的包裹信息
	 */
	public void getDespatchBags() {
		new Thread() {
			@Override
			public void run() {
				Request commitRequest = new Request();
				commitRequest.put(JsonTag.REQUEST_ID, Commondefine.REQUESTID_GET_DESPATCH_BAGS + "");
				try {
					String jsonStr = HttpRequest.ConnectByPost(HttpRequest.DEFAULT_POST_URL, commitRequest.getRequestJson());
					System.out.println("jsonStrjsonStr:" + commitRequest.getRequestJson());
					mNetworkObserver.onNetOperateEnd(jsonStr);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 请求当前小站的已经揽收的包裹信息
	 */
	public void getAcceptBags() {
		new Thread() {
			@Override
			public void run() {
				Request commitRequest = new Request();
				commitRequest.put(JsonTag.REQUEST_ID, Commondefine.REQUESTID_GET_ACCPET_BAGS + "");
				try {
					String jsonStr = HttpRequest.ConnectByPost(HttpRequest.DEFAULT_POST_URL, commitRequest.getRequestJson());
					System.out.println("jsonStrjsonStr:" + commitRequest.getRequestJson());
					mNetworkObserver.onNetOperateEnd(jsonStr);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * 请求小站的剩余包裹信息
	 */
	public void getLeftBags() {
		new Thread() {
			@Override
			public void run() {
				Request commitRequest = new Request();
				commitRequest.put(JsonTag.REQUEST_ID, Commondefine.REQUESTID_GET_LEFT_BAGS + "");
				try {
					String jsonStr = HttpRequest.ConnectByPost(HttpRequest.DEFAULT_POST_URL, commitRequest.getRequestJson());
					System.out.println("jsonStrjsonStr:" + commitRequest.getRequestJson());
					mNetworkObserver.onNetOperateEnd(jsonStr);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
