package com.plugin.commons.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 常用的sp操作
 * 将一些基础信息写入手机存储
 * @author Vinci
 * @date 2013-2-27 下午04:54:35 
 * @modifylog
 */
public class SharePreferenceUtil {
	public final static String SPKEY_MERID = "merid";
	public final static String SPKEY_USER = "user";
	public final static String SPKEY_LASTCONSUME = "lastconsume";
	public final static String SPKEY_QUIT = "quit";
	public final static String SPKEY_ADMIN_PWD = "adminpwd";
	public final static String SPKEY_ADMIN_URL_TYPE = "apiurl_type";
	public final static String SPKEY_HASLOAD = "hasload";
	
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public SharePreferenceUtil(Context context, String file) {
		sp = context.getSharedPreferences(file, context.MODE_PRIVATE);
		editor = sp.edit();
	}
	
	public void setParam(String key,String value)
	{
		editor.putString(key, value);
		editor.commit();
	}
	
	public String getValue(String key)
	{
		return sp.getString(key, "");
	}

	public SharedPreferences getSp() {
		return sp;
	}

	public void setSp(SharedPreferences sp) {
		this.sp = sp;
	}

	public SharedPreferences.Editor getEditor() {
		return editor;
	}

	public void setEditor(SharedPreferences.Editor editor) {
		this.editor = editor;
	}
	
	//reciemsg
	/**
	 * 是否接受通知
	 * @param isRecei
	 */
	public void setReceiveNotic(boolean isRecei) {
		editor.putBoolean("receiveNotic", isRecei);
		editor.commit();
	}

	public boolean getReceiveNotic() {//默认接收
		return sp.getBoolean("receiveNotic",true);
	}
	/**
	 * 评论结果
	 * @param addCmSuss
	 */
	public void setCommentRet(boolean addCmSuss) {
		editor.putBoolean("commentRet", addCmSuss);
		editor.commit();
	}
	
	public boolean getCommentRet() {// 
		return sp.getBoolean("commentRet",false);
	}
}
