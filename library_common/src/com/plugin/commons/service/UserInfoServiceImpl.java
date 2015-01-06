package com.plugin.commons.service;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.UserInfoModel;
import com.zq.util.StCacheHelper;

public class UserInfoServiceImpl implements UserInfoService{
	DingLog log = new DingLog(UserInfoServiceImpl.class);

	@Override
	public RspResultModel login(String code, String pwd,String smscode) {
		// TODO Auto-generated method stub
		RspResultModel rsp = null;
		rsp = ComApp.getInstance().getApi().login(code, pwd);
		if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
			rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"登录失败":rsp.getRetmsg());
		}
		else{
			log.info("登录成功，保存用户信息");
			StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_USER,"1",rsp.getUser());
			ComApp.getInstance().setCustomer(rsp.getUser());
		}
		return rsp;
	}

	@Override
	public UserInfoModel getLoginInfo() {
		// TODO Auto-generated method stub
		return (UserInfoModel)StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_USER, "1");
	}
	
	public RspResultModel register(String code,String pwd,String smscode){
		RspResultModel rsp  = null;
		rsp = ComApp.getInstance().getApi().register(code,pwd, code, smscode);
		if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
			rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"注册失败":rsp.getRetmsg());
		}
		else{
			StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_USER,"1",rsp.getUser());
			ComApp.getInstance().setCustomer(rsp.getUser());
			
		}
		return rsp;
		
	}
	
	public RspResultModel modifyUser(String name,String email){
		RspResultModel rsp  = null;
		rsp = ComApp.getInstance().getApi().modifyinfo(name, email);
		if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
			//rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"修改失败":rsp.getRetmsg());
		}
		else{
			UserInfoModel user = ComApp.getInstance().getLoginInfo();
			user.setEmail(email);
			user.setUsername(name);
			StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_USER,"1",user);
			ComApp.getInstance().setCustomer(user);
		}
		return rsp;
	}
	
	private RspResultModel buildRsp(String retcode,String retmsg){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode(retcode);
		rsp.setRetmsg(retmsg);
		return rsp;
	}
	
	public RspResultModel loginout(){
		RspResultModel rsp = new RspResultModel();
		StCacheHelper.deleteCacheObj(ComApp.getInstance(), CoreContants.CACHE_USER,"1");
		ComApp.getInstance().setCustomer(null);
		rsp.setRetcode(CoreContants.RETCODE_SUCC);
		rsp.setRetmsg("退出成功");
		return rsp;
	}

	/**
	 * 找回密码
	 * @param code
	 * @param smscode
	 * @return
	 */
	public RspResultModel findpwd(String phone,String password,String smscode){
		 
		RspResultModel rsp  = null;
		rsp = ComApp.getInstance().getApi().findpwd(phone,smscode,password);
		if(rsp==null||!"0".equals(CoreContants.RETCODE_SUCC)){
			rsp = buildRsp(CoreContants.RETCODE_ERR,rsp==null?"找回密码失败":rsp.getRetmsg());
		} 
		return rsp;
	}
}
