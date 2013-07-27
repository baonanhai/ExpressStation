package com.expressstation.control.hardware;

import com.expressstation.control.NotifyAble;
import com.expressstation.control.TakeBagControl;

public class IdentityCardAdapter extends ParentAdapter {

	public IdentityCardAdapter(NotifyAble notifyAble) {
		super(notifyAble);
	}

	@Override
	public void open() {
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sendMessage(TakeBagControl.MSG_PERSON_INFO_END, "1232132132131421421");
			}
		}.start();
	}
}
