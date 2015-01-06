package com.plugin.commons.ui.fragment.base;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.barcode.scanner.CaptureActivity;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.LocationUtils;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.UserInfoModel;
import com.plugin.commons.service.SmartWeatherService;
import com.plugin.commons.service.SmartWeatherServiceImpl;
import com.plugin.commons.setting.SettingActivity;
import com.plugin.commons.ui.my.SysNotifyActivity;
import com.plugin.commons.ui.my.WeatherActivity;
import com.plugin.commons.user.LoginActivity;
import com.plugin.commons.user.UserInfoActivity;
import com.zq.types.StBaseType;

@SuppressLint({ "NewApi", "ValidFragment" })
public class RightMenuFragment extends Fragment{
	DingLog log = new DingLog(RightMenuFragment.class);
	final static int REQUEST_CODE_LOGIN = 11;
	LinearLayout ll_my;
	RelativeLayout rl_my;
	TextView tv_user;
	UserInfoModel mUser;
	ImageView iv_icon;
	View mWeatheView;
	private String userTips;
	private String shortName;
	boolean needUserGuid=false;
	private Class MyFragmentActivity;
	Activity act;
	String city;
	String areaid;
	
	public RightMenuFragment(){
		super();
		this.userTips=ComApp.getInstance().appStyle.zh_usertips;
		this.MyFragmentActivity=ComApp.getInstance().appStyle.MyFragmentActivity;
		this.needUserGuid=Boolean.parseBoolean(ComApp.getInstance().appStyle.needUserGuid);
	}
	
	public RightMenuFragment(boolean needUserGuid,String userTips,Class MyFragmentActivity){
		super();
		this.userTips=userTips;
		this.MyFragmentActivity=MyFragmentActivity;
		this.needUserGuid=needUserGuid;
	}
	
