package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.model.NewsInfoModel;


public class SpecialListAdapter extends ZhKdBaseAdapter<NewsInfoModel> {
	
	private Context context;
	 
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public SpecialListAdapter(Context context, List<NewsInfoModel> newItems){
		this.context = context;
		this.dataList = newItems;
	}

	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final NewsInfoModel news = dataList.get(position);
		View rowView = viewMap.get(news.getId());
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_special, null);
                viewCache = new NewListItemCache(rowView,null,context,news.getId());
                rowView.setTag(viewCache);
                viewMap.put(news.getId()+"", rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        Log.i("ding", news.getTitle());
        viewCache.getTv_desc().setText(news.getDescition());
        viewCache.getTv_title().setText(news.getTitle());
        ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), news.getImg());
        viewCache.getTv_desc().setVisibility(View.VISIBLE);
        return rowView;
	}
 
}
