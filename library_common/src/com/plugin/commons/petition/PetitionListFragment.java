package com.plugin.commons.petition;

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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.PetitionListAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.service.AskGovService;
import com.plugin.commons.service.AskGovServiceImpl;
import com.plugin.commons.service.SituoAjaxCallBackImp;
import com.plugin.commons.ui.fragment.base.BaseFragment;

/**
 * @author zhang
 * 书记信箱列表（信访列表）fragment
 */
public class PetitionListFragment extends BaseFragment {
	public DingLog log = new DingLog(PetitionListFragment.class);
	private static final String TAG = "PetitionListFragment";
	private List<AskMsgModel> dataList = new ArrayList<AskMsgModel>();
	private ZhKdBaseAdapter<AskMsgModel> mAdapter;
	AskGovService askSvc;
	
	
	protected void initViews(View view) {
		askSvc = new AskGovServiceImpl();
		lv_news = (PullToRefreshListView) view.findViewById(R.id.lv_news);
		mAdapter = new PetitionListAdapter(mActivity,dataList);
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
			}
		});
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mActivity,PetitionDetailActivity.class);
				intent.putExtra(CoreContants.PARAMS_TITLE, "交流详情");
				intent.putExtra(CoreContants.PARAMS_MSG,dataList.get(arg2-1));
				startActivity(intent);
			}
			
		});
	
	}
	protected void initDisplay(){
		doRefresh(true, true);
	}
	private void doRefresh(final boolean isInit,final boolean isRefresh)
	{
		if(!ComApp.getInstance().isLogin())
		{
			DialogUtil.showToast(mActivity, "尚未登陆，请登陆后查看");
			lv_news.onRefreshComplete();
		}else{
			ComUtil.showListNone(getView(), "努力加载中...", dataList);
			//异步
			sCallBack=new SituoAjaxCallBackImp<AskMsgModel,AskGovService>(this.getView(),pageStart,this.dataList,isInit, isRefresh, 
					mActivity, lv_news, mAdapter,CoreContants.REQUEST_MY_LETTER,askSvc) {//, askSvc,null,null

				@Override
				public void afterService(Map<String,AskMsgModel> response,List<AskMsgModel> dataList,int pageStart) {
					PetitionListFragment.this.pageStart=pageStart;
					PetitionListFragment.this.dataList=dataList;
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
