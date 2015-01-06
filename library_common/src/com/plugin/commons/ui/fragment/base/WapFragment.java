package com.plugin.commons.ui.fragment.base;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.plugin.R;
import com.plugin.commons.view.ZqCircleView;

public class WapFragment extends BaseFragment {
	
	public WapFragment(){}
	private ZqCircleView mLJWebView = null;
	private String url = "";
	RelativeLayout rl_bottom;
	private boolean showBar=true;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_chinawap, container, false);
        return rootView;
    }
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initDisplay();
	}
	
	private void initViews(View view){
		mLJWebView = (ZqCircleView) view.findViewById(R.id.web);
		rl_bottom=(RelativeLayout) view.findViewById(R.id.rl_bottom);
		initBottom(view);
	}
	private void initDisplay(){
		
		if(!showBar){
			rl_bottom.setVisibility(View.GONE);
		}
		
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
		});
		
		mLJWebView.loadUrl(url);
	}
	
	
	
	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onPageStart(getActivity(), "WapFragment");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPageEnd(getActivity(), "WapFragment");
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String getFragmentName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onFrageSelect(int index) {
		// TODO Auto-generated method stub
		
	}
	
	private void initBottom(View view)
	{
		view.findViewById(R.id.rl_bottom).setVisibility(View.VISIBLE);
		view.findViewById(R.id.btn_backweb).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mLJWebView.getmWebView().goBack();
			}
		});
		view.findViewById(R.id.btn_goweb).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mLJWebView.getmWebView().goForward();
			}
		});
		view.findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mLJWebView.getmWebView().reload();
			}
		});
	}
	
	/**
	 * 是否显示web页面下面的工具栏：如前进、返回、刷新
	 * @param show
	 */
	public void showWebRefreshBar(boolean show){
		showBar=show;
	}
}
