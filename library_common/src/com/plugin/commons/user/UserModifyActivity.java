package com.plugin.commons.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
import android.widget.RelativeLayout;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.UserInfoModel;
import com.plugin.commons.service.UserInfoService;
import com.plugin.commons.service.UserInfoServiceImpl;
import com.zq.types.StBaseType;
import com.zq.util.StCacheHelper;

public class UserModifyActivity extends Activity{
	DingLog log = new DingLog(UserModifyActivity.class);
	public static final int REQUEST_CODE_PICTER = 31;
	public static String gl_picturePath = "";
	EditText et_name;
	EditText et_email;
	RelativeLayout rl_info;
	ImageView iv_icon;
	UserInfoService userService;
	UserInfoModel mUser;
	Button btn_sure;
	Bitmap bitmap = null;
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usermodify);
		ComUtil.customeTitle(this, getResources().getString(R.string.zh_userinfomodify),true);
		initProper();
		ensureUI();
	}
	
	private void initProper(){
		userService = new UserInfoServiceImpl();
		mUser = ComApp.getInstance().getLoginInfo();
		et_name = (EditText)findViewById(R.id.et_name);
		et_email = (EditText)findViewById(R.id.et_email);
		rl_info = (RelativeLayout)findViewById(R.id.rl_info);
		iv_icon = (ImageView)findViewById(R.id.iv_icon);
		btn_sure = (Button)findViewById(R.id.btn_sure);
		btn_sure.setBackgroundResource(ComApp.getInstance().appStyle.btn_sign_selector);
		
	}
	
	private void ensureUI(){
		et_name.setText(mUser.getUsername());
		if(!FuncUtil.isEmpty(mUser.getPhoto())){
			ComApp.getInstance().getFinalBitmap().display(iv_icon, mUser.getPhoto());
		}
		et_email.setText(FuncUtil.isEmpty(mUser.getEmail())?"":mUser.getEmail());
		rl_info.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(UserModifyActivity.this,RegisterSmsCodeActivity.class);
				//startActivity(intent);
				String state = Environment
						.getExternalStorageState();
				if (state.equals(Environment.MEDIA_MOUNTED)) {
					Intent intent = new Intent(
							Intent.ACTION_GET_CONTENT);
//					intent.addCategory(Intent.CATEGORY_OPENABLE);
					intent.setType("image/*");
					/**
					intent.putExtra("crop", "true");           
					intent.putExtra("aspectX", 1);   
					        intent.putExtra("aspectY", 1);           
					        intent.putExtra("outputX", iv_icon.getLayoutParams().width);           
					        intent.putExtra("outputY", iv_icon.getLayoutParams().height);           
					        intent.putExtra("return-data", true);   
					        **/
					UserModifyActivity.this.startActivityForResult(
							Intent.createChooser(intent,"选择图片"),
							REQUEST_CODE_PICTER);
					/**
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					String saveDir = Environment.getExternalStorageDirectory()
					+ "/vellnicepic";
					File dir = new File(saveDir);
					if (!dir.exists()) {
						dir.mkdir();
					}
					gl_picturePath = saveDir+"/"+System.currentTimeMillis()+".jpg";
					//log.info("文件路径："+RoomListActivity.gl_picturePath);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(gl_picturePath)));
					UserModifyActivity.this.startActivityForResult(intent, 11);
					**/
				} else {
					DialogUtil.showToast(UserModifyActivity.this, "请插入SD卡");
				}							
				
			}
		});
		
		btn_sure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String name = et_name.getText().toString();
				final String email = et_email.getText().toString();
				if(FuncUtil.isEmpty(name)){
					DialogUtil.showToast(UserModifyActivity.this, "用户名不能为空");
					return ;
				}
				DialogUtil.showProgressDialog(UserModifyActivity.this);
				SituoHttpAjax.ajax(new SituoAjaxCallBack(){

					@Override
					public StBaseType requestApi() {
						// TODO Auto-generated method stub
						RspResultModel rsp = userService.modifyUser(name, email);
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(UserModifyActivity.this);
						// TODO Auto-generated method stub
						RspResultModel rsp = (RspResultModel)baseType;
						if(ComUtil.checkRsp(UserModifyActivity.this, rsp)){
							DialogUtil.showToast(UserModifyActivity.this, "修改成功");
							finish();
						}
					}
					
				});
				
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		 if (resultCode != RESULT_OK) {  
	            return;  
	        }  
		if (REQUEST_CODE_PICTER == requestCode) { // 本地上传
			try {
				final String file = ComUtil.saveChosePic(data, this, 128, 128);
				if(!FuncUtil.isEmpty(file)){
					DialogUtil.showProgressDialog(UserModifyActivity.this,"上传中");
					SituoHttpAjax.ajax(new SituoAjaxCallBack(){
						@Override
						public StBaseType requestApi() {
							// TODO Auto-generated method stub
							InputStream imageIS = null;
							RspResultModel rsp = null;
							File imageFile = new File(file);
							try{
								imageIS = new FileInputStream(imageFile);
								log.info("文件长度："+imageIS.available());
								rsp = ComApp.getInstance().getApi().uploadUserphoto(imageFile.getName(), imageIS);
								return rsp;
							}
							catch(Exception e){
								e.printStackTrace();
								return null;
							}
							finally{
								if(imageIS!=null){
									try {
										imageIS.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}

						@Override
						public void callBack(StBaseType baseType) {
							DialogUtil.closeProgress(UserModifyActivity.this);
							// TODO Auto-generated method stub
							RspResultModel rsp = (RspResultModel)baseType;
							if(ComUtil.checkRsp(UserModifyActivity.this, rsp)){
								DialogUtil.showToast(UserModifyActivity.this, "修改成功");
								log.info("rsp.getPhoto():"+rsp.getPhoto());
								Bitmap bitmap=BitmapFactory.decodeFile(file);
								iv_icon.setImageBitmap(bitmap);
								mUser.setPhoto(rsp.getPhoto());
								StCacheHelper.setCacheObj(ComApp.getInstance(), CoreContants.CACHE_USER,"1",mUser);
								ComApp.getInstance().setCustomer(mUser);
							}
						}
					});
				}
				else{
					DialogUtil.showToast(this, "选择图片失败");
				}
			} catch (Exception e) {
				DialogUtil.showToast(this, "选择图片失败");
				e.printStackTrace();
			}
		}
	}


	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onResume(this);//新华sdk
	}
	
	@Override
	 public void onDestroy() {
		   //删除广播注册
		super.onDestroy();
       if(null!=bitmap&& !bitmap.isRecycled()){
			bitmap.recycle();
			bitmap = null;
		}
	}

	/**
	 * 清空呢称
	 * @param v
	 */
	public void reset(View v){
		et_name.setText("");
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPause(this);//新华sdk
	}
}
