package com.plugin.commons.service;

import java.util.Date;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.RspResultModel;



public class SmartWeatherServiceImpl implements SmartWeatherService{
	public static RspResultModel _smartForecastData = null;
	/**
	 * 获取缓存的预报数据
	 * @Description:
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-10-14 上午9:50:20
	 */
	public RspResultModel getCacheForecast(){
		if(_smartForecastData!=null){
			String date = _smartForecastData.getF().getF0();
			if(!FuncUtil.isEmpty(date)){//如果是今天的，则直接返回
				String today = FuncUtil.formatTime(new Date(), "yyyyMMdd");
				if(today.equals(date.substring(0, 8))){
					return _smartForecastData;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取服务器预报
	 * @Description:
	 * @param areaid
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-10-14 上午9:51:25
	 */
	public RspResultModel getForecast(String areaid){
		String date = FuncUtil.formatTime(new Date(), "yyyyMMddHHmm");
		RspResultModel rsp = ComApp.getInstance().getApi().smartWeather(areaid, "forecast3d", date);
		if(ComUtil.checkRsp(ComApp.getInstance(), rsp,false)){
			_smartForecastData = rsp;
		}
		return rsp;
		
	}
	
	/**
	 * 获取实况
	 * @Description:
	 * @param areaid
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-10-14 上午9:51:40
	 */
	public RspResultModel getObserver(String areaid){
		String date = FuncUtil.formatTime(new Date(), "yyyyMMddHHmm");
		RspResultModel rsp = ComApp.getInstance().getApi().smartWeather(areaid, "observe", date);
		return rsp;
	}
	
	/**
	 * 获取实况
	 * @Description:
	 * @param areaid
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-10-14 上午9:51:40
	 */
	public RspResultModel getIndex(String areaid){
		String date = FuncUtil.formatTime(new Date(), "yyyyMMddHHmm");
		RspResultModel rsp = ComApp.getInstance().getApi().smartWeather(areaid, "index", date);
		return rsp;
	}

	@Override
	public String getAreaidFromCity(String cityName) {
		String[] areaids=ComApp.getInstance().appStyle.getAreaids();
		String[] areainf;
		if(areaids!=null&&areaids.length!=0){
			for(String area:areaids){
				areainf=area.split(",");
				if(cityName!=null){
					if(cityName.contains(areainf[1])){
						return areainf[0];
					}
				}else{
					return ComApp.getInstance().appStyle.weatherAreaid;
				}
			}
		}
		return ComApp.getInstance().appStyle.weatherAreaid;
	}
	
	
}
