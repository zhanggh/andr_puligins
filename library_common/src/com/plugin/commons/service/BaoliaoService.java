package com.plugin.commons.service;

import java.io.InputStream;

import com.plugin.commons.AppException;
import com.plugin.commons.model.CommentModel;
import com.plugin.commons.model.RspResultModel;


public interface BaoliaoService {

	/**
	 * 获取网友爆料列表
	 * @param size
	 * @return
	 */
	public RspResultModel getNetfBliaoList(boolean iscache,String start,String size);
	
	/**
	 * 【我的】-问政
	 * @param iscache
	 * @param start
	 * @param size
	 * @return
	 */
	public RspResultModel politicsMyAsk(boolean iscache,String start, String size);
	/**
	 * 爆料详情
	 * @param id
	 * @return
	 */
	public RspResultModel getBaoliaoDetail(boolean iscache,String id);
	
	
	/**
	 * 我的爆料列表
	 * @param sessionid
	 * @param start
	 * @param size
	 * @return
	 */
	public RspResultModel getMyBaoliaoList(String type,boolean iscache,String sessionid,String start,String size);
	
	
	/**
	 * 发布爆料
	 * @param blmodel
	 * @return
	 */
	public RspResultModel pubBaoliaoInfo(String content,
			String picname1, InputStream pic1, String picname2,
			InputStream pic2, String picname3, InputStream pic3,
			String audioname, InputStream audio, String vidioname,
			InputStream vidio);
	 
	
	
	/**
	 * 评论爆料
	 * @param cm
	 * @return
	 */
	public RspResultModel commentBaoliao(CommentModel cm,String sessionid);
	
	/**
	 * 我的- 我的收藏
	 * @return
	 * @throws AppException 
	 */
	public RspResultModel colectAll() throws AppException;
	
	/**
	 * 我评论的爆料列表
	 * @return
	 */
	public  RspResultModel getMyReplyBaoliaoList(boolean iscache,String start,String size);
}
