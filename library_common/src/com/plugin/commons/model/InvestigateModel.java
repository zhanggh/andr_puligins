package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

public class InvestigateModel implements StBaseType,Serializable{
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7705148780507747932L;
	private String id;//id
	private String title;//标题
	private String type;// 类型
	private String voteCount;//投票数量
	private String status;// 状态
	private String reserved;// 保留字段
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVoteCount() {
		return voteCount;
	}
	public void setVoteCount(String voteCount) {
		this.voteCount = voteCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	 
}
