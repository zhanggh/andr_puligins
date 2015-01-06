package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

public class NumberModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4383295062573551869L;
	private String id;//id
	private String telNum;//电话号码
	private String username;// 联系人
	private String address;// 地址
	private String reserved;// 保留字段
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTelNum() {
		return telNum;
	}
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	 
}
