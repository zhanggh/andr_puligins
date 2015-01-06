package com.plugin.commons.broadcast;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.plugin.commons.CoreContants;


public class ComBroatCast   extends BaseBroadCast{
	
	public ComBroatCast(Activity ac,String acAction ) {
		super();
		this.ac=ac;
		this.acAction=acAction;
		 
	}

	 
	@Override
	public void onReceive(Context context, Intent intent) {
		String ACTION_NAME = intent.getAction();
		if(this.ac!=null&&CoreContants.ACTIVITY_COSE.equals(ACTION_NAME))
		{	
			this.ac.finish();
		}
		//评论返回之后
		if(this.ac!=null&&CoreContants.ACTIVITY_RETTRUN.equals(ACTION_NAME))
		{	
			int replyCount=Integer.parseInt(this.mNews.getReplycount())+1;
			this.mNews.setReplycount(String.valueOf(replyCount));
		}
	}

	 
}
