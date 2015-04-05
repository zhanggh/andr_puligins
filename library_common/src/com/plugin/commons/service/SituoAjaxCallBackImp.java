package com.plugin.commons.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.NumberModel;
import com.plugin.commons.model.NumberType;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.SysNoticeModel;
import com.zq.types.StBaseType;

public abstract class SituoAjaxCallBackImp<T,M> implements SituoAjaxCallBack {
	private boolean isInit,isRefresh;
	private List<T> dataList;
	private List<T> rspList;
	private M m;
	private Activity activity;
	private PullToRefreshListView lv_news;
	private ZhKdBaseAdapter<T> mAdapter;
	private DingLog log ;
	int pageStart=0;
	private String reqType;
	private AskGovService askSvc;
	private NewsService newService;
	private SysNotifyService sysNotifySv;
	private NumberService numSvc;
	private View view;
	private NewsTypeModel mNewType;
	private NewsInfoModel mNews;
	private String keyWord;//关键字
	
	/**
	 * @param view
	 * @param pageStart 开始查询的游标
	 * @param dataList 数据集
	 * @param isInit 是否初始化
	 * @param isRefresh 是否下拉
	 * @param log 日志
	 * @param activity 
	 * @param lv_news 拉下组件
	 * @param mAdapter 
	 * @param reqType 请求类型
	 * @param askSvc 业务对象
	 * @param newService 业务对象
	 * @param sysNotifySv 业务对象
	 * @param numSvc 业务对象
	 */
	public SituoAjaxCallBackImp(View view,int pageStart,List<T> dataList,boolean isInit,boolean isRefresh,
			Activity activity,PullToRefreshListView lv_news,ZhKdBaseAdapter<T> mAdapter
			,String reqType,M m) {//,AskGovService askSvc,NewsService newService,SysNotifyService sysNotifySv
		super();
		this.view=view;
		this.isInit=isInit;
		this.isRefresh=isRefresh;
		this.pageStart=pageStart;
		this.dataList=dataList;
		this.activity=activity;
		this.reqType=reqType;
		this.lv_news=lv_news;
		this.mAdapter=mAdapter;
		this.m=m;
		log=new DingLog(SituoAjaxCallBackImp.class);
	}

