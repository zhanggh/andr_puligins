package com.plugin.commons.ui.baoliao;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.model.BaoLiaoInfoModel;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.CommentModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.DetailBarManager;
import com.zq.types.StBaseType;


/**
 * @author zhang
 * 报料详情+评论
 */
public class BaoliaoDetailActivity extends Activity{
	public static String PARAMS_MSG = "msg";
	BaoLiaoInfoModel mMsg;
	Button btn_right;
	List<CommentModel> mComments;
	ImageView iv_image;
	TextView tv_name;
	TextView tv_time;
	TextView tv_content;
	 
	 
	LinearLayout ll_comment;
	ImageView iv_rpimage;
	TextView tv_rpname;
	TextView tv_rptime;
	TextView tv_rpcontent;
	TextView tv_replycount;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baoliaodetail);
		ComUtil.customeTitle(this, "报料详情",true);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAMS_MSG)) {
			mMsg =(BaoLiaoInfoModel)getIntent().getExtras().get(PARAMS_MSG);
		}
		initViews();
		refreshUI();
		request();
	}
	
	
	private void initViews()
	{
		iv_image = (ImageView)this.findViewById(R.id.iv_image);
		iv_image.setBackgroundResource(ComApp.getInstance().appStyle.my_btn_normal);
		tv_name = (TextView)this.findViewById(R.id.tv_name);
		tv_time = (TextView)this.findViewById(R.id.tv_time);
		tv_content = (TextView)this.findViewById(R.id.tv_content);
		 
		ll_comment = (LinearLayout)this.findViewById(R.id.ll_comment);
		iv_rpimage = (ImageView)this.findViewById(R.id.iv_rpimage);
		tv_rpname = (TextView)this.findViewById(R.id.tv_rpname);
		tv_rptime = (TextView)this.findViewById(R.id.tv_rptime);
		tv_rpcontent = (TextView)this.findViewById(R.id.tv_rpcontent);
		tv_replycount = (TextView)this.findViewById(R.id.tv_replycount);
		CacheModel cm = new CacheModel();
		cm.type = CacheModel.CACHE_ASKBAOLIAO;
		cm.id = mMsg.getId()+"";
		cm.msg = mMsg;
		DetailBarManager detailBarMng = new DetailBarManager(this,this.findViewById(R.id.rl_commentbar),cm);
		detailBarMng.setCommtentCB(new DetailBarManager.OnCommentCallBack() {
			
			@Override
			public void onCommentCallBack(Dialog dlg, String comment) {
				final CommentModel cm=new CommentModel();
				cm.setContent(comment);
				cm.setId(Integer.parseInt(mMsg.getId()));
				DialogUtil.showProgressDialog(BaoliaoDetailActivity.this);
				SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {

					@Override
					public StBaseType requestApi() {
						RspResultModel  rsp = ComApp.getInstance().getBlService().commentBaoliao(cm,ComApp.getInstance().getLoginInfo().getSessionid());
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(BaoliaoDetailActivity.this);
						RspResultModel result = (RspResultModel)baseType;
						if(ComUtil.checkRsp(BaoliaoDetailActivity.this, result)){
							if("0".equals(result.getRetcode())){
								DialogUtil.showToast(BaoliaoDetailActivity.this, "评论成功");
								cm.setCreatetime(FuncUtil.formatTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
								cm.setUsername(ComApp.getInstance().getLoginInfo().getUsername());
								cm.setUserid(ComApp.getInstance().getLoginInfo().getUserid());
								cm.setUserphoto(ComApp.getInstance().getLoginInfo().getPhoto());
								mComments.add(cm);
								mMsg.setReplycount((Integer.parseInt(mMsg.getReplycount())+1)+"");
								refreshUI();
							}else{
								DialogUtil.showToast(BaoliaoDetailActivity.this, "评论失败");
							}
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
			tv_name.setText(mMsg.getUsername());
			tv_time.setText(mMsg.getCreatetime());
			tv_content.setText(mMsg.getContent());
			tv_replycount.setText("评论数("+mMsg.getReplycount()+")");
			 
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
		DialogUtil.showProgressDialog(BaoliaoDetailActivity.this);
		SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {
			
			@Override
			public StBaseType requestApi() {
				// TODO Auto-generated method stub
				RspResultModel rsp = ComApp.getInstance().getApi().getBaoliaoDetail(mMsg.getId());
				return rsp;
			}
			
			@Override
			public void callBack(StBaseType baseType) {
				// TODO Auto-generated method stub
				DialogUtil.closeProgress(BaoliaoDetailActivity.this);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(BaoliaoDetailActivity.this, result)){
					mComments = result.getComments();
					refreshUI();
				}
			}
		});
	}
}
