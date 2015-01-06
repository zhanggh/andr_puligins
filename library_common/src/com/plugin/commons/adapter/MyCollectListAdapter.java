package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.ContentBean;


/**
 * @author zhang
 * 我的收藏
 */
public class MyCollectListAdapter extends ZhKdBaseAdapter<ContentBean> {
	
	private Context context;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public MyCollectListAdapter(Context context, List<ContentBean> data){
		this.context = context;
		this.dataList = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ContentBean cols = dataList.get(position);
		View rowView = viewMap.get(cols.getId());
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_my_collect, null);
                viewCache = new NewListItemCache(rowView,null,context,cols.getId());
                rowView.setTag(viewCache);
                viewMap.put(cols.getId()+"", rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        viewCache.getTv_desc().setText(cols.getContent());
        if(FuncUtil.isEmpty(cols.getAttypeName())){
        	viewCache.getTv_title().setText("来自新闻中心");
        }else{
        	viewCache.getTv_title().setText(cols.getAttypeName());
        }
        return rowView;
	}
 
}
