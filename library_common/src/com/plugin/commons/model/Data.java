package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

public class Data implements StBaseType,Serializable{
	private String imgUrl;
	private String hrefUrl;
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getHrefUrl() {
		return hrefUrl;
	}
	public void setHrefUrl(String hrefUrl) {
		this.hrefUrl = hrefUrl;
	}
	
}