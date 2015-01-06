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
import com.plugin.commons.model.BaoLiaoInfoModel;


public class MyBaoliaoListAdapter extends BaseAdapter {
	
	private Context context;
	private List<BaoLiaoInfoModel> dataList;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public MyBaoliaoListAdapter(Context context, List<BaoLiaoInfoModel> data){
		this.context = context;
		this.dataList = data;
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {		
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final BaoLiaoInfoModel news = dataList.get(position);
		View rowView = viewMap.get(news.getId()+"");
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_my_baoliao, null);
                viewCache = new NewListItemCache(rowView,null,context,news.getId()+"");
                rowView.setTag(viewCache);
                viewMap.put(news.getId()+"", rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        viewCache.getTv_desc().setText(news.getContent());
        viewCache.getTv_title().setText(news.getUsername());
         
        return rowView;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<BaoLiaoInfoModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<BaoLiaoInfoModel> dataList) {
		this.dataList = dataList;
	}
}
