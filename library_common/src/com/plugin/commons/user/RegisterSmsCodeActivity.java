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
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.RspResultModel;
import com.zq.types.StBaseType;

public class RegisterSmsCodeActivity extends Activity{
	EditText et_mobile;
	EditText et_sms;
	Button btn_sms;
	Button btn_sure;
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registersms);
		ComUtil.customeTitle(this, getResources().getString(R.string.zh_register),true);
		initProper();
		ensureUI();
	}
	
	private void initProper(){
		et_mobile = (EditText)findViewById(R.id.et_mobile);
		et_sms = (EditText)findViewById(R.id.et_sms);
		btn_sms = (Button)findViewById(R.id.btn_sms);
		btn_sms.setBackgroundResource(ComApp.getInstance().appStyle.btn_again_selector);
		
		btn_sure = (Button)findViewById(R.id.btn_sure);
		btn_sure.setBackgroundResource(ComApp.getInstance().appStyle.btn_sign_selector);
	}
	
	private void ensureUI(){
		btn_sms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String mobile = et_mobile.getText().toString();
				if(FuncUtil.isEmpty(mobile)||!FuncUtil.checkMobile(mobile)){
					DialogUtil.showToast(RegisterSmsCodeActivity.this, "请输入正确的手机号码");
					return ;
				}
				DialogUtil.showProgressDialog(RegisterSmsCodeActivity.this);
				SituoHttpAjax.ajax(new SituoAjaxCallBack(){

					@Override
					public StBaseType requestApi() {
						// TODO Auto-generated method stub
						RspResultModel rsp = ComApp.getInstance().getApi().loginSmsCode(mobile,CoreContants.SMS_TYPE_0);
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(RegisterSmsCodeActivity.this);
						// TODO Auto-generated method stub
						RspResultModel rsp = (RspResultModel)baseType;
						if(ComUtil.checkRsp(RegisterSmsCodeActivity.this, rsp)){
							DialogUtil.showToast(RegisterSmsCodeActivity.this, rsp.getRetmsg());
						}
					}
					
				});
			}
		});
		
		btn_sure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String mobile = et_mobile.getText().toString();
				final String smscode = et_sms.getText().toString();
				if(FuncUtil.isEmpty(mobile)||!FuncUtil.checkMobile(mobile)){
					DialogUtil.showToast(RegisterSmsCodeActivity.this, "请输入正确的手机号码");
					return ;
				}
				
				if(FuncUtil.isEmpty(smscode)){
					DialogUtil.showToast(RegisterSmsCodeActivity.this, "验证码不能为空");
					return ;
				}
				
				Intent intent = new Intent(RegisterSmsCodeActivity.this,RegisterSetPwdActivity.class);
				intent.putExtra(RegisterSetPwdActivity.PARAMS_MOBILE, mobile);
				intent.putExtra(RegisterSetPwdActivity.PARAMS_SMSCODE, smscode);
				startActivity(intent);
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
