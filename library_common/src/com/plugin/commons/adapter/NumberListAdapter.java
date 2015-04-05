package com.plugin.commons.adapter;


import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.NumberModel;

public class NumberListAdapter extends  ZhKdBaseAdapter<NumberModel> {
	
	private Context context;
	
	public NumberListAdapter(Context context, List<NumberModel> data){
		this.context = context;
		this.dataList = data;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final NumberModel num = dataList.get(position);
		View rowView = convertView;
		final NewListItemCache viewCache;
		if(FuncUtil.isEmpty(num.getImg())){		
			rowView = LayoutInflater.from(context).inflate(R.layout.item_number_list_imgnull, null);
            viewCache = new NewListItemCache(rowView,null,context,num.getId());           
			viewCache.getTv_desc().setText(num.getName());
			viewCache.getTv_title().setText(num.getPhone()!=null?num.getPhone().split(",")[0]:"");		       
		}else{
			if (rowView == null) {
	                rowView = LayoutInflater.from(context).inflate(R.layout.item_number_list, null);
	                viewCache = new NewListItemCache(rowView,null,context,num.getId());
	                rowView.setTag(viewCache);
				} else {
	                viewCache = (NewListItemCache) rowView.getTag();
	        }
	        viewCache.getTv_desc().setText(num.getName());	        
	        viewCache.getTv_title().setText(num.getPhone()!=null?num.getPhone().split(",")[0]:"");	       
	        ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), num.getImg());
	      }		
       
        ImageView imCall=(ImageView) rowView.findViewById(R.id.im_call);
        imCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 String number = num.getPhone();  
				 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));  
				 context.startActivity(intent);  
			}
		});
        return rowView;
	}
}