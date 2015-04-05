package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.NewsInfoModel;


public class VideoNewsListAdapter extends BaseAdapter {
	
	private Context context;
	private List<NewsInfoModel> newList;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public VideoNewsListAdapter(Context context, List<NewsInfoModel> newItems){
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
		View rowView =convertView;// viewMap.get(news.getId());
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_news, null);
                viewCache = new NewListItemCache(rowView,null,context,news.getId());
                viewCache.getTv_desc();
                viewCache.getTv_commentcount();
                viewCache.getTv_title();
                viewCache.getTv_local();
                viewCache.getIv_image();
                viewCache.getIv_video();
                rowView.setTag(viewCache);
//                viewMap.put(news.getId()+"", rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
//        Log.i("ding", news.getTitle());
        viewCache.getTv_commentcount().setText(news.getReplycount()+"跟帖");
        //摘要
        if(!FuncUtil.isEmpty(news.getDescition())){
        	if(!FuncUtil.isEmpty(news.getTitle())){
//        		int maxEms=viewCache.getTv_title().getMaxEms();
        		viewCache.getTv_title().setText(news.getTitle().trim());
        		viewCache.getTv_title().setMaxLines(1);
        	}
        	viewCache.getTv_desc().setVisibility(View.VISIBLE);
        	viewCache.getTv_desc().setText(news.getDescition().replaceAll("\r", "").replaceAll("\n", "").trim());
        }else{
        	viewCache.getTv_title().setText(news.getTitle());
        	viewCache.getTv_desc().setVisibility(View.GONE);
        }
        viewCache.getTv_local().setText(news.getLocation());
        
        //解决卡顿的问题
	    ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), news.getImg());
        viewCache.getIv_video().setVisibility(View.VISIBLE);
        return rowView;
	}

	public List<NewsInfoModel> getNewList() {
		return newList;
	}

	public void setNewList(List<NewsInfoModel> newList) {
		this.newList = newList;
	}
}
