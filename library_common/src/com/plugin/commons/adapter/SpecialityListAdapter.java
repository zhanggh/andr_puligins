package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.NewsInfoModel;


public class SpecialityListAdapter extends BaseAdapter {
	
	private Context context;
	private List<NewsInfoModel> specList;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public SpecialityListAdapter(Context context, List<NewsInfoModel> specList){
		this.context = context;
		this.specList = specList;
	}

	@Override
	public int getCount() {
		return specList.size();
	}

	@Override
	public Object getItem(int position) {		
		return specList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final NewsInfoModel specModel = specList.get(position);
		View rowView = convertView;//viewMap.get(specModel.getId());
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_news, null);
                viewCache = new NewListItemCache(rowView,null,context,specModel.getId());
                viewCache.getIv_image();
                specModel.getDescition();
                specModel.getTitle();
                rowView.setTag(viewCache);
//                viewMap.put(specModel.getId()+"", rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        viewCache.getTv_title().setText(specModel.getTitle());
        viewCache.getTv_desc().setText(FuncUtil.isEmpty(specModel.getDescition())?"暂无介绍":specModel.getDescition());
        
        //解决卡顿的问题
		ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), specModel.getImg());
        return rowView;
	}

	public List<NewsInfoModel> getSpecList() {
		return specList;
	}

	public void setSpecList(List<NewsInfoModel> specList) {
		this.specList = specList;
	}
}
