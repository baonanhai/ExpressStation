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
import java.util.Enumeration;

/**
 * 串口交互模块，以及输入输出需要的16进制转换方法
 * 
 * @author 狄贤俊
 * 
 *         2013-7-28
 */
public class HardWareTools {

	private SerialReader mSerialReader;
	private SerialWriter mSerialWriter;
	private SerialPort mSerialPort;

	private ResultNotify resultNotify;

	private String portName;

	/**
	 * 初始化串口
	 * 
	 * @param portName
	 *            串口名，windows下估计是com1、com2之类
	 * @param resultNotify
	 *            回调需要的观察者
	 */
	public HardWareTools(String portName, ResultNotify resultNotify) {
		this.portName = portName;
		this.resultNotify = resultNotify;
	}

	/**
	 * 打开连接
	 * 
	 * @param command
	 *            发送给串口的指令
	 * @param mBoudRate
	 *            波特率
	 */
	public void connect(byte[] command, int mBoudRate) {
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
						mSerialPort.setSerialPortParams(mBoudRate,
								SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);

						InputStream in = mSerialPort.getInputStream();
						OutputStream out = mSerialPort.getOutputStream();

						mSerialReader = new SerialReader(in, resultNotify);
						new Thread(mSerialReader).start();
						mSerialWriter = new SerialWriter(out, command);
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

	public void setCommand(byte[] command) {
		mSerialWriter.setCommand(command);
	}

	public void setDelayTime(int delayTime) {
		mSerialWriter.setDelayTime(delayTime);
	}

	public void stop() {
		mSerialWriter.stop();
		mSerialReader.stop();
		mSerialPort.close();
	}

	/**
	 *  打印所有串口名，用于不知道用哪个串口的时候看下
	 */
	public void listSerialPort() {
		Enumeration<?> enumeration = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier commPortIdentifier = null;
		while (enumeration.hasMoreElements()) {
			commPortIdentifier = (CommPortIdentifier) enumeration.nextElement();
			System.out.println(commPortIdentifier.getName());
		}
	}

	/** 串口输出工具，结果通过ResultNotify这个接口回调 */
	public static class SerialReader implements Runnable {
		private InputStream in;
		private boolean flag = true;
		private ResultNotify resultNotify;

		public SerialReader(InputStream in, ResultNotify resultNotify) {
			this.in = in;
			this.resultNotify = resultNotify;
		}

		public void stop() {
			flag = false;
		}

		public void run() {
			byte[] buffer = new byte[1024];
			int len = -1;
			try {
				while (flag) {
					len = this.in.read(buffer);
					resultNotify.handleResult(HardWareTools.byte2HexStr(buffer,
							len));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** 串口写入工具，需要制定周期延时，以及指令 */
	public static class SerialWriter implements Runnable {
		private OutputStream out;
		private boolean flag = true;
		private byte[] command;
		private int delayTime = 1000;

		public SerialWriter(OutputStream out, byte[] command) {
			this.out = out;
			this.command = command;
		}

		public void stop() {
			flag = false;
		}

		public void setCommand(byte[] command) {
			this.command = command;
		}

		public void setDelayTime(int delayTime) {
			this.delayTime = delayTime;
		}

		public void run() {
			try {
				while (flag) {
					this.out.write(command);
					Thread.sleep(delayTime);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static byte[] hexStringToBytes(String hexString) {
		if ((hexString == null) || (hexString.equals(""))) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; ++i) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[(pos + 1)]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	// 字节数组转换成16进制串
	public static String byte2HexStr(byte[] b, int length) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < length; ++n) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
}
