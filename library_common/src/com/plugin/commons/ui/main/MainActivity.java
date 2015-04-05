package com.plugin.commons.ui.main;

import java.util.HashSet;
import java.util.Set;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.CryptUtils;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.NavDrawerItem;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.UserInfoModel;
import com.plugin.commons.petition.PetitionFragment;
import com.plugin.commons.service.ComService;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.service.XinHuaService;
import com.plugin.commons.service.XinHuaServiceImpl;
import com.plugin.commons.ui.event.SLMenuListOnItemClickListener;
import com.plugin.commons.ui.fragment.base.FindPeopleFragment;
import com.plugin.commons.ui.fragment.base.MenuFragment;
import com.plugin.commons.ui.fragment.base.RightMenuFragment;
import com.plugin.commons.ui.fragment.base.WapFragment;
import com.plugin.commons.ui.news.HomePageFragment;
import com.plugin.commons.ui.news.NewsGroupFragment;
import com.plugin.commons.ui.news.NewsImageListFragment;
import com.plugin.commons.ui.news.NewsTabFragment;
import com.plugin.commons.ui.news.NewsVideoTabFragment;
import com.plugin.commons.ui.news.SubNewsTabFragment;
import com.plugin.commons.ui.number.NumberTypeFragment;
import com.plugin.commons.ui.speciality.SpecialityListFragment;

@SuppressLint("NewApi")
public class MainActivity extends SlidingFragmentActivity implements SLMenuListOnItemClickListener{
	DingLog log = new DingLog(MainActivity.class);
	private SlidingMenu mSlidingMenu;
	Button btn_left;
	Button btn_right;
	TextView tv_title;
	ProgressBar proc_loading;
	private long exitTime = 0;
	public HomePageFragment homeFragmet;
	public NewsGroupFragment kdFragment;
	RightMenuFragment rightMenu;
	public WapFragment webFragment;
	Fragment mContent;
	NavDrawerItem mSelectMenu;
	XinHuaService xhSv;
	private SpecialityListFragment specFragment;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_content);

        //set the Behind View
        setBehindContentView(R.layout.frame_left_menu);
        initProper();
        xhSv=new XinHuaServiceImpl();
        // customize the SlidingMenu
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);//设置左右都可以划出SlidingMenu菜单
        mSlidingMenu.setSecondaryMenu(R.layout.frame_right_menu);	//设置右侧菜单的布局文件
        mSlidingMenu.setSecondaryShadowDrawable(R.drawable.drawer_shadow);
        
