package com.plugin.commons.adapter;

import java.util.ArrayList;
import java.util.List;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author zhang
 * 基础adapter
 * @param <T>
 */
public class ZhKdBaseAdapter<T>  extends BaseAdapter {
	public boolean isSpec=false;
	public List<T> dataList;
	
	 
	@Override
	public int getCount() {
		if(dataList==null)
			dataList=new ArrayList<T>();
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		if(dataList==null)
			dataList=new ArrayList<T>();
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return super.getViewTypeCount();
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}
