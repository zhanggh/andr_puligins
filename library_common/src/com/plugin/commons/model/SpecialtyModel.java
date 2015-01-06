package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

/**
 * 报料
 */
public class SpecialtyModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6156861774359619440L;
	private String id;//id
	private String pdtype;//产品类型
	private String createtime;//问政时间
	private String intrduce;//简介
	private String photo;//图片地址
	private String recontent;//答复内容
	private String replytime;//回复时间
	private String pdname;//产品名
	private String pdphoto;//产品图片
	private String viewcount;//查看数
	private String replycount;//
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPdtype() {
		return pdtype;
	}
	public void setPdtype(String pdtype) {
		this.pdtype = pdtype;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getIntrduce() {
		return intrduce;
	}
	public void setIntrduce(String intrduce) {
		this.intrduce = intrduce;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
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
	public String getPdname() {
		return pdname;
	}
	public void setPdname(String pdname) {
		this.pdname = pdname;
	}
	public String getPdphoto() {
		return pdphoto;
	}
	public void setPdphoto(String pdphoto) {
		this.pdphoto = pdphoto;
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
	 
}
