package com.plugin.commons.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.AskMsgModel;


public class AskHotListAdapter extends BaseAdapter {
	
	private Context context;
	private List<AskMsgModel> dataList;
	private static Map<String,View> viewMap = new HashMap<String,View>();
	
	public AskHotListAdapter(Context context, List<AskMsgModel> data){
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
		final AskMsgModel news = dataList.get(position);
		View rowView = viewMap.get(news.getId()+"");
		final NewListItemCache viewCache;
        if (rowView == null) {
                rowView = LayoutInflater.from(context).inflate(R.layout.item_askhot, null);
                viewCache = new NewListItemCache(rowView,null,context,news.getId()+"");
                rowView.setTag(viewCache);
                viewMap.put(news.getId(), rowView);
        } else {
                viewCache = (NewListItemCache) rowView.getTag();
        }
        ImageView iv_image=(ImageView) rowView.findViewById(R.id.iv_image);
        iv_image.setImageResource(ComApp.getInstance().getAppStyle().my_btn_normal);
        ImageView im_discuss=(ImageView) rowView.findViewById(R.id.im_discuss);
        im_discuss.setImageResource(ComApp.getInstance().appStyle.complain_discuss_btn_normal);
        
        viewCache.getTv_commentcount().setText(FuncUtil.isEmpty(news.getReplycount())?"0":news.getReplycount()+"");
        viewCache.getTv_desc().setText(news.getContent());
        viewCache.getTv_title().setText(Html.fromHtml(news.getUsername()+" 向  <font color=#138ef8>"+news.getOrgname()+"</font> 提问"));
        viewCache.getTv_time().setText(news.getCreatetime());
        viewCache.getTv_readcount().setText(FuncUtil.isEmpty(news.getViewcount())?"0":news.getViewcount()+"");
        ComApp.getInstance().getFinalBitmap().display(viewCache.getIv_image(), news.getUserphoto());
        return rowView;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<AskMsgModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<AskMsgModel> dataList) {
		this.dataList = dataList;
	}
}
