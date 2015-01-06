package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.NewsInfoModel;


public class HasSubNewsListAdapter extends BaseAdapter {
	
	private Context context;
	private List<NewsInfoModel> newList;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public HasSubNewsListAdapter(Context context, List<NewsInfoModel> newItems){
		this.context = context;
		this.newList = newItems;
	}

	@Override
	public int getCount() {
		return newList.size();
	}

	@Override
	public Object getItem(int position) {		
		return newList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final NewsInfoModel news = newList.get(position);
		final NewListItemCache viewCache;
		View rowView = viewMap.get(news.getId());
		if(FuncUtil.isEmpty(news.getImg())){
			if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_zhuanti_tx_list, null);
                viewCache = new NewListItemCache(rowView,null,context,news.getId());
                rowView.setTag(viewCache);
                viewMap.put(news.getId(), rowView);
	        } else {
	                viewCache = (NewListItemCache) rowView.getTag();
	        }
			viewCache.getTv_title().setText(news.getTitle());
		}else{
			if (rowView == null) {
	                rowView = LayoutInflater.from(context).inflate(R.layout.item_hassubnews, null);
	                viewCache = new NewListItemCache(rowView,null,context,news.getId());
	                rowView.setTag(viewCache);
	                viewMap.put(news.getId(), rowView);
	        } else {
	                viewCache = (NewListItemCache) rowView.getTag();
	        }
	        viewCache.getTv_title().setText(news.getTitle());
	        ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), news.getImg());
		}
       
        return rowView;
	}
	public List<NewsInfoModel> getNewList() {
		return newList;
	}

	public void setNewList(List<NewsInfoModel> newList) {
		this.newList = newList;
	}
}
