package com.expressstation.control.hardware;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;

import com.expressstation.control.NotifyAble;
import com.expressstation.control.TakeBagControl;
import com.expressstation.control.hardware.HardWareTools.SerialReader;
import com.expressstation.control.hardware.HardWareTools.SerialWriter;

public class IdentityCardAdapter extends ParentAdapter implements ResultNotify {
	// 两次查询之间的延时
	private static final int DELAY_TIME = 2000;
	// 获取卡号的指令
	private static final byte[] COMMAND_GET_ID = HardWareTools
			.hexStringToBytes("400700010000000d");
	
	private StringBuilder mStringBuilder;
	
	private SerialReader mSerialReader;
	private SerialWriter mSerialWriter;
	private SerialPort mSerialPort;

	public IdentityCardAdapter(NotifyAble notifyAble) {
		super(notifyAble);
	}

	@Override
	public void open() {
		/*
		 * Enumeration<?> enumeration = CommPortIdentifier.getPortIdentifiers();
		 * CommPortIdentifier commPortIdentifier = null; while
		 * (enumeration.hasMoreElements()) { commPortIdentifier =
		 * (CommPortIdentifier) enumeration.nextElement();
		 * System.out.println(commPortIdentifier.getName()); }
		 */
		connect("/dev/ttyUSB0");
	}

	private void connect(String portName) {
		CommPortIdentifier portIdentifier;
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			if (portIdentifier.isCurrentlyOwned()) {
				System.out.println("Error: Port is currently in use");
			} else {
				CommPort commPort;
				try {
					commPort = portIdentifier.open(this.getClass().getName(),
							2000);
					if (commPort instanceof SerialPort) {
						mSerialPort = (SerialPort) commPort;
						mSerialPort.setSerialPortParams(115200,
								SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);

						InputStream in = mSerialPort.getInputStream();
						OutputStream out = mSerialPort.getOutputStream();

						mSerialReader = new SerialReader(in, this);
						new Thread(mSerialReader).start();
						mSerialWriter = new SerialWriter(out, DELAY_TIME,
								COMMAND_GET_ID);
						new Thread(mSerialWriter).start();
					} else {
						System.out
								.println("Error: Only serial ports are handled by this app.");
					}
				} catch (PortInUseException e) {
					e.printStackTrace();
				} catch (UnsupportedCommOperationException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (NoSuchPortException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void handleResult(String result) {
		if (mStringBuilder == null) {
			mStringBuilder = new StringBuilder();
		}
		mStringBuilder.append(result);

		if (mStringBuilder.toString().endsWith("0D")) { // 遇到结束符
			result = mStringBuilder.toString();
			System.out.println("result:" + result);
			if (result.startsWith("40")) {
				if (result.length() > 6) {
					if (result.substring(4, 6).equals("00")) {
						String data = result.substring(10, result.length() - 6);
						String hexId = data.substring(8);
						System.out.println("data:" + data);
						System.out.println("hexId:" + hexId);
						sendMessage(TakeBagControl.MSG_PERSON_INFO_END, ""
								+ new BigInteger(hexId, 16));
						mSerialReader.stop();
						mSerialWriter.stop();
						mSerialPort.close();
					} else {
						// 错误处理
					}
				}
			} else {
				// 错误处理
			}
			mStringBuilder.setLength(0); // 清除数据
		}
	}
}
