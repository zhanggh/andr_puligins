package com.plugin.commons.ui.pkbk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.PhotoDTListAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.PhotoAndVideoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.DetailBarManager;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.service.SituoAjaxCallBackImp;
import com.plugin.commons.ui.base.BaseActivity;
import com.plugin.commons.ui.news.NewsCommentsListActivity;
import com.zq.types.StBaseType;

public class VideoDetailActivity extends BaseActivity {

	private ZhKdBaseAdapter<PhotoAndVideoModel> mAdapter;
	private PullToRefreshListView lv_news;
	List<PhotoAndVideoModel> dataList=  new ArrayList<PhotoAndVideoModel>();
	NewsService newService;
	private Button btn_right;
	private TextView tv_right;
	private String videoUrl;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pt_detail);
		videoUrl=(String) this.getIntent().getExtras().get(CoreContants.PARAMS_MSG);
		ComUtil.customeTitle(this,"播客详情",true);
		newService =new NewsServiceImpl();
		initViews();
		initDisplay();
	}
	
	
	private void initDisplay(){
		lv_news.setRefreshing(true);
		doRefresh(true,true);
	}
	
	private void initViews()
	{
		btn_right = (Button)this.findViewById(R.id.btn_title_right);
		btn_right.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_comment_selector));
		btn_right.setVisibility(View.VISIBLE);
		tv_right = (TextView)this.findViewById(R.id.tv_right);
		tv_right.setVisibility(View.VISIBLE);
		tv_right.setText("11");
		addComment();
		lv_news = (PullToRefreshListView) this.findViewById(R.id.lv_photos);
		mAdapter = new PhotoDTListAdapter(this, dataList);
		lv_news.setAdapter(mAdapter);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(VideoDetailActivity.this, System.currentTimeMillis(),
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
//				Toast.makeText(PhotoDetailActivity.this, "已无更多加载", Toast.LENGTH_SHORT).show();
			}
		});
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ComUtil.playVideoFromUrl(VideoDetailActivity.this,videoUrl);
			}
			
		});
		 
		btn_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(VideoDetailActivity.this,NewsCommentsListActivity.class);
				intent.putExtra(CoreContants.PARAMS_MSG,"0");
//				intent.putExtra(CoreContants.PARAMS_MSG_ID,mNews.getId());
//				intent.putExtra(CoreContants.PARAMS_TYPE,mAttype);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * 评论
	 */
	private void addComment(){
		CacheModel cm = new CacheModel();
		cm.type = CacheModel.CACHE_ASKNEWS;
//		cm.id = mNews.getId()+"";
//		cm.msg = mNews;
		DetailBarManager detailBarMng = new DetailBarManager(this,this.findViewById(R.id.rl_commentbar),cm);
		detailBarMng.setCommtentCB(new DetailBarManager.OnCommentCallBack() {
			
			@Override
			public void onCommentCallBack(Dialog dlg, final String comment) {
				
				DialogUtil.showProgressDialog(VideoDetailActivity.this);
				SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {

					@Override
					public StBaseType requestApi() {
						RspResultModel rsp =null;// newService.pubNewComment(mNews.getId(), comment,mAttype);
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(VideoDetailActivity.this);
						RspResultModel result = (RspResultModel)baseType;
						if(ComUtil.checkRsp(VideoDetailActivity.this, result)){
							if("0".equals(result.getRetcode())){
								DialogUtil.showToast(VideoDetailActivity.this, "评论成功");
//								int replyCount=Integer.parseInt(mNews.getReplycount())+1;
//								tv_right.setText(String.valueOf(replyCount));
								//用户行为采集
//								XHSDKUtil.addXHBehavior(PhotoDetailActivity.this,mAttype+"_"+mNews.getId(), XHConstants.XHTOPIC_COMMENT, mNews.getId());
							}else{
								DialogUtil.showToast(VideoDetailActivity.this, "评论失败");
							}
						}
					}
					
				});
			}
		});
		detailBarMng.refreshUI();
	}
	
	
	private void doRefresh(final boolean isInit,final boolean isRefresh)
	{
		//异步
		sCallBack=new SituoAjaxCallBackImp<PhotoAndVideoModel,NewsService>(findViewById(R.id.ll_root),pageStart,this.dataList,isInit, isRefresh,
				this, lv_news, mAdapter,CoreContants.REQUEST_PAIKE_DETAIL,newService) {//, null,newService,null

			@Override
			public void afterService(Map<String,PhotoAndVideoModel> response ,List<PhotoAndVideoModel> dataList,int pageStart) {
				VideoDetailActivity.this.pageStart=pageStart;
				VideoDetailActivity.this.dataList=dataList;
			}
		};
		// Do work to refresh the list here.
		SituoHttpAjax.ajax(sCallBack);
	}
}
