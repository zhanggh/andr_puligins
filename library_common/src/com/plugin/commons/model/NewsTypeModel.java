package com.plugin.commons.model;

import java.io.Serializable;
import java.util.List;

import com.zq.types.StBaseType;

public class NewsTypeModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1909225431898093055L;
	private String id;
	private String name;
	private String type;//0图文新闻，1图片新闻，2视频新闻
	private String hassub;//是否有子文章(主题类的文章有子文章)
	private List<NewsTypeModel>subtypes;
	private String parentid;//父id
	private String ext;//拓展信息
	private String outurl;// 
	private String openreply;//是否可以评论，1可以 0否
	
	 
	
	public String getOpenreply() {
		return openreply;
	}
	public void setOpenreply(String openreply) {
		this.openreply = openreply;
	}
	public String getOuturl() {
		return outurl;
	}
	public void setOuturl(String outurl) {
		this.outurl = outurl;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHassub() {
		return hassub;
	}
	public void setHassub(String hassub) {
		this.hassub = hassub;
	}
	public List<NewsTypeModel> getSubtypes() {
		return subtypes;
	}
	public void setSubtypes(List<NewsTypeModel> subtypes) {
		this.subtypes = subtypes;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
}
