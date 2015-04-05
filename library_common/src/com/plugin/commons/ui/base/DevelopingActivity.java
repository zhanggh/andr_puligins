package com.plugin.commons.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.plugin.R;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.FuncUtil;

public class DevelopingActivity  extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_find_people);
		String title="开发中";
		title = (String) getIntent().getCharSequenceExtra(CoreContants.PARAMS_MSG);
		if(!FuncUtil.isEmpty(title)){
			ComUtil.customeTitle(this,title,true);
		}else{
			ComUtil.customeTitle(this,"开发中",true);
		}
		
	}
}
