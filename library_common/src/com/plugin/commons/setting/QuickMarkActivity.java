package com.plugin.commons.setting;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;



public class QuickMarkActivity  extends Activity {
	DingLog log = new DingLog(QuickMarkActivity.class);
	
	ImageView quickmark;
    TextView  markdesc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quick_mark);
		ComUtil.customeTitle(this, "二维码",true);
		initViews();
	}
	
	
	private void initViews(){
		
		quickmark= (ImageView) this.findViewById(R.id.quickmark);
		//quickmark.setImageResource(ComApp.getInstance().appStyle.im_set_logo);
		markdesc=(TextView)this.findViewById(R.id.markdesc);
		markdesc.setText("扫描二维码，下载魅力蒙古贞");
		
		
		
	}
	
	
	

}
