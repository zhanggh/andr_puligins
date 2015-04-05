package com.plugin.commons.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.model.AreaModel;

public class GvAreaAdapter extends ZhKdBaseAdapter<AreaModel> {
	
	private Context context;
	 
	public GvAreaAdapter(Context context, List<AreaModel> list){
		this.context = context;
		this.dataList = list;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final AreaModel area=this.dataList.get(position);
		NewListItemCache viewCache;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(	R.layout.item_areas, null);
			viewCache = new NewListItemCache(convertView, null, context, position+ "");
			viewCache.getBtn_area();
			convertView.setTag(viewCache);
		} else {
			viewCache = (NewListItemCache) convertView.getTag();
		}		
		viewCache.getBtn_area().setText(area.getName());
        return convertView;
	}

}
