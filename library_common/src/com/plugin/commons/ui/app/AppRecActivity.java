package com.plugin.commons.ui.app;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;

import com.plugin.R;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.AppInfoModel;
import com.plugin.commons.model.AreaModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.zq.types.StBaseType;

/**
 * @author zhang
 *	应用推荐首页
 */
public class AppRecActivity extends FragmentActivity {
	FragmentTransaction fragmentTransaction;
	IndustryRecFragment industryFragment;
	HotAppRecFragment hotAppFragment;
	AraeRecFragment areaRecFragment;
	private List<AreaModel> area_list;//地区列表
	private List<AppInfoModel> hot_app_list;//热门列表
	private List<AppInfoModel> industry_app_list;//热门列表
	NewsService nsService;
	int reqThread=3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app);
		ComUtil.customeTitle(this,"应用推荐",true);
		doRequest();
		initView();
	}

	private void doRequest() {
		ComUtil.showListNone(this.getEmptyView(), "加载中...",area_list);
		nsService=new NewsServiceImpl();
		DialogUtil.showProgressDialog(this, "加载中...");
		 
//		// Do work to refresh the list here.
		SituoHttpAjax.ajax(new SituoAjaxCallBackExt(CoreContants.REQUEST_APP_AREA));
		SituoHttpAjax.ajax(new SituoAjaxCallBackExt(CoreContants.REQUEST_APP_HOT));
		SituoHttpAjax.ajax(new SituoAjaxCallBackExt(CoreContants.REQUEST_APP_INDUSTRY));
		
	}

	private void initView() {
		
	}
	
	private void initDisplay(){
		industryFragment=new IndustryRecFragment();
		industryFragment.setModelTile("行业应用推荐");
		industryFragment.setIndustry_app_list(industry_app_list);
		areaRecFragment=new AraeRecFragment();
		areaRecFragment.setArea_list(area_list);
		hotAppFragment = new HotAppRecFragment();
		hotAppFragment.setModelTile("热门应用推荐");
		hotAppFragment.setHot_app_list(hot_app_list);
		
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_area_app, areaRecFragment);
        fragmentTransaction.replace(R.id.fl_industry_app,industryFragment);
        fragmentTransaction.replace(R.id.fl_hot_app, hotAppFragment);
        fragmentTransaction.commit();
	}
	
	class SituoAjaxCallBackExt implements SituoAjaxCallBack{

		String type;
		SituoAjaxCallBackExt(String type){
			this.type=type;
		}
		@Override
		public StBaseType requestApi() {
			RspResultModel rsp=null;
			if(CoreContants.REQUEST_APP_AREA.equals(type)){
				 rsp=nsService.getAreaList();
			}
			if(CoreContants.REQUEST_APP_HOT.equals(type)){
				rsp=nsService.getHotRecList();
			}
			if(CoreContants.REQUEST_APP_INDUSTRY.equals(type)){
				rsp=nsService.getIndustryRecList();
			}
			return rsp;
		}

		@Override
		public void callBack(StBaseType baseType) {
			 
			RspResultModel result = (RspResultModel)baseType;
			if(ComUtil.checkRsp(AppRecActivity.this, result)){
				reqThread--;
				if("1".equals(type)){
					area_list=result.getArea_list();
				}
				if("2".equals(type)){
					hot_app_list=result.getApp_list();
				}
				if("3".equals(type)){
					industry_app_list=result.getApp_list();
				}
				if(reqThread==0){
					initDisplay();
					DialogUtil.closeProgress(AppRecActivity.this);
				}
			}
		}
		
	}
	private View getEmptyView(){
		return this.findViewById(R.id.ll_root);
	}
}
