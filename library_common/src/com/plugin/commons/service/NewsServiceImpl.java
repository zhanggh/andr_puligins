package com.plugin.commons.service;

import java.util.ArrayList;
import java.util.List;

import com.plugin.commons.AppException;
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
	private String tripId;
	public NewsServiceImpl(String tripId){
		this.tripId=tripId;
	}
	public NewsServiceImpl(){
		super();
	}
	@Override
	public NewsTypeModel getNewsType(String attype){
		if(FuncUtil.isEmpty(this.tripId)){
			log.error("旅游栏目id不可空");
		}
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
			for(NewsTypeModel type:mTypeList){
				if(attype.equals(type.getId())){
					//type.setParentid(attype);
					if(attype.equals(this.tripId)){//旅游栏目
						NewsTypeModel newType0=new NewsTypeModel();
						if(type.getSubtypes()==null||type.getSubtypes().size()==0){
							type.setSubtypes(new ArrayList<NewsTypeModel>());
							newType0.setId("0");
							newType0.setName("本地热点");
							newType0.setType("0");
							if(type.getSubtypes().size()!=2){
								type.getSubtypes().add(newType0);
							}
						}
						newType0=new NewsTypeModel();
						newType0.setId("-45");
						newType0.setName("旅游联盟");
						newType0.setType(CoreContants.NEWS_SUBTYPE_TTIP);
						if(type.getSubtypes().size()!=2){
							type.getSubtypes().add(newType0);
						}
					}
					return type;
				}
			}
		}
		return null;
		/**
		List<NewsSubTypeModel> list = new ArrayList<NewsSubTypeModel>();
		NewsTypeModel tm = new NewsTypeModel();
		if(CoreContants.NEWS_TYPE_GOV.equals(attype)){
			tm.setId(CoreContants.NEWS_TYPE_GOV);
			tm.setName("政务");
			NewsSubTypeModel sm = new NewsSubTypeModel();
			sm.setId("6");
			sm.setName("公告公示");
			list.add(sm);
			
			NewsSubTypeModel sm1 = new NewsSubTypeModel();
			sm1.setId("7");
			sm1.setName("政府资讯");
			list.add(sm1);
			tm.setSubtypes(list);
		}
		else if(CoreContants.NEWS_TYPE_NEWS.equals(attype)){
			tm.setId(CoreContants.NEWS_TYPE_NEWS);
			tm.setName("新闻");
			NewsSubTypeModel sm = new NewsSubTypeModel();
			sm.setId("3");
			sm.setName("头条");
			list.add(sm);
			
			NewsSubTypeModel sm1 = new NewsSubTypeModel();
			sm1.setId("4");
			sm1.setName("本地");
			list.add(sm1);
			
			NewsSubTypeModel sm2 = new NewsSubTypeModel();
			sm2.setId("5");
			sm2.setName("天下");
			list.add(sm2);
			
			NewsSubTypeModel sm3 = new NewsSubTypeModel();
			sm3.setId(CoreContants.NEWS_SUBTYPE_PIC);
			sm3.setName("图片");
			list.add(sm3);
			
			NewsSubTypeModel sm4 = new NewsSubTypeModel();
			sm4.setId(CoreContants.NEWS_SUBTYPE_VIDEO);
			sm4.setName("视频");
			list.add(sm4);
			tm.setSubtypes(list);
		}
		return tm;
		**/
	}

	@Override
	public RspResultModel getNewsList(boolean isCache,String start, String size,
			String attype, String subattype) {
		// TODO Auto-generated method stub
		log.info(attype+";"+subattype);
		if(isCache){
			log.info("获取缓存的新闻列表");
			RspResultModel rsp = (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), 
					CoreContants.CACHE_NEWS_NEWSLIST+attype+"_"+subattype, "1");
			if(rsp!=null&&rsp.getArticle_list()!=null&&rsp.getArticle_list().size()>0)
				return rsp;
		}
		
		RspResultModel rsp = null;
		rsp = ComApp.getInstance().getApi().getNewsList(attype, subattype, start, size);
		
		if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){//获取列表成功，覆盖本地缓存
			if(FuncUtil.isEmpty(start)||"0".equals(start)){//如果游标是0，则把数据缓存起来
				log.info("保存缓存");
				StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_NEWS_NEWSLIST+attype+"_"+subattype, "1",rsp);
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
		// TODO Auto-generated method stub
		if(cache){
			log.info("获取缓存的新闻列表");
			RspResultModel rsp = (RspResultModel)StCacheHelper.getCacheObj(ComApp.getInstance(), 
					CoreContants.CACHE_NEWS_NEWSLIST+"home22", "1");
			if(rsp!=null&&rsp.getHome_page_list()!=null&&rsp.getHome_page_list().size()>0)
				return rsp;
		}
		 
		RspResultModel rsp = null;
		rsp = ComApp.getInstance().getApi().homePage("");
		if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){//获取列表成功，覆盖本地缓存
			StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_NEWS_NEWSLIST+"home22", "1",rsp);
			return rsp;
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
			
			if(rsp!=null&&rsp.getMsg_list()!=null&&rsp.getMyreply_list().size()!=0){
				return rsp;
			}
		} 
		rsp = ComApp.getInstance().getApi().getMyCmNewList(start,size);
		if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
			rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"获取我评论的新闻列表失败":rsp.getRetmsg());
		}
		else{
			if("0".equals(start)){//如果获取成功，并且是下拉，则缓存数据起来
				StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_MYREPLY_LIST, "1",rsp);
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
	
	/**
	 * 自定义app栏目
	 * @param mTypeList
	 
	private void addExtTypes(List<NewsTypeModel> mTypeList){
		List<NewsTypeModel> subs=new ArrayList<NewsTypeModel>();
		NewsTypeModel baoliao=new NewsTypeModel();
		//报料
		baoliao.setId(CoreContants.MENU_CODE_BAOLIAO);
		baoliao.setName("报料");
		baoliao.setHassub("0");
		baoliao.setType(CoreContants.NEWS_TYPE_EXT);
		NewsTypeModel subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BAOLIAO+"_"+0);
		subExtType.setHassub("0");
		subExtType.setName("网友报料");
		subExtType.setParentid(CoreContants.MENU_CODE_BAOLIAO);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BAOLIAO+"_"+1);
		subExtType.setHassub("0");
		subExtType.setName("我的报料");
		subExtType.setParentid(CoreContants.MENU_CODE_BAOLIAO);
		subExtType.setType("0");
		subs.add(subExtType);
		baoliao.setSubtypes(subs);
		mTypeList.add(baoliao);
		//调查
		NewsTypeModel inquiry=new NewsTypeModel();
		subs=new ArrayList<NewsTypeModel>();
		inquiry.setId(CoreContants.MENU_CODE_IINQUIRY);
		inquiry.setName("调查");
		inquiry.setHassub("0");
		inquiry.setType(CoreContants.NEWS_TYPE_EXT);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_IINQUIRY+"_"+0);
		subExtType.setHassub("0");
		subExtType.setName("社会热点");
		subExtType.setParentid(CoreContants.MENU_CODE_IINQUIRY);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_IINQUIRY+"_"+1);
		subExtType.setHassub("0");
		subExtType.setName("生活消费");
		subExtType.setParentid(CoreContants.MENU_CODE_IINQUIRY);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_IINQUIRY+"_"+2);
		subExtType.setHassub("0");
		subExtType.setName("体育娱乐");
		subExtType.setParentid(CoreContants.MENU_CODE_IINQUIRY);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_IINQUIRY+"_"+3);
		subExtType.setHassub("0");
		subExtType.setName("趣味其他");
		subExtType.setParentid(CoreContants.MENU_CODE_IINQUIRY);
		subExtType.setType("0");
		subs.add(subExtType);
		inquiry.setSubtypes(subs);
		mTypeList.add(inquiry);
		//拍客播客
		NewsTypeModel paike=new NewsTypeModel();
		subs=new ArrayList<NewsTypeModel>();
		paike.setId(CoreContants.MENU_CODE_PK);
		paike.setName("拍客播客");
		paike.setHassub("0");
		paike.setType(CoreContants.NEWS_TYPE_EXT);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_PK+"_"+0);
		subExtType.setHassub("0");
		subExtType.setName("主题拍");
		subExtType.setParentid(CoreContants.MENU_CODE_PK);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_PK+"_"+1);
		subExtType.setHassub("0");
		subExtType.setName("随手拍");
		subExtType.setParentid(CoreContants.MENU_CODE_PK);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_PK+"_"+2);
		subExtType.setHassub("0");
		subExtType.setName("主题播");
		subExtType.setParentid(CoreContants.MENU_CODE_PK);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_PK+"_"+3);
		subExtType.setHassub("0");
		subExtType.setName("随手播");
		subExtType.setParentid(CoreContants.MENU_CODE_PK);
		subExtType.setType("0");
		subs.add(subExtType);
		paike.setSubtypes(subs);
		mTypeList.add(paike);
		//便民
		NewsTypeModel bianmin=new NewsTypeModel();
		subs=new ArrayList<NewsTypeModel>();
		bianmin.setId(CoreContants.MENU_CODE_BIANMIN);
		bianmin.setName("便民信息");
		bianmin.setHassub("0");
		bianmin.setType(CoreContants.NEWS_TYPE_EXT_WEB);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+0);
		subExtType.setHassub("0");
		subExtType.setName("社会保险");
		subExtType.setExt("http://www.lnyk.lss.gov.cn:7001/issp/");
		subExtType.setParentid(CoreContants.MENU_CODE_BIANMIN);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+1);
		subExtType.setHassub("0");
		subExtType.setName("公积金查询");
		subExtType.setExt("http://www.ykgjjzx.gov.cn/ecdomain/framework/ykgjj/kkdcfhma-pfcd-bbno-leoj-nmfcknbikdpg.jsp");
		subExtType.setParentid(CoreContants.MENU_CODE_BIANMIN);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+2);
		subExtType.setHassub("0");
		subExtType.setName("交通违章查询");
		subExtType.setExt("http://www.ykjj.gov.cn/wfcx.php");
		subExtType.setParentid(CoreContants.MENU_CODE_PK);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+3);
		subExtType.setHassub("0");
		subExtType.setName("满12分查询");
		subExtType.setExt("http://www.ykjj.gov.cn/12cx.php");
		subExtType.setParentid(CoreContants.MENU_CODE_BIANMIN);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+4);
		subExtType.setHassub("0");
		subExtType.setName("煤气公司");
		subExtType.setExt("http://www.yingkougas.com/wskf01.asp");
		subExtType.setParentid(CoreContants.MENU_CODE_BIANMIN);
		subExtType.setType("0");
		subs.add(subExtType); 
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+6);
		subExtType.setHassub("0");
		subExtType.setName("房产信息");
		subExtType.setExt("http://www.ykhouse.com/index.php");
		subExtType.setParentid(CoreContants.MENU_CODE_BIANMIN);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+7);
		subExtType.setHassub("0");
		subExtType.setName("自来水公司");
		subExtType.setExt("http://www.ykwater.com/news.php?sid=9");
		subExtType.setParentid(CoreContants.MENU_CODE_BIANMIN);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+8);
		subExtType.setHassub("0");
		subExtType.setName("人才招聘");
		subExtType.setExt("http://www.ykrc.com.cn");
		subExtType.setParentid(CoreContants.MENU_CODE_BIANMIN);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+9);
		subExtType.setHassub("0");
		subExtType.setName("价格行情");
		subExtType.setExt("http://www.ykwjj.cn/ykwj/index.php?s=/Index");
		subExtType.setParentid(CoreContants.MENU_CODE_BIANMIN);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+10);
		subExtType.setHassub("0");
		subExtType.setName("家政信息");
		subExtType.setExt("http://www.0417.com.cn/");
		subExtType.setParentid(CoreContants.MENU_CODE_BIANMIN);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+10);
		subExtType.setHassub("0");
		subExtType.setName("未成年人");
		subExtType.setExt("http://yk.wenming.cn/wcnr/");
		subExtType.setParentid(CoreContants.MENU_CODE_BIANMIN);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+11);
		subExtType.setHassub("0");
		subExtType.setName("点定药店");
		subExtType.setExt("http://ykfda.lnfda.gov.cn/CL0260/");
		subExtType.setParentid(CoreContants.MENU_CODE_BIANMIN);
		subExtType.setType("0");
		subs.add(subExtType);
		subExtType=new NewsTypeModel();
		subExtType.setId(CoreContants.MENU_CODE_BIANMIN+"_"+12);
		subExtType.setHassub("0");
		subExtType.setName("航班时刻");
		subExtType.setExt("http://www.airchina.com.cn/");
		subExtType.setParentid(CoreContants.MENU_CODE_BIANMIN);
		subExtType.setType("0");
		subs.add(subExtType);
		bianmin.setSubtypes(subs);
		mTypeList.add(bianmin);
	}*/
}
