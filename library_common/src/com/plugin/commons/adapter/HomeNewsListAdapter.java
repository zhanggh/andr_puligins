package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.service.NewsService;


public class HomeNewsListAdapter extends BaseAdapter {
	
	private Context context;
	private List<List<NewsInfoModel>> newList;
//	private static Map<String,View> viewMap = new HashMap<String,View>();
	NewsTypeModel mNewType;
	NewsService newsSvc;
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
		View rowView =convertView;// viewMap.get(position+"");
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_homenews, null);
                viewCache = new NewListItemCache(rowView,null,context,position+"");
                rowView.setTag(viewCache);
                viewCache.getLl_datalist();
//                viewMap.put(position+"", rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        RelativeLayout rl_new1 = (RelativeLayout)viewCache.getView().findViewById(R.id.rl_new1);
        ImageView iv_image1 = (ImageView)viewCache.getView().findViewById(R.id.iv_image1);
        TextView tv_title1 = (TextView)viewCache.getView().findViewById(R.id.tv_title1);
        viewCache.getLl_datalist().removeAllViews();
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
            	}else{
            		LinearLayout line=new LinearLayout(context);
            		line.setBackgroundColor(context.getResources().getColor(R.color.line_color));
            		LinearLayout.LayoutParams imagebtn_params = new LinearLayout.LayoutParams(
                             LayoutParams.MATCH_PARENT,2);
            		line.setLayoutParams(imagebtn_params);
            		View rowViewext = LayoutInflater.from(context).inflate(R.layout.item_home_news, null);
            		LinearLayout rl_new = (LinearLayout)rowViewext.findViewById(R.id.rl_new);
        	        ImageView iv_image = (ImageView)rowViewext.findViewById(R.id.iv_image);
        	        TextView tv_title = (TextView)rowViewext.findViewById(R.id.tv_title);
        	        tv_title.setText(item.getTitle());
            		ComApp.getInstance().getFinalBitmap().display(iv_image, item.getImg(),ComApp.getInstance().getLoadingBig(),ComApp.getInstance().getLoadingBig());
            		rl_new.setOnClickListener(new View.OnClickListener() {
    					
    					@Override
    					public void onClick(View arg0) {
    						// TODO Auto-generated method stub
    						doOnclick(item);
    					}
    				});
        	        viewCache.getLl_datalist().addView(rowViewext);
        	        viewCache.getLl_datalist().addView(line);
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

	public NewsTypeModel getmNewType() {
		return mNewType;
	}

	public void setmNewType(NewsTypeModel mNewType) {
		this.mNewType = mNewType;
	}

}
