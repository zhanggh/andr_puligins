package com.plugin.commons.ui.base;

import java.util.Map;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.content.IntentFilter;

import com.plugin.commons.broadcast.BaseBroadCast;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;


/**
 * @author zhang
 * activity基类
 */
public class BaseActivity extends Activity{
	public DingLog log = new DingLog(this.getClass());
	protected BaseBroadCast receiver;
	protected SituoAjaxCallBack sCallBack;
	protected Map reqService;
	public int pageStart=0;
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
	 
}
