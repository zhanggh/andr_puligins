package com.plugin.commons.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.plugin.R;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.FuncUtil;


public class WaitingActivity extends Activity{
	public final static String PARAMS_TITLE= "title";
	private String title = "";
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waiting);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAMS_TITLE)) {
			title =getIntent().getExtras().getString(PARAMS_TITLE);
		}
		title = FuncUtil.isEmpty(title)?"功能开发中":title;
		ComUtil.customeTitle(this, title,true);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
