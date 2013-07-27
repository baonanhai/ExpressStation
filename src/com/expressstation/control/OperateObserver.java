package com.expressstation.control;

/**
 * 用于下层控制器向上层控制器反馈信息
 * @author 狄贤俊
 *
 * 2013-7-27
 */
public interface OperateObserver {
	public void operateEnd();
}
