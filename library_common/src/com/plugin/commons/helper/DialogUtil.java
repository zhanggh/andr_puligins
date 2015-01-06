package com.plugin.commons.helper;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.plugin.R;


/**
 * 常用的弹出框操作
 * @author Vinci
 * @date 2013-8-27 下午04:54:08 
 * @modifylog
 */
public class DialogUtil {
	
	private static Map<Context,ProgressDialog> pDialogMap = new HashMap<Context,ProgressDialog>();
	private static Map<Context,TimeoutProgressDialog> myDialogMap = new HashMap<Context,TimeoutProgressDialog>();
	
	public interface OnAlertSureOnclick {
		/**
		 * 回调函数
		 * @author vinci
		 * @date 2013-3-27 下午03:42:08 
		 * @modifylog   
		 */
		public void onAlertSureOnclick();
	}
	
	public static ProgressDialog showProgressDialog(Context context){
		return showProgressDialog(context,context.getResources().getString(R.string.loading));
	}
	
	public static ProgressDialog showProgressDialog(Context context,String message){
		ProgressDialog pDialog = new ProgressDialog(context);
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setMessage(message == null?context.getResources().getString(R.string.loading):message);
		pDialog.show();
		pDialogMap.put(context, pDialog);
		return pDialog;
	}
	
	public static void closeProgress(Context context){
		if(pDialogMap.containsKey(context)){
			ProgressDialog pDialog = pDialogMap.remove(context);
			if(pDialog!=null){
				try{
					pDialog.dismiss();
				}catch(Exception e){
				}
			}
		}
	}

	public static void showToast(Context context,String msg){
		Toast toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public static void showCommonAlertDialog(Context context,String msg){
		showCommonAlertDialog(context,msg,"");
	}
	public static void showCommonAlertDialog(Context context,String msg,String title){
		final AlertDialog dlg = new AlertDialog.Builder(context).create();
		 dlg.setView(LayoutInflater.from(context).inflate(R.layout.dialog_common_alert, null));
		 final Window window = dlg.getWindow();
		 dlg.show();
		 window.setContentView(R.layout.dialog_common_alert);
		 Button sure = (Button) window.findViewById(R.id.btn_sure);
		 final TextView tv_msg = (TextView)window.findViewById(R.id.tv_msg);
		 final TextView tv_title = (TextView)window.findViewById(R.id.tv_title);
		 tv_msg.setText(msg);
		 tv_title.setText(FuncUtil.isEmpty(title)?context.getResources().getString(R.string.tips):title);
		 sure.setVisibility(View.VISIBLE);
	    Button cancel = (Button) window.findViewById(R.id.btn_close);
	    cancel.setVisibility(View.GONE);
	    sure.setOnClickListener(new View.OnClickListener() {
				   public void onClick(View v) {
					   dlg.cancel();
				 }
	    });
	}
	
	public static void showConfirmAlertDialog(Context context,String msg,String title,
		 final DialogUtil.OnAlertSureOnclick sureListner){
		 final AlertDialog dlg = new AlertDialog.Builder(context).create();
		 dlg.setView(LayoutInflater.from(context).inflate(R.layout.dialog_common_alert, null));
		 final Window window = dlg.getWindow();
		 dlg.show();
		 window.setContentView(R.layout.dialog_common_alert);
		 Button sure = (Button) window.findViewById(R.id.btn_sure);
		 final TextView tv_msg = (TextView)window.findViewById(R.id.tv_msg);
		 final TextView tv_title = (TextView)window.findViewById(R.id.tv_title);
		 tv_msg.setText(msg);
		 tv_title.setText(FuncUtil.isEmpty(title)?context.getResources().getString(R.string.tips):title);
		 sure.setOnClickListener(new View.OnClickListener() {
			   public void onClick(View v) {
				   //et_name
				   if(sureListner!=null){
					   sureListner.onAlertSureOnclick();
				   }
				   dlg.dismiss();
			   }
	    });
		
	    Button cancel = (Button) window.findViewById(R.id.btn_close);
	    cancel.setOnClickListener(new View.OnClickListener() {
				   public void onClick(View v) {
					   dlg.cancel();
				 }
	    });
	}
	public static void showConfirmAlertDialogExt(Context context,String msg,String title,
			final DialogUtil.OnAlertSureOnclick sureListner,final DialogUtil.OnAlertSureOnclick cancleListner){
		final AlertDialog dlg = new AlertDialog.Builder(context).create();
		dlg.setView(LayoutInflater.from(context).inflate(R.layout.dialog_common_alert, null));
		final Window window = dlg.getWindow();
		dlg.show();
		window.setContentView(R.layout.dialog_common_alert);
		Button sure = (Button) window.findViewById(R.id.btn_sure);
		final TextView tv_msg = (TextView)window.findViewById(R.id.tv_msg);
		final TextView tv_title = (TextView)window.findViewById(R.id.tv_title);
		tv_msg.setText(msg);
		tv_title.setText(FuncUtil.isEmpty(title)?context.getResources().getString(R.string.tips):title);
		sure.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//et_name
				if(sureListner!=null){
					sureListner.onAlertSureOnclick();
				}
				dlg.dismiss();
			}
		});
		
		Button cancel = (Button) window.findViewById(R.id.btn_close);
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(cancleListner!=null){
					cancleListner.onAlertSureOnclick();
				}
				dlg.cancel();
			}
		});
	}
	
	public static void showConfirmAlertDialog(Activity context,String msg,String title,
			final DialogUtil.OnAlertSureOnclick sureListner,String cancelStr,String sureStr){
		 final AlertDialog dlg = new AlertDialog.Builder(context).create();
		 dlg.setView(context.getLayoutInflater().inflate(R.layout.dialog_common_alert, null));
		 final Window window = dlg.getWindow();
		 dlg.show();
		 window.setContentView(R.layout.dialog_common_alert);
		 Button sure = (Button) window.findViewById(R.id.btn_sure);
		 final TextView tv_msg = (TextView)window.findViewById(R.id.tv_msg);
		 final TextView tv_title = (TextView)window.findViewById(R.id.tv_title);
		 tv_msg.setText(msg);
		 if(!FuncUtil.isEmpty(sureStr)){
			 sure.setText(sureStr);
		 }
		 tv_title.setText(FuncUtil.isEmpty(title)?context.getResources().getString(R.string.tips):title);
		 sure.setOnClickListener(new View.OnClickListener() {
			   public void onClick(View v) {
				   //et_name
				   if(sureListner!=null){
					   sureListner.onAlertSureOnclick();
				   }
				   dlg.dismiss();
			   }
	    });
		
	    Button cancel = (Button) window.findViewById(R.id.btn_close);
	    if(!FuncUtil.isEmpty(cancelStr)){
	    	cancel.setText(cancelStr);
		 }
	    cancel.setOnClickListener(new View.OnClickListener() {
				   public void onClick(View v) {
					   dlg.cancel();
				 }
	    });
	}
	
	public static void showConfirmAlertDialog(Activity context,String msg){
		showConfirmAlertDialog(context,msg,"",null);
	}
	
	public static TimeoutProgressDialog showTimeoutProgressDialog(Context context,String message,long timeout,TimeoutProgressDialog.OnTimeOutListener
			timeOutListener){
		TimeoutProgressDialog pDialog = new TimeoutProgressDialog(context);
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setMessage(message == null?"Loading":message);
		if(timeOutListener!=null){
			pDialog.setTimeOut(timeout, timeOutListener);
		}
		pDialog.setCancelable(false);
		pDialog.setCanceledOnTouchOutside(false);
		pDialog.show();
		myDialogMap.put(context, pDialog);
		return pDialog;
	}
	
	public static void closeTimoutProgress(Context context){
		if(myDialogMap.containsKey(context)){
			ProgressDialog pDialog = myDialogMap.remove(context);
			if(pDialog!=null){
				try{
					pDialog.dismiss();
				}catch(Exception e){
				}
			}
		}
	}
	
	public static AlertDialog inflaterDialog(Context context,int layoutId){
		 final AlertDialog dlg = new AlertDialog.Builder(context).create();
		 dlg.setView(LayoutInflater.from(context).inflate(layoutId, null));
		 dlg.show();
		 final Window window = dlg.getWindow();
		 window.setContentView(layoutId);
		 return dlg;
	}
	
}
