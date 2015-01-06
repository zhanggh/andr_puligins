package com.plugin.commons.broadcast;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.plugin.commons.helper.DingLog;
import com.plugin.commons.model.NewsInfoModel;

public class BaseBroadCast extends BroadcastReceiver {
	public Activity ac;
	public DingLog log = new DingLog(this.getClass());
	public String acAction = "1";//关闭activity
	NewsInfoModel mNews;
	@Override
	public void onReceive(Context context, Intent intent) {
		String ACTION_NAME = intent.getAction();
		log.debug("ACTION_NAME："+ACTION_NAME);
		if("".equals(ACTION_NAME))
		{	
			 
		}
	}

	public String getAcAction() {
		return acAction;
	}

	public void setAcAction(String acAction) {
		this.acAction = acAction;
	}

	public NewsInfoModel getmNews() {
		return mNews;
	}

	public void setmNews(NewsInfoModel mNews) {
		this.mNews = mNews;
	}

	
}
