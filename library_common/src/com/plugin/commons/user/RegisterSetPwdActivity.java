package com.plugin.commons.user;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.UserInfoService;
import com.plugin.commons.service.UserInfoServiceImpl;
import com.plugin.commons.ui.main.MainActivity;
import com.zq.types.StBaseType;

public class RegisterSetPwdActivity extends Activity{
	public final static String PARAMS_SMSCODE = "smscode";
	public final static String PARAMS_MOBILE = "mobile";
	EditText et_pwd;
	EditText et_pwd2;
	Button btn_sure;
	String mMobile = "";
	String mSmsCode = "";
	UserInfoService userService;
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setpwd);
		ComUtil.customeTitle(this, getResources().getString(R.string.zh_setpwd),true);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAMS_SMSCODE)) {
			mSmsCode =getIntent().getExtras().getString(PARAMS_SMSCODE);
		}
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAMS_MOBILE)) {
			mMobile =getIntent().getExtras().getString(PARAMS_MOBILE);
		}
		userService = new UserInfoServiceImpl();
		initProper();
		ensureUI();
	}
	
	private void initProper(){
		et_pwd = (EditText)findViewById(R.id.et_pwd);
		et_pwd2 = (EditText)findViewById(R.id.et_pwd2);
		btn_sure = (Button)findViewById(R.id.btn_sure);
		btn_sure.setBackgroundResource(ComApp.getInstance().appStyle.btn_sign_selector);
	}
	
	private void ensureUI(){
		btn_sure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String pwd = et_pwd.getText().toString();
				final String pwd2 = et_pwd2.getText().toString();
				if(FuncUtil.isEmpty(pwd)){
					DialogUtil.showToast(RegisterSetPwdActivity.this, "请输入密码");
					return ;
				}
				if(FuncUtil.isEmpty(pwd2)){
					DialogUtil.showToast(RegisterSetPwdActivity.this, "请输入确认密码");
					return ;
				}
				if(!pwd.equals(pwd2)){
					DialogUtil.showToast(RegisterSetPwdActivity.this, "两次输入密码不一致");
					return ;
				}
				DialogUtil.showProgressDialog(RegisterSetPwdActivity.this);
				SituoHttpAjax.ajax(new SituoAjaxCallBack(){

					@Override
					public StBaseType requestApi() {
						// TODO Auto-generated method stub
						RspResultModel rsp = userService.register(mMobile, pwd, mSmsCode);
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(RegisterSetPwdActivity.this);
						// TODO Auto-generated method stub
						RspResultModel rsp = (RspResultModel)baseType;
						if(ComUtil.checkRsp(RegisterSetPwdActivity.this, rsp)){
							//用户行为采集
							XHSDKUtil.addXHBehavior(RegisterSetPwdActivity.this,mMobile, XHConstants.XHTOPIC_REGISTER,mMobile+" register success");
							Intent intent = new Intent(RegisterSetPwdActivity.this,MainActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							finish();
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
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPause(this);//新华sdk
	}

}
