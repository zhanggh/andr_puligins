package com.plugin.commons.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.plugin.R;


public class NewListItemCache {
	Context context;
	View view;
	String code;
	BaseAdapter adapter;
	TextView tv_title;
	TextView tv_desc;
	TextView tv_commentcount;
	TextView tv_readcount;
	TextView tv_local;
	ImageView iv_image;
	TextView tv_time;
	TextView tv_rpname;
	TextView tv_status;
	public NewListItemCache(View baseView,BaseAdapter adapter,Context mContext,String code){
		this.view = baseView;
		this.adapter = adapter;
		this.context = mContext;
		this.code = code;
	}
	public BaseAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(BaseAdapter adapter) {
		this.adapter = adapter;
	}
	
	public View getView() {
		return view;
	}
	public void setView(View view) {
		this.view = view;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public TextView getTv_desc() {
		if(null==tv_desc)
			tv_desc = (TextView)view.findViewById(R.id.tv_desc);
		return tv_desc;
	}
	public void setTv_desc(TextView tv_desc) {
		this.tv_desc = tv_desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public TextView getTv_title() {
		if(null==tv_title)
			tv_title = (TextView)view.findViewById(R.id.tv_title);
		return tv_title;
	}
	public void setTv_title(TextView tv_title) {
		this.tv_title = tv_title;
	}
	public TextView getTv_commentcount() {
		if(null==tv_commentcount)
			tv_commentcount = (TextView)view.findViewById(R.id.tv_commentcount);
		return tv_commentcount;
	}
	public void setTv_commentcount(TextView tv_commentcount) {
		this.tv_commentcount = tv_commentcount;
	}
	public TextView getTv_local() {
		if(null==tv_local)
			tv_local = (TextView)view.findViewById(R.id.tv_local);
		return tv_local;
	}
	public void setTv_local(TextView tv_local) {
		this.tv_local = tv_local;
	}
	public ImageView getIv_image() {
		if(null==iv_image)
			iv_image = (ImageView)view.findViewById(R.id.iv_image);
		return iv_image;
	}
	public void setIv_image(ImageView iv_image) {
		this.iv_image = iv_image;
	}
	public TextView getTv_readcount() {
		if(tv_readcount==null){
			tv_readcount = (TextView)view.findViewById(R.id.tv_readcount);
		}
		return tv_readcount;
	}
	public void setTv_readcount(TextView tv_readcount) {
		this.tv_readcount = tv_readcount;
	}
	public TextView getTv_time() {
		if(tv_time==null){
			tv_time = (TextView)view.findViewById(R.id.tv_time);
		}
		return tv_time;
	}
	public void setTv_time(TextView tv_time) {
		this.tv_time = tv_time;
	}
	public TextView getTv_status() {
		if(tv_status==null){
			tv_status = (TextView)view.findViewById(R.id.tv_status);
		}
		return tv_status;
	}
	public void setTv_status(TextView tv_status) {
		this.tv_status = tv_status;
	}
	public TextView getTv_rpname() {
		if(tv_rpname==null){
			tv_rpname = (TextView)view.findViewById(R.id.tv_rpname);
		}
		return tv_rpname;
	}
	public void setTv_rpname(TextView tv_rpname) {
		this.tv_rpname = tv_rpname;
	}
	
}
