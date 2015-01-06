package com.plugin.commons.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.model.NavDrawerItem;


public class NavDrawerListAdapter extends BaseAdapter {
	
	private Context context;
	private List<NavDrawerItem> navDrawerItems;
	
	public NavDrawerListAdapter(Context context, List<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_left_menu, null);
        }
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        txtTitle.setTextColor(ComApp.getInstance().getResources().getColor(ComApp.getInstance().appStyle.menu_title_color));
        imgIcon.setImageResource(navDrawerItems.get(position).getIcon()); 
        if("生态旅游实验区".equals(navDrawerItems.get(position).getTitle())){
        	txtTitle.setSingleLine(false);
        	txtTitle.setText("生态旅游\n实验区");
        }else{
        	txtTitle.setText(navDrawerItems.get(position).getTitle());
        }
        return convertView;
	}

}
