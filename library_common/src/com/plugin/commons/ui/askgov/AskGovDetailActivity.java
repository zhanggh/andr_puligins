package com.plugin.commons.ui.askgov;

import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.CommentModel;
import com.plugin.commons.model.GovmentInfoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.AskGovService;
import com.plugin.commons.service.AskGovServiceImpl;
import com.plugin.commons.user.LoginActivity;
import com.zq.types.StBaseType;


public class AskGovDetailActivity extends Activity{
	public static String PARAMS_GOV = "gov";
	GovmentInfoModel mGov;
	Button btn_right;
	List<CommentModel> mComments;
	ImageView iv_image;
	TextView tv_name;
	TextView tv_helpcount;
	TextView tv_content;
	TextView tv_answer;
	Button btn_ask;
	AskGovService askSvc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_askgovdetail);
		ComUtil.customeTitle(this, "机构详情",true);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAMS_GOV)) {
			mGov =(GovmentInfoModel)getIntent().getExtras().get(PARAMS_GOV);
		}
		askSvc = new AskGovServiceImpl();
		initViews();
		refreshUI();
		request();
	}
	
	
	private void initViews()
	{
		iv_image = (ImageView)this.findViewById(R.id.iv_image);
		iv_image.setImageResource(ComApp.getInstance().appStyle.my_btn_normal);
		
		tv_name = (TextView)this.findViewById(R.id.tv_name);
		tv_helpcount = (TextView)this.findViewById(R.id.tv_helpcount);
		tv_content = (TextView)this.findViewById(R.id.tv_content);
		tv_answer = (TextView)this.findViewById(R.id.tv_answer);
		tv_answer.setTextColor(ComApp.getInstance().appStyle.font_blue_selector);
		
		btn_ask = (Button)this.findViewById(R.id.btn_ask);
		btn_ask.setBackgroundResource(ComApp.getInstance().appStyle.btn_sign_selector);
		
		tv_answer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AskGovDetailActivity.this,AskGovAnswerActivity.class);
				intent.putExtra(AskGovAnswerActivity.PARAMS_GOV, mGov);
				startActivity(intent);
				
			}
		});
		
		btn_ask.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(ComApp.getInstance().isLogin())
				{
					Intent intent = new Intent(AskGovDetailActivity.this,AskGovActivity.class);
					intent.putExtra(AskGovActivity.PARAMS_ORG, mGov);
					startActivity(intent);
				}
				else{
					Intent intent = new Intent(AskGovDetailActivity.this,LoginActivity.class);
					intent.putExtra(LoginActivity.PARAM_BACK, LoginActivity.PARAM_BACK);
					startActivity(intent);
				}
			}
		});
		
	}
	
	private void refreshUI()
	{
		if(mGov!=null){
			ComApp.getInstance().getFinalBitmap().display(iv_image, mGov.getPhoto());
			tv_name.setText(mGov.getName());
			tv_helpcount.setText("累计帮助人数  150次");
			tv_content.setText(mGov.getDesc());
		}
		
		
	}
	
	private void request(){
		DialogUtil.showProgressDialog(AskGovDetailActivity.this);
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){

			@Override
			public StBaseType requestApi() {
				// TODO Auto-generated method stub
				RspResultModel rsp = askSvc.getOrgDetail(mGov.getId());
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(AskGovDetailActivity.this);
				// TODO Auto-generated method stub
				RspResultModel rsp = (RspResultModel)baseType;
				if(ComUtil.checkRsp(AskGovDetailActivity.this, rsp)){
					mGov = rsp.getOrg();
					refreshUI();
				}
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
