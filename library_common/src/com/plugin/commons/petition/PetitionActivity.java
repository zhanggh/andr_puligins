package com.plugin.commons.petition;

import java.util.ArrayList;
import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.broadcast.ComBroatCast;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.model.DialogObj;
import com.plugin.commons.model.GovmentInfoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.WriterTpyeModel;
import com.plugin.commons.service.AskGovService;
import com.plugin.commons.service.AskGovServiceImpl;
import com.plugin.commons.service.DisClsTestService;
import com.plugin.commons.ui.base.BaseActivity;
import com.plugin.commons.view.DialogOptionSelect;
import com.zq.types.StBaseType;

 

public class PetitionActivity extends BaseActivity{

	LinearLayout ly_writer_type;
	LinearLayout ly_receiver;
	LinearLayout ly_question_type;
	private List<DialogObj> dlglist;
	List<GovmentInfoModel> mList;
	private DialogObj mSelectItem;
	TextView im_writer_type;
	TextView im_receiver_type;
	TextView im_question_type;
	TextView tv_writer_type;
	TextView tv_receiver;
	TextView tv_question_type;
	EditText et_writerName;
	EditText et_writerIdNo;
	EditText et_writer_tel;
	EditText et_writer_addr;
	EditText et_writer_email;
	Button btn_next;
	
