package com.plugin.commons.service;

import com.plugin.commons.model.RspResultModel;



public interface SmartWeatherService {
	
	/**
	 * 获取缓存的预报数据
	 * @Description:
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-10-14 上午9:50:20
	 */
	public RspResultModel getCacheForecast();
	
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
	public RspResultModel getForecast(String areaid);
	
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
	public RspResultModel getObserver(String areaid);
	
	/**
	 * 获取生活指数
	 * @Description:
	 * @param areaid
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-10-14 下午3:51:46
	 */
	public RspResultModel getIndex(String areaid);
	
	
	/**
	 * 根据GPS定位得到的城市名称找到天气查询的地区编号
	 * @param cityName
	 * @return
	 */
	public String getAreaidFromCity(String cityName);
}
