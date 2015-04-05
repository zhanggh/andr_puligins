package com.plugin.commons.ui.news;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.ui.fragment.base.BaseFragment;
 

public class NewsGroupActivity extends FragmentActivity {
	public static final String TAG = "NewsGroupActivity";
	DingLog log = new DingLog(NewsGroupActivity.class);
	private Map<String,BaseFragment> mMap = new HashMap<String,BaseFragment>();
	TextView tv_title;
	private Button btn_left;
	private Button btn_right;
	private String title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_content);
        
        String menCode=(String) this.getIntent().getCharSequenceExtra(CoreContants.PARAMS_CODE);
        title=(String) this.getIntent().getCharSequenceExtra(CoreContants.PARAMS_TITLE);
        
        initView();
        
        NewsGroupFragment groupFragment =new NewsGroupFragment();
        groupFragment.setTypeId(menCode);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, groupFragment);
        fragmentTransaction.commit();
	        	
    }  
	
	private void initView(){
		LinearLayout ll_title_bg_color=(LinearLayout) this.findViewById(R.id.ll_title_bg_color);
		ll_title_bg_color.setBackgroundColor(getResources().getColor(ComApp.getInstance().appStyle.title_bg_color));
		
		btn_left = (Button)this.findViewById(R.id.btn_title_left);
		btn_left.setBackgroundResource(ComApp.getInstance().appStyle.btn_back_selector);
		btn_right = (Button)this.findViewById(R.id.btn_title_right);
		btn_right.setBackgroundResource(ComApp.getInstance().appStyle.btn_my_selector);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText(title);
		tv_title.setTextColor(this.getResources().getColor(ComApp.getInstance().appStyle.title_text_color));
		btn_left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				NewsGroupActivity.this.finish();
			}
		});
	}
}
