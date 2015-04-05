package com.plugin.commons.view;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.api.ComAppApi;
import com.plugin.commons.helper.FuncUtil;



/**
 * 
 * 备注:
 * 此LJWebView继承自Relativielayout,所以会导致丢失一个WebView的属性，如果大家
 * 在项目中需要用到，可是此类中加入，然后调用即可，可参考
 * 	public void setClickable(boolean value){
		mWebView.setClickable(value);
	}
 * 这个方法的定义和调用
 * 
 */
public class ZqCircleView extends RelativeLayout{
	
	public static int Circle = 0x01;
	public static int Horizontal = 0x02;
	
	private Context context;
	
	private WebView mWebView = null;  //
	private ProgressBar progressBar = null;  //水平进度条
	private RelativeLayout progressBar_circle = null;  //包含圆形进度条的布局
	private int barHeight = 8;  //水平进度条的高
	private boolean isAdd = false;  //判断是否已经加入进度条
	private int progressStyle = Circle;  //进度条样式,Circle表示为圆形，Horizontal表示为水平
	

	
	public ZqCircleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	public ZqCircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	public ZqCircleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}
	
	private void init(){
		mWebView = new WebView(context);
		this.addView(mWebView, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		mWebView.getSettings().setLoadWithOverviewMode(true); 
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setDomStorageEnabled(true); 
		mWebView.setWebChromeClient(new WebChromeClient(){

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				if(newProgress == 100){
					if(progressStyle == Horizontal){
						progressBar.setVisibility(View.GONE);
					}else{
						if(progressBar_circle!=null){
							progressBar_circle.setVisibility(View.GONE);
						}
					}
				}else{
					if(!isAdd){
						if(progressStyle == Horizontal){
							progressBar = (ProgressBar) LayoutInflater.from(context).inflate(R.layout.progress_horizontal, null);
							progressBar.setMax(100);
							progressBar.setProgress(0);
							ZqCircleView.this.addView(progressBar, LayoutParams.FILL_PARENT, barHeight);
						}else{
							progressBar_circle = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.progress_circle, null);
							ZqCircleView.this.addView(progressBar_circle, LayoutParams.FILL_PARENT,  LayoutParams.FILL_PARENT);
						}
						isAdd = true;
					}
					if(progressBar_circle!=null){
						if(progressStyle == Horizontal){
							progressBar.setVisibility(View.VISIBLE);
							progressBar.setProgress(newProgress);
						}else{
							progressBar_circle.setVisibility(View.VISIBLE);
						}
					}
					
				}
				
			}
		});
	}
	
	public void setBarHeight(int height){
		barHeight = height;
	}
	
	public void setProgressStyle(int style){
		progressStyle = style;
	}
	
	public void setClickable(boolean value){
		mWebView.setClickable(value);
	}
	
	public void setUseWideViewPort(boolean value){
		mWebView.getSettings().setUseWideViewPort(value);
	}
	
	public void setSupportZoom(boolean value){
		mWebView.getSettings().setSupportZoom(value);
	}
	
	public void setBuiltInZoomControls(boolean value){
		mWebView.getSettings().setBuiltInZoomControls(value);
	}
	
	public void setJavaScriptEnabled(boolean value){
		mWebView.getSettings().setJavaScriptEnabled(value);
	}
	
	public void setCacheMode(int value){
		mWebView.getSettings().setCacheMode(value);
	}

	public void setWebViewClient(WebViewClient value){
		mWebView.setWebViewClient(value);
	}
	
	public void loadUrl(String url){
		
		if(url.equals("http://218.24.200.244/")){
			mWebView.getSettings().setDefaultTextEncodingName("GBK");
		} 
		mWebView.loadUrl(url);
		 
	}

	public WebView getmWebView() {
		return mWebView;
	}

	public void setmWebView(WebView mWebView) {
		this.mWebView = mWebView;
	}
}
