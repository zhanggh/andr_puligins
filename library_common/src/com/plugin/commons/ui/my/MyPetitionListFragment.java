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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.MyPetitionListAdapter;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.petition.PetitionDetailActivity;
import com.plugin.commons.service.AskGovService;
import com.plugin.commons.service.AskGovServiceImpl;
import com.plugin.commons.service.SituoAjaxCallBackImp;
import com.plugin.commons.ui.fragment.base.BaseFragment;

/**
 * @author zhang
 * 书记信箱列表（信访列表）fragment
 */
public class MyPetitionListFragment extends BaseFragment {
	public DingLog log = new DingLog(MyPetitionListFragment.class);
	private static final String TAG = "MyPetitionListFragment";
	 
	private List<AskMsgModel> dataList = new ArrayList<AskMsgModel>();
	private MyPetitionListAdapter mAdapter;
	AskGovService askSvc;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		log.info("onViewCreated");
		initViews(view);
		askSvc = new AskGovServiceImpl();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		log.info("onActivityCreated");
		initDisplay();
	}
	
	private void initViews(View view) {
		lv_news = (PullToRefreshListView) view.findViewById(R.id.lv_news);
		mAdapter = new MyPetitionListAdapter(mActivity,dataList);
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
				// TODO Auto-generated method stub
				Intent intent = new Intent(mActivity,PetitionDetailActivity.class);
				intent.putExtra(CoreContants.PARAMS_MSG,dataList.get(arg2-1));
				mActivity.startActivity(intent);
				//startActivity(intent);
			}
			
		});
	
	}
	
	private void initDisplay() {
		doRefresh(true,true);
	}
	
	/**
	 * @param isInit 初始化？
	 * @param isRefresh 下拉？
	 */
	private void doRefresh(boolean isInit,boolean isRefresh)
	{ 
		if(!ComApp.getInstance().isLogin())
		{
			DialogUtil.showToast(mActivity, "尚未登陆，请登陆后查看");
			lv_news.onRefreshComplete();
		}else{
			//异步
			sCallBack=new SituoAjaxCallBackImp<AskMsgModel,AskGovService>(this.getView(),this.pageStart,this.dataList,isInit, isRefresh, log,
					mActivity, lv_news, mAdapter,CoreContants.REQUEST_MY_LETTER,askSvc) {//, askSvc,null,null

				@Override
				public void afterService(Map<String,AskMsgModel> response ,List<AskMsgModel> dataList,int pageStart) {
					MyPetitionListFragment.this.pageStart=pageStart;
					MyPetitionListFragment.this.dataList=dataList;
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
