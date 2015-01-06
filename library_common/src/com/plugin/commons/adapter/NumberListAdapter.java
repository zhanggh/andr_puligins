package com.plugin.commons.adapter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.plugin.R;
import com.plugin.commons.model.NumberModel;

public class NumberListAdapter extends BaseAdapter {
	
	private Context context;
	private List<NumberModel> dataList;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public NumberListAdapter(Context context, List<NumberModel> data){
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
		final NumberModel numList = dataList.get(position);
		View rowView = viewMap.get(numList.getId());
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_number_list, null);
                viewCache = new NewListItemCache(rowView,null,context,numList.getId());
                rowView.setTag(viewCache);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        viewCache.getTv_desc().setText(numList.getUsername());
        viewCache.getTv_title().setText(numList.getTelNum());
        ImageView imCall=(ImageView) rowView.findViewById(R.id.im_call);
        imCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 String number = numList.getTelNum();  
				 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));  
				 context.startActivity(intent);  
			}
		});
        return rowView;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<NumberModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<NumberModel> dataList) {
		this.dataList = dataList;
	}
}