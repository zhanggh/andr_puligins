package com.plugin.commons.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.plugin.R;
import com.plugin.commons.model.NumberType;

public class GvNumberAdapter extends ZhKdBaseAdapter<NumberType> {
	
	private Context context;
	 
	public GvNumberAdapter(Context context, List<NumberType> numTypes){
		this.context = context;
		this.dataList = numTypes;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final NumberType num=this.dataList.get(position);
		NewListItemCache viewCache;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(	R.layout.item_num, null);
			viewCache = new NewListItemCache(convertView, null, context, position+ "");
			viewCache.getTv_title();
			convertView.setTag(viewCache);
		} else {
			viewCache = (NewListItemCache) convertView.getTag();
		}		
		viewCache.getTv_title().setText(num.getName());
        return convertView;
	}

}
