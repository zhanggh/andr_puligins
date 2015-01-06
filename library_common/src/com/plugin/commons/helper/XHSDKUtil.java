package com.plugin.commons.helper;

import java.util.HashMap;
import java.util.Map;

import org.xinhua.analytics.analytics.AnalyticsAgent;
import org.xinhua.analytics.analytics.BeHaviorInfo;

import android.content.Context;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.model.UserInfoModel;

public class XHSDKUtil {
	static DingLog log = new DingLog(XHSDKUtil.class);
	public static Map<String,String> XH_SERVICE_MAP = new HashMap<String,String>();
	/**
	 * 新华总社用户行为采集
	 * @Description:
	 * @param context
	 * @param id id  例如新闻ID ，用户ID
	 * @param type 类型 参考XHConstants 例如点击新闻，登录
	 * @param param 值 例如新闻id，用户id
	 * void
	 * @exception:
	 * @author: vinci
	 * @time:2014-10-16 上午11:58:20
	 */
	public static void addXHBehavior(Context context,String id,String type,String param){
		BeHaviorInfo bi = new BeHaviorInfo();
		bi.setOperateObjID(getObjId(id,type));
		bi.setOperateType(type);
		bi.setServiceParm(param);
		AnalyticsAgent.setEvent(context, bi);
	}
	
	public static void addMainBehavior(Context context){
		AnalyticsAgent.startWithAppKey(context);//新华统计
		AnalyticsAgent.setDebugMode(CoreContants.DEBUG);
		UserInfoModel user = ComApp.getInstance().getLoginInfo();
		String userid = "未知用户";
		if(user!=null){
			AnalyticsAgent.setUserId(context, user.getPhone());
			AnalyticsAgent.setUserName(context, user.getUsername());
			userid = user.getUserid();
		}
		String isInstall = ComApp.getInstance().getSpUtil().getValue("isInstall");
		if(!"1".equals(isInstall)){//如果是第一次启动，代表第一次安装，上传014
			ComApp.getInstance().getSpUtil().setParam("isInstall", "1");
			XHSDKUtil.addXHBehavior(context, "install", XHConstants.XHTOPIC_INSTALL, "install");
		}
		//启动app，上传024
	 	XHSDKUtil.addXHBehavior(context,userid, XHConstants.XHTOPIC_START,"start app success");
	 	
	}
	
	/**
	 * 获取服务id
	 * @param id
	 * @param type
	 * @return
	 */
	private static String getObjId(String id,String type){
		if(XHConstants.XHTOPIC_MENUCLICK.equals(type)){//操作类型是点击栏目，则需要填入服务id
			if(XH_SERVICE_MAP.isEmpty()){
//				XH_SERVICE_MAP.put(CoreContants.MENU_CODE_FOOD, "01001");
//				XH_SERVICE_MAP.put(CoreContants.MENU_CODE_TRIP, "01002");
//				XH_SERVICE_MAP.put(CoreContants.MENU_CODE_LETTER, "02");
//				XH_SERVICE_MAP.put(CoreContants.MENU_CODE_SHOP, "01009");
//				XH_SERVICE_MAP.put(CoreContants.MENU_CODE_BIANMING, "02008");
			}
			if(XH_SERVICE_MAP.containsKey(id)){
				id = XH_SERVICE_MAP.get(id);
			}
		}
		return id;
			
	}
	
	
	
	
	/**
	 * 
	 * 
	 * 01	60	便民生活服务	2014-10-13 09:17:18
	01001	60	美食（吃）	2014-10-13 09:17:18
	01002	60	旅游（玩）	2014-10-13 09:17:18
	01003	60	住宿（住）	2014-10-13 09:17:18
	01004	60	找银行	2014-10-13 09:17:18
	01005	60	公交信息（行）	2014-10-13 09:17:18
	01006	60	自行车租用	2014-10-13 09:17:18
	01007	60	加油站	2014-10-13 09:17:18
	01008	60	天气预报	2014-10-13 09:17:18
	01009	60	团购	2014-10-13 09:17:18
	01010	60	打折	2014-10-13 09:17:18
	01011	60	优惠券	2014-10-13 09:17:18
	01012	60	打车	2014-10-13 09:17:18
	01013	60	彩票	2014-10-13 09:17:18
	01014	60	保险	2014-10-13 09:17:18
	01015	60	星座	2014-10-13 09:17:18
	01016	60	求职招聘	2014-10-13 09:17:18
	01017	60	网页内容搜索	2014-10-13 09:17:18
	01018	60	ATM机位置	2014-10-13 09:17:18
	01019	60	车辆交易信息	2014-10-13 09:17:18
	01020	60	房屋租赁信息	2014-10-13 09:17:18
	01999	60	其它便民生活服务	2014-10-13 09:17:18
	02	60	便民政务服务	2014-10-13 09:17:18
	02001	60	公积金查询	2014-10-13 09:17:18
	02002	60	路况查询	2014-10-13 09:17:18
	02003	60	生活缴费	2014-10-13 09:17:18
	02004	60	交通查询	2014-10-13 09:17:18
	02005	60	违章查询	2014-10-13 09:17:18
	02006	60	热线电话	2014-10-13 09:17:18
	02007	60	在线挂号	2014-10-13 09:17:18
        02008	60	办事指南	2014-10-13 09:17:18
	02999	60	其它便民政务服务	2014-10-13 09:17:18
	 */
}
