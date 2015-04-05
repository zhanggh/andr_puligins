package com.plugin.commons.ui.base;

import java.util.Map;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.listener.BaseBroadCast;
import com.plugin.commons.service.SituoAjaxCallBackImp;


/**
 * @author zhang
 * activity基类
 */
public class BaseActivity extends Activity{
	public DingLog log = new DingLog(this.getClass());
	protected BaseBroadCast receiver;
	protected SituoAjaxCallBackImp sCallBack;
	public int pageStart=0;
	protected PullToRefreshListView lv_news;
	protected View footer;
	protected boolean hasFooter=false;
	protected ProgressBar proc_loading;
	protected ProgressBar pro_btm;
	protected TextView tv_msg;
	 @Override
     protected void onResume(){
        super.onResume();
        AnalyticsAgent.onResume(this);
        if(this.receiver!=null){
        	IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(this.receiver.getAcAction());
            registerReceiver(this.receiver, intentFilter);
        }
     }	
	 
	 @Override
	 protected void onDestroy() {
	     super.onDestroy();
	     if(this.receiver!=null){
	    	 unregisterReceiver(receiver);
	     }
	 }

	@Override
	protected void onPause() {
		AnalyticsAgent.onPause(this);
		super.onPause();
	}
	 
	/**
	 * listview 底部
	 */
	public void initFooterView(){
		LayoutInflater mInflater = LayoutInflater.from(this);
		footer = mInflater.inflate(R.layout.view_list_footer, null);
		tv_msg=(TextView) footer.findViewById(R.id.tv_msg);
		pro_btm=(ProgressBar) footer.findViewById(R.id.pro_btm);
	}
	public View getEmptyView(){
		return this.findViewById(R.id.ll_root);
	}
	
	public void initLoadingImg(){
		proc_loading=(ProgressBar) this.findViewById(R.id.proc_loading);
	}
}
