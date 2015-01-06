package com.plugin.commons.ui.my;

import java.util.Date;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.LocationUtils;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.SmartWeatherService;
import com.plugin.commons.service.SmartWeatherServiceImpl;
import com.zq.types.StBaseType;

public class WeatherActivity extends Activity {
	DingLog log = new DingLog(WeatherActivity.class);
	
	SmartWeatherService swSvc;
	ImageView iv_observe;
	ImageView iv_11;
	ImageView iv_21;
	ImageView iv_31;
	TextView tv_otemp;
	TextView tv_oname;
	TextView tv_odate;
	TextView tv_date;
	TextView tv_11;
	TextView tv_12;
	TextView tv_13;
	TextView tv_21;
	TextView tv_22;
	TextView tv_23;
	TextView tv_31;
	TextView tv_32;
	TextView tv_33;
	TextView tv_index;
	TextView tv_title;
	TextView tv_indexlable;
	
	String city;
	String areaid;
	
	
	LinearLayout ll1;
	LinearLayout ll2;
	LinearLayout ll3;
	
	Button btn_left;
	RspResultModel mTodayData;
	RspResultModel mForeData;
	RspResultModel mIndexData;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initViews();
        ensureUI();
        AnalyticsAgent.setPageMode(true);
    }
    
    private void initViews()
    {
    	swSvc = new SmartWeatherServiceImpl();
    	
    	city=ComApp.getInstance().appStyle.weatherShortAddr;
		if(CoreContants.APP_LNZX.equals(ComApp.APP_NAME)){
			city=ComApp.getInstance().appStyle.getArea();
			if(FuncUtil.isEmpty(city)){
				city=ComApp.getInstance().appStyle.weatherShortAddr;
	    	}
		}
		areaid=swSvc.getAreaidFromCity(city);
    	
    	iv_observe = (ImageView)this.findViewById(R.id.iv_observe);
    	iv_11 = (ImageView)this.findViewById(R.id.iv_11);
    	iv_21 = (ImageView)this.findViewById(R.id.iv_21);
    	iv_31 = (ImageView)this.findViewById(R.id.iv_31);
    	
    	btn_left = (Button)this.findViewById(R.id.btn_left);
    	tv_otemp = (TextView)this.findViewById(R.id.tv_otemp);
    	tv_oname = (TextView)this.findViewById(R.id.tv_oname);
    	tv_odate = (TextView)this.findViewById(R.id.tv_odate);
    	tv_date = (TextView)this.findViewById(R.id.tv_date);
    	tv_title = (TextView)this.findViewById(R.id.tv_title);
    	 
    	tv_title.setText(city+"天气");
    	tv_indexlable = (TextView)this.findViewById(R.id.tv_indexlable);
    	
    	
    	
    	tv_11 = (TextView)this.findViewById(R.id.tv_11);
    	tv_12 = (TextView)this.findViewById(R.id.tv_12);
    	tv_13 = (TextView)this.findViewById(R.id.tv_13);
    	
    	tv_21 = (TextView)this.findViewById(R.id.tv_21);
    	tv_22 = (TextView)this.findViewById(R.id.tv_22);
    	tv_23 = (TextView)this.findViewById(R.id.tv_23);
    	
    	tv_31 = (TextView)this.findViewById(R.id.tv_31);
    	tv_32 = (TextView)this.findViewById(R.id.tv_32);
    	tv_33 = (TextView)this.findViewById(R.id.tv_33);
    	
    	tv_index = (TextView)this.findViewById(R.id.tv_index);
    	
    	iv_11 = (ImageView)this.findViewById(R.id.iv_11);
    	iv_21 = (ImageView)this.findViewById(R.id.iv_21);
    	iv_31 = (ImageView)this.findViewById(R.id.iv_31);
    	
    	ll1 = (LinearLayout)this.findViewById(R.id.ll1);
    	ll2 = (LinearLayout)this.findViewById(R.id.ll2);
    	ll3 = (LinearLayout)this.findViewById(R.id.ll3);
    	
      
    }
    private void ensureUI(){
    	int width = ComUtil.getWindowWidth(this)/3;
    	ll1.getLayoutParams().width = width;
    	ll2.getLayoutParams().width = width;
    	ll3.getLayoutParams().width = width;
    	btn_left.setOnClickListener(new View.OnClickListener() {
  			
  			@Override
  			public void onClick(View arg0) {
  				// TODO Auto-generated method stub
  				finish();
  			}
  		});
    	DialogUtil.showProgressDialog(WeatherActivity.this);
    	SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				mTodayData = swSvc.getObserver(areaid);
				mForeData = swSvc.getForecast(areaid);
				mIndexData = swSvc.getIndex(areaid);
				return null;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(WeatherActivity.this);
				if(ComUtil.checkRsp(WeatherActivity.this, mTodayData,false)
						&&ComUtil.checkRsp(WeatherActivity.this, mForeData,false)
						){
					Date date = new Date();
					tv_otemp.setText(mTodayData.getL().getL1()+"°C");
					
					tv_odate.setText(FuncUtil.formatTime(date, "yyyy.MM.dd")+" "+FuncUtil.getWeekString(date)+" 农历"+FuncUtil.getNongLi(date));
					
					if(mForeData.getF()!=null&&mForeData.getF().getF1().size()>0){
						tv_oname.setText(mForeData.getF().getF1().get(0).getWeatherName());
						tv_11.setText("今天");
						tv_12.setText(mForeData.getF().getF1().get(0).getFc()+"°C/"+mForeData.getF().getF1().get(0).getFd()+"°C");
						tv_13.setText(mForeData.getF().getF1().get(0).getWeatherName());
						log.info("img:"+mForeData.getF().getF1().get(0).getWeatherImg());
						int id1 = getResources().getIdentifier(mForeData.getF().getF1().get(0).getWeatherImg(), "drawable", getPackageName());
						log.info("id1:"+id1);
						if(id1>0){
							log.info("获取到img");
							iv_observe.setImageDrawable(getResources().getDrawable(id1));
							iv_11.setImageDrawable(getResources().getDrawable(id1));
						}
						
						if(mForeData.getF().getF1().size()>1){
							tv_21.setText("明天");
							tv_22.setText(mForeData.getF().getF1().get(1).getFc()+"°C/"+mForeData.getF().getF1().get(1).getFd()+"°C");
							tv_23.setText(mForeData.getF().getF1().get(1).getWeatherName());
							int id2 = getResources().getIdentifier(mForeData.getF().getF1().get(1).getWeatherImg(), "drawable", getPackageName());
							log.info("id2:"+id2);
							if(id2>0){
								log.info("获取到img");
								iv_21.setImageDrawable(getResources().getDrawable(id2));
							}
						}
						
						if(mForeData.getF().getF1().size()>2){
							tv_31.setText("后天");
							tv_32.setText(mForeData.getF().getF1().get(2).getFc()+"°C/"+mForeData.getF().getF1().get(2).getFd()+"°C");
							tv_33.setText(mForeData.getF().getF1().get(2).getWeatherName());
							int id3 = getResources().getIdentifier(mForeData.getF().getF1().get(2).getWeatherImg(), "drawable", getPackageName());
							log.info("id3:"+id3);
							if(id3>0){
								log.info("获取到img");
								iv_31.setImageDrawable(getResources().getDrawable(id3));
							}
						}
						
					}
					tv_date.setText(FuncUtil.formatTime(new Date(), "yyyy-MM-dd")+" "+mTodayData.getL().getL7()+" 发布");
					if(mIndexData!=null&&!FuncUtil.isEmpty(mIndexData.getI())){
						tv_indexlable.setText("生活指数:");
						tv_index.setText(mIndexData.getI().get(0).getI5());
					}
				}
				else{
					DialogUtil.showToast(WeatherActivity.this, "获取天气数据失败");
				}
				
				
			}
		});
    }
	@Override
	public void onDestroy(){
		super.onDestroy();
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
}
