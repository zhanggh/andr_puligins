package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

public class UserInfoModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7620007368166790832L;
	private String phone;
	private String userid;
	private String username;
	private String photo;
	private String sessionid;
	private String email;
	private String identity;
	
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
}
