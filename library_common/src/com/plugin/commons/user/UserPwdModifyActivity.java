package com.plugin.commons.user;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
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
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.UserInfoModel;
import com.plugin.commons.service.UserInfoService;
import com.plugin.commons.service.UserInfoServiceImpl;
import com.zq.types.StBaseType;

public class UserPwdModifyActivity extends Activity{
	EditText et_pwd;
	EditText et_pwd1;
	EditText et_pwd2;
	UserInfoService userService;
	UserInfoModel mUser;
	Button btn_sure;
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userpwdmodify);
		ComUtil.customeTitle(this, getResources().getString(R.string.zh_userpwdmodify),true);
		initProper();
		ensureUI();
	}
	
	private void initProper(){
		userService = new UserInfoServiceImpl();
		mUser = ComApp.getInstance().getLoginInfo();
		et_pwd = (EditText)findViewById(R.id.et_pwd);
		et_pwd1 = (EditText)findViewById(R.id.et_pwd1);
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
				final String pwd1 = et_pwd1.getText().toString();
				final String pwd2 = et_pwd2.getText().toString();
				if(FuncUtil.isEmpty(pwd)){
					DialogUtil.showToast(UserPwdModifyActivity.this, "请输入原密码");
					return ;
				}
				if(FuncUtil.isEmpty(pwd1)){
					DialogUtil.showToast(UserPwdModifyActivity.this, "请输入新密码");
					return ;
				}
				if(FuncUtil.isEmpty(pwd2)){
					DialogUtil.showToast(UserPwdModifyActivity.this, "请输入确认密码");
					return ;
				}
				if(!pwd1.equals(pwd2)){
					DialogUtil.showToast(UserPwdModifyActivity.this, "两次密码输入不一致");
					return ;
				}
				DialogUtil.showProgressDialog(UserPwdModifyActivity.this);
				SituoHttpAjax.ajax(new SituoAjaxCallBack(){

					@Override
					public StBaseType requestApi() {
						// TODO Auto-generated method stub
						RspResultModel rsp = ComApp.getInstance().getApi().modify_pwd(mUser.getPhone(), pwd, pwd1);
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(UserPwdModifyActivity.this);
						// TODO Auto-generated method stub
						RspResultModel rsp = (RspResultModel)baseType;
						if(ComUtil.checkRsp(UserPwdModifyActivity.this, rsp)){
							DialogUtil.showToast(UserPwdModifyActivity.this, "修改成功");
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
