package com.plugin.commons.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.CoreContants;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.PhotoAndVideoModel;


/**
 * @author zhang
 *	图片详情适配器
 */
public class PhotoDTListAdapter extends ZhKdBaseAdapter<PhotoAndVideoModel> {
	
	private Context context;
	 
	
	public PhotoDTListAdapter(Context context, List<PhotoAndVideoModel> newItems){
		this.context = context;
		this.dataList = newItems;
	}

	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PhotoAndVideoModel  news = dataList.get(position);
		View rowView = convertView;
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_detail_photo, null);
                viewCache = new NewListItemCache(rowView,null,context,position+"");
                rowView.setTag(viewCache);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        
//        viewCache.getTv_title().setText(news.getTitle());
        return rowView;
	}
}
