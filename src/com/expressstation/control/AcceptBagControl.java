package com.expressstation.control;

import com.expressstation.control.hardware.BoxAdapter;
import com.expressstation.control.hardware.PrinterAdapter;
import com.expressstation.control.hardware.WeightAdapter;
import com.expressstation.model.ExpresstationInfo;
import com.expressstation.net.NetworkClient;
import com.expressstation.net.NetworkObserver;
import com.expressstation.view.accpet.AccpetBagTipPage;
import com.expressstation.view.accpet.ExpressInfoPage;
import com.expressstation.view.accpet.WeightPage;

/**
 * 揽件控制实现
 * 
 * @author 狄贤俊
 * 
 *         2013-7-21
 */
public class AcceptBagControl extends ParentControl implements NetworkObserver{
	public static final int MSG_WEIGHT_END = 1;
	public static final int MSG_WEIGHT_RETRY = 2;
	public static final int MSG_WEIGHT_CONFIRM_END = 3;
	
	public static final int MSG_EXPRESSTATION_INFO_END = 10;
	
	public static final int MSG_PRINT_END = 20;
	
	public static final int MSG_BOX_OPEN = 30;
	
	private WeightAdapter mWeightAdapter;
	private PrinterAdapter mPrinterAdapter;
	private BoxAdapter mBoxAdapter;
	
	private ExpresstationInfo mExpresstationInfo;
	
	private OperateObserver mOperateObserver;
	
	public AcceptBagControl(OperateObserver operateObserver) {
		mOperateObserver = operateObserver;
	}

	@Override
	protected void start() {
		 getWeight();
	}

	@Override
	public void handleMessage(int messageId, String... message) {
		switch (messageId) {
		case MSG_WEIGHT_END:
			notifyPage(messageId, message);
			break;
		case MSG_WEIGHT_RETRY:
			if (mWeightAdapter != null) {
				mWeightAdapter.open();
			}
			break;
		case MSG_WEIGHT_CONFIRM_END:
			mExpresstationInfo = new ExpresstationInfo();
			mExpresstationInfo.setWeight(message[0]);
			setInfo();
			break;
		case MSG_EXPRESSTATION_INFO_END:
			mExpresstationInfo.setToPerson(message[0]);
			mExpresstationInfo.setToLocation(message[1]);
			mExpresstationInfo.setToMobile(message[2]);
			mExpresstationInfo.setFromPerson(message[3]);
			mExpresstationInfo.setFromLocation(message[4]);
			mExpresstationInfo.setFromMobile(message[5]);
			mExpresstationInfo.setState(Integer.parseInt(message[6]));
			printExpresswaybill();
			break;
		case MSG_PRINT_END:
			openBox();
			break;
		case MSG_BOX_OPEN:
			notifyPage(MSG_BOX_OPEN, message[0]);
			break;
		case MSG_CANCEL:
			mOperateObserver.operateEnd();
			break;
		case MSG_END:
			NetworkClient client = NetworkClient.getInstance(this);
			client.commitBagInfo(mExpresstationInfo);
			mOperateObserver.operateEnd();
			break;
		default:
			break;
		}
	}
	
	private void getWeight() {
		startPage(WeightPage.class);
		mWeightAdapter = new WeightAdapter(this);
		mWeightAdapter.open();
	}
	
	private void setInfo() {
		startPage(ExpressInfoPage.class);
	}
	
	private void printExpresswaybill() {
		startPage(AccpetBagTipPage.class);
		mPrinterAdapter = new PrinterAdapter(mExpresstationInfo, this);
		mPrinterAdapter.open();
	}
	
	private void openBox() {
		mBoxAdapter = new BoxAdapter(this);
		mBoxAdapter.open();
	}

	@Override
	public void onNetOperateEnd(String content) {
		System.out.print("sssss"+ content);
		
	}
}
