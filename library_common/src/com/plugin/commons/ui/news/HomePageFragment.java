package com.plugin.commons.ui.news;

import java.util.ArrayList;
import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.HomeNewsListAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.CacheDataService;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.zq.types.StBaseType;

public class HomePageFragment extends BaseFragment {
	public DingLog log = new DingLog(HomePageFragment.class);
	private static final String TAG = "HomePageFragment";
	private List<List<NewsInfoModel>> newList = new ArrayList<List<NewsInfoModel>>();
	private HomeNewsListAdapter mAdapter;
	private NewsService newsSvc;
	NewsTypeModel mNewType; 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_homepage, container, false);
		return view;
	}
	
 
	protected void initViews(View view) {
		newsSvc = new NewsServiceImpl();
		lv_news = (PullToRefreshListView) view.findViewById(R.id.lv_news);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				doRefresh(true);
			}
		});
		
		lv_news.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				//Toast.makeText(mActivity, "已无更多加载", Toast.LENGTH_SHORT).show();
//				doRefresh(false);
			}
		});
	
	}
	
	protected void initDisplay() {
		ComApp.getInstance().appStyle.getProc_loading().setVisibility(View.VISIBLE);
		RspResultModel rsp = newsSvc.homeList(true);
		if(ComUtil.checkRsp(mActivity, rsp,false)){
			newList = rsp.getHome_page_list();
			if(newList!=null&&newList.size()>0){
				CacheDataService.setNeedLoad(CoreContants.CACHE_HOME_LIST);
				refreshList();
			}else{
				doRefresh(true);
			}
		}else{
			doRefresh(true);
		}
		if(CacheDataService.isNeedLoad(CoreContants.CACHE_HOME_LIST))
		{
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					Message msg = new Message();
					mHandler.sendMessage(msg);
				}
			}, 200);
			
		}
		 
	}
	
	public void doRefresh(final boolean isRefresh)
	{
		 
		ComUtil.showListNone(getView(), "努力加载中...", newList);
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				RspResultModel rsp = newsSvc.homeList(false);
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(mActivity);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(mActivity, result)){
					newList = result.getHome_page_list();
					if(newList!=null&&newList.size()>0){
						CacheDataService.setNeedLoad(CoreContants.CACHE_HOME_LIST);
					}
				}
				lv_news.onRefreshComplete();
				refreshList();
			}
		});
 
	}
	
	 private Handler mHandler = new Handler() {

			public void handleMessage(Message msg) {
				synchronized (mActivity) {
					doRefresh(true);
				}
			}
	 };
	
	private void refreshList(){
		int count=0;
		if(FuncUtil.isEmpty(newList)){
			newList = new ArrayList<List<NewsInfoModel>>();
		}
		List<List<NewsInfoModel>> temList=new ArrayList<List<NewsInfoModel>>();
		if(newList!=null){
			count=newList.size();
			for(List<NewsInfoModel> list:newList){
				if(list==null||list.size()==0){
					temList.add(list);
				} 
			}
			for(List<NewsInfoModel> list:temList){
				newList.remove(list);
			}
		} 
		if(temList.size()!=count&&newList.size()!=0){
			mAdapter = new HomeNewsListAdapter(mActivity,newList);
			mAdapter.setmNewType(mNewType);
			lv_news.setAdapter(mAdapter);
			mAdapter.setNewList(newList);
			mAdapter.notifyDataSetChanged();
			ComUtil.showListNone(getView(), "暂无数据", newList);
		}else{
			ComUtil.showListNone(getView(), "暂无数据", null);
		}
		ComApp.getInstance().appStyle.getProc_loading().setVisibility(View.GONE);
		log.info("是否为空:"+FuncUtil.isEmpty(newList));
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}
	public void onFrageSelect(int idnex){
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onPageStart(getActivity(), "HomePageFragment");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPageEnd(getActivity(), "HomePageFragment");
	}

	public NewsTypeModel getmNewType() {
		return mNewType;
	}

	public void setmNewType(NewsTypeModel mNewType) {
		this.mNewType = mNewType;
	}

}
