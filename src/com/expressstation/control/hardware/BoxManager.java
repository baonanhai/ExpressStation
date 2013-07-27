package com.expressstation.control.hardware;

/**
 * 管理所有的箱子，需要维护一个本地数据库,偷懒也可以搞个本地文件,或者用序列化来做
 * @author 狄贤俊
 *
 * 2013-7-27
 */
public class BoxManager {
	private static BoxManager mBoxManager;

	private BoxManager() {
		initBoxsState();
	}
	
	public synchronized static BoxManager getInstance() {
		if (mBoxManager == null) {
			mBoxManager = new BoxManager();
		}
		return mBoxManager;
	}

	private void initBoxsState() {
		
	}
}
