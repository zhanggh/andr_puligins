package com.plugin.commons.api;


import android.util.Log;

import com.google.gson.Gson;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.RadioVideoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.XinHuaModel;
import com.zq.types.StBaseType;

public class JsonParse  {
	
	
	public StBaseType consume(String content) {

		RspResultModel obj=(RspResultModel)static_consume(content);
		 		 
		return obj;
	}
	
	public static StBaseType static_consume(String content) {
		try {
			Gson gson = new Gson();
			RspResultModel obj=gson.fromJson(content, RspResultModel.class);
			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print(ex.getMessage());
		} finally {
		}
		return null;
	}
	public static StBaseType parsXinHua(String content) {
		try {
			Gson gson = new Gson();
			XinHuaModel obj=gson.fromJson(content, XinHuaModel.class);
			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print(ex.getMessage());
		} finally {
		}
		return null;
	}
	/**
	 * 瞬间和radio
	 * @param content
	 * @return
	 */
	public static StBaseType parsShJianRadio(String content) {
		try {
			String header="{'retcode':'0','retmsg':'','radioVideos':";
			String tail="}";
			Gson gson = new Gson();
			RspResultModel obj=gson.fromJson(header+content+tail, RspResultModel.class);
			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print(ex.getMessage());
		} finally {
		}
		return null;
	}
	
	
	public static StBaseType static_consume_object(String content,Class c) {
		Gson gson = new Gson();
		try {
			StBaseType obj = gson.fromJson(content, c);
			return obj;
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		} finally {
		}
		return null;
	}
	
	public static Object consume(String content,Class ctf)
	{
		if(FuncUtil.isEmpty(content))return null;
		Gson gson=new Gson();
		try
		{
			return gson.fromJson(content,ctf);
		}
		catch(Exception e)
		{
			Log.d("转换json异常", e.getMessage());
			return null;
		}
	}


}
