package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

public class SettingFqaModel implements StBaseType ,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3898807317146237817L;

	private String id;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
