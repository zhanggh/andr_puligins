package com.plugin.commons.ui.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.NewsCommentsListAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.CommentModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.DetailBarManager;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.service.SituoAjaxCallBackImp;
import com.plugin.commons.ui.base.BaseActivity;
import com.zq.types.StBaseType;

/**
 * @author zhang
 *  评论信息列表
 */
public class NewsCommentsListActivity extends BaseActivity{
	
	String type;//评论类型 
	String msg_id;//原文id
	List<CommentModel> dataList=  new ArrayList<CommentModel>();
	public boolean addCmSucc=false;
	LinearLayout ll_comment;
	Button btn_fav;
	NewsService newService;
	String newsTypeId;
	private PullToRefreshListView lv_news;
	private ZhKdBaseAdapter<CommentModel> mAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_comments_list);
		ComUtil.customeTitle(this, "评论",true);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_MSG)) {
			type =(String)getIntent().getExtras().get(CoreContants.PARAMS_MSG);
			msg_id =(String)getIntent().getExtras().get(CoreContants.PARAMS_MSG_ID);
		}
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_TYPE)) {
			newsTypeId =(String)getIntent().getExtras().get(CoreContants.PARAMS_TYPE);
		}
		this.addCmSucc=false;
		newService =new NewsServiceImpl();
		initViews();
		initDisplay();
	}
	
	private void initViews()
	{
		
		lv_news = (PullToRefreshListView) this.findViewById(R.id.lv_news);
		mAdapter = new NewsCommentsListAdapter(this, dataList);
		lv_news.setAdapter(mAdapter);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(NewsCommentsListActivity.this, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				doRefresh(false,true);
			}
		});
		
		lv_news.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				doRefresh(false,false);
//				Toast.makeText(NewsCommentsListActivity.this, "已无更多加载", Toast.LENGTH_SHORT).show();
			}
		});
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
 
			}
			
		});
		
		//进行评论
		CacheModel cm = new CacheModel();
		cm.type = CacheModel.CACHE_ASKBAOLIAO;
		cm.id = msg_id;
		cm.msg = null;
		DetailBarManager detailBarMng = new DetailBarManager(this,this.findViewById(R.id.rl_commentbar),cm);
		detailBarMng.setCommtentCB(new DetailBarManager.OnCommentCallBack() {
			
			@Override
			public void onCommentCallBack(Dialog dlg, final String comment) {
				 
				DialogUtil.showProgressDialog(NewsCommentsListActivity.this);
				SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {

					@Override
					public StBaseType requestApi() {
						RspResultModel  rsp = ComApp.getInstance().getApi().pubNewComment(msg_id, comment,newsTypeId);
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(NewsCommentsListActivity.this);
						RspResultModel result = (RspResultModel)baseType;
						if(ComUtil.checkRsp(NewsCommentsListActivity.this, result)){
							if("0".equals(result.getRetcode())){
								DialogUtil.showToast(NewsCommentsListActivity.this, "评论成功");
								CommentModel cm=new CommentModel();
								cm.setCreatetime(FuncUtil.formatTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
								cm.setUsername(ComApp.getInstance().getLoginInfo().getUsername());
								cm.setUserid(ComApp.getInstance().getLoginInfo().getUserid());
								cm.setUserphoto(ComApp.getInstance().getLoginInfo().getPhoto());
								cm.setContent(comment);
								//更新评论
								mAdapter.getDataList().add(0,cm);
								mAdapter.notifyDataSetChanged();
								NewsCommentsListActivity.this.addCmSucc=true;
								ComApp.getInstance().getSpUtil().setCommentRet(true);
							}else{
								DialogUtil.showToast(NewsCommentsListActivity.this, "评论失败");
							}
						}
					}
					
				});
				
			}
		});
		detailBarMng.refreshUI();
		
	}
	
	
	
	private void initDisplay(){
		doRefresh(true,true);
	}
	
	@Override
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	    	if(this.addCmSucc){
				Intent caseIntent=new Intent(CoreContants.ACTIVITY_RETTRUN);
				this.sendBroadcast(caseIntent);
			}
//	    	return false;   
	    }
	    return super.onKeyDown(keyCode, event);
	  }
	
	
	private void doRefresh(final boolean isInit,final boolean isRefresh)
	{
		ComUtil.showListNone(this.getEmptyView(), "努力加载中...", dataList);
		//异步
		sCallBack=new SituoAjaxCallBackImp<CommentModel,NewsService>(findViewById(R.id.ll_root),pageStart,this.dataList,isInit, isRefresh,
				this, lv_news, mAdapter,CoreContants.REQUEST_COMMENT,newService) {//, null,newService,null

			@Override
			public void afterService(Map<String,CommentModel> response ,List<CommentModel> dataList,int pageStart) {
				NewsCommentsListActivity.this.pageStart=pageStart;
				NewsCommentsListActivity.this.dataList=dataList;
			}
		};
		// Do work to refresh the list here.
		SituoHttpAjax.ajax(sCallBack);
	}
}
