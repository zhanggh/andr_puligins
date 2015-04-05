package com.plugin.commons.service;

import java.util.List;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.zq.util.StCacheHelper;


public class NewsServiceImpl implements  NewsService{
	DingLog log = new DingLog(NewsServiceImpl.class);
	public static List<NewsTypeModel> mTypeList = null;
	 
	public NewsServiceImpl(){
		super();
	}
	@Override
	public NewsTypeModel getNewsType(String attype){
		 
		log.debug("getNewsType attype:"+attype);
		// TODO Auto-generated method stub
		if(FuncUtil.isEmpty(mTypeList)){
			RspResultModel rsp = (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_NEWS_ARTTYPE, "1");
			if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){
				mTypeList = rsp.getAttype_list();
			} 
		}
		if(!FuncUtil.isEmpty(mTypeList)){
			for(NewsTypeModel type:mTypeList){
				if(!FuncUtil.isEmpty(type.getSubtypes())){
					for(NewsTypeModel subType:type.getSubtypes()){
						subType.setParentid(type.getId());
					}
				}
			}
		}
		
		for(NewsTypeModel type:mTypeList){
			if(attype.equals(type.getId())){
				return type;
			}else{
				for(NewsTypeModel subType:type.getSubtypes()){
					if(attype.equals(subType.getId())){
						return type;
					}
				}
			}
		}
		return null;
	}

	@Override
	public RspResultModel getNewsList(boolean isCache,String start, String size,
			String attype, String subattype) {
		// TODO Auto-generated method stub
		log.info(attype+";"+subattype);
		RspResultModel rsp = null;
		if(isCache){
			log.info("获取缓存的新闻列表");
			rsp = (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), 
					CoreContants.CACHE_NEWS_NEWSLIST+attype+"_"+subattype, "1");
		}else{
			rsp = ComApp.getInstance().getApi().getNewsList(attype, subattype, start, size);
			
			if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){//获取列表成功，覆盖本地缓存
				if(FuncUtil.isEmpty(start)||"0".equals(start)){//如果游标是0，则把数据缓存起来
					log.info("保存缓存");
					StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_NEWS_NEWSLIST+attype+"_"+subattype, "1",rsp);
				}
			}
		}
		return rsp;
	}

	@Override
	public RspResultModel pubNewComment(String articleid, String replycontent,String attype) {
		RspResultModel rsp = ComApp.getInstance().getApi().pubNewComment(articleid,replycontent,attype);
		return rsp;
	}

	@Override
	public RspResultModel commentsList(boolean iscache,String id,String type,String start,String size,String attype) {
		RspResultModel rsp =null;
		if(iscache){
			rsp =(RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_NEWS_COMMENTLIST,id);
			if(rsp!=null&&rsp.getComment_list()!=null&&rsp.getComment_list().size()>0){
				return rsp;
			}
		} 
		rsp = ComApp.getInstance().getApi().commentsList(id,type,start,size,attype);
		StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_NEWS_COMMENTLIST,id,rsp);
	 
		return rsp;
	}
	
	public List<NewsTypeModel> getNewsTypes(){
		if(NewsServiceImpl.mTypeList==null){
			// TODO Auto-generated method stub
			RspResultModel rsp =null;
			 
			rsp=(RspResultModel) StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_NEWS_ARTTYPE,"1");
			if(rsp!=null){
				mTypeList = rsp.getAttype_list();
				if(!FuncUtil.isEmpty(mTypeList)){
					for(NewsTypeModel type:mTypeList){
						if(!FuncUtil.isEmpty(type.getSubtypes())){
							for(NewsTypeModel subType:type.getSubtypes()){
								subType.setParentid(type.getId());
							}
						}
					}
				}
			}
		}
		return mTypeList;
	}

	/**
	 * 初始化栏目，当获取服务后台的栏目失败时，使用原先缓存中的数据
	 */
	@Override
	public RspResultModel initNewsType() {
		// TODO Auto-generated method stub
		RspResultModel rsp =null;
		rsp = ComApp.getInstance().getApi().artTypeList();
		if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){
			StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_NEWS_ARTTYPE,"1",rsp);
		}else{
			rsp=(RspResultModel) StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_NEWS_ARTTYPE,"1");
		}
		if(rsp!=null){
			mTypeList = rsp.getAttype_list();
			if(!FuncUtil.isEmpty(mTypeList)){
				for(NewsTypeModel type:mTypeList){
					if(!FuncUtil.isEmpty(type.getSubtypes())){
						for(NewsTypeModel subType:type.getSubtypes()){
							subType.setParentid(type.getId());
						}
					}
				}
			}
		}
		
		return rsp;
	}

	@Override
	public RspResultModel homeList(boolean cache) {
		RspResultModel rsp=null;
		if(cache){
			log.info("获取缓存的新闻列表");
			rsp = (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), 
					CoreContants.CACHE_NEWS_NEWSLIST+"home22", "1");
		}else{
			rsp = ComApp.getInstance().getApi().homePage("");
			if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){//获取列表成功，覆盖本地缓存
				StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_NEWS_NEWSLIST+"home22", "1",rsp);
			}
		}
		return rsp;
		 
	}
	
	public RspResultModel getSubNewsList(boolean cache,String start,String size,String attype,String subattype,String id){
		log.info(attype+";"+subattype);
		if(cache){
			log.info("获取缓存的新闻列表");
			RspResultModel rsp = (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), 
					CoreContants.CACHE_NEWS_NEWSLIST+attype+"_"+subattype+"_"+id, "1");
			return rsp;
		}
		else{
			RspResultModel rsp = null;
			rsp = ComApp.getInstance().getApi().getSubNewsList(attype, subattype, start, size,id);
			
			if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){//获取列表成功，覆盖本地缓存
				if(FuncUtil.isEmpty(start)||"0".equals(start)){//如果游标是0，则把数据缓存起来
					log.info("保存缓存");
					StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_NEWS_NEWSLIST+attype+"_"+subattype+"_"+id, "1",rsp);
				}
				return rsp;
			}
			else{
				log.info("获取服务器新闻列表失败，返回缓存");
				return (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_NEWS_NEWSLIST+attype+"_"+subattype, "1");
			}
		}
	}

	@Override
	public RspResultModel getPicNewsDetail(String attype, String id) {
		// TODO Auto-generated method stub
		RspResultModel rsp = null;
		rsp = ComApp.getInstance().getApi().getPicArtDetail(attype, id);
		return rsp;
	}

	@Override
	public RspResultModel getMyCmNewList(boolean iscache,String start, String size) {
		
		RspResultModel rsp = null;
		if(iscache){
			rsp =(RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_MYREPLY_LIST, "1");
		}else{
			rsp = ComApp.getInstance().getApi().getMyCmNewList(start,size);
			if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
				rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"获取我评论的新闻列表失败":rsp.getRetmsg());
			}
			else{
				if("0".equals(start)){//如果获取成功，并且是下拉，则缓存数据起来
					StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_MYREPLY_LIST, "1",rsp);
				}
				
			}
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
	public RspResultModel getIndustryRecList() {
		RspResultModel rsp = null;
		rsp=ComApp.getInstance().getApi().getIndustryRecList();
		return rsp;
	}
	@Override
	public RspResultModel getHotRecList() {
		RspResultModel rsp = null;
		rsp=ComApp.getInstance().getApi().getHotRecList();
		return rsp;
	}
	@Override
	public RspResultModel getAreaRecList(String areaId) {
		RspResultModel rsp = null;
		rsp=ComApp.getInstance().getApi().getAreaRecList(areaId);
		return rsp;
	}
	@Override
	public RspResultModel getAreaList() {
		RspResultModel rsp = null;
		rsp=ComApp.getInstance().getApi().getAreaList();
		return rsp;
	}
}
