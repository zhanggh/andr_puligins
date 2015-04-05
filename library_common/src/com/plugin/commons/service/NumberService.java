package com.plugin.commons.service;

import com.plugin.commons.model.NumberModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.SysNoticeModel;


 

public interface NumberService {

	/**
	 * 获取NumberType列表
	 * @return
	 */
	public RspResultModel getNumTypeList(boolean isCache);
	
	/**
	 * 获取NumberModel列表
	 * @return
	 */
	public RspResultModel getNumList(boolean isCache,String type,String stext,String start,String size);
	
	
	/**
	 * 更新通知列表
	 * @return
	 */
	public boolean updatetNumberList(NumberModel number);
	
	
}
