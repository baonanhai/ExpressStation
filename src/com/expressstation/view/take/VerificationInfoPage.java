package com.expressstation.view.take;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.expressstation.control.NotifyAble;
import com.expressstation.control.TakeBagControl;
import com.expressstation.view.ParentPage;

/**
 * 验证信息界面
 * @author 狄贤俊
 *
 * 2013-7-27
 */
public class VerificationInfoPage extends ParentPage{
	private JTextField verificationField;
	private JTextField mobileNumberField;
	private JFrame mScreen;
	private JDialog mJDialog;

	public VerificationInfoPage(JFrame screen, NotifyAble notifyAble) {
		super(screen, notifyAble);
		mScreen = screen;
	}

	@Override
	protected void initPage(Container content) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		content.setLayout(gridBagLayout);
		
		JLabel label = new JLabel("请输入验证信息");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 2;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		content.add(label, gbc_label);
		
		JLabel label_1 = new JLabel("验证码：");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 1;
		content.add(label_1, gbc_label_1);
		
		verificationField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		content.add(verificationField, gbc_textField);
		verificationField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("手机号：");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		content.add(lblNewLabel, gbc_lblNewLabel);
		
		mobileNumberField = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 2;
		content.add(mobileNumberField, gbc_textField_1);
		mobileNumberField.setColumns(10);
		
		JButton button = new JButton("确定");
		button.setActionCommand(TakeBagControl.MSG_VERIFICATION_INFO_END + "");
		button.addActionListener(this);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 3;
		content.add(button, gbc_button);
		
		JButton button_1 = new JButton("取消");
		button_1.setActionCommand(TakeBagControl.MSG_CANCEL + "");
		button_1.addActionListener(this);
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.anchor = GridBagConstraints.EAST;
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 3;
		content.add(button_1, gbc_button_1);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (Integer.parseInt(e.getActionCommand())) {
		case TakeBagControl.MSG_VERIFICATION_INFO_END:
			String verification = verificationField.getText();
			String mobileNumber = mobileNumberField.getText();
			sendMessage(TakeBagControl.MSG_VERIFICATION_INFO_END, verification, mobileNumber);
			mJDialog = new JOptionPane().createDialog(mScreen, null);
			JLabel jl = new JLabel("正在处理中，请稍等！", JLabel.CENTER);
			mJDialog.setContentPane(jl);
			mJDialog.setVisible(true);
			break;
		case TakeBagControl.MSG_CANCEL:
			sendMessage(TakeBagControl.MSG_CANCEL);
			break;
		default:
			break;
		}
	}

	@Override
	public void onNotify(int messageId, String... message) {
		mJDialog.setVisible(false);      
	}
}
