package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.model.Reply;


public class MyCommentListAdapter extends ZhKdBaseAdapter<Reply> {
	
	private Context context;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public MyCommentListAdapter(Context context, List<Reply> data){
		this.context = context;
		this.dataList = data;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Reply cm = dataList.get(position);
		View rowView = viewMap.get(cm.getArt_id()+position);
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_my_comment, null);
                viewCache = new NewListItemCache(rowView,null,context,cm.getArt_id()+position);
                rowView.setTag(viewCache);
                viewMap.put(cm.getArt_id()+position, rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        viewCache.getTv_desc().setText(cm.getRecontent());
        viewCache.getTv_title().setText(cm.getArt_title());
        viewCache.getTv_time().setText(cm.getReplytime());
        return rowView;
	}
}
