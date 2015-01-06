package com.plugin.commons.ui.askgov;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.CommentModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.AskGovService;
import com.plugin.commons.service.AskGovServiceImpl;
import com.plugin.commons.service.DetailBarManager;
import com.zq.types.StBaseType;


public class AskDetailActivity extends Activity{
	AskMsgModel mMsg;
	Button btn_right;
	List<CommentModel> mComments;
	ImageView iv_image;
	TextView tv_name;
	TextView tv_time;
	TextView tv_content;
	ImageView iv_org;
	TextView tv_orgname;
	LinearLayout ll_comment;
	ImageView iv_rpimage;
	TextView tv_rpname;
	TextView tv_rptime;
	TextView tv_rpcontent;
	TextView tv_replycount;
	AskGovService askSvc;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_askdetail);
		ComUtil.customeTitle(this, "信件详情",true);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_MSG)) {
			mMsg =(AskMsgModel)getIntent().getExtras().get(CoreContants.PARAMS_MSG);
		}
		askSvc = new AskGovServiceImpl();
		initViews();
		refreshUI();
		request();
	}
	
	
	private void initViews()
	{
		iv_image = (ImageView)this.findViewById(R.id.iv_image);
		iv_image.setImageResource(ComApp.getInstance().appStyle.my_btn_normal);
		
		tv_name = (TextView)this.findViewById(R.id.tv_name);
		tv_time = (TextView)this.findViewById(R.id.tv_time);
		tv_content = (TextView)this.findViewById(R.id.tv_content);
		iv_org = (ImageView)this.findViewById(R.id.iv_org);
		iv_org.setImageResource(ComApp.getInstance().appStyle.my_btn_normal);
		
		tv_orgname = (TextView)this.findViewById(R.id.tv_orgname);
		ll_comment = (LinearLayout)this.findViewById(R.id.ll_comment);
		iv_rpimage = (ImageView)this.findViewById(R.id.iv_rpimage);
		iv_rpimage.setImageResource(ComApp.getInstance().appStyle.my_btn_normal);
		
		tv_rpname = (TextView)this.findViewById(R.id.tv_rpname);
		tv_rptime = (TextView)this.findViewById(R.id.tv_rptime);
		tv_rpcontent = (TextView)this.findViewById(R.id.tv_rpcontent);
		tv_replycount = (TextView)this.findViewById(R.id.tv_replycount);
		CacheModel cm = new CacheModel();
		cm.type = CacheModel.CACHE_ASKGOV;
		cm.id = mMsg.getId();
		cm.msg = mMsg;
		DetailBarManager detailBarMng = new DetailBarManager(this,this.findViewById(R.id.rl_commentbar),cm);
		detailBarMng.setCommtentCB(new DetailBarManager.OnCommentCallBack() {
			
			@Override
			public void onCommentCallBack(Dialog dlg, final String comment) {
				// TODO Auto-generated method stub
				DialogUtil.showProgressDialog(AskDetailActivity.this);
				SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {
					
					@Override
					public StBaseType requestApi() {
						// TODO Auto-generated method stub
						RspResultModel rsp = ComApp.getInstance().getApi().politicsComment(mMsg.getId(),comment);
						return rsp;
					}
					
					@Override
					public void callBack(StBaseType baseType) {
						// TODO Auto-generated method stub
						DialogUtil.closeProgress(AskDetailActivity.this);
						RspResultModel result = (RspResultModel)baseType;
						if(ComUtil.checkRsp(AskDetailActivity.this, result)){
							DialogUtil.showToast(AskDetailActivity.this, "评论成功");
							CommentModel cm = new CommentModel();
							cm.setContent(comment);
							cm.setCreatetime(FuncUtil.DTREAD.format(new Date()));
							cm.setId(0);
							cm.setOrgid(mMsg.getOrgid());
							cm.setUserid(ComApp.getInstance().getLoginInfo().getUserid());
							cm.setUsername(ComApp.getInstance().getLoginInfo().getUsername());
							cm.setUserphoto(ComApp.getInstance().getLoginInfo().getPhoto());
							if(mComments==null){
								mComments = new ArrayList<CommentModel>();
							}
							if(mComments.size()==0){
								mComments.add(cm);
								refreshUI();
							}
							else{
								List<CommentModel> list = new ArrayList<CommentModel>();
								list.add(cm);
								for(CommentModel cmd:mComments){
									list.add(cmd);
								}
								mComments = list;
								refreshUI();
							}
							//mComments = result.getComments();
							//mMsg = result.getMsg();
							//refreshUI();
							//request();
						}
					}
				});
				
			}
		});
		detailBarMng.refreshUI();
		
	}
	
	private void refreshUI()
	{
		if(mMsg!=null){
			ComApp.getInstance().getFinalBitmap().display(iv_image, mMsg.getUserphoto());
			tv_name.setText(Html.fromHtml(mMsg.getUsername()+" 向  <font color=#138ef8>"+mMsg.getOrgname()+"</font> 提问"));
			tv_time.setText(mMsg.getCreatetime());
			tv_content.setText(mMsg.getContent());
			tv_replycount.setText("评论数("+(FuncUtil.isEmpty(mComments)?"0":mComments.size())+")");
			ComApp.getInstance().getFinalBitmap().display(iv_org, mMsg.getOrgphoto());
			if(FuncUtil.isEmpty(mMsg.getRecontent())){
				//如果没有回答
				ComApp.getInstance().getFinalBitmap().display(iv_org, mMsg.getOrgphoto());
				tv_orgname.setText(mMsg.getOrgname());
				this.findViewById(R.id.ll_noreply).setVisibility(View.VISIBLE);
				this.findViewById(R.id.ll_waitreply).setVisibility(View.VISIBLE);
				this.findViewById(R.id.ll_reply).setVisibility(View.GONE);
			}
			else{
				this.findViewById(R.id.ll_noreply).setVisibility(View.GONE);
				this.findViewById(R.id.ll_waitreply).setVisibility(View.GONE);
				this.findViewById(R.id.ll_reply).setVisibility(View.VISIBLE);
				
				ComApp.getInstance().getFinalBitmap().display(iv_rpimage, mMsg.getOrgphoto());
				tv_rpname.setText(mMsg.getOrgname());
				tv_rptime.setText(mMsg.getReplytime());
				tv_rpcontent.setText(mMsg.getRecontent());
			}
			if(!FuncUtil.isEmpty(mComments)){
				ll_comment.removeAllViews();
				for(int i=0;i<mComments.size();i++){
					ll_comment.addView(createCommentItem(mComments.get(i)));
					ll_comment.addView(ComUtil.createLineGrey(this));
				}
			}
		}
	}
	
	private View createCommentItem(CommentModel comment){
		View view = LayoutInflater.from(this).inflate(R.layout.item_askanswer, null);
		ImageView iv_rpimage = (ImageView)view.findViewById(R.id.iv_rpimage);
		TextView tv_rpname = (TextView)view.findViewById(R.id.tv_rpname);
		TextView tv_rptime = (TextView)view.findViewById(R.id.tv_rptime);
		TextView tv_rpcontent = (TextView)view.findViewById(R.id.tv_rpcontent);
		
		ComApp.getInstance().getFinalBitmap().display(iv_rpimage, comment.getUserphoto());
		tv_rpname.setText(comment.getUsername());
		tv_rptime.setText(comment.getCreatetime());
		tv_rpcontent.setText(comment.getContent());
		return view;
	}
	
	
	private void request(){
		DialogUtil.showProgressDialog(AskDetailActivity.this);
		SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {
			
			@Override
			public StBaseType requestApi() {
				// TODO Auto-generated method stub
				RspResultModel rsp = ComApp.getInstance().getApi().politicsDetail(mMsg.getId());
				return rsp;
			}
			
			@Override
			public void callBack(StBaseType baseType) {
				// TODO Auto-generated method stub
				DialogUtil.closeProgress(AskDetailActivity.this);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(AskDetailActivity.this, result)){
					mComments = result.getComments();
					mMsg = result.getMsg();
					refreshUI();
				}
			}
		});
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

}
