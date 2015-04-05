package com.plugin.commons.ui.base;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.plugin.R;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.view.ZqCircleView;


public class WebHasTitleActivity   extends BaseActivity{
	private ZqCircleView mLJWebView = null;
	private String targetUrl;
	private boolean showBar=true;
	RelativeLayout rl_bottom;
	public boolean isShowBar() {
		return showBar;
	}

	public void setShowBar(boolean showBar) {
		this.showBar = showBar;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_govweb);
		String title="开发中";
		title = (String) getIntent().getCharSequenceExtra(CoreContants.PARAMS_TITLE);
		
		this.showBar = this.getIntent().getBooleanExtra(CoreContants.PARAMS_TYPE, true);
		if(!FuncUtil.isEmpty(title)){
			ComUtil.customeTitle(this,title,true);
		}else{
			ComUtil.customeTitle(this,"开发中",true);
		}
		targetUrl=(String) this.getIntent().getCharSequenceExtra(CoreContants.PARAMS_MSG);
		
		mLJWebView = (ZqCircleView)this.findViewById(R.id.web);
		mLJWebView.setProgressStyle(ZqCircleView.Circle);
		mLJWebView.setBarHeight(8);
		mLJWebView.setClickable(true);
		mLJWebView.setUseWideViewPort(true);
		mLJWebView.setCacheMode(WebSettings.LOAD_NO_CACHE);		
		mLJWebView.setWebViewClient(new WebViewClient() {
			//重写此方法，浏览器内部跳转
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				System.out.println("跳的URL =" + url);
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
			}
			
			
		});
		mLJWebView.loadUrl(targetUrl);
		 
		initBottom();
	}
	
	private void initBottom()
	{
		rl_bottom=(RelativeLayout) this.findViewById(R.id.rl_bottom);
		if(!showBar){
			rl_bottom.setVisibility(View.GONE);
		}else{
			rl_bottom.setVisibility(View.VISIBLE);
		}
		this.findViewById(R.id.btn_backweb).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mLJWebView.getmWebView().goBack();
			}
		});
		this.findViewById(R.id.btn_goweb).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mLJWebView.getmWebView().goForward();
			}
		});
		this.findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mLJWebView.getmWebView().reload();
			}
		});
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
}
