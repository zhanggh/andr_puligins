package com.plugin.commons.ui.number;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
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
import com.plugin.commons.adapter.NumberListAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.NumberModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.DisClsTestService;
import com.zq.types.StBaseType;
 

public class NumberListActivity  extends Activity{
	DingLog log = new DingLog(NumberListActivity.class);
   
	String titile="号码通";

	PullToRefreshListView numPullList;

	private NumberListAdapter mAdapter;
	List<NumberModel> numList = new ArrayList<NumberModel>();
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_list);
		titile = this.getIntent().getStringExtra(CoreContants.TITLE_PARAM);
		ComUtil.customeTitle(this, titile,true);
		
		initViews();
		initDisplay();
	}
	
	
	private void initViews() {
		numPullList = (PullToRefreshListView) this.findViewById(R.id.lv_numbers);
		mAdapter = new NumberListAdapter(this,numList);
		numPullList.setAdapter(mAdapter);
		numPullList.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(ComApp.getInstance(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				doRefresh();
			}
		});
		
		numPullList.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				Toast.makeText(NumberListActivity.this, "已无更多加载", Toast.LENGTH_SHORT).show();
			}
		});
		numPullList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(NumberListActivity.this,NumberDetailActivity.class);
				intent.putExtra(NumberDetailActivity.PARAMS_MSG,numList.get(arg2-1));
				NumberListActivity.this.startActivity(intent);
				//startActivity(intent);
			}
			
		});
	
	}
	
	private void initDisplay() {
 
		DialogUtil.showProgressDialog(NumberListActivity.this);
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){

			@Override
			public StBaseType requestApi() {
				RspResultModel rsp = DisClsTestService.getNum();
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(NumberListActivity.this);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(NumberListActivity.this, result)){
					numList = result.getNumlist();
					log.info("numList:"+numList.size());
//					MyBaoliaoTabFragment.this.start=baoliaoList.size();
					mAdapter.setDataList(numList);
					numPullList.setAdapter(mAdapter);
					log.info("numList:"+numList.size());
					mAdapter.notifyDataSetChanged();
				}
				ComUtil.showListNone(NumberListActivity.this.findViewById(R.layout.activity_number_list), "暂无数据", numList);
				
			}
			
		});
	}
	
	private void doRefresh()
	{
		// Do work to refresh the list here.
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				RspResultModel rsp =DisClsTestService.getNum();
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(NumberListActivity.this, result)){
					if(result.getNumlist().size()>0){
						numList = result.getNumlist();
						mAdapter.setDataList(numList);
					}
					log.info("numList:"+numList.size());
					mAdapter.notifyDataSetChanged();
				}
				ComUtil.showListNone(NumberListActivity.this.findViewById(R.layout.activity_number_list), "暂无数据", numList);
				numPullList.onRefreshComplete();
			}
		});
	}
	
	/**
	 * 打电话
	 */
//	public void toCall(View view){
////		DialogUtil.showToast(this, "功能开发中");
//		View rowView =mAdapter.getView(1, null, null);
//		TextView et_phonenumber = (TextView)rowView.findViewById(R.id.tv_title);  
//        String number = et_phonenumber.getText().toString();  
//		Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));  
//		startActivity(intent);  
//	}
}
