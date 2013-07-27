package com.expressstation.control;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import com.expressstation.view.MainPage;

/**
 * 程序启动控制
 * 
 * @author 狄贤俊 2013-7-21
 */
public class MainControl extends ParentControl implements OperateObserver{
	public static final int ACCEPT = 0;
	public static final int TAKE = 1;
	public static final int SETTING = 2;

	public static void main(String[] args) {
		MainControl controlCenter = new MainControl();
		if (controlCenter.init()) {
			controlCenter.start();
		}
	}

	/** 如果以后有自检，可以在这边加入,暂时空缺 */
	private boolean init() {
		return true;
	}

	/** 小站开始正常运作 */
	protected void start() {
		GraphicsDevice[] devices;
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice useDevice = graphicsEnvironment.getDefaultScreenDevice();
		devices = graphicsEnvironment.getScreenDevices();

		if (devices.length > 0) {
			useDevice = devices[0];
		}

		if (useDevice.isFullScreenSupported()) {
			mScreen.setUndecorated(true);// 取消window装饰
			mScreen.setResizable(false);
			useDevice.setFullScreenWindow(mScreen);
		}

		mScreen.toFront();
		
		startPage(MainPage.class);
	}

	@Override
	public void handleMessage(int messageId, String... message) {
		switch (messageId) {
		case ACCEPT:
			AcceptBagControl acceptBagControl = new AcceptBagControl(this);
			acceptBagControl.start();
			System.out.print("揽件模式");
			break;
		case TAKE:
			TakeBagControl takeBagControl = new TakeBagControl(this);
			takeBagControl.start();
			System.out.print("取件模式");
			break;
		case SETTING:
			EmployeeControl employeeControl = new EmployeeControl(this);
			employeeControl.start();
			System.out.print("雇员模式");
			break;
		}
	}

	@Override
	public void operateEnd() {
		startPage(MainPage.class);
	}
}
