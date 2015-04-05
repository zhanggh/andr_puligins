package com.plugin.commons.ui.base;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.listener.ComBroatCast;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.CacheDataService;
import com.plugin.commons.service.DetailBarManager;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.ui.news.NewsCommentsListActivity;
import com.plugin.commons.view.ZqWebView;
import com.zq.types.StBaseType;


@SuppressLint("NewApi")
public class ActivityWebExt extends BaseActivity{
	private ZqWebView mLJWebView = null;
	NewsInfoModel mNews;
	Button btn_right;
	NewsService newService;
	NewsTypeModel mNewType;
	String mAttype = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview_ext);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_NEWS)) {
			mNews =(NewsInfoModel)getIntent().getExtras().get(CoreContants.PARAMS_NEWS);
		}
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_TYPE)) {
			mNewType =(NewsTypeModel)getIntent().getExtras().get(CoreContants.PARAMS_TYPE);
		}
		if(ComApp.APP_NAME.equals(CoreContants.APP_LNZX)){
			ComUtil.customeTitle(this,mNewType.getName(),true);
		}else{
			ComUtil.customeTitle(this,FuncUtil.getPexfStr(mNews.getTitle(), 12, "..."),true);
		}
		//回调
		this.receiver=new ComBroatCast(mNews,this,CoreContants.ACTIVITY_RETTRUN);
		newService = new NewsServiceImpl();
		mLJWebView = (ZqWebView) findViewById(R.id.web);
		mLJWebView.setBarHeight(3);
		mLJWebView.setClickable(true);
		mLJWebView.setUseWideViewPort(true);
		mLJWebView.setSupportZoom(false);
		mLJWebView.setBuiltInZoomControls(false);
		mLJWebView.setJavaScriptEnabled(true);
		mLJWebView.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		mLJWebView.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		if(mNews!=null){
			mLJWebView.loadUrl(mNews.getUrl());
		}
		refreshUI();
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPause(this);//新华sdk
	}
	
	private void refreshUI(){
		btn_right = (Button)this.findViewById(R.id.btn_title_right);
		if(mNewType!=null){
			mAttype = FuncUtil.isEmpty(mNewType.getParentid())? mNewType.getId():mNewType.getParentid();
			if(FuncUtil.isEmpty(mNews.getArttype())){
				mNews.setArttype(mAttype);
			}
		}
		else{
			mAttype = mNews.getArttype();
		}
		
		NewsTypeModel ntype=newService.getNewsType(mAttype);
		if(ntype!=null){
			if(CoreContants.MENU_CODE_CHINANEWS.equals(mAttype)){
				this.findViewById(R.id.rl_commentbar).setVisibility(View.GONE);
			}else if(CoreContants.NEWS_COMMENT.equals(newService.getNewsType(mAttype).getOpenreply())){
				ComUtil.addComment(ActivityWebExt.this,mNews,newService,mAttype,null,CacheModel.CACHE_ASKNEWS_EXT);
			}else{
//				ComUtil.addComment(ActivityWebExt.this,mNews,newService,mAttype,null,CacheModel.CACHE_ASKNEWS_EXT);
				this.findViewById(R.id.rl_commentbar).setVisibility(View.GONE);
			}
			btn_right.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_colection_selector));
			btn_right.setVisibility(View.VISIBLE);
			CacheModel cm = CacheDataService.getAcction(CacheModel.CACHE_ASKNEWS_EXT,mNews.getArttype()+mNews.getId());
			btn_right.setSelected(cm!=null);
			btn_right.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {//只有收藏，没有进入评论列表
					CacheModel cm = new CacheModel();
					cm.type = CacheModel.CACHE_ASKNEWS_EXT;
					cm.id = mNews.getArttype()+mNews.getId();
					cm.msg = mNews;
					ComUtil.doCollection(cm,ActivityWebExt.this,mNews,mAttype,btn_right);
				}
			});
		}else{
			btn_right.setVisibility(View.INVISIBLE);
			this.findViewById(R.id.rl_commentbar).setVisibility(View.GONE);
		}
	}
	
	 
	@Override
	protected void onResume() {
		 super.onResume();
		if(this.receiver!=null){
	    	IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction(this.receiver.getAcAction());
	        registerReceiver(this.receiver, intentFilter);
	    }
		AnalyticsAgent.onResume(this);//新华sdk
		
	}
}
