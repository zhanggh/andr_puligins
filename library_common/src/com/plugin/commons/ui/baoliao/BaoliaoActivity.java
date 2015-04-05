package com.plugin.commons.ui.baoliao;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.model.BaoLiaoInfoModel;
import com.plugin.commons.model.DialogObj;
import com.plugin.commons.model.GovmentInfoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.BaoliaoService;
import com.plugin.commons.service.BaoliaoServiceImpl;
import com.zq.types.StBaseType;


/**
 * @author zhang
 *  发布报料信息
 */
public class BaoliaoActivity extends Activity{
	DingLog log = new DingLog(BaoliaoActivity.class);
	public static String PARAMS_ORG = "org";
	private final int REQUEST_CODE_IMAGE = 11;
	private final int REQUEST_CODE_PHOTO = 12;
	private final int REQUEST_CODE_VIDEO = 13;
	BaoLiaoInfoModel mGov;
	Button btn_right;
	List<GovmentInfoModel> mList;
	TextView tv_org;
	EditText et_content;
	 
	LinearLayout ll_pic;
	LinearLayout ll_photo;
	LinearLayout ll_video;
	Button btn_pic;
	Button btn_photo;
	Button btn_video;
	ImageView iv_myimage;
	ImageView iv_myvideo;
	RelativeLayout rl_myvideo;
	private List<DialogObj> dlglist;
	List<String> picPathList = new ArrayList<String>();
	Bitmap mPic;
	String picPath="";
	String videoPath="";

