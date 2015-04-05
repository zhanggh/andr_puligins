package com.plugin.commons.petition;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

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
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.model.DialogObj;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.WriterTpyeModel;
import com.plugin.commons.service.AskGovService;
import com.plugin.commons.service.AskGovServiceImpl;
import com.plugin.commons.service.DisClsTestService;
import com.plugin.commons.ui.base.BaseActivity;
import com.plugin.commons.view.DialogOptionSelect;
import com.zq.types.StBaseType;

public class PetitionWrActivity extends BaseActivity{

	private final int REQUEST_CODE_IMAGE = 11;
	private final int REQUEST_CODE_PHOTO = 12;
	Button btn_right;
	EditText et_content;
	LinearLayout ll_typelist;
	LinearLayout ly_writer_type;
	TextView tv_writer_type;
	private DialogObj mSelectItem;
	private List<DialogObj> dlglist;
	Button btn_photo;
	Button btn_pic;
	TextView tv_pic;
	TextView tv_photo;
	LinearLayout ll_photo;
	LinearLayout ll_pic;
	String picPath = "";
	ImageView iv_myimage1;
	ImageView iv_myimage2;
	ImageView iv_myimage3;
	AskGovService askSvc;
	AskMsgModel mGov;
	List<String> picPathList = new ArrayList<String>();
	private TextView im_writer_type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_petition_write);
		
		ComUtil.customeTitle(this, "写信",true);
		if(this.getIntent().getExtras()!=null&&this.getIntent().getExtras().containsKey(CoreContants.PARAMS_MSG)){
			mGov =(AskMsgModel)getIntent().getExtras().get(CoreContants.PARAMS_MSG);
		}else{
			mGov=new AskMsgModel();
			mGov.setMsgtype("0");
			mGov.setOrgid("0");
		}
		
		initViews();
	}
	
	
	private void initViews()
	{
		askSvc = new AskGovServiceImpl();
		ll_pic = (LinearLayout)this.findViewById(R.id.ll_pic);
		ll_photo = (LinearLayout)this.findViewById(R.id.ll_photo);
		btn_right = (Button)this.findViewById(R.id.btn_title_right);
		ll_typelist = (LinearLayout)this.findViewById(R.id.ll_typelist);
		ly_writer_type = (LinearLayout)this.findViewById(R.id.ly_writer_type);
		if(CoreContants.APP_LNZX.equals(ComApp.APP_NAME)){
			ll_typelist.setVisibility(View.VISIBLE);
			im_writer_type = (TextView)this.findViewById(R.id.im_writer_type);
			im_writer_type.setBackgroundResource(ComApp.getInstance().appStyle.btn_petition_selector);
			tv_writer_type = (TextView)this.findViewById(R.id.tv_writer_type);
		}
		
		et_content = (EditText)this.findViewById(R.id.et_content);
		ll_photo = (LinearLayout)this.findViewById(R.id.ll_photo);
		iv_myimage1 = (ImageView)this.findViewById(R.id.iv_myimage1);
		iv_myimage2 = (ImageView)this.findViewById(R.id.iv_myimage2);
		iv_myimage3 = (ImageView)this.findViewById(R.id.iv_myimage3);
		
		btn_photo = (Button)this.findViewById(R.id.btn_photo);
		btn_photo.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_pic_selector));
		
		btn_pic = (Button)this.findViewById(R.id.btn_pic);
		btn_pic.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_photo_selector));
		
		tv_pic = (TextView)this.findViewById(R.id.tv_pic);
		tv_pic.setTextColor(this.getResources().getColor(ComApp.getInstance().appStyle.font_grey_selector));
		
		tv_photo = (TextView)this.findViewById(R.id.tv_photo);
		tv_photo.setTextColor(this.getResources().getColor(ComApp.getInstance().appStyle.font_grey_selector));
		
		btn_right.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_dialogsure_selector));
		btn_right.setVisibility(View.VISIBLE);
		
		//图片删除？
		iv_myimage1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DialogUtil.showConfirmAlertDialog(PetitionWrActivity.this, "确定删除该图片?", "", new DialogUtil.OnAlertSureOnclick() {
					
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
				DialogUtil.showConfirmAlertDialog(PetitionWrActivity.this, "确定删除该图片?", "", new DialogUtil.OnAlertSureOnclick() {
					
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
				DialogUtil.showConfirmAlertDialog(PetitionWrActivity.this, "确定删除该图片?", "", new DialogUtil.OnAlertSureOnclick() {
					
					@Override
					public void onAlertSureOnclick() {
						// TODO Auto-generated method stub
						picPathList.remove(2);
						refreshImage();
					}
				});
			}
		});
		//发布
		btn_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DialogUtil.showProgressDialog(PetitionWrActivity.this, "提交中...");
				// TODO Auto-generated method stub
				final String content = et_content.getText().toString();
				 
				if(FuncUtil.isEmpty(content)){
					DialogUtil.showToast(PetitionWrActivity.this, "请填写写信内容");
					return ;
				}
				SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {
					
					@Override
					public StBaseType requestApi() {
						FileInputStream ins1=null;
						FileInputStream ins2=null;
						FileInputStream ins3=null;
						FileInputStream ins4=null;
						try
						{
							log.info(mGov.getOrgid()+";;"+mGov.getOrgname());
							ins1 = getFileIns(0,ins1);
							ins2 = getFileIns(1,ins2);
							ins3 = getFileIns(2,ins3);
							ins4 = getFileIns(3,ins4);
							return askSvc.addAskGovExt(mGov, content,getFileName(0) , ins1, getFileName(1), ins2, getFileName(2),ins3, "", null, getFileName(3), ins4);
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
						DialogUtil.closeProgress(PetitionWrActivity.this);
						RspResultModel result = (RspResultModel)baseType;
						if(ComUtil.checkRsp(PetitionWrActivity.this, result)){
							DialogUtil.closeProgress(PetitionWrActivity.this);
							DialogUtil.showToast(PetitionWrActivity.this, "写信成功");
							//用户行为采集
							String user="未登录用户";
							if(ComApp.getInstance().isLogin()){
								user=ComApp.getInstance().getLoginInfo().getUserid();
							}
							XHSDKUtil.addXHBehavior(PetitionWrActivity.this,user, XHConstants.XHTOPIC_LIUYAN,content);
							//关闭上一个activity
							Intent caseIntent=new Intent(CoreContants.ACTIVITY_COSE);
							PetitionWrActivity.this.sendBroadcast(caseIntent);
							finish();
						}else{
							DialogUtil.showToast(PetitionWrActivity.this, "写信失败,请重试");
						}
					}
				});
			}
		});
		//拍照
		ll_photo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(picPathList.size()>3){
					DialogUtil.showToast(PetitionWrActivity.this, "每次最多只允许上次3张图片");
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
					DialogUtil.showToast(PetitionWrActivity.this, "请插入SD卡");
				}							
			}
		});
		//从相册中选择
		ll_pic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(picPathList.size()>2){
					DialogUtil.showToast(PetitionWrActivity.this, "每次最多只允许上次3张图片");
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
					DialogUtil.showToast(PetitionWrActivity.this, "请插入SD卡");
				}		
			}
		});
	}
		
	
	//结果返回
	
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
		if (REQUEST_CODE_PHOTO == requestCode) {
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
	}
	
	private FileInputStream getFileIns(int index,FileInputStream ins) throws Exception{
		String fileName = "";
		if(index<3){
			fileName = picPathList.size()>index?picPathList.get(index):"";
		}
		 
		if(!FuncUtil.isEmpty(fileName)){
			File imageFile = new File(fileName);
			ins = new FileInputStream(imageFile);
			log.info("文件大小:"+ins.available());
			return ins;
		}
		return null;
	}
	

	private String getFileName(int index){
		String fileName = "";
		if(index<3){
			fileName = picPathList.size()>index?picPathList.get(index):"";
		} 
		if(!FuncUtil.isEmpty(fileName)){
			File imageFile = new File(fileName);
			if(imageFile.exists()){
				return imageFile.getName();
			}
		}
		return "";
	}
	
	/**
	 * 选择写信人类型
	 * @param view
	 */
	public void selectType(final View view){
		// TODO Auto-generated method stub
		 
			DialogUtil.showProgressDialog(PetitionWrActivity.this);
			SituoHttpAjax.ajax(new SituoAjaxCallBack(){

				@Override
				public StBaseType requestApi() {
					
					if(R.id.ly_writer_type==view.getId()){
						RspResultModel rsp = DisClsTestService.getWriterList("3");
						return rsp;
					}
					return null;
				}

				@Override
				public void callBack(StBaseType baseType) {
					DialogUtil.closeProgress(PetitionWrActivity.this);
					List<WriterTpyeModel> writerList;
					// TODO Auto-generated method stub
					RspResultModel rsp = (RspResultModel)baseType;
					if(ComUtil.checkRsp(PetitionWrActivity.this, rsp)){
						 
						writerList = rsp.getWriterList();
						dlglist = new ArrayList<DialogObj>();
						for(WriterTpyeModel wtype:writerList){
							dlglist.add(new DialogObj(wtype.getId(),wtype.getTypeName()));
						}
						DialogOptionSelect dlg = new DialogOptionSelect(PetitionWrActivity.this,new DialogOptionSelect.PickDialogcallback() {
							
							@Override
							public void onItemSelect(DialogObj selectItem) {
								// TODO Auto-generated method stub
								mSelectItem = selectItem;
								mGov.setMsgtype(selectItem.getCode());
								mGov.setOrgid("0");
								tv_writer_type.setText(mSelectItem.getName());
							}
						},dlglist,mSelectItem);
						dlg.show();
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
