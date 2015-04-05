package com.plugin.commons.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.IntentService;
import android.content.Intent;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.RspResultModel;
import com.zq.util.StCacheHelper;

public class ComService extends IntentService {
	DingLog log = new DingLog(ComService.class);
	public ComService() {
		super("ComService");
		log.debug("******************后台服务ComService******************");
	}
	
	@Override
	public void onCreate() {
		log.debug("******************后台服务onCreate******************");
		super.onCreate();
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		log.debug("******************后台服务onStartCommand******************");
		return super.onStartCommand(intent, flags, startId);
	}



	@SuppressWarnings("unchecked")
	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		log.debug("service线程号："+Thread.currentThread().getId());
		log.debug("******************后台服务正确进行中******************");
		Map<String,NewsInfoModel> newsView = null;
		NewsInfoModel ns=(NewsInfoModel) arg0.getExtras().get(CoreContants.PARAMS_NEWS);
		String type=(String) arg0.getExtras().get(CoreContants.PARAMS_TYPE);
		
		newsView = (Map<String, NewsInfoModel>) StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_ART_VIEW,"1");
		if(newsView==null){
			newsView=new HashMap<String, NewsInfoModel>();
		}
		if(ns!=null){
			NewsInfoModel tem = newsView.get(ns.getArttype()+"_"+ns.getId());
			if(tem!=null){
				if(!FuncUtil.isEmpty(ns.getArttype())){
					tem.setViewTimes(tem.getViewTimes()+1);
					newsView.put(ns.getArttype()+"_"+ns.getId(),tem);
				}else{
					log.debug("栏目id为空，不统计");
				}
			}else{
				if(!FuncUtil.isEmpty(ns.getArttype())){
					ns.setViewTimes(1);
					newsView.put(ns.getArttype()+"_"+ns.getId(),ns);
				}else{
					log.debug("栏目id为空，不统计");
				}
			}
			
			StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_ART_VIEW,"1", newsView);
		}
		
		if(newsView.size()>0){
			Iterator<Entry<String, NewsInfoModel>> iter = newsView.entrySet().iterator();
			Entry<String, NewsInfoModel> entry;
			int count=0;
			StringBuffer reqstr=new StringBuffer();
			while (iter.hasNext()) {
			    entry = iter.next();
			    count+=entry.getValue().getViewTimes();
			    if(entry.getValue().getViewTimes()>0){
			    	 reqstr.append(entry.getKey()).append("_").append(entry.getValue().getViewTimes()).append(",");
			    }
			}
			log.debug("统计数："+count);
			if(CoreContants.PUSH_TYPE.equals(type)||count>=CoreContants.MAXREQ_TIMES){
				String req=reqstr.substring(0, reqstr.length()-1);
				log.debug("统计推送请求:"+req);
				RspResultModel rsp=ComApp.getInstance().getApi().pushArtViewTimes(req);
				if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){
					log.debug("推送结果成功，删除缓存");
					newsView.clear();
					StCacheHelper.deleteCacheObj(ComApp.getInstance(), CoreContants.CACHE_ART_VIEW,"1");
				}else{
					log.debug("推送统计结果失败，下次继续");
				}
			}else{
				log.debug("不满足上送条件，待下次继续");
			}
		}else{
			log.debug("newsView.size()==0，不满足上送条件，待下次继续");
		}
	}

}
