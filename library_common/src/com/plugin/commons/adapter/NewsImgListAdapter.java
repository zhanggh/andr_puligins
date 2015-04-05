package com.plugin.commons.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.model.NewsInfoModel;


public class NewsImgListAdapter extends ZhKdBaseAdapter<NewsInfoModel> {
	
	private Context context;
	
	public NewsImgListAdapter(Context context, List<NewsInfoModel> data){
		this.context = context;
		this.dataList = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final NewsInfoModel imgList = dataList.get(position);
		View rowView =convertView;// viewMap.get(cmsList.getId()+""+position);
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_newscomment, null);
                viewCache = new NewListItemCache(rowView,null,context,imgList.getId()+"");
                viewCache.getTv_rpname();
                viewCache.getTv_time();
                viewCache.getTv_desc();
                viewCache.getIv_image();
                rowView.setTag(viewCache);
//                viewMap.put(cmsList.getId()+""+position, rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
         
       
//        //解决卡顿的问题
//		 if(!FuncUtil.isEmpty(cmsList.getUserphoto())){
//			ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), cmsList.getUserphoto());
//		 }
        
//        viewCache.getTv_rpname().setText(cmsList.getUsername());
//        viewCache.getTv_time().setText(cmsList.getCreatetime());
//        viewCache.getTv_desc().setText(cmsList.getContent());
        return rowView;
	}
 
}
