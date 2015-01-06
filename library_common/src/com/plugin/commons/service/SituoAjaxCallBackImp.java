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
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.SysNoticeModel;
import com.plugin.commons.ui.base.BaseActivity;
import com.zq.types.StBaseType;

public abstract class SituoAjaxCallBackImp<T,M> implements SituoAjaxCallBack {
	public boolean isInit,isRefresh;
	public List<T> dataList;
	public List<T> rspList;
	public M m;
	public Activity activity;
	public PullToRefreshListView lv_news;
	public ZhKdBaseAdapter<T> mAdapter;
	public DingLog log ;
	int pageStart=0;
	public String reqType;
	public AskGovService askSvc;
	public NewsService newService;
	public SysNotifyService sysNotifySv;
	private View view;
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
	 */
	public SituoAjaxCallBackImp(View view,int pageStart,List<T> dataList,boolean isInit,boolean isRefresh,DingLog log,
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
		this.log=log;
		this.m=m;
//		this.askSvc=askSvc;
//		this.newService=newService;
//		this.sysNotifySv=sysNotifySv;
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
			
			if(CoreContants.REQUEST_ART_27.equals(reqType)){
				rspList = (List<T>) result.getArticle_list();
			}
			
			if(CoreContants.REQUEST_NOTICE.equals(reqType)){
				rspList = (List<T>) result.getNotice_list();
			}
			
			log.info("mComments:"+rspList.size());
			if(rspList.size()==0){
				//DialogUtil.showToast(activity,"暂无数据");
				pageStart-=CoreContants.PAGE_SIZE;
			}else{
				if(!isRefresh){//如果是上拉，并且返回结果值不为空时，游标递增
					dataList.addAll(rspList);
				}else{
					dataList=rspList;
				}
//				dataList=null;
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
}
