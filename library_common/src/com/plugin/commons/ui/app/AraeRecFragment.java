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
import android.widget.Button;
import android.widget.LinearLayout;

import com.plugin.R;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.model.AreaModel;
import com.plugin.commons.ui.fragment.base.BaseFragment;

/**
 * @author zhang
 *	地方应用推荐
 */
@SuppressLint("ResourceAsColor")
public class AraeRecFragment extends BaseFragment {

	
	private List<AreaModel> area_list;//地区列表
	private LinearLayout ll_datalist;
	private boolean last;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.info(getFragmentName(), " onCreateView()");
		View view = inflater.inflate(R.layout.activity_app_area_rec, container, false);
		return view;
	}
	protected  void initViews(View view){
		ll_datalist=(LinearLayout) view.findViewById(R.id.ll_datalist);
		createViews(false);
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
	public List<AreaModel> getArea_list() {
		if(area_list==null){
			area_list=new ArrayList<AreaModel>();
		}
		return area_list;
	}
	public void setArea_list(List<AreaModel> area_list) {
		this.area_list = area_list;
	}
	
	/**
	 * flag true的时候，全部展开
	 * @param flag
	 */
	private void createViews(boolean flag){
		int less=(this.getArea_list().size()+1)%4;
		int lineNum=((this.getArea_list().size()+1)/4)+(less==0?0:1);
		int showLine=2;//显示的行数
		if(lineNum<2||flag){
			showLine=lineNum;
		}
		int showSize=4;
		int itemWidth = ComUtil.getWindowWidth(mActivity)/showSize-30;//将屏幕分成4份，并减去前后margin
		itemWidth = itemWidth>140?140:itemWidth;//如果大于140*140，则最多140，如果小于，则取width值
		int height = mActivity.getResources().getDimensionPixelSize(R.dimen.height_areaapp);
		for(int a=0;a<showLine;a++){
			LinearLayout itemLy=new LinearLayout(getActivity());
			itemLy.setId(2000+a);
			itemLy.setOrientation(LinearLayout.HORIZONTAL);
			itemLy.setPadding(1, 1, 1, 1);
			itemLy.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,100);
			itemLy.setLayoutParams(params);
			for(int b=0;b<showSize;b++){
				ColorStateList csl = (ColorStateList) getActivity().getResources().getColorStateList(R.color.black);
				Button btn=new Button(getActivity());
				btn.setId(3000+a*4+b);
				int index=a*4+b;
				if(index<this.getArea_list().size()){
					final AreaModel area=this.getArea_list().get(index);
					if(a==1&&b==3&&!flag){
						btn.setId(3999);
						btn.setText("更多>>");
						csl = (ColorStateList) getActivity().getResources().getColorStateList(R.color.more);
						btn.setTextColor(csl);
					}else if(a==showLine-1&&b==showSize-1){
						btn.setId(4000);
						btn.setText("收起<<");
						csl = (ColorStateList) getActivity().getResources().getColorStateList(R.color.more);
						btn.setTextColor(csl);
					}else{
						if(a==showLine-1&&index==this.getArea_list().size()-1){
							last=true;
						}
						btn.setText(area.getName());
						btn.setTextColor(csl);
					}
					LinearLayout.LayoutParams btnParams=new LinearLayout.LayoutParams(0,height,1);
					btnParams.setMargins(10, 15, 10, 15);
					btn.setLayoutParams(btnParams);
					btn.setBackgroundResource(R.drawable.btn_area_selector);
					btn.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
					itemLy.addView(btn);
					btn.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = null;
							 if(v.getId()==3999){
								 ll_datalist.removeAllViews();
								 createViews(true);
							 }else if(v.getId()==4000){
								 ll_datalist.removeAllViews();
								 createViews(false);
							 }else{
								 intent=new Intent(getActivity(), AppListActivity.class);
								 intent.putExtra(CoreContants.PARAMS_MSG,area);
								 intent.putExtra(CoreContants.PARAMS_TITLE,area.getName());
								 getActivity().startActivity(intent);
							 }
						}
					});
				}else if(last){
					last=false;
					//不足4个的情况
					for(int c=0;c<(showSize-less+1);c++){
						csl = (ColorStateList) getActivity().getResources().getColorStateList(R.color.black);
						btn=new Button(getActivity());
						btn.setId(5000+a*4+c);
						LinearLayout.LayoutParams btnParams=new LinearLayout.LayoutParams(0,height,1);
						btnParams.setMargins(10, 15, 10, 15);
						btn.setLayoutParams(btnParams);
						btn.setBackgroundResource(R.drawable.btn_area_selector);
						btn.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
						btn.setTextColor(csl);
						if(c==0&&!flag){
							btn.setId(3999);
							btn.setText("更多 >>");
							csl = (ColorStateList) getActivity().getResources().getColorStateList(R.color.more);
							btn.setTextColor(csl);
						}else if(c==0&&flag){
							btn.setId(4000);
							btn.setText("收起<<");
							csl = (ColorStateList) getActivity().getResources().getColorStateList(R.color.more);
							btn.setTextColor(csl);
						}else{
							btn.setVisibility(View.INVISIBLE);
						}
						itemLy.addView(btn);
						btn.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Intent intent = null;
								 if(v.getId()==3999){
									 ll_datalist.removeAllViews();
									 createViews(true);
								 }else if(v.getId()==4000){
									 last=false;
									 ll_datalist.removeAllViews();
									 createViews(false);
								 }
							}
						});
					}
				}
			}
			ll_datalist.addView(itemLy);
		}
	}
}
