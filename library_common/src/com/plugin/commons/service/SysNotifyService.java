package com.plugin.commons.service;

import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.SysNoticeModel;


 

public interface SysNotifyService {

	/**
	 * 从缓存中获取通知
	 * @return
	 */
	public RspResultModel getNotifyList();
	
	/**
	 * 更新通知列表
	 * @return
	 */
	public boolean updatetNotifyList(SysNoticeModel sysNotic);
	
	
}
