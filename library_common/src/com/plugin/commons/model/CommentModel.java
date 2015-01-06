package com.plugin.commons.model;

import java.io.Serializable;

import com.plugin.commons.helper.ComUtil;
import com.zq.types.StBaseType;

public class CommentModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3464207867482880705L;
	private int id;//id
	private String userid;//评论者ID
	private String username;// 评论者名称
	private String userphoto;// 评论者头像
	private String createtime;// 评论时间
	private String content;// 评论内容
	private String orgid;// 评论原条目id
	private String orgcontent;// 评论原条目内容
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public String getUserphoto() {
		return userphoto;
	}
	public void setUserphoto(String userphoto) {
		this.userphoto = userphoto;
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
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getOrgcontent() {
		return orgcontent;
	}
	public void setOrgcontent(String orgcontent) {
		this.orgcontent = orgcontent;
	}
	
}
