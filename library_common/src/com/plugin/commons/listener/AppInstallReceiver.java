package com.plugin.commons.listener;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;

/**
 * @author zhang
 *	应用安装监听器
 */
public class AppInstallReceiver extends BroadcastReceiver {
	DingLog log = new DingLog(AppInstallReceiver.class);
    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName =null;
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "安装成功"+packageName, Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "卸载成功"+packageName, Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "替换成功"+packageName, Toast.LENGTH_LONG).show();
        }
        if(!FuncUtil.isEmpty(packageName)){
        	removeInstalledApk(context,packageName);
        }
    }
    
    /**
     * 删除已安装完成的apk
     */
    private void removeInstalledApk(Context context,String pkname){
    	 try {
			String archiveFilePath=ComUtil.getApkDiretion();//安装包路径  
			 PackageManager pm = context.getPackageManager();   
			 File file=new File(archiveFilePath);
			 File removeFile=null;
			 PackageInfo info = null;
			 for(String fn:file.list()){
				 log.debug("apk:"+fn);
				 info = pm.getPackageArchiveInfo(archiveFilePath+fn, PackageManager.GET_ACTIVITIES);    
			     if(info != null){    
			         ApplicationInfo appInfo = info.applicationInfo;    
//			         String appName = pm.getApplicationLabel(appInfo).toString();    
			         String packageName = appInfo.packageName;  //得到安装包名称  
			         log.debug("apk packageName:"+packageName);
			         if(pkname.equals(packageName)){
			        	 removeFile=new File(archiveFilePath+fn);
			        	 removeFile.delete();
			        	 log.debug("删除apk:"+archiveFilePath+fn);
			         }
			     } 
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
    }

}