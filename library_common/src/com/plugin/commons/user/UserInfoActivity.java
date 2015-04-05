package com.plugin.commons.user;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.UserInfoModel;
import com.plugin.commons.service.UserInfoService;
import com.plugin.commons.service.UserInfoServiceImpl;
import com.plugin.commons.ui.main.LoginStatusActivity;

public class UserInfoActivity extends Activity{
	TextView tv_name;
	TextView tv_mobile;
	RelativeLayout rl_info;
	RelativeLayout rl_pwd;
	RelativeLayout rl_mobile;
	ImageView iv_icon;
	Button btn_loginout;
	UserInfoService userService;
	UserInfoModel mUser;
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		ComUtil.customeTitle(this, getResources().getString(R.string.zh_userinfo),true);
		initProper();
		ensureUI();
	}
	
	private void initProper(){
		userService = new UserInfoServiceImpl();
		tv_name = (TextView)findViewById(R.id.tv_name);
		tv_mobile = (TextView)findViewById(R.id.tv_mobile);
		rl_info = (RelativeLayout)findViewById(R.id.rl_info);
		rl_pwd = (RelativeLayout)findViewById(R.id.rl_pwd);
		rl_mobile = (RelativeLayout)findViewById(R.id.rl_mobile);
		iv_icon = (ImageView)findViewById(R.id.iv_icon);
		btn_loginout = (Button)findViewById(R.id.btn_loginout);
		btn_loginout.setBackgroundResource(ComApp.getInstance().appStyle.btn_sign_selector);
		
	}
	
	private void ensureUI(){
		
		rl_info.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserInfoActivity.this,UserModifyActivity.class);
				startActivity(intent);
			}
		});
		rl_pwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserInfoActivity.this,UserPwdModifyActivity.class);
				startActivity(intent);
			}
		});
		rl_mobile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ComUtil.gotoWaitingActivity(UserInfoActivity.this, "修改绑定手机号码");
			}
		});
		btn_loginout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String user="未登录用户";
	    		if(ComApp.getInstance().isLogin()){
					user=ComApp.getInstance().getLoginInfo().getUserid();
				}
				XHSDKUtil.addXHBehavior(UserInfoActivity.this,user, XHConstants.XHTOPIC_LOGINOUT,user+" logout success");
				userService.loginout();	
				
				Intent intent = new Intent(UserInfoActivity.this,LoginStatusActivity.class);			
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		mUser = ComApp.getInstance().getLoginInfo();
		if(mUser!=null){
			tv_name.setText(mUser.getUsername());
			tv_mobile.setText("绑定手机:"+mUser.getPhone());
			if(!FuncUtil.isEmpty(mUser.getPhoto())){
				ComApp.getInstance().getFinalBitmap().display(iv_icon, mUser.getPhoto());
			}
		}
		AnalyticsAgent.onResume(this);//新华sdk
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPause(this);//新华sdk
	}

}
