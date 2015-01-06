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

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.model.BaoLiaoInfoModel;


public class BaoliaoListAdapter extends BaseAdapter {
	
	private Context context;
	private List<BaoLiaoInfoModel> baoliaoList;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public BaoliaoListAdapter(Context context, List<BaoLiaoInfoModel> baoliaoList){
		this.context = context;
		this.baoliaoList = baoliaoList;
	}

	@Override
	public int getCount() {
		return baoliaoList.size();
	}

	@Override
	public Object getItem(int position) {		
		return baoliaoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final BaoLiaoInfoModel baoliao = baoliaoList.get(position);
		View rowView = viewMap.get(baoliao.getId());
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_baoliao, null);
                viewCache = new NewListItemCache(rowView,null,context,baoliao.getId()+"");
                rowView.setTag(viewCache);
                viewMap.put(baoliao.getId(), rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        
        ImageView iv_image=(ImageView) rowView.findViewById(R.id.iv_image);
        iv_image.setImageResource(ComApp.getInstance().appStyle.my_btn_normal);
        ImageView im_discuss=(ImageView) rowView.findViewById(R.id.im_discuss);
        im_discuss.setImageResource(ComApp.getInstance().appStyle.complain_discuss_btn_normal);
        
//        Log.i("ding", baoliao.getContent());
        viewCache.getTv_commentcount().setText(baoliao.getReplycount());
        viewCache.getTv_desc().setText(baoliao.getContent());
        viewCache.getTv_title().setText(baoliao.getUsername());
        viewCache.getTv_time().setText(baoliao.getCreatetime());
        viewCache.getTv_readcount().setText(baoliao.getViewcount());
        ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), baoliao.getUserphoto());
        return rowView;
	}

	public List<BaoLiaoInfoModel> getBaoliaoList() {
		return baoliaoList;
	}

	public void setBaoliaoList(List<BaoLiaoInfoModel> baoliaoList) {
		this.baoliaoList = baoliaoList;
	}
}
