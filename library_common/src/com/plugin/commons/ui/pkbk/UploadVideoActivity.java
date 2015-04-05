package com.plugin.commons.ui.pkbk;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.RelativeLayout;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DialogUtil.OnAlertSureOnclick;
import com.plugin.commons.helper.FileUtils;
import com.plugin.commons.helper.FuncUtil;
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
public class UploadVideoActivity extends BaseActivity implements OnClickListener{
	
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
	private List<PhotoAndVideoModel>  phtModel=new ArrayList<PhotoAndVideoModel>();
	private List<Bitmap> bitMapList=new ArrayList<Bitmap>();
	private int maxPhtos=9;
	private EditText ed_title;
	private Button btn_photo;
	private LinearLayout ll_photo;
	private RelativeLayout rl_up_video;
	private ArrayList<DialogObj> dlglist;
	private PaiKeBoKeService pkSv;
	private CommonModel cmmodel;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_video);
		 
		ComUtil.customeTitle(this,"播客上传",true);
		cmmodel=(CommonModel) getIntent().getExtras().get(CoreContants.PARAMS_MSG);
		pkSv =new PaiKeBoKeServiceImpl();
		initViews();
		initDisplay();
	}
	
	
	private void initDisplay(){
		dlglist =new ArrayList<DialogObj>();
		DialogObj dobj=new DialogObj("1", "拍视频");
		DialogObj dobj2=new DialogObj("2", "在视频库中选择");
		dlglist.add(dobj);
		dlglist.add(dobj2);
	}
	
	private void initViews(){
		btn_right = (Button)this.findViewById(R.id.btn_title_right);
		btn_right.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_dialogsure_selector));
		btn_right.setVisibility(View.VISIBLE);
		this.ed_title=(EditText) this.findViewById(R.id.et_upload_title);
		ll_photo=(LinearLayout) this.findViewById(R.id.ll_photo);
		rl_up_video=(RelativeLayout) this.findViewById(R.id.rl_up_video);
		
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
		btn_photo.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.boke_btn_selector));
		ll_show_imgs=(LinearLayout) this.findViewById(R.id.ll_show_imgs);
		btn_photo.setOnClickListener(new View.OnClickListener() {
			private DialogObj mSelectItem;//已选项目
			@Override
			public void onClick(View arg0) {

				DialogOptionSelect dlg = new DialogOptionSelect(UploadVideoActivity.this,new DialogOptionSelect.PickDialogcallback() {
					
					@Override
					public void onItemSelect(DialogObj selectItem) {
						
						if("1".equals(selectItem.getCode())){//从相机拍照获取相片
							takeVideo();
						}else{//从视频库中获取
							getVideoFromlib();
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
					DialogUtil.showToast(UploadVideoActivity.this, "请填写标题！");
					return;
				}
				DialogUtil.showProgressDialog(UploadVideoActivity.this);
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
							for(PhotoAndVideoModel pkmodel:phtModel){//暂时只能上传一个视频
								index++;
								if(index%9==1){
									cmmodel.setVideoname(FileUtils.getPathName(pkmodel.getFilePath()));	
									cmmodel.setVidio(getFileIns(pkmodel.getFilePath(),ins1));
									cmmodel.setVideoFile(pkmodel.getFilePath());
								}
							}
							RspResultModel rsp =pkSv.pushVideo(cmmodel);
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
						DialogUtil.closeProgress(UploadVideoActivity.this);
						RspResultModel result = (RspResultModel)baseType;
						if(ComUtil.checkRsp(UploadVideoActivity.this, result)){
							DialogUtil.showToast(UploadVideoActivity.this, "上传播客信息成功");
						}else{
							DialogUtil.showToast(UploadVideoActivity.this, "上传播客信息失败");
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
			
			if (CoreContants.REQUEST_CODE_TAKE_VIDEO == requestCode) {
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
							PhotoAndVideoModel ph=new PhotoAndVideoModel();
							ph.setFilePath(filePath);
							phtModel.add(ph);
							refreshImageView(filePath);
						}

					}
				}
			}else{
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
						PhotoAndVideoModel ph=new PhotoAndVideoModel();
						ph.setFilePath(path);
						phtModel.add(ph);
						refreshImageView(file);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			DialogUtil.showToast(this, "获取视频失败");
			return;
		}
	}

	@Override
	public void onClick(final View v) {
		 
		 DialogUtil.showConfirmAlertDialog(this, "确定删除该视频？","删除视频提示", new OnAlertSureOnclick() {
			
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
			Bitmap bitmap = ComUtil.getVideoThumbnail(p.getFilePath(),100,100,MediaStore.Images.Thumbnails.MINI_KIND);
			bitMapList.add(bitmap);
			if(bitmap!=null){
				if(index==0){
					im_ph_1.setImageBitmap(bitmap);
					im_ph_1.setVisibility(View.VISIBLE);
					p.setId(R.id.im_ph_1+"");
					if(rl_up_video!=null){
						rl_up_video.setVisibility(View.VISIBLE);
					}
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
	 * 录影
	 */
	private void takeVideo(){
		// TODO Auto-generated method stub
		if(phtModel.size()==maxPhtos){
			DialogUtil.showToast(UploadVideoActivity.this, "每次只允许上次1个视频");
			return ;
		}
		ComUtil.takeVideo(this);	
	}
	
	/**
	 * 选择视频
	 */
	private void getVideoFromlib(){
		if(phtModel.size()==maxPhtos){
			DialogUtil.showToast(UploadVideoActivity.this, "每次只允许上次1个视频");
			return ;
		}
		ComUtil.getVideoFromLib(this);
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
