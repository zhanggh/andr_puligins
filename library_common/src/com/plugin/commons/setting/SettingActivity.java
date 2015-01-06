package com.plugin.commons.setting;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.SharePreferenceUtil;
import com.plugin.commons.helper.UpdateManager;
import com.plugin.commons.model.DialogObj;
import com.plugin.commons.ui.base.FqaActivity;
import com.plugin.commons.view.DialogRecmdSelect;
import com.plugin.commons.widget.WiperSwitch;
import com.plugin.commons.widget.WiperSwitch.OnChangedListener;
import com.zq.util.StCacheHelper;

public class SettingActivity  extends Activity  implements CompoundButton.OnCheckedChangeListener {
	
	TextView tv_cache_size;
	ImageView im_set_logo;
	WiperSwitch switch_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ComUtil.customeTitle(this, "设置",true);
		initViews();
	}
	
	
	private void initViews(){
		
		im_set_logo= (ImageView) this.findViewById(R.id.im_set_logo);
		im_set_logo.setImageResource(ComApp.getInstance().appStyle.im_set_logo);
		
		
		tv_cache_size= (TextView) this.findViewById(R.id.tv_cache_size);
		switch_btn= (WiperSwitch) this.findViewById(R.id.switch_btn);
		switch_btn.init(ComApp.getInstance().appStyle.switch_a_1,ComApp.getInstance().appStyle.switch_b_1,ComApp.getInstance().appStyle.switch_c);
		//tv_cache_size.setText("11.4M");
		boolean status=ComApp.getInstance().getSpUtil().getReceiveNotic();
		switch_btn.setNowStatus(status);
		switch_btn.setOnChangedListener(new OnChangedListener() {
			
			@Override
			public void OnChanged(WiperSwitch wiperSwitch, boolean checkState) {
				// TODO Auto-generated method stub
				if(checkState){
					DialogUtil.showToast(SettingActivity.this, "开关已打开");	
					//接收jpush推送消息
					if(JPushInterface.isPushStopped(ComApp.getInstance())){
						JPushInterface.resumePush(ComApp.getInstance()) ;
					}
					ComApp.getInstance().getSpUtil().setReceiveNotic(true);
				}else{
					DialogUtil.showToast(SettingActivity.this, "开关已关闭");	
					ComApp.getInstance().getSpUtil().setReceiveNotic(false);
					JPushInterface.stopPush(ComApp.getInstance());
				}
			}
		});
	}
	
	
	/**
	 * 清除缓存
	 * @param v
	 */
	public void clearcache(View v){
		//tv_cache_size.setText("0M");
		ComApp.getInstance().getFinalBitmap().clearCache();
//		StCacheHelper.deleteCacheObj(SettingActivity.this, subtype,"1");
		ComApp.getInstance().getRemoteResourceManager().clear();
		ComUtil.clearAppCache(ComApp.getInstance());
		DialogUtil.showToast(SettingActivity.this, "缓存清除成功");
		
	}
	/**
	 * 推荐人
	 * @param v
	 */
	public void referrer(View v){
		Intent intent = new Intent(this,ReferrerActivity.class);
		this.startActivity(intent);
		
	}
	/**
	 * 推荐给朋友
	 * @param v
	 */
	public void ref_friend(View v){
//		DialogUtil.showToast(SettingActivity.this, "功能开发中");
		DialogRecmdSelect dlg = new DialogRecmdSelect(SettingActivity.this,new DialogRecmdSelect.PickDialogcallback() {
			
			@Override
			public void onItemSelect(DialogObj selectItem) {
				// TODO Auto-generated method stub
//				mSelectItem = selectItem;
//				tv_org.setText(mSelectItem.getName());
			}
		},null,null);
		dlg.show();
	}
	/**
	 * 新手引导
	 * @param v
	 */
	public void user_guide(View v){
		DialogUtil.showToast(SettingActivity.this, "功能开发中");
	}
	/**
	 * 常见问题
	 * @param v
	 */
	public void sys_faq(View v){
		Intent intent = new Intent(this,FqaActivity.class);
		this.startActivity(intent);
	}
	/**
	 * 消息推送
	 * @param v
	 */
	public void msg_send(View v){
		
	}
	/**
	 * 跳转到建议视图
	 * @param v
	 */
	public void suggest(View v){
		Intent intent = new Intent(this,SuggestActivity.class);
		this.startActivity(intent);
		
	}
	/**
	 * 检测版本
	 * @param v
	 */
	public void test_version(View v){
//		DialogUtil.showToast(SettingActivity.this, "目前已是最新版本");
		UpdateManager.getUpdateManager().checkAppUpdate(SettingActivity.this,
				true);
	}
	/**
	 * 智慧浑南
	 * @param v
	 */
	public void toAppInfo(View v){
		Intent intent = new Intent(this,AppInfoActivity.class);
		this.startActivity(intent);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
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
