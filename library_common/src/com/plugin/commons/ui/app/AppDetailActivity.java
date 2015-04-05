package com.plugin.commons.ui.app;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DialogUtil.OnAlertSureOnclick;
import com.plugin.commons.model.AppInfoModel;
import com.plugin.commons.setting.AppInfoActivity;
import com.plugin.commons.ui.base.BaseActivity;

/**
 * @author zhang
 *应用详情
 */
public class AppDetailActivity extends BaseActivity {
	FragmentTransaction fragmentTransaction;
	private String title;
	AppInfoModel appInfo;
	TextView tv_title;
	TextView tv_desc;
	ImageView im_app_icon;
	Button btn_down;
	private TextView tv_contxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_detail);
		if(this.getIntent()!=null&&this.getIntent().getExtras().containsKey(CoreContants.PARAMS_TITLE)){
			title=this.getIntent().getExtras().getString(CoreContants.PARAMS_TITLE,"应用详情");
		}
		if(this.getIntent()!=null&&this.getIntent().getExtras().containsKey(CoreContants.PARAMS_MSG)){
			appInfo=(AppInfoModel) this.getIntent().getExtras().get(CoreContants.PARAMS_MSG);
		}
		ComUtil.customeTitle(this,title,true);
		initView();
		initDisplay();
	}

	private void initDisplay() {
		tv_contxt.setText(appInfo.getInfo());
		tv_title.setText(appInfo.getName());
		ComApp.getInstance().getFinalBitmap().display(im_app_icon, appInfo.getLogo());
	}

	private void initView() {
		ComApp.getInstance().getSpUtil().setParam(appInfo.getId(),"0");
		tv_desc=(TextView) this.findViewById(R.id.tv_desc);
		tv_contxt=(TextView) this.findViewById(R.id.tv_contxt);
		tv_title=(TextView) this.findViewById(R.id.tv_title);
		btn_down=(Button) this.findViewById(R.id.btn_down);
		im_app_icon=(ImageView) this.findViewById(R.id.im_app_icon);
		if("1".equals(ComApp.getInstance().getSpUtil().getValue(appInfo.getId()))){
			btn_down.setText("下载中...");
			btn_down.setEnabled(false);
		}else{
			btn_down.setText("下载");
			btn_down.setEnabled(true);
		}
	}
	
	/**
	 * 下载app
	 * @param v
	 */
	public void download(View v){
		final int notifyId=399+Integer.parseInt(appInfo.getId());
		appInfo.setTaskId(notifyId);
		ComApp.getInstance().getSpUtil().setParam(appInfo.getId(),"1");
		DialogUtil.showConfirmAlertDialog(AppDetailActivity.this, "是否下载"+appInfo.getName(), "应用下载提示",new OnAlertSureOnclick() {
			
			@Override
			public void onAlertSureOnclick() {
				 
//				AppDetailActivity
				ComUtil.addDownLoadTask(AppDetailActivity.this, appInfo);
				DialogUtil.showToast(AppDetailActivity.this,"转到后台下载...");
				// 获取通知管理器，用于发送通知
				ComUtil.showNotification(AppDetailActivity.this,ComApp.getInstance().appStyle.yytb,null,notifyId,appInfo.getName()+"下载通知",Notification.DEFAULT_VIBRATE);
			}
		});
		ComApp.getInstance().getSpUtil().setParam(appInfo.getId(),"0");
	}
}
