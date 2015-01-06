package com.plugin.commons.ui.askgov;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.plugin.R;
import com.plugin.commons.adapter.AskGovListAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.GovmentInfoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.AskGovService;
import com.plugin.commons.service.AskGovServiceImpl;
import com.zq.types.StBaseType;

public class AskGovListFragment extends Fragment {
	public DingLog log = new DingLog(AskGovListFragment.class);
	private Activity mActivity;
	private ListView lv_list;
	private List<GovmentInfoModel> dataList = new ArrayList<GovmentInfoModel>();
	private AskGovListAdapter mAdapter;
	private LinearLayout ll_box;
	AskGovService askSvc;
	boolean hasLoad = false;
	
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
		View view = inflater.inflate(R.layout.fragment_ask_gov_list, container, false);
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
		
	}
	
	@Override
	 public void onHiddenChanged(boolean hidden) {
	       super.onHiddenChanged(hidden);
	       if (!hidden&&!hasLoad) {//如果显示，并且没有加载过，则加载数据
	    	    initDisplay();
	   			request();
	   			hasLoad = true;
	       }
	}
	
	private void initViews(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);
		ll_box = (LinearLayout)view.findViewById(R.id.ll_box);
		
		mAdapter = new AskGovListAdapter(mActivity,dataList);
		lv_list.setAdapter(mAdapter);
		lv_list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mActivity,AskGovDetailActivity.class);
				intent.putExtra(AskGovDetailActivity.PARAMS_GOV,dataList.get(arg2));
				mActivity.startActivity(intent);
			}
			
		});
		
		ll_box.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(FuncUtil.isEmpty(dataList)){
					return ;
				}
				RspResultModel rsp = new RspResultModel();
				rsp.setRetcode("0");
				rsp.setOrg_list(dataList);
				Intent intent = new Intent(mActivity,AskGovSearchActivity.class);
				intent.putExtra(AskGovSearchActivity.PARAMS_GOV,rsp);
				mActivity.startActivity(intent);
			}
		});
	}
	
	private void initDisplay() {
		RspResultModel rsp = askSvc.getOrgList(true);
		if(rsp!=null&&rsp.getOrg_list()!=null){
			dataList = rsp.getOrg_list();
		}
		else{
			dataList = new ArrayList<GovmentInfoModel>();
		}
		mAdapter.setDataList(dataList);
		lv_list.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}
	
	private void request(){
		DialogUtil.showProgressDialog(mActivity);
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){

			@Override
			public StBaseType requestApi() {
				// TODO Auto-generated method stub
				RspResultModel rsp = askSvc.getOrgList(false);
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(mActivity);
				// TODO Auto-generated method stub
				RspResultModel rsp = (RspResultModel)baseType;
				if(ComUtil.checkRsp(mActivity, rsp)){
					dataList = rsp.getOrg_list();
					mAdapter.setDataList(dataList);
					lv_list.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
				}
			}
			
		});
	}
}
