package com.plugin.commons.model;

import java.io.Serializable;

import com.plugin.commons.helper.ComUtil;
import com.zq.types.StBaseType;

public class AskMsgModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8987881351032450067L;
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
	private String replycount;//评论数
	private String usertype;
	private String idcard;
	private String phone;
	private String address;
	private String email;
	private String msgtype;
	private String photo1;
	private String photo2;
	private String photo3;
	private String status;//0已审核 1待审核
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
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
		return ComUtil.getTimeIntervalString(replytime);
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
		return viewcount;
	}
	public void setViewcount(String viewcount) {
		this.viewcount = viewcount;
	}
	public String getReplycount() {
		return replycount;
	}
	public void setReplycount(String replycount) {
		this.replycount = replycount;
	}
	public String getPhoto1() {
		return photo1;
	}
	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}
	public String getPhoto2() {
		return photo2;
	}
	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}
	public String getPhoto3() {
		return photo3;
	}
	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}
	
	
}
