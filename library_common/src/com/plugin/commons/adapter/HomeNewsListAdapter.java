package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;


public class HomeNewsListAdapter extends BaseAdapter {
	
	private Context context;
	private List<List<NewsInfoModel>> newList;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public HomeNewsListAdapter(Context context, List<List<NewsInfoModel>> newItems){
		this.context = context;
		this.newList = newItems;
	}

	@Override
	public int getCount() {
		return newList.size();
	}

	@Override
	public Object getItem(int position) {		
		return newList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final List<NewsInfoModel> news = newList.get(position);
		View rowView = viewMap.get(position+"");
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_homenews, null);
                viewCache = new NewListItemCache(rowView,null,context,position+"");
                rowView.setTag(viewCache);
                viewMap.put(position+"", rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        RelativeLayout rl_new1 = (RelativeLayout)viewCache.getView().findViewById(R.id.rl_new1);
        RelativeLayout rl_new2 = (RelativeLayout)viewCache.getView().findViewById(R.id.rl_new2);
        RelativeLayout rl_new3 = (RelativeLayout)viewCache.getView().findViewById(R.id.rl_new3);
        ImageView iv_image1 = (ImageView)viewCache.getView().findViewById(R.id.iv_image1);
        ImageView iv_image2 = (ImageView)viewCache.getView().findViewById(R.id.iv_image2);
        ImageView iv_image3 = (ImageView)viewCache.getView().findViewById(R.id.iv_image3);
        
        TextView tv_title1 = (TextView)viewCache.getView().findViewById(R.id.tv_title1);
        TextView tv_title2 = (TextView)viewCache.getView().findViewById(R.id.tv_title2);
        TextView tv_title3 = (TextView)viewCache.getView().findViewById(R.id.tv_title3);
        for(int i=0;i<news.size();i++){
        	final NewsInfoModel item = news.get(i);
        	if(item!=null){
        		if(i==0){
            		tv_title1.setText(item.getTitle());
            		ComApp.getInstance().getFinalBitmap().display(iv_image1, item.getImg(),ComApp.getInstance().getLoadingBig(),ComApp.getInstance().getLoadingBig());
            		rl_new1.setOnClickListener(new View.OnClickListener() {
    					
    					@Override
    					public void onClick(View arg0) {
    						// TODO Auto-generated method stub
    						doOnclick(item);
    					}
    				});
            	}
            	else if(i==1){
            		tv_title2.setText(item.getTitle());
            		ComApp.getInstance().getFinalBitmap().display(iv_image2, item.getImg());
            		rl_new2.setOnClickListener(new View.OnClickListener() {
    					
    					@Override
    					public void onClick(View arg0) {
    						// TODO Auto-generated method stub
    						doOnclick(item);
    					}
    				});
            	}
            	else if(i==2){
            		tv_title3.setText(item.getTitle());
            		ComApp.getInstance().getFinalBitmap().display(iv_image3, item.getImg());
            		rl_new3.setOnClickListener(new View.OnClickListener() {
    					
    					@Override
    					public void onClick(View arg0) {
    						// TODO Auto-generated method stub
    						doOnclick(item);
    					}
    				});
            	}
        	}
        }
        return rowView;
	}
	
	private void doOnclick(NewsInfoModel item ){
		NewsTypeModel type = new NewsTypeModel();
		type.setId(item.getArttype());
		type.setType(item.getNewtype());
		type.setHassub("0");
		ComUtil.addViewTimes(context,item,type);
		ComUtil.goNewsDetail(context, item, type);
		XHSDKUtil.addXHBehavior(context,item.getArttype()+"_"+item.getId(), XHConstants.XHTOPIC_ARTICAL_CLICK, item.getId());
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<List<NewsInfoModel>> getNewList() {
		return newList;
	}

	public void setNewList(List<List<NewsInfoModel>> newList) {
		this.newList = newList;
	}

	public static Map<String, View> getViewMap() {
		return viewMap;
	}

	public static void setViewMap(Map<String, View> viewMap) {
		HomeNewsListAdapter.viewMap = viewMap;
	}

}
