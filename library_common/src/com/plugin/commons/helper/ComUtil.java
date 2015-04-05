package com.plugin.commons.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.AppConfig;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.AppInfoModel;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.CacheDataService;
import com.plugin.commons.service.ComService;
import com.plugin.commons.service.DetailBarManager;
import com.plugin.commons.service.DownloadService;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.SmartWeatherService;
import com.plugin.commons.service.SmartWeatherServiceImpl;
import com.plugin.commons.ui.base.ActivityWeb;
import com.plugin.commons.ui.base.ActivityWebExt;
import com.plugin.commons.ui.base.VideoOLActivity;
import com.plugin.commons.ui.base.WaitingActivity;
import com.plugin.commons.ui.base.WebActivity;
import com.plugin.commons.ui.news.NewsImageActivity;
import com.plugin.commons.ui.news.SubNewsActivity;
import com.zq.types.StBaseType;



public class ComUtil {
	static DingLog log = new DingLog(ComUtil.class);
 
	 
	 public static void customeTitle(final Activity context,String title)
	 {
		 customeTitle(context,title,false);
	 }
	 
	 public static void customeTitle(final Activity context,String title,boolean isLeft)
	 {
	    	context.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
	    			R.layout.common_title);
			
			TextView tvTitle=(TextView)context.findViewById(R.id.tv_title);
			tvTitle.setText(title);
			tvTitle.setTextColor(context.getResources().getColor(ComApp.getInstance().appStyle.title_text_color));
			context.findViewById(R.id.ll_title).setBackgroundColor(ComApp.getInstance().getResources().getColor(ComApp.getInstance().appStyle.title_bg_color));
//			RelativeLayout titleLy=(RelativeLayout) 
//			titleLy.setBackgroundColor(ComApp.getInstance().getResources().getColor(ComApp.getInstance().appStyle.title_bg_color));
			Button btn_title_left=(Button) context.findViewById(R.id.btn_title_left);
			btn_title_left.setBackgroundResource(ComApp.getInstance().appStyle.btn_back_selector);
			Button btn_title_right=(Button) context.findViewById(R.id.btn_title_right);
			btn_title_right.setBackgroundResource(ComApp.getInstance().appStyle.btn_my_selector);
			if(isLeft){
				
				btn_title_left.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						//关闭上一个activity
						if(ComApp.getInstance().getSpUtil().getCommentRet()){
							Intent caseIntent=new Intent(CoreContants.ACTIVITY_RETTRUN);
							context.sendBroadcast(caseIntent);
						}
						context.finish();
					}
				});
			}
			
	 }
	
	 public static boolean checkRsp(final Context c, RspResultModel rsp) {
		 return checkRsp(c,rsp,true);
	}
	 
	 public static boolean checkRsp(final Context c, RspResultModel rsp,boolean showToast) {
			if (rsp == null) {
				log.info("为空");
				if(showToast){
					DialogUtil.showToast(c, c.getResources().getString(R.string.rsp_err));
				}
				return false;
			}
			log.info(rsp.getRetcode()+";"+rsp.getRetmsg());
			if ("0".equals(rsp.getRetcode())) {
				return true;
			} 
			else{
				if(showToast){
					DialogUtil.showToast(c,FuncUtil.isEmpty(rsp.getRetmsg())?c.getResources().getString(R.string.rsp_err):rsp.getRetmsg() );
				}
				return false;
			}
		}
	 
	 public static View createLineBlack(Context context) {
			LayoutInflater mInflater = LayoutInflater.from(context);
			View view = mInflater.inflate(R.layout.line_b, null);
			return view;
	 }
	 
	 public static View createLineGrey(Context context) {
			LayoutInflater mInflater = LayoutInflater.from(context);
			View view = mInflater.inflate(R.layout.line_grey, null);
			return view;
	 }
	 public static String saveImage(Context context,String url,int width,int height){
	    	DisplayMetrics dm = new android.util.DisplayMetrics();
	        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
	    	if(url!=null&&url.indexOf("/")>-1){
	    		try{
					//显示选择或者拍照的图片
					File file = new File(url);
					if(file.exists()){
						log.info("image exists");
						final BitmapFactory.Options options = new BitmapFactory.Options();
				        options.inJustDecodeBounds = true;
				        BitmapFactory.decodeFile(url, options);
				        options.inPurgeable = true;
				        options.inSampleSize = ComUtil.calculateInSampleSize(options, width,
				        		height);
				        log.info("计算出来缩放比例------------:"+options.inSampleSize);
				        if(options.inSampleSize<1){
				        	options.inSampleSize = 1;
				        }else{
				        	options.inSampleSize=options.inSampleSize+1;
				        }
				        options.inJustDecodeBounds = false;
				        Bitmap bitmap = BitmapFactory.decodeFile(url,options);
						log.info("size---------:"+options.inSampleSize+"");
						if(null!=bitmap){
							String saveDir = Environment.getExternalStorageDirectory()
							+ "/"+ComApp.APP_NAME+"/";
							File dir = new File(saveDir);
							if (!dir.exists()) {
								dir.mkdir();
							}
							String filename = url.substring(url.lastIndexOf("/"));
							filename = saveDir+"/"+getFilename();
							File fileSave=new File(filename); 
					        try { 
					        	log.info("保存");
					            FileOutputStream out=new FileOutputStream(fileSave); 
					            if(bitmap.compress(Bitmap.CompressFormat.PNG,50, out)){ 
					                out.flush(); 
					                out.close(); 
					                if(bitmap!=null){
					                	bitmap.recycle();
					                	bitmap=null;
					                }
					                return filename;
					            } 
					        } catch (FileNotFoundException e) { 
					            // TODO Auto-generated catch block 
					            e.printStackTrace(); 
					        } catch (IOException e) { 
					            // TODO Auto-generated catch block 
					            e.printStackTrace(); 
					        } 
						}
						
					}
						
				}
				catch(Exception e){
					//DialogUtil.showToast(mContext, "获取图片失败！");
					log.info("获取文件失败:"+e.getMessage());
				}
	    	}
	    	return "";
	 }
	 public static String getFilename(){
	    	String time = FuncUtil.YMDHMS.format(FuncUtil.getCurrTimestamp());
	    	return "v"+time+".jpg";
	    }
	 
	 /**
	     * 获取图片缓存
	     * @author vinci
	     * @date 2013-8-7 上午11:00:39 
	     * @modifylog   
	     * @param options
	     * @param reqWidth
	     * @param reqHeight
	     * @return
	     */
	    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
	        final int height = options.outHeight;
	        final int width = options.outWidth;
	        int inSampleSize = 1;

	        if (height > reqHeight || width > reqWidth) {
	            if (width > height) {
	                inSampleSize = Math.round((float) height / (float) reqHeight);
	            } else {
	                inSampleSize = Math.round((float) width / (float) reqWidth);
	            }

	            final float totalPixels = width * height;

	            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

	            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
	                inSampleSize++;
	            }
	        }
	        return inSampleSize;
	    }
	    
	    public static boolean showPhoto(Context context,String url,ImageView iv,Bitmap bitmap){
	    	boolean isImageValue = true;
	    	try{
				//显示选择或者拍照的图片
				File file = new File(url);
				if(file.exists()){
			        bitmap = BitmapFactory.decodeFile(url);
					if(null!=bitmap){
						iv.setImageBitmap(bitmap);
						isImageValue = true;
					}
				}
					
			}
			catch(Exception e){
				log.info("获取文件失败:"+e.getMessage());
			}
			return isImageValue;
	    }
 	    /**
	     * 获取屏幕高度
	     * @author vinci
	     * @date 2013-9-17 上午10:38:26 
	     * @modifylog   
	     * @param context
	     * @return
	     */
		public static int getWindowWidth(Context context){
			DisplayMetrics dm = new android.util.DisplayMetrics();
	        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
	        return dm.widthPixels;
		}
		
		   /**
	     * 获取屏幕高度
	     * @author vinci
	     * @date 2013-9-17 上午10:38:26 
	     * @modifylog   
	     * @param context
	     * @return
	     */
		public static int getWindowHeight(Context context){
			DisplayMetrics dm = new android.util.DisplayMetrics();
	        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
	        return dm.heightPixels;
		}
		
		/**
		 * 获取视频的缩略图
		 * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
		 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
		 * @param videoPath 视频的路径
		 * @param width 指定输出视频缩略图的宽度
		 * @param height 指定输出视频缩略图的高度度
		 * @param kind 参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
		 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
		 * @return 指定大小的视频缩略图
		 */
		public static Bitmap getVideoThumbnail(String videoPath, int width, int height,
				int kind) {
			Bitmap bitmap = null;
			// 获取视频的缩略图
			bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			return bitmap;
		}
		
		public static String getTimeIntervalString(Date date){
			String str = "";
			long last = date.getTime();
			long now = System.currentTimeMillis();
			if(last>0)
			{
				long interval = now-last;
				if(interval<1000*60){
					str += "新发表";//原来是"刚刚",林跃说改成”新发表“
				}
				else if(interval<1000*60*60){
					str += (interval/(1000*60)+1)+"分钟前";
				}
				else if(interval<1000*60*60*24){
					str +=(interval/(1000*60*60)+1)+"小时前";
				}
				else{
					str += (interval/(1000*60*60*24)+1)+"天前";
				}
			}
			return str;
		}
		
		public static String getTimeIntervalString(String dateStr){
			if(FuncUtil.isEmpty(dateStr)||!dateStr.contains(":"))
				return dateStr;
			String str = "";
			long now = System.currentTimeMillis();
			long last = now;
			try {
				last = FuncUtil.parseTime(dateStr, "yyyy-MM-dd HH:mm:ss").getTime();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(last>0)
			{
				long interval = now-last;
				if(interval<1000*60){
					str += "新发表";
				}
				else if(interval<1000*60*60){
					str += (interval/(1000*60)+1)+"分钟前";
				}
				else if(interval<1000*60*60*24){
					str +=(interval/(1000*60*60)+1)+"小时前";
				}
				else{
					str += (interval/(1000*60*60*24)+1)+"天前";
				}
			}
			return str;
		}
		
		public static void showListNone(View view,String tips,List data){
			if(view==null||view.findViewById(R.id.ll_none)==null||view.findViewById(R.id.tv_tips)==null)
				return ;
			if(data==null||data.size()==0)
			{
				view.findViewById(R.id.ll_none).setVisibility(View.VISIBLE);
				((TextView)view.findViewById(R.id.tv_tips)).setText(tips);
			}
			else{
				view.findViewById(R.id.ll_none).setVisibility(View.GONE);
			}
		}
		
		public static void showListNone(View view,String tips,List data,List head){
			if(view==null||view.findViewById(R.id.ll_none)==null||view.findViewById(R.id.tv_tips)==null)
				return ;
			if(FuncUtil.isEmpty(head)&&FuncUtil.isEmpty(data))
			{
				view.findViewById(R.id.ll_none).setVisibility(View.VISIBLE);
				((TextView)view.findViewById(R.id.tv_tips)).setText(tips);
			}
			else{
				view.findViewById(R.id.ll_none).setVisibility(View.GONE);
			}
		}
		
		public static void showListNone(View view,List data){
			if(view==null||view.findViewById(R.id.ll_none)==null||view.findViewById(R.id.tv_tips)==null)
				return ;
			if(data==null||data.size()==0)
			{
				view.findViewById(R.id.ll_none).setVisibility(View.VISIBLE);
			}
			else{
				view.findViewById(R.id.ll_none).setVisibility(View.GONE);
			}
		}

		/**
		 * 功能：最严格的身份证验证 2014-7-6
		 */
		public static boolean idcardVerify(String certificateNo) {
			// String certificateNo = "61072919761109762X";//身份证号码
			if (certificateNo.length() == 15) {
				System.err.println("身份证号码无效，请使用第二代身份证！！！");
				return false;
			} else if (certificateNo.length() == 18) {
				String address = certificateNo.substring(0, 6);// 6位，地区代码
				String birthday = certificateNo.substring(6, 14);// 8位，出生日期
				String sequenceCode = certificateNo.substring(14, 17);// 3位，顺序码：奇为男，偶为女
				String checkCode = certificateNo.substring(17);// 1位，校验码：检验位
				System.out.println("身份证号码:" + certificateNo + "、地区代码:" + address
						+ "、出生日期:" + birthday + "、顺序码:" + sequenceCode + "、校验码:"
						+ checkCode + "\n");
				String[] provinceArray = { "11:北京", "12:天津", "13:河北", "14:山西",
						"15:内蒙古", "21:辽宁", "22:吉林", "23:黑龙江", "31:上海", "32:江苏",
						"33:浙江", "34:安徽", "35:福建", "36:江西", "37:山东", "41:河南",
						"42:湖北 ", "43:湖南", "44:广东", "45:广西", "46:海南", "50:重庆",
						"51:四川", "52:贵州", "53:云南", "54:西藏 ", "61:陕西", "62:甘肃",
						"63:青海", "64:宁夏", "65:新疆", "71:台湾", "81:香港", "82:澳门",
						"91:国外" };
				boolean valideAddress = false;
				for (int i = 0; i < provinceArray.length; i++) {
					String provinceKey = provinceArray[i].split(":")[0];
					if (provinceKey.equals(address.substring(0, 2))) {
						valideAddress = true;
					}
				}
				if (valideAddress) {
					String year = birthday.substring(0, 4);
					String month = birthday.substring(4, 6);
					String day = birthday.substring(6);
					Date tempDate = new Date(Integer.parseInt(year),
							Integer.parseInt(month) - 1, Integer.parseInt(day));
					if ((tempDate.getYear() != Integer.parseInt(year)
							|| tempDate.getMonth() != Integer.parseInt(month) - 1 || tempDate
							.getDate() != Integer.parseInt(day))) {// 避免千年虫问题
						System.err.println("身份证号码无效，请重新输入！！！");
					} else {
						int[] weightedFactors = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7,
								9, 10, 5, 8, 4, 2, 1 };// 加权因子
						int[] valideCode = { 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 };// 身份证验证位值，其中10代表X
						int sum = 0;// 声明加权求和变量
						String[] certificateNoArray = new String[certificateNo
								.length()];
						for (int i = 0; i < certificateNo.length(); i++) {
							certificateNoArray[i] = String.valueOf(certificateNo
									.charAt(i));
						}
						if ("x".equals(certificateNoArray[17].toLowerCase())) {
							certificateNoArray[17] = "10";// 将最后位为x的验证码替换为10
						}
						for (int i = 0; i < 17; i++) {
							sum += weightedFactors[i]
									* Integer.parseInt(certificateNoArray[i]);// 加权求和
						}
						int valCodePosition = sum % 11;// 得到验证码所在位置
						if (Integer.parseInt(certificateNoArray[17]) == valideCode[valCodePosition]) {
							String sex = "男";
							if (Integer.parseInt(sequenceCode) % 2 == 0) {
								sex = "女";
							}
							System.out.println("身份证号码有效，性别为：" + sex + "！");
							return true;
						} else {
							System.err.println("身份证号码无效，请重新输入！！！");
						}
					}
				} else {
					System.err.println("身份证号码无效，请重新输入！！！");
				}
			} else {
				System.err.println("非身份证号码，请输入身份证号码！！！");
			}
			return false;
		}

		/**
	     * 跳转到待开发界面
	     * @Description:
	     * @param context
	     * @param title
	     * void
	     * @exception:
	     * @author: vinci
	     * @time:2014-8-18 下午4:52:22
	     */
	    public static void gotoWaitingActivity(Context context,String title)
		{
			Intent intent = new Intent(context,WaitingActivity.class);
			intent.putExtra(WaitingActivity.PARAMS_TITLE, title);
			context.startActivity(intent);
		}
	    
	    
		
		public static void goNewsDetail(Context context,NewsInfoModel news,NewsTypeModel type){
			if("主题拍".equals(type.getName())){
				Intent intent = new Intent(context,ActivityWeb.class);
				intent.putExtra(CoreContants.PARAMS_NEWS,news);
				intent.putExtra(CoreContants.PARAMS_TYPE,type);
				context.startActivity(intent);
			}else if(CoreContants.NEWS_SUBTYPE_VIDEO.equals(type.getType())){
				     Intent intent = new Intent(context,VideoOLActivity.class);
				     intent.putExtra(CoreContants.PARAMS_NEWS,news);
				     intent.putExtra(CoreContants.PARAMS_TYPE,type);
				     context.startActivity(intent);
		    }else if("1".equals(type.getHassub())){
				Intent intent = new Intent(context,SubNewsActivity.class);
				intent.putExtra(CoreContants.PARAMS_NEWS,news);
				intent.putExtra(CoreContants.PARAMS_TYPE,type);
				context.startActivity(intent);
			}else if(CoreContants.NEWS_SUBTYPE_WAP.equals(type.getType())){
				Intent intent = new Intent(context,WebActivity.class); 
				intent.putExtra(CoreContants.PARAMS_MSG, news.getUrl());
				context.startActivity(intent); 
			}else{
				if(CoreContants.NEWS_SUBTYPE_PIC.equals(type.getType())){
					Intent intent = new Intent(context,NewsImageActivity.class);
					intent.putExtra(CoreContants.PARAMS_NEWS,news);
					intent.putExtra(CoreContants.PARAMS_TYPE,type);
					context.startActivity(intent);
				}else if(CoreContants.NEWS_SUBTYPE_WORD_EXT.equals(type.getType())){
					Intent intent = new Intent(context,ActivityWebExt.class);
					intent.putExtra(CoreContants.PARAMS_NEWS,news);
					intent.putExtra(CoreContants.PARAMS_TYPE,type);
					context.startActivity(intent);
				}else{
					Intent intent = new Intent(context,ActivityWeb.class);
					intent.putExtra(CoreContants.PARAMS_NEWS,news);
					intent.putExtra(CoreContants.PARAMS_TYPE,type);
					context.startActivity(intent);
				}
			}
			
			ComUtil.addViewTimes(context,news,type);
		}
		
		
		 /**
		  * 选择图片保存--用于选择或者拍照onActivityResult回调,使用参考修改用户头像
		  * @param data
		  * @param context
		  * @param url
		  * @param width
		  * @param heigh
		  * @param showTips--是否弹出提示框
		  * @return
		  */
		 public static String saveChosePic(Intent data,Context context,int width,int height)
		 {
			 if(data==null){
				 return "";
			 }
			 Bitmap image=null;  
			 Uri mImageCaptureUri = data.getData();  
			 try
			 {
				 if (mImageCaptureUri != null) {  
		        	 image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), mImageCaptureUri);  
		         } else {  
		             Bundle extras = data.getExtras();  
		             if (extras != null) {  
		                 image = extras.getParcelable("data");  
		             }  
		         } 
				 String filePath = "";  
				 if(image!=null){
					String saveDir = Environment.getExternalStorageDirectory()
								+ "/"+ComApp.APP_NAME+"/";
					File dir = new File(saveDir);
					if (!dir.exists()) {
						dir.mkdir();
					}
					filePath = saveDir+"/"+getFilename();
		            File destFile = new File(filePath);  
		            OutputStream os = null;  
		            os = new FileOutputStream(destFile); 
	                final BitmapFactory.Options options = new BitmapFactory.Options();
	                options.outWidth = image.getWidth();
	                options.outHeight = image.getHeight();
	                options.inSampleSize = ComUtil.calculateInSampleSize(options, width,
	                		height);
	                image.compress(CompressFormat.JPEG, 100/options.inSampleSize, os);  
	                os.flush();  
	                os.close();  
	                return filePath;
				 }
				 else{
					 return "";
				 }
			 }
			 catch(Exception e){
				 e.printStackTrace();
				 return "";
			 }
		 }
		 
		 
		 /**
     * 检查当前网络是否可用
     * 
     * @param context
     * @return
     */
    
    public static boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            
            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static void doSmartWeather(final String city,final Context cxt,final View mWeatheView){
		final SmartWeatherService sw = new SmartWeatherServiceImpl();
		RspResultModel cacheData = sw.getCacheForecast();
		
		final String areaid = sw.getAreaidFromCity(city);
		
		if(cacheData!=null&&cacheData.getF()!=null&&!FuncUtil.isEmpty(cacheData.getF().getF1())){
			log.info("缓存预报不为空，直接显示");
			if(mWeatheView!=null){
				TextView tv_title = (TextView)mWeatheView.findViewById(R.id.title);
				
				tv_title.setText(city+cacheData.getF().getF1().get(0).getFc()+"°C/"+cacheData.getF().getF1().get(0).getFd()+"°C");
			}
		}else{
			log.info("缓存预报为空，获取服务器数据");
			SituoHttpAjax.ajax(new SituoAjaxCallBack(){
				@Override
				public StBaseType requestApi() {
					return sw.getForecast(areaid);
				}

				@Override
				public void callBack(StBaseType baseType) {
					TextView tv_title = (TextView)mWeatheView.findViewById(R.id.title);
					RspResultModel result = (RspResultModel)baseType;
					if(ComUtil.checkRsp(cxt, result,false)&&result.getF()!=null&&!FuncUtil.isEmpty(result.getF().getF1())){
						log.info("返回天气数据");
						if(mWeatheView!=null){
							log.info("显示白天黑夜温度");
							tv_title.setText(city+result.getF().getF1().get(0).getFc()+"°C/"+result.getF().getF1().get(0).getFd()+"°C");
						}
					}else{
						tv_title.setText(city+" 天气服务访问异常~");
					}
					
				}
			});
		}
	}
    
    /**
     * 统计访问次数
     * @param cxt
     * @param newsInfoModel 
     */
    public static void addViewTimes(Context cxt, NewsInfoModel newsInfoModel,NewsTypeModel type){
    	 Intent intent=new Intent(cxt,ComService.class);
    	 if(FuncUtil.isEmpty(newsInfoModel.getArttype())){
    		 newsInfoModel.setArttype(type.getParentid());
    	 }
    	 intent.putExtra(CoreContants.PARAMS_NEWS, newsInfoModel);
    	 cxt.startService(intent);
    }
    
    /**
     * 启动下载任务
     * @param cxt
     * @param newsInfoModel 
     */
    public static void addDownLoadTask(Context cxt, AppInfoModel appInfo){
    	Intent intent=new Intent(cxt,DownloadService.class);
    	intent.putExtra(CoreContants.PARAMS_MSG, appInfo);
    	cxt.startService(intent);
    }
    
	
	/**
	 * 判断当前版本是否兼容目标版本的方法
	 * @param VersionCode
	 * @return
	 */
	public static boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}
	
	/**
	 * 清除app缓存
	 */
	public static void clearAppCache(Context cxt)
	{
		//清除webview缓存
//		file file = cachemanager.getcachefilebasedir();  
//		if (file != null && file.exists() && file.isdirectory()) {  
//		    for (file item : file.listfiles()) {  
//		    	item.delete();  
//		    }  
//		    file.delete();  
//		}  		  
		cxt.deleteDatabase("webview.db");  
		cxt.deleteDatabase("webview.db-shm");  
		cxt.deleteDatabase("webview.db-wal");  
		cxt.deleteDatabase("webviewCache.db");  
		cxt.deleteDatabase("webviewCache.db-shm");  
		cxt.deleteDatabase("webviewCache.db-wal");  
		//清除数据缓存
		clearCacheFolder(cxt.getFilesDir(),System.currentTimeMillis());
		clearCacheFolder(cxt.getCacheDir(),System.currentTimeMillis());
		//2.2版本才有将应用缓存转移到sd卡的功能
		if(isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)){
			clearCacheFolder(MethodsCompat.getExternalCacheDir(cxt),System.currentTimeMillis());
		}
		//清除编辑器保存的临时内容
		Properties props = getProperties(cxt);
		for(Object key : props.keySet()) {
			String _key = key.toString();
			if(_key.startsWith("temp"))
				removeProperty(cxt,_key);
		}
	}	
	/**
	 * 清除缓存目录
	 * @param dir 目录
	 * @param numDays 当前系统时间
	 * @return
	 */
	private static int clearCacheFolder(File dir, long curTime) {          
	    int deletedFiles = 0;         
	    if (dir!= null && dir.isDirectory()) {             
	        try {                
	            for (File child:dir.listFiles()) {    
	                if (child.isDirectory()) {              
	                    deletedFiles += clearCacheFolder(child, curTime);          
	                }  
	                if (child.lastModified() < curTime) {     
	                    if (child.delete()) {                   
	                        deletedFiles++;           
	                    }    
	                }    
	            }             
	        } catch(Exception e) {       
	            e.printStackTrace();    
	        }     
	    }       
	    return deletedFiles;     
	}
	public  static Properties getProperties(Context cxt){
		return AppConfig.getAppConfig(cxt).get();
	}
	public  static void removeProperty(Context cxt,String...key){
		AppConfig.getAppConfig(cxt).remove(key);
	}

	/**
	 * 从相册中获取图片
	 * @param cxt
	 */
	public static void getImagesFromLib(Activity cxt){
		String state = Environment
		.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(
					Intent.ACTION_GET_CONTENT);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType("image/*");
			cxt.startActivityForResult(
					Intent.createChooser(intent,"选择图片"),
					CoreContants.REQUEST_CODE_IMAGE);
		} else {
			DialogUtil.showToast(cxt, "请插入SD卡");
		}		
	}
	
	/**
	 * 拍照
	 * @param cxt
	 * @return 图片目录
	 */
	public static String takePhoto(Activity cxt){
		String piPath="";
		String state = Environment
		.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);//
			String saveDir = Environment.getExternalStorageDirectory()
			+ "/"+ComApp.APP_NAME;
			File dir = new File(saveDir);
			if (!dir.exists()) {
				dir.mkdir();
			}
			
			piPath = saveDir+"/"+ComUtil.getFilename();
			log.info("文件路径："+piPath);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(piPath)));
			cxt.startActivityForResult(intent,CoreContants.REQUEST_CODE_CAMERL);
		} else {
			DialogUtil.showToast(cxt, "请插入SD卡");
		}
		return piPath;	
	}
	
	/**
	 * 从库中获取已有的视频
	 * @param cxt
	 */
	public static void getVideoFromLib(Activity cxt){
		String state = Environment
		.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(
					Intent.ACTION_GET_CONTENT);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType("video/*");
			cxt.startActivityForResult(
					Intent.createChooser(intent,"选择视频"),
					CoreContants.REQUEST_CODE_VIDEO);
		} else {
			DialogUtil.showToast(cxt, "请插入SD卡");
		}		
	}
	
	/**
	 * 调用摄像机
	 * @param cxt
	 */
	public static void takeVideo(Activity cxt){
		String state = Environment
		.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			intent.putExtra(MediaStore.EXTRA_MEDIA_TITLE,"正在录制视频");
			intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 80);//视频的最大播放时间，单位为秒
			intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1512);//视频的最大字节数
			cxt.startActivityForResult(intent, CoreContants.REQUEST_CODE_TAKE_VIDEO);
		} else {
			DialogUtil.showToast(cxt, "请插入SD卡");
		}	
	}
	
    /**
     * 播放本地视频
     * @param cxt
     * @param videoPath
     */
    public static void playVideo(Activity cxt,String videoPath){  
        Intent intent = new Intent(Intent.ACTION_VIEW);  
        String strend="";  
        if(videoPath.toLowerCase().endsWith(".mp4")){  
            strend="mp4";  
        }  
        else if(videoPath.toLowerCase().endsWith(".3gp")){  
            strend="3gp";  
        }  
        else if(videoPath.toLowerCase().endsWith(".mov")){  
            strend="mov";  
        }  
        else if(videoPath.toLowerCase().endsWith(".wmv")){  
            strend="wmv";  
        }else{
        	DialogUtil.showToast(cxt,"正在打开系统浏览器跳转页面");
	        Uri content_url = Uri.parse(videoPath);  
	        intent.setData(content_url); 
	        cxt.startActivityForResult(intent, 100);
        }
        intent.setDataAndType(Uri.parse(videoPath), "video/"+strend);  
        cxt.startActivity(intent);  
    }  
	
	/**
	 *播放网络视频
	 * @param cxt
	 * @param url
	 */
	public static void playVideoFromUrl(Activity cxt,String url){
//	    String extension = MimeTypeMap.getFileExtensionFromUrl(url);  
//	    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);  
//	    Intent mediaIntent = new Intent(Intent.ACTION_VIEW);  
//	    mediaIntent.setDataAndType(Uri.parse(url), mimeType);  
//	    cxt.startActivity(mediaIntent);  
		playVideo(cxt,url);//
	}
	
	public static WindowManager getWindowService(Context cxt){
		WindowManager wm = (WindowManager) cxt.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		return wm;
	}
	
	public static void hideKeyBoard(Activity cxt,EditText searchEd){
		((InputMethodManager) searchEd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
		.hideSoftInputFromWindow(
				cxt.getCurrentFocus()
		.getWindowToken(),
		InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	
	
	/**
	 * 评论
	 */
	public static void addComment(final Activity cxt,final NewsInfoModel mNews,final NewsService newService,final String mAttype,final TextView tv_right,int cacheType){
		CacheModel cm = new CacheModel();
		cm.type =cacheType;
		cm.id = mNews.getId()+"";
		cm.msg = mNews;
		DetailBarManager detailBarMng = new DetailBarManager(cxt,cxt.findViewById(R.id.rl_commentbar),cm);
		detailBarMng.setCommtentCB(new DetailBarManager.OnCommentCallBack() {
			
			@Override
			public void onCommentCallBack(Dialog dlg, final String comment) {
				
				DialogUtil.showProgressDialog(cxt);
				SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {

					@Override
					public StBaseType requestApi() {
						RspResultModel rsp = newService.pubNewComment(mNews.getId(), comment,mAttype);
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(cxt);
						RspResultModel result = (RspResultModel)baseType;
						if(ComUtil.checkRsp(cxt, result)){
							if("0".equals(result.getRetcode())){
								DialogUtil.showToast(cxt, "评论成功");
								if(tv_right!=null){
									int replyCount=Integer.parseInt(mNews.getReplycount())+1;
									tv_right.setText(String.valueOf(replyCount));
								}
								//用户行为采集
								XHSDKUtil.addXHBehavior(cxt,mAttype+"_"+mNews.getId(), XHConstants.XHTOPIC_COMMENT, mNews.getId());
							}else{
								DialogUtil.showToast(cxt, "评论失败");
							}
						}
					}
					
				});
			}
		});
		detailBarMng.refreshUI();
	}
	
	public static void doCollection(final CacheModel cacheModel,final Activity cxt,final NewsInfoModel mNews,final String mAttype,final Button btn_right){
		CacheModel cm = CacheDataService.getAcction(cacheModel.type, cacheModel.id);
		if(cm==null){
			CacheDataService.addAcction(cacheModel);
			btn_right.setSelected(true);
			DialogUtil.showToast(cxt, "收藏成功");
			XHSDKUtil.addXHBehavior(cxt,mAttype+"_"+mNews.getId(), XHConstants.XHTOPIC_ARTICAL_FAV, mNews.getId());
		}
		else{
			CacheDataService.cancelAcction(cacheModel.type, cacheModel.id+"");
			btn_right.setSelected(false);
			XHSDKUtil.addXHBehavior(cxt,mAttype+"_"+mNews.getId(), XHConstants.XHTOPIC_ARTICAL_FAV_CANCEL, mNews.getId());
			DialogUtil.showToast(cxt, "取消收藏");
		}
	}

	/**
	    * 安装apk
	    * @param url
	    */
	public static  void installApk(Context cxt,String apkFilePath){
		File apkfile = new File(apkFilePath);
        if (!apkfile.exists()) {
            return;
        }    
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
        cxt.startActivity(i);
	}
	
	/**
	 * 安装apk
	 * @param url
	 */
	public static  void installApk(Application app,String apkFilePath){
		File apkfile = new File(apkFilePath);
		if (!apkfile.exists()) {
			return;
		}    
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		app.startActivity(intent);
	}
	
	/**
     * 在状态栏显示通知
     */
	public static   void showNotification(Context cxt,int appIcon,Class target,int notifyId,String title,int progress,int notifyType){
		
        
        Notification   notification = createNotification( cxt,appIcon,target,notifyId,title,ComApp.getInstance().getRemoteViews(title, appIcon, progress),notifyType);
        // 把Notification传递给NotificationManager   
        ComApp.getInstance().getNotificationManager().notify(notifyId, notification);   
         
	}
	/**
     * 在状态栏显示通知
     */
	public static   void showNotification(Context cxt,int appIcon,Class target,int notifyId,String title,int notifyType){
		showNotification(cxt,appIcon,target,notifyId,title,1,notifyType);
    }
	
	public static Notification createNotification(Context cxt,int appIcon,Class target,int notifyId,String title,RemoteViews contentView,int notifyType){
		 // 定义Notification的各种属性   
        Notification notification = ComApp.getInstance().getNotification();
        notification.defaults =notifyType;//Notification.DEFAULT_VIBRATE; 
        if(target!=null){
        	 Intent notificationIntent =new Intent(cxt,target); // 点击该通知后要跳转的Activity   
             PendingIntent contentItent = PendingIntent.getActivity(cxt, 0, notificationIntent,0);   
//             notification.setLatestEventInfo(cxt, contentTitle, contentText, contentItent);
             notification.contentIntent=contentItent;
        }
//        notification.tickerText = title;
        notification.icon = appIcon;
        notification.contentView = contentView;
		return notification;
	}
	
    //删除通知    
	public static   void clearNotification(Context cxt,int notifyId){
        // 启动后删除之前我们定义的通知   
		ComApp.getInstance().getNotificationManager().cancel(notifyId);  
  
    }
	
	/**
	 * apk下载所在的目录
	 * @return
	 */
	public static String getApkDiretion(){
		String dir="";
		dir=Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+ComApp.APP_NAME+"/Update/";
		return dir;
	}
}
