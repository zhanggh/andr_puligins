package com.plugin.commons.model;

import java.io.Serializable;

import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.FuncUtil;
import com.zq.types.StBaseType;

/**
 * @author zhang
 *	我评论的新闻
 */
public class Reply implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6853394043349170190L;
	String recontent;
	String replytime;
	String art_title;
	String art_showurl;
	String art_type;
	String art_id;
	String replycount;
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
	public String getArt_title() {
		return art_title;
	}
	public void setArt_title(String art_title) {
		this.art_title = art_title;
	}
	public String getArt_showurl() {
		return art_showurl;
	}
	public void setArt_showurl(String art_showurl) {
		this.art_showurl = art_showurl;
	}
	public String getArt_type() {
		return art_type;
	}
	public void setArt_type(String art_type) {
		this.art_type = art_type;
	}
	public String getArt_id() {
		return art_id;
	}
	public void setArt_id(String art_id) {
		this.art_id = art_id;
	}
	public String getReplycount() {
		if(FuncUtil.isEmpty(replycount)){
			replycount="1";
		}
		return replycount;
	}
	public void setReplycount(String replycount) {
		this.replycount = replycount;
	}

}
