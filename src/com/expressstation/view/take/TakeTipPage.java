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

public class TakeTipPage extends ParentPage{
	public TakeTipPage(JFrame screen, NotifyAble notifyAble) {
		super(screen, notifyAble);
	}

	@Override
	protected void initPage(Container content) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		content.setLayout(gridBagLayout);
		
		JLabel lblxxxxxx = new JLabel("您的包裹在XX行XX列XX号箱子，请取出包裹后把快递单留在箱中，谢谢");
		GridBagConstraints gbc_lblxxxxxx = new GridBagConstraints();
		gbc_lblxxxxxx.insets = new Insets(0, 0, 5, 0);
		gbc_lblxxxxxx.gridx = 0;
		gbc_lblxxxxxx.gridy = 0;
		content.add(lblxxxxxx, gbc_lblxxxxxx);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addActionListener(this);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		content.add(btnNewButton, gbc_btnNewButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		sendMessage(TakeBagControl.MSG_CANCEL);
	}

	@Override
	public void onNotify(int messageId, String... message) {
		// TODO Auto-generated method stub
		
	}

}
