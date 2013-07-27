package com.expressstation.view.accpet;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.expressstation.control.AcceptBagControl;
import com.expressstation.control.NotifyAble;
import com.expressstation.view.ParentPage;

/**
 * 输入快递信息界面
 * @author 狄贤俊
 *
 * 2013-7-27
 */
public class ExpressInfoPage extends ParentPage{
	
	private JTextField toPerson;
	private JTextField toLocation;
	private JTextField toMobile;
	private JTextField fromPerson;
	private JTextField fromLocation;
	private JTextField fromMobile;
	
	public ExpressInfoPage(JFrame screen, NotifyAble notifyAble) {
		super(screen, notifyAble);
	}

	@Override
	protected void initPage(Container content) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		content.setLayout(gridBagLayout);
		
		JLabel label_4 = new JLabel("请输入快递信息");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.gridwidth = 2;
		gbc_label_4.insets = new Insets(0, 0, 5, 0);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 0;
		content.add(label_4, gbc_label_4);
		
		JLabel label = new JLabel("收件人信息");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.weightx = 1;
		gbc_label.gridy = 1;
		content.add(label, gbc_label);
		
		JLabel label_1 = new JLabel("收件人：   ");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 2;
		content.add(label_1, gbc_label_1);
		
		toPerson = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 1;
		gbc_textField.weightx = 1;
		gbc_textField.gridy = 2;
		content.add(toPerson, gbc_textField);
		toPerson.setColumns(10);
		
		JLabel label_2 = new JLabel("收件地址：");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 3;
		content.add(label_2, gbc_label_2);
		
		toLocation = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 3;
		content.add(toLocation, gbc_textField_1);
		toLocation.setColumns(10);
		
		JLabel label_3 = new JLabel("联系电话：");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 4;
		content.add(label_3, gbc_label_3);
		
		toMobile = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 4;
		content.add(toMobile, gbc_textField_2);
		toMobile.setColumns(10);
		
		JLabel label_5 = new JLabel("发件人信息");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 5;
		content.add(label_5, gbc_label_5);
		
		JLabel label_6 = new JLabel("发件人：   ");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 0;
		gbc_label_6.gridy = 6;
		content.add(label_6, gbc_label_6);
		
		fromPerson = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 6;
		content.add(fromPerson, gbc_textField_3);
		fromPerson.setColumns(10);
		
		JLabel label_7 = new JLabel("发件地址：");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 0;
		gbc_label_7.gridy = 7;
		content.add(label_7, gbc_label_7);
		
		fromLocation = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 7;
		content.add(fromLocation, gbc_textField_4);
		fromLocation.setColumns(10);
		
		JLabel label_8 = new JLabel("联系电话：");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.insets = new Insets(0, 0, 5, 5);
		gbc_label_8.gridx = 0;
		gbc_label_8.gridy = 8;
		content.add(label_8, gbc_label_8);
		
		fromMobile = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 0);
		gbc_textField_5.gridx = 1;
		gbc_textField_5.gridy = 8;
		content.add(fromMobile, gbc_textField_5);
		fromMobile.setColumns(10);
		
		JButton button_1 = new JButton("确定");
		button_1.setActionCommand(AcceptBagControl.MSG_EXPRESSTATION_INFO_END + "");
		button_1.addActionListener(this);
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 0, 5);
		gbc_button_1.gridx = 0;
		gbc_button_1.gridy = 9;
		content.add(button_1, gbc_button_1);
		
		JButton button = new JButton("取消");
		button.setActionCommand(AcceptBagControl.MSG_CANCEL + "");
		button.addActionListener(this);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridx = 1;
		gbc_button.gridy = 9;
		content.add(button, gbc_button);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (Integer.parseInt(arg0.getActionCommand())) {
		case AcceptBagControl.MSG_EXPRESSTATION_INFO_END:
			String toPersonStr = toPerson.getText();
			String toLocationStr = toLocation.getText();
			String toMobileStr = toMobile.getText();
			String fromPersonStr = fromPerson.getText();
			String fromLocationStr = fromLocation.getText();
			String fromMobileStr = fromMobile.getText();
			sendMessage(AcceptBagControl.MSG_EXPRESSTATION_INFO_END, toPersonStr, toLocationStr, toMobileStr,
					fromPersonStr, fromLocationStr, fromMobileStr);
			break;
		case AcceptBagControl.MSG_CANCEL	:
			sendMessage(AcceptBagControl.MSG_CANCEL);
			break;
		default:
			break;
		}
	}

	@Override
	public void onNotify(int messageId, String... message) {
		
	}
}
