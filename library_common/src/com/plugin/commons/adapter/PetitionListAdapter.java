package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.AskMsgModel;


@SuppressLint("ResourceAsColor")
public class PetitionListAdapter extends ZhKdBaseAdapter<AskMsgModel> {
	
	private Context context;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	public PetitionListAdapter(Context context, List<AskMsgModel> dataList){
		this.context = context;
		this.dataList= dataList;
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
        if(CoreContants.APP_LNZX.equals(ComApp.APP_NAME)){
        	if("0".equals(pertModel.getMsgtype())){
                viewCache.getTv_status().setText("提案");
              }else{
                viewCache.getTv_status().setText("社情民意");
              }
        }else{
        	if("0".equals(pertModel.getStatus())){
                viewCache.getTv_status().setText("已受理");
                viewCache.getTv_status().setBackgroundColor(Color.parseColor("#ffc000"));
              }else{
                viewCache.getTv_status().setText("待受理");
                viewCache.getTv_status().setBackgroundColor(Color.parseColor("#ff5305"));
              }
          	if(!FuncUtil.isEmpty(pertModel.getRecontent())){
          		 viewCache.getTv_status().setText("已办结");
          		 viewCache.getTv_status().setBackgroundColor(Color.parseColor("#00965a"));
      		}
        }
        return rowView;
	}
 
}
