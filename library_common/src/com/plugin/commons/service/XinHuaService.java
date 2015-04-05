package com.plugin.commons.service;

import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.XinHuaModel;


public interface XinHuaService {

	public XinHuaModel getXinHuaImg(int clientWidth,int clientHeight,String paramId,String paramKeys);
	
	public String getXinHuaIndex(String paramId,String url,String paramKeys);
	
	/**
	 * 新华社首页对接
	 * @param clientWidth
	 * @param clientHeight
	 * @param paramId
	 * @param paramKeys
	 * @return
	 */
	public RspResultModel getXHImg(int clientWidth,int clientHeight,String paramId,String paramKeys);

	/**
	 * new radio栏目类型
	 * @return
	 */
	public RspResultModel getNewRadioTypeList();
	/**
	 * new radio 栏目详情(二级栏目)
	 * @return
	 */
	public RspResultModel getNewRadioTypeDetail(String id);
	/**
	 * 瞬间栏目类型
	 * @return
	 */
	public RspResultModel getShunJianTypeList();
	/**
	 * 瞬间 栏目详情(二级栏目)
	 * @return
	 */
	public RspResultModel getShunJianTypeDetail(String id);
}
