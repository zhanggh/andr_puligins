package com.plugin.commons.model;

import java.io.Serializable;
import java.util.List;

import com.zq.types.StBaseType;

/**
 * app版本信息
 */
public class SWFrontcastModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7998208348629317852L;
	private List<SWFrontcastDayModel> f1;//id
	private String f0 = "";//发布时间
	public List<SWFrontcastDayModel> getF1() {
		return f1;
	}
	public void setF1(List<SWFrontcastDayModel> f1) {
		this.f1 = f1;
	}
	public String getF0() {
		return f0;
	}
	public void setF0(String f0) {
		this.f0 = f0;
	}
}
