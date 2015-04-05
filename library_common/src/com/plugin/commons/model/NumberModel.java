package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

public class NumberModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4383295062573551869L;
	private String id;//id
	private String phone;//电话号码
	private String name;// 联系人
	private String addr;// 地址
	private String reserved;// 保留字段
	private String img;
	private String type;

	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	 
}
