package com.plugin.commons.ui.askgov;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

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
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.DialogObj;
import com.plugin.commons.model.GovmentInfoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.AskGovService;
import com.plugin.commons.service.AskGovServiceImpl;
import com.plugin.commons.view.DialogOptionSelect;
import com.zq.types.StBaseType;


public class AskGovActivity extends Activity{
	DingLog log = new DingLog(AskGovActivity.class);
	public static String PARAMS_ORG = "org";
	private final int REQUEST_CODE_IMAGE = 11;
	private final int REQUEST_CODE_PHOTO = 12;
	private final int REQUEST_CODE_VIDEO = 13;
	GovmentInfoModel mGov;
	Button btn_right;
	List<GovmentInfoModel> mList;
	TextView tv_org;
	TextView tv_pic;
	TextView tv_photo;
	TextView tv_video;
	EditText et_content;
	RelativeLayout rl_org;
	LinearLayout ll_pic;
	LinearLayout ll_photo;
	LinearLayout ll_video;
	Button btn_pic;
	Button btn_photo;
	Button btn_video;
	ImageView iv_myimage1;
	ImageView iv_myimage2;
	ImageView iv_myimage3;
	ImageView iv_myvideo;
	ImageView iv_arrow;
	ImageView im_complain_video;
	RelativeLayout rl_myvideo;
	private List<DialogObj> dlglist;
	private DialogObj mSelectItem;
	Bitmap mPic;
	String videoPath="";
	List<String> picPathList = new ArrayList<String>();
	String picPath = "";
	AskGovService askSvc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_askgov);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAMS_ORG)) {
			mGov =(GovmentInfoModel)getIntent().getExtras().get(PARAMS_ORG);
			mSelectItem = new DialogObj(mGov.getId(),mGov.getName());
		}
		ComUtil.customeTitle(this, "问政",true);
		askSvc = new AskGovServiceImpl();
		initViews();
		refreshUI();
	}
	
	
	private void initViews()
	{
		btn_right = (Button)this.findViewById(R.id.btn_title_right);
		tv_org = (TextView)this.findViewById(R.id.tv_org);
		tv_pic = (TextView)this.findViewById(R.id.tv_pic);
		tv_pic.setTextColor(ComApp.getInstance().appStyle.font_grey_selector);
		
		tv_photo = (TextView)this.findViewById(R.id.tv_photo);
		tv_photo.setTextColor(ComApp.getInstance().appStyle.font_grey_selector);
		
		tv_video = (TextView)this.findViewById(R.id.tv_photo);
		tv_video.setTextColor(ComApp.getInstance().appStyle.font_grey_selector);
		
		et_content = (EditText)this.findViewById(R.id.et_content);
		rl_org = (RelativeLayout)this.findViewById(R.id.rl_org);
		ll_pic = (LinearLayout)this.findViewById(R.id.ll_pic);
		ll_photo = (LinearLayout)this.findViewById(R.id.ll_photo);
		ll_video = (LinearLayout)this.findViewById(R.id.ll_video);
		
		btn_pic = (Button)this.findViewById(R.id.btn_pic);
		btn_pic.setBackgroundResource(ComApp.getInstance().appStyle.btn_pic_selector);
		
		btn_photo = (Button)this.findViewById(R.id.btn_photo);
		btn_photo.setBackgroundResource(ComApp.getInstance().appStyle.btn_photo_selector);
		
		btn_video = (Button)this.findViewById(R.id.btn_video);
		btn_video.setBackgroundResource(ComApp.getInstance().appStyle.btn_video_selector);
		
		iv_myimage1 = (ImageView)this.findViewById(R.id.iv_myimage1);
		iv_myimage2 = (ImageView)this.findViewById(R.id.iv_myimage2);
		iv_myimage3 = (ImageView)this.findViewById(R.id.iv_myimage3);
		
		iv_arrow = (ImageView)this.findViewById(R.id.iv_arrow);
		iv_arrow.setImageResource(ComApp.getInstance().appStyle.downward_btn_normal);
		
		im_complain_video = (ImageView)this.findViewById(R.id.im_complain_video);
		im_complain_video.setImageResource(ComApp.getInstance().appStyle.complain_video);
		
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
		if(mSelectItem!=null){
			tv_org.setText(mSelectItem.getName());
		}
		
		iv_myimage1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DialogUtil.showConfirmAlertDialog(AskGovActivity.this, "确定删除该图片?", "", new DialogUtil.OnAlertSureOnclick() {
					
					@Override
					public void onAlertSureOnclick() {
						// TODO Auto-generated method stub
						picPathList.remove(0);
						refreshImage();
					}
				});
			}
		});
		
		iv_myimage2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DialogUtil.showConfirmAlertDialog(AskGovActivity.this, "确定删除该图片?", "", new DialogUtil.OnAlertSureOnclick() {
					
					@Override
					public void onAlertSureOnclick() {
						// TODO Auto-generated method stub
						picPathList.remove(1);
						refreshImage();
					}
				});
			}
		});
		
		iv_myimage3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DialogUtil.showConfirmAlertDialog(AskGovActivity.this, "确定删除该图片?", "", new DialogUtil.OnAlertSureOnclick() {
					
					@Override
					public void onAlertSureOnclick() {
						// TODO Auto-generated method stub
						picPathList.remove(2);
						refreshImage();
					}
				});
			}
		});
		
		iv_myvideo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DialogUtil.showConfirmAlertDialog(AskGovActivity.this, "确定删除该视频?", "", new DialogUtil.OnAlertSureOnclick() {
					
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
				if(mSelectItem==null){
					DialogUtil.showToast(AskGovActivity.this, "请选择问政机构");
					return ;
				}
				if(FuncUtil.isEmpty(content)){
					DialogUtil.showToast(AskGovActivity.this, "请填写问政内容");
					return ;
				}
				DialogUtil.showProgressDialog(AskGovActivity.this);
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
							log.info(mSelectItem.getCode()+";;"+mSelectItem.getName());
							ins1 = getFileIns(0,ins1);
							ins2 = getFileIns(1,ins2);
							ins3 = getFileIns(2,ins3);
							ins4 = getFileIns(3,ins4);
							return askSvc.addAskGov(mSelectItem.getCode(), content,getFileName(0) , ins1, getFileName(1), ins2, getFileName(2),ins3, "", null, getFileName(3), ins4);
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
						DialogUtil.closeProgress(AskGovActivity.this);
						RspResultModel result = (RspResultModel)baseType;
						if(ComUtil.checkRsp(AskGovActivity.this, result)){
							DialogUtil.showToast(AskGovActivity.this, "问政成功");
							refreshUI();
							finish();
						}
					}
				});
			}
		});
		
		rl_org.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RspResultModel rsp = askSvc.getOrgList(true);
				if(rsp!=null&&rsp.getOrg_list()!=null){
					mList = rsp.getOrg_list();
				}
				else{
					mList = new ArrayList<GovmentInfoModel>();
				}
				if(FuncUtil.isEmpty(mList)){
					DialogUtil.showProgressDialog(AskGovActivity.this);
					SituoHttpAjax.ajax(new SituoAjaxCallBack(){

						@Override
						public StBaseType requestApi() {
							// TODO Auto-generated method stub
							RspResultModel rsp = askSvc.getOrgList(false);
							return rsp;
						}

						@Override
						public void callBack(StBaseType baseType) {
							DialogUtil.closeProgress(AskGovActivity.this);
							// TODO Auto-generated method stub
							RspResultModel rsp = (RspResultModel)baseType;
							if(ComUtil.checkRsp(AskGovActivity.this, rsp)){
								mList = rsp.getOrg_list();
								dlglist = new ArrayList<DialogObj>();
								for(GovmentInfoModel gov:mList){
									dlglist.add(new DialogObj(gov.getId()+"",gov.getName()));
								}
								DialogOptionSelect dlg = new DialogOptionSelect(AskGovActivity.this,new DialogOptionSelect.PickDialogcallback() {
									
									@Override
									public void onItemSelect(DialogObj selectItem) {
										// TODO Auto-generated method stub
										mSelectItem = selectItem;
										tv_org.setText(mSelectItem.getName());
									}
								},dlglist,mSelectItem);
								dlg.show();
							}
						}
						
					});
				}
				else{
					dlglist = new ArrayList<DialogObj>();
					for(GovmentInfoModel gov:mList){
						dlglist.add(new DialogObj(gov.getId()+"",gov.getName()));
					}
					DialogOptionSelect dlg = new DialogOptionSelect(AskGovActivity.this,new DialogOptionSelect.PickDialogcallback() {
						
						@Override
						public void onItemSelect(DialogObj selectItem) {
							// TODO Auto-generated method stub
							mSelectItem = selectItem;
							tv_org.setText(mSelectItem.getName());
						}
					},dlglist,mSelectItem);
					dlg.show();
				}
				
				
			}
		});
		
		ll_pic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(picPathList.size()>2){
					DialogUtil.showToast(AskGovActivity.this, "每次最多只允许上次3张图片");
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
					DialogUtil.showToast(AskGovActivity.this, "请插入SD卡");
				}		
			}
		});
		
		ll_photo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(picPathList.size()>2){
					DialogUtil.showToast(AskGovActivity.this, "每次最多只允许上次3张图片");
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
					DialogUtil.showToast(AskGovActivity.this, "请插入SD卡");
				}							
			}
		});
		
		ll_video.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!FuncUtil.isEmpty(videoPath)){
					DialogUtil.showToast(AskGovActivity.this, "每次只允许上传一个视频");
					return ;
				}
				Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
				startActivityForResult(intent, REQUEST_CODE_VIDEO);
				
			}
		});
	}
	
	
	
	//刷新媒体上传图标
	private void refreshImage(){
		iv_myimage1.setVisibility(View.GONE);
		iv_myimage2.setVisibility(View.GONE);
		iv_myimage3.setVisibility(View.GONE);
		for(int i=0;i<picPathList.size();i++){
			String picPath = picPathList.get(i);
			Bitmap bitmap = BitmapFactory.decodeFile(picPath);
			if(bitmap!=null){
				if(i==0){
					iv_myimage1.setImageBitmap(bitmap);
					iv_myimage1.setVisibility(View.VISIBLE);
				}
				else if(i==1){
					iv_myimage2.setImageBitmap(bitmap);
					iv_myimage2.setVisibility(View.VISIBLE);
				}
				else if(i==2){
					iv_myimage3.setImageBitmap(bitmap);
					iv_myimage3.setVisibility(View.VISIBLE);
				}
				
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
						picPathList.add(file);
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
				picPathList.add(file);
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
