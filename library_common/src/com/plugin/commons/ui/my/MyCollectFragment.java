package com.plugin.commons.ui.my;

import java.util.List;

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
import com.plugin.commons.AppException;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.MyCollectListAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.ContentBean;
import com.plugin.commons.model.MyCollectInfoModel;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.ui.askgov.AskDetailActivity;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.zq.types.StBaseType;

/**
 * @author zhang
 * 右边我的-我的收藏
 */
public class MyCollectFragment extends BaseFragment {
	public DingLog log = new DingLog(MyCollectFragment.class);
	private static final String TAG = "MyCollectFragment";
	private MyCollectInfoModel dataList=new MyCollectInfoModel();
	List<ContentBean> colslist;
	private ZhKdBaseAdapter<ContentBean> mAdapter;
	
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
	}
	
	private void initViews(View view) {
		lv_news = (PullToRefreshListView) view.findViewById(R.id.lv_news);
		mAdapter = new MyCollectListAdapter(mActivity,dataList.getColslist());
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
				if(colslist.get(arg2-1).getType()==CacheModel.CACHE_ASKBAOLIAO){
				}
				if(colslist.get(arg2-1).getType()==CacheModel.CACHE_ASKGOV){
					Intent intent = new Intent(mActivity,AskDetailActivity.class);
					intent.putExtra(CoreContants.PARAMS_MSG,dataList.getAskMsgById(dataList.getColslist().get(arg2-1).getId()));
					mActivity.startActivity(intent);
				}
				if(colslist.get(arg2-1).getType()==CacheModel.CACHE_ASKNEWS){
					NewsInfoModel item = dataList.getNewsById(dataList.getColslist().get(arg2-1).getId());
					NewsTypeModel type = new NewsTypeModel();
					type.setId(item.getArttype());
					type.setType(item.getNewtype());
					type.setHassub("0");
					ComUtil.goNewsDetail(mActivity, item, type);
				}
				
			}
			
		});
	
	}
	
	private void initDisplay() {
		doRefresh(true);
	}
	
	private void doRefresh(final boolean isInit)
	{
		if(isInit){
//			DialogUtil.showProgressDialog(mActivity);
		}
		// Do work to refresh the list here.
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				RspResultModel rsp = null;
				try {
					rsp = ComApp.getInstance().getBlService().colectAll();
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				if(isInit){
					DialogUtil.closeProgress(mActivity);
				}
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(mActivity, result)){
					dataList=result.getColts();
					colslist = result.getColts().getColslist();
					log.info("newList:"+colslist.size());
					mAdapter.setDataList(colslist);
					lv_news.setAdapter(mAdapter);
					log.info("newList:"+colslist.size());
					mAdapter.notifyDataSetChanged();
				}
				if(!isInit){
					lv_news.onRefreshComplete();
				}
				ComUtil.showListNone(getView(), "暂无数据", colslist);
			}
			
		});
		
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}
	public void onFrageSelect(int idnex){
		
	}

}
