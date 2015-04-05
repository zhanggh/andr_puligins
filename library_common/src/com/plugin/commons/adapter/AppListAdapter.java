package com.plugin.commons.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.AppInfoModel;


public class AppListAdapter extends ZhKdBaseAdapter<AppInfoModel> {
	
	private Context context;
	 
	public AppListAdapter(Context context, List<AppInfoModel> newItems){
		this.context = context;
		this.dataList = newItems;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final AppInfoModel appinfo = dataList.get(position);
		View rowView = convertView; 
		final NewListItemCache viewCache;
        if (rowView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.item_applist, null);
            viewCache = new NewListItemCache(rowView,null,context,appinfo.getId());
            viewCache.getIv_image();
            viewCache.getTv_title();
            rowView.setTag(viewCache);
        } else {
            viewCache = (NewListItemCache) rowView.getTag();
        }
        if(!FuncUtil.isEmpty(appinfo.getLogo())){
//        	//解决卡顿的问题
			 ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(),appinfo.getLogo());
        } 
         
        if(!FuncUtil.isEmpty(appinfo.getName())){
        	viewCache.getTv_title().setText(appinfo.getName());
        }
        return rowView;
	}

	 
}
