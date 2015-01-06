package com.plugin.commons.ui.number;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.plugin.R;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;

public class NumberActivity  extends FragmentActivity{
	private LinearLayout ly_num_gov;
	private LinearLayout ly_num_hos;
	private LinearLayout ly_num_help;
	private LinearLayout ly_num_societyhelp;
	private LinearLayout ly_num_libry;
	private LinearLayout ly_num_museum;
	private LinearLayout ly_num_traffic;
	private LinearLayout ly_num_bank;
	private LinearLayout ly_num_logistics;
	private LinearLayout ly_num_car;
	private LinearLayout ly_num_repair;
	private LinearLayout ly_num_familyhelp;
	private LinearLayout ly_num_move;
	private LinearLayout ly_num_takeout;
	private LinearLayout ly_num_traffic1;
	private LinearLayout ly_num_life;
	

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number);
		ComUtil.customeTitle(this,"号码通",true);
		initViews();
	}
	 
	 
	/**
	 * 政府机构
	 * @param view
	 */
	public void toGovNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_GOV);
		this.startActivity(intent);
	}
	
	/**
	 * 医院机构
	 * @param view
	 */
	public void toHospitalNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_HOSP);
		this.startActivity(intent);
	}
	/**
	 * 救助热线
	 * @param view
	 */
	public void toHotLineNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_HOTHELP);
		this.startActivity(intent);
	}
	
	/**
	 * 社会服务
	 * @param view
	 */
	public void toSocietyHNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_SOCIETYH);
		this.startActivity(intent);
	}
	/**
	* 图书馆
	* @param view
	*/
	public void toLibryNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_LIBRY);
		this.startActivity(intent);
	}
	/**
	 * 博物馆
	 * @param view
	 */
	public void toMseumNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_MSEUM);
		this.startActivity(intent);
	}
	/**
	 * 铁路交通
	 * @param view
	 */
	public void toTrafficNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_TRAFFIC);
		this.startActivity(intent);
	}
	/**
	 * 银行
	 * @param view
	 */
	public void toBankNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_BANK);
		this.startActivity(intent);
	}
	/**
	 * 快递物流
	 * @param view
	 */
	public void toLogsticsNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_LOGSTICS);
		this.startActivity(intent);
	}
	
	/**
	 * 租车
	 * @param view
	 */
	public void toCarNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_RENTAL_CAR);
		this.startActivity(intent);
	}
	/**
	 * 家电维修
	 * @param view
	 */
	public void toRepairNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_FAMILY_REPAIR);
		this.startActivity(intent);
	}
	/**
	 * 家政
	 * @param view
	 */
	public void toFamilyHNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_FAMILY_HELP);
		this.startActivity(intent);
	}
	
	/**
	 * 搬家
	 * @param view
	 */
	public void toFamilyMoveNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_FAMILYMOVE);
		this.startActivity(intent);
	}
	/**
	 * 外卖
	 * @param view
	 */
	public void toTakeOutNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_TAKEOUT);
		this.startActivity(intent);
	}
	
	/**
	 * 铁路航空
	 * @param view
	 */
	public void toTraffic1Num(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_TRAFFIC);
		this.startActivity(intent);
	}
	/**
	 * 生活
	 * @param view
	 */
	public void toLifeNum(View view){
//		DialogUtil.showToast(this, "功能开发中");
		Intent intent = new Intent(this,NumberListActivity.class);
		intent.putExtra(CoreContants.TITLE_PARAM,CoreContants.TITLE_LIFT);
		this.startActivity(intent);
	}
	
	
	
	private void initViews(){
		DisplayMetrics dm = new DisplayMetrics();  
		this.getWindowManager().getDefaultDisplay().getMetrics(dm); 
		int screenWidthDip = dm.widthPixels;        // 屏幕宽（dip，如：320dip）  
		int screenHeightDip = dm.heightPixels;      // 屏幕宽（dip，如：533dip）
		int layoutWd=screenWidthDip/4;
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(layoutWd, 130);
		ly_num_gov =(LinearLayout) this.findViewById(R.id.ly_num_gov);
		ly_num_hos=(LinearLayout) this.findViewById(R.id.ly_num_hos);
		ly_num_help=(LinearLayout) this.findViewById(R.id.ly_num_help);
		ly_num_societyhelp=(LinearLayout) this.findViewById(R.id.ly_num_societyhelp);
		ly_num_libry=(LinearLayout) this.findViewById(R.id.ly_num_libry);
		ly_num_museum=(LinearLayout) this.findViewById(R.id.ly_num_museum);
		ly_num_traffic=(LinearLayout) this.findViewById(R.id.ly_num_traffic);
		ly_num_bank=(LinearLayout) this.findViewById(R.id.ly_num_bank);
		ly_num_logistics=(LinearLayout) this.findViewById(R.id.ly_num_logistics);
		ly_num_car=(LinearLayout) this.findViewById(R.id.ly_num_car);
		ly_num_repair=(LinearLayout) this.findViewById(R.id.ly_num_repair);
		ly_num_familyhelp=(LinearLayout) this.findViewById(R.id.ly_num_familyhelp);
		ly_num_move=(LinearLayout) this.findViewById(R.id.ly_num_move);
		ly_num_takeout=(LinearLayout) this.findViewById(R.id.ly_num_takeout);
		ly_num_traffic1=(LinearLayout) this.findViewById(R.id.ly_num_traffic1);
		ly_num_life=(LinearLayout) this.findViewById(R.id.ly_num_life);
		
		ly_num_gov.setLayoutParams(params);
		ly_num_hos.setLayoutParams(params);
		ly_num_help.setLayoutParams(params);
		ly_num_societyhelp.setLayoutParams(params);
		ly_num_libry.setLayoutParams(params);
		ly_num_museum.setLayoutParams(params);
		ly_num_traffic.setLayoutParams(params);
		ly_num_bank.setLayoutParams(params);
		ly_num_logistics.setLayoutParams(params);
		ly_num_car.setLayoutParams(params);
		ly_num_repair.setLayoutParams(params);
		ly_num_familyhelp.setLayoutParams(params);
		ly_num_move.setLayoutParams(params);
		ly_num_takeout.setLayoutParams(params);
		ly_num_traffic1.setLayoutParams(params);
		ly_num_life.setLayoutParams(params);
		 
	}
}
