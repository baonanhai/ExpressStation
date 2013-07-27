package com.expressstation.control.hardware;

import com.expressstation.control.AcceptBagControl;
import com.expressstation.control.NotifyAble;

/**
 * 这个类不像其他适配器一样直接和硬件交互，由BoxManager直接和硬件交互，并维护一个箱子的数据库
 * 
 * @author 狄贤俊
 * 
 *         2013-7-27
 */
public class BoxAdapter extends ParentAdapter {

	public BoxAdapter(NotifyAble notifyAble) {
		super(notifyAble);
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
				sendMessage(AcceptBagControl.MSG_BOX_OPEN, 1 + "");
			}
		}.start();
	}
}
