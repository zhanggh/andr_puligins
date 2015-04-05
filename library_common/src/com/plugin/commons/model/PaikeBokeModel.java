package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

/**
 * 拍客播客
 */
public class PaikeBokeModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5135659127378845900L;
	/**
	 * 
	 */
	private String id;//id
	private String version;//最新版本号
	private String path;//更新文件url
	private String info;//更新日志
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersion() {
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
}
