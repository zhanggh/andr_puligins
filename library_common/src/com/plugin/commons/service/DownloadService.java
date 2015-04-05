package com.plugin.commons.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Iterator;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Environment;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.model.AppInfoModel;


public class DownloadService extends IntentService {
	DingLog log = new DingLog(DownloadService.class);
	public DownloadService() {
		super("ComService");
		log.debug("******************后台服务DownloadService******************");
	}
	
	 
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		log.debug("******************DownloadService后台服务onStartCommand******************");
		return super.onStartCommand(intent, flags, startId);
	}



	@Override
	protected void onHandleIntent(Intent arg0) {
		AppInfoModel appInfo=(AppInfoModel) arg0.getExtras().get(CoreContants.PARAMS_MSG);
		Iterator<AppInfoModel> iterator=ComApp.getInstance().appQue.iterator();
		while(iterator.hasNext()){
			AppInfoModel app=iterator.next();
			if(app.getId().equals(appInfo.getId())){
				log.debug("******************"+appInfo.getName()+"已在下载队列，无需再次下载******************");
				return;
			}
		}
		ComApp.getInstance().appQue.add(appInfo);
		// TODO Auto-generated method stub
		log.debug("service线程号："+Thread.currentThread().getId());
		log.debug("******************后台服务正确进行中******************");
		appInfo=ComApp.getInstance().appQue.element();//永远是取队列第一个
		if(appInfo.getStatus()==CoreContants.DOWNLOAD_STATUS_0){//未下载的方可进行下载
			appInfo.setStatus(CoreContants.DOWNLOAD_STATUS_1);
			downloadApk(appInfo); 	
		}
	}

	
	private void downloadApk(AppInfoModel appInfo){
		String savePath =null;
		String apkFilePath =null;
		String tmpFilePath =null;
		try {
			String apkName = appInfo.getId()+"_"+appInfo.getVersion()+".apk";
			String tmpApk = appInfo.getId()+"_"+appInfo.getVersion()+".tmp";
			//判断是否挂载了SD卡
			String storageState = Environment.getExternalStorageState();		
			if(storageState.equals(Environment.MEDIA_MOUNTED)){
				savePath = ComUtil.getApkDiretion();
				File file = new File(savePath);
				if(!file.exists()){
					file.mkdirs();
				}
				apkFilePath = savePath + apkName;
				tmpFilePath = savePath + tmpApk;
			}
			log.debug("apk目录："+apkFilePath);
			log.debug("apk临时目录："+tmpFilePath);
			//没有挂载SD卡，无法下载文件
			if(apkFilePath == null || apkFilePath == ""){
				ComUtil.showNotification(this,ComApp.getInstance().appStyle.yytb,null,appInfo.getTaskId(),appInfo.getName()+"apk下载失败，没有挂载SD卡，无法下载文件",0,Notification.DEFAULT_VIBRATE);
				return;
			}
			
			File ApkFile = new File(apkFilePath);
			
			//是否已下载更新文件
			if(ApkFile.exists()){
				log.debug(appInfo.getName()+"已存在："+apkFilePath);
				ComApp.getInstance().appQue.remove();
				ComUtil.showNotification(this,ComApp.getInstance().appStyle.yytb,null,appInfo.getTaskId(),appInfo.getName()+"已存在，可直接安装",100,Notification.DEFAULT_LIGHTS);
				ComUtil.installApk(this.getApplication(),apkFilePath);
				return;
			}
			
			//输出临时下载文件
			File tmpFile = new File(tmpFilePath);
			FileOutputStream fos = new FileOutputStream(tmpFile);
			
			URL url = new URL(appInfo.getAppfile());
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.connect();
			int length = conn.getContentLength();
			InputStream is = conn.getInputStream();
			
			//显示文件大小格式：2个小数点显示
	    	DecimalFormat df = new DecimalFormat("0.00");
	    	//进度条下面显示的总文件大小
	    	String apkFileSize = df.format((float) length / 1024 / 1024) + "MB";
			
			int count = 0;
			byte buf[] = new byte[1024];
			
			do{   		   		
	    		int numread = is.read(buf);
	    		count += numread;
	    		//进度条下面显示的当前下载文件大小
	    		String tmpFileSize = df.format((float) count / 1024) + "KB";
	    		//当前进度值
	    	    int progress = (int)(((float)count / length) * 100);
	    	    //更新进度
	    	    if(count%250>=0&&count%250<=2){//注意，系统通知不宜过于频繁，否则严重影响系统性能
	    	    	ComUtil.showNotification(this,ComApp.getInstance().appStyle.yytb,null,appInfo.getTaskId(),appInfo.getName()+apkFileSize+"已下载:"+tmpFileSize,progress,Notification.DEFAULT_LIGHTS);
	    	    }
	    		if(numread <= 0){	
	    			//下载完成 - 将临时下载文件转成APK文件
	    			ComApp.getInstance().appQue.remove();
					if(tmpFile.renameTo(ApkFile)){
						ComUtil.showNotification(this,ComApp.getInstance().appStyle.yytb,null,appInfo.getTaskId(),appInfo.getName()+"下载完成",100,Notification.DEFAULT_VIBRATE);
						//通知安装
						ComUtil.installApk(this.getApplication(),apkFilePath); 
					}
	    			break;
	    		}
	    		fos.write(buf,0,numread);
	    	}while(true);//点击取消就停止下载
			
			fos.close();
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