	@Override
	public StBaseType requestApi() {
		if(isRefresh){//下拉
			pageStart=0;
		}else{//上拉
			pageStart+=CoreContants.PAGE_SIZE;
		}
//		log.debug("***********************必须重新requestApi方法**************************");
		RspResultModel rsp =null;
		if(CoreContants.REQUEST_LETTER.equals(reqType)){//我的信件、信件列表
			askSvc=(AskGovService) m;
			rsp =  askSvc.getMyAskList(isInit, String.valueOf(pageStart),String.valueOf(CoreContants.PAGE_SIZE), "0");
		}
		if(CoreContants.REQUEST_COMMENT.equals(reqType)){//评论列表
			newService=(NewsService) m;
			String type = null;//评论类型 
			String msg_id = null;//原文id
			String newsTypeId = null;
			if (activity.getIntent().getExtras() != null && activity.getIntent().getExtras().containsKey(CoreContants.PARAMS_MSG)) {
				type =(String)activity.getIntent().getExtras().get(CoreContants.PARAMS_MSG);
				msg_id =(String)activity.getIntent().getExtras().get(CoreContants.PARAMS_MSG_ID);
			}
			if (activity.getIntent().getExtras() != null && activity.getIntent().getExtras().containsKey(CoreContants.PARAMS_TYPE)) {
				newsTypeId =(String)activity.getIntent().getExtras().get(CoreContants.PARAMS_TYPE);
			}
			rsp = newService.commentsList(false,msg_id,type, String.valueOf(pageStart),String.valueOf(CoreContants.PAGE_SIZE),newsTypeId);
		}
		if(CoreContants.REQUEST_MY_LETTER.equals(reqType)){//我的信件、信件列表
			askSvc=(AskGovService) m;
			rsp =  askSvc.getMyAskList(isInit, String.valueOf(pageStart),String.valueOf(CoreContants.PAGE_SIZE), "0");
		}
		if(CoreContants.REQUEST_MY_COMMENT.equals(reqType)){//我的-评论
			newService=(NewsService) m;
			rsp =  newService.getMyCmNewList(isInit,  String.valueOf(pageStart), String.valueOf(CoreContants.PAGE_SIZE));
		}
		if(CoreContants.REQUEST_NOTICE.equals(reqType)){//我的-通知
			sysNotifySv=(SysNotifyService) m;
			if(!isRefresh){//下拉
				rsp = new RspResultModel();
				rsp.setRetcode("0");
				rsp.setNotice_list(new ArrayList<SysNoticeModel>());
			}else{
				rsp = sysNotifySv.getNotifyList();
			}
			
		}
		
		if(CoreContants.REQUEST_COM_PHOTOS.equals(reqType)){//拍客播客
//			rsp =  DisClsTestService.getComPhotos(false);
			newService=(NewsService) m;
			rsp  = newService.getSubNewsList(false, String.valueOf(pageStart),String.valueOf(CoreContants.PAGE_SIZE),mNewType.getParentid(), mNewType.getId(),getmNews().getId());
			
		}
		if(CoreContants.REQUEST_PAIKE_DETAIL.equals(reqType)){//拍客详情
			rsp =  DisClsTestService.getComPhotos(false);
			
		}
		if(CoreContants.REQUEST_NUMBER.equals(reqType)){//号码通列表
			numSvc=(NumberService) m;			
			NumberType type=null;				
			if (activity.getIntent().getExtras() != null && activity.getIntent().getExtras().containsKey(CoreContants.PARAMS_MSG)) {
				type =(NumberType)activity.getIntent().getExtras().get(CoreContants.PARAMS_MSG);
			}	
			String id=String.valueOf(type.getId());
			if(id.equals("-1")){
				id="";
			}
			rsp = numSvc.getNumList(isInit,id,keyWord, String.valueOf(pageStart),String.valueOf(CoreContants.PAGE_SIZE));
			
		}
		
		return rsp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void callBack(StBaseType baseType) {
		 
		RspResultModel result = (RspResultModel)baseType;
		if(ComUtil.checkRsp(activity, result)){
			if(CoreContants.REQUEST_COMMENT.equals(reqType)){//新闻评论
				rspList = (List<T>) result.getComment_list();
			}
			if(CoreContants.REQUEST_LETTER.equals(reqType)){//信件列表
				rspList = (List<T>) result.getMsg_list();
			}
			if(CoreContants.REQUEST_MY_LETTER.equals(reqType)){//我的-信件列表
				rspList = (List<T>) result.getMsg_list();
			}
			if(CoreContants.REQUEST_MY_COMMENT.equals(reqType)){//我的-评论列表
				rspList = (List<T>) result.getMyreply_list();
			}
			
			if(CoreContants.REQUEST_ART_27.equals(reqType)){//文章列表
				rspList = (List<T>) result.getArticle_list();
			}
			
			if(CoreContants.REQUEST_NOTICE.equals(reqType)){//系统通知列表
				rspList = (List<T>) result.getNotice_list();
			}
			if(CoreContants.REQUEST_COM_PHOTOS.equals(reqType)){//拍客列表
				rspList = (List<T>) result.getArticle_list();
			}
			
			if(CoreContants.REQUEST_COM_VIDEO.equals(reqType)){//播客列表
				rspList = (List<T>) result.getComphotos();
			}
			
			if(CoreContants.REQUEST_NUMBER.equals(reqType)){//号码通列表
				rspList = (List<T>) result.getNumbers();
			}
			
			log.info("mComments:"+rspList.size());
			if(rspList.size()==0){
				pageStart-=CoreContants.PAGE_SIZE;
			}else{
				if(!isRefresh){//如果是上拉，并且返回结果值不为空时，游标递增
					dataList.addAll(rspList);
				}else{
					dataList=rspList;
				}
				mAdapter.setDataList(dataList);
				afterService(null,dataList,pageStart);
				mAdapter.notifyDataSetChanged();
			}
		}
		ComUtil.showListNone(view, "暂无数据", dataList);
		if(!isInit){
			lv_news.onRefreshComplete();
		}
	}
	/**
	 * 回调方法
	 * @param response
	 * @param pageStart
	 */
	public abstract void afterService(Map<String,T> response ,List<T> dataList,int pageStart);

	public void setmNews(NewsInfoModel mNews) {
		this.mNews = mNews;
	}

	public NewsInfoModel getmNews() {
		return mNews;
	}

	public NewsTypeModel getmNewType() {
		return mNewType;
	}

	public void setmNewType(NewsTypeModel mNewType) {
		this.mNewType = mNewType;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
}
