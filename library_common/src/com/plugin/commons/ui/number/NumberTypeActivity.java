package com.plugin.commons.ui.number;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
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
import com.plugin.commons.service.NumberService;
import com.plugin.commons.service.NumberServiceImpl;
import com.plugin.commons.ui.base.BaseActivity;
import com.zq.types.StBaseType;

public class NumberTypeActivity  extends BaseActivity{
		public DingLog log = new DingLog(NumberTypeActivity.class);
		private List<NumberType> numbertypes= new ArrayList<NumberType>();
		private List<NumberType> temtypes= new ArrayList<NumberType>();
		private ZhKdBaseAdapter<NumberType> mAdapter;			 
		
		private EditText edit;
		private ImageView img;
		NumberService numberService;
		private String title;
		private boolean noCache;
		 
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.fragment_numbersense);
			title = (String) getIntent().getCharSequenceExtra(CoreContants.PARAMS_TITLE);
			noCache = (boolean) getIntent().getBooleanExtra(CoreContants.PARAMS_MSG_ID, false);
			if(!FuncUtil.isEmpty(title)){
				ComUtil.customeTitle(this,title,true);
			}else{
				ComUtil.customeTitle(this,"",true);
			}
			
			initViews();
			initDisplay();
		}
		 
		
		protected void initViews() {
			numberService=new NumberServiceImpl();
			lv_news = (PullToRefreshListView) this.findViewById(R.id.lv_numbertype);
			edit=(EditText) this.findViewById(R.id.et_numtype);
			img=(ImageView) this.findViewById(R.id.img_numtype);
			
			//通过适配器装载并展示数据
			if(mAdapter==null){
				mAdapter = new NumberTypeAdapter(this,numbertypes);
				lv_news.setAdapter(mAdapter);
			}
			
			lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					String label = DateUtils.formatDateTime(NumberTypeActivity.this, System.currentTimeMillis(),
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
						DialogUtil.showToast(NumberTypeActivity.this,"请输入搜索关键字");
						return;
					}
					Intent intent=new Intent(NumberTypeActivity.this ,NumberListActivity.class);
					intent.putExtra(CoreContants.PARAMS_MSG,numType);
					intent.putExtra(CoreContants.PARAM_CODE, code);
					NumberTypeActivity.this.startActivity(intent);
				}
			}); 
		 
			edit.setOnEditorActionListener(new TextView.OnEditorActionListener() { 

				public boolean onEditorAction(TextView v, int actionId,KeyEvent event)  { 
					if (actionId==EditorInfo.IME_ACTION_SEND ||actionId==EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)){                
						// 先隐藏键盘
						ComUtil.hideKeyBoard(NumberTypeActivity.this,edit);
						NumberType numType=new NumberType();
						numType.setId(-1);
						String code = edit.getText().toString();  
						log.info("搜索关键字"+code);
						if(FuncUtil.isEmpty(code)){
							DialogUtil.showToast(NumberTypeActivity.this,"请输入搜索关键字");
							return false;
						}
						Intent intent=new Intent(NumberTypeActivity.this ,NumberListActivity.class);
						intent.putExtra(CoreContants.PARAMS_MSG,numType);
						intent.putExtra(CoreContants.PARAM_CODE, code);
						NumberTypeActivity.this.startActivity(intent);           
					}               
					return true;           
				}       
			});

		     
		}
		
		protected void initDisplay() {
			DialogUtil.showProgressDialog(this, "搜索中...");
			//走异步请求
			doRefresh(!noCache);
		}
		
		private void refreshList(){
			if(!FuncUtil.isEmpty(numbertypes)){
				mAdapter.setDataList(numbertypes);
				mAdapter.notifyDataSetChanged();
				lv_news.onRefreshComplete();
				DialogUtil.closeProgress(this);
				log.info("是否为空:"+FuncUtil.isEmpty(numbertypes));
			}else{
				ComUtil.showListNone(NumberTypeActivity.this.getEmptyView(), "暂无数据", numbertypes);
			}
		}
		
		public void doRefresh(final boolean cache)
		{
			ComUtil.showListNone(this.getEmptyView(), "努力加载中...", numbertypes);
			SituoHttpAjax.ajax(new SituoAjaxCallBack(){
				@Override
				public StBaseType requestApi() {
					RspResultModel rsp  = numberService.getNumTypeList(cache);
					return rsp;
				}

				@Override
				public void callBack(StBaseType baseType) {
					DialogUtil.closeProgress(NumberTypeActivity.this);
					RspResultModel result = (RspResultModel)baseType;
					if(ComUtil.checkRsp(NumberTypeActivity.this, result)){
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

	}
