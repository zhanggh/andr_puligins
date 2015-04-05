package com.plugin.commons.ui.pkbk;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.plugin.R;
import com.plugin.commons.ui.base.BaseActivity;

/**
 * @author zhang
 *	显示大图片信息
 */
public class ShowBigImgActivity extends BaseActivity {
	LinearLayout ll_show_imgs;
	Button btn_back;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_img);
		initViews(); 
	}
	
	 
	
	private void initViews(){
		ll_show_imgs=(LinearLayout) this.findViewById(R.id.ll_show_imgs);
		btn_back = (Button) this.findViewById(R.id.btn_back);
	
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShowBigImgActivity.this.finish(); 
			}
		});
	}
}
