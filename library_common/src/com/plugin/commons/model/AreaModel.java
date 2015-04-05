package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

/**
 * 地区信息
 */
public class AreaModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8988329042286265562L;
	private String id;//id
	String name;//名称
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
 
}
