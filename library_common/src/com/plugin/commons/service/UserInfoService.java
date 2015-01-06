package com.plugin.commons.service;

import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.UserInfoModel;


public interface UserInfoService {
	/**
	 * 登录
	 * @Description:
	 * @param code
	 * @param pwd
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-7-25 下午5:04:11
	 */
	public RspResultModel login(String code,String pwd,String smscode);
	
	/**
	 * 获取登录用户信息
	 * @Description:
	 * @return
	 * UserInfoModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-7-25 下午5:04:47
	 */
	public UserInfoModel getLoginInfo();
	
	/**
	 * 注册
	 * @Description:
	 * @param code
	 * @param pwd
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-7-25 下午5:04:11
	 */
	public RspResultModel register(String code,String pwd,String smscode);
	
	/**
	 * 修改用户
	 * @Description:
	 * @param code
	 * @param pwd
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-7-25 下午5:04:11
	 */
	public RspResultModel modifyUser(String name,String email);
	
	/**
	 * 退出登录
	 * @Description:
	 * @return
	 * RspResultModel
	 * @exception:
	 * @author: vinci
	 * @time:2014-8-5 下午4:13:41
	 */
	public RspResultModel loginout();
	
	
}
