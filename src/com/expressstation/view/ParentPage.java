package com.expressstation.view;

import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.expressstation.control.NotifyAble;

/**
 * 所有界面的父类，暂时负责全屏设置，以及获取焦点
 * 
 * @author 狄贤俊
 * 
 *         2013-7-22
 */
public abstract class ParentPage implements ActionListener{
	protected NotifyAble mNotifyAble;

	public ParentPage(JFrame screen, NotifyAble notifyAble) {
		Container container = screen.getContentPane();
		container.removeAll();
		initPage(container);
		container.revalidate();
		container.repaint();
		mNotifyAble = notifyAble;
	}

	/**
	 * 初始化界面布局的类
	 */
	protected abstract void initPage(Container content);

	/**
	 * 接受控制模块传来的通知
	 */
	public abstract void onNotify(int messageId, String... message);

	protected void sendMessage(int messageId, String... message) {
		if (mNotifyAble != null) {
			mNotifyAble.handleMessage(messageId, message);
		}
	}
}
