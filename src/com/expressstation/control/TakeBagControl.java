package com.expressstation.control;

import com.expressstation.control.hardware.IdentityCardAdapter;
import com.expressstation.net.NetworkClient;
import com.expressstation.net.NetworkObserver;
import com.expressstation.view.take.PersonInfoPage;
import com.expressstation.view.take.TakeTipPage;
import com.expressstation.view.take.VerificationInfoPage;

/**
 * 取包控制实现
 * @author 狄贤俊
 *
 * 2013-7-21
 */
public class TakeBagControl extends ParentControl implements NetworkObserver{
	public static final int MSG_PERSON_INFO_END = 1;
	public static final int MSG_PERSON_INFO_RETRY = 2;
	public static final int MSG_PERSON_INFO_CONFIRM_END = 3;
	
	public static final int MSG_VERIFICATION_INFO_END = 10;
	private String mPersonID;
	
	private OperateObserver mOperateObserver;
	private IdentityCardAdapter mIdentityCardAdapter;
	
	public TakeBagControl(OperateObserver operateObserver) {
		mOperateObserver = operateObserver;
	}

	@Override
	protected void start() {
		getPersonInfo();
	}

	@Override
	public void handleMessage(int messageId, String... message) {
		switch (messageId) {
		case MSG_PERSON_INFO_END:
			notifyPage(TakeBagControl.MSG_PERSON_INFO_END, message[0]);
			break;
		case MSG_PERSON_INFO_RETRY:
			mIdentityCardAdapter.open();
			break;
		case MSG_PERSON_INFO_CONFIRM_END:
			mPersonID = message[0];
			getVerificationInfo();
			break;
		case MSG_VERIFICATION_INFO_END:
			String verification = message[0];
			String mobileNumber = message[1];
			NetworkClient networkClient = NetworkClient.getInstance(this);
			networkClient.verifyInfo(mPersonID, verification, mobileNumber);
			break;
		case MSG_CANCEL:
			mOperateObserver.operateEnd();
			break;
		case MSG_END:
			mOperateObserver.operateEnd();
			break;
		default:
			break;
		}
		
	}
	
	private void getPersonInfo() {
		startPage(PersonInfoPage.class);
		mIdentityCardAdapter = new IdentityCardAdapter(this);
		mIdentityCardAdapter.open();
	}
	
	private void getVerificationInfo() {
		startPage(VerificationInfoPage.class);
	}
	
	private void startTip() {
		startPage(TakeTipPage.class);
	}

	@Override
	public void onNetOperateEnd(String content) {
		if (content.equals("true")) {
			notifyPage(10000);
			startTip();
		}
	}
}
