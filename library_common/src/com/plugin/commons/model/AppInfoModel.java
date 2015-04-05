package com.plugin.commons.model;

import java.io.Serializable;

import com.plugin.commons.helper.FuncUtil;
import com.zq.types.StBaseType;

/**
 * app版本信息
 */
public class AppInfoModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8988329042286265562L;
	private String id;//id
	private String version;//最新版本号
	private String path;//更新文件url
	private String info;//更新日志
	String name;//名称
	String logo;//logo url
	String appfile;//app文件地址
	String desc;//简介
	private int taskId;//下载任务编号
	private int status=0;//下载状态 0未下载，1下载中，2下载完毕
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersion() {
		if(FuncUtil.isEmpty(version)){
			version="01";
		}
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getAppfile() {
		return appfile;
	}
	public void setAppfile(String appfile) {
		this.appfile = appfile;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
