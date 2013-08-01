package com.expressstation.view.employee;

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

import com.expressstation.control.EmployeeControl;
import com.expressstation.control.NotifyAble;
import com.expressstation.view.ParentPage;

/**
 * 雇员模式登录界面
 * 
 * @author 狄贤俊
 * 
 *         2013-7-24
 */
public class LoginPage extends ParentPage {
	private JTextField mAccountField;
	private JTextField mPasswordField;
	private JDialog mJDialog;
	private JLabel mTip;
	private JFrame mScreen;

	public LoginPage(JFrame screen, NotifyAble notifyAble) {
		super(screen, notifyAble);
		mScreen = screen;
	}

	@Override
	protected void initPage(Container content) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0 };
		content.setLayout(gridBagLayout);

		JLabel lblNewLabel_1 = new JLabel("请登录");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		content.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("账号：");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		content.add(lblNewLabel, gbc_lblNewLabel);

		mAccountField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		content.add(mAccountField, gbc_textField);
		mAccountField.setColumns(5);

		JLabel label = new JLabel("密码：");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 2;
		content.add(label, gbc_label);

		mPasswordField = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 2;
		content.add(mPasswordField, gbc_textField_1);
		mPasswordField.setColumns(10);

		JButton btnNewButton = new JButton("确定");
		btnNewButton.setActionCommand(EmployeeControl.MSG_LOGIN_INFO_START + "");
		btnNewButton.addActionListener(this);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		content.add(btnNewButton, gbc_btnNewButton);

		JButton btnNewButton_1 = new JButton("取消");
		btnNewButton_1.setActionCommand(EmployeeControl.MSG_CANCEL + "");
		btnNewButton_1.addActionListener(this);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 3;
		content.add(btnNewButton_1, gbc_btnNewButton_1);
	}

	@Override
	public void onNotify(int messageId, String... message) {
		mJDialog.setVisible(false);   
		switch (messageId) {
		case EmployeeControl.MSG_LOGIN_INFO_END:
			//这个消息发过来只是为了关闭提示框
			break;
		case EmployeeControl.MSG_LOGIN_ERROR:
			JOptionPane.showMessageDialog(mScreen, "账号或密码错误。请重试！");
			mAccountField.setText("");
			mPasswordField.setText("");
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (Integer.parseInt(arg0.getActionCommand())) {
		case EmployeeControl.MSG_LOGIN_INFO_START:
			String account = mAccountField.getText();
			String password = mPasswordField.getText();
			sendMessage(EmployeeControl.MSG_LOGIN_INFO_START, account, password);
			mJDialog = new JOptionPane().createDialog(mScreen, null);
			mTip = new JLabel("正在处理中，请稍等！", JLabel.CENTER);
			mJDialog.setContentPane(mTip);
			mJDialog.setVisible(true);
			break;
		case EmployeeControl.MSG_CANCEL:
			sendMessage(EmployeeControl.MSG_CANCEL);
			break;
		default:
			break;
		}
	}
}
