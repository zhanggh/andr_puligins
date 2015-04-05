package com.plugin.commons.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
	TextView tv_subtype;
	
	TextView tx_view_times;
	TextView tx_col_times;
	TextView tx_cm_times;
	
	ImageView iv_image1;
	ImageView iv_video1;
	ImageView iv_image2;
	ImageView iv_video2;
	ImageView iv_video;
	
	ImageView img_view_times;
	ImageView im_col_times;
	ImageView im_cm_times;
	GridLayout gridContainer;
	RelativeLayout rl_layout;
	LinearLayout ll_datalist;
	Button btn_area;
	View ll_itemlist;
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
	public ImageView getIv_image1() {
		if(iv_image1==null){
			iv_image1 = (ImageView)view.findViewById(R.id.iv_image1);
		}
		return iv_image1;
	}
	public void setIv_image1(ImageView iv_image1) {
		this.iv_image1 = iv_image1;
	}
	public ImageView getIv_video1() {
		if(iv_video1==null){
			iv_video1 = (ImageView)view.findViewById(R.id.iv_video1);
		}
		return iv_video1;
	}
	public void setIv_video1(ImageView iv_video1) {
		this.iv_video1 = iv_video1;
	}
	public ImageView getIv_image2() {
		if(iv_image2==null){
			iv_image2 = (ImageView)view.findViewById(R.id.iv_image2);
		}
		return iv_image2;
	}
	public void setIv_image2(ImageView iv_image2) {
		this.iv_image2 = iv_image2;
	}
	public ImageView getIv_video2() {
		if(iv_video2==null){
			iv_video2 = (ImageView)view.findViewById(R.id.iv_video2);
		}
		return iv_video2;
	}
	public void setIv_video2(ImageView iv_video2) {
		this.iv_video2 = iv_video2;
	}
	public ImageView getIv_video() {
		if(iv_video==null){
			iv_video = (ImageView)view.findViewById(R.id.iv_video);
		}
		return iv_video;
	}
	public void setIv_video(ImageView iv_video) {
		this.iv_video = iv_video;
	}
	public RelativeLayout getRl_layout() {
		if(rl_layout==null){
			rl_layout=(RelativeLayout) view.findViewById(R.id.rl_layout);
		}
		return rl_layout;
	}
	public void setRl_layout(RelativeLayout rl_layout) {
		this.rl_layout = rl_layout;
	}
	public TextView getTv_subtype() {
		if(tv_subtype==null){
			tv_subtype=(TextView) view.findViewById(R.id.tv_subtype);
		}
		return tv_subtype;
	}
	public void setTv_subtype(TextView tv_subtype) {
		this.tv_subtype = tv_subtype;
	}
	public ImageView getImg_view_times() {
		if(img_view_times==null){
			img_view_times=(ImageView) view.findViewById(R.id.img_view_times);
		}
		return img_view_times;
	}
	public void setImg_view_times(ImageView img_view_times) {
		this.img_view_times = img_view_times;
	}
	public ImageView getIm_col_times() {
		if(im_col_times==null){
			im_col_times=(ImageView) view.findViewById(R.id.im_col_times);
		}
		return im_col_times;
	}
	public void setIm_col_times(ImageView im_col_times) {
		this.im_col_times = im_col_times;
	}
	public ImageView getIm_cm_times() {
		if(im_cm_times==null){
			im_cm_times=(ImageView) view.findViewById(R.id.im_cm_times);
		}
		return im_cm_times;
	}
	public void setIm_cm_times(ImageView im_cm_times) {
		this.im_cm_times = im_cm_times;
	}
	public TextView getTx_view_times() {
		if(tx_view_times==null){
			tx_view_times=(TextView) view.findViewById(R.id.tx_view_times);
		}
		return tx_view_times;
	}
	public void setTx_view_times(TextView tx_view_times) {
		this.tx_view_times = tx_view_times;
	}
	public TextView getTx_col_times() {
		if(tx_col_times==null){
			tx_col_times=(TextView) view.findViewById(R.id.tx_col_times);
		}
		return tx_col_times;
	}
	public void setTx_col_times(TextView tx_col_times) {
		this.tx_col_times = tx_col_times;
	}
	
	public TextView getTx_cm_times() {
		if(tx_cm_times==null){
			tx_cm_times=(TextView) view.findViewById(R.id.tx_cm_times);
		}
		return tx_cm_times;
	}
	public void setTx_cm_times(TextView tx_cm_times) {
		this.tx_cm_times = tx_cm_times;
	}
	public GridLayout getGridContainer() {
		if(gridContainer==null){
//			gridContainer=(GridLayout) view.findViewById(R.id.gridContainer);
		}
		return gridContainer;
	}
	public void setGridContainer(GridLayout gridContainer) {
		this.gridContainer = gridContainer;
	}
	public LinearLayout getLl_datalist() {
		if(ll_datalist==null){
			ll_datalist=(LinearLayout) view.findViewById(R.id.ll_datalist);
		}
		return ll_datalist;
	}
	public void setLl_datalist(LinearLayout ll_datalist) {
		this.ll_datalist = ll_datalist;
	}
	public View getLl_itemlist() {
		return ll_itemlist;
	}
	public void setLl_itemlist(View ll_itemlist) {
		this.ll_itemlist = ll_itemlist;
	}
	public Button getBtn_area() {
		if(btn_area==null){
			btn_area=(Button) view.findViewById(R.id.btn_area);
		}
		return btn_area;
	}
	public void setBtn_area(Button btn_area) {
		this.btn_area = btn_area;
	}
	
}
