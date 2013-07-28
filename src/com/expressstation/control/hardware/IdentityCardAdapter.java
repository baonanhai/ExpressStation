package com.expressstation.control.hardware;

import java.math.BigInteger;

import com.expressstation.control.NotifyAble;
import com.expressstation.control.TakeBagControl;

public class IdentityCardAdapter extends ParentAdapter implements ResultNotify {
	// 获取卡号的指令
	private static final byte[] COMMAND_GET_ID = HardWareTools
			.hexStringToBytes("400700010000000d");
	
	private static final int BOUD_RATE = 115200;
	
	private StringBuilder mStringBuilder;
	
	private HardWareTools mHardWareTools;
	
	public IdentityCardAdapter(NotifyAble notifyAble) {
		super(notifyAble);
	}

	@Override
	public void open() {
		mHardWareTools = new HardWareTools("/dev/ttyUSB0", this);
		mHardWareTools.connect(COMMAND_GET_ID, BOUD_RATE);
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
						mHardWareTools.stop();
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
