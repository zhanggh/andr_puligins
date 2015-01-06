package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

public class NewPicItemModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9045705949298683440L;
	/**
	 * “id”:”123”,
			“title”:”上海福喜事件”,
			 “img”:”http://xxx/xxx.jpg”,
			“url“：”http://xxx/123.html”，
			“replycount“:”120”,
			“attype”:”01”-新闻所属类型

	 */
	private String id;
	private String img;
	private String title;
	private String content;
	private String descition;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescition() {
		return descition;
	}
	public void setDescition(String descition) {
		this.descition = descition;
	}
}