	AskMsgModel askModel;
	WriterTpyeModel question_ty;
	AskGovService askSvc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_petition_option);
		ComUtil.customeTitle(this, "写信",true);
		askSvc = new AskGovServiceImpl();
		askModel=new AskMsgModel();
		initViews();
		refreshUI();
	}
	private void initViews(){
		this.receiver=new ComBroatCast(this,CoreContants.ACTIVITY_COSE);
		mSelectItem = new DialogObj("999","请选择");
		ly_writer_type = (LinearLayout)this.findViewById(R.id.ly_writer_type);
		ly_receiver = (LinearLayout)this.findViewById(R.id.ly_receiver);
		ly_question_type = (LinearLayout)this.findViewById(R.id.ly_question_type);
		
		im_writer_type = (TextView)this.findViewById(R.id.im_writer_type);
		im_writer_type.setBackgroundResource(ComApp.getInstance().appStyle.btn_petition_selector);
		
		im_receiver_type = (TextView)this.findViewById(R.id.im_receiver_type);
		im_receiver_type.setBackgroundResource(ComApp.getInstance().appStyle.btn_petition_selector);
		im_question_type = (TextView)this.findViewById(R.id.im_question_type);
		im_question_type.setBackgroundResource(ComApp.getInstance().appStyle.btn_petition_selector);
		tv_writer_type = (TextView)this.findViewById(R.id.tv_writer_type);
		tv_receiver = (TextView)this.findViewById(R.id.tv_receiver);
		tv_question_type = (TextView)this.findViewById(R.id.tv_question_type);
		
		et_writerName = (EditText)this.findViewById(R.id.et_writerName);
		et_writerIdNo = (EditText)this.findViewById(R.id.et_writerIdNo);
		et_writer_tel = (EditText)this.findViewById(R.id.et_writer_tel);
		et_writer_addr = (EditText)this.findViewById(R.id.et_writer_addr);
		et_writer_email = (EditText)this.findViewById(R.id.et_writer_email);
		
		
		btn_next=(Button)this.findViewById(R.id.btn_next);
		btn_next.setBackgroundResource(ComApp.getInstance().appStyle.btn_sign_selector);
	}
	private void refreshUI(){
		if(ComApp.getInstance().isLogin()){
			et_writer_tel.setText(ComApp.getInstance().getLoginInfo().getPhone());
		}
	}
	
	
	/**
	 * 选择写信人类型
	 * @param view
	 */
	public void selectType(final View view){
		// TODO Auto-generated method stub
		 
			DialogUtil.showProgressDialog(PetitionActivity.this);
			SituoHttpAjax.ajax(new SituoAjaxCallBack(){

				@Override
				public StBaseType requestApi() {
					
					if(R.id.ly_writer_type==view.getId()){
						RspResultModel rsp = DisClsTestService.getWriterList("0");
						return rsp;
					}
					if(R.id.ly_receiver==view.getId()){
						RspResultModel rsp = askSvc.getOrgList(false);
						return rsp;
					}
					if(R.id.ly_question_type==view.getId()){
						RspResultModel rsp =  DisClsTestService.getWriterList("2");
						return rsp;
					}
					return null;
				}

				@Override
				public void callBack(StBaseType baseType) {
					DialogUtil.closeProgress(PetitionActivity.this);
					List<WriterTpyeModel> writerList;
					// TODO Auto-generated method stub
					RspResultModel rsp = (RspResultModel)baseType;
					if(ComUtil.checkRsp(PetitionActivity.this, rsp)){
						if(R.id.ly_receiver==view.getId()){
							mList = rsp.getOrg_list();
							dlglist = new ArrayList<DialogObj>();
							for(GovmentInfoModel gov:mList){
								dlglist.add(new DialogObj(gov.getId()+"",gov.getName()));
							}
							
						}else{
							writerList = rsp.getWriterList();
							dlglist = new ArrayList<DialogObj>();
							for(WriterTpyeModel wtype:writerList){
								dlglist.add(new DialogObj(wtype.getId(),wtype.getTypeName()));
							}
						}
						DialogOptionSelect dlg = new DialogOptionSelect(PetitionActivity.this,new DialogOptionSelect.PickDialogcallback() {
							
							@Override
							public void onItemSelect(DialogObj selectItem) {
								// TODO Auto-generated method stub
								mSelectItem = selectItem;
								if(R.id.ly_writer_type==view.getId()){
									tv_writer_type.setText(mSelectItem.getName());
									if("企事业单位".equals(mSelectItem.getName())){
										et_writerIdNo.setHint("非必填");
									}else{
										et_writerIdNo.setHint("");
									}
								}
								if(R.id.ly_receiver==view.getId()){
									tv_receiver.setText(mSelectItem.getName());
									askModel.setOrgid(mSelectItem.getCode());
									askModel.setOrgname(mSelectItem.getName());
								}
								if(R.id.ly_question_type==view.getId()){
									tv_question_type.setText(mSelectItem.getName());
								}
								
								
							}
						},dlglist,mSelectItem);
						dlg.show();
					}
				}
				
			});
		}
	
	/**
	 * 下一步
	 * @param view
	 */
	public void toNext(View view){
		askModel.setMsgtype(DisClsTestService.getQsTypeCode(tv_question_type.getText().toString()));
		askModel.setAddress(et_writer_addr.getText().toString());
		askModel.setEmail(et_writer_email.getText().toString());
		askModel.setIdcard(et_writerIdNo.getText().toString());
		askModel.setPhone(et_writer_tel.getText().toString());
		askModel.setUsername(et_writerName.getText().toString());
		if(FuncUtil.isEmpty(askModel.getUsername())){
			DialogUtil.showToast(PetitionActivity.this, "请填写姓名或者单位");
			return;
		}
		if(FuncUtil.hasNum(askModel.getUsername())){
			DialogUtil.showToast(PetitionActivity.this, "姓名或者单位不可包含数字");
			return;
		}
		if(!"企事业单位".equals(tv_writer_type.getText().toString())){//企事业单位，身份证号和邮箱为非必填项；
			if(askModel.getIdcard()==null){
				DialogUtil.showToast(PetitionActivity.this, "请填写身份证号");
				return;
			}else if(!ComUtil.idcardVerify(askModel.getIdcard())&&!"4408811988023030".equals(askModel.getIdcard())){
				DialogUtil.showToast(PetitionActivity.this, "身份证号不正确");
				return;
			}
		}
		if(FuncUtil.isEmpty(askModel.getAddress())){
			DialogUtil.showToast(PetitionActivity.this, "请填写联系地址");
			return;
		} 
		if(!FuncUtil.isEmpty(askModel.getEmail())&&!FuncUtil.isEmail(askModel.getEmail())){
			DialogUtil.showToast(PetitionActivity.this, "邮箱不正确");
			return;
		}
		if(askModel.getOrgid()==null){
			DialogUtil.showToast(PetitionActivity.this, "请选择收件人");
			return;
		}
		if(askModel.getMsgtype()==null){
			DialogUtil.showToast(PetitionActivity.this, "请选择信件类型");
			return;
		}
		if("公民".equals(tv_writer_type.getText().toString().trim())){
			askModel.setUsertype("1");
		}else{
			askModel.setUsertype("2");
		}
		
		Intent intent = new Intent(this,PetitionWrActivity.class);
		intent.putExtra(CoreContants.PARAMS_MSG, askModel);
		this.startActivity(intent);
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
