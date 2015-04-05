package com.plugin.commons.ui.number;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.NumberListAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.model.NumberModel;
import com.plugin.commons.model.NumberType;
import com.plugin.commons.service.NumberService;
import com.plugin.commons.service.NumberServiceImpl;
import com.plugin.commons.service.SituoAjaxCallBackImp;
import com.plugin.commons.ui.base.BaseActivity;
 

public class NumberListActivity  extends BaseActivity{
	NumberType mMsg;
	private ImageView searchImg;
	private EditText et_numInfo;
	DingLog log = new DingLog(NumberListActivity.class);
	PullToRefreshListView lv_news;	
	private  ZhKdBaseAdapter<NumberModel> mAdapter;
	private List<NumberModel> dataList = new ArrayList<NumberModel>();
	NumberService numberService;
	private String keyWord;
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_list);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_MSG)) {
			mMsg =(NumberType)getIntent().getExtras().get(CoreContants.PARAMS_MSG);
		}	
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAM_CODE)) {
			keyWord =(String) getIntent().getExtras().get(CoreContants.PARAM_CODE);
		}
		ComUtil.customeTitle(this, mMsg.getName(),true);
		numberService =new NumberServiceImpl();
		initViews();
		initDisplay();
	}
	
	private void initViews(){
		
		searchImg=(ImageView) this.findViewById(R.id.img_numInfo);
		et_numInfo= (EditText) this.findViewById(R.id.et_numInfo);
		lv_news= (PullToRefreshListView) this.findViewById(R.id.lv_numbers);
		mAdapter = new NumberListAdapter(this,dataList);				
		lv_news.setAdapter(mAdapter);
		
		et_numInfo.setFocusable(false);
		et_numInfo.setFocusableInTouchMode(false);
		
//		ComUtil.hideKeyBoard(this,et_numInfo);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(NumberListActivity.this, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				doRefresh(false,true,"");
			}
		});
		
		lv_news.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
			@Override
			public void onLastItemVisible() {
				doRefresh(false,false,"");
			}
		});
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 Intent intent=new Intent(NumberListActivity.this, NumberDetailActivity.class);
				 intent.putExtra(CoreContants.PARAMS_MSG,dataList.get(arg2-1));
				 NumberListActivity.this.startActivity(intent);
			}			
		});	
		
		searchImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				NumberType numType=new NumberType();
				numType.setId(-1);
				
				String code = et_numInfo.getText().toString();  
				log.info("搜索关键字"+code);
				if(FuncUtil.isEmpty(code)){
					DialogUtil.showToast(NumberListActivity.this,"请输入搜索关键字");
					return;
				}
				doRefresh(false,true,code);
			}
		}); 
		
		
		et_numInfo.setOnEditorActionListener(new TextView.OnEditorActionListener() { 

			public boolean onEditorAction(TextView v, int actionId,KeyEvent event)  {
				if (actionId==EditorInfo.IME_ACTION_SEND ||actionId==EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)){
					// 先隐藏键盘
					ComUtil.hideKeyBoard(NumberListActivity.this,et_numInfo);
					String code = et_numInfo.getText().toString();  
					log.info("搜索关键字"+code);
					if(FuncUtil.isEmpty(code)){
						DialogUtil.showToast(NumberListActivity.this,"请输入搜索关键字");
						return false;
					}
					doRefresh(false,true,code);
				}
				return true;
			}
		});
	}
	
	private void initDisplay(){		
		
		//走异步请求		
		if(!FuncUtil.isEmpty(keyWord)){
			DialogUtil.showProgressDialog(this, "查询中...");
			doRefresh(false,true,keyWord);
		}else{
			doRefresh(false,true,keyWord);
		}
		
	}
	
	private void doRefresh(final boolean isInit,final boolean isRefresh,final String keyWord)
	{
		ComUtil.showListNone(this.getEmptyView(), "努力加载中...", dataList);
		//异步
		sCallBack=new SituoAjaxCallBackImp<NumberModel,NumberService>(this.getEmptyView(),pageStart,this.dataList,isInit, isRefresh,
				this, lv_news, mAdapter,CoreContants.REQUEST_NUMBER,numberService) {
			@Override
			public void afterService(Map<String,NumberModel> response ,List<NumberModel> dataList,int pageStart) {
				NumberListActivity.this.pageStart=pageStart;
				NumberListActivity.this.dataList=dataList;
				et_numInfo.setFocusable(true);
				et_numInfo.setFocusableInTouchMode(true);
				et_numInfo.requestFocus();
				et_numInfo.requestFocusFromTouch();
				if(!FuncUtil.isEmpty(keyWord)){
					DialogUtil.closeProgress(NumberListActivity.this);
				}
			}
			
		};
		sCallBack.setKeyWord(keyWord);
		SituoHttpAjax.ajax(sCallBack);
	}
}