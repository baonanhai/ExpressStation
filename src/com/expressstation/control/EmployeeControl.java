package com.expressstation.control;

import com.expressstation.net.NetworkClient;
import com.expressstation.net.NetworkObserver;
import com.expressstation.view.employee.EmployeeInfoPage;
import com.expressstation.view.employee.LoginPage;

/**
 * 工作人员模式
 * @author 狄贤俊
 *
 * 2013-7-21
 */
public class EmployeeControl extends ParentControl implements NetworkObserver{
	public static final int MSG_LOGIN_INFO_START = 1;
	public static final int MSG_LOGIN_INFO_END = 2;
	public static final int MSG_LOGIN_ERROR = 3;
	public static final int MSG_BAG_INFO = 4;
	public static final int MSG_DESPATCH_BAGS = 5;
	public static final int MSG_ACCEPT_BAGS = 6;
	public static final int MSG_LEFT_BAGS = 7;
	
	private OperateObserver mOperateObserver;
	
	public EmployeeControl(OperateObserver operateObserver) {
		mOperateObserver = operateObserver;
	}
	
	@Override
	protected void start() {
		startLogin();
	}
	
	@Override
	public void handleMessage(int messageId, String... message) {
		NetworkClient networkClient = NetworkClient.getInstance(this);
		switch (messageId) {
		case MSG_LOGIN_INFO_START:
			networkClient.login(message[0], message[1]);
			break;
		case MSG_DESPATCH_BAGS:
			networkClient.getDespatchBags();
			break;
		case MSG_ACCEPT_BAGS:
			networkClient.getAcceptBags();
			break;
		case MSG_LEFT_BAGS:
			networkClient.getLeftBags();
			break;
		case MSG_END:
			mOperateObserver.operateEnd();
			break;
		default:
			break;
		}
	}
	
	private void startLogin() {
		startPage(LoginPage.class);
	}
	
	private void showEmployeeInfo() {
		notifyPage(MSG_LOGIN_INFO_END);
		startPage(EmployeeInfoPage.class);
		NetworkClient networkClient = NetworkClient.getInstance(this);
		networkClient.getAcceptBags();
	}

	@Override
	public void onNetOperateEnd(String content) {
		if (content.equals("true")) {
			showEmployeeInfo();
		} else if (content.equals("false")){
			notifyPage(MSG_LOGIN_ERROR);
		} else {
			notifyPage(MSG_BAG_INFO, content);
		}
	}
}
