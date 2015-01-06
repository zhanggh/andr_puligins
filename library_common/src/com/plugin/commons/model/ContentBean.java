package com.plugin.commons.model;

public class ContentBean {
	private String id;
	private String title;
	private String content;
	private int type;
	private String username;
	private String attypeName;//栏目名称
	
	 
	public String getAttypeName() {
		return attypeName;
	}
	public void setAttypeName(String attypeName) {
		this.attypeName = attypeName;
	}
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
