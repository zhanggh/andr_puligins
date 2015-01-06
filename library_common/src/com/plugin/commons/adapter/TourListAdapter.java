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
import com.plugin.commons.model.TourModel;


/**
 * @author zhang
 *	旅游休闲适配器
 */
public class TourListAdapter extends BaseAdapter {
	
	private Context context;
	private List<TourModel> dataList;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public TourListAdapter(Context context, List<TourModel> tourList){
		this.context = context;
		this.dataList = tourList;
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
		final TourModel tourModel = dataList.get(position);
		View rowView = viewMap.get(tourModel.getId());
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_speciality, null);
                viewCache = new NewListItemCache(rowView,null,context,tourModel.getId());
                rowView.setTag(viewCache);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        viewCache.getTv_title().setText(tourModel.getTitle());
//        viewCache.getTv_desc().setText(specModel.getIntrduce());
        
//        MyApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), pertModel.getUserphoto());
        return rowView;
	}

	public List<TourModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<TourModel> dataList) {
		this.dataList = dataList;
	}

}
