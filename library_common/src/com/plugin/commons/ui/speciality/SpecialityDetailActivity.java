package com.plugin.commons.ui.speciality;

import java.util.ArrayList;
import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.ui.base.BaseActivity;
import com.zq.types.StBaseType;

public class SpecialityDetailActivity  extends BaseActivity{
	NewsInfoModel mNews;
	ImageView iv_logo;
	TextView tv_desc;
	LinearLayout ll_list;
	private List<NewsInfoModel> itemList = new ArrayList<NewsInfoModel>();
	NewsService newsSvc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specialty_detail);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_MSG)) {
        	mNews = (NewsInfoModel) getIntent().getExtras().getSerializable(CoreContants.PARAMS_MSG);
		}
		String pdName =mNews.getTitle();
		if(FuncUtil.isEmpty(pdName)){
			pdName="特产详情";
		}
		ComUtil.customeTitle(this,pdName,true);
		initViews();
		refreshUI();
	}
	private void initViews(){
		iv_logo = (ImageView)this.findViewById(R.id.iv_logo);
		tv_desc = (TextView)this.findViewById(R.id.tv_desc);
		ll_list = (LinearLayout)this.findViewById(R.id.ll_list);
		 
	}
	private void refreshUI(){
		newsSvc = new NewsServiceImpl();
		ComApp.getInstance().getFinalBitmap().display(iv_logo, mNews.getImg(), 
				ComApp.getInstance().getLoadingBig(), ComApp.getInstance().getLoadingBig());
		tv_desc.setText(FuncUtil.isEmpty(mNews.getDescition())?"暂无介绍":mNews.getDescition());
//		this.findViewById(R.id.rl_desc).setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				NewsTypeModel type = new NewsTypeModel();
//				type.setId(CoreContants.MENU_CODE_SPECIAL);
//				type.setHassub("0");
//				type.setType(CoreContants.NEWS_SUBTYPE_WORD);
//				ComUtil.goNewsDetail(SpecialityDetailActivity.this, mNews, type);
//			}
//		});
		DialogUtil.showProgressDialog(SpecialityDetailActivity.this);
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				RspResultModel rsp  = newsSvc.getSubNewsList(false, "0", "20",CoreContants.MENU_CODE_SPECIAL, "0",mNews.getId());
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(SpecialityDetailActivity.this);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(SpecialityDetailActivity.this, result)){
					itemList = result.getArticle_list();
					refreshList();
				}
			}
		});
	}
	
	private void refreshList(){
		if(!FuncUtil.isEmpty(itemList)){
			ll_list.removeAllViews();
			for(int i=0;i<itemList.size();i++){
				ll_list.addView(createProdItem(itemList.get(i)));
				ll_list.addView(ComUtil.createLineGrey(this));
			}
		}
	}
	
	private View createProdItem(final NewsInfoModel info){
		View view = LayoutInflater.from(this).inflate(R.layout.item_specialitydetail, null);
		TextView tv_name = (TextView)view.findViewById(R.id.tv_name);
		tv_name.setText(info.getTitle());
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				NewsTypeModel type = new NewsTypeModel();
				type.setId(CoreContants.MENU_CODE_SPECIAL);
				type.setHassub("0");
				type.setType(CoreContants.NEWS_SUBTYPE_WORD);
				ComUtil.goNewsDetail(SpecialityDetailActivity.this, info, type);
				XHSDKUtil.addXHBehavior(SpecialityDetailActivity.this, type.getId()+"_"+info.getId(), XHConstants.XHTOPIC_ARTICAL_CLICK, info.getId());
			}
		});
		return view;
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