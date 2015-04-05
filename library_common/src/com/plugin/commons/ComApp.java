package com.plugin.commons;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.widget.RemoteViews;
import cn.jpush.android.api.JPushInterface;

import com.baidu.location.LocationClient;
import com.plugin.R;
import com.plugin.commons.api.ComAppApi;
import com.plugin.commons.helper.BaiduLocationUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SharePreferenceUtil;
import com.plugin.commons.listener.MyLocationListener;
import com.plugin.commons.model.AppInfoModel;
import com.plugin.commons.model.AppStylePropModel;
import com.plugin.commons.model.UserInfoModel;
import com.plugin.commons.service.BaoliaoService;
import com.plugin.commons.service.BaoliaoServiceImpl;
import com.plugin.commons.service.NumberService;
import com.plugin.commons.service.NumberServiceImpl;
import com.plugin.commons.service.SysNotifyService;
import com.plugin.commons.service.SysNotifyServiceImp;
import com.plugin.commons.service.UserInfoService;
import com.plugin.commons.service.UserInfoServiceImpl;
import com.zq.util.RemoteResourceManager;
import com.zq.widget.StApp;

 
 
public abstract class ComApp  extends StApp{
	DingLog log = new DingLog(ComApp.class);
	
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public ExecutorService pool = Executors.newFixedThreadPool(2);
	public NotificationManager notificationManager =null;  
	public RemoteViews notifyView;
	public Notification notification ;
	public Queue<AppInfoModel> appQue = new LinkedList<AppInfoModel>();  
	//
	/**
	 * 图片缓存,管理器
	 */
	private FinalBitmap mFinalBitmap;

	public static Timer mTimer = new Timer();// 定时器  
	
	/**
	 * app左边栏菜单
	 */
	private Map<String,Integer> menuMap;
	
	/**
	 * 网络连接,管理器
	 */
	FinalHttp mFinalHttp;
	
	ComAppApi mGlobalHttpApi;
	
	SharePreferenceUtil spUtil;
	//app样式
	public AppStylePropModel appStyle;
	
	/**
	 * 数据存储，管理器
	 */
	FinalDb mFinalDb;
	
	public boolean mIsLanuch=false;
	private UserInfoModel customer;
	UserInfoService userService;
	SysNotifyService sysNotifySv;
	BaoliaoService blService;
	NumberService numberService;
	Bitmap loadingBig;
	Bitmap loadingSmall;

	private static ComApp self;
	public Vibrator mVibrator;
	
	@Override
	public void onCreate() {
		super.onCreate();
		appStyle =new AppStylePropModel();
		userService = new UserInfoServiceImpl();
		String pkName = this.getPackageName();
		if(pkName.indexOf(".")>0){
			pkName=pkName.split("\\.")[1];	
		}
		APP_NAME = pkName;
		this.init();
		RemoteResourceManager.DEBUG = true;
		self = this;
		 
		JPushInterface.setDebugMode(CoreContants.DEBUG); 	// 设置开启日志,发布时请关闭日志
		if(this.getSpUtil().getReceiveNotic()){
	        JPushInterface.init(this);     		// 初始化 JPush
		}
		//具体每个app下面的MyApp类都必须实现的方法，用于初始化一些app样式
		initAppStyle();
		
		//百度定位器初始化
		if(CoreContants.LOCATION_APP.contains(ComApp.APP_NAME)){
			this.appStyle.areaids=getResources().getStringArray(R.array.arr_areaids);
			mMyLocationListener = new MyLocationListener();
			BaiduLocationUtil.startUpLocationServer(mLocationClient,getApplicationContext(), mMyLocationListener);
		}
	}
	
	
	
	public FinalBitmap getFinalBitmap() {

		if (mFinalBitmap == null) {
			mFinalBitmap = FinalBitmap.create(this);
			mFinalBitmap.configDiskCachePath(APP_NAME);
			mFinalBitmap.configLoadingImage(appStyle.placeholder_c);
			mFinalBitmap.configLoadfailImage(appStyle.placeholder_c);
		}
		return mFinalBitmap;
	}
	
	public Bitmap getLoadingBig(){
		if(loadingBig==null){
			loadingBig = BitmapFactory.decodeResource(this.getResources(),appStyle.placeholder_b);
		}
		return loadingBig;
		
	}
	
	public Bitmap getLoadingSmall(){
		if(loadingBig==null){
			loadingSmall = BitmapFactory.decodeResource(this.getResources(), appStyle.placeholder_b);
		}
		return loadingSmall;
		
	}

	public FinalHttp getFinalHttp() {
		if (mFinalHttp == null) {
			mFinalHttp = new FinalHttp();
			mFinalHttp.configTimeout(38000);
		}
		return mFinalHttp;
	}
	
	public ComAppApi getApi() {
		if(mGlobalHttpApi==null)
			mGlobalHttpApi=new ComAppApi();
		return mGlobalHttpApi;
	}
	
	/**
	 * 获取登录信息
	 * @Description:
	 * @return
	 * UserInfoModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-7-31 上午11:50:06
	 */
	public UserInfoModel getLoginInfo(){
		if(customer==null){
			customer = userService.getLoginInfo();
		}
		return customer;
	}
	
	public boolean isLogin(){
		UserInfoModel user = getLoginInfo();
		return user!=null;
	}
	
	public static ComApp getInstanceNoLoad() {
		return self;
	}
	public static ComApp getInstance() {
		return self;
	}

	public static void newInstance(ComApp app){
		self=app;
	}
	public SharePreferenceUtil getSpUtil() {
		if(spUtil==null){
			spUtil = new SharePreferenceUtil(this,APP_NAME);
		}
		return spUtil;
	}

	public void setSpUtil(SharePreferenceUtil spUtil) {
		this.spUtil = spUtil;
	}



	public UserInfoModel getCustomer() {
		return customer;
	}



	public void setCustomer(UserInfoModel customer) {
		this.customer = customer;
	}
	
	public BaoliaoService getBlService(){
		if(blService==null){
			blService=new BaoliaoServiceImpl();
		}
		return blService;
	}



	public SysNotifyService getSysNotifySv() {
		if(sysNotifySv==null){
			sysNotifySv=new SysNotifyServiceImp();
		}
		return sysNotifySv;
	}
	public NumberService getNumberService() {
		if(numberService==null){
			numberService=new NumberServiceImpl();
		}
		return numberService;
	}

	public Map<String,Integer> getMenuMap(){
		if(menuMap==null){
			menuMap=new HashMap<String, Integer>();
		}
		return menuMap;
	}

	/**
	 * 初始化app样式,文字描述等
	 */
	public abstract void initAppStyle();


	 
	public AppStylePropModel getAppStyle() {
		return appStyle;
	}

	public void setAppStyle(AppStylePropModel appStyle) {
		this.appStyle = appStyle;
	}
	public ExecutorService getPool() {
		return pool;
	}
	
	public NotificationManager getNotificationManager(){
		if(notificationManager==null){
			notificationManager=(NotificationManager)this.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		}
		 return notificationManager;
	}
	public RemoteViews getRemoteViews(String title,int appIcon,int process){
		 // our custom view
		if(notifyView==null){
			notifyView = new RemoteViews(this.getPackageName(), R.layout.item_notify);
		}
		notifyView.setTextViewText(R.id.tv_title, title);
		notifyView.setImageViewResource(R.id.im_icon, appIcon);
		notifyView.setProgressBar(R.id.pro_down, 100,process, false);
		return notifyView;
	}
	
	public Notification getNotification(){
		 // 定义Notification的各种属性   
		if(notification==null){
			  notification = new Notification();
		}
		return notification;
	}
}
