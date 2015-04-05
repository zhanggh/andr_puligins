package com.plugin.commons.adapter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.NewsInfoModel;


public class NewsListAdapter extends ZhKdBaseAdapter<NewsInfoModel> {
	
	private Context context;
	 
	private static Map<String,View> viewMap = new HashMap<String,View>();
	public NewsListAdapter(Context context, List<NewsInfoModel> newItems){
		this.context = context;
		this.dataList = newItems;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final NewsInfoModel news = dataList.get(position);
		View rowView = convertView;//viewMap.get(news.getId());
//		View convertView1=convertView;
		 
		final NewListItemCache viewCache;
        if (rowView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.item_news, null);
            viewCache = new NewListItemCache(rowView,null,context,news.getId());
            viewCache.getIv_image();
            viewCache.getTv_desc();
            viewCache.getTv_title();
            viewCache.getTv_subtype();
            rowView.setTag(viewCache);
//            viewMap.put(news.getId(), rowView);
        } else {
            viewCache = (NewListItemCache) rowView.getTag();
        }
//        news.setSubtypename("动态信息");
        if(!FuncUtil.isEmpty(news.getImg())){
//        	//解决卡顿的问题
			 ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), news.getImg());
        }else{
        	viewCache.getRl_layout().setVisibility(View.GONE);
        }
         
        if(!FuncUtil.isEmpty(news.getSubtypename())){
        	viewCache.getTv_subtype().setVisibility(View.VISIBLE);
        	viewCache.getTv_subtype().setText(news.getSubtypename());
        }
        else{
        	viewCache.getTv_subtype().setVisibility(View.GONE);
        }
        
        //摘要
        if(!FuncUtil.isEmpty(news.getDescition())){
        	if(!FuncUtil.isEmpty(news.getTitle())){
//        		int maxEms=viewCache.getTv_title().getMaxEms();
        		viewCache.getTv_title().setText(news.getTitle().replaceAll("\r", "").replaceAll("\n", "").trim());
        		viewCache.getTv_title().setMaxLines(1);
        	}
        	viewCache.getTv_desc().setVisibility(View.VISIBLE);
        	viewCache.getTv_desc().setText(news.getDescition().replaceAll("\r", "").replaceAll("\n", "").trim());
        }else{
        	viewCache.getTv_title().setText(news.getTitle().replaceAll("\r", "").replaceAll("\n", "").trim());
        	viewCache.getTv_desc().setVisibility(View.GONE);
        }
        return rowView;
	}

	 
}
