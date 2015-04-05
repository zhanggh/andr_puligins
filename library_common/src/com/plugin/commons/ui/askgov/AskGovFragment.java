package com.plugin.commons.ui.askgov;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.plugin.commons.user.LoginActivity;

public class AskGovFragment extends BaseFragment{
	DingLog log = new DingLog(AskGovFragment.class);
	public static final String TAG = "AskGovFragment";
	LinearLayout ll_my;
	Button btn_ask;
	LinearLayout ll_trends;
	LinearLayout ll_hot;
	LinearLayout ll_gov;
	private static List<Fragment> fragmentList;
	public int mSelectTab=0;
	public static AskGovFragment newInstance() {
		AskGovFragment homeFragment = new AskGovFragment();
		return homeFragment;
	}
 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_askgov, container, false);
		return view;
	}

	 

	protected void initViews(View view) {
		ll_my = (LinearLayout) view.findViewById(R.id.ll_my);
		btn_ask = (Button) view.findViewById(R.id.btn_ask);
		btn_ask.setBackgroundResource(ComApp.getInstance().appStyle.btn_ask_selector);
		
		ll_trends = (LinearLayout) view.findViewById(R.id.ll_trends);
		ll_hot = (LinearLayout) view.findViewById(R.id.ll_hot);
		ll_gov = (LinearLayout) view.findViewById(R.id.ll_gov);
		int width = (ComUtil.getWindowWidth(mActivity)-mActivity.getResources().getDimensionPixelSize(R.dimen.gov_ask_width))/4;
		ll_my.getLayoutParams().width = width;
		ll_trends.getLayoutParams().width = width;
		ll_hot.getLayoutParams().width = width;
		ll_gov.getLayoutParams().width = width;
		ll_my.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(ComApp.getInstance().isLogin())
				{
					mSelectTab = 3;
					setFragmentIndicator();
				}
				else{
					Intent intent = new Intent(mActivity,LoginActivity.class);
					intent.putExtra(LoginActivity.PARAM_BACK, LoginActivity.PARAM_BACK);
					mActivity.startActivity(intent);
				}
				
			}
		});
		
		ll_hot.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mSelectTab = 0;
				setFragmentIndicator();
			}
		});
		
		ll_trends.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mSelectTab = 1;
				setFragmentIndicator();
			}
		});
		
		ll_gov.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mSelectTab = 2;
				setFragmentIndicator();
			}
		});
		
		btn_ask.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(ComApp.getInstance().isLogin())
				{
					Intent intent = new Intent(mActivity,AskGovActivity.class);
					mActivity.startActivity(intent);
				}
				else{
					Intent intent = new Intent(mActivity,LoginActivity.class);
					intent.putExtra(LoginActivity.PARAM_CODE, LoginActivity.FROM_CODE_ASKGOV);
					mActivity.startActivity(intent);
				}
			}
		});




	}
	
	protected void initDisplay() {
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(getFragmentManager().findFragmentById(R.id.fl_hot));
		fragmentList.add(getFragmentManager().findFragmentById(R.id.fl_trends));
		fragmentList.add(getFragmentManager().findFragmentById(R.id.fl_gov));
		fragmentList.add(getFragmentManager().findFragmentById(R.id.fl_my));
		setFragmentIndicator();
	}
	
	//刷新选择tab
	private void refreshSelectTab(){
		Button btn_hot = (Button)mActivity.findViewById(R.id.btn_hot);
		btn_hot.setBackgroundResource(ComApp.getInstance().appStyle.hotspot_btn_normal);
		
		
		TextView tv_hot = (TextView)mActivity.findViewById(R.id.tv_hot);
		
		Button btn_trends = (Button)mActivity.findViewById(R.id.btn_trends);
		btn_trends.setBackgroundResource(ComApp.getInstance().appStyle.trends_btn_normal);
		
		TextView tv_trends = (TextView)mActivity.findViewById(R.id.tv_trends);
		
		Button btn_gov = (Button)mActivity.findViewById(R.id.btn_gov);
		btn_gov.setBackgroundResource(ComApp.getInstance().appStyle.institution_btn_normal);
		
		TextView tv_gov = (TextView)mActivity.findViewById(R.id.tv_gov);
		
		Button btn_my = (Button)mActivity.findViewById(R.id.btn_my);
		btn_my.setBackgroundResource(ComApp.getInstance().appStyle.my_btn_normal);
		
		TextView tv_my = (TextView)mActivity.findViewById(R.id.tv_my);
		btn_hot.setBackgroundDrawable(mActivity.getResources().getDrawable(ComApp.getInstance().appStyle.hotspot_btn_normal));
		tv_hot.setTextColor(mActivity.getResources().getColor(R.color.grey_font2));
		btn_trends.setBackgroundDrawable(mActivity.getResources().getDrawable(ComApp.getInstance().appStyle.trends_btn_normal));
		tv_trends.setTextColor(mActivity.getResources().getColor(R.color.grey_font2));
		btn_gov.setBackgroundDrawable(mActivity.getResources().getDrawable(ComApp.getInstance().appStyle.institution_btn_normal));
		tv_gov.setTextColor(mActivity.getResources().getColor(R.color.grey_font2));
		btn_my.setBackgroundDrawable(mActivity.getResources().getDrawable(ComApp.getInstance().appStyle.my_btn_normal));
		tv_my.setTextColor(mActivity.getResources().getColor(R.color.grey_font2));
		if(mSelectTab==1){
			btn_trends.setBackgroundDrawable(mActivity.getResources().getDrawable(ComApp.getInstance().appStyle.trends_btn_press));
			tv_trends.setTextColor(mActivity.getResources().getColor(R.color.blue_font));
		}
		else if(mSelectTab==2){
			btn_gov.setBackgroundDrawable(mActivity.getResources().getDrawable(ComApp.getInstance().appStyle.institution_btn_press));
			tv_gov.setTextColor(mActivity.getResources().getColor(R.color.blue_font));
		}
		else if(mSelectTab==3){
			btn_my.setBackgroundDrawable(mActivity.getResources().getDrawable(ComApp.getInstance().appStyle.my_btn_press));
			tv_my.setTextColor(mActivity.getResources().getColor(R.color.blue_font));
		}
		else if(mSelectTab==0){
			btn_hot.setBackgroundDrawable(mActivity.getResources().getDrawable(ComApp.getInstance().appStyle.hotspot_btn_press));
			tv_hot.setTextColor(mActivity.getResources().getColor(R.color.blue_font));
		}
		
	}
	
	private void setFragmentIndicator() {
		refreshSelectTab();
		getFragmentManager().beginTransaction()
				.hide(fragmentList.get(0))
				.hide(fragmentList.get(1))
				.hide(fragmentList.get(2))
				.hide(fragmentList.get(3)).show(fragmentList.get(mSelectTab)).commit();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	@Override
	public void onDestroyView()  
    {  
	  // TODO Auto-generated method stub  
		super.onDestroyView();  
	    
		  for(int i=0;i<fragmentList.size();i++){
			  if(fragmentList.get(i)!=null){
				  log.info("销毁:"+i);
				  getFragmentManager().beginTransaction().remove(fragmentList.get(i)).commit();  
			  }
		  }
	   
	  }  

	@Override
	public String getFragmentName() {
		return TAG;
	}
	public void onFrageSelect(int idnex){
		
	}

}
