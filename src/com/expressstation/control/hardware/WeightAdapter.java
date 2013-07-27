package com.expressstation.control.hardware;

import com.expressstation.control.AcceptBagControl;
import com.expressstation.control.NotifyAble;

public class WeightAdapter extends ParentAdapter {

	public WeightAdapter(NotifyAble notifyAble) {
		super(notifyAble);
	}

	@Override
	public void open() {
		new Thread() {
			public void run() {
				try {
					sleep(3000);
					sendMessage(AcceptBagControl.MSG_WEIGHT_END, 1 + "");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
