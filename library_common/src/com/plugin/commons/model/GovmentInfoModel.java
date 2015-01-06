package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

public class GovmentInfoModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8561247241524087127L;
	private String id;
	private String photo;
	private String name;
	private String url;
	private String content;
	private String desc;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
