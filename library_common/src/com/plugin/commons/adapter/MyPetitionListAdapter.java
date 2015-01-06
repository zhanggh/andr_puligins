package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.AskMsgModel;

@SuppressLint("ResourceAsColor")
public class MyPetitionListAdapter extends ZhKdBaseAdapter<AskMsgModel> {
	
	private Context context;
	 
	private static Map<String,View> viewMap = new HashMap<String,View>();
	 
	public MyPetitionListAdapter(Context context, List<AskMsgModel> pertitionList){
		this.context = context;
		this.dataList = pertitionList;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final AskMsgModel pertModel = dataList.get(position);
		View rowView = viewMap.get(pertModel.getId());
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_petition, null);
                viewCache = new NewListItemCache(rowView,null,context,pertModel.getId()+"");
                rowView.setTag(viewCache);
                viewMap.put(pertModel.getId()+"", rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        viewCache.getTv_time().setText(pertModel.getCreatetime());
        String desc=FuncUtil.getPexfStr(pertModel.getContent(), 20, "...");
        viewCache.getTv_desc().setText(desc);
        viewCache.getTv_status().setVisibility(View.GONE);
        
//        MyApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), pertModel.getUserphoto());
        return rowView;
	}

	 
}
