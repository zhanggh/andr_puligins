package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

public class MenuModel implements StBaseType,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4999302011438445468L;

	
	public String name;
	public String code;
	public int icon;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
}
