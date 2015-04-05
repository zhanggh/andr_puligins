package com.plugin.commons.model;

import java.io.Serializable;
import java.util.List;

import com.zq.types.StBaseType;

/**
 * 视听播放的模型
 */
public class RadioVideoModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8988329042286265562L;
	private String columnid;	//栏目编号
	private String cname;//栏目中文名称
	private String ename;//栏目英文名称
	private String Ename;//栏目英文名称
	private String description;//栏目简介
	private String logo;//栏目图表
	private String fatherid;//父栏目编号 为0时说明这个栏目是一级栏目
//	private String column;//此栏目下的栏目数据
	private String Id;//需要查询的栏目编号 测试：3
	private String Resid;//资源编号
//	private String Columnid;//资源所属栏目
	private String Musavepath;//播放地址
	private String Duration;//资源简介
	private String Rename;//资源名称
	private String Muauthor;//主播
	private String Number;//排位序号
	private String Num;//播放次数
	private String Playimage;//播放页面图片（如果没有播放页面图片就在播放页展示首页图片）
	
	private List<RadioVideoModel> column;
	private List<ImgurlModel> Imgurl;//首页
	
	
	public String getColumnid() {
		return columnid;
	}
	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getFatherid() {
		return fatherid;
	}
	public void setFatherid(String fatherid) {
		this.fatherid = fatherid;
	}
	 
	public List<RadioVideoModel> getColumn() {
		return column;
	}
	public void setColumn(List<RadioVideoModel> column) {
		this.column = column;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getResid() {
		return Resid;
	}
	public void setResid(String resid) {
		Resid = resid;
	}
	public String getMusavepath() {
		return Musavepath;
	}
	public void setMusavepath(String musavepath) {
		Musavepath = musavepath;
	}
	 
	public List<ImgurlModel> getImgurl() {
		return Imgurl;
	}
	public void setImgurl(List<ImgurlModel> imgurl) {
		Imgurl = imgurl;
	}
	public String getDuration() {
		return Duration;
	}
	public void setDuration(String duration) {
		Duration = duration;
	}
	public String getRename() {
		return Rename;
	}
	public void setRename(String rename) {
		Rename = rename;
	}
	public String getMuauthor() {
		return Muauthor;
	}
	public void setMuauthor(String muauthor) {
		Muauthor = muauthor;
	}
	public String getNumber() {
		return Number;
	}
	public void setNumber(String number) {
		Number = number;
	}
	public String getNum() {
		return Num;
	}
	public void setNum(String num) {
		Num = num;
	}
	public String getPlayimage() {
		return Playimage;
	}
	public void setPlayimage(String playimage) {
		Playimage = playimage;
	}
	
}
