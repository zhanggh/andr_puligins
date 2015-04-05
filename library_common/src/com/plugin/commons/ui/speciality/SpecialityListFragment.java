package com.plugin.commons.ui.speciality;

import java.util.ArrayList;
import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.SpecialityListAdapter;
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

public class SpecialityListFragment extends BaseFragment {
	public DingLog log = new DingLog(SpecialityListFragment.class);
	private static final String TAG = "SpecialityListFragment";
	private PullToRefreshListView lv_petitions;
	private List<NewsInfoModel> newList = new ArrayList<NewsInfoModel>();
	private SpecialityListAdapter mAdapter;
	NewsTypeModel mNewType;
	NewsService newsSvc;
	 
	 
	protected void initViews(View view) {
		newsSvc = new NewsServiceImpl();
		mNewType = newsSvc.getNewsType(CoreContants.MENU_CODE_SPECIAL);
		lv_petitions = (PullToRefreshListView) view.findViewById(R.id.lv_news);
//		mAdapter = new SpecialityListAdapter(mActivity,newList);
//		lv_petitions.setAdapter(mAdapter);
		lv_petitions.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				doRefresh(false,true);
			}
		});
		
		lv_petitions.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
//				Toast.makeText(mActivity, "已无更多加载", Toast.LENGTH_SHORT).show();
				doRefresh(false,false);
			}
		});
		lv_petitions.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mActivity,SpecialityDetailActivity.class);
				intent.putExtra(CoreContants.PARAMS_MSG,newList.get(arg2-1));
				mActivity.startActivity(intent);
				//startActivity(intent);
				XHSDKUtil.addXHBehavior(mActivity,CoreContants.MENU_CODE_SPECIAL+"_"+newList.get(arg2-1).getId(), XHConstants.XHTOPIC_ARTICAL_CLICK, newList.get(arg2-1).getId());
			}
			
		});
	
	}
	
	protected void initDisplay() {
		lv_petitions.setRefreshing(true);
		doRefresh(true,false);
		refreshList();
	}
	
	private void refreshList(){
		if(mAdapter==null){
			mAdapter = new SpecialityListAdapter(mActivity,newList);
			lv_petitions.setAdapter(mAdapter);
		}
		
		mAdapter.setSpecList(newList);
		mAdapter.notifyDataSetChanged();
		lv_petitions.onRefreshComplete();
		log.info("是否为空:"+FuncUtil.isEmpty(newList));
		ComUtil.showListNone(getView(), "暂无数据", newList);
	}
	
	public void doRefresh(final boolean cache,final boolean isRefresh)
	{
		ComUtil.showListNone(this.getView(), "努力加载中...", newList);
		if(isRefresh){//下拉
			pageStart=0;
		}else{//上拉
			pageStart+=CoreContants.PAGE_SIZE;
		}
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				RspResultModel rsp  = newsSvc.getNewsList(cache,String.valueOf(pageStart),String.valueOf(CoreContants.PAGE_SIZE),mNewType.getId(), "0");
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(mActivity);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(mActivity, result)){
//					newList = result.getArticle_list();
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
				}else{
					lv_petitions.onRefreshComplete();
				}
			}
		});
	}
	
	public void onFrageSelect(int idnex){
		
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onPageStart(mActivity, "SpecialityListFragment");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPageEnd(mActivity, "SpecialityListFragment");
	}

}