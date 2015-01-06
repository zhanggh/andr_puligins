package com.plugin.commons.service;

import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.XinHuaModel;


public interface XinHuaService {

	public XinHuaModel getXinHuaImg(int clientWidth,int clientHeight,String paramId,String paramKeys);
	
	public String getXinHuaIndex(String paramId,String url,String paramKeys);
	
	public RspResultModel getXHImg(int clientWidth,int clientHeight,String paramId,String paramKeys);
}
