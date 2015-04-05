package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

/**
 * 瞬间播放那个奇葩的model
 */
public class ImgurlModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8988329042286265562L;
	private String Imageid;//id
	private String Title;// 
	private String Description;// 
	private String Isonline;// 
	private String Imgurl;// 
	private String Fatherid;// 
	public String getImageid() {
		return Imageid;
	}
	public void setImageid(String imageid) {
		Imageid = imageid;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getIsonline() {
		return Isonline;
	}
	public void setIsonline(String isonline) {
		Isonline = isonline;
	}
	public String getImgurl() {
		return Imgurl;
	}
	public void setImgurl(String imgurl) {
		Imgurl = imgurl;
	}
	public String getFatherid() {
		return Fatherid;
	}
	public void setFatherid(String fatherid) {
		Fatherid = fatherid;
	}
}
