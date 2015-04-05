package com.plugin.commons.petition;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.user.LoginActivity;

public class PetitionFirstActivity extends FragmentActivity{
	public static final String TAG = "PetitionFragment";
	Button btn_petition;
	private static List<Fragment> fragmentList;
	public int mSelectTab=0;
	private String title;
	private boolean noCache;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_petition);
		title = (String) getIntent().getCharSequenceExtra(CoreContants.PARAMS_TITLE);
		noCache = (boolean) getIntent().getBooleanExtra(CoreContants.PARAMS_MSG_ID, false);
		if(!FuncUtil.isEmpty(title)){
			ComUtil.customeTitle(this,title,true);
		}else{
			ComUtil.customeTitle(this,"",true);
		}
		
		initViews();
		initDisplay();
	}
	
	
	
	 
	protected void initViews() {
		 
		btn_petition = (Button) this.findViewById(R.id.btn_petition);
		btn_petition.setBackgroundResource(ComApp.getInstance().appStyle.btn_ask_selector);
//		int width = (ViewsUtil.getWindowWidth(PetitionFirstActivity.this)-PetitionFirstActivity.this.getResources().getDimensionPixelSize(R.dimen.gov_ask_width))/2;
		
		btn_petition.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(ComApp.getInstance().isLogin())
				{
					Intent intent=null;
					if(CoreContants.APP_LNZX.equals(ComApp.APP_NAME)){
						intent = new Intent(PetitionFirstActivity.this,PetitionWrActivity.class);
					}else{
						intent = new Intent(PetitionFirstActivity.this,PetitionActivity.class);
					}
					
					PetitionFirstActivity.this.startActivity(intent);
				}
				else{
					Intent intent = new Intent(PetitionFirstActivity.this,LoginActivity.class);
					intent.putExtra(LoginActivity.PARAM_BACK, LoginActivity.PARAM_BACK);
					PetitionFirstActivity.this.startActivity(intent);
				}
			}
		});




	}
	
	protected void initDisplay() {
		fragmentList = new ArrayList<Fragment>();
		if(!fragmentList.contains(this.getSupportFragmentManager().findFragmentById(R.id.fl_petition_list)))
			fragmentList.add(this.getSupportFragmentManager().findFragmentById(R.id.fl_petition_list));
		setFragmentIndicator();
	}
	
	//刷新选择tab
	private void refreshSelectTab(){
		
	}
	
	private void setFragmentIndicator() {
		refreshSelectTab();
		this.getSupportFragmentManager().beginTransaction().show(fragmentList.get(0)).commit();
	}

	 
	@Override
	public void onDestroy()  
    {  
		super.onDestroy();  
	  
		  for(int i=0;i<fragmentList.size();i++){
			  if(fragmentList.get(i)!=null){
//				  this.getSupportFragmentManager().beginTransaction().remove(fragmentList.get(i)).commit();  
			  }
		  }
	   System.out.println("***************************************************8");
	  }  
 
}
