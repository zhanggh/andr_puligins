package com.plugin.commons.ui.pkbk;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.DialogUtil.OnAlertSureOnclick;
import com.plugin.commons.helper.FileUtils;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.model.CommonModel;
import com.plugin.commons.model.DialogObj;
import com.plugin.commons.model.PhotoAndVideoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.PaiKeBoKeService;
import com.plugin.commons.service.PaiKeBoKeServiceImpl;
import com.plugin.commons.ui.base.BaseActivity;
import com.plugin.commons.view.DialogOptionSelect;
import com.zq.types.StBaseType;

/**
 * @author zhang
 *	上传拍照信息
 */
public class UploadPhotoActivity extends BaseActivity implements OnClickListener{
	
	LinearLayout ll_show_imgs;
	Button btn_right;
	ImageView im_ph_1;
	ImageView im_ph_2;
	ImageView im_ph_3;
	ImageView im_ph_4;
	ImageView im_ph_5;
	ImageView im_ph_6;
	ImageView im_ph_7;
	ImageView im_ph_8;
	ImageView im_ph_9;
	EditText ed_title;
	private List<PhotoAndVideoModel>  phtModel=new ArrayList<PhotoAndVideoModel>();
	private List<DialogObj> dlglist;
	private int maxPhtos=9;
	private PaiKeBoKeService pkSv;
	private Button btn_photo;
	private LinearLayout ll_photo;
	private List<Bitmap> bitMapList=new ArrayList<Bitmap>();
	private CommonModel cmmodel;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_photo);
		 
		ComUtil.customeTitle(this,"拍客上传",true);
		
		cmmodel=(CommonModel) getIntent().getExtras().get(CoreContants.PARAMS_MSG);
		pkSv =new PaiKeBoKeServiceImpl();
		initViews();
		initDisplay();
	}
	
	
	private void initDisplay(){
		dlglist =new ArrayList<DialogObj>();
		DialogObj dobj=new DialogObj("1", "拍照");
		DialogObj dobj2=new DialogObj("2", "在相册中选择");
		dlglist.add(dobj);
		dlglist.add(dobj2);
	}
	
	private void initViews(){
		
		this.ed_title=(EditText) this.findViewById(R.id.et_upload_title);
		
		btn_right = (Button)this.findViewById(R.id.btn_title_right);
		btn_right.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_dialogsure_selector));
		btn_right.setVisibility(View.VISIBLE);
		
		ll_photo=(LinearLayout) this.findViewById(R.id.ll_photo);
		
		im_ph_1=(ImageView) this.findViewById(R.id.im_ph_1);
		im_ph_1.setOnClickListener(this);
		im_ph_2=(ImageView) this.findViewById(R.id.im_ph_2);
		im_ph_2.setOnClickListener(this);
		im_ph_3=(ImageView) this.findViewById(R.id.im_ph_3);
		im_ph_3.setOnClickListener(this);
		im_ph_4=(ImageView) this.findViewById(R.id.im_ph_4);
		im_ph_4.setOnClickListener(this);
		im_ph_5=(ImageView) this.findViewById(R.id.im_ph_5);
		im_ph_5.setOnClickListener(this);
		im_ph_6=(ImageView) this.findViewById(R.id.im_ph_6);
		im_ph_6.setOnClickListener(this);
		im_ph_7=(ImageView) this.findViewById(R.id.im_ph_7);
		im_ph_7.setOnClickListener(this);
		im_ph_8=(ImageView) this.findViewById(R.id.im_ph_8);
		im_ph_8.setOnClickListener(this);
		im_ph_9=(ImageView) this.findViewById(R.id.im_ph_9);
		im_ph_9.setOnClickListener(this);
		
		btn_photo = (Button) this.findViewById(R.id.btn_photo);
		btn_photo.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.upload_photo_btn_selector));
		ll_show_imgs=(LinearLayout) this.findViewById(R.id.ll_show_imgs);
		btn_photo.setOnClickListener(new View.OnClickListener() {
			
			private DialogObj mSelectItem;//已选项目

			@Override
			public void onClick(View arg0) {
				
				
				DialogOptionSelect dlg = new DialogOptionSelect(UploadPhotoActivity.this,new DialogOptionSelect.PickDialogcallback() {
					
					@Override
					public void onItemSelect(DialogObj selectItem) {
						
						if("1".equals(selectItem.getCode())){//从相机拍照获取相片
							takephoto();
						}else{//从相册中获取
							getImgFromlib();
						}
					}
				},dlglist,mSelectItem);
				dlg.show();
			}
		});
	
		
		btn_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				cmmodel.setTitle(ed_title.getText().toString());
				if(FuncUtil.isEmpty(cmmodel.getTitle())){
					DialogUtil.showToast(UploadPhotoActivity.this, "请填写标题！");
					return;
				}
				DialogUtil.showProgressDialog(UploadPhotoActivity.this);
				SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {
					
					@Override
					public StBaseType requestApi() {
						int index=0;
						FileInputStream ins1=null;
						FileInputStream ins2=null;
						FileInputStream ins3=null;
						FileInputStream ins4=null;
						FileInputStream ins5=null;
						FileInputStream ins6=null;
						FileInputStream ins7=null;
						FileInputStream ins8=null;
						FileInputStream ins9=null;
						try {
							for(PhotoAndVideoModel pkmodel:phtModel){
								index++;
								if(index%9==1){
									cmmodel.setPicname1(FileUtils.getPathName(pkmodel.getFilePath()));	
									cmmodel.setPic1(getFileIns(pkmodel.getFilePath(),ins1));
								}
								if(index%9==2){
									cmmodel.setPicname2(FileUtils.getPathName(pkmodel.getFilePath()));	
									cmmodel.setPic2(getFileIns(pkmodel.getFilePath(),ins2));
								}
								if(index%9==3){
									cmmodel.setPicname3(FileUtils.getPathName(pkmodel.getFilePath()));	
									cmmodel.setPic3(getFileIns(pkmodel.getFilePath(),ins3));
								}
								if(index%9==4){
									cmmodel.setPicname4(FileUtils.getPathName(pkmodel.getFilePath()));	
									cmmodel.setPic4(getFileIns(pkmodel.getFilePath(),ins4));
								}
								if(index%9==5){
									cmmodel.setPicname5(FileUtils.getPathName(pkmodel.getFilePath()));	
									cmmodel.setPic5(getFileIns(pkmodel.getFilePath(),ins5));
								}
								if(index%9==6){
									cmmodel.setPicname6(FileUtils.getPathName(pkmodel.getFilePath()));
									cmmodel.setPic6(getFileIns(pkmodel.getFilePath(),ins6));
								}
								if(index%9==7){
									cmmodel.setPicname7(FileUtils.getPathName(pkmodel.getFilePath()));
									cmmodel.setPic7(getFileIns(pkmodel.getFilePath(),ins7));
								}
								if(index%9==8){
									cmmodel.setPicname8(FileUtils.getPathName(pkmodel.getFilePath()));	
									cmmodel.setPic8(getFileIns(pkmodel.getFilePath(),ins8));
								}
								if(index%9==0){
									cmmodel.setPicname9(FileUtils.getPathName(pkmodel.getFilePath()));	
									cmmodel.setPic9(getFileIns(pkmodel.getFilePath(),ins9));
								}
							}
							RspResultModel rsp =pkSv.pushPhotos(cmmodel);
							return rsp;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							try{
								if(ins1!=null)
									ins1.close();
								if(ins2!=null)
									ins2.close();
								if(ins3!=null)
									ins3.close();
								if(ins4!=null)
									ins4.close();
								if(ins5!=null)
									ins5.close();
								if(ins6!=null)
									ins6.close();
								if(ins7!=null)
									ins7.close();
								if(ins8!=null)
									ins8.close();
								if(ins9!=null)
									ins9.close();
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						return null;
					}
					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(UploadPhotoActivity.this);
						RspResultModel result = (RspResultModel)baseType;
						if(ComUtil.checkRsp(UploadPhotoActivity.this, result)){
							DialogUtil.showToast(UploadPhotoActivity.this, "上传拍客信息成功");
						}else{
							DialogUtil.showToast(UploadPhotoActivity.this, "上传拍客信息失败");
						}
					}
				});
			}
		});
	}

	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		log.info(requestCode+";"+resultCode);
		try {
			if (CoreContants.REQUEST_CODE_IMAGE == requestCode) {
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
						final String file = ComUtil.saveImage(this, path,480,700);
						PhotoAndVideoModel ph=new PhotoAndVideoModel();
						ph.setFilePath(file);
						phtModel.add(ph);
						refreshImageView(file);
					}
					else{
						DialogUtil.showToast(this, "选择图片失败");
					}
				} else {
					DialogUtil.showToast(this, "选择图片失败");
				}
			}else{
				PhotoAndVideoModel phv=phtModel.get(phtModel.size()-1);
				String file = ComUtil.saveImage(this, phv.getFilePath(),480,700);
				phv.setFilePath(file);//去缩略图
				refreshImageView(file);
			}
			
			
		} catch (Exception e) {
			DialogUtil.showToast(this, "拍照失败");
			return;
		}
	}

	@Override
	public void onClick(final View v) {
		 
		 DialogUtil.showConfirmAlertDialog(this, "确定删除该图片？","删除图片提示", new OnAlertSureOnclick() {
			
			@Override
			public void onAlertSureOnclick() {
				PhotoAndVideoModel tem = null;
				for(PhotoAndVideoModel ph:phtModel){
					if(ph.getId().equals(v.getId()+"")){
						tem=ph;
						break;
					}
				}
				phtModel.remove(tem);
				FileUtils.deleteFile(tem.getFilePath());
				v.setVisibility(View.GONE); 
			}
		});
	}
	
	/**
	 * 刷新图片
	 * @param file
	 */
	private  void refreshImageView(String file){
		int index=0;
		for(PhotoAndVideoModel p:phtModel){
			Bitmap bitmap = BitmapFactory.decodeFile(p.getFilePath());
			bitMapList.add(bitmap);
			if(bitmap!=null){
				if(index==0){
					im_ph_1.setImageBitmap(bitmap);
					im_ph_1.setVisibility(View.VISIBLE);
					p.setId(R.id.im_ph_1+"");
				}
				if(index==1){
					im_ph_2.setImageBitmap(bitmap);
					im_ph_2.setVisibility(View.VISIBLE);
					p.setId(R.id.im_ph_2+"");
				}
				if(index==2){
					im_ph_3.setImageBitmap(bitmap);
					im_ph_3.setVisibility(View.VISIBLE);
					p.setId(R.id.im_ph_3+"");
				}
				if(index==3){
					im_ph_4.setImageBitmap(bitmap);
					im_ph_4.setVisibility(View.VISIBLE);
					p.setId(R.id.im_ph_4+"");
				}
				if(index==4){
					im_ph_5.setImageBitmap(bitmap);
					im_ph_5.setVisibility(View.VISIBLE);
					p.setId(R.id.im_ph_5+"");
				}
				if(index==5){
					im_ph_6.setImageBitmap(bitmap);
					im_ph_6.setVisibility(View.VISIBLE);
					p.setId(R.id.im_ph_6+"");
				}
				if(index==6){
					im_ph_7.setImageBitmap(bitmap);
					im_ph_7.setVisibility(View.VISIBLE);
					p.setId(R.id.im_ph_7+"");
				}
				if(index==7){
					im_ph_8.setImageBitmap(bitmap);
					im_ph_8.setVisibility(View.VISIBLE);
					p.setId(R.id.im_ph_8+"");
				}
				if(index==8){
					im_ph_9.setImageBitmap(bitmap);
					im_ph_9.setVisibility(View.VISIBLE);
					p.setId(R.id.im_ph_9+"");
				}
				index++;
			}
		}
	}
	
	/**
	 * 拍照
	 */
	public void takephoto(){
		// TODO Auto-generated method stub
		if(phtModel.size()==maxPhtos){
			DialogUtil.showToast(UploadPhotoActivity.this, "每次只允许上次9张图片");
			return ;
		}
		PhotoAndVideoModel ph=new PhotoAndVideoModel();
		ph.setFilePath(ComUtil.takePhoto(this));
		phtModel.add(ph);
	}
	
	
	public void getImgFromlib(){
		// TODO Auto-generated method stub
		if(phtModel.size()==maxPhtos){
			DialogUtil.showToast(UploadPhotoActivity.this, "每次只允许上次9张图片");
			return ;
		}
		ComUtil.getImagesFromLib(this);
	}

	@Override
	protected void onStop() {
		for(Bitmap bmap:bitMapList){
			if(bmap!=null){
				bmap.recycle();
				bmap=null;
			}
		}
		super.onStop();
	}
	
	private FileInputStream getFileIns(String fileName,FileInputStream ins) throws Exception{
		if(!FuncUtil.isEmpty(fileName)){
			File imageFile = new File(fileName);
			ins = new FileInputStream(imageFile);
			log.info("文件大小:"+ins.available()/1024+"KB");
			return ins;
		}
		return null;
	}
	
}
