package com.plugin.commons.ui.baoliao;

import java.util.ArrayList;
import java.util.List;

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
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.BaoliaoListAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.BaoLiaoInfoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.BaoliaoService;
import com.plugin.commons.service.UserInfoService;
import com.plugin.commons.service.UserInfoServiceImpl;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.zq.types.StBaseType;

/**
 * @author zhang
 * 我的回复fragment
 */
public class MyBlReplyTabFragment extends BaseFragment {
	public DingLog log = new DingLog(MyBlReplyTabFragment.class);
	private static final String TAG = "MyBlReplyTabFragment";
	private List<BaoLiaoInfoModel> baoliaoList = new ArrayList<BaoLiaoInfoModel>();
	private BaoliaoListAdapter mAdapter;
	private BaoliaoService blService;
	UserInfoService userService;
	private int start=0;
	 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listview_pullrefresh, container, false);
		return view;
	}
	
	 
	protected void initViews(View view) {
		lv_news = (PullToRefreshListView) view.findViewById(R.id.lv_news);
		mAdapter = new BaoliaoListAdapter(mActivity,baoliaoList);
		lv_news.setAdapter(mAdapter);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				doRefresh();
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
				Intent intent = new Intent(mActivity,BaoliaoDetailActivity.class);
				intent.putExtra(BaoliaoDetailActivity.PARAMS_MSG,baoliaoList.get(arg2-1));
				mActivity.startActivity(intent);
				//startActivity(intent);
			}
			
		});
	
	}
	
	protected void initDisplay() {
		blService=ComApp.getInstance().getBlService();
		userService =new UserInfoServiceImpl();
//		baoliaoList = DisClsTestService.getBaoliaoList(mMsgName).getBaoLiaoList();
		DialogUtil.showProgressDialog(mActivity);
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){

			@Override
			public StBaseType requestApi() {
				RspResultModel rsp = blService.getMyReplyBaoliaoList(false,String.valueOf(start),CoreContants.PAGE_SIZE+"");
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(mActivity);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(mActivity, result)){
					baoliaoList = result.getBaoliao_list();
					log.info("newList:"+baoliaoList.size());
//					MyBlReplyTabFragment.this.start=baoliaoList.size();
					mAdapter.setBaoliaoList(baoliaoList);
					lv_news.setAdapter(mAdapter);
					log.info("newList:"+baoliaoList.size());
					mAdapter.notifyDataSetChanged();
				}
				ComUtil.showListNone(getView(), "暂无我的回答", baoliaoList);
				lv_news.onRefreshComplete();
				
			}
			
		});
		
		
		//doRefresh();
		//lv_news.setRefreshing(false);
	}
	
	private void doRefresh()
	{
		// Do work to refresh the list here.
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				RspResultModel rsp = blService.getMyReplyBaoliaoList(false,String.valueOf(start),CoreContants.PAGE_SIZE+"");
//				MyBlReplyTabFragment.this.start=AppConstants.PAGE_SIZE+start;
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(mActivity, result)){
					log.info("Baoliao_list:"+result.getBaoliao_list().size());
					if(result.getBaoliao_list().size()>0){
						baoliaoList = result.getBaoliao_list();
						mAdapter.setBaoliaoList(baoliaoList);
//						MyBlReplyTabFragment.this.start+=baoliaoList.size();
					}
					mAdapter.notifyDataSetChanged();
				}
				ComUtil.showListNone(getView(), "暂无我的回答", baoliaoList);
				lv_news.onRefreshComplete();
			}
			
		});
		
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

	@Override
	public void onFrageSelect(int index) {
		// TODO Auto-generated method stub
		
	}

}
