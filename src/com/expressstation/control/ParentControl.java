package com.expressstation.control;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import com.expressstation.view.ParentPage;

/**
 * 所有控制器都需要继承的类，封装启动界面和给界面设置通知观察者的功能
 * 
 * @author 狄贤俊
 * 
 *         2013-7-23
 */
public abstract class ParentControl implements NotifyAble {
	public static final int MSG_CANCEL = -1;
	public static final int MSG_END = 0;
	private ParentPage mPage;
	protected static JFrame mScreen = new JFrame();

	protected void startPage(Class<?> pageClass) {
		if (ParentPage.class.isAssignableFrom(pageClass)) {
			try {
				Constructor<?> constructor = pageClass.getConstructor(
						JFrame.class, NotifyAble.class);
				mPage = (ParentPage) constructor.newInstance(mScreen, this);
			} catch (InstantiationException | IllegalAccessException
					| NoSuchMethodException | SecurityException
					| IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 接受从界面以及硬件适配器模块反馈的信息，以便做出逻辑控制
	 * 
	 * @param messageId
	 *            信息id，区分不同的信息
	 * @param message
	 *            信息附带的数据，比如秤重得到的重量数据，可以有多个
	 */
	public abstract void handleMessage(int messageId, String... message);

	/** 控制正常运作 */
	protected abstract void start();

	/** 通知前台界面 */
	protected void notifyPage(int messageId, String... message) {
		if (mPage != null) {
			mPage.onNotify(messageId, message);
		}
	}
}
