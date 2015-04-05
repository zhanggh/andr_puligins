package com.plugin.commons.service;

import com.plugin.commons.model.CommonModel;
import com.plugin.commons.model.RspResultModel;

/**
 * @author zhang
 *	拍客播客业务处理
 */
public interface PaiKeBoKeService {

	
	/**
	 * 增加拍客信息
	 * @param viewstr
	 * @return
	 */
	public RspResultModel pushPhotos(CommonModel cmmodel);
	/**
	 * 增加播客客信息
	 * @param viewstr
	 * @return
	 */
	public RspResultModel pushVideo(CommonModel cmmodel);
	
}
