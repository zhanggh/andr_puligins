package com.plugin.commons.ui.number;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.NumberModel;

public class NumberDetailActivity  extends FragmentActivity{
	 private NumberModel number;
	 private ImageView iv_img;
	 private TextView tv_desc;
	 private TextView tv_phone;
	 private TextView tv_addr;
	 private LinearLayout ll_call;
	 private LinearLayout ll_addr;
	 private LinearLayout ll_phones;
	

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_MSG)) {
			number =(NumberModel)getIntent().getExtras().get(CoreContants.PARAMS_MSG);
		}	
		if(FuncUtil.isEmpty(number.getImg())){
			setContentView(R.layout.activity_number_detail_imgnull);
		}else{
			setContentView(R.layout.activity_number_detail);
		}
		ComUtil.customeTitle(this,"详情",true);
		initViews();
	}
	private void initViews(){
		iv_img=(ImageView) this.findViewById(R.id.iv_img);
		tv_desc=(TextView) this.findViewById(R.id.tv_desc);
	
		tv_addr=(TextView) this.findViewById(R.id.tv_addr);
		ll_call=(LinearLayout) this.findViewById(R.id.ll_call);
		ll_addr=(LinearLayout) this.findViewById(R.id.ll_addr);
		ll_phones=(LinearLayout) this.findViewById(R.id.ll_phones);
		
		
		if(number!=null){
			ComApp.getInstance().getFinalBitmap().display(iv_img, number.getImg());
			tv_desc.setText(number.getName());						
			if(!FuncUtil.isEmpty(number.getAddr())){
				tv_addr.setText(number.getAddr());
			}else{
				ll_addr.setVisibility(View.GONE);
			}
			refreshList(number.getPhone());	
		}
		
		ll_call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 String num =number.getPhone();  
				 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+num));  
				 NumberDetailActivity.this.startActivity(intent);  
			}
		});
	}
	 
	private void refreshList(String phone){
		//多电话号码处理
		ll_phones.removeAllViews();
		String[] phones=phone.split(",");
		for(String p:phones){
			if(!FuncUtil.isEmpty(p)){
				ll_phones.addView(createProdItem(p));
				//ll_phones.addView(ComUtil.createLineGrey(this));
			}
		}
		
	}
	private View createProdItem(final String p) {
		View itemview = LayoutInflater.from(this).inflate(R.layout.item_phones, null);
		tv_phone=(TextView) itemview.findViewById(R.id.tv_phone);	
		ll_call=(LinearLayout) itemview.findViewById(R.id.ll_call);
		tv_phone.setText(p);
		ll_call.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {  
						 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+p));  
						 NumberDetailActivity.this.startActivity(intent);  
					}
				});
		
		return itemview;
	}
 
}
