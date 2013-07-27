package com.expressstation.control.hardware;

import com.expressstation.control.NotifyAble;

/**
 * 所有硬件适配器的父类
 * 
 * @author 狄贤俊
 * 
 *         2013-7-22
 */
public abstract class ParentAdapter {

	protected NotifyAble mNotifyAble;

	public ParentAdapter(NotifyAble notifyAble) {
		mNotifyAble = notifyAble;
	}

	protected void sendMessage(int messageId, String... message) {
		if (mNotifyAble != null) {
			mNotifyAble.handleMessage(messageId, message);
		}
	}
	
	/**
	 * 硬件适配器打开响应硬件
	 */
	public abstract void open();
}
