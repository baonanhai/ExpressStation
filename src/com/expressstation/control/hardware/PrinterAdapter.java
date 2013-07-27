package com.expressstation.control.hardware;

import com.expressstation.control.AcceptBagControl;
import com.expressstation.control.NotifyAble;
import com.expressstation.model.ExpresstationInfo;

public class PrinterAdapter extends ParentAdapter {
	private ExpresstationInfo mExpresstationInfo;

	public PrinterAdapter(ExpresstationInfo expresstationInfo,NotifyAble notifyAble) {
		super(notifyAble);
		mExpresstationInfo = expresstationInfo;
	}

	@Override
	public void open() {
		new Thread() {
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sendMessage(AcceptBagControl.MSG_PRINT_END);
			}
		}.start();
	}

}
