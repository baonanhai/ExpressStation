package com.expressstation.control;

/**
 * 所有控制中心都要实现的接口，界面和硬件适配器通过这个接口通知控制中心
 * @author 狄贤俊
 *
 * 2013-7-23
 */
public interface NotifyAble {
	public void handleMessage (int messageId, String... message);
}
