package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

/**
 * @author zhang
 *	旅游休闲模型
 */
public class TourModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2527172053319477513L;
	private String id;// 
	private String title;// 
	private String content;// 
	private String photo;// 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
