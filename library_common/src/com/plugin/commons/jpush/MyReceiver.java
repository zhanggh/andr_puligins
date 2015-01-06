package com.plugin.commons.jpush;

import java.util.Date;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.google.gson.Gson;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.JpushModel;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.SysNoticeModel;
import com.plugin.commons.ui.base.ActivityWeb;
import com.plugin.commons.ui.main.MainActivity;
import com.plugin.commons.ui.my.SysNotifyActivity;


/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	DingLog log = new DingLog(MyReceiver.class);
	public NotificationManager mNotificationManager;
	private static final String TAG = "JPush";
	
	public MyReceiver() {
		super();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	 int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        	processCustomMessage(context, bundle,notifactionId);
        	
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            
        	//打开自定义的Activity
        	Intent i = new Intent(context, SysNotifyActivity.class);
        	i.putExtras(bundle);
        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        	context.startActivity(i);
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.e(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	// 打印所有的 intent extra 数据
	private String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		SysNoticeModel sysNotic =new SysNoticeModel();
		int index=0;
		if(!ComApp.getInstance().getSpUtil().getReceiveNotic()){
			DialogUtil.showToast(ComApp.getInstance(),"接收推送消息开关状态为关，不接收通知");
		}
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey22222:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey333:" + key + ", value:" + bundle.getBoolean(key));
			} 
			else {
				sb.append("\nkey----:" + key + ", value:" + bundle.getString(key));
			}
			if("cn.jpush.android.ALERT".equals(key)&&ComApp.getInstance().getSpUtil().getReceiveNotic()){
				index++;
				sysNotic.setContent(bundle.getString(key));
				sysNotic.setId(FuncUtil.formatTime(new Date(), "yyyyMMddHHmmss"));
				sysNotic.setCreatetime(FuncUtil.formatTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
				ComApp.getInstance().getSysNotifySv().updatetNotifyList(sysNotic);
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(final Context context, Bundle bundle,int notifactionId) {
		
		/**
		if (MainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		}
		**/
		try
		{
			Intent resultIntent =null;
			String data = bundle.getString(JPushInterface.EXTRA_EXTRA);
			if(!FuncUtil.isEmpty(data)){
				data = data.replace("value:", "");
				Gson gson = new Gson();
				JpushModel jm = (JpushModel)gson.fromJson(data, JpushModel.class);
				if(jm!=null){
					if(CoreContants.NOTIFY_TYPE_NEWS.equals(jm.getType())){
						NewsInfoModel news = new NewsInfoModel();
						news.setArttype(jm.getArttype());
						news.setId(jm.getId());
						news.setContent(jm.getContent());
						news.setDescition(jm.getContent());
						news.setUrl(jm.getShowurl());
						news.setTitle(jm.getTitle());
						resultIntent = new Intent(context,ActivityWeb.class);
						resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						resultIntent.putExtra(CoreContants.PARAMS_NEWS,news);
					}
					else if(CoreContants.NOTIFY_TYPE_SYS.equals(jm.getType())){
						resultIntent = new Intent(context, SysNotifyActivity.class);
						resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
					}
					else{
						resultIntent = new Intent(context, MainActivity.class);
						resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
					}
					String id = bundle.getString("id");
					String title = bundle.getString("title");
					title = FuncUtil.isEmpty(jm.getTitle())?context.getResources().getString(R.string.app_name):jm.getTitle();
					if(CoreContants.NOTIFY_TYPE_SYS.equals(jm.getType())){
						PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
						mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
						log.info(id+";"+title+"------------");
						NotificationCompat.Builder mBuilder;
						mBuilder = new NotificationCompat.Builder(context);
						mBuilder.setContentTitle(title)
								.setContentText(jm.getContent())
								.setTicker(title)//通知首次出现在通知栏，带上升动画效果的
								.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
								.setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
//								.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消  
								.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
								.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
								//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
								.setSmallIcon(ComApp.getInstance().appStyle.jpushlogo);
						mBuilder.setContentIntent(pendingIntent);
						mNotificationManager.notify(notifactionId, mBuilder.build());
					}
					else{
						
						final Intent intent = resultIntent;
						View view =LayoutInflater.from(context).inflate(R.layout.dialog_jpush_alert, null);
						final AlertDialog dlg = new AlertDialog.Builder(context).create();
						dlg.setView(view);
						
						final Window window = dlg.getWindow();
						 window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
						 
						 dlg.show();
//						 WindowManager.LayoutParams lp = window.getAttributes(); 
//						 lp.x = 0;
//						 lp.gravity = Gravity.CENTER;
//			             dlg.onWindowAttributesChanged(lp);  
//						 window.setGravity(Gravity.CENTER);
						 
//						 window.setContentView(R.layout.dialog_jpush_alert);
						 final TextView tv_msg = (TextView)view.findViewById(R.id.tv_msg);
						 final TextView tv_time = (TextView)view.findViewById(R.id.tv_time);
						 final LinearLayout ll_msg = (LinearLayout)view.findViewById(R.id.ll_msg);
						 tv_msg.setText(jm.getContent());
						 tv_time.setText(FuncUtil.formatTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
						 ll_msg.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								context.startActivity(intent);
								dlg.dismiss();
							}
						});
						 //播放提示音
						 Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);  
	                     Ringtone r = RingtoneManager.getRingtone(context, notification);  
	                     r.play();  
					}
					
					/**
					
				    **/
				}
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
