package com.plugin.commons.user;

import java.util.HashSet;
import java.util.Set;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.UserInfoModel;
import com.plugin.commons.service.UserInfoService;
import com.plugin.commons.service.UserInfoServiceImpl;
import com.plugin.commons.ui.askgov.AskGovActivity;
import com.plugin.commons.ui.main.MainActivity;
import com.zq.types.StBaseType;

public class LoginActivity extends Activity{
	public static final int QUEST_CODE_LOGIN = 31;
	public static final String FROM_CODE_ASKGOV = "21";
	public static final String FROM_CODE_MYASKGOV = "22";
	public static final String PARAM_BACK = "back";
	public static final String PARAM_CODE = "code";
	EditText et_name;
	EditText et_pwd;
	Button btn_sign;
	Button btn_register;
	TextView zh_tv_forgetpwd;
	UserInfoService userService;
	boolean mIsBack;
	String mFromCode = "";
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ComUtil.customeTitle(this, getResources().getString(R.string.zh_login),true);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAM_BACK)) {//如果是原路返回
			mIsBack = true;
		}
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAM_CODE)) {//如果是原路返回
			mFromCode = getIntent().getExtras().getString(PARAM_CODE);
		}
		initProper();
		ensureUI();
	}
	
	private void initProper(){
		zh_tv_forgetpwd=(TextView) findViewById(R.id.zh_tv_forgetpwd);
		
		zh_tv_forgetpwd.setTextColor(ComApp.getInstance().appStyle.tv_forgetpwd_color);
		userService = new UserInfoServiceImpl();
		et_name = (EditText)findViewById(R.id.et_name);
		et_pwd = (EditText)findViewById(R.id.et_pwd);
		btn_sign = (Button)findViewById(R.id.btn_sign);
		btn_sign.setBackgroundResource(ComApp.getInstance().appStyle.btn_sign_selector);
		 
		if(!ComApp.APP_NAME.equals(CoreContants.APP_LNZX)){
			btn_register = (Button)findViewById(R.id.btn_title_right);
			btn_register.setVisibility(View.VISIBLE);
			btn_register.setBackground(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_registration_selector));
		}else{
			zh_tv_forgetpwd.setVisibility(View.GONE);
			 
		}
	}
	
	private void ensureUI(){
		
		if(!ComApp.APP_NAME.equals(CoreContants.APP_LNZX)){
			btn_register.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(LoginActivity.this,RegisterSmsCodeActivity.class);
					startActivity(intent);
				}
			});
		}
		
		btn_sign.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String pwd = et_pwd.getText().toString();
				final String name = et_name.getText().toString();
				if(FuncUtil.isEmpty(name)){
					DialogUtil.showToast(LoginActivity.this, "请输入账号");
					return ;
				}
				if(FuncUtil.isEmpty(pwd)){
					DialogUtil.showToast(LoginActivity.this, "请输入密码");
					return ;
				}
				
				DialogUtil.showProgressDialog(LoginActivity.this);
				SituoHttpAjax.ajax(new SituoAjaxCallBack(){

					@Override
					public StBaseType requestApi() {
						// TODO Auto-generated method stub
						RspResultModel rsp = userService.login(name, pwd, "");
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(LoginActivity.this);
						// TODO Auto-generated method stub
						RspResultModel rsp = (RspResultModel)baseType;
						if(ComUtil.checkRsp(LoginActivity.this, rsp)){
							setResult(RESULT_OK);
							if (ComApp.getInstance().isLogin()) {
					        	UserInfoModel customer = ComApp.getInstance().getLoginInfo();
					        	Set<String> ss = new HashSet<String>();
					        	if(!FuncUtil.isEmpty(customer.getIdentity())){
					        		ss.add(customer.getIdentity());
					        	}
					        	if(customer!=null){
					        		JPushInterface.setAliasAndTags(LoginActivity.this,
					        				customer.getPhone(), ss);
					        	}
					        	UserInfoModel user = ComApp.getInstance().getLoginInfo();
					    		if(user!=null){
					    			AnalyticsAgent.setUserId(LoginActivity.this, user.getPhone());
					    			AnalyticsAgent.setUserName(LoginActivity.this, user.getUsername());
					    		}
					    		String userid="未登录用户";
					    		userid=ComApp.getInstance().getLoginInfo().getUserid();
					    		XHSDKUtil.addXHBehavior(LoginActivity.this,userid, XHConstants.XHTOPIC_LOGIN,userid+"login success");
							}
							if(mIsBack){
								finish();
							}
							else{
								if(FROM_CODE_ASKGOV.equals(mFromCode)){
									Intent intent = new Intent(LoginActivity.this,AskGovActivity.class);
									startActivity(intent);
									finish();
								}else{
									//用户行为采集
									Intent intent = new Intent(LoginActivity.this,MainActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
									finish();
								}
								
							}
							
						}
					}
					
				});
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onResume(this);//新华sdk
	}

	/**
	 * 忘记密码
	 * @param view
	 */
	public void forgetpwd(View view){
		
		Intent intent = new Intent(LoginActivity.this,ForgetPwdSmsCodeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPause(this);//新华sdk
	}
}
