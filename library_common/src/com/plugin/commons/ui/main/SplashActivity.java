package com.plugin.commons.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import cn.jpush.android.api.JPushInterface;

 
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
 
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.XinHuaModel;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.service.XinHuaService;
import com.plugin.commons.service.XinHuaServiceImpl;
import com.zq.types.StBaseType;

/**
 * 启动程序的加载页
 * 
 * @author zhoukaiban
 * 
 */
public class SplashActivity extends Activity {
	DingLog log = new DingLog(SplashActivity.class);
	
	AnimationDrawable anim = null;
	public String mVersion = "";
	ImageView iv_qidongye;
	ImageView im_loading;
	boolean isFirstIn = false;
	NewsService newsSvc = null;
	XinHuaModel xhModel;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		if(CoreContants.APP_LMXF.equals(ComApp.APP_NAME)){//鹿鸣西丰进入个性化splash
			Intent intent = new Intent(SplashActivity.this,SpecialSplashActivity.class);
			intent.putExtra(XinHuaAdActivity.PARAM_XH, xhModel);
			startActivity(intent);
			finish();
		}
		
		newsSvc = new NewsServiceImpl();
		iv_qidongye=(ImageView) this.findViewById(R.id.im_qidongye);
		im_loading=(ImageView) this.findViewById(R.id.im_loading);
		iv_qidongye.setImageResource(ComApp.getInstance().appStyle.iv_qidongye);
	}

	@Override
	public void onResume() {
		JPushInterface.onResume(ComApp.getInstance());
		super.onResume();
		gotoNewActivity();
	}
	
	private void doInitMenu(){
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){

			@Override
			public StBaseType requestApi() {
				// TODO Auto-generated method stub
				RspResultModel rsp = newsSvc.initNewsType();
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(SplashActivity.this);
				// TODO Auto-generated method stub
				RspResultModel rsp = (RspResultModel)baseType;
				//如果需要进入广告页面，并且已经获取到新华图片路径
				if(isAdd()){
					if(xhModel!=null){
						Intent intent = new Intent(SplashActivity.this,XinHuaAdActivity.class);
						intent.putExtra(XinHuaAdActivity.PARAM_XH, xhModel);
						startActivity(intent);
					}
					else{
						if(CoreContants.APP_LNZX.equals(ComApp.APP_NAME)){
							startActivity(new Intent(SplashActivity.this,MainWithoutRightSideActivity.class));
						}else{
							startActivity(new Intent(SplashActivity.this,MainActivity.class));
						}
					}
					
				}else{
					Intent intent = new Intent(SplashActivity.this,LoginStatusActivity.class);
					intent.putExtra(XinHuaAdActivity.PARAM_XH, xhModel);
					startActivity(intent);
				}
				finish();
			}
			
		});
	}
	
	/**
	 * 是否需要进入广告
	 * @return
	 */
	private boolean isAdd(){
		return !CoreContants.APP_LNZX.equals(ComApp.APP_NAME);
	}
	
	 private Handler mHandler = new Handler() {

			public void handleMessage(Message msg) {
				synchronized (SplashActivity.this) {
					
				}
			}
	 };
	@Override
	protected void onPause() {
		JPushInterface.onPause(ComApp.getInstance());
		super.onPause();
	}
	 
	
	private void doRequest(){
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				SituoHttpAjax.ajax(new SituoAjaxCallBack(){

					@Override
					public StBaseType requestApi() {
						// TODO Auto-generated method stub
						//先获取新华广告信息
						RspResultModel rsp = new RspResultModel();
						XinHuaService xhSvc = new XinHuaServiceImpl();
						DisplayMetrics dm = new DisplayMetrics();
						getWindowManager().getDefaultDisplay().getMetrics(dm);
						int width = dm.widthPixels;//宽度
						int height = dm.heightPixels ;//高度
						rsp = xhSvc.getXHImg(width, height, ComApp.getInstance().appStyle.appid,ComApp.getInstance().appStyle.xinhuaKey);
						
						
						//测试
						RspResultModel rsp2 =xhSvc.getNewRadioTypeList();
						
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						// TODO Auto-generated method stub
						try{
							RspResultModel rsp = (RspResultModel)baseType;
							if(rsp!=null){
								log.info(rsp.getRetcode()+";"+rsp.getRetmsg());
							}
							if(ComUtil.checkRsp(SplashActivity.this, rsp)){
								xhModel = rsp.getXhModel();
								//开始下载图片--afinal如果硬盘缓存存在，不会重新下载，否则会下载图片 
								//因为图片下载时间比较长，所以希望提前下载
								ComApp.getInstance().getFinalBitmap().display(im_loading,xhModel.getData().getImgUrl());
							}
							//此时初始化菜单，图片在另一个线程进行下载
							doInitMenu();
						}
						catch(Exception e){//如果异常则不进入广告页面
							e.printStackTrace();
							doInitMenu();
						}
					}
				});
			}
		}, 800);
	}
	
	private void gotoNewActivity(){
		if(ComUtil.isNetworkAvailable(SplashActivity.this)){
			doRequest();
        }else{
        	mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					DialogUtil.showToast(SplashActivity.this, "网络无法正常连接，请检查网络");
					if(isAdd()){
						if(CoreContants.APP_LNZX.equals(ComApp.APP_NAME)){
							startActivity(new Intent(SplashActivity.this,MainWithoutRightSideActivity.class));
						}else{
							startActivity(new Intent(SplashActivity.this,MainActivity.class));
						}
					}else{
						Intent intent = new Intent(SplashActivity.this,LoginStatusActivity.class);
						startActivity(intent);
					}
					finish();
				}
        		
        	}, 1500);
        }
	}
}

