package com.plugin.commons.user;

import java.util.Timer;
import java.util.TimerTask;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.RspResultModel;
import com.zq.types.StBaseType;

/**
 * @author zhang
 * 填写短信验证码（忘记密码）
 */
public class ForgetPwdInputCodeActivity extends Activity{

	public final static String PARAMS_MOBILE = "mobile";
	public final static String PARAMS_SMSCODE = "smscode";
	EditText et_smscode;
	Button btn_next;
	Button btn_resms;
	String phone = "";
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpwd_inputcode);
		ComUtil.customeTitle(this, getResources().getString(R.string.zh_findpwd),true);
		 
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAMS_MOBILE)) {
			phone =getIntent().getExtras().getString(PARAMS_MOBILE);
		}
		
		initProper();
		ensureUI();
	}
	
	private void initProper(){
		et_smscode = (EditText)findViewById(R.id.et_smscode);
		btn_next = (Button)findViewById(R.id.btn_next);
		btn_next.setBackgroundResource(ComApp.getInstance().appStyle.btn_sign_selector);
		
		btn_resms = (Button)findViewById(R.id.btn_resms);
		btn_resms.setBackgroundResource(ComApp.getInstance().appStyle.btn_sign_selector);
		 
	}
	
	private void ensureUI(){
		
		timer.schedule(task, 100,1000);  
		btn_next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String smscode = et_smscode.getText().toString();
				if(FuncUtil.isEmpty(smscode)){
					DialogUtil.showToast(ForgetPwdInputCodeActivity.this, "请输入验证码");
					return ;
				}
				 
				Intent intent = new Intent(ForgetPwdInputCodeActivity.this,ForgetPwdSetPwdActivity.class);
				intent.putExtra(RegisterSetPwdActivity.PARAMS_MOBILE, phone);
				intent.putExtra(RegisterSetPwdActivity.PARAMS_SMSCODE, smscode);
				startActivity(intent);
					 
				}
			});
			
		}
	
	
	Timer timer = new Timer();  
    Handler handler = new Handler(){ 
    	 int index=60;
        public void handleMessage(Message msg) {
        	if(index==0){
        		timer.cancel();
        		btn_resms.setText("重发验证码");
        		btn_resms.setEnabled(true);
        		index=60;
        	}else{
        		index--;
            	btn_resms.setText("重发验证码("+index+")");
        	}
        }  
          
    };  
   
    TimerTask task = new TimerTask(){ 
    	 
        public void run() {  
            Message message = new Message();
            message.what = 1;      
            handler.sendMessage(message);    
        }            
    };  

    /**
     * 重新发送验证码
     * @param view
     */
    public void resendCode(View view){
    	DialogUtil.showProgressDialog(ForgetPwdInputCodeActivity.this);
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){

			@Override
			public StBaseType requestApi() {
				RspResultModel rsp = ComApp.getInstance().getApi().loginSmsCode(phone,CoreContants.SMS_TYPE_1);
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(ForgetPwdInputCodeActivity.this);
				// TODO Auto-generated method stub
				RspResultModel rsp = (RspResultModel)baseType;
				if(ComUtil.checkRsp(ForgetPwdInputCodeActivity.this, rsp)){
					DialogUtil.showToast(ForgetPwdInputCodeActivity.this, rsp.getRetmsg());
					if("0".equals(rsp.getRetcode())){
						timer.schedule(task, 100,1000);
						btn_next.setEnabled(true);
					}else{
						btn_next.setEnabled(false);
					}
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
