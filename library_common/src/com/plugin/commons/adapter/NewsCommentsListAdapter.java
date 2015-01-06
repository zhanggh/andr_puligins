package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.CommentModel;


public class NewsCommentsListAdapter extends ZhKdBaseAdapter<CommentModel> {
	
	private Context context;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public NewsCommentsListAdapter(Context context, List<CommentModel> data){
		this.context = context;
		this.dataList = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final CommentModel cmsList = dataList.get(position);
		View rowView = viewMap.get(cmsList.getId()+""+position);
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_newscomment, null);
                viewCache = new NewListItemCache(rowView,null,context,cmsList.getId()+"");
                rowView.setTag(viewCache);
                viewMap.put(cmsList.getId()+""+position, rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
         
        if(!FuncUtil.isEmpty(cmsList.getUserphoto())){
			ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), cmsList.getUserphoto());
		}
        viewCache.getTv_rpname().setText(cmsList.getUsername());
        viewCache.getTv_time().setText(cmsList.getCreatetime());
        viewCache.getTv_desc().setText(cmsList.getContent());
        return rowView;
	}
 
}
