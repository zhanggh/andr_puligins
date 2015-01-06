package com.plugin.commons.service;

import java.util.HashMap;
import java.util.Map;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.UserInfoModel;
import com.zq.types.Group;
import com.zq.util.StCacheHelper;

/**
 * 常用的缓存
 * @Description:
 * @author:vinci
 * @time:2014-7-25 下午4:52:51
 */
public class CacheDataService {
	static DingLog log = new DingLog(CacheDataService.class);
	private static final long LOAD_INTERVAL = 30000;//30秒刷新一次
	public static UserInfoModel gl_user = null;
	public static Map<String,Long> mLoadTimeMap = new HashMap<String,Long>();
	
	/**
	 * 判断该列表是否需要刷新
	 * @Description:
	 * @param flag
	 * @return
	 * boolean
	 * @exception:
	 * @author: vinci
	 * @time:2014-9-24 下午8:13:46
	 */
	public static boolean isNeedLoad(String flag){
		long lasttime = 0;
		if(mLoadTimeMap.containsKey(flag)){
			lasttime = mLoadTimeMap.get(flag);
		}
		return (System.currentTimeMillis()-lasttime)>LOAD_INTERVAL;
	}
	
	/**
	 * 设置该列表的刷新时间
	 * @Description:
	 * @param flag
	 * void
	 * @exception:
	 * @author: vinci
	 * @time:2014-9-24 下午8:14:09
	 */
	public static void setNeedLoad(String flag){
		mLoadTimeMap.put(flag, System.currentTimeMillis());
	}
	
	/**
	 * 是否登录
	 * @Description:
	 * @return
	 * boolean
	 * @exception:
	 * @author: vinci
	 * @time:2014-7-25 下午5:01:01
	 */
	public static boolean isLogin()
	{
		return gl_user != null;
	}
	
	/**
	 * 获取缓存的关注列表 如果获取全部 type ==-1
	 * @Description:
	 * @param type
	 * @return
	 * Group<CacheModel>
	 * @exception:
	 * @author: vinci
	 * @time:2014-8-23 下午5:24:55
	 */
	public static Group<CacheModel> getAcctionList(int type){
		Group g = (Group)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_ACCTION, "1");
		if(type!=CacheModel.CACHE_ALL&&g!=null){//如果不是获取所有，并且列表不为空，则遍历返回缓存类型相同的
			log.info("获取列表不为空----"+g.size());
			Group<CacheModel> gnew = new Group<CacheModel>();
			for(int i=0;i<g.size();i++){
				CacheModel cm = (CacheModel)g.get(i);
				log.info("========="+cm.type+";"+cm.id+";"+type);
				if(cm.type==type)
					gnew.add(cm);
			}
			return gnew;
		}
		else{
			log.info("获取列表为空");
		}
		return g;
	}
	
	/**
	 * 获取关注
	 * @Description:
	 * @param type
	 * @param id
	 * @return
	 * CacheModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-8-23 下午5:38:34
	 */
	public static CacheModel getAcction(int type,String id){
		Group<CacheModel> g = getAcctionList(type);
		if(g!=null){//如果不是获取所有，并且列表不为空，则遍历返回缓存类型相同的
			for(int i=0;i<g.size();i++){
				CacheModel cm = g.get(i);
				log.info(cm.type+";"+cm.id);
				if(cm.type==type&&cm.id.equals(id))
					return cm;
			}
		}
		return null;
	}
	
	/**
	 * 添加关注
	 * @Description:
	 * @param cm
	 * void
	 * @exception:
	 * @author: vinci
	 * @time:2014-8-23 下午5:41:01
	 */
	public static void addAcction(CacheModel cm){
		CacheModel cmOld = getAcction(cm.type,cm.id);
		//
		if(cmOld!=null){
			log.info("如果已经添加关注，返回");
			return ;
		}
		
		Group<CacheModel> g = getAcctionList(CacheModel.CACHE_ALL);
		if(g==null||g.size()==0){
			log.info("获取关注列表为空");
			g = new Group<CacheModel>();
			g.add(cm);
			StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_ACCTION, "1",g);
		}
		else{
			log.info("获取关注列表不为空");
			Group<CacheModel> gnew = new Group<CacheModel>();//倒叙排列
			gnew.add(cm);
			for(CacheModel obj:g){
				gnew.add(obj);
			}
			StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_ACCTION, "1",gnew);
		}
		
	}
	
	public static void cancelAcction(int type,String id){
		Group<CacheModel> g = getAcctionList(CacheModel.CACHE_ALL);
		if(g==null){//如果列表为空，则直接返回
			return ;
			
		}
		else{
			for(int i=0;i<g.size();i++){
				CacheModel obj = g.get(i);
				if(obj.id.equals(id)&&obj.type==type){
					g.remove(i);
					break;
				}
				//g.add(obj);
			}
		}
		StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_ACCTION, "1",g);
	}

}
