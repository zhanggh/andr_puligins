package com.plugin.commons.setting;

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
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.RspResultModel;
import com.zq.types.StBaseType;

public class SuggestActivity   extends Activity{
	
	Button btn_right;
	EditText et_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_suggest);
		ComUtil.customeTitle(this, "意见反馈",true);
		initViews();
		refreshUI();
	}
	
	private void initViews()
	{
		btn_right = (Button)this.findViewById(R.id.btn_title_right);
		 
		et_content = (EditText)this.findViewById(R.id.et_content);
		btn_right.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_dialogsure_selector));
		btn_right.setVisibility(View.VISIBLE);
	}
	private void refreshUI(){
		btn_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String content = et_content.getText().toString();
				if(FuncUtil.isEmpty(content)){
					DialogUtil.showToast(SuggestActivity.this, "请填写意见内容");
					return ;
				}
				SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {
					
					@Override
					public StBaseType requestApi() {
						RspResultModel rsp =null;
						rsp = ComApp.getInstance().getApi().userFeedback(content);
						return rsp;
					}
					@Override
					public void callBack(StBaseType baseType) {
						RspResultModel result = (RspResultModel)baseType;
						if(ComUtil.checkRsp(SuggestActivity.this, result)){
							DialogUtil.showToast(SuggestActivity.this, "意见反馈成功，谢谢您的宝贵意见");
							String user="未登录用户";
							if(ComApp.getInstance().isLogin()){
								user=ComApp.getInstance().getLoginInfo().getUserid();
							}
							XHSDKUtil.addXHBehavior(SuggestActivity.this,user, XHConstants.XHTOPIC_FEEDBACK,user+" feedback success");
							SuggestActivity.this.finish();	
						}else{
							DialogUtil.showToast(SuggestActivity.this, "意见反馈失败，请重试");
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