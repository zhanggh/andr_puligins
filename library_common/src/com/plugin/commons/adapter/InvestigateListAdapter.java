package com.plugin.commons.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.model.InvestigateModel;

public class InvestigateListAdapter extends ZhKdBaseAdapter<InvestigateModel> {
	
	private Context context;
	
	public InvestigateListAdapter(Context context, List<InvestigateModel> data){
		this.context = context;
		this.dataList = data;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final InvestigateModel numList = dataList.get(position);
		View rowView = convertView;
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
}