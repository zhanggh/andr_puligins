package com.plugin.commons.service;

import com.plugin.commons.model.RspResultModel;



public interface SpecialService {
	
	/**
	 * 获取特产列表
	 * @Description:
	 * @param start
	 * @param size
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-9-19 下午2:40:54
	 */
	public RspResultModel getSpecialList(String start,String size);
	
	/**
	 * 获取特产详情
	 * @Description:
	 * @param id
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-9-19 下午2:41:23
	 */
	public RspResultModel getSpecialDetail(String id); 
	
}