	BaoliaoService baoliaoSevice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baoliao);
		ComUtil.customeTitle(this, "报料",true);
		baoliaoSevice=new BaoliaoServiceImpl();
		initViews();
		refreshUI();
	}
	
	
	@SuppressWarnings("deprecation")
	private void initViews()
	{
		btn_right = (Button)this.findViewById(R.id.btn_title_right);
		 
		et_content = (EditText)this.findViewById(R.id.et_content);
		 
		ll_pic = (LinearLayout)this.findViewById(R.id.ll_pic);
		ll_photo = (LinearLayout)this.findViewById(R.id.ll_photo);
		ll_video = (LinearLayout)this.findViewById(R.id.ll_video);
		
		btn_pic = (Button)this.findViewById(R.id.btn_pic);
		btn_photo = (Button)this.findViewById(R.id.btn_photo);
		btn_video = (Button)this.findViewById(R.id.btn_video);
		
		iv_myimage = (ImageView)this.findViewById(R.id.iv_myimage);
		iv_myvideo = (ImageView)this.findViewById(R.id.iv_myvideo);
		rl_myvideo = (RelativeLayout)this.findViewById(R.id.rl_myvideo);
		
		btn_right.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_dialogsure_selector));
		btn_right.setVisibility(View.VISIBLE);
		
		int width = (ComUtil.getWindowWidth(this))/3;
		ll_pic.getLayoutParams().width = width;
		ll_photo.getLayoutParams().width = width;
		ll_video.getLayoutParams().width = width;
	}
	
	private void refreshUI()
	{
		iv_myimage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DialogUtil.showConfirmAlertDialog(BaoliaoActivity.this, "确定删除该图片?", "", new DialogUtil.OnAlertSureOnclick() {
					
					@Override
					public void onAlertSureOnclick() {
						// TODO Auto-generated method stub
						picPath = "";
						refreshImage();
					}
				});
				
			}
		});
		
		iv_myvideo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DialogUtil.showConfirmAlertDialog(BaoliaoActivity.this, "确定删除该视频?", "", new DialogUtil.OnAlertSureOnclick() {
					
					@Override
					public void onAlertSureOnclick() {
						// TODO Auto-generated method stub
						videoPath = "";
						refreshImage();
					}
				});
				
			}
		});
		
		btn_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String content = et_content.getText().toString();
				 
				if(FuncUtil.isEmpty(content)){
					DialogUtil.showToast(BaoliaoActivity.this, "请填写报料内容");
					return ;
				}
				SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {
					
					@Override
					public StBaseType requestApi() {
						// TODO Auto-generated method stub
						FileInputStream ins1=null;
						FileInputStream ins2=null;
						FileInputStream ins3=null;
						FileInputStream ins4=null;
						try
						{
							ins1 = getFileIns(0,ins1);
							ins2 = getFileIns(1,ins2);
							ins3 = getFileIns(2,ins3);
							ins4 = getFileIns(3,ins4);
							return baoliaoSevice.pubBaoliaoInfo(content,getFileName(0) , ins1, getFileName(1), ins2, getFileName(2),ins3, "", null, getFileName(3), ins4);
						}
						catch(Exception e){
							e.printStackTrace();
						}
						finally{
							try{
								if(ins1!=null)
									ins1.close();
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						return null;
					}
					
					@Override
					public void callBack(StBaseType baseType) {
						// TODO Auto-generated method stub
						DialogUtil.closeProgress(BaoliaoActivity.this);
						RspResultModel result = (RspResultModel)baseType;
						if(ComUtil.checkRsp(BaoliaoActivity.this, result)){
							DialogUtil.showToast(BaoliaoActivity.this, "报料成功");
							refreshUI();
							finish();
						}
					}
				});
				DialogUtil.showToast(BaoliaoActivity.this, "报料成功");
				finish();
			}
		});
 
		
		btn_pic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!FuncUtil.isEmpty(picPath)){
					DialogUtil.showToast(BaoliaoActivity.this, "每次只允许上次一张图片");
					return ;
				}
				String state = Environment
						.getExternalStorageState();
				if (state.equals(Environment.MEDIA_MOUNTED)) {
					Intent intent = new Intent(
							Intent.ACTION_GET_CONTENT);
					intent.addCategory(Intent.CATEGORY_OPENABLE);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent,"选择图片"),
							REQUEST_CODE_IMAGE);
				} else {
					DialogUtil.showToast(BaoliaoActivity.this, "请插入SD卡");
				}		
			}
		});
		
		btn_photo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!FuncUtil.isEmpty(picPath)){
					DialogUtil.showToast(BaoliaoActivity.this, "每次只允许上次一张图片");
					return ;
				}
				String state = Environment
						.getExternalStorageState();
				if (state.equals(Environment.MEDIA_MOUNTED)) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					String saveDir = Environment.getExternalStorageDirectory()
					+ "/"+ComApp.APP_NAME;
					File dir = new File(saveDir);
					if (!dir.exists()) {
						dir.mkdir();
					}
					picPath = saveDir+"/"+ComUtil.getFilename();
					log.info("文件路径："+picPath);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(picPath)));
					startActivityForResult(intent, REQUEST_CODE_PHOTO);
				} else {
					DialogUtil.showToast(BaoliaoActivity.this, "请插入SD卡");
				}							
			}
		});
		
		btn_video.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!FuncUtil.isEmpty(picPath)){
					DialogUtil.showToast(BaoliaoActivity.this, "每次只允许上传一个视频");
					return ;
				}
				Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
				intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 512);//视频的最大字节数
				startActivityForResult(intent, REQUEST_CODE_VIDEO);
				
			}
		});
	}
	
	
	private void request(){
		DialogUtil.showProgressDialog(BaoliaoActivity.this);
		SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {
			
			@Override
			public StBaseType requestApi() {
//				RspResultModel rsp = MyApp.getBlService().pubBaoliaoInfo(blmodel,MyApp.getInstance().getLoginInfo().getSessionid());
				return null;
			}
			
			@Override
			public void callBack(StBaseType baseType) {
				// TODO Auto-generated method stub
				DialogUtil.closeProgress(BaoliaoActivity.this);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(BaoliaoActivity.this, result)){
					refreshUI();
				}
			}
		});
	}
	
	//刷新媒体上传图标
	private void refreshImage(){
		if(FuncUtil.isEmpty(picPath)){
			iv_myimage.setVisibility(View.GONE);
		}
		else{
			Bitmap bitmap = BitmapFactory.decodeFile(picPath);
			if(bitmap!=null){
				iv_myimage.setImageBitmap(bitmap);
				iv_myimage.setVisibility(View.VISIBLE);
			}
		}
		
		if(!FuncUtil.isEmpty(videoPath)){
			Bitmap bm = ComUtil.getVideoThumbnail(videoPath,100,100,MediaStore.Images.Thumbnails.MINI_KIND);
			if(bm!=null){
				iv_myvideo.setImageBitmap(bm);
				rl_myvideo.setVisibility(View.VISIBLE);
			}
		}
		else{
			rl_myvideo.setVisibility(View.GONE);
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		log.info(requestCode+";"+resultCode);
		 if (resultCode != RESULT_OK) {  
	            return;  
	        }  
		if (REQUEST_CODE_IMAGE == requestCode) {
			try {
				Uri uri = data.getData();
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor cursor = managedQuery(uri, proj, null, null, null);
				// 选择的图片不为空，获取图片的路径 并跳转到确认页面
				if (cursor != null) {
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					String path = cursor.getString(column_index);
					if (path != null) {
						log.info("显示图片");
						final String file = ComUtil.saveImage(this, path,480,640);
						picPath = file;
						refreshImage();
					}
					else{
						DialogUtil.showToast(this, "选择图片失败");
					}
				} else {
					DialogUtil.showToast(this, "选择图片失败");
				}
			} catch (Exception e) {
				DialogUtil.showToast(this, "选择图片失败");
			}
		}
		else if (REQUEST_CODE_PHOTO == requestCode) {
			try {
				String file = ComUtil.saveImage(this, picPath,480,640);
				picPath = file;
				refreshImage();
			} catch (Exception e) {
				DialogUtil.showToast(this, "拍照失败");
				return;
			}
		}
		else if(REQUEST_CODE_VIDEO == requestCode){
			if (null != data) {
				log.info("处理video");
				Uri uri = data.getData();
				if (uri == null) {
					return;
				} else {
					// 视频捕获并保存到指定的fileUri意图
					Cursor c = getContentResolver().query(uri,
							new String[] { MediaStore.MediaColumns.DATA },
							null, null, null);
					if (c != null && c.moveToFirst()) {
						String filePath = c.getString(0);
						videoPath = filePath;
						log.info(videoPath);
						refreshImage();
						//
					}

				}
			}
		}
	}
	
	private FileInputStream getFileIns(int index,FileInputStream ins) throws Exception{
		String fileName = "";
		if(index<3){
			fileName = picPathList.size()>index?picPathList.get(index):"";
		}
		else {
			fileName = videoPath;
		}
		if(!FuncUtil.isEmpty(fileName)){
			File imageFile = new File(fileName);
			ins = new FileInputStream(imageFile);
			//log.info("文件大小:"+imageIS.available());
			return ins;
		}
		return null;
	}

	private String getFileName(int index){
		String fileName = "";
		if(index<3){
			fileName = picPathList.size()>index?picPathList.get(index):"";
		}
		else {
			fileName = videoPath;
		}
		if(!FuncUtil.isEmpty(fileName)){
			File imageFile = new File(fileName);
			if(imageFile.exists()){
				return imageFile.getName();
			}
		}
		return "";
	}
}
