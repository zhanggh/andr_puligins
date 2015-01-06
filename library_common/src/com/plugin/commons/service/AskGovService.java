package com.plugin.commons.service;

import java.io.InputStream;

import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.model.RspResultModel;



public interface AskGovService {
	
	/**
	 * 获取机构列表
	 * @Description:
	 * @param isCache 是否读取缓存数据，如果选择否，优先读取网络，读取网络失败再读取缓存
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-8-27 上午10:13:55
	 */
	public RspResultModel getOrgList(boolean isCache);
	
	/**
	 * 获取机构详情
	 * @Description:
	 * @param orgId
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-8-27 上午10:15:34
	 */
	public RspResultModel getOrgDetail(String orgId);
	
	//问政
	public RspResultModel addAskGov(String orgid, String content, String picname1,InputStream pic1,
			String picname2,InputStream pic2,String picname3,InputStream pic3,String audioname,
			InputStream audio,
			String vidioname,
			InputStream vidio);
	
	//获取问政列表
	public RspResultModel getAskList(boolean isCache,String start,String size,String hot);
	
	/**
	 * 获取我的问政列表
	 * @Description:
	 * @param isCache
	 * @param start
	 * @param size
	 * @param type 0--我的问政 1--我的回答 2--我的关注
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-8-27 下午4:48:36
	 */
	public RspResultModel getMyAskList(boolean isCache,String start,String size,String type);
	
	/**
	 * 机构回答列表
	 * @Description:
	 * @param isCache
	 * @param start
	 * @param size
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-8-27 下午4:50:16
	 */
	public RspResultModel getOrgAnswerList(boolean isCache,String start,String size,String orgid);
	
	/**
	 * 信访
	 * @param mGov
	 * @param content
	 * @param picname1
	 * @param pic1
	 * @param picname2
	 * @param pic2
	 * @param picname3
	 * @param pic3
	 * @param audioname
	 * @param audio
	 * @param vidioname
	 * @param vidio
	 * @return
	 */
	public RspResultModel addAskGovExt(AskMsgModel mGov, String content,
			String picname1, InputStream pic1, String picname2,
			InputStream pic2, String picname3, InputStream pic3,
			String audioname, InputStream audio, String vidioname,
			InputStream vidio);
}
