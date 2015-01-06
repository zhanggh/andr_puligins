package com.plugin.commons.service;

import java.util.ArrayList;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.SysNoticeModel;
import com.zq.util.StCacheHelper;

/**
 * @author zhang
 *	系统通知业务类
 */
public class SysNotifyServiceImp implements SysNotifyService {

	@Override
	public RspResultModel getNotifyList() {
		RspResultModel rsp = null;
		rsp =(RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_NOTIFY_LIST, "1");
		if(rsp==null){
			rsp=new RspResultModel();
		}
		if(rsp.getNotice_list()==null){
			rsp.setNotice_list(new ArrayList<SysNoticeModel>());
		}
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		return rsp;
	}

	@Override
	public boolean updatetNotifyList(SysNoticeModel sysNotic) {
		RspResultModel rsp = getNotifyList();
		if(rsp==null){
			rsp=new RspResultModel();
		}
		if(rsp.getNotice_list()==null){
			rsp.setNotice_list(new ArrayList<SysNoticeModel>());
		}
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		rsp.getNotice_list().add(0, sysNotic);
		StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_NOTIFY_LIST, "1",rsp);
		return true;
	}

}
