package com.plugin.commons.ui.app;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.model.AppInfoModel;
import com.plugin.commons.ui.fragment.base.BaseFragment;

/**
 * @author zhang
 *	热门应用推荐
 */
@SuppressLint("ResourceAsColor")
public class HotAppRecFragment extends BaseFragment {

	private List<AppInfoModel> hot_app_list;//热门列表
	protected TextView tv_modelTile;
	private LinearLayout ll_datalist;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.info(getFragmentName(), " onCreateView()");
		View view = inflater.inflate(R.layout.activity_app_industry_rec, container, false);
		return view;
	}
	protected  void initViews(View view){
		tv_modelTile=(TextView) view.findViewById(R.id.tv_desc);
		ll_datalist=(LinearLayout) view.findViewById(R.id.ll_datalist);
		tv_modelTile.setText(getModelTile());
		int itemWidth = ComUtil.getWindowWidth(mActivity)/4-30;//将屏幕分成4份，并减去前后margin
		itemWidth = itemWidth>140?140:itemWidth;//如果大于140*140，则最多140，如果小于，则取width值
		int num=3;
		if(this.getHot_app_list().size()<3){
			num=this.getHot_app_list().size();
		}
		for(int a=0;a<num+1;a++){
			final AppInfoModel appInfo=this.getHot_app_list().get(a);
			ColorStateList csl = (ColorStateList) getActivity().getResources().getColorStateList(R.color.black);
			LinearLayout itemLy=new LinearLayout(getActivity());
			itemLy.setId(1000+a);
			itemLy.setOrientation(LinearLayout.VERTICAL);
			itemLy.setPadding(1, 1, 1, 1);
			itemLy.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1);
			params.setMargins(15, 30, 15, 30);
			itemLy.setLayoutParams(params);
			ImageView img=new ImageView(getActivity());
			LinearLayout.LayoutParams imgParam=new LinearLayout.LayoutParams(itemWidth,itemWidth);
			imgParam.setMargins(1, 1, 1, 10);
			img.setLayoutParams(imgParam);
			TextView appname=new TextView(getActivity());
			appname.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
			if(a==num){
				csl = (ColorStateList) getActivity().getResources().getColorStateList(R.color.more);
				itemLy.setId(3999);
				appname.setText("更多>>");
				appname.setTextColor(R.color.more);
				appname.setTextColor(csl);
				img.setBackgroundResource(R.drawable.gengduo);
			}else{
				appname.setText(appInfo.getName());
//				img.setBackgroundResource(R.drawable.pk001);
				ComApp.getInstance().getFinalBitmap().display(img, appInfo.getLogo());
				appname.setTextColor(csl);
			}
			itemLy.addView(img);
			itemLy.addView(appname);
			
			ll_datalist.addView(itemLy);

			itemLy.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent;
					 if(v.getId()==3999){
						 intent=new Intent(getActivity(), AppListActivity.class);
						 intent.putExtra(CoreContants.PARAMS_MSG_ID,CoreContants.REQUEST_APP_HOT);
						 intent.putExtra(CoreContants.PARAMS_TITLE, getModelTile());
					 }else{
						 intent=new Intent(getActivity(), AppDetailActivity.class);
						 intent.putExtra(CoreContants.PARAMS_MSG,appInfo);
						 intent.putExtra(CoreContants.PARAMS_TITLE, getModelTile());
					 }
					 getActivity().startActivity(intent);
				}
			});
		}
	}
	
	@Override
	public String getFragmentName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onFrageSelect(int index) {
		// TODO Auto-generated method stub

	}
	public List<AppInfoModel> getHot_app_list() {
		if(hot_app_list==null){
			hot_app_list=new ArrayList<AppInfoModel>();
		}
		return hot_app_list;
	}
	public void setHot_app_list(List<AppInfoModel> hot_app_list) {
		this.hot_app_list = hot_app_list;
	}
}
