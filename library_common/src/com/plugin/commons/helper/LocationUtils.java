package com.plugin.commons.helper;


import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.DialogUtil.OnAlertSureOnclick;


/**
 * @author zhang
 * gps定位
 */
public class LocationUtils {

	public static String city=ComApp.getInstance().appStyle.weatherShortAddr;;
	private static LocationMode tempMode = LocationMode.Hight_Accuracy;
	private static LocationClient mLocationClient;
	private static String tempcoor="gcj02";//国测局经纬度
	public static String getCity(final Activity cxt){
		mLocationClient =ComApp.getInstance().mLocationClient;
		if(!mLocationClient.isStarted()){
			InitLocation();
			DialogUtil.showConfirmAlertDialog(cxt, "是否允许定位","是否允许定位",new OnAlertSureOnclick() {
				
				@Override
				public void onAlertSureOnclick() {
					LocationUtils.startLocaton();
				}
			});
		}
		
		Log.i("city:", ComApp.getInstance().appStyle.getArea());
//		if(isEnabled(cxt)){
//			city=doWork(cxt);
//		    if(FuncUtil.isEmpty(city)){
//		    	city=ComApp.weatherShortAddr;
//		    }
//		}else{
//			DialogUtil.showToast(cxt, "请打开定位服务，并设置定位模式为准确度高的项目");
//			openLocationSetting(cxt,CoreContants.REQ_CODE_1000);
//		}
		return city; 
	}  

	private static String doWork(Activity cxt) {  
		String msg = "";  
		
		LocationManager locationManager = (LocationManager) cxt.getSystemService(Context.LOCATION_SERVICE);  
		
		Criteria criteria = new Criteria();  
		// 获得最好的定位效果  
		criteria.setAccuracy(Criteria.ACCURACY_FINE);  
		criteria.setAltitudeRequired(false);  
		criteria.setBearingRequired(false);  
		criteria.setCostAllowed(false);  
		// 使用省电模式  
		criteria.setPowerRequirement(Criteria.POWER_LOW);  
		// 获得当前的位置提供者  
		String provider = locationManager.getBestProvider(criteria, true);  
		// 获得当前的位置  
		Location location = locationManager.getLastKnownLocation(provider);  
		
		location=getLocationByGPS(cxt);
		if(location==null){
			location=getLocationByNetwork(cxt);
		}
		if(location==null){
			DialogUtil.showToast(cxt, "定位失败，无法获取准确的位置");
			return "";
		}
		Geocoder gc = new Geocoder(cxt);   
		List<Address> addresses = null;  
		try {  
		    addresses = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);  
		} catch (IOException e) {  
		    e.printStackTrace();  
		}
		if (addresses.size() > 0) {   
			msg += "AddressLine：" + addresses.get(0).getAddressLine(0)+ "\n";   
			msg += "CountryName：" + addresses.get(0).getCountryName()+ "\n";   
			msg += "Locality：" + addresses.get(0).getLocality() + "\n";   
			msg += "FeatureName：" + addresses.get(0).getFeatureName();   
		}  
		return addresses.get(0).getLocality();
	} 
	public static boolean isEnabled(Context context) {  
        LocationManager mLocationManager = (LocationManager) context  
                .getSystemService(Context.LOCATION_SERVICE);  
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  
            return true;  
        }  
        if (mLocationManager  
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {  
            return true;  
        }  
        return false;  
    }  
  
    public static void startLocationService(Context context) {  
        LocationManager mLocationManager = (LocationManager) context  
                .getSystemService(Context.LOCATION_SERVICE);  
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,  
                1000L, 10, mLocationListener);  
        mLocationManager.requestLocationUpdates(  
                LocationManager.NETWORK_PROVIDER, 1000L, 10,  
                mLocationListener);  
    }  
  
    public static void stopLocationService(Context context) {  
        LocationManager mLocationManager = (LocationManager) context  
                .getSystemService(Context.LOCATION_SERVICE);  
        mLocationManager.removeUpdates(mLocationListener);  
    }  
  
    public static Location getLocationByGPS(Context context) {  
        LocationManager mLocationManager = (LocationManager) context  
                .getSystemService(Context.LOCATION_SERVICE);  
        Location location = mLocationManager  
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);  
        return location;  
    }  
  
    public static Location getLocationByNetwork(Context context) {  
        LocationManager mLocationManager = (LocationManager) context  
                .getSystemService(Context.LOCATION_SERVICE);  
        Location location = mLocationManager  
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);  
        return location;  
    }  
  
    public static void openLocationSetting(Activity act, int requestCode) {  
        act.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),  
                requestCode);  
    }  
  
    private static final LocationListener mLocationListener = new LocationListener() {  
  
        public void onLocationChanged(Location location) {  
            // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发  
            System.out.println("onLocationChanged:" + location.getLatitude());  
        }  
  
        public void onProviderDisabled(String provider) {  
            // Provider被disable时触发此函数，比如GPS被关闭  
            System.out.println("onProviderDisabled:" + provider);  
        }  
  
        public void onProviderEnabled(String provider) {  
            // Provider被enable时触发此函数，比如GPS被打开  
            System.out.println("onProviderEnabled:" + provider);  
        }  
  
        public void onStatusChanged(String provider, int status, Bundle extras) {  
            // Provider的转态在可用、暂时不可用和无服务三个状态直接切换时触发此函数  
            System.out.println("onStatusChanged:" + status);  
        }  
    };

    public static  void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);//设置定位模式
		option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
		int span=5000;
		 
		option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
    
    public static void startLocaton(){
    	mLocationClient.start();
    }
    
    public void stopLocaton() {
		mLocationClient.stop();
	}
}  
 