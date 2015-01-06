package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

/**
 * @author zhang
 * 新华社启动页面以及新闻页面对接的model
 */
public class XinHuaModel implements StBaseType,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1205422874478385042L;

	
	
	private String retcode;
	private String retmsg;
	private String state;
	public Data data;
	
	public String getState() {
		return state;
	}




	public void setState(String state) {
		this.state = state;
	}




	public Data getData() {
		return data;
	}




	public void setData(Data data) {
		this.data = data;
	}


	


	public String getRetcode() {
		return retcode;
	}




	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}




	public String getRetmsg() {
		return retmsg;
	}




	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}
	
}
