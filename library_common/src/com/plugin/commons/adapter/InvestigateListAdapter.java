package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.model.InvestigateModel;

public class InvestigateListAdapter extends BaseAdapter {
	
	private Context context;
	private List<InvestigateModel> dataList;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public InvestigateListAdapter(Context context, List<InvestigateModel> data){
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
		final InvestigateModel numList = dataList.get(position);
		View rowView = viewMap.get(numList.getId());
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_investigate_all_list, null);
                viewCache = new NewListItemCache(rowView,null,context,numList.getId());
                rowView.setTag(viewCache);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        viewCache.getTv_desc().setText(numList.getTitle());
        viewCache.getTv_title().setText(numList.getVoteCount());
        TextView tv_vote_status = (TextView) rowView.findViewById(R.id.tv_vote_status);
		if("1".equals(numList.getStatus())){//已结束
			tv_vote_status.setVisibility(View.VISIBLE);
		}
        return rowView;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<InvestigateModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<InvestigateModel> dataList) {
		this.dataList = dataList;
	}
}