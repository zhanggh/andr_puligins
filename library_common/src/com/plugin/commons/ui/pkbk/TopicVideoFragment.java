package com.plugin.commons.ui.pkbk;

import java.util.ArrayList;
import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

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
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.TopicPhotListAdapter;
import com.plugin.commons.adapter.TopicVideoListAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.PhotoAndVideoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.CacheDataService;
import com.plugin.commons.service.DisClsTestService;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.zq.types.StBaseType;

public class TopicVideoFragment extends BaseFragment {
	public DingLog log = new DingLog(TopicVideoFragment.class);
	private static final String TAG = "TopicVideoFragment";
	private List<NewsInfoModel> newList = new ArrayList<NewsInfoModel>();
	private List<List<NewsInfoModel>> dataList = new ArrayList<List<NewsInfoModel>>();
	private TopicVideoListAdapter mAdapter;
	NewsService newsSvc;
	DisClsTestService dctest;
	public boolean isVideo=true;
	NewsTypeModel mNewType;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.info(getFragmentName(), " onCreateView()");
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
				doRefresh(false,true);
			}
		});
		
		lv_news.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				//Toast.makeText(mActivity, "已无更多加载", Toast.LENGTH_SHORT).show();
				doRefresh(false,false);
			}
		});
	
	}
	
	protected void initDisplay() {
		doRefresh(true, true);
		refreshList();
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
	
	public void doRefresh(final boolean cache,final boolean isRefresh)
	{
		ComUtil.showListNone(getView(), "努力加载中...", newList);
		if(isRefresh){//下拉
			pageStart=0;
		}else{//上拉
			pageStart+=CoreContants.PAGE_SIZE;
		}
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				RspResultModel rsp = newsSvc.getNewsList(cache,String.valueOf(pageStart),String.valueOf(CoreContants.PAGE_SIZE),mNewType.getParentid(), mNewType.getId());
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(mActivity);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(mActivity, result)){
					if(result.getArticle_list().size()==0){
						pageStart-=CoreContants.PAGE_SIZE;
					}else{
						if(!isRefresh){//如果是上拉，并且返回结果值不为空时，游标递增
							newList.addAll(result.getArticle_list());
						}else{
							newList=result.getArticle_list();
						}
					}
					refreshList();
					CacheDataService.setNeedLoad(CoreContants.CACHE_NEWS_NEWSLIST+mNewType.getParentid()+"_"+mNewType.getId());
				
				}
				else{
					lv_news.onRefreshComplete();
				}
			}
		});
	}
	
	 private Handler mHandler = new Handler() {

			public void handleMessage(Message msg) {
				synchronized (mActivity) {
					doRefresh(false,true);
				}
			}
	 };
	
	 private void refreshList(){
			dataList = new ArrayList<List<NewsInfoModel>>();
			if(mAdapter==null){
				mAdapter = new TopicVideoListAdapter(mActivity,dataList);
				lv_news.setAdapter(mAdapter);
				mAdapter.setmNewType(mNewType);
			}
			int total=newList.size();
			int duobleSize=total/2; 
			for(int a=0;a<total;a++){
				List<NewsInfoModel> temlist=new ArrayList<NewsInfoModel>();
				
				if(duobleSize>0&&(total-a)>=2){
					temlist.add(newList.get(a));
					temlist.add(newList.get(a+1));
					a++;
				}else{
					temlist.add(newList.get(a));
				}
				
				dataList.add(temlist);
			}
			mAdapter.setDataList(dataList);
			mAdapter.notifyDataSetChanged();
			lv_news.onRefreshComplete();
			ComUtil.showListNone(getView(), "暂无数据", newList);
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
		AnalyticsAgent.onPageStart(getActivity(), "TopicVideoFragment");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPageEnd(getActivity(), "TopicVideoFragment");
	}

	public NewsTypeModel getmNewType() {
		return mNewType;
	}

	public void setmNewType(NewsTypeModel mNewType) {
		this.mNewType = mNewType;
	}

}
