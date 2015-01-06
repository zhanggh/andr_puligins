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
import com.plugin.commons.model.GovmentInfoModel;


public class AskGovListAdapter extends BaseAdapter {
	
	private Context context;
	private List<GovmentInfoModel> dataList;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public AskGovListAdapter(Context context, List<GovmentInfoModel> data){
		this.context = context;
		this.dataList = data;
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {		
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final GovmentInfoModel item = dataList.get(position);
		View rowView = viewMap.get(item.getId());
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_askgov, null);
                viewCache = new NewListItemCache(rowView,null,context,item.getId());
                rowView.setTag(viewCache);
                viewMap.put(item.getId(), rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        viewCache.getTv_desc().setText(item.getDesc());
        viewCache.getTv_title().setText(item.getName());
        ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), item.getPhoto());
        return rowView;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<GovmentInfoModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<GovmentInfoModel> dataList) {
		this.dataList = dataList;
	}
}
