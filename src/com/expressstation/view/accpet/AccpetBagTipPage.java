package com.expressstation.view.accpet;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.expressstation.control.AcceptBagControl;
import com.expressstation.control.NotifyAble;
import com.expressstation.view.ParentPage;

/**
 * 揽件最后提示页面
 * @author 狄贤俊
 *
 * 2013-7-27
 */
public class AccpetBagTipPage extends ParentPage {
	private JLabel tip;
	
	public AccpetBagTipPage(JFrame screen, NotifyAble notifyAble) {
		super(screen, notifyAble);
	}

	@Override
	protected void initPage(Container content) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		content.setLayout(gridBagLayout);
		
		tip = new JLabel("快递单正在打印中，请稍等！");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.HORIZONTAL;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		content.add(tip, gbc_label);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

	@Override
	public void onNotify(int messageId, String... message) {
		switch (messageId) {
		case AcceptBagControl.MSG_BOX_OPEN:
			tip.setText("快递单已打印完毕，请把快递单贴上后放入" + message[0] + "号箱子，谢谢");
			new Thread() {
				public void run() {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sendMessage(AcceptBagControl.MSG_END);
				}
			}.start();
			break;

		default:
			break;
		}
	}
}
