package com.plugin.commons.petition;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.plugin.commons.user.LoginActivity;

public class PetitionFragment extends BaseFragment{
	public static final String TAG = "PetitionFragment";
	Button btn_petition;
	private static List<Fragment> fragmentList;
	public int mSelectTab=0;
	View view ;
	NewsTypeModel mNewType;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_petition, container, false);
		return view;
	}
 

	protected void initViews(View view) {
		 
		btn_petition = (Button) view.findViewById(R.id.btn_petition);
		btn_petition.setBackgroundResource(ComApp.getInstance().appStyle.btn_ask_selector);
		btn_petition.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(ComApp.getInstance().isLogin())
				{
					Intent intent = null;//mActivity
					if(CoreContants.APP_LNZX.equals(ComApp.APP_NAME)){
						intent = new Intent(mActivity,PetitionWrActivity.class);
					}else{
						intent = new Intent(mActivity,PetitionActivity.class);
					}
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
	
	protected void initDisplay() {
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
	public void onDestroyView()  
    {  
	  // TODO Auto-generated method stub  
		super.onDestroyView();  
	  
		  for(int i=0;i<fragmentList.size();i++){
			  if(fragmentList.get(i)!=null){
//				  getFragmentManager().beginTransaction().remove(fragmentList.get(i)).commit();  
			  }
		  }
	   
	  }  

	@Override
	public String getFragmentName() {
		return TAG;
	}
	public void onFrageSelect(int idnex){
		
	}


	public NewsTypeModel getmNewType() {
		return mNewType;
	}


	public void setmNewType(NewsTypeModel mNewType) {
		this.mNewType = mNewType;
	}
	
}
