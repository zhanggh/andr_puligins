package com.plugin.commons.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;


public class ComVideoListAdapter extends ZhKdBaseAdapter<NewsInfoModel> {
	
	private Context context;
	 
	
	public ComVideoListAdapter(Context context, List<NewsInfoModel> newItems){
		this.context = context;
		this.dataList = newItems;
	}

	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NewsInfoModel  news = dataList.get(position);
		View rowView = convertView;
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_coms_video, null);
                viewCache = new NewListItemCache(rowView,null,context,position+"");
                viewCache.getIm_cm_times();
                rowView.setTag(viewCache);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        
        viewCache.getIm_cm_times().setBackgroundDrawable(ComApp.getInstance().getResources().getDrawable(ComApp.getInstance().appStyle.pinglun_btn_selector));
        viewCache.getTv_title().setText(news.getTitle());
        
        if(!FuncUtil.isEmpty(news.getImg())){
       	 //解决卡顿的问题
			 ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), news.getImg());
       }

        return rowView;
	}
}
