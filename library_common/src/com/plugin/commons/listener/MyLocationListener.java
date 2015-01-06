package com.plugin.commons.listener;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.zq.util.StCacheHelper;

/**
 * 实现定位回调监听
 */
public class MyLocationListener implements BDLocationListener {
	DingLog log = new DingLog(MyLocationListener.class);
	@Override
	public void onReceiveLocation(BDLocation location) {
		//Receive Location 
		
		if(CoreContants.APP_LNZX.equals(ComApp.APP_NAME)){
			String aname=(String) StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_AREA,"1");
			if(aname==null){
				ComUtil.doSmartWeather(location.getCity(),ComApp.getInstance(), ComApp.getInstance().appStyle.mWeatheView);
			}else if(!aname.equals(location.getCity())){
				ComUtil.doSmartWeather(location.getCity(),ComApp.getInstance(), ComApp.getInstance().appStyle.mWeatheView);
			}
		}
		//以下的debug数据
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		if (location.getLocType() == BDLocation.TypeGpsLocation){
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
			sb.append("\ndirection : ");
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append("\ngetCity : ");
			sb.append(location.getCity());
			sb.append(location.getDirection());
			ComApp.getInstance().appStyle.setArea(location.getCity());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append("\ngetCity : ");
			sb.append(location.getCity());
			//运营商信息
			sb.append("\noperationers : ");
			sb.append(location.getOperators());
			ComApp.getInstance().appStyle.setArea(location.getCity());
		}
		log.info(sb.toString());
		Log.i("BaiduLocationApiDem", sb.toString());
	}
}