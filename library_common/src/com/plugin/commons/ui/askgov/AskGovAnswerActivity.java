package com.plugin.commons.ui.askgov;

import java.util.ArrayList;
import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

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
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.AskHotListAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.model.CommentModel;
import com.plugin.commons.model.GovmentInfoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.AskGovService;
import com.plugin.commons.service.AskGovServiceImpl;
import com.zq.types.StBaseType;


public class AskGovAnswerActivity extends Activity{
	DingLog log = new DingLog(AskGovAnswerActivity.class);
	public static String PARAMS_GOV = "gov";
	GovmentInfoModel mGov;
	List<CommentModel> mComments;
	AskGovService askSvc;
	private Activity mActivity;
	private PullToRefreshListView lv_news;
	private List<AskMsgModel> dataList = new ArrayList<AskMsgModel>();
	private AskHotListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_gov_tab);
		ComUtil.customeTitle(this, "回答列表",true);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAMS_GOV)) {
			mGov =(GovmentInfoModel)getIntent().getExtras().get(PARAMS_GOV);
		}
		mActivity = this;
		askSvc = new AskGovServiceImpl();
		initViews();
		initDisplay();
		doRefresh(true);
	}
	
	
	private void initViews()
	{
		lv_news = (PullToRefreshListView)findViewById(R.id.lv_news);
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
		RspResultModel rsp  = askSvc.getOrgAnswerList(true, "0", "20",mGov.getId());
		if(rsp!=null&&rsp.getMsg_list()!=null){
			dataList = rsp.getMsg_list();
		}
		
		mAdapter.setDataList(dataList);
		lv_news.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
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
				RspResultModel rsp  = askSvc.getOrgAnswerList(false, "0", "20",mGov.getId());
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				log.info("返回...");
				DialogUtil.closeProgress(AskGovAnswerActivity.this);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(mActivity, result)){
					dataList = result.getMsg_list();
					mAdapter.setDataList(dataList);
				}
				mAdapter.notifyDataSetChanged();
				lv_news.onRefreshComplete();
			}
			
		});
		
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
