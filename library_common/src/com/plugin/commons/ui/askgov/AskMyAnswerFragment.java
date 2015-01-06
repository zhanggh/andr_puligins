package com.plugin.commons.ui.askgov;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.plugin.commons.adapter.AskHotListAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.AskGovService;
import com.plugin.commons.service.AskGovServiceImpl;
import com.zq.types.StBaseType;

public class AskMyAnswerFragment extends Fragment {
	public DingLog log = new DingLog(AskMyAnswerFragment.class);
	private Activity mActivity;
	private PullToRefreshListView lv_news;
	private List<AskMsgModel> dataList = new ArrayList<AskMsgModel>();
	private AskHotListAdapter mAdapter;
	private String myTab;
	AskGovService askSvc;
	boolean hasLoad ;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listview_pullrefresh, container, false);
		askSvc = new AskGovServiceImpl();
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		log.info("onViewCreated");
		initViews(view);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		log.info("onActivityCreated");
		 initDisplay();
		 if(!hasLoad){
			 doRefresh(true);
		 }
	}
	
	
	private void initViews(View view) {
		lv_news = (PullToRefreshListView) view.findViewById(R.id.lv_news);
		mAdapter = new AskHotListAdapter(mActivity,dataList);
		lv_news.setAdapter(mAdapter);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				doRefresh(false);
			}
		});
		
		lv_news.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				Toast.makeText(mActivity, "已无更多加载", Toast.LENGTH_SHORT).show();
			}
		});
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mActivity,AskDetailActivity.class);
				intent.putExtra(CoreContants.PARAMS_MSG,dataList.get(arg2-1));
				mActivity.startActivity(intent);
			}
			
		});
	
	}
	
	private void initDisplay() {
		//mMsgTv.setText("暂无新闻");
		RspResultModel rsp  = askSvc.getMyAskList(true, "0", "20", "1");
		if(rsp!=null&&rsp.getMsg_list()!=null){
			dataList = rsp.getMsg_list();
		}
		mAdapter.setDataList(dataList);
		lv_news.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		ComUtil.showListNone(getView(), "暂无我的回答", dataList);
		//doRefresh();
		//lv_news.setRefreshing(false);
	}
	
	private void doRefresh(boolean show)
	{
		// Do work to refresh the list here.
		if(show){
			lv_news.setRefreshing(false);
		}
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				RspResultModel rsp  = askSvc.getMyAskList(false, "0", "20","1");
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(mActivity);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(mActivity, result)){
					dataList = result.getMsg_list();
					mAdapter.setDataList(dataList);
					mAdapter.notifyDataSetChanged();
					hasLoad = true;
					ComUtil.showListNone(getView(), "暂无我的回答", dataList);
				}
				lv_news.onRefreshComplete();
			}
		});
		
	}
	@Override
	public void onDestroyView()  
    {  
		super.onDestroyView(); 
    }

	public String getMyTab() {
		return myTab;
	}

	public void setMyTab(String myTab) {
		this.myTab = myTab;
	}
}
