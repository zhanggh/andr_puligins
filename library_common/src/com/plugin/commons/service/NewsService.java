package com.plugin.commons.service;

import java.util.List;

import com.plugin.commons.AppException;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;



public interface NewsService {
	/**
	 * 初始化新闻类别
	 * @Description:
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-9-18 下午3:33:40
	 */
	public RspResultModel initNewsType();
	/**
	 * 获取政务新闻类别
	 * @Description:
	 * @return
	 * List<NewsSubTypeModel>
	 * @exception:
	 * @author: vinci
	 * @throws AppException 
	 * @time:2014-8-27 下午7:42:58
	 */
	public NewsTypeModel getNewsType(String attype);
	
	/**
	 * 获取政务新闻列表
	 * @Description:
	 * @param cache
	 * @param start
	 * @param size
	 * @param attype
	 * @param subattype
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-8-27 下午7:46:05
	 */
	public RspResultModel getNewsList(boolean cache,String start,String size,String attype,String subattype);
	
	
	
	/**
	 * 评论新闻
	 * @param articleid
	 * @param replycontent
	 * @return
	 */
	public RspResultModel pubNewComment(String articleid,String replycontent,String attype);
	
	
	/**
	 * 评论列表
	 * @param type
	 * @return
	 */
	public RspResultModel commentsList(boolean iscache,String id,String type,String start,String size,String attype);
	
	/**
	 * 头条新闻
	 * @Description:
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-9-18 下午5:13:04
	 */
	public RspResultModel homeList(boolean cache);
	
	public RspResultModel getSubNewsList(boolean cache,String start,String size,String attype,String subattype,String id);
	
	public RspResultModel getPicNewsDetail(String attype,String id);
	
	/**
	 * 获取我评论的新闻
	 * @param content
	 * @return
	 */
	public RspResultModel getMyCmNewList(boolean iscache,String start,String size);
	
	public List<NewsTypeModel> getNewsTypes();
	
	/**
	 * 地区列表
	 * @return
	 */
	public RspResultModel getAreaList();
	/**
	 * 地区app推荐列表
	 * @return
	 */
	public RspResultModel getAreaRecList(String areaId);
	/**
	 * 行业app推荐列表
	 * @return
	 */
	public RspResultModel getIndustryRecList();
	/**
	 *热门app推荐列表
	 * @return
	 */
	public RspResultModel getHotRecList();
}
