package com.plugin.commons.ui.number;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.NumberTypeAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.NumberType;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.CacheDataService;
import com.plugin.commons.service.DisClsTestService;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.service.NumberService;
import com.plugin.commons.service.NumberServiceImpl;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.zq.types.StBaseType;

public class NumberTypeFragment  extends BaseFragment{
		public DingLog log = new DingLog(NumberTypeFragment.class);
		private static final String TAG = "NumberTypeFragment";
		private List<List<NumberType>> numList = new ArrayList<List<NumberType>>();
		private List<NumberType> numbertypes= new ArrayList<NumberType>();
		private ZhKdBaseAdapter<List<NumberType>> mAdapter;			 
		
		private EditText edit;
		private ImageView img;
		NumberService numberService;
		 
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			log.info(getFragmentName(), " onCreateView()");
			View view = inflater.inflate(R.layout.fragment_numbersense, container, false);
			return view;
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			log.info("onActivityCreated");
			initDisplay();
		}
		
		protected void initViews(View view) {
			numberService=new NumberServiceImpl();
			lv_news = (PullToRefreshListView) view.findViewById(R.id.lv_numbertype);
			edit=(EditText) view.findViewById(R.id.et_numtype);
			img=(ImageView) view.findViewById(R.id.img_numtype);
			
			//通过适配器装载并展示数据
			if(mAdapter==null){
//				mAdapter = new NumberTypeAdapter(mActivity,numList);
				lv_news.setAdapter(mAdapter);
			}
			
			lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					doRefresh(false);
				}
			});	
		
			img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {	
					NumberType numType=new NumberType();
					numType.setId(-1);
					
					String code = edit.getText().toString();  
					log.info("搜索关键字"+code);
					if(FuncUtil.isEmpty(code)){
						DialogUtil.showToast(mActivity,"请输入搜索关键字");
						return;
					}
					Intent intent=new Intent(mActivity ,NumberListActivity.class);
					intent.putExtra(CoreContants.PARAMS_MSG,numType);
					intent.putExtra(CoreContants.PARAM_CODE, code);
					mActivity.startActivity(intent);
				}
			}); 
			
		     
		}
		
		protected void initDisplay() {
			//走异步请求
			doRefresh(true);
		}
		
		private void refreshList(){
			if(!FuncUtil.isEmpty(numbertypes)){
				//组装数据
				List<NumberType> temList = new ArrayList<NumberType>();
				int index=0;
				boolean flag=false;//分组标识
				for(NumberType num:numbertypes){
					temList.add(num);
					index++;
					if(index%12==0){//8个元素为一组
						numList.add(temList);
						temList=new ArrayList<NumberType>();
						flag=true;
					}
				}
				if(!flag){
					numList.add(temList);
				}
				mAdapter.setDataList(numList);
				mAdapter.notifyDataSetChanged();
				lv_news.onRefreshComplete();
				log.info("是否为空:"+FuncUtil.isEmpty(numList));
			}else{
				ComUtil.showListNone(getView(), "暂无数据", numbertypes);
			}
		}
		
		public void doRefresh(final boolean cache)
		{
			ComUtil.showListNone(this.getView(), "努力加载中...", numbertypes);
			SituoHttpAjax.ajax(new SituoAjaxCallBack(){
				@Override
				public StBaseType requestApi() {
					RspResultModel rsp  = numberService.getNumTypeList(cache);
					return rsp;
				}

				@Override
				public void callBack(StBaseType baseType) {
					DialogUtil.closeProgress(mActivity);
					RspResultModel result = (RspResultModel)baseType;
					if(ComUtil.checkRsp(mActivity, result)){
						numbertypes = result.getNumbertypes();
						refreshList();
						CacheDataService.setNeedLoad(CoreContants.CACHE_NUMBER_TYPES);
					}
					else{
						lv_news.onRefreshComplete();
					}
				
				}
			});
		}

		@Override
		public String getFragmentName() {
			return TAG;
		}

		@Override
		public void onFrageSelect(int index) {
			
		}
	}
