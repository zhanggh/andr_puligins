package com.plugin.commons.ui.main;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import cn.jpush.android.api.JPushInterface;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.XinHuaModel;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.service.XinHuaService;
import com.plugin.commons.service.XinHuaServiceImpl;
import com.plugin.commons.widget.FixedSpeedScroller;
import com.zq.types.StBaseType;

public class SpecialSplashActivity  extends Activity {
	
	private ViewPager mViewPager;
	private int mMyDuration = 30;          //持续时间 
	private FixedSpeedScroller mScroller;  
	private ImageView mPage0;
	private ImageView mPage1;
	private Scroller scoller;
	private Field mField=null;
	private DingLog log = new DingLog(SpecialSplashActivity.class);
	private int currIndex = 0;
	private boolean isFirstIn = false;
	private NewsService newsSvc = null;
	private View view1 =null;
	private View view2 =null;
	private View view3 =null;
	private int index=0;
	private XinHuaModel xhModel;
	private ImageView im;
    private ImageView im_loading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_viewpager);
        mViewPager = (ViewPager)findViewById(R.id.whatsnew_viewpager);        
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        im_loading=(ImageView) this.findViewById(R.id.im_loading);
        newsSvc = new NewsServiceImpl();
        mPage0 = (ImageView)findViewById(R.id.page0);
        mPage1 = (ImageView)findViewById(R.id.page1);
      //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        view1 = mLi.inflate(R.layout.view_page0, null);
        view2 = mLi.inflate(R.layout.view_page1, null);
        view3 = mLi.inflate(R.layout.view_page2, null);
        view1.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.qidongye_a));
        view2.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.qidongye_b));
      //每个页面的view数据
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        
        view2.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(currIndex==1){
					try {
						if(mField!=null)
							mField.set(mViewPager, scoller); 
						isScoll=true;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return false;
			}
		});
        
        im=(ImageView) view2.findViewById(R.id.im_lmxf);
        //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}
			
			
			
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setCurrentItem(0,true);
		doRequest();
    }    
    

    public class MyOnPageChangeListener implements OnPageChangeListener {
//    	int index=0;
		@Override
		public void onPageSelected(int arg0) {
			try {
				switch (arg0) {
				case 0:
					mField.set(mViewPager, scoller); 
					flag=false;
					mPage0.setImageDrawable(getResources().getDrawable(R.drawable.circle_a));
					mPage1.setImageDrawable(getResources().getDrawable(R.drawable.circle_b));
					uiHandler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							Message msg=new Message();
							msg.arg1=1;
							uiHandler.sendMessage(msg);// 向Handler发送消息    
						}
					}, 3000);
					
					break;
				case 1:
					mPage1.setImageDrawable(getResources().getDrawable(R.drawable.circle_a));
					mPage0.setImageDrawable(getResources().getDrawable(R.drawable.circle_b));
					if(view2!=null){
						// 当Activity显示出来后，每两秒钟切换一次图片显示
						timerTask();
						uiHandler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								if(!isScoll){
									Message msg=new Message();
									msg.arg1=2;
				    				uiHandler.sendMessage(msg);// 向Handler发送消息   
								}
							}
						}, 3000);
						flag=true;
					}
					break;
				case 2:
					uiHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							if(flag){
								Intent intent = new Intent (SpecialSplashActivity.this,XinHuaAdActivity.class);			
								intent.putExtra(XinHuaAdActivity.PARAM_XH, xhModel);
								startActivity(intent);
								SpecialSplashActivity.this.finish();
							}
							flag=false;
						}
					}, 600);
					break;
				}
				currIndex = arg0;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//animation.setFillAfter(true);// True:图片停在动画结束位置
			//animation.setDuration(300);
			//mPageImg.startAnimation(animation);
			
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public void onResume() {
		JPushInterface.onResume(ComApp.getInstance());
		super.onResume();
		if(ComApp.getInstance().isLogin()){
			XHSDKUtil.addXHBehavior(SpecialSplashActivity.this,ComApp.getInstance().getLoginInfo().getUserid(), XHConstants.XHTOPIC_START,ComApp.getInstance().getLoginInfo().getUserid() +" start app success");
		}
		else{
			XHSDKUtil.addXHBehavior(SpecialSplashActivity.this,"start app", XHConstants.XHTOPIC_START,"start app success");
		}
	}

	@Override
	protected void onPause() {
		JPushInterface.onPause(ComApp.getInstance());
		super.onPause();
		if(ComApp.mTimer!=null)
			ComApp.mTimer.cancel();
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
				DialogUtil.closeProgress(SpecialSplashActivity.this);
				// TODO Auto-generated method stub
				RspResultModel rsp = (RspResultModel)baseType;
				uiHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						if(currIndex!=1){
							Message msg=new Message();
							msg.arg1=1;
							uiHandler.sendMessage(msg);// 向Handler发送消息    
						}
					}
				}, 1600);
			}
			
		});
	}
	
	
	
	 @Override
	public boolean onTouchEvent(MotionEvent event) {
		if(currIndex==1){
			isScoll=true;
		}
		return super.onTouchEvent(event);
	}



	private Handler mHandler = new Handler() {

			public void handleMessage(Message msg) {
				synchronized (SpecialSplashActivity.this) {
				}
			}
	 };
	 
	 
	 
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
							if("0".equals(rsp.getRetcode())){
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
		}, 200);
	}
   
	 /** 
     * 消息处理器的应用 
     */  
    private Handler uiHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
        	if(msg.arg1==10){
        		 index++;
	   			 log.debug("*************************index:"+index+"***********************");
	   			 if(index%5==1){
	   				 im.setBackgroundDrawable(ComApp.getInstance().getResources().getDrawable(R.drawable.lmxf_1));
	   			 }else
	   			 if(index%5==2){
	   				 im.setBackgroundDrawable(ComApp.getInstance().getResources().getDrawable(R.drawable.lmxf_2));
	   			 }else
	   			 if(index%5==3){
	   				 im.setBackgroundDrawable(ComApp.getInstance().getResources().getDrawable(R.drawable.lmxf_3));
	   			 }else
	   			 if(index%5==4){
	   				 im.setBackgroundDrawable(ComApp.getInstance().getResources().getDrawable(R.drawable.lmxf_4));
	   			 }else
	   			 if(index%5==0){
	   				 im.setBackgroundDrawable(ComApp.getInstance().getResources().getDrawable(R.drawable.lmxf_5));
	   			 } 
        	}else{
        		int ret=msg.arg1;
				 try {               
					   mField = ViewPager.class.getDeclaredField("mScroller");               
					   mField.setAccessible(true);     
				    //<span style="color:#ff0000;">设置加速度 ，通过改变FixedSpeedScroller这个类中的mDuration来改变动画时间（如mScroller.setmDuration(mMyDuration);）   
					   scoller=(Scroller) mField.get(mViewPager);
					   mScroller = new FixedSpeedScroller(mViewPager.getContext(), new AccelerateInterpolator());          
					   mMyDuration = 400;  
					   mScroller.setmDuration(mMyDuration); 
					   mField.set(mViewPager, mScroller);   
					   if(currIndex!=ret){
						   mViewPager.setCurrentItem(ret,true); 
					   }
				 } catch (Exception e) {           
				      e.printStackTrace();  
				 }
				 
        	}
        }
        
    };
 
    public boolean flag=false;
    public boolean isScoll=false;
    
    private void timerTask() {  
        //创建定时线程执行更新任务  
        try {
			ComApp.mTimer.schedule(new TimerTask() {  
			    @Override  
			    public void run() {
			    	log.debug("************mTimer***************");
			    	if(flag){
			    		Message msg=new Message();
			    		msg.arg1=10;
			    		uiHandler.sendMessage(msg);// 向Handler发送消息  
			    	}
			    }  
			}, 100, 500);// 定时任务  
		} catch (Exception e) {
			log.debug("mTimer 已经取消");
			e.printStackTrace();
		}
    }  
}