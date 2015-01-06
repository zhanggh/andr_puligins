package com.plugin.commons.ui.baoliao;

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
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.plugin.commons.user.LoginActivity;

public class BaoliaoFragment extends BaseFragment{
	public static final String TAG = "BaoliaoFragment";
	private Activity mActivity;
	LinearLayout ll_my_bl;
	Button btn_baoliao;
	Button btn_other_bl;
	Button btn_my_bl;
	TextView tv_other_bl;
	TextView tv_my_bl;
	LinearLayout ll_other_bl;
	private static List<Fragment> fragmentList;
	public int mSelectTab=0;
	View view ;
	public static BaoliaoFragment newInstance() {
		BaoliaoFragment homeFragment = new BaoliaoFragment();
		return homeFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null)
			view = inflater.inflate(R.layout.fragment_baoliao, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initDisplay();
		
	}

	private void initViews(View view) {
		ll_my_bl = (LinearLayout) view.findViewById(R.id.ll_my_bl);
		btn_baoliao = (Button) view.findViewById(R.id.btn_baoliao);
		btn_baoliao.setBackgroundResource(ComApp.getInstance().appStyle.btn_ask_selector);
		
		tv_my_bl = (TextView) view.findViewById(R.id.tv_my_bl);
		tv_other_bl = (TextView) view.findViewById(R.id.tv_other_bl);
		btn_other_bl = (Button) view.findViewById(R.id.btn_other_bl);
		btn_other_bl.setBackgroundResource(ComApp.getInstance().appStyle.btn_baoliao_left_selector);
		
		btn_my_bl = (Button) view.findViewById(R.id.btn_my_bl);
		btn_my_bl.setBackgroundResource(ComApp.getInstance().appStyle.btn_baoliao_right_selector);
		
		ll_other_bl = (LinearLayout) view.findViewById(R.id.ll_other_bl);
		int width = (ComUtil.getWindowWidth(mActivity)-mActivity.getResources().getDimensionPixelSize(R.dimen.gov_ask_width))/2;
		ll_my_bl.getLayoutParams().width = width;
		ll_other_bl.getLayoutParams().width = width;
		
		ll_other_bl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				mSelectTab = 0;
				setFragmentIndicator();
			}
		});
		 
		ll_my_bl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mSelectTab = 1;
				setFragmentIndicator();
			}
		});
		
		btn_baoliao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(ComApp.getInstance().isLogin())
				{
					Intent intent = new Intent(mActivity,BaoliaoActivity.class);
					mActivity.startActivity(intent);
				}
				else{
					Intent intent = new Intent(mActivity,LoginActivity.class);
					intent.putExtra(CoreContants.PARAM_BACK, CoreContants.PARAM_BACK);
					mActivity.startActivity(intent);
				}
			}
		});




	}
	
	private void initDisplay() {
		fragmentList = new ArrayList<Fragment>();
		if(!fragmentList.contains(getFragmentManager().findFragmentById(R.id.fl_other_baoliao)))
			fragmentList.add(getFragmentManager().findFragmentById(R.id.fl_other_baoliao));
		if(!fragmentList.contains(getFragmentManager().findFragmentById(R.id.fl_my_baoliao)))
			fragmentList.add(getFragmentManager().findFragmentById(R.id.fl_my_baoliao));
		setFragmentIndicator();
	}
	
	//刷新选择tab
	@SuppressWarnings("deprecation")
	private void refreshSelectTab(){
		if(mSelectTab==1){
			btn_my_bl.setBackground(mActivity.getResources().getDrawable(ComApp.getInstance().appStyle.my_newspaper_btn_press));
			tv_my_bl.setTextColor(mActivity.getResources().getColor(R.color.blue_font));
			btn_other_bl.setBackground(mActivity.getResources().getDrawable(ComApp.getInstance().appStyle.net_newspaper_btn_normal));
			tv_other_bl.setTextColor(mActivity.getResources().getColor(R.color.grey_font2));
		}
		else if(mSelectTab==0){
			btn_other_bl.setBackground(mActivity.getResources().getDrawable(ComApp.getInstance().appStyle.net_newspaper_btn_press));
			tv_other_bl.setTextColor(mActivity.getResources().getColor(R.color.blue_font));
			btn_my_bl.setBackground(mActivity.getResources().getDrawable(ComApp.getInstance().appStyle.my_newspaper_btn_normal));
			tv_my_bl.setTextColor(mActivity.getResources().getColor(R.color.grey_font2));
		}
	}
	
	private void setFragmentIndicator() {
		refreshSelectTab();
		getFragmentManager().beginTransaction()
				.hide(fragmentList.get(0))
				.hide(fragmentList.get(1)).show(fragmentList.get(mSelectTab)).commit();
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
				  getFragmentManager().beginTransaction().remove(fragmentList.get(i)).commit();  
			  }
		  }
	   
	  }  

	@Override
	public String getFragmentName() {
		return TAG;
	}

	@Override
	public void onFrageSelect(int index) {
		// TODO Auto-generated method stub
		
	}
}
