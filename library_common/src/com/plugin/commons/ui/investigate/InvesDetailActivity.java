package com.plugin.commons.ui.investigate;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;

public class InvesDetailActivity  extends Activity{
	DingLog log = new DingLog(InvesDetailActivity.class);
	public static String PARAMS_ORG = "org";
	Button btn_vote;
	LinearLayout ly_share;
	LinearLayout ly_option;
	TextView tv_vote_status;
	String status="0";//火热进行中
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_investigate_detail);
		ComUtil.customeTitle(this, "调查",true);
		status = this.getIntent().getStringExtra(PARAMS_ORG);
		initViews();
//		refreshUI();
	}
	
	private void initViews(){
		btn_vote=(Button) this.findViewById(R.id.btn_vote);
		ly_share=(LinearLayout) this.findViewById(R.id.ly_share);
		ly_share.setBackgroundResource(ComApp.getInstance().appStyle.btn_inves_share_selector);
		ly_option=(LinearLayout) this.findViewById(R.id.ly_option);
		
	}
	
	
	public void toVote(View view){
		DialogUtil.showToast(this, "投票成功");
		btn_vote.setBackgroundResource(R.drawable.vote_btn_unavailable);
		final LayoutInflater inflater = LayoutInflater.from(this);
		ly_option.removeAllViews();
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.item_vote_result_list, null);
		ly_option.addView(layout);
	}
	public void toShareVote(View view){
		DialogUtil.showToast(this, "分享成功");
		ly_share.setBackgroundResource(ComApp.getInstance().appStyle.investigate_share_btn_press);
	}
}
