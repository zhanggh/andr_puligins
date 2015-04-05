package com.plugin.commons.helper;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;

public class BaiduLocationUtil {
	
	public static void startUpLocationServer(LocationClient mLocationClient,Context cxt,BDLocationListener locationListener){
		mLocationClient = new LocationClient(cxt);
		mLocationClient.registerLocationListener(locationListener);
		initLocation(mLocationClient);
	}
	
	
	public static void initLocation(LocationClient mLocationClient){
		//启动百度定位器
		if(CoreContants.LOCATION_APP.contains(ComApp.APP_NAME)){
//        	mLocationClient =ComApp.getInstance().mLocationClient;
        	initLocationParams(mLocationClient);
        	if(!mLocationClient.isStarted()){
				mLocationClient.start();
			}
        }
	}
	
	/**
	 * 初始化定位参数
	 */
	private static void initLocationParams(LocationClient mLocationClient){
		//百度定位参数
		LocationMode tempMode = LocationMode.Hight_Accuracy;//高精度
		String tempcoor="gcj02";//高精度
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);//设置定位模式
		option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
		int span=1000*30;
		option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
}
