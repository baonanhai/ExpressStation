package com.expressstation.control.hardware;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 串口输入输出需要的16进制转换方法
 * @author 狄贤俊
 *
 * 2013-7-28
 */
public class HardWareTools {

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

	//字节数组转换成16进制串
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
	
	/**串口输出工具，结果通过ResultNotify这个接口回调 */
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
					resultNotify.handleResult(HardWareTools.byte2HexStr(buffer, len));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**串口写入工具，需要制定周期延时，以及指令 */
	public static class SerialWriter implements Runnable {
		private OutputStream out;
		private boolean flag = true;
		private int delayTime = 2000;
		private byte[] command;

		public SerialWriter(OutputStream out, int delayTime , byte[] command) {
			this.out = out;
			this.delayTime = delayTime;
			this.command = command;
		}
		
		public void stop() {
			flag = false;
		}
		
		public void setCommand(byte[] command) {
			this.command = command;
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
}
