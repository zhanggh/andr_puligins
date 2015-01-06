package com.plugin.commons.model;

import java.io.Serializable;

import com.plugin.commons.helper.FuncUtil;
import com.zq.types.StBaseType;

public class NewsInfoModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4317066544080696415L;
	private String id;
	private String img;
	private String title;
	private String url;
	private String content;
	private String descition;
	private String replycount;
	private String location;
	private String newtype;
	private String arttype;
	private String subtypename;
	private String videourl;
	private String createtime;
	private String openreply;
	private int viewTimes;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescition() {
		return descition;
	}
	public void setDescition(String descition) {
		this.descition = descition;
	}
	public String getReplycount() {
		if(FuncUtil.isEmpty(replycount)){
			replycount="0";
		}
		return replycount;
	}
	public void setReplycount(String replycount) {
		this.replycount = replycount;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getNewtype() {
		return newtype;
	}
	public void setNewtype(String newtype) {
		this.newtype = newtype;
	}
	public String getArttype() {
		return arttype;
	}
	public void setArttype(String attype) {
		this.arttype = attype;
	}
	public String getSubtypename() {
		return subtypename;
	}
	public void setSubtypename(String subtypename) {
		this.subtypename = subtypename;
	}
	public String getVideourl() {
		return videourl;
	}
	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getOpenreply() {
		return openreply;
	}
	public void setOpenreply(String openreply) {
		this.openreply = openreply;
	}
	public int getViewTimes() {
		return viewTimes;
	}
	public void setViewTimes(int viewTimes) {
		this.viewTimes = viewTimes;
	}
}
