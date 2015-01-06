package com.plugin.commons.model;

import java.io.Serializable;

import com.plugin.commons.helper.ComUtil;
import com.zq.types.StBaseType;

public class SysNoticeModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3465750216590669201L;
	private String id;//id
	private String userid;//用户ID
	private String username;// 用户名
	private String createtime;// 通知时间
	private String content;// 通知内容
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCreatetime() {
		return ComUtil.getTimeIntervalString(createtime);
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
