package com.expressstation.view.accpet;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.expressstation.control.AcceptBagControl;
import com.expressstation.control.NotifyAble;
import com.expressstation.view.ParentPage;

public class WeightPage extends ParentPage {
	private JLabel mWeightlabel;
	private JButton mRetryButton;
	private JButton mOkButton;
	
	private String mWeight;
	
	public WeightPage(JFrame screen, NotifyAble notifyAble) {
		super(screen, notifyAble);
	}
	
	protected void initPage(Container content) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		content.setLayout(gridBagLayout);
		
		mWeightlabel = new JLabel();
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		gbc_label.gridwidth = 2;
		gbc_label.weightx = 1;
		gbc_label.weighty = 1;
		content.add(mWeightlabel, gbc_label);
		
		mRetryButton = new JButton("重秤");
		GridBagConstraints retryConstraints = new GridBagConstraints();
		retryConstraints.gridx = 0;
		retryConstraints.gridy = 1;
		retryConstraints.weightx = 1;
		retryConstraints.insets = new Insets(0, 0, 10, 0);
		content.add(mRetryButton, retryConstraints);
		mRetryButton.setActionCommand("" + AcceptBagControl.MSG_WEIGHT_RETRY);
		mRetryButton.addActionListener(this);
		
		mOkButton = new JButton("确定");
		GridBagConstraints okConstraints = new GridBagConstraints();
		okConstraints.gridx = 1;
		okConstraints.gridy = 1;
		okConstraints.weightx = 1;
		okConstraints.insets = new Insets(0, 0, 10, 0);
		content.add(mOkButton, okConstraints);
		mOkButton.setActionCommand("" + AcceptBagControl.MSG_WEIGHT_CONFIRM_END);
		mOkButton.addActionListener(this);
		
		resetUI();
	}

	@Override
	public void onNotify(int messageId, String... message) {
		mWeight = message[0];
		mWeightlabel.setText("您的包裹为" + mWeight + "千克");
		mRetryButton.setVisible(true);
		mOkButton.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.print(arg0.getActionCommand());
		switch (Integer.parseInt(arg0.getActionCommand())) {
		case AcceptBagControl.MSG_WEIGHT_RETRY:
			resetUI();
			sendMessage(AcceptBagControl.MSG_WEIGHT_RETRY);
			break;
		case AcceptBagControl.MSG_WEIGHT_CONFIRM_END:
			sendMessage(AcceptBagControl.MSG_WEIGHT_CONFIRM_END, mWeight);
			break;
		default:
			break;
		}
	}
	
	private void resetUI() {
		mWeightlabel.setText("请把包裹放到秤重器上，谢谢！");
		mRetryButton.setVisible(false);
		mOkButton.setVisible(false);
	}
}
