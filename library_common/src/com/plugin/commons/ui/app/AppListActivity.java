package com.plugin.commons.ui.app;


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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.AppListAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.AppInfoModel;
import com.plugin.commons.model.AreaModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.ui.base.BaseActivity;
import com.plugin.commons.ui.news.NewsTabActivity;
import com.zq.types.StBaseType;

/**
 * @author zhang
 *	应用列表
 */
public class AppListActivity extends BaseActivity {
	private String title="";
	NewsService nsService;
	AreaModel area;
	List<AppInfoModel> appList;
	String type;
	ZhKdBaseAdapter<AppInfoModel> mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_pullrefresh);
		if(this.getIntent()!=null&&this.getIntent().getExtras().containsKey(CoreContants.PARAMS_TITLE)){
			title=this.getIntent().getExtras().getString(CoreContants.PARAMS_TITLE,"应用列表");
		}
		if(this.getIntent()!=null&&this.getIntent().getExtras().containsKey(CoreContants.PARAMS_MSG_ID)){
			type=this.getIntent().getExtras().getString(CoreContants.PARAMS_MSG_ID,CoreContants.REQUEST_APP_HOT);
		}
		if(this.getIntent()!=null&&this.getIntent().getExtras().containsKey(CoreContants.PARAMS_MSG)){
			area=(AreaModel) this.getIntent().getExtras().get(CoreContants.PARAMS_MSG);
		}else{
			area=new AreaModel();
		}
		ComUtil.customeTitle(this,title,true);
		initViews();
		initDisplay();
	}
	private void initViews() {
		nsService=new NewsServiceImpl();
		lv_news = (PullToRefreshListView) this.findViewById(R.id.lv_news);
		mAdapter=new AppListAdapter(this,appList);
		lv_news.setAdapter(mAdapter);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(ComApp.getInstance(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				doRefresh();
			}
		});
		
		lv_news.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				doRefresh();
//				Toast.makeText(SysNotifyActivity.this, "已无更多加载", Toast.LENGTH_SHORT).show();
			}
		});
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 Intent intent=new Intent(AppListActivity.this, AppDetailActivity.class);
				 intent.putExtra(CoreContants.PARAMS_MSG,appList.get(arg2-1));
				 intent.putExtra(CoreContants.PARAMS_TITLE,appList.get(arg2-1).getName());
				 AppListActivity.this.startActivity(intent);
			}
			
		});
	
	}
	
	private void initDisplay() {
		DialogUtil.showProgressDialog(AppListActivity.this, "加载中...");
		doRefresh();
	}
	
	private void doRefresh()
	{
		ComUtil.showListNone(AppListActivity.this.getEmptyView(), "加载中...",appList);
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				RspResultModel rsp=null;
				if(!FuncUtil.isEmpty(area.getId())){
					rsp=nsService.getAreaRecList(area.getId());
				}else if(CoreContants.REQUEST_APP_HOT.equals(type)){
					 rsp=nsService.getHotRecList();
				}else{
					 rsp=nsService.getHotRecList();
				}
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(AppListActivity.this);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(AppListActivity.this, result)){
					appList=result.getApp_list();
					mAdapter.setDataList(appList);
					mAdapter.notifyDataSetChanged();
				}
				ComUtil.showListNone(AppListActivity.this.getEmptyView(), "暂无数据",appList);
				lv_news.onRefreshComplete();
			}
			
		});
	}
	
}
