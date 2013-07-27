package com.expressstation.view.take;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.expressstation.control.NotifyAble;
import com.expressstation.control.TakeBagControl;
import com.expressstation.view.ParentPage;

/**
 * 身份证获取界面，空白界面，设置个背景图片就好
 * @author 狄贤俊
 *
 * 2013-7-22
 */
public class PersonInfoPage extends ParentPage {
	private JLabel mTiplabel;
	private JButton mRetryButton;
	private JButton mOkButton;
	
	private String mPersonID;
	
	public PersonInfoPage(JFrame screen, NotifyAble notifyAble) {
		super(screen, notifyAble);
	}

	@Override
	protected void initPage(Container content) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		content.setLayout(gridBagLayout);
		
		mTiplabel = new JLabel();
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		gbc_label.gridwidth = 2;
		gbc_label.weightx = 1;
		gbc_label.weighty = 1;
		content.add(mTiplabel, gbc_label);
		
		mRetryButton = new JButton("重试");
		GridBagConstraints retryConstraints = new GridBagConstraints();
		retryConstraints.gridx = 0;
		retryConstraints.gridy = 1;
		retryConstraints.weightx = 1;
		retryConstraints.insets = new Insets(0, 0, 10, 0);
		content.add(mRetryButton, retryConstraints);
		mRetryButton.setActionCommand("" + TakeBagControl.MSG_PERSON_INFO_RETRY);
		mRetryButton.addActionListener(this);
		
		mOkButton = new JButton("确定");
		GridBagConstraints okConstraints = new GridBagConstraints();
		okConstraints.gridx = 1;
		okConstraints.gridy = 1;
		okConstraints.weightx = 1;
		okConstraints.insets = new Insets(0, 0, 10, 0);
		content.add(mOkButton, okConstraints);
		mOkButton.setActionCommand("" + TakeBagControl.MSG_PERSON_INFO_CONFIRM_END);
		mOkButton.addActionListener(this);
		
		resetUI();
	}

	@Override
	public void onNotify(int messageId, String... message) {
		switch (messageId) {
		case TakeBagControl.MSG_PERSON_INFO_END:
			mPersonID = message[0];
			mTiplabel.setText("您的身份证号码为：" + message[0]);
			mRetryButton.setVisible(true);
			mOkButton.setVisible(true);
			break;
		default:
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (Integer.parseInt(arg0.getActionCommand())) {
		case TakeBagControl.MSG_PERSON_INFO_RETRY:
			resetUI();
			sendMessage(TakeBagControl.MSG_PERSON_INFO_RETRY);
			break;
		case TakeBagControl.MSG_PERSON_INFO_CONFIRM_END:
			sendMessage(TakeBagControl.MSG_PERSON_INFO_CONFIRM_END, mPersonID);
			break;
		default:
			break;
		}
	}
	
	private void resetUI() {
		mTiplabel.setText("请在读卡器位置放上身份证,谢谢");
		mRetryButton.setVisible(false);
		mOkButton.setVisible(false);
	}
}
