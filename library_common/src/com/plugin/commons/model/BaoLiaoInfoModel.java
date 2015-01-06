package com.plugin.commons.model;

import java.io.Serializable;

import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.FuncUtil;
import com.zq.types.StBaseType;

/**
 * 报料
 */
public class BaoLiaoInfoModel implements StBaseType,Serializable{
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -7840623199524110816L;
	private String id;//id
	private String userid;//问政者ID
	private String username;//问政者名称
	private String userphoto;//问证人头像
	private String createtime;//问政时间
	private String content;//问政内容
	private String photo;//图片地址
	private String audio;//音频地址
	private String video;//视频地址
	private String recontent;//答复内容
	private String replytime;//回复时间
	private String orgid;//问政机构号
	private String orgname;//问政机构名称
	private String orgphoto;//机构头像
	private String viewcount;//查看数
	private String replycount;//
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getAudio() {
		return audio;
	}
	public void setAudio(String audio) {
		this.audio = audio;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getRecontent() {
		return recontent;
	}
	public void setRecontent(String recontent) {
		this.recontent = recontent;
	}
	public String getReplytime() {
		return replytime;
	}
	public void setReplytime(String replytime) {
		this.replytime = replytime;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getOrgphoto() {
		return orgphoto;
	}
	public void setOrgphoto(String orgphoto) {
		this.orgphoto = orgphoto;
	}
	public String getViewcount() {
		if(FuncUtil.isEmpty(viewcount)){
			viewcount="0";
		}
		return viewcount;
	}
	public void setViewcount(String viewcount) {
		this.viewcount = viewcount;
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
	 
}
