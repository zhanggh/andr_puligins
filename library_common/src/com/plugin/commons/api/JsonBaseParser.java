package com.plugin.commons.api;

import android.util.Log;

import com.google.gson.Gson;
import com.plugin.commons.helper.FuncUtil;

public class JsonBaseParser<T>{
	private Class<T> ctf;
	
	public JsonBaseParser(Class<T> clazz)
	{
		this.ctf = clazz;
	}
	public T consume(String content)
	{
		if(FuncUtil.isEmpty(content))return null;
		Gson gson=new Gson();
		try
		{
			return (T)gson.fromJson(content,ctf);
		}
		catch(Exception e)
		{
			Log.d("转换json异常", e.getMessage());
			return null;
		}
	}
	public Class<T> getCtf() {
		return ctf;
	}
	public void setCtf(Class<T> ctf) {
		this.ctf = ctf;
	}
}
