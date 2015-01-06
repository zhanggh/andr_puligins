package com.plugin.commons.ui.number;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.plugin.R;
import com.plugin.commons.helper.ComUtil;

public class NumberDetailActivity  extends FragmentActivity{
	 
	

	protected static final String PARAMS_MSG = "params_msg";

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_detail);
		ComUtil.customeTitle(this,"详情",true);
	}
	 
 
}
