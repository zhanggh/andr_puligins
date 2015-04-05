package com.plugin.commons.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.PhotoAndVideoModel;
import com.plugin.commons.ui.pkbk.TopicPhotoActivity;


public class TopicPhotListAdapter extends ZhKdBaseAdapter<List<NewsInfoModel>> {
	
	private Context context;
	NewsTypeModel mNewType;
	public TopicPhotListAdapter(Context context, List<List<NewsInfoModel>> newItems){
		this.context = context;
		this.dataList = newItems;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		List<NewsInfoModel> news = dataList.get(position);
		View rowView = convertView;
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_topic_photo, null);
                viewCache = new NewListItemCache(rowView,null,context,position+"");
                viewCache.getRl_layout();
                rowView.setTag(viewCache);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        
        
        for(int i=0;i<news.size();i++){
        	final NewsInfoModel item = news.get(i);
        	 
        	if(item!=null){
        		if(i==0){
        			TextView tv_title1 = (TextView)viewCache.getView().findViewById(R.id.tv_title1);
        			tv_title1.setText(item.getTitle());
        			ImageView img1=(ImageView) viewCache.getView().findViewById(R.id.iv_image1);
        			ComApp.getInstance().getFinalBitmap().display(img1, item.getImg(),ComApp.getInstance().getLoadingBig(),ComApp.getInstance().getLoadingBig());
        			
        			img1.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intet=new Intent(context,TopicPhotoActivity.class);
							intet.putExtra(CoreContants.PARAMS_NEWS,item);
							intet.putExtra(CoreContants.PARAMS_TYPE,mNewType);
							context.startActivity(intet);
						}
					});
        		}else if(i==1){
        			viewCache.getRl_layout().setVisibility(View.VISIBLE);
        			TextView tv_title2 = (TextView)viewCache.getView().findViewById(R.id.tv_title2);
        			tv_title2.setText(item.getTitle());
        			ImageView img2=(ImageView) viewCache.getView().findViewById(R.id.iv_image2);
        			ComApp.getInstance().getFinalBitmap().display(img2, item.getImg(),ComApp.getInstance().getLoadingBig(),ComApp.getInstance().getLoadingBig());
        			img2.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intet=new Intent(context,TopicPhotoActivity.class);
							intet.putExtra(CoreContants.PARAMS_NEWS,item);
							intet.putExtra(CoreContants.PARAMS_TYPE,mNewType);
							context.startActivity(intet);
						}
					});
        		}
        		 
        	}
        	
        }
        
        return rowView;
	}

	public NewsTypeModel getmNewType() {
		return mNewType;
	}

	public void setmNewType(NewsTypeModel mNewType) {
		this.mNewType = mNewType;
	}
 
}
