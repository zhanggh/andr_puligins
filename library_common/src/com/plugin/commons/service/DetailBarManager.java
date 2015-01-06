package com.plugin.commons.service;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.user.LoginActivity;



/**
 * 详细页面 分享 评论 关注bar的管理
 * @Description:
 * @author:vinci
 * @time:2014-8-23 下午12:16:58
 */
public class DetailBarManager {
	private Context context;
	private View view;
	private OnCommentCallBack commtentCB;
	public CacheModel cacheModel;
	
	LinearLayout ll_addcomment;
	Button btn_fav;
	Button btn_share;
	
	public DetailBarManager(Context context,View barView,CacheModel cacheModel){
		this.context = context;
		this.view = barView;
		this.cacheModel = cacheModel;
		ll_addcomment = (LinearLayout)view.findViewById(R.id.ll_addcomment);
		btn_fav = (Button)view.findViewById(R.id.btn_fav);
//		btn_share = (Button)view.findViewById(R.id.btn_share);
		
	}
	
	public void refreshUI(){
		CacheModel cm = CacheDataService.getAcction(cacheModel.type, cacheModel.id);
		btn_fav.setSelected(cm!=null);
		btn_fav.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				CacheModel cm = CacheDataService.getAcction(cacheModel.type, cacheModel.id);
				if(cm==null){
					XHSDKUtil.addXHBehavior(context, cacheModel.id+"", XHConstants.XHTOPIC_ARTICAL_FAV, cacheModel.type+"");
					CacheDataService.addAcction(cacheModel);
					btn_fav.setSelected(true);
					DialogUtil.showToast(context, "收藏成功");
				}
				else{
					XHSDKUtil.addXHBehavior(context, cacheModel.id+"", XHConstants.XHTOPIC_ARTICAL_FAV, cacheModel.type+"");
					CacheDataService.cancelAcction(cacheModel.type, cacheModel.id+"");
					btn_fav.setSelected(false);
					DialogUtil.showToast(context, "取消收藏");
				}
			}
		});
		ll_addcomment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!ComApp.getInstance().isLogin()){
					Intent intent = new Intent(context,LoginActivity.class);
					intent.putExtra(CoreContants.PARAM_BACK, CoreContants.PARAM_BACK);
					context.startActivity(intent);
					return ;
				}
				final Dialog dlg = new Dialog(context, R.style.dialog);
				dlg.setContentView(R.layout.view_comment);
				dlg.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				dlg.getWindow().setWindowAnimations(R.style.dialog_up);
				dlg.getWindow().setGravity(Gravity.BOTTOM);
				//dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				dlg.show();
				 Button sure = (Button) dlg.findViewById(R.id.btn_sure);
				 Button cancel = (Button) dlg.findViewById(R.id.btn_close);
				 sure.setBackgroundResource(ComApp.getInstance().appStyle.btn_comment_sure_selector);
				 cancel.setBackgroundResource(ComApp.getInstance().appStyle.btn_dialogcancel_selector);
				 final EditText et_comment = (EditText)dlg.findViewById(R.id.et_comment);
				 et_comment.setFocusable(true);
			     sure.setOnClickListener(new View.OnClickListener() {
					   public void onClick(View v) {
						   String comment = et_comment.getText().toString();
						   if(FuncUtil.isEmpty(comment)){
							   DialogUtil.showToast(context, "评论内容不能为空");
							   return ;
						   }
						   dlg.cancel();
						   if(commtentCB!=null){
							   commtentCB.onCommentCallBack(dlg, comment);
						   }
					}
			    });
			    cancel.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dlg.cancel();
					}
				});
			}
		});
	}
	
	public void cancelAddAcctction(int id){
		
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public OnCommentCallBack getCommtentCB() {
		return commtentCB;
	}

	public void setCommtentCB(OnCommentCallBack commtentCB) {
		this.commtentCB = commtentCB;
	}



	/**
	 * 评论的回调
	 * @Description:
	 * @author:vinci
	 * @time:2014-8-23 下午12:18:40
	 */
	public interface OnCommentCallBack {
		public void onCommentCallBack(Dialog dlg,String comment);
	}
	
	
	

}
