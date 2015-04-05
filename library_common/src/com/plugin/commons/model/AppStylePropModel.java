package com.plugin.commons.model;

import java.io.Serializable;

import android.view.View;
import android.widget.ProgressBar;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.FuncUtil;
import com.zq.types.StBaseType;
import com.zq.util.StCacheHelper;

/**
 * @author zhang
 *	app样式以及属性
 */
public class AppStylePropModel implements StBaseType,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1445950233071296000L;

	
	public ProgressBar proc_loading;
	
	
	//类
	public Class  MyFragmentActivity;
	/**
	 * 启动页面加载时显示的默认图片
	 */
	public  int startLoading;
	
	/**
	 * 设置-消息推送开关图片
	 */
	public  int switch_a_1;
	public  int switch_b_1;
	public  int switch_c;
	
	public View mWeatheView;
	
	/**
	 * 加载网络图片时的默认图片
	 */
	public  int placeholder_a;
	public  int placeholder_b;
	public  int placeholder_c;
	
	public  int home_btn_selector;
	public  int setting_rec_qq_selector;
	public  int setting_rec_weibo_selector;
	public  int setting_rec_weixin_selector;
	public  int btn_share_selector;
	public  int btn_my_selector;
	public  int btn_fav_selector;
	public  int btn_dialogsure_selector;//意见反馈的提交按键 样式
	public  int btn_comment_sure_selector;
	public  int btn_dialogcancel_selector;
	public  int btn_comsure_selector;
	public  int btn_back_selector;
	public  int btn_menu_selector;
	public  int my_btn_normal;
	public  int complain_discuss_btn_normal;
	public  int sure_bnt_normal;
	public  int jpushlogo;
	public  int yytb;
	public  int btn_colection_selector;
	public  int btn_comment_selector;
	public  int im_setting_version_logo;//gywm_logo
	public  int qidongye_a; 
	public  int qidongye_b; 
	/**
	 * 拍客播客图标
	 */
	public int upload_photo_right_selector;
	public int boke_btn_selector;
	public int like_btn_selector;
	public int paike_btn_selector;
	public int upload_photo_btn_selector;
	public int pinglun_btn_selector;
	public int like_a_btn_selector;
	public int eyes_btn_selector;
	/**
	 * 设置-关于我们的icon
	 */
	public  int im_set_logo;// 
	/**
	 * 启动页面
	 */
	public  int iv_qidongye;//
	/**
	 * app左边菜单的背景图
	 */
	public  int background_left;// 
	/**
	 * app右边菜单的背景图
	 */
	public  int background_right;// 
	/**
	 * 登陆界面按键的样式
	 */
	public  int btn_sign_selector;// 
	/**
	 * 注册按键样式
	 */
	public  int btn_registration_selector;// 
	/**
	 * next按键背景
	 */
	public  int btn_again_selector;// 
	
	//信件的样式
	public  int btn_petition_selector;
	public  int font_grey_selector;
	public  int btn_pic_selector;
	public  int btn_photo_selector;
	public  int btn_ask_selector;
	
	//投票
	public  int btn_inves_share_selector;
	public  int investigate_share_btn_press;
	
	//问政
	public  int downward_btn_normal;
	public  int complain_video;
	public  int btn_video_selector;
	public  int font_blue_selector;
	public  int hotspot_btn_normal;
	public  int trends_btn_normal;
	public  int institution_btn_normal;
	public  int trends_btn_press;
	public  int institution_btn_press;
	public  int hotspot_btn_press;
	public  int my_btn_press;
	public  int tuijian;
	
	
	//报料
	public  int btn_baoliao_left_selector;
	public  int btn_baoliao_right_selector;
	public  int my_newspaper_btn_press;
	public  int net_newspaper_btn_normal;
	public  int net_newspaper_btn_press;
	public  int my_newspaper_btn_normal;
	//color
	/**
	 * 标题字体颜色
	 */
	public  int menu_title_color;//左右两边菜单的字体颜色
	public  int title_text_color;
	public  int select_tap_title_text_color;
	public  int unselect_tap_title_text_color;
	public  int tv_forgetpwd_color;
	public  int backgroup_reft_color;
	public  int backgroup_right_color;
	/**
	 * 标题栏的背景颜色
	 */
	public  int title_bg_color;
	
	/**
	 * 初始化字符串
	 * 
	 */
	public  String isImgbackgFormenu;// 如果左右菜单的背景是图片，则填true，如果是普通颜色值，则填false ，并且此时在color文件中要填写具体的颜色值
	public  String appid;// 
	public  String weatherAreaid;// 天气地区编号
	public  String needUserGuid;// 是否需要新手指引
	public  String xinhuaKey;// 新华发布页面的key
	public  String zh_usertips;//和好友一起分享xxx
	public  String weatherShortAddr;//天气地址  如：沈阳
	public  String tv_setting_version_tile;//app信息-标题
	public  String tv_setting_version_sponsor_tx;//app信息-主办单位
	public  String tv_setting_version_contact_phone_tx;//app信息-联系电话
	public  String tv_setting_version_contact_phone_tx2;//app信息-联系电话
	public  String tv_setting_version_contact_fax_tx;//app信息-传真
	public  String tv_setting_version_contact_addr_tx;////app信息-地址
	public  String tv_setting_version_contact_website_tx;////app信息-网站
	public String[] areaids;
	public String area;
	public boolean needScaner=false;//是否需要扫一扫
	public boolean recommend=false;//是否需要应用推荐模块
	public boolean homeOnButtom=false;//首页菜单显示在底部右下角？
	
	
	/**
	 * 初始化菜单
	 */
	public  int nav_drawer_items;
	public  int nav_code_items;
	public  int nav_drawer_icons;
	public  int getStartLoading() {
		return startLoading;
	}
	public  void setStartLoading(int startLoading) {
		this.startLoading = startLoading;
	}
	public  int getSwitch_a_1() {
		return switch_a_1;
	}
	public  void setSwitch_a_1(int switch_a_1) {
		this.switch_a_1 = switch_a_1;
	}
	public  int getSwitch_b_1() {
		return switch_b_1;
	}
	public  void setSwitch_b_1(int switch_b_1) {
		this.switch_b_1 = switch_b_1;
	}
	public  int getSwitch_c() {
		return switch_c;
	}
	public  void setSwitch_c(int switch_c) {
		this.switch_c = switch_c;
	}
	public View getmWeatheView() {
		return mWeatheView;
	}
	public void setmWeatheView(View mWeatheView) {
		this.mWeatheView = mWeatheView;
	}
	public  int getPlaceholder_a() {
		return placeholder_a;
	}
	public  void setPlaceholder_a(int placeholder_a) {
		this.placeholder_a = placeholder_a;
	}
	public  int getPlaceholder_b() {
		return placeholder_b;
	}
	public  void setPlaceholder_b(int placeholder_b) {
		this.placeholder_b = placeholder_b;
	}
	public  int getPlaceholder_c() {
		return placeholder_c;
	}
	public  void setPlaceholder_c(int placeholder_c) {
		this.placeholder_c = placeholder_c;
	}
	public  int getHome_btn_selector() {
		return home_btn_selector;
	}
	public  void setHome_btn_selector(int home_btn_selector) {
		this.home_btn_selector = home_btn_selector;
	}
	public  int getSetting_rec_qq_selector() {
		return setting_rec_qq_selector;
	}
	public  void setSetting_rec_qq_selector(int setting_rec_qq_selector) {
		this.setting_rec_qq_selector = setting_rec_qq_selector;
	}
	public  int getSetting_rec_weibo_selector() {
		return setting_rec_weibo_selector;
	}
	public  void setSetting_rec_weibo_selector(int setting_rec_weibo_selector) {
		this.setting_rec_weibo_selector = setting_rec_weibo_selector;
	}
	public  int getSetting_rec_weixin_selector() {
		return setting_rec_weixin_selector;
	}
	public  void setSetting_rec_weixin_selector(
			int setting_rec_weixin_selector) {
		this.setting_rec_weixin_selector = setting_rec_weixin_selector;
	}
	public  int getBtn_share_selector() {
		return btn_share_selector;
	}
	public  void setBtn_share_selector(int btn_share_selector) {
		this.btn_share_selector = btn_share_selector;
	}
	public  int getBtn_my_selector() {
		return btn_my_selector;
	}
	public  void setBtn_my_selector(int btn_my_selector) {
		this.btn_my_selector = btn_my_selector;
	}
	public  int getBtn_fav_selector() {
		return btn_fav_selector;
	}
	public  void setBtn_fav_selector(int btn_fav_selector) {
		this.btn_fav_selector = btn_fav_selector;
	}
	public  int getBtn_dialogsure_selector() {
		return btn_dialogsure_selector;
	}
	public  void setBtn_dialogsure_selector(int btn_dialogsure_selector) {
		this.btn_dialogsure_selector = btn_dialogsure_selector;
	}
	public  int getBtn_dialogcancel_selector() {
		return btn_dialogcancel_selector;
	}
	public  void setBtn_dialogcancel_selector(int btn_dialogcancel_selector) {
		this.btn_dialogcancel_selector = btn_dialogcancel_selector;
	}
	public  int getBtn_comsure_selector() {
		return btn_comsure_selector;
	}
	public  void setBtn_comsure_selector(int btn_comsure_selector) {
		this.btn_comsure_selector = btn_comsure_selector;
	}
	public  int getBtn_back_selector() {
		return btn_back_selector;
	}
	public  void setBtn_back_selector(int btn_back_selector) {
		this.btn_back_selector = btn_back_selector;
	}
	public  int getBtn_menu_selector() {
		return btn_menu_selector;
	}
	public  void setBtn_menu_selector(int btn_menu_selector) {
		this.btn_menu_selector = btn_menu_selector;
	}
	public  int getMy_btn_normal() {
		return my_btn_normal;
	}
	public  void setMy_btn_normal(int my_btn_normal) {
		this.my_btn_normal = my_btn_normal;
	}
	public  int getComplain_discuss_btn_normal() {
		return complain_discuss_btn_normal;
	}
	public  void setComplain_discuss_btn_normal(
			int complain_discuss_btn_normal) {
		this.complain_discuss_btn_normal = complain_discuss_btn_normal;
	}
	public  int getSure_bnt_normal() {
		return sure_bnt_normal;
	}
	public  void setSure_bnt_normal(int sure_bnt_normal) {
		this.sure_bnt_normal = sure_bnt_normal;
	}
	public  int getJpushlogo() {
		return jpushlogo;
	}
	public  void setJpushlogo(int jpushlogo) {
		this.jpushlogo = jpushlogo;
	}
	 
	public  int getBtn_colection_selector() {
		return btn_colection_selector;
	}
	public  void setBtn_colection_selector(int btn_colection_selector) {
		this.btn_colection_selector = btn_colection_selector;
	}
	 
	public  int getBtn_comment_selector() {
		return btn_comment_selector;
	}
	public  void setBtn_comment_selector(int btn_comment_selector) {
		this.btn_comment_selector = btn_comment_selector;
	}
	 
	public  int getIm_setting_version_logo() {
		return im_setting_version_logo;
	}
	public  void setIm_setting_version_logo(int im_setting_version_logo) {
		this.im_setting_version_logo = im_setting_version_logo;
	}
	public  int getIm_set_logo() {
		return im_set_logo;
	}
	public  void setIm_set_logo(int im_set_logo) {
		this.im_set_logo = im_set_logo;
	}
	public  int getIv_qidongye() {
		return iv_qidongye;
	}
	public  void setIv_qidongye(int iv_qidongye) {
		this.iv_qidongye = iv_qidongye;
	}
	public  int getBackground_left() {
		return background_left;
	}
	public  void setBackground_left(int background_left) {
		this.background_left = background_left;
	}
	public  int getBackground_right() {
		return background_right;
	}
	public  void setBackground_right(int background_right) {
		this.background_right = background_right;
	}
	public  int getBtn_sign_selector() {
		return btn_sign_selector;
	}
	public  void setBtn_sign_selector(int btn_sign_selector) {
		this.btn_sign_selector = btn_sign_selector;
	}
	public  int getBtn_registration_selector() {
		return btn_registration_selector;
	}
	public  void setBtn_registration_selector(int btn_registration_selector) {
		this.btn_registration_selector = btn_registration_selector;
	}
	public  int getBtn_again_selector() {
		return btn_again_selector;
	}
	public  void setBtn_again_selector(int btn_again_selector) {
		this.btn_again_selector = btn_again_selector;
	}
	public  int getBtn_petition_selector() {
		return btn_petition_selector;
	}
	public  void setBtn_petition_selector(int btn_petition_selector) {
		this.btn_petition_selector = btn_petition_selector;
	}
	public  int getFont_grey_selector() {
		return font_grey_selector;
	}
	public  void setFont_grey_selector(int font_grey_selector) {
		this.font_grey_selector = font_grey_selector;
	}
	public  int getBtn_pic_selector() {
		return btn_pic_selector;
	}
	public  void setBtn_pic_selector(int btn_pic_selector) {
		this.btn_pic_selector = btn_pic_selector;
	}
	public  int getBtn_photo_selector() {
		return btn_photo_selector;
	}
	public  void setBtn_photo_selector(int btn_photo_selector) {
		this.btn_photo_selector = btn_photo_selector;
	}
	public  int getBtn_ask_selector() {
		return btn_ask_selector;
	}
	public  void setBtn_ask_selector(int btn_ask_selector) {
		this.btn_ask_selector = btn_ask_selector;
	}
	public  int getBtn_inves_share_selector() {
		return btn_inves_share_selector;
	}
	public  void setBtn_inves_share_selector(int btn_inves_share_selector) {
		this.btn_inves_share_selector = btn_inves_share_selector;
	}
	public  int getInvestigate_share_btn_press() {
		return investigate_share_btn_press;
	}
	public  void setInvestigate_share_btn_press(
			int investigate_share_btn_press) {
		this.investigate_share_btn_press = investigate_share_btn_press;
	}
	public  int getDownward_btn_normal() {
		return downward_btn_normal;
	}
	public  void setDownward_btn_normal(int downward_btn_normal) {
		this.downward_btn_normal = downward_btn_normal;
	}
	public  int getComplain_video() {
		return complain_video;
	}
	public  void setComplain_video(int complain_video) {
		this.complain_video = complain_video;
	}
	public  int getBtn_video_selector() {
		return btn_video_selector;
	}
	public  void setBtn_video_selector(int btn_video_selector) {
		this.btn_video_selector = btn_video_selector;
	}
	public  int getFont_blue_selector() {
		return font_blue_selector;
	}
	public  void setFont_blue_selector(int font_blue_selector) {
		this.font_blue_selector = font_blue_selector;
	}
	public  int getHotspot_btn_normal() {
		return hotspot_btn_normal;
	}
	public  void setHotspot_btn_normal(int hotspot_btn_normal) {
		this.hotspot_btn_normal = hotspot_btn_normal;
	}
	public  int getTrends_btn_normal() {
		return trends_btn_normal;
	}
	public  void setTrends_btn_normal(int trends_btn_normal) {
		this.trends_btn_normal = trends_btn_normal;
	}
	public  int getInstitution_btn_normal() {
		return institution_btn_normal;
	}
	public  void setInstitution_btn_normal(int institution_btn_normal) {
		this.institution_btn_normal = institution_btn_normal;
	}
	public  int getTrends_btn_press() {
		return trends_btn_press;
	}
	public  void setTrends_btn_press(int trends_btn_press) {
		this.trends_btn_press = trends_btn_press;
	}
	public  int getInstitution_btn_press() {
		return institution_btn_press;
	}
	public  void setInstitution_btn_press(int institution_btn_press) {
		this.institution_btn_press = institution_btn_press;
	}
	public  int getHotspot_btn_press() {
		return hotspot_btn_press;
	}
	public  void setHotspot_btn_press(int hotspot_btn_press) {
		this.hotspot_btn_press = hotspot_btn_press;
	}
	public  int getMy_btn_press() {
		return my_btn_press;
	}
	public  void setMy_btn_press(int my_btn_press) {
		this.my_btn_press = my_btn_press;
	}
	public  int getBtn_baoliao_left_selector() {
		return btn_baoliao_left_selector;
	}
	public  void setBtn_baoliao_left_selector(int btn_baoliao_left_selector) {
		this.btn_baoliao_left_selector = btn_baoliao_left_selector;
	}
	public  int getBtn_baoliao_right_selector() {
		return btn_baoliao_right_selector;
	}
	public  void setBtn_baoliao_right_selector(int btn_baoliao_right_selector) {
		this.btn_baoliao_right_selector = btn_baoliao_right_selector;
	}
	public  int getMy_newspaper_btn_press() {
		return my_newspaper_btn_press;
	}
	public  void setMy_newspaper_btn_press(int my_newspaper_btn_press) {
		this.my_newspaper_btn_press = my_newspaper_btn_press;
	}
	public  int getNet_newspaper_btn_normal() {
		return net_newspaper_btn_normal;
	}
	public  void setNet_newspaper_btn_normal(int net_newspaper_btn_normal) {
		this.net_newspaper_btn_normal = net_newspaper_btn_normal;
	}
	public  int getNet_newspaper_btn_press() {
		return net_newspaper_btn_press;
	}
	public  void setNet_newspaper_btn_press(int net_newspaper_btn_press) {
		this.net_newspaper_btn_press = net_newspaper_btn_press;
	}
	public  int getMy_newspaper_btn_normal() {
		return my_newspaper_btn_normal;
	}
	public  void setMy_newspaper_btn_normal(int my_newspaper_btn_normal) {
		this.my_newspaper_btn_normal = my_newspaper_btn_normal;
	}
	public  int getMenu_title_color() {
		return menu_title_color;
	}
	public  void setMenu_title_color(int menu_title_color) {
		this.menu_title_color = menu_title_color;
	}
	public  int getTitle_text_color() {
		return title_text_color;
	}
	public  void setTitle_text_color(int title_text_color) {
		this.title_text_color = title_text_color;
	}
	public  int getSelect_tap_title_text_color() {
		return select_tap_title_text_color;
	}
	public  void setSelect_tap_title_text_color(
			int select_tap_title_text_color) {
		this.select_tap_title_text_color = select_tap_title_text_color;
	}
	public  int getUnselect_tap_title_text_color() {
		return unselect_tap_title_text_color;
	}
	public  void setUnselect_tap_title_text_color(
			int unselect_tap_title_text_color) {
		this.unselect_tap_title_text_color = unselect_tap_title_text_color;
	}
	public  int getTv_forgetpwd_color() {
		return tv_forgetpwd_color;
	}
	public  void setTv_forgetpwd_color(int tv_forgetpwd_color) {
		this.tv_forgetpwd_color = tv_forgetpwd_color;
	}
	public  int getBackgroup_reft_color() {
		return backgroup_reft_color;
	}
	public  void setBackgroup_reft_color(int backgroup_reft_color) {
		this.backgroup_reft_color = backgroup_reft_color;
	}
	public  int getBackgroup_right_color() {
		return backgroup_right_color;
	}
	public  void setBackgroup_right_color(int backgroup_right_color) {
		this.backgroup_right_color = backgroup_right_color;
	}
	public  int getTitle_bg_color() {
		return title_bg_color;
	}
	public  void setTitle_bg_color(int title_bg_color) {
		this.title_bg_color = title_bg_color;
	}
	public  String getIsImgbackgFormenu() {
		return isImgbackgFormenu;
	}
	public  void setIsImgbackgFormenu(String isImgbackgFormenu) {
		this.isImgbackgFormenu = isImgbackgFormenu;
	}
	public  String getAppid() {
		return appid;
	}
	public  void setAppid(String appid) {
		this.appid = appid;
	}
	public  String getWeatherAreaid() {
		return weatherAreaid;
	}
	public  void setWeatherAreaid(String weatherAreaid) {
		this.weatherAreaid = weatherAreaid;
	}
	public  String getNeedUserGuid() {
		return needUserGuid;
	}
	public  void setNeedUserGuid(String needUserGuid) {
		this.needUserGuid = needUserGuid;
	}
	public  String getXinhuaKey() {
		return xinhuaKey;
	}
	public  void setXinhuaKey(String xinhuaKey) {
		this.xinhuaKey = xinhuaKey;
	}
	public  String getZh_usertips() {
		return zh_usertips;
	}
	public  void setZh_usertips(String zh_usertips) {
		this.zh_usertips = zh_usertips;
	}
	public  String getWeatherShortAddr() {
		return weatherShortAddr;
	}
	public  void setWeatherShortAddr(String weatherShortAddr) {
		this.weatherShortAddr = weatherShortAddr;
	}
	public  String getTv_setting_version_tile() {
		return tv_setting_version_tile;
	}
	public  void setTv_setting_version_tile(String tv_setting_version_tile) {
		this.tv_setting_version_tile = tv_setting_version_tile;
	}
	public  String getTv_setting_version_sponsor_tx() {
		return tv_setting_version_sponsor_tx;
	}
	public  void setTv_setting_version_sponsor_tx(
			String tv_setting_version_sponsor_tx) {
		this.tv_setting_version_sponsor_tx = tv_setting_version_sponsor_tx;
	}
	public  String getTv_setting_version_contact_phone_tx() {
		return tv_setting_version_contact_phone_tx;
	}
	public  void setTv_setting_version_contact_phone_tx(
			String tv_setting_version_contact_phone_tx) {
		this.tv_setting_version_contact_phone_tx = tv_setting_version_contact_phone_tx;
	}
	public  String getTv_setting_version_contact_phone_tx2() {
		return tv_setting_version_contact_phone_tx2;
	}
	public  void setTv_setting_version_contact_phone_tx2(
			String tv_setting_version_contact_phone_tx2) {
		this.tv_setting_version_contact_phone_tx2 = tv_setting_version_contact_phone_tx2;
	}
	public  String getTv_setting_version_contact_fax_tx() {
		return tv_setting_version_contact_fax_tx;
	}
	public  void setTv_setting_version_contact_fax_tx(
			String tv_setting_version_contact_fax_tx) {
		this.tv_setting_version_contact_fax_tx = tv_setting_version_contact_fax_tx;
	}
	public  String getTv_setting_version_contact_addr_tx() {
		return tv_setting_version_contact_addr_tx;
	}
	public  void setTv_setting_version_contact_addr_tx(
			String tv_setting_version_contact_addr_tx) {
		this.tv_setting_version_contact_addr_tx = tv_setting_version_contact_addr_tx;
	}
	public  String getTv_setting_version_contact_website_tx() {
		return tv_setting_version_contact_website_tx;
	}
	public  void setTv_setting_version_contact_website_tx(
			String tv_setting_version_contact_website_tx) {
		this.tv_setting_version_contact_website_tx = tv_setting_version_contact_website_tx;
	}
	public  int getNav_drawer_items() {
		return nav_drawer_items;
	}
	public  void setNav_drawer_items(int nav_drawer_items) {
		this.nav_drawer_items = nav_drawer_items;
	}
	public  int getNav_code_items() {
		return nav_code_items;
	}
	public  void setNav_code_items(int nav_code_items) {
		this.nav_code_items = nav_code_items;
	}
	public  int getNav_drawer_icons() {
		return nav_drawer_icons;
	}
	public  void setNav_drawer_icons(int nav_drawer_icons) {
		this.nav_drawer_icons = nav_drawer_icons;
	}
	public Class getMyFragmentActivity() {
		return MyFragmentActivity;
	}
	public void setMyFragmentActivity(Class myFragmentActivity) {
		this.MyFragmentActivity = myFragmentActivity;
	}
	public String[] getAreaids() {
		return areaids;
	}
	public void setAreaids(String[] areaids) {
		this.areaids = areaids;
	}

	public String getArea() {
		String aname=(String) StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_AREA,"1");
		if(FuncUtil.isEmpty(aname)&&FuncUtil.isEmpty(this.area)){
			aname=this.weatherShortAddr;
		}
		if(FuncUtil.isEmpty(aname)){
			aname=this.area;
		}
		return aname;
	}

	public void setArea(String area) {
		
		String aname=(String) StCacheHelper.getCacheObj(ComApp.getInstance(), CoreContants.CACHE_AREA,"1");
		if(!area.equals(aname)){
			StCacheHelper.setCacheObj(ComApp.getInstance(),CoreContants.CACHE_AREA,"1", area);
		}
		this.area = area;
	}
	public boolean isNeedScaner() {
		return needScaner;
	}
	public void setNeedScaner(boolean needScaner) {
		this.needScaner = needScaner;
	}
	public boolean isRecommend() {
		return recommend;
	}
	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}
	public boolean isHomeOnButtom() {
		return homeOnButtom;
	}
	public void setHomeOnButtom(boolean homeOnButtom) {
		this.homeOnButtom = homeOnButtom;
	}
	public ProgressBar getProc_loading() {
		return proc_loading;
	}
	public void setProc_loading(ProgressBar proc_loading) {
		this.proc_loading = proc_loading;
	}
	public int getYytb() {
		return yytb;
	}
	public void setYytb(int yytb) {
		this.yytb = yytb;
	}
	
}
