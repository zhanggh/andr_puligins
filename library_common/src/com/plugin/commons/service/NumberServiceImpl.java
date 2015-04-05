package com.plugin.commons.service;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.model.NumberModel;
import com.plugin.commons.model.RspResultModel;
import com.zq.util.StCacheHelper;

/**
 * @author huangmeng
 *	号码通
 */
public class NumberServiceImpl implements NumberService {
	DingLog log = new DingLog(NumberServiceImpl.class);
	@Override
	public RspResultModel getNumTypeList(boolean isCache) {
		if(isCache){
			log.info("获取缓存的号码类型");
			RspResultModel rsp = (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), 
					CoreContants.CACHE_NUMBER_TYPES, "1");
			return rsp;
		}else{
			RspResultModel rsp = null;
			rsp = ComApp.getInstance().getApi().getNubmerTypes();
			
			if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){//获取列表成功，覆盖本地缓存
					log.info("保存缓存");
					StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_NUMBER_TYPES, "1",rsp);
			}
			return rsp;
		}
	}
	@Override
	public RspResultModel getNumList(boolean isCache,String type,String stext,String start,String size) {
		RspResultModel rsp = null;
		if (isCache) {
			log.info("获取缓存的号码列表");
			rsp = (RspResultModel) StCacheHelper.getCacheObj(
					ComApp.getInstance(), CoreContants.CACHE_NUMBER_LIST, type);
			return rsp;

		}else{
			rsp = ComApp.getInstance().getApi().getNubmerList(type,stext,start,size);
			StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_NUMBER_LIST,type,rsp);
			return rsp;
		}
	}

	@Override
	public boolean updatetNumberList(NumberModel number) {
		
		return false;
	}

	

}
