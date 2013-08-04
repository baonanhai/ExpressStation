package com.expressstation.view.employee;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.expressstation.control.EmployeeControl;
import com.expressstation.control.NotifyAble;
import com.expressstation.view.ParentPage;

/**
 * 输入快递信息界面
 * 
 * @author 狄贤俊
 * 
 *         2013-7-27
 */
public class EmployeeInfoPage extends ParentPage {
	private static final int STATE_ACCEPT_BAG = 1;
	private static final int STATE_TAKE_BAG = 2;
	private static final int STATE_LEFT_BAG = 3;

	private JTable acceptBagTable;
	private JTable getBagTable;
	private JTable leftBagTable;
	private JButton mAccpetButton;
	private JButton mTakeButton;
	private JButton mLeftButton;

	private CardLayout mCardLayout;
	private JPanel mContentPanel;

	private int mState ;

	public EmployeeInfoPage(JFrame screen, NotifyAble notifyAble) {
		super(screen, notifyAble);
	}

	@Override
	protected void initPage(Container content) {
		content.setLayout(new BorderLayout());
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		content.add(titlePanel, BorderLayout.NORTH);

		mAccpetButton = new JButton("揽件");
		mAccpetButton.setActionCommand(STATE_ACCEPT_BAG + "");
		mAccpetButton.addActionListener(this);
		titlePanel.add(mAccpetButton);

		mTakeButton = new JButton("取件");
		mTakeButton.setActionCommand(STATE_TAKE_BAG + "");
		mTakeButton.addActionListener(this);
		titlePanel.add(mTakeButton);

		mLeftButton = new JButton("遗留件");
		mLeftButton.setActionCommand(STATE_LEFT_BAG + "");
		mLeftButton.addActionListener(this);
		titlePanel.add(mLeftButton);

		mCardLayout = new CardLayout();
		mContentPanel = new JPanel(mCardLayout);
		content.add(mContentPanel, BorderLayout.CENTER);

		acceptBagTable = new JTable();
		String[] columnNames = { "快递单号", "发往", "已扫件" };
		Object[][] tableData = { { "000012", "XX省XX市", new Boolean(true) },
				{ "000032", "YY省YY市", new Boolean(false) } };
		acceptBagTable.setModel(new MyTableModel(tableData, columnNames));
		JScrollPane pane = new JScrollPane(acceptBagTable);
		mContentPanel.add(pane, STATE_ACCEPT_BAG + "");

		getBagTable = new JTable();
		String[] columnNames1 = { "快递单号", "发往", "已扫件" };
		Object[][] tableData1 = { { "000012", "XX省XX市", new Boolean(true) },
				{ "000032", "YY省YY市", new Boolean(false) } };
		getBagTable.setModel(new MyTableModel(tableData1, columnNames1));
		JScrollPane pane1 = new JScrollPane(getBagTable);
		mContentPanel.add(pane1, STATE_TAKE_BAG + "");

		leftBagTable = new JTable();
		String[] columnNames2 = { "快递单号", "发往" };
		Object[][] tableData2 = { { "000012", "XX省XX市"},
				{ "000032", "YY省YY市"} };
		leftBagTable.setModel(new MyTableModel(tableData2, columnNames2));
		JScrollPane pane2 = new JScrollPane(leftBagTable);
		mContentPanel.add(pane2, STATE_LEFT_BAG + "");

		JButton okButton = new JButton("退出");
		okButton.setActionCommand(EmployeeControl.MSG_END + "");
		okButton.addActionListener(this);
		JPanel okPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		okPanel.add(okButton);
		content.add(okPanel, BorderLayout.SOUTH);

		mState = STATE_ACCEPT_BAG;
		resetUI();
	}

	private void resetUI() {
		switch (mState) {
		case STATE_ACCEPT_BAG:
			mAccpetButton.setEnabled(false);
			mTakeButton.setEnabled(true);
			mLeftButton.setEnabled(true);
			break;
		case STATE_TAKE_BAG:
			mAccpetButton.setEnabled(true);
			mTakeButton.setEnabled(false);
			mLeftButton.setEnabled(true);
			break;
		case STATE_LEFT_BAG:
			mAccpetButton.setEnabled(true);
			mTakeButton.setEnabled(true);
			mLeftButton.setEnabled(false);
			break;
		default:
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (Integer.parseInt(arg0.getActionCommand())) {
		case STATE_ACCEPT_BAG:
			mState = STATE_ACCEPT_BAG;
			sendMessage(EmployeeControl.MSG_ACCEPT_BAGS);
			break;
		case STATE_TAKE_BAG:
			mState = STATE_TAKE_BAG;
			sendMessage(EmployeeControl.MSG_DESPATCH_BAGS);
			break;
		case STATE_LEFT_BAG:
			mState = STATE_LEFT_BAG;
			sendMessage(EmployeeControl.MSG_LEFT_BAGS);
			break;
		case EmployeeControl.MSG_END:
			sendMessage(EmployeeControl.MSG_END);
			break;
		default:
			break;
		}
		resetUI();
		mCardLayout.show(mContentPanel, mState + "");
	}

	/**
	 * 这边会获取到服务器上的包裹数据，把这边获取到的数据显示到界面
	 */
	@Override
	public void onNotify(int messageId, String... message) {
		switch (messageId) {
		case EmployeeControl.MSG_BAG_INFO:
			System.out.print("MSG_BAG_INFO:" + message[0]);
			break;
		default:
			break;
		}
	}

	class MyTableModel extends DefaultTableModel {
		private static final long serialVersionUID = 1L;

		public MyTableModel(Object[][] data, String[] columns) {
			super(data, columns);
		}

		public boolean isCellEditable(int row, int column) { // 设置Table单元格是否可编辑
			if (column == 0)
				return true;
			return false;
		}

		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 2) {
				return Boolean.class;
			}
			return Object.class;
		}
	}
}
