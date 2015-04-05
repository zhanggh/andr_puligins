package com.plugin.commons.ui.my;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.MyCommentListAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.Reply;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.service.SituoAjaxCallBackImp;
import com.plugin.commons.ui.base.ActivityWeb;
import com.plugin.commons.ui.base.ActivityWebExt;
import com.plugin.commons.ui.fragment.base.BaseFragment;

/**
 * @author zhang
 * 我的评论
 */
public class MyCommentFragment extends BaseFragment {
	public DingLog log = new DingLog(MyCommentFragment.class);
	private static final String TAG = "MyCommentFragment";
	 
	NewsService newsSvc;
	List<Reply> dataList= new ArrayList<Reply>();
	private ZhKdBaseAdapter<Reply> mAdapter;
	
	 
	protected void initViews(View view) {
		newsSvc = new NewsServiceImpl();
		lv_news = (PullToRefreshListView) view.findViewById(R.id.lv_news);
		mAdapter = new MyCommentListAdapter(mActivity,dataList);
		lv_news.setAdapter(mAdapter);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(),
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
//				Toast.makeText(mActivity, "已无更多加载", Toast.LENGTH_SHORT).show();
			}
		});
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Reply reply = dataList.get(arg2-1);
				if(FuncUtil.isEmpty(reply.getArt_showurl())){
					Toast.makeText(mActivity, "无新闻详情", Toast.LENGTH_SHORT).show();
					return;
				}
				NewsInfoModel mNews=new NewsInfoModel();
				mNews.setArttype(reply.getArt_type());
				mNews.setContent(reply.getArt_title());
				mNews.setId(reply.getArt_id());
				mNews.setUrl(reply.getArt_showurl());
				mNews.setReplycount(reply.getReplycount());
				NewsTypeModel mNewType =new NewsTypeModel();
				mNewType.setId(reply.getArt_type());
				Intent intent = null;
				if(CoreContants.APP_LNZX.equals(ComApp.APP_NAME)){
					intent = new Intent(mActivity,ActivityWebExt.class);
				}else{
					intent = new Intent(mActivity,ActivityWeb.class);
				}
				intent.putExtra(CoreContants.PARAMS_NEWS,mNews);
				intent.putExtra(CoreContants.PARAMS_TYPE,mNewType);
				mActivity.startActivity(intent);
			}
			
		});
	
	}
	protected void initDisplay() {
		doRefresh(true,true);
	}

	private void doRefresh(boolean isInit,boolean isRefresh)
	{
		if(!ComApp.getInstance().isLogin())
		{
			DialogUtil.showToast(mActivity, "尚未登陆，请登陆后查看");
			lv_news.onRefreshComplete();
		}else{
			ComUtil.showListNone(getView(), "努力加载中...", dataList);
			//异步
			sCallBack=new SituoAjaxCallBackImp<Reply,NewsService>(this.getView(),this.pageStart,this.dataList,isInit, isRefresh, this.mActivity, 
					lv_news, mAdapter,CoreContants.REQUEST_MY_COMMENT,newsSvc) {//, null,newsSvc,null

				@Override
				public void afterService(Map<String,Reply> response ,List<Reply> dataList,int pageStart) {
					MyCommentFragment.this.pageStart=pageStart;
					MyCommentFragment.this.dataList=dataList;
				}
			};
			// Do work to refresh the list here.
			SituoHttpAjax.ajax(sCallBack);
		}
		
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}
	public void onFrageSelect(int idnex){
		
	}

}
