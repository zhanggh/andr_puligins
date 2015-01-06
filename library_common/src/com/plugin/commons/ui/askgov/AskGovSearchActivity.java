package com.plugin.commons.ui.askgov;

import java.util.ArrayList;
import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.plugin.R;
import com.plugin.commons.adapter.AskGovListAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.model.GovmentInfoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.TestService;



public class AskGovSearchActivity extends Activity{
	public static String PARAMS_GOV = "gov";
	
	RspResultModel mData;
	private ListView lv_list;
	private List<GovmentInfoModel> dataList = new ArrayList<GovmentInfoModel>();
	private AskGovListAdapter mAdapter;
	private EditText et_name;
	private String mSearchString = "";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_askgov_searchlist);
		ComUtil.customeTitle(this, "机构搜索",true);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAMS_GOV)) {
			mData =(RspResultModel)getIntent().getExtras().get(PARAMS_GOV);
		}
		initViews();
		refreshUI();
	}
	
	
	private void initViews()
	{
		lv_list = (ListView)findViewById(R.id.lv_list);
		et_name = (EditText)findViewById(R.id.et_name);
	}
	
	private void refreshUI()
	{
		mAdapter = new AskGovListAdapter(AskGovSearchActivity.this,dataList);
		lv_list.setAdapter(mAdapter);
		lv_list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AskGovSearchActivity.this,AskGovDetailActivity.class);
				intent.putExtra(AskGovDetailActivity.PARAMS_GOV,dataList.get(arg2));
				AskGovSearchActivity.this.startActivity(intent);
			}
		});
		
		et_name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence text, int start, int before, int count) {
				mSearchString = text + "";
				mSearchString=mSearchString.trim();
				doSearch();

			}

			@Override
			public void beforeTextChanged(CharSequence text, int start, int count, int after) {
				// text 输入框中改变前的字符串信息
				// start 输入框中改变前的字符串的起始位置
				// count 输入框中改变前后的字符串改变数量一般为0
				// after 输入框中改变后的字符串与起始位置的偏移量

			}

			@Override
			public void afterTextChanged(Editable edit) {
				// edit 输入结束呈现在输入框中的信息

			}
		});
		
	}
	
	private void doSearch()
	{
		if(mData!=null&&mData.getOrg_list()!=null&&mData.getOrg_list().size()>0){
			dataList.clear();
			for(GovmentInfoModel gov:mData.getOrg_list()){
				if(gov.getName().contains(mSearchString)){
					dataList.add(gov);
				}
			}
			mAdapter.setDataList(dataList);
			lv_list.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
		}
	}
	
	private void request(){
		dataList = TestService.getGovmentList().getOrg_list();
		mAdapter.setDataList(dataList);
		lv_list.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}
	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onResume(this);//新华sdk
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPause(this);//新华sdk
	}

}
