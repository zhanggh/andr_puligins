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

/**
 * @author zhang
 * 发送短信验证码（忘记密码）
 */
public class ForgetPwdSmsCodeActivity extends Activity{
	EditText et_mobile;
	Button btn_next;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpwd_smscode);
		ComUtil.customeTitle(this, getResources().getString(R.string.zh_forgetpwd),true);
		 
		initProper();
		ensureUI();
	}
	
	private void initProper(){
		 
		et_mobile = (EditText)findViewById(R.id.et_mobile);
		btn_next = (Button)findViewById(R.id.btn_next);
		btn_next.setBackgroundResource(ComApp.getInstance().appStyle.btn_sign_selector);
	}
	
	private void ensureUI(){
		btn_next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String phone = et_mobile.getText().toString();
				if(FuncUtil.isEmpty(phone)){
					DialogUtil.showToast(ForgetPwdSmsCodeActivity.this, "请输入手机号");
					return ;
				}
				DialogUtil.showProgressDialog(ForgetPwdSmsCodeActivity.this);
				SituoHttpAjax.ajax(new SituoAjaxCallBack(){

					@Override
					public StBaseType requestApi() {
						RspResultModel rsp = ComApp.getInstance().getApi().loginSmsCode(phone,CoreContants.SMS_TYPE_1);
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(ForgetPwdSmsCodeActivity.this);
						// TODO Auto-generated method stub
						RspResultModel rsp = (RspResultModel)baseType;
						if(ComUtil.checkRsp(ForgetPwdSmsCodeActivity.this, rsp)){
							DialogUtil.showToast(ForgetPwdSmsCodeActivity.this, rsp.getRetmsg());
							if("0".equals(rsp.getRetcode())){
								Intent intent = new Intent(ForgetPwdSmsCodeActivity.this,ForgetPwdInputCodeActivity.class);
								intent.putExtra(RegisterSetPwdActivity.PARAMS_MOBILE, phone);
								startActivity(intent);
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
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPause(this);//新华sdk
	}
}
