package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

public class JpushModel implements StBaseType,Serializable{
	private String type;  //0系统通知，1新闻
	private String title; // 新闻标题，当type为1时填
	private String content; //新闻简介，当type为1时填
	private String id; //新闻id，当type为1时填
	private String arttype; //新闻大类，当type为1时填
	private String subtype; //新闻子类，当type为1时填
	private String showurl; //详情url，当type为1时填
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArttype() {
		return arttype;
	}
	public void setArttype(String arttype) {
		this.arttype = arttype;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getShowurl() {
		return showurl;
	}
	public void setShowurl(String showurl) {
		this.showurl = showurl;
	}
	
}
