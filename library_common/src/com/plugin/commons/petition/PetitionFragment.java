package com.plugin.commons.petition;

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

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.plugin.commons.user.LoginActivity;

public class PetitionFragment extends BaseFragment{
	public static final String TAG = "PetitionFragment";
	Button btn_petition;
	private static List<Fragment> fragmentList;
	public int mSelectTab=0;
	View view ;
	public static PetitionFragment newInstance() {
		PetitionFragment homeFragment = new PetitionFragment();
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
			view = inflater.inflate(R.layout.fragment_petition, container, false);
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
		 
		btn_petition = (Button) view.findViewById(R.id.btn_petition);
		btn_petition.setBackgroundResource(ComApp.getInstance().appStyle.btn_ask_selector);
//		int width = (ViewsUtil.getWindowWidth(mActivity)-mActivity.getResources().getDimensionPixelSize(R.dimen.gov_ask_width))/2;
		
		btn_petition.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(ComApp.getInstance().isLogin())
				{
					Intent intent = new Intent(mActivity,PetitionActivity.class);
					mActivity.startActivity(intent);
				}
				else{
					Intent intent = new Intent(mActivity,LoginActivity.class);
					intent.putExtra(LoginActivity.PARAM_BACK, LoginActivity.PARAM_BACK);
					mActivity.startActivity(intent);
				}
			}
		});




	}
	
	private void initDisplay() {
		fragmentList = new ArrayList<Fragment>();
		if(!fragmentList.contains(getFragmentManager().findFragmentById(R.id.fl_petition_list)))
			fragmentList.add(getFragmentManager().findFragmentById(R.id.fl_petition_list));
		setFragmentIndicator();
	}
	
	//刷新选择tab
	private void refreshSelectTab(){
		
	}
	
	private void setFragmentIndicator() {
		refreshSelectTab();
		getFragmentManager().beginTransaction().show(fragmentList.get(0)).commit();
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
	public void onFrageSelect(int idnex){
		
	}
}
