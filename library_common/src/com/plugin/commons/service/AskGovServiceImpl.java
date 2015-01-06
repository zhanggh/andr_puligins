package com.plugin.commons.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.RspResultModel;
import com.zq.types.Group;
import com.zq.util.StCacheHelper;


public class AskGovServiceImpl implements AskGovService{
	DingLog log = new DingLog(AskGovServiceImpl.class);
	
	/**
	 * 获取机构列表--缓存起来防止网络不好情况下
	 * @Description:
	 * @param isCache 是否读取缓存数据，如果选择否，优先读取网络，读取网络失败再读取缓存
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-8-27 上午10:13:55
	 */
	public RspResultModel getOrgList(boolean isCache){
		if(isCache){
			log.info("获取缓存的机构列表");
			RspResultModel rsp = (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_ASK_GOVLIST, "1");
			return rsp;
		}
		else{
			RspResultModel rsp = ComApp.getInstance().getApi().orgList();
			if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){//获取机构列表成功，覆盖本地缓存
				StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_ASK_GOVLIST, "1",rsp);
				return rsp;
			}
			else{
				log.info("获取服务器机构列表失败，返回缓存");
				return (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_ASK_GOVLIST, "1");
			}
		}
	}
	
	/**
	 * 获取机构详情
	 * @Description:
	 * @param orgId
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-8-27 上午10:15:34
	 */
	public RspResultModel getOrgDetail(String orgId){
		RspResultModel rsp = ComApp.getInstance().getApi().orgDetail(orgId);
		return rsp;
	}

	@Override
	public RspResultModel addAskGov(String orgid, String content,
			String picname1, InputStream pic1, String picname2,
			InputStream pic2, String picname3, InputStream pic3,
			String audioname, InputStream audio, String vidioname,
			InputStream vidio) {
		// TODO Auto-generated method stub
		return ComApp.getInstance().getApi().politicsAdd(orgid, content, picname1, 
				pic1, picname2, pic2, picname3, pic3, audioname, audio, vidioname, vidio);
	}

	public RspResultModel addAskGovExt(AskMsgModel mGov, String content,
			String picname1, InputStream pic1, String picname2,
			InputStream pic2, String picname3, InputStream pic3,
			String audioname, InputStream audio, String vidioname,
			InputStream vidio) {
		return ComApp.getInstance().getApi().politicsAddExt(mGov, content, picname1, 
				pic1, picname2, pic2, picname3, pic3, audioname, audio, vidioname, vidio);
	}
	@Override
	public RspResultModel getAskList(boolean isCache,String start, String size,String hot) {
		// TODO Auto-generated method stub
		if(isCache){
			log.info("获取缓存的问政列表");
			RspResultModel rsp = (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_ASK_ASKLIST+"_"+hot, "1");
			if(rsp!=null&&rsp.getMsg_list()!=null){
				return rsp;
			}
			
		}
		RspResultModel rsp = ComApp.getInstance().getApi().politicsList(start, size,hot);
		log.info("rsp is null:"+(rsp==null));
		if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){//获取机构列表成功，覆盖本地缓存
			log.info("获取服务器问政列表成功，返回");
			StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_ASK_ASKLIST+"_"+hot, "1",rsp);
			return rsp;
		}
		else{
			log.info("获取服务器问政列表失败，返回缓存");
			return (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_ASK_ASKLIST+"_"+hot, "1");
		}
	}

	@Override
	public RspResultModel getMyAskList(boolean isCache, String start,
			String size, String type) {
		// TODO Auto-generated method stub
		if("2".equals(type)){
			RspResultModel rsp = new RspResultModel();
			rsp.setRetcode("0");
			rsp.setRetmsg("查询成功");
			List<AskMsgModel> list = new ArrayList<AskMsgModel>();
			Group group = CacheDataService.getAcctionList(CacheModel.CACHE_ASKGOV);
			if(group!=null&&group.size()>0){
				for(int i=0;i<group.size();i++){
					CacheModel cm = (CacheModel)group.get(i);
					list.add((AskMsgModel)cm.msg);
				}
			}
			rsp.setMsg_list(list);
			return rsp;
		}
		String cacheType = "0".equals(type)?CoreContants.CACHE_ASK_MYASK:CoreContants.CACHE_ASK_MYANSWER;
		if(isCache){
			log.info("获取缓存的信件列表");
			RspResultModel rsp = (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), cacheType, "1");
			if(rsp!=null&&rsp.getMsg_list()!=null&&rsp.getMsg_list().size()!=0){
				return rsp;
			}
		}
		RspResultModel rsp = null;
		if("0".equals(type)){
			rsp = ComApp.getInstance().getApi().politicsMyAsk(start, size);
		}
		else{
			rsp = ComApp.getInstance().getApi().politicsMyComment(start, size);
		}
		log.info("rsp is null:"+(rsp==null));
		if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){//获取机构列表成功，覆盖本地缓存
			log.info("获取服务器问信件表成功，返回");
			StCacheHelper.setCacheObj(ComApp.getInstance(), cacheType, "1",rsp);
			return rsp;
		}
		else{
			log.info("获取服务器信件列表失败，返回缓存");
			return (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), cacheType, "1");
		}
	}

	@Override
	public RspResultModel getOrgAnswerList(boolean isCache, String start,
			String size,String orgid) {
		// TODO Auto-generated method stub
		if(isCache){
			log.info("获取缓存的问政列表");
			RspResultModel rsp = (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_ASK_ORGANSWER+orgid, "1");
			return rsp;
		}
		else{
			RspResultModel rsp = ComApp.getInstance().getApi().politicsOrgAnswer(start, size,orgid);
			log.info("rsp is null:"+(rsp==null));
			if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){//获取机构列表成功，覆盖本地缓存
				log.info("获取服务器问政列表成功，返回");
				StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_ASK_ORGANSWER+orgid, "1",rsp);
				return rsp;
			}
			else{
				log.info("获取服务器问政列表失败，返回缓存");
				rsp = (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_ASK_ORGANSWER+orgid, "1");
				return rsp;
			}
		}
	}
	
	
}
