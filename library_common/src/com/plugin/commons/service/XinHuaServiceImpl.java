package com.plugin.commons.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpException;

import com.plugin.commons.ComApp;
import com.plugin.commons.helper.CryptUtils;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.XinHuaModel;

public class XinHuaServiceImpl implements XinHuaService {
	public static final String XINHUA_HOME_URL = "http://xhpfm.open.zhongguowangshi.com/open/index";
	@Override
	public XinHuaModel getXinHuaImg(int clientWidth,int clientHeight,String paramId,String paramKeys) {
		 

		List<String> params=new ArrayList<String>();
		String randomNum= "g"+(int)(Math.random()*1000000); 
		String timeStamp= String.valueOf(System.currentTimeMillis());
		params.add(paramId);
		params.add(paramKeys);
		params.add(randomNum);
		params.add(timeStamp);
		Collections.sort(params);
		
		StringBuffer orgstr=new StringBuffer();;
		String myEncrypt="";
		for(int a=params.size()-1;a>=0;a--){
			orgstr.append(params.get(a));
		}
		myEncrypt=CryptUtils.SHA1(orgstr.toString());
		
		XinHuaModel rsp = null;
		try {
			rsp = ComApp.getInstance().getApi().getStartUpPage(String.valueOf(clientWidth), String.valueOf(clientHeight), paramId,timeStamp,randomNum,myEncrypt);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsp;
	}
	
	public RspResultModel getXHImg(int clientWidth,int clientHeight,String paramId,String paramKeys)
	{
		XinHuaModel xh = getXinHuaImg(clientWidth,clientHeight,paramId,paramKeys);
		RspResultModel rsp = new RspResultModel();
		if(xh!=null){
			rsp.setRetcode("0");
			rsp.setRetmsg("操作成功");
			rsp.setXhModel(xh);
		}
		else{
			rsp.setRetcode("1");
			rsp.setRetmsg("获取信息失败");
		}
		return rsp;
		
	}

	@Override
	public String getXinHuaIndex(String paramId,String url, String paramKeys) {
		 
		List<String> params=new ArrayList<String>();
		String randomNum= "g"+(int)(Math.random()*1000000); 
		String timeStamp= String.valueOf(System.currentTimeMillis());
		params.add(paramId);
		params.add(paramKeys);
		params.add(randomNum);
		params.add(timeStamp);
		Collections.sort(params);
		
		StringBuffer orgstr=new StringBuffer();
		String myEncrypt="";
		for(int a=params.size()-1;a>=0;a--){
			orgstr.append(params.get(a));
		}
		myEncrypt=CryptUtils.SHA1(orgstr.toString());
		orgstr=new StringBuffer();
		 
		orgstr.append("paramId").append("=").append(paramId).append("&");
		orgstr.append("paramKeys").append("=").append(paramKeys).append("&");
		orgstr.append("randomNum").append("=").append(randomNum).append("&");
		orgstr.append("timeStamp").append("=").append(timeStamp).append("&");
		orgstr.append("myEncrypt").append("=").append(myEncrypt);
		 
	    return url+"?"+orgstr.toString();
	}

}
