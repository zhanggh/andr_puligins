package com.plugin.commons.ui.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.SysNoticeListAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.model.SysNoticeModel;
import com.plugin.commons.service.SituoAjaxCallBackImp;
import com.plugin.commons.service.SysNotifyService;
import com.plugin.commons.service.SysNotifyServiceImp;
import com.plugin.commons.ui.base.BaseActivity;

public class SysNotifyActivity extends BaseActivity{
	private PullToRefreshListView lv_news;
	ZhKdBaseAdapter<SysNoticeModel> mAdapter;
	private List<SysNoticeModel> dataList= new ArrayList<SysNoticeModel>();
	SysNotifyService sysNotifySv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_pullrefresh);
		ComUtil.customeTitle(this, "通知",true);
		initViews();
		initDisplay();
	}
	private void initViews() {
		reqService=new HashMap();
		sysNotifySv = new  SysNotifyServiceImp();
		reqService.put("reqService", reqService);
		lv_news = (PullToRefreshListView) this.findViewById(R.id.lv_news);
		mAdapter = new SysNoticeListAdapter(this,dataList);
		lv_news.setAdapter(mAdapter);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(ComApp.getInstance(), System.currentTimeMillis(),
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
//				Toast.makeText(SysNotifyActivity.this, "已无更多加载", Toast.LENGTH_SHORT).show();
			}
		});
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}
			
		});
	
	}
	
	private void initDisplay() {
		 
		doRefresh(true,true);
	}
	
	private void doRefresh(boolean isInit,boolean isRefresh)
	{
		
		//异步
		sCallBack=new SituoAjaxCallBackImp<SysNoticeModel,SysNotifyService>(findViewById(R.id.ll_root),pageStart,this.dataList,isInit, isRefresh, log, 
				this, lv_news, mAdapter,CoreContants.REQUEST_NOTICE,sysNotifySv) {//null,null,sysNotifySv

			@Override
			public void afterService(Map<String,SysNoticeModel> response,List<SysNoticeModel> dataList ,int pageStart) {
				SysNotifyActivity.this.pageStart=pageStart;
				SysNotifyActivity.this.dataList=dataList;
			}
		};
		SituoHttpAjax.ajax(sCallBack);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onResume(this);//新华sdk
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPause(this);//新华sdk
	}

}