//      mSlidingMenu.setShadowWidth(5);
//      mSlidingMenu.setBehindOffset(100);
        mSlidingMenu.setShadowDrawable(R.drawable.drawer_shadow);//设置阴影图片
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width); //设置阴影图片的宽度
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset); //SlidingMenu划出时主页面显示的剩余宽度
        mSlidingMenu.setFadeDegree(0.35f);
        //设置SlidingMenu 的手势模式
        //TOUCHMODE_FULLSCREEN 全屏模式，在整个content页面中，滑动，可以打开SlidingMenu
        //TOUCHMODE_MARGIN 边缘模式，在content页面中，如果想打开SlidingMenu,你需要在屏幕边缘滑动才可以打开SlidingMenu
        //TOUCHMODE_NONE 不能通过手势打开SlidingMenu
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        
        //设置 SlidingMenu 内容
        homeFragmet = new HomePageFragment();
        mContent = homeFragmet;
        //实例化左边菜单栏目
        MenuFragment leftMenu= new MenuFragment();
        rightMenu= new RightMenuFragment();
        
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.left_menu, leftMenu);
        fragmentTransaction.replace(R.id.right_menu,rightMenu);
        fragmentTransaction.replace(R.id.content, homeFragmet);
        
        fragmentTransaction.commit();
        
        if (ComApp.getInstance().isLogin()) {
        	UserInfoModel customer = ComApp.getInstance().getLoginInfo();
        	Set<String> ss = new HashSet<String>();
        	if(!FuncUtil.isEmpty(customer.getIdentity())){
        		ss.add(customer.getIdentity());
        	}
        	if(customer!=null){
        		JPushInterface.setAliasAndTags(MainActivity.this,
        				customer.getPhone(), ss);
        	}
		}
        
        log.debug("mian线程号："+Thread.currentThread().getId());
		//启动访问统计推送服务
		startCustomService();
        //新华社统计
        XHSDKUtil.addMainBehavior(this);
        ensureUI();
		AnalyticsAgent.startWithAppKey(this);//新华统计
		AnalyticsAgent.setDebugMode(CoreContants.DEBUG);
		//AnalyticsAgent.setPageMode(false);
		UserInfoModel user = ComApp.getInstance().getLoginInfo();
		if(user!=null){
			AnalyticsAgent.setUserId(this, user.getPhone());
			AnalyticsAgent.setUserName(this, user.getUsername());
		}
	}
	
	private void initProper(){
		
		proc_loading=(ProgressBar) this.findViewById(R.id.proc_loading);
		ComApp.getInstance().appStyle.setProc_loading(proc_loading);
		LinearLayout ll_title_bg_color=(LinearLayout) this.findViewById(R.id.ll_title_bg_color);
		ll_title_bg_color.setBackgroundColor(getResources().getColor(ComApp.getInstance().appStyle.title_bg_color));
		
		btn_left = (Button)this.findViewById(R.id.btn_title_left);
		btn_left.setBackgroundResource(ComApp.getInstance().appStyle.btn_back_selector);
		btn_right = (Button)this.findViewById(R.id.btn_title_right);
		btn_right.setBackgroundResource(ComApp.getInstance().appStyle.btn_my_selector);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText(this.getResources().getString(R.string.app_name));
		tv_title.setTextColor(this.getResources().getColor(ComApp.getInstance().appStyle.title_text_color));
		btn_left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toggle();
			}
		});
		btn_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mSlidingMenu.isSecondaryMenuShowing()){
	        		mSlidingMenu.showContent();
	        	}else{
	        		mSlidingMenu.showSecondaryMenu();
	        	}
			}
		});
	}
	
	private void ensureUI(){
		btn_left.setBackgroundDrawable(getResources().getDrawable(ComApp.getInstance().appStyle.btn_menu_selector));
		btn_right.setBackgroundDrawable(getResources().getDrawable(ComApp.getInstance().appStyle.btn_my_selector));
		//btn_left.getLayoutParams().width = LayoutParams.WRAP_CONTENT;
		//btn_left.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
		btn_right.setVisibility(View.VISIBLE);
	}
	

	/**
	 * 0图文  1图片 2视频 3外部WEB  4问政 5报料 6调查，7号码通,8信件  9暂时未开通栏目 ,10拍客播客
	 */
	@SuppressLint("NewApi")
	@Override
	public void selectItem(NavDrawerItem menu) {
		// update the main content by replacing fragments  
		log.info(menu.getCode()+";"+menu.getTitle());
		if(mSelectMenu!=null&&mSelectMenu.getCode().equals(menu.getCode())){
			mSlidingMenu.showContent();
			return ;
		}
		NewsService newsSvc = new NewsServiceImpl();
    	NewsTypeModel newsType = newsSvc.getNewsType(menu.getCode());
	    Fragment fragment = null; 
	    
	    if(CoreContants.MENU_CODE_HOME.equals(menu.getCode())){
	    	log.info("homeFragmet==null:"+(homeFragmet==null));
	    	if(homeFragmet==null){
	    		homeFragmet = new HomePageFragment();
	    		homeFragmet.setmNewType(newsType);
	    	}
	    	fragment = homeFragmet;
	    }else if(CoreContants.MENU_CODE_SPECIAL.equals(menu.getCode())){//宽甸特产
	    	specFragment = new SpecialityListFragment();
	    	fragment = specFragment;
	    	
	    }else if(CoreContants.NEWS_SUBTYPE_DEVELOPPING.equals(newsType.getType())){//开发中
	    
	    	FindPeopleFragment	findPeopleFragmet = new FindPeopleFragment();
	    	fragment = findPeopleFragmet;
	    }else if(FuncUtil.isEmpty(newsType.getSubtypes())&&"1".equals(newsType.getHassub())){
    		NewsTypeModel subType =new NewsTypeModel();
	    	subType.setId("0");
	    	subType.setHassub("1");
	    	subType.setParentid(menu.getCode());
	    	subType.setType(newsType.getType());
    		SubNewsTabFragment subFragment = new SubNewsTabFragment();
    		subFragment.setMsgName("0");
    		subFragment.setmNewType(subType);
			fragment=subFragment;
    	}else if(CoreContants.NEWS_SUBTYPE_WORD.equals(newsType.getType())
	    		||CoreContants.NEWS_SUBTYPE_PIC.equals(newsType.getType())
	    		||CoreContants.NEWS_SUBTYPE_DIAOCHA.equals(newsType.getType())
	    		||CoreContants.NEWS_SUBTYPE_PKBK.equals(newsType.getType())
	    		||CoreContants.NEWS_SUBTYPE_VIDEO.equals(newsType.getType())){
	    	 
	    	if(!FuncUtil.isEmpty(newsType.getSubtypes())){
	    		kdFragment = new NewsGroupFragment();
	    		kdFragment.setTypeId(menu.getCode());
		    	fragment = kdFragment;
	    	}else if(CoreContants.NEWS_SUBTYPE_PIC.equals(newsType.getType())) {
	    		NewsImageListFragment imgFragment=new NewsImageListFragment();
	    		NewsTypeModel subType =new NewsTypeModel();
		    	subType.setId("0");
		    	subType.setParentid(menu.getCode());
		    	subType.setType(newsType.getType());
		    	imgFragment.setmNewType(subType);
		    	imgFragment.setMsgName("0");
		    	fragment=imgFragment;
	    	}else if(CoreContants.NEWS_SUBTYPE_VIDEO.equals(newsType.getType())) {
	    		NewsVideoTabFragment videoFragment=new NewsVideoTabFragment();
	    		NewsTypeModel subType =new NewsTypeModel();
		    	subType.setId("0");
		    	subType.setParentid(menu.getCode());
		    	subType.setType(newsType.getType());
		    	videoFragment.setmNewType(subType);
		    	videoFragment.setMsgName("0");
		    	fragment=videoFragment;
	    	}else{
	    		NewsTabFragment newFragment  = new NewsTabFragment(); 
		    	NewsTypeModel subType =new NewsTypeModel();
		    	subType.setId("0");
		    	subType.setParentid(menu.getCode());
		    	subType.setType(newsType.getType());
		    	newFragment.setmNewType(subType);
		    	newFragment.setMsgName("0");
		    	fragment=newFragment;
	    	}
	    }else if(CoreContants.NEWS_SUBTYPE_WAP.equals(newsType.getType())){
	    	if(!FuncUtil.isEmpty(newsType.getSubtypes())){
	    		kdFragment = new NewsGroupFragment();
	    		kdFragment.setTypeId(menu.getCode());
		    	fragment = kdFragment;
	    	}else{
	    		webFragment=new WapFragment();
		    	if("商城".equals(newsType.getName())){
		    		if(ComApp.getInstance().isLogin()){
			    		webFragment.setUrl(newsType.getOuturl()+"?phone="+ComApp.getInstance().getLoginInfo().getPhone()+"&sign="+CryptUtils.MD5enc("phone="+ComApp.getInstance().getLoginInfo().getPhone()+CoreContants.SHOP_KEY));
			    	}else{
			    		webFragment.setUrl(newsType.getOuturl());
			    	}
		    	}else if("新华社发布".equals(newsType.getName())||"新华发布".equals(newsType.getName())){ 
			    	String url=xhSv.getXinHuaIndex(ComApp.getInstance().appStyle.appid,newsType.getOuturl(),ComApp.getInstance().appStyle.xinhuaKey);
			    	webFragment.setUrl(url);
		    	}else if("中国网事".equals(newsType.getName())){ 
		    		webFragment.showWebRefreshBar(false);
		    		webFragment.setUrl(newsType.getOuturl());
		    	}else{
		    		webFragment.setUrl(newsType.getOuturl());
		    	}
		    	fragment = webFragment;
	    	}
	    }else if(CoreContants.NEWS_SUBTYPE_WENZHENG.equals(newsType.getType())){//问政
	    	FindPeopleFragment	findPeopleFragmet = new FindPeopleFragment();
	    	fragment = findPeopleFragmet;
	    }else if(CoreContants.NEWS_SUBTYPE_LETTER.equals(newsType.getType())){//信件
	    	PetitionFragment petFragment = new PetitionFragment();
	    	fragment = petFragment;
	    }else if(CoreContants.NEWS_SUBTYPE_BAOLIAO.equals(newsType.getType())){//报料
	    	FindPeopleFragment	findPeopleFragmet = new FindPeopleFragment();
	    	fragment = findPeopleFragmet;
	    }else if(CoreContants.NEWS_SUBTYPE_NUMBER.equals(newsType.getType())){//号码通	    	
	    	NumberTypeFragment numFragment=new NumberTypeFragment();    	
	    	fragment=numFragment;
	    }
	    XHSDKUtil.addXHBehavior(this, menu.getCode(), XHConstants.XHTOPIC_MENUCLICK, menu.getCode());
	    mSelectMenu = menu;
	    if (fragment != null) {  
	    	 
	    	log.info("set");
	        FragmentManager fragmentManager = getSupportFragmentManager();
	        fragmentManager.beginTransaction()  
	                .replace(R.id.content, fragment).commit();  
	        tv_title.setText(menu.getTitle());
	        mSlidingMenu.showContent();
	    }
	}
	
	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onResume(this);//新华sdk
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPause(this);//新华sdk
	}
	
	/**
	 * 返回键，退出程序
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			//dialog();
			 if((System.currentTimeMillis()-exitTime) > 2000){  
		            Toast.makeText(getApplicationContext(), getResources().getString(R.string.touch_again_exit), Toast.LENGTH_SHORT).show();                                
		            exitTime = System.currentTimeMillis();   
		     } else {
		    	 	String user="未登录用户";
		    		if(ComApp.getInstance().isLogin()){
						user=ComApp.getInstance().getLoginInfo().getUserid();
					}
		    	 	XHSDKUtil.addXHBehavior(MainActivity.this,user, XHConstants.XHTOPIC_EXIT,user +" exit app success");
		    	 	stopCustomService();
		    	 	finish();
		            System.exit(0);
		     }
		}
		return false;
	}
	@Override
	protected void onStop() {
		if(CoreContants.LOCATION_APP.contains(ComApp.APP_NAME)){
			log.info("************stop baidu locatoin************");
			if(ComApp.getInstance().mLocationClient!=null)
				ComApp.getInstance().mLocationClient.stop();
		}
		super.onStop();
	}

	//启动服务
    private void startCustomService(){
         Intent intent=new Intent(this,ComService.class);
         intent.putExtra(CoreContants.PARAMS_TYPE, CoreContants.PUSH_TYPE);
         startService(intent);
    }
    private void stopCustomService(){
    	Intent intent=new Intent(this,ComService.class);
    	stopService(intent);
    }
    
}