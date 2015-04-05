package com.plugin.commons.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.plugin.commons.AppException;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.model.BaoLiaoInfoModel;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.CommentModel;
import com.plugin.commons.model.ContentBean;
import com.plugin.commons.model.MyCollectInfoModel;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.zq.types.Group;
import com.zq.util.StCacheHelper;

public class BaoliaoServiceImpl implements BaoliaoService{

	@Override
	public RspResultModel getNetfBliaoList(boolean iscache,String start, String size) {
		RspResultModel rsp = null;
		if(iscache){
			rsp =(RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_BAOLIAO_NETFLIST, "1");
			return rsp;
		} 
		else{
			rsp = ComApp.getInstance().getApi().getNetfBliaoList(start,size);
			if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
				rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"获取网友爆料列表失败":rsp.getRetmsg());
			}
			else{
				if("0".equals(start)){//如果获取成功，并且是下拉，则缓存数据起来
					StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_BAOLIAO_NETFLIST, "1",rsp);
				}
				
			}
		}
		
		return rsp;
	}
	@Override
	public RspResultModel politicsMyAsk(boolean iscache,String start, String size) {
		RspResultModel rsp = null;
		if(iscache){
			rsp =(RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_MY_ASKGOV, "1");
			if(rsp!=null){
				return rsp;
			}
		} 
		rsp = ComApp.getInstance().getApi().politicsMyAsk(start, size);
		StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_MY_ASKGOV, "1",rsp);
		if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
			rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"获取问政列表失败":rsp.getRetmsg());
		}
		return rsp;
	}

	@Override
	public RspResultModel getBaoliaoDetail(boolean iscache,String id) {
		RspResultModel rsp = null;
		if(iscache){
			rsp =(RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_BAOLIAO_DETAIL, "1");
			if(rsp!=null){
				return rsp;
			}
		} 
		rsp = ComApp.getInstance().getApi().getBaoliaoDetail(id);
		StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_BAOLIAO_DETAIL, "1",rsp);
		if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
			rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"获取爆料详情失败":rsp.getRetmsg());
		}
		return rsp;
	}

	@Override
	public RspResultModel getMyBaoliaoList(String type,boolean iscache,String sessionid, String start,
			String size) {
		RspResultModel rsp = null;
		
		if("2".equals(type)){
			rsp = new RspResultModel();
			rsp.setRetcode("0");
			rsp.setRetmsg("查询成功");
			List<BaoLiaoInfoModel> list = new ArrayList<BaoLiaoInfoModel>();
			Group group = CacheDataService.getAcctionList(CacheModel.CACHE_ASKBAOLIAO);
			if(group!=null&&group.size()>0){
				for(int i=0;i<group.size();i++){
					CacheModel cm = (CacheModel)group.get(i);
					list.add((BaoLiaoInfoModel)cm.msg);
				}
			}
			rsp.setBaoliao_list(list);
			return rsp;
		}
		
		
		if(iscache){
			rsp =(RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_BAOLIAO_MYLIST, "1");
			if(rsp!=null){
				return rsp;
			}
		} 
		rsp = ComApp.getInstance().getApi().getMyBaoliaoList(sessionid,start,size);
		StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_BAOLIAO_MYLIST, "1",rsp);
		if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
			rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"获取我的爆料失败":rsp.getRetmsg());
		}
		return rsp;
	}

	@Override
	public RspResultModel pubBaoliaoInfo(String content,
			String picname1, InputStream pic1, String picname2,
			InputStream pic2, String picname3, InputStream pic3,
			String audioname, InputStream audio, String vidioname,
			InputStream vidio){
		RspResultModel rsp = null;
		rsp = ComApp.getInstance().getApi().pubBaoliaoInfo(content, picname1, 
				pic1, picname2, pic2, picname3, pic3, audioname, audio, vidioname, vidio);
		if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
			rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"发布爆料失败":rsp.getRetmsg());
		}
		return rsp;
	}

	@Override
	public RspResultModel commentBaoliao(CommentModel cm,String sessionid) {
		RspResultModel rsp = null;
		rsp = ComApp.getInstance().getApi().commentBaoliao(cm,sessionid);
		if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
			rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"发布爆料失败":rsp.getRetmsg());
		}
		return rsp;
	}

	/**
	 * 构造返回对象（失败对象）
	 * @param retcode
	 * @param retmsg
	 * @return
	 */
	private RspResultModel buildRsp(String retcode,String retmsg){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode(retcode);
		rsp.setRetmsg(retmsg);
		return rsp;
	}
	@Override
	public RspResultModel colectAll() throws AppException {
		RspResultModel rsp=null;
		NewsTypeModel mNewType;
		NewsService newsSvc;
		rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		MyCollectInfoModel cols=new MyCollectInfoModel();
		Group group = CacheDataService.getAcctionList(CacheModel.CACHE_ASKBAOLIAO);
		ContentBean colsObj=null;
		newsSvc = new NewsServiceImpl();
		if(group!=null&&group.size()>0){
			for(int i=0;i<group.size();i++){
				CacheModel cm = (CacheModel)group.get(i);
				BaoLiaoInfoModel bl=(BaoLiaoInfoModel)cm.msg;
				cols.getBaoliaoList().add(bl);
				colsObj=new ContentBean();
				colsObj.setContent(bl.getContent());
				colsObj.setId(bl.getId());
				colsObj.setUsername(bl.getUsername());
				colsObj.setType(CacheModel.CACHE_ASKBAOLIAO);
				cols.getColslist().add(colsObj);
			}
		}
		group = CacheDataService.getAcctionList(CacheModel.CACHE_ASKGOV);
		if(group!=null&&group.size()>0){
			for(int i=0;i<group.size();i++){
				CacheModel cm = (CacheModel)group.get(i);
				AskMsgModel bl =(AskMsgModel)cm.msg;
				cols.getAskMsgList().add(bl);
				cols.getAskMsgList().add(bl);
				colsObj=new ContentBean();
				colsObj.setContent(bl.getContent());
				colsObj.setId(bl.getId());
				colsObj.setUsername(bl.getUsername());
				colsObj.setType(CacheModel.CACHE_ASKGOV);
				cols.getColslist().add(colsObj);
			}
		}
		group = CacheDataService.getAcctionList(CacheModel.CACHE_ASKNEWS);
		if(group!=null&&group.size()>0){
			for(int i=0;i<group.size();i++){
				CacheModel cm = (CacheModel)group.get(i);
				NewsInfoModel bl =(NewsInfoModel)cm.msg;
				cols.getNewsList().add(bl);
				colsObj=new ContentBean();
				colsObj.setContent(bl.getTitle());
				colsObj.setId(bl.getId());
				colsObj.setType(CacheModel.CACHE_ASKNEWS);
				mNewType = newsSvc.getNewsType(bl.getArttype());
				colsObj.setAttypeName(mNewType.getName());
				cols.getColslist().add(colsObj);
			}
		}
		group = CacheDataService.getAcctionList(CacheModel.CACHE_ASKNEWS_EXT);
		if(group!=null&&group.size()>0){
			for(int i=0;i<group.size();i++){
				CacheModel cm = (CacheModel)group.get(i);
				NewsInfoModel bl =(NewsInfoModel)cm.msg;
				cols.getNewsList().add(bl);
				colsObj=new ContentBean();
				colsObj.setContent(bl.getTitle());
				colsObj.setId(bl.getId());
				colsObj.setType(CacheModel.CACHE_ASKNEWS_EXT);
				mNewType = newsSvc.getNewsType(bl.getArttype());
				colsObj.setAttypeName(mNewType.getName());
				cols.getColslist().add(colsObj);
			}
		}
		group = CacheDataService.getAcctionList(CacheModel.CACHE_VIDEO);
		if(group!=null&&group.size()>0){
			for(int i=0;i<group.size();i++){
				CacheModel cm = (CacheModel)group.get(i);
				NewsInfoModel bl =(NewsInfoModel)cm.msg;
				cols.getNewsList().add(bl);
				colsObj=new ContentBean();
				colsObj.setContent(bl.getTitle());
				colsObj.setId(bl.getId());
				colsObj.setType(CacheModel.CACHE_VIDEO);
				mNewType = newsSvc.getNewsType(bl.getArttype());
				colsObj.setAttypeName(mNewType.getName());
				cols.getColslist().add(colsObj);
			}
		}
		group = CacheDataService.getAcctionList(CacheModel.CACHE_IMG_NEWS);
		if(group!=null&&group.size()>0){
			for(int i=0;i<group.size();i++){
				CacheModel cm = (CacheModel)group.get(i);
				NewsInfoModel bl =(NewsInfoModel)cm.msg;
				cols.getNewsList().add(bl);
				colsObj=new ContentBean();
				colsObj.setContent(bl.getTitle());
				colsObj.setId(bl.getId());
				colsObj.setType(CacheModel.CACHE_IMG_NEWS);
				mNewType = newsSvc.getNewsType(bl.getArttype());
				colsObj.setAttypeName(mNewType.getName());
				cols.getColslist().add(colsObj);
			}
		}
		rsp.setColts(cols);
		return rsp;
		 
	}
	@Override
	public RspResultModel getMyReplyBaoliaoList(boolean iscache,String start,String size) {
		RspResultModel rsp=null;
		if(iscache){
			rsp =(RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_BAOLIAO_MYREPLY, "1");
			if(rsp!=null){
				return rsp;
			}
		} 
		rsp = ComApp.getInstance().getApi().getMyReplyBaoliaoList(start,size);
		StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_BAOLIAO_MYREPLY, "1",rsp);
		if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
			rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"获取我评论的爆料失败":rsp.getRetmsg());
		}
		return rsp;
	}
}
