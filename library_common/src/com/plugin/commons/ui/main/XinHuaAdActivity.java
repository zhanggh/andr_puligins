package com.plugin.commons.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import cn.jpush.android.api.JPushInterface;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.ImageUtils;
import com.plugin.commons.model.BaoLiaoInfoModel;
import com.plugin.commons.model.XinHuaModel;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.service.XinHuaService;
import com.plugin.commons.service.XinHuaServiceImpl;
import com.plugin.commons.view.CustomDialog;


public class XinHuaAdActivity extends Activity {
	DingLog log = new DingLog(XinHuaAdActivity.class);
	public static final String PARAM_XH = "xh";
	AnimationDrawable anim = null;
	public String mVersion = "";
	boolean isFirstIn = false;
	NewsService newsSvc = null;
	CustomDialog startUpdialog;
	ImageView im_show;
	XinHuaService xhSv;
	XinHuaModel xhModel;
	boolean oclickToWeb=false;
	String url;
	int width ;
	int height ;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup_image_activity);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAM_XH)) {
			xhModel =(XinHuaModel)getIntent().getExtras().get(PARAM_XH);
		}
		im_show=(ImageView) this.findViewById(R.id.im_default_show);
		if(xhModel!=null&&!FuncUtil.isEmpty(xhModel.getData().getImgUrl())){
			//如果上一个页面已经下载图片完毕了，则直接显示，否则，继续下载,
			//所以有可能造成第一次启动app或者第一次新华更改广告路径的时候，会有加载延迟，此时考虑默认图片设计的好看点
			log.info("图片加载:"+xhModel.getData().getImgUrl());
			ComApp.getInstance().getFinalBitmap().display(im_show,xhModel.getData().getImgUrl(),ImageUtils.drawableIdToBitmap(ComApp.getInstance(),ComApp.getInstance().appStyle.startLoading));
		}else{
			startActivity(new Intent(this,MainActivity.class));
			finish();
		}
	}

	@Override
	public void onResume() {
		JPushInterface.onResume(ComApp.getInstance());
		super.onResume();
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if(!oclickToWeb){
					startActivity(new Intent(XinHuaAdActivity.this,MainActivity.class));
					finish();	
				}
			}
		}, 2000);
	}
	
	 private Handler mHandler = new Handler() {

			public void handleMessage(Message msg) {
				synchronized (XinHuaAdActivity.this) {
					
				}
			}
	 };
	@Override
	protected void onPause() {
		JPushInterface.onPause(ComApp.getInstance());
		super.onPause();
	}
	 
	public void toAdWeb(View v){
		if(!FuncUtil.isEmpty(xhModel.getData().getHrefUrl())){
			oclickToWeb=true;
			DialogUtil.showToast(XinHuaAdActivity.this,"正在打开系统浏览器跳转页面");
			Intent intent = new Intent();       
	        intent.setAction("android.intent.action.VIEW");   
	        Uri content_url = Uri.parse(xhModel.getData().getHrefUrl());  
	        intent.setData(content_url); 
	        startActivityForResult(intent, 100);
		}
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
		startActivity(new Intent(this,MainActivity.class));
		finish();
    } 
}
