package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.model.SysNoticeModel;



public class SysNoticeListAdapter extends ZhKdBaseAdapter<SysNoticeModel> {
	
	private Context context;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public SysNoticeListAdapter(Context context, List<SysNoticeModel> data){
		this.context = context;
		this.dataList = data;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final SysNoticeModel cmsList = dataList.get(position);
		View rowView = viewMap.get(cmsList.getId());
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_sysnotice_list, null);
                viewCache = new NewListItemCache(rowView,null,context,cmsList.getId()+"");
                rowView.setTag(viewCache);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        viewCache.getTv_desc().setText(cmsList.getContent());
        viewCache.getTv_title().setText(cmsList.getCreatetime());
         
        return rowView;
	}

	 
}
