package com.plugin.commons.listener;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.plugin.commons.helper.DingLog;
import com.plugin.commons.model.NewsInfoModel;

public abstract class BaseBroadCast extends BroadcastReceiver {
	public Activity ac;
	public DingLog log = new DingLog(this.getClass());
	public String acAction = "1";//关闭activity
	protected NewsInfoModel mNews; 
	@Override
	public void onReceive(Context context, Intent intent) {
		String ACTION_NAME = intent.getAction();
		log.debug("ACTION_NAME："+ACTION_NAME);
		onReceiveExt(context, intent);
	}

	public String getAcAction() {
		return acAction;
	}

	public void setAcAction(String acAction) {
		this.acAction = acAction;
	}

	public abstract void onReceiveExt(Context context, Intent intent);
}
