package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;


public class CacheModel  implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 712386624367943611L;
	public final static int CACHE_ALL = -1;//所有
	public final static int CACHE_ASKGOV = 1;//问政
	public final static int CACHE_ASKBAOLIAO = 2;//报料
	public final static int CACHE_ASKNEWS = 3;//图文
	public final static int CACHE_ASKNEWS_EXT = 33;//图文-个性化图文
	public final static int CACHE_VIDEO = 4;//视频
	public final static int CACHE_IMG_NEWS = 5;//图片
	public String id;
	public int type;
	public Object msg;
}
