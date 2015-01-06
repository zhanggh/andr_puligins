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
import com.plugin.commons.service.UserInfoServiceImpl;
import com.plugin.commons.ui.main.MainActivity;
import com.zq.types.StBaseType;

/**
 * @author zhang
 *	重置密码（忘记密码）
 */
public class ForgetPwdSetPwdActivity extends Activity{
	public final static String PARAMS_MOBILE = "mobile";
	public final static String PARAMS_SMSCODE = "smscode";
	EditText et_passwd;
	EditText et_ensure_passwd;
	Button btn_op_finish;
	private UserInfoServiceImpl userService;
	String phone = "";
	String smscode = "";
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpwd_setpwd);
		ComUtil.customeTitle(this, getResources().getString(R.string.zh_findpwd),true);
		 
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAMS_MOBILE)) {
			phone =getIntent().getExtras().getString(PARAMS_MOBILE);
		}
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAMS_SMSCODE)) {
			smscode =getIntent().getExtras().getString(PARAMS_SMSCODE);
		}
		
		initProper();
		ensureUI();
	}
	
	private void initProper(){
		userService = new UserInfoServiceImpl();
		et_passwd = (EditText)findViewById(R.id.et_passwd);
		et_ensure_passwd = (EditText)findViewById(R.id.et_ensure_passwd);
		btn_op_finish = (Button)findViewById(R.id.btn_op_finish);
		btn_op_finish.setBackgroundResource(ComApp.getInstance().appStyle.btn_sign_selector);
	}
	
	private void ensureUI(){
		btn_op_finish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String pwd = et_passwd.getText().toString();
				final String enSurePwd = et_ensure_passwd.getText().toString();
				 
				if(FuncUtil.isEmpty(pwd)){
					DialogUtil.showToast(ForgetPwdSetPwdActivity.this, "请输入密码");
					return ;
				}
				if(FuncUtil.isEmpty(enSurePwd)){
					DialogUtil.showToast(ForgetPwdSetPwdActivity.this, "请输入确认密码");
					return ;
				}
				if(!enSurePwd.equals(pwd)){
					DialogUtil.showToast(ForgetPwdSetPwdActivity.this, "请输入两次密码不一致，请确认");
					return ;
				}
				
				DialogUtil.showProgressDialog(ForgetPwdSetPwdActivity.this);
				SituoHttpAjax.ajax(new SituoAjaxCallBack(){

					@Override
					public StBaseType requestApi() {
						// TODO Auto-generated method stub
						RspResultModel rsp = userService.findpwd(phone, pwd,smscode );
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(ForgetPwdSetPwdActivity.this);
						// TODO Auto-generated method stub
						RspResultModel rsp = (RspResultModel)baseType;
						if(ComUtil.checkRsp(ForgetPwdSetPwdActivity.this, rsp)){
							setResult(RESULT_OK);
							//用户行为采集
							XHSDKUtil.addXHBehavior(ForgetPwdSetPwdActivity.this,phone, XHConstants.XHTOPIC_FINDPWD,phone +" Reset password success");
							Intent intent = new Intent(ForgetPwdSetPwdActivity.this,MainActivity.class);
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
