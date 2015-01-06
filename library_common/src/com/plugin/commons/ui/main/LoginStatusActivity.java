package com.plugin.commons.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.model.XinHuaModel;
import com.plugin.commons.user.LoginActivity;

public class LoginStatusActivity extends Activity {

	XinHuaModel xhModel;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		 
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(XinHuaAdActivity.PARAM_XH)) {
			xhModel =(XinHuaModel)getIntent().getExtras().get(XinHuaAdActivity.PARAM_XH);
		}
		if(ComApp.APP_NAME.equals(CoreContants.APP_LNZX)){
			// TODO Auto-generated method stub
			if(!ComApp.getInstance().isLogin())
			{
				Intent intent = new Intent(this,LoginActivity.class);
				intent.putExtra(LoginActivity.PARAM_BACK, LoginActivity.PARAM_BACK);
				this.startActivityForResult(intent,100);
			}else{
				Intent intent = new Intent(LoginStatusActivity.this,XinHuaAdActivity.class);
				intent.putExtra(XinHuaAdActivity.PARAM_XH, xhModel);
				startActivity(intent);
				finish();
			}
		}else{
			startActivity(new Intent(LoginStatusActivity.this,XinHuaAdActivity.class));
			finish();
		}
		
	}
	
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
		if(RESULT_OK==resultCode){
			Intent intent = new Intent(LoginStatusActivity.this,XinHuaAdActivity.class);
			intent.putExtra(XinHuaAdActivity.PARAM_XH, xhModel);
			startActivity(intent);
			finish();
		}else{
			this.finish();
		}
    } 
}
