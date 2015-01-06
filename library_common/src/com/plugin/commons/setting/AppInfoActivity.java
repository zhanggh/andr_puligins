package com.plugin.commons.setting;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.UpdateManager;
import com.plugin.commons.ui.base.WebActivity;

public class AppInfoActivity  extends Activity{
	
	private TextView tv_setting_version_contact_phone_tx;
	private TextView tv_setting_version_contact_phone_tx2;
	private TextView tv_setting_version_contact_website_tx;
	private TextView tv_setting_version_tile;
	private TextView tv_setting_version_sponsor_tx;
	private TextView tv_setting_version_contact_fax_tx;
	private TextView tv_setting_version_contact_addr_tx;
	private ImageView im_setting_version_logo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_testversion);
		ComUtil.customeTitle(this,this.getResources().getString(R.string.app_name),true);
		initView();
	}
	
	
	
	
	private void initView(){
		if(!FuncUtil.isEmpty(ComApp.getInstance().appStyle.tv_setting_version_contact_phone_tx)||!FuncUtil.isEmpty(ComApp.getInstance().appStyle.tv_setting_version_contact_phone_tx2)){
			if(!FuncUtil.isEmpty(ComApp.getInstance().appStyle.tv_setting_version_contact_phone_tx)){
				tv_setting_version_contact_phone_tx=(TextView) this.findViewById(R.id.tv_setting_version_contact_phone_tx);
				tv_setting_version_contact_phone_tx.setVisibility(View.VISIBLE);
				tv_setting_version_contact_phone_tx.setText(ComApp.getInstance().appStyle.tv_setting_version_contact_phone_tx);
			}
			if(!FuncUtil.isEmpty(ComApp.getInstance().appStyle.tv_setting_version_contact_phone_tx2)){
				tv_setting_version_contact_phone_tx2=(TextView) this.findViewById(R.id.tv_setting_version_contact_phone_tx2);
				tv_setting_version_contact_phone_tx2.setVisibility(View.VISIBLE);
				tv_setting_version_contact_phone_tx2.setText(ComApp.getInstance().appStyle.tv_setting_version_contact_phone_tx2);
			}
		}else{
			LinearLayout ll_ph=(LinearLayout)this.findViewById(R.id.ll_setting_version_contact_phone);
			ll_ph.setVisibility(View.GONE);
		}
		im_setting_version_logo = (ImageView) this.findViewById(R.id.im_setting_version_logo);
		im_setting_version_logo.setImageResource(ComApp.getInstance().appStyle.im_setting_version_logo);	
			
		if(!FuncUtil.isEmpty(ComApp.getInstance().appStyle.tv_setting_version_contact_website_tx)){
			tv_setting_version_contact_website_tx=(TextView) this.findViewById(R.id.tv_setting_version_contact_website_tx);
			tv_setting_version_contact_website_tx.setText(ComApp.getInstance().appStyle.tv_setting_version_contact_website_tx);
		}else{
			LinearLayout ll_wb=(LinearLayout)this.findViewById(R.id.ll_setting_version_website);
			ll_wb.setVisibility(View.GONE);
		}
		
		if(!FuncUtil.isEmpty(ComApp.getInstance().appStyle.tv_setting_version_tile)){
			tv_setting_version_tile=(TextView) this.findViewById(R.id.tv_setting_version_tile);
			tv_setting_version_tile.setText(ComApp.getInstance().appStyle.tv_setting_version_tile);
		}
		
		if(!FuncUtil.isEmpty(ComApp.getInstance().appStyle.tv_setting_version_sponsor_tx)){
			tv_setting_version_sponsor_tx=(TextView) this.findViewById(R.id.tv_setting_version_sponsor_tx);
			tv_setting_version_sponsor_tx.setText(ComApp.getInstance().appStyle.tv_setting_version_sponsor_tx);
		}else{
			LinearLayout ll_sponsor=(LinearLayout)this.findViewById(R.id.ll_setting_version_sponsor);
			ll_sponsor.setVisibility(View.GONE);
		}
		
		if(!FuncUtil.isEmpty(ComApp.getInstance().appStyle.tv_setting_version_contact_fax_tx)){
			tv_setting_version_contact_fax_tx=(TextView) this.findViewById(R.id.tv_setting_version_contact_fax_tx);
			tv_setting_version_contact_fax_tx.setText(ComApp.getInstance().appStyle.tv_setting_version_contact_fax_tx);
		}else{
			LinearLayout ll_fax=(LinearLayout)this.findViewById(R.id.ll_setting_version_fax);
			ll_fax.setVisibility(View.GONE);
		}
		
		if(!FuncUtil.isEmpty(ComApp.getInstance().appStyle.tv_setting_version_contact_addr_tx)){
			tv_setting_version_contact_addr_tx=(TextView) this.findViewById(R.id.tv_setting_version_contact_addr_tx);
			tv_setting_version_contact_addr_tx.setText(ComApp.getInstance().appStyle.tv_setting_version_contact_addr_tx);
		}else{
			LinearLayout ll_addr=(LinearLayout)this.findViewById(R.id.ll_setting_version_addr);
			ll_addr.setVisibility(View.GONE);
		}
	}
	
	
	/**
	 * 检查版本
	 * @param v
	 */
	public void checkNewVersion(View v){
		UpdateManager.getUpdateManager().checkAppUpdate(AppInfoActivity.this,
				true);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onResume(this);//新华sdk
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPause(this);//新华sdk
	}
	
	
	public void toCall(View v){
		String number=null;
		if(v.getId()==R.id.tv_setting_version_contact_phone_tx){
			number =ComApp.getInstance().appStyle.tv_setting_version_contact_phone_tx;  
		}
		if(v.getId()==R.id.tv_setting_version_contact_phone_tx2){
			number =ComApp.getInstance().appStyle.tv_setting_version_contact_phone_tx2;  
		}
		Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number.replace("-", "")));  
		startActivity(intent); 
	}
	public void toWebView(View v){
		Intent intent = new Intent(this,WebActivity.class);  
		startActivity(intent); 
	}
}
