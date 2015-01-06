package com.plugin.commons.model;

import java.io.Serializable;
import java.util.List;

import com.zq.types.StBaseType;

public class NewsGroupModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7240854461809031414L;
	/**
	 * “id”:”123”,
			“title”:”上海福喜事件”,
			 “img”:”http://xxx/xxx.jpg”,
			“url“：”http://xxx/123.html”，
			“replycount“:”120”,
			“attype”:”01”-新闻所属类型

	 */
	private String id;
	private List<NewsInfoModel> newsList;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<NewsInfoModel> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<NewsInfoModel> newsList) {
		this.newsList = newsList;
	}
}
