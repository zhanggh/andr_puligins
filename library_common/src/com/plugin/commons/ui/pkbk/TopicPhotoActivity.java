package com.plugin.commons.ui.pkbk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.ComPhotListAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.CommonModel;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.PhotoAndVideoModel;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.service.SituoAjaxCallBackImp;
import com.plugin.commons.ui.base.BaseActivity;

public class TopicPhotoActivity extends BaseActivity{

	List<NewsInfoModel> dataList=  new ArrayList<NewsInfoModel>();
	ImageView im_tkphoto;
	NewsTypeModel mNewType;
	NewsInfoModel mNews;
	NewsService newsSvc;
	private PullToRefreshListView lv_news;
	private ZhKdBaseAdapter<NewsInfoModel> mAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_list);
		
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_NEWS)) {
        	mNews = (NewsInfoModel) getIntent().getExtras().getSerializable(CoreContants.PARAMS_NEWS);
		}
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_TYPE)) {
			mNewType = (NewsTypeModel) getIntent().getExtras().getSerializable(CoreContants.PARAMS_TYPE);
		}
		newsSvc = new NewsServiceImpl();
		ComUtil.customeTitle(this, mNews.getTitle(),true);
		initViews();
		initDisplay();
		
	}
	
	private void initViews()
	{
		
		im_tkphoto=(ImageView) this.findViewById(R.id.im_tkphoto);
		im_tkphoto.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.paike_btn_selector));
		lv_news = (PullToRefreshListView) this.findViewById(R.id.lv_news);
		mAdapter = new ComPhotListAdapter(this, dataList);
		lv_news.setAdapter(mAdapter);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(TopicPhotoActivity.this, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				doRefresh(false,true);
			}
		});
		
		lv_news.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				doRefresh(false,false);
//				Toast.makeText(TopicPhotoActivity.this, "已无更多加载", Toast.LENGTH_SHORT).show();
			}
		});
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				XHSDKUtil.addXHBehavior(TopicPhotoActivity.this, mNewType.getParentid()+"_"+dataList.get(arg2-1).getId(), XHConstants.XHTOPIC_ARTICAL_CLICK, dataList.get(arg2-1).getId());
				ComUtil.goNewsDetail(TopicPhotoActivity.this, dataList.get(arg2-1), mNewType);
			}
			
		});
		
		im_tkphoto.setOnClickListener(new OnClickListener(
				) {
			
			@Override
			public void onClick(View v) {
				CommonModel cmmodel=new CommonModel();
				cmmodel.setType(mNewType.getParentid());
				cmmodel.setSubtype(mNewType.getId());
				cmmodel.setThemeid(mNews.getId());
				Intent intent=new Intent(TopicPhotoActivity.this, UploadPhotoActivity.class);
				intent.putExtra(CoreContants.PARAMS_MSG, cmmodel);
				TopicPhotoActivity.this.startActivity(intent); 
			}
		});
	}
	
	
	
	private void initDisplay(){
		doRefresh(true,true);
	}
	
	
	private void doRefresh(final boolean isInit,final boolean isRefresh)
	{
		ComUtil.showListNone(this.getEmptyView(), "努力加载中...", dataList);
		//异步
		sCallBack=new SituoAjaxCallBackImp<NewsInfoModel,NewsService>(findViewById(R.id.ll_root),pageStart,this.dataList,isInit, isRefresh,
				this, lv_news, mAdapter,CoreContants.REQUEST_COM_PHOTOS,newsSvc) {//, null,newService,null

			@Override
			public void afterService(Map<String,NewsInfoModel> response ,List<NewsInfoModel> dataList,int pageStart) {
				TopicPhotoActivity.this.pageStart=pageStart;
				TopicPhotoActivity.this.dataList=dataList;
			}
		};
		sCallBack.setmNews(this.mNews);
		sCallBack.setmNewType(mNewType);
		// Do work to refresh the list here.
		SituoHttpAjax.ajax(sCallBack);
	}

	public NewsTypeModel getmNewType() {
		return mNewType;
	}

	public void setmNewType(NewsTypeModel mNewType) {
		this.mNewType = mNewType;
	}
}
