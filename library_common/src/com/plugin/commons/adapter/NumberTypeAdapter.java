package com.plugin.commons.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


import android.widget.LinearLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.model.NumberType;
import com.plugin.commons.ui.baoliao.BaoliaoDetailActivity;
import com.plugin.commons.ui.number.NumberListActivity;



public class NumberTypeAdapter extends   ZhKdBaseAdapter<NumberType> {
	
	private Context context;
	 
	public NumberTypeAdapter(Context context, List<NumberType> numTypes){
		this.context = context;
		this.dataList = numTypes;
	}
	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    final NumberType number=dataList.get(position);
	    List<List<NumberType> > groupType=new ArrayList<List<NumberType> >();
	  
		View rowView = convertView;		
		final NewListItemCache viewCache;
		if (rowView == null) {
			rowView = LayoutInflater.from(context).inflate(R.layout.item_number_ext, null);
			viewCache = new NewListItemCache(rowView, null, context, position+ "");
			viewCache.getTv_title();
			viewCache.getLl_datalist();
			rowView.setTag(viewCache);
		} else {
			viewCache = (NewListItemCache) rowView.getTag();
		}	
		viewCache.getTv_title().setText(number.getName());
		viewCache.getLl_datalist().removeAllViews();
		//组装数据
		List<NumberType> temList = new ArrayList<NumberType>();
		int index=0;
		boolean flag=false;//分组标识
		for(NumberType num:number.getSubtypes()){
			temList.add(num);
			index++;
			if(index%3==0){//8个元素为一组
				groupType.add(temList);
				temList=new ArrayList<NumberType>();
				flag=true;
			}else{
				flag=false;
			}
		}
		if(!flag){
			groupType.add(temList);
		}
		//描绘视图
		drowView(viewCache.getLl_datalist(),groupType);
		
        return rowView;
	}

	 
	class ItemOnclickListener implements OnClickListener{

		NumberType numType;
		LinearLayout dataList;
		List<List<NumberType> > groupType;
		ItemOnclickListener(NumberType numType){
			this.numType=numType;
		}
		
		ItemOnclickListener(LinearLayout dataList,List<List<NumberType> > groupType){
			this.dataList=dataList;
			this.groupType=groupType;
		}
		
		@Override
		public void onClick(View v) {
			String flag=(String) v.getTag(R.id.tv_title3);
			if(v.getId()==R.id.tv_title3&&"more".equals(flag)){
				this.dataList.removeAllViews();
				drowFullView(this.dataList,this.groupType);
			}else{
				Intent intent=new Intent(context ,NumberListActivity.class);
				intent.putExtra(CoreContants.PARAMS_MSG,numType);
				intent.putExtra(CoreContants.PARAM_CODE, "");
				context.startActivity(intent);
			}

		}
	}
	
	private void drowView(LinearLayout dataList,List<List<NumberType> > groupType){
		int lineNum=0;
		for(List<NumberType> temL:groupType){
			View chiView=LayoutInflater.from(context).inflate(R.layout.item_num, null);
			TextView tv1=(TextView) chiView.findViewById(R.id.tv_title1);
			TextView tv2=(TextView) chiView.findViewById(R.id.tv_title2);
			TextView tv3=(TextView) chiView.findViewById(R.id.tv_title3);
			lineNum++;
			if(lineNum>3){
				chiView.setVisibility(View.GONE);
			}else if(lineNum==3){
				for(int a=0;a<temL.size();a++){
					if(a==0){
						tv1.setText(temL.get(a).getName());	
						tv1.setOnClickListener(new ItemOnclickListener(temL.get(a)));
						tv1.setVisibility(View.VISIBLE);
					}
					if(a==1){
						tv2.setOnClickListener(new ItemOnclickListener(temL.get(a)));
						tv2.setText(temL.get(a).getName());	
						tv2.setVisibility(View.VISIBLE);
					}
					if(a==2){
						tv3.setVisibility(View.VISIBLE);
						tv3.setOnClickListener(new ItemOnclickListener(dataList,groupType));
						tv3.setText("更多>>");
						tv3.setTag(R.id.tv_title3, "more");
						tv3.setTextColor(ComApp.getInstance().getResources().getColor(R.color.light_blue));
					}
				}
			}else{
				for(int a=0;a<temL.size();a++){
					if(a==0){
						tv1.setVisibility(View.VISIBLE);
						tv1.setText(temL.get(a).getName());	
						tv1.setOnClickListener(new ItemOnclickListener(temL.get(a)));
					}
					if(a==1){
						tv2.setVisibility(View.VISIBLE);
						tv2.setOnClickListener(new ItemOnclickListener(temL.get(a)));
						tv2.setText(temL.get(a).getName());	
					}
					if(a==2){
						tv3.setVisibility(View.VISIBLE);
						tv3.setText(temL.get(a).getName());	
						tv3.setOnClickListener(new ItemOnclickListener(temL.get(a)));
					}
				}
			}
			dataList.addView(chiView);
		}
		
	}
	private void drowFullView(LinearLayout dataList,List<List<NumberType> > groupType){
		for(List<NumberType> temL:groupType){
			View chiView=LayoutInflater.from(context).inflate(R.layout.item_num, null);
			TextView tv1=(TextView) chiView.findViewById(R.id.tv_title1);
			TextView tv2=(TextView) chiView.findViewById(R.id.tv_title2);
			TextView tv3=(TextView) chiView.findViewById(R.id.tv_title3);
			for(int a=0;a<temL.size();a++){
				if(a==0){
					tv1.setVisibility(View.VISIBLE);
					tv1.setText(temL.get(a).getName());	
					tv1.setOnClickListener(new ItemOnclickListener(temL.get(a)));
				}
				if(a==1){
					tv2.setVisibility(View.VISIBLE);
					tv2.setOnClickListener(new ItemOnclickListener(temL.get(a)));
					tv2.setText(temL.get(a).getName());	
				}
				if(a==2){
					tv3.setVisibility(View.VISIBLE);
					tv3.setText(temL.get(a).getName());	
					tv3.setOnClickListener(new ItemOnclickListener(temL.get(a)));
				}
			}
			dataList.addView(chiView);
		}
		
	}
}
