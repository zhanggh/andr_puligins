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
		View rowView = viewMap.get(news.getId());
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_news, null);
                viewCache = new NewListItemCache(rowView,null,context,news.getId());
                rowView.setTag(viewCache);
                viewMap.put(news.getId(), rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        //news.setSubtypename("动态信息");
        TextView tv_subtype = (TextView)rowView.findViewById(R.id.tv_subtype);
       // viewCache.getTv_commentcount().setText(news.getReplycount()+"跟帖");
        //viewCache.getTv_desc().setText(news.getDescition());
        viewCache.getTv_title().setText(news.getTitle());
        //viewCache.getTv_local().setText(news.getLocation());
        ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), news.getImg());
        if(!FuncUtil.isEmpty(news.getSubtypename())){
        	tv_subtype.setVisibility(View.VISIBLE);
        	tv_subtype.setText(news.getSubtypename());
        }
        else{
        	tv_subtype.setVisibility(View.GONE);
        }
        return rowView;
	}

	 
}