	@Override
	public void onAttach(Activity activity) {
		act=activity;
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_right_menu, null);
		log.info("frame create");
		TextView tv_tips=(TextView) rootView.findViewById(R.id.tv_tips);
		tv_tips.setText(this.userTips);
		tv_tips.setTextColor(ComApp.getInstance().getResources().getColor(ComApp.getInstance().appStyle.menu_title_color));
		if(Boolean.parseBoolean(ComApp.getInstance().appStyle.isImgbackgFormenu)){
			rootView.setBackgroundResource(ComApp.getInstance().appStyle.background_right);
		}else{
			rootView.setBackgroundColor(ComApp.getInstance().appStyle.backgroup_right_color);
		}
		findView(rootView);
		ensureUI();
		return rootView;
	}
	
	 @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
        	log.info("frame resume");
        } else {
            //相当于Fragment的onPause
        	log.info("frame pause");
        }
        AnalyticsAgent.onPageStart(getActivity(), "RightMenuFragment");
    }
	 
	@Override
	public void onResume() {
			super.onResume();
			mUser = ComApp.getInstance().getLoginInfo();
			if(mUser!=null){
				tv_user.setText(mUser.getUsername());
				if(!FuncUtil.isEmpty(mUser.getPhoto())){
					ComApp.getInstance().getFinalBitmap().display(iv_icon, mUser.getPhoto());
				}
			}
			else{
				tv_user.setText("登录帐号");
				iv_icon.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.head));
			}
			if(ComUtil.isNetworkAvailable(act)){
				ComApp.getInstance().appStyle.setmWeatheView(mWeatheView);
				ComUtil.doSmartWeather(ComApp.getInstance().appStyle.getArea(),act, mWeatheView);
			}else{
				TextView tv_title = (TextView)mWeatheView.findViewById(R.id.title);
				tv_title.setText("正在努力获取天气...");
			}
	}

	private void findView(View rootView) {
		ll_my = (LinearLayout)rootView.findViewById(R.id.ll_my);
		rl_my = (RelativeLayout)rootView.findViewById(R.id.rl_my);
		tv_user = (TextView)rootView.findViewById(R.id.tv_user);
		tv_user.setTextColor(this.getResources().getColor(ComApp.getInstance().appStyle.menu_title_color));
		iv_icon = (ImageView)rootView.findViewById(R.id.iv_icon);
		
	}
	private void ensureUI(){
		ll_my.removeAllViews();
		mWeatheView = createItemInfoView(R.drawable.weather,
				shortName,"zh_my_weather");
		ll_my.addView(mWeatheView);
		ll_my.addView(ComUtil.createLineBlack(getActivity()));
		
		ll_my.addView(createItemInfoView(R.drawable.personalinformation,
				getActivity().getString(R.string.zh_my_info),"zh_my_info"));
		ll_my.addView(ComUtil.createLineBlack(getActivity()));
		
//		ll_my.addView(createItemInfoView(R.drawable.saoyisao,
//				getActivity().getString(R.string.zh_my_qr),"zh_my_qr"));
//		ll_my.addView(ComUtil.createLineBlack(getActivity()));
//		
//		ll_my.addView(createItemInfoView(R.drawable.orderform,
//				getActivity().getString(R.string.zh_my_order),"zh_my_order"));
//		ll_my.addView(ComUtil.createLineBlack(getActivity()));
		
		ll_my.addView(createItemInfoView(R.drawable.tongzhi,
				getActivity().getString(R.string.zh_my_notify),"zh_my_notify"));
		ll_my.addView(ComUtil.createLineBlack(getActivity()));
		
		ll_my.addView(createItemInfoView(R.drawable.shezhi,
				getActivity().getString(R.string.zh_my_set),"zh_my_set"));
		ll_my.addView(ComUtil.createLineBlack(getActivity()));
		
		if(this.needUserGuid){
			ll_my.addView(createItemInfoView(R.drawable.fingerpost,
					getActivity().getString(R.string.zh_my_guide),"zh_my_guide"));
			ll_my.addView(ComUtil.createLineBlack(getActivity()));
		}
		
		rl_my.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				boolean isLogin = ComApp.getInstance().isLogin();
				if(!isLogin){
					Intent intent = new Intent(RightMenuFragment.this.getActivity(),LoginActivity.class);
					getActivity().startActivityForResult(intent, REQUEST_CODE_LOGIN);
				}
				else{
					Intent intent = new Intent(RightMenuFragment.this.getActivity(),UserInfoActivity.class);
					getActivity().startActivityForResult(intent, REQUEST_CODE_LOGIN);
				}
			}
		});
	}
	
	private View createItemInfoView(int icon,String title,final String code){
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_right_menu, null);
		TextView tv_title = (TextView)view.findViewById(R.id.title);
		tv_title.setTextColor(ComApp.getInstance().getResources().getColor(ComApp.getInstance().appStyle.menu_title_color));
		ImageView iv_icon = (ImageView)view.findViewById(R.id.icon);
		view.setTag(code);
		tv_title.setText(title);
		iv_icon.setBackground(getActivity().getResources().getDrawable(icon));
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if("zh_my_info".equals(code)){
					Intent intent = new Intent(RightMenuFragment.this.getActivity(),MyFragmentActivity);
//					Intent intent = new Intent(RightMenuFragment.this.getActivity(),MyActivity.class);
					//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					
				}
				else if("zh_my_weather".equals(code)){
					Intent intent = new Intent(getActivity(),WeatherActivity.class);
					startActivity(intent);
				}
				else if("zh_my_qr".equals(code)){
					Intent intent = new Intent(RightMenuFragment.this.getActivity(),CaptureActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
				else if("zh_my_order".equals(code)){
					ComUtil.gotoWaitingActivity(getActivity(), "订单");
				}
				else if("zh_my_notify".equals(code)){
					Intent intent = new Intent(RightMenuFragment.this.getActivity(),SysNotifyActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
				else if("zh_my_set".equals(code)){
					Intent intent = new Intent(RightMenuFragment.this.getActivity(),SettingActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
				else if("zh_my_guide".equals(code)){
					ComUtil.gotoWaitingActivity(getActivity(), "新手指引");
				}
			}
		});
		return view;
	}
		
	@Override
	public void onPause() {
		super.onPause();
		AnalyticsAgent.onPageEnd(getActivity(), "RightMenuFragment");
	}
	 
}
