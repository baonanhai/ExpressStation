package com.expressstation.view;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.expressstation.control.MainControl;
import com.expressstation.control.NotifyAble;

/**
 * 第一个界面
 * 
 * @author 狄贤俊
 * 
 * @since 2013-7-21
 */
public class MainPage extends ParentPage {
	public MainPage(JFrame screen, NotifyAble notifyAble) {
		super(screen, notifyAble);
	}

	@Override
	protected void initPage(Container content) {
		GridBagLayout gridbag = new GridBagLayout();
		content.setLayout(gridbag);

		GridBagConstraints acceptBagConstraints = new GridBagConstraints();
		acceptBagConstraints.gridx = 1;
		acceptBagConstraints.gridy = 0;
		acceptBagConstraints.weighty = 1.0;
		acceptBagConstraints.insets = new Insets(0, 0, 0, 10);
		JButton acceptBagButton = new JButton("揽件");
		gridbag.setConstraints(acceptBagButton, acceptBagConstraints);
		content.add(acceptBagButton);
		acceptBagButton.setActionCommand(MainControl.ACCEPT + "");
		acceptBagButton.addActionListener(this);

		GridBagConstraints takeBagConstraints = new GridBagConstraints();
		takeBagConstraints.gridx = 1;
		takeBagConstraints.gridy = 1;
		takeBagConstraints.insets = new Insets(0, 0, 0, 10);
		JButton takeBagButton = new JButton("取件");
		gridbag.setConstraints(takeBagButton, takeBagConstraints);
		content.add(takeBagButton);
		takeBagButton.setActionCommand(MainControl.TAKE + "");
		takeBagButton.addActionListener(this);

		GridBagConstraints employeeConstraints = new GridBagConstraints();
		employeeConstraints.gridx = 0;
		employeeConstraints.gridy = 1;
		employeeConstraints.weightx = 1.0;
		employeeConstraints.weighty = 1.0;
		employeeConstraints.insets = new Insets(0, 10, 10, 0);
		employeeConstraints.anchor = GridBagConstraints.SOUTHWEST;
		JButton employeeButton = new JButton("设置");
		gridbag.setConstraints(employeeButton, employeeConstraints);
		content.add(employeeButton);
		employeeButton.setActionCommand(MainControl.SETTING + "");
		employeeButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		sendMessage(Integer.parseInt(e.getActionCommand()));
	}

	@Override
	public void onNotify(int messageId, String... message) {
		// TODO Auto-generated method stub

	}
}
