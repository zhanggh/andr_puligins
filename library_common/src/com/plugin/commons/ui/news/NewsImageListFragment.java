package com.plugin.commons.ui.news;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.ComPhotListAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.CacheDataService;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.zq.types.StBaseType;

public class NewsImageListFragment extends BaseFragment {
	public DingLog log = new DingLog(NewsImageListFragment.class);
	private static final String TAG = "NewsImageListFragment";
	private List<NewsInfoModel> newList = new ArrayList<NewsInfoModel>();
	private ZhKdBaseAdapter<NewsInfoModel> mAdapter;
	NewsService newsSvc;
	NewsTypeModel mNewType;
	private LinearLayout ll_img_bar;

	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.info(getFragmentName(), " onCreateView()");
		View view = inflater.inflate(R.layout.fragment_photo_list, container, false);
		return view;
	}
	protected void initViews(View view) {
		initFooterView();
		
		ll_img_bar=(LinearLayout)view.findViewById(R.id.ll_img_bar);
		ll_img_bar.setVisibility(View.GONE);
		newsSvc = new NewsServiceImpl();
		lv_news = (PullToRefreshListView) view.findViewById(R.id.lv_news);
		mAdapter = new ComPhotListAdapter(getActivity(),newList);
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
				if(NewsImageListFragment.this.tv_msg!=null){
					NewsImageListFragment.this.tv_msg.setText("加载中...");
					NewsImageListFragment.this.pro_btm.setVisibility(View.VISIBLE);
				}
				doRefresh(false,false);
			}
		});
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				XHSDKUtil.addXHBehavior(mActivity, mNewType.getParentid()+"_"+newList.get(arg2-1).getId(), XHConstants.XHTOPIC_ARTICAL_CLICK, newList.get(arg2-1).getId());
				ComUtil.goNewsDetail(mActivity, newList.get(arg2-1), mNewType);
			}
			
		});
	}
	
	protected void initDisplay() {
		ComApp.getInstance().appStyle.getProc_loading().setVisibility(View.VISIBLE);
		RspResultModel rsp  = newsSvc.getNewsList(true,"0",String.valueOf(CoreContants.PAGE_SIZE),mNewType.getParentid(), mNewType.getId());
		if(ComUtil.checkRsp(mActivity, rsp,false)){
			newList = rsp.getArticle_list();
			if(newList!=null&&newList.size()>0){
				refreshList();
			}else{
				doRefresh(false,true);
			}
		}else{
			doRefresh(false,true);
		}
		//如果是第一个fragment并且没有下拉加载过，则第一次进入就自动下拉加载
		if("0".equals(mMsgName)&&
				CacheDataService.isNeedLoad(CoreContants.CACHE_NEWS_NEWSLIST+mNewType.getParentid()+"_"+mNewType.getId()))
		{
			
			lv_news.setRefreshing(false);
		}
	}
	
	private void refreshList(){
		mAdapter.setDataList(newList);
		mAdapter.notifyDataSetChanged();
		if(NewsImageListFragment.this.tv_msg!=null){
			NewsImageListFragment.this.pro_btm.setVisibility(View.GONE);
			NewsImageListFragment.this.tv_msg.setText(ComApp.getInstance().getResources().getString(R.string.lastLoading));
		}
		ComApp.getInstance().appStyle.getProc_loading().setVisibility(View.GONE);
		log.info("是否为空:"+FuncUtil.isEmpty(newList));
		ComUtil.showListNone(getView(), "暂无数据", newList);
	}
	
	 
	
	public void doRefresh(final boolean isCache,final boolean isRefresh)
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
				RspResultModel rsp  = newsSvc.getNewsList(isCache,String.valueOf(pageStart),String.valueOf(CoreContants.PAGE_SIZE),mNewType.getParentid(), mNewType.getId());
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
					CacheDataService.setNeedLoad(CoreContants.CACHE_NEWS_NEWSLIST+mNewType.getParentid()+"_"+mNewType.getId());
				}
				refreshList();
				lv_news.onRefreshComplete();
				if(newList.size()>=CoreContants.PAGE_SIZE&&!hasFooter){
					lv_news.getRefreshableView().addFooterView(footer);
					hasFooter=true;
				}
				
			}
		});
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}
	
	public NewsTypeModel getmNewType() {
		return mNewType;
	}

	public void setmNewType(NewsTypeModel mNewType) {
		this.mNewType = mNewType;
	}
	public void onFrageSelect(int idnex){
		if(CacheDataService.isNeedLoad(CoreContants.CACHE_NEWS_NEWSLIST+mNewType.getParentid()+"_"+mNewType.getId()))
		{
			lv_news.setRefreshing(false);
		}
	}
}