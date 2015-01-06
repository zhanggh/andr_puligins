package com.plugin.commons.petition;

import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.model.CommentModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.ui.base.BaseActivity;
import com.plugin.commons.view.CustomDialog;
import com.zq.types.StBaseType;

public class PetitionDetailActivity extends BaseActivity{

	AskMsgModel govDetail;
	TextView tv_reciever;
	TextView tv_time;
	TextView tv_title;
	TextView tv_reply_time;
	TextView tv_reply_context;
	TextView tv_status;
	RelativeLayout ly_reply_cx;
	List<CommentModel> mComments;
	LinearLayout ll_med_pics;
	ImageView iv_myimage1;
	ImageView iv_myimage2;
	ImageView iv_myimage3;
	
	
	AskMsgModel mMsg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_petition_detail);
		ComUtil.customeTitle(this, "信件详情",true);
		govDetail=(AskMsgModel) this.getIntent().getExtras().get(CoreContants.PARAMS_MSG);
		initViews();
		initData();
	}
	private void initViews(){
		tv_reciever = (TextView)this.findViewById(R.id.tv_reciever);
		tv_time = (TextView)this.findViewById(R.id.tv_time);
		tv_title = (TextView)this.findViewById(R.id.tv_email_title);
		tv_reply_time = (TextView)this.findViewById(R.id.tv_reply_time);
		tv_reply_context = (TextView)this.findViewById(R.id.tv_reply_context);
		tv_status = (TextView)this.findViewById(R.id.tv_status);
		ly_reply_cx = (RelativeLayout)this.findViewById(R.id.ly_reply_cx);
		ll_med_pics = (LinearLayout)this.findViewById(R.id.ll_med_pics);
		iv_myimage1 = (ImageView)this.findViewById(R.id.iv_myimage1);
		iv_myimage2 = (ImageView)this.findViewById(R.id.iv_myimage2);
		iv_myimage3 = (ImageView)this.findViewById(R.id.iv_myimage3);
		 
	}
	private void initData(){
		tv_reciever.setText(govDetail.getOrgname());
		tv_time.setText(govDetail.getCreatetime());
		tv_title.setText(govDetail.getContent());
		tv_reply_time.setText(govDetail.getReplytime());
		tv_reply_context.setText(govDetail.getRecontent());
		
		if(!FuncUtil.isEmpty(govDetail.getPhoto())){
			ll_med_pics.setVisibility(View.VISIBLE);
			ComApp.getInstance().getFinalBitmap().display(ll_med_pics,govDetail.getPhoto());
		}
		
		if("0".equals(govDetail.getStatus())){
			tv_status.setText("已受理");
			tv_status.setBackgroundColor(Color.parseColor("#ffc000"));
        }else{
        	tv_status.setText("待受理");
        	tv_status.setBackgroundColor(Color.parseColor("#ff5305"));
        }
		if(!FuncUtil.isEmpty(govDetail.getRecontent())){
			tv_status.setText("已办结");
			tv_status.setBackgroundColor(Color.parseColor("#00965a"));
		}
		if(FuncUtil.isEmpty(govDetail.getRecontent())){
			ly_reply_cx.setVisibility(View.GONE);
		}
		request();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onResume(this);//新华sdk
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPause(this);//新华sdk
	}
	
	private void request(){
		DialogUtil.showProgressDialog(this);
		SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {
			
			@Override
			public StBaseType requestApi() {
				// TODO Auto-generated method stub
				RspResultModel rsp = ComApp.getInstance().getApi().politicsDetail(govDetail.getId());
				return rsp;
			}
			
			@Override
			public void callBack(StBaseType baseType) {
				// TODO Auto-generated method stub
				DialogUtil.closeProgress(PetitionDetailActivity.this);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(PetitionDetailActivity.this, result)){
					mMsg = result.getMsg();
					if(!FuncUtil.isEmpty(mMsg.getPhoto1())){
						ll_med_pics.setVisibility(View.VISIBLE);
						iv_myimage1.setVisibility(View.VISIBLE);
						ComApp.getInstance().getFinalBitmap().display(iv_myimage1,mMsg.getPhoto1());
					}else{
						iv_myimage1.setVisibility(View.GONE);
					}
					if(!FuncUtil.isEmpty(mMsg.getPhoto2())){
						ll_med_pics.setVisibility(View.VISIBLE);
						iv_myimage2.setVisibility(View.VISIBLE);
						ComApp.getInstance().getFinalBitmap().display(iv_myimage2,mMsg.getPhoto2());
					}else{
						iv_myimage2.setVisibility(View.GONE);
					}
					if(!FuncUtil.isEmpty(mMsg.getPhoto3())){
						ll_med_pics.setVisibility(View.VISIBLE);
						iv_myimage3.setVisibility(View.VISIBLE);
						ComApp.getInstance().getFinalBitmap().display(iv_myimage3,mMsg.getPhoto3());
					}else{
						iv_myimage3.setVisibility(View.GONE);
					}
				}
			}
		});
	}
	
	
	public void showImg(View view){
		
		if(view.getId()==R.id.iv_myimage1){
			CustomDialog dialog1 = new CustomDialog(this, R.layout.activity_image_dialog,R.style.dialog,mMsg.getPhoto1());//Dialog使用默认大小(160) 
	        dialog1.show();//显示Dialog
		}else if(view.getId()==R.id.iv_myimage2){
			CustomDialog dialog1 = new CustomDialog(this, R.layout.activity_image_dialog,R.style.dialog,mMsg.getPhoto2());//Dialog使用默认大小(160) 
	        dialog1.show();//显示Dialog
		}else if(view.getId()==R.id.iv_myimage3){
			CustomDialog dialog1 = new CustomDialog(this, R.layout.activity_image_dialog,R.style.dialog,mMsg.getPhoto3());//Dialog使用默认大小(160) 
	        dialog1.show();//显示Dialog
		} 
		
	}
}
