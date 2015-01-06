package com.plugin.commons;

import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import cn.jpush.android.api.JPushInterface;

import com.baidu.location.LocationClient;
import com.plugin.R;
import com.plugin.commons.api.ComAppApi;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SharePreferenceUtil;
import com.plugin.commons.listener.MyLocationListener;
import com.plugin.commons.model.AppStylePropModel;
import com.plugin.commons.model.UserInfoModel;
import com.plugin.commons.service.BaoliaoService;
import com.plugin.commons.service.BaoliaoServiceImpl;
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
	
	//
	/**
	 * 图片缓存,管理器
	 */
	private FinalBitmap mFinalBitmap;

	
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
		this.appStyle.areaids=getResources().getStringArray(R.array.arr_areaids);
		//百度定位器初始化
		if(CoreContants.APP_LNZX.equals(ComApp.APP_NAME)){
			mLocationClient = new LocationClient(this.getApplicationContext());
			mMyLocationListener = new MyLocationListener();
			mLocationClient.registerLocationListener(mMyLocationListener);
		}
	}
	
	
	
	public FinalBitmap getFinalBitmap() {

		if (mFinalBitmap == null) {
			mFinalBitmap = FinalBitmap.create(this);
			//mFinalBitmap.configDiskCachePath("youwawa");
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
	
}
