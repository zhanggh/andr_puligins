package com.plugin.commons.ui.base;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.listener.ComBroatCast;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.CacheDataService;
import com.plugin.commons.service.DetailBarManager;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.ui.news.NewsCommentsListActivity;
import com.plugin.commons.widget.VideoSurfacePlayer;
import com.zq.types.StBaseType;


public class VideoOLActivity extends BaseActivity{
	DingLog log = new DingLog(VideoOLActivity.class);
	NewsInfoModel mNews;
	Button btn_right;
	Button btn_share;
	Button btn_fav;
	TextView tv_right;
	ImageView iv_image;
	RelativeLayout rl_video;
	TextView tv_arttitle;
	TextView tv_time;
	TextView tv_content;
	NewsService newService;
	NewsTypeModel mNewType;
	String mAttype = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_videool);
		ComUtil.customeTitle(this, " ",true);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_NEWS)) {
			mNews =(NewsInfoModel)getIntent().getExtras().get(CoreContants.PARAMS_NEWS);
		}
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_TYPE)) {
			mNewType =(NewsTypeModel)getIntent().getExtras().get(CoreContants.PARAMS_TYPE);
		}
		//回调
		this.receiver=new ComBroatCast(mNews,this,CoreContants.ACTIVITY_RETTRUN);
		newService = new NewsServiceImpl();
		refreshUI();
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPause(this);//新华sdk
	}
	
	private void refreshUI(){
		btn_share = (Button)this.findViewById(R.id.btn_share);
		btn_share.setBackgroundResource(ComApp.getInstance().appStyle.btn_share_selector);
		btn_fav = (Button)this.findViewById(R.id.btn_fav);
		btn_fav.setBackgroundResource(ComApp.getInstance().appStyle.btn_fav_selector);
		
		btn_right = (Button)this.findViewById(R.id.btn_title_right);
		iv_image = (ImageView)this.findViewById(R.id.iv_image);
		rl_video = (RelativeLayout)this.findViewById(R.id.rl_video);
		tv_arttitle = (TextView)this.findViewById(R.id.tv_arttitle);
		tv_time = (TextView)this.findViewById(R.id.tv_time);
		tv_content = (TextView)this.findViewById(R.id.tv_content);
		if(mNewType!=null){
			mAttype = FuncUtil.isEmpty(mNewType.getParentid())? mNewType.getId():mNewType.getParentid();
			if(FuncUtil.isEmpty(mNews.getArttype())){
				mNews.setArttype(mAttype);
			}
		}
		else{
			mAttype = mNews.getArttype();
		}
		
	
		if(CoreContants.MENU_CODE_VIDEO_WH.equals(mAttype)){
			this.findViewById(R.id.rl_commentbar).setVisibility(View.GONE);
		}
		else{
			//btn_comment_selector
			btn_right.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_comment_selector));
			btn_right.setVisibility(View.VISIBLE);
			tv_right = (TextView)this.findViewById(R.id.tv_right);
			tv_right.setVisibility(View.VISIBLE);
			tv_right.setText(mNews.getReplycount());
			addComment();
		}
		if(mNews!=null){
			log.info(mNews.getDescition()+";"+mNews.getContent());
			tv_arttitle.setText(mNews.getTitle());
			tv_time.setText(mNews.getCreatetime());
			tv_content.setText(mNews.getDescition());
			ComApp.getInstance().getFinalBitmap().display(iv_image, mNews.getImg(), ComApp.getInstance().getLoadingBig(), ComApp.getInstance().getLoadingBig());
		}
		
		rl_video.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(VideoOLActivity.this,VideoWidget.class);
//				intent.putExtra(VideoWidget.PARAM_URL, mNews.getVideourl());
//				intent.putExtra(VideoWidget.PARAM_TITLE, mNews.getTitle());
//				startActivity(intent);
//				overridePendingTransition(R.anim.my_scale_action,
//						R.anim.fade_out);
				//调用系统自带播放器
				ComUtil.playVideoFromUrl(VideoOLActivity.this,mNews.getVideourl());
				
//				Intent intent = new Intent(VideoOLActivity.this,VideoSurfacePlayer.class);
//				intent.putExtra(CoreContants.PARAMS_MSG, mNews.getVideourl());
//				startActivity(intent);
			}
		});
		btn_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(VideoOLActivity.this,NewsCommentsListActivity.class);
				intent.putExtra(CoreContants.PARAMS_MSG,"0");
				intent.putExtra(CoreContants.PARAMS_MSG_ID,mNews.getId());
				intent.putExtra(CoreContants.PARAMS_TYPE,mAttype);
				startActivity(intent);
			}
		});
	}
	
	
	/**
	 * 评论
	 */
	private void addComment(){
		CacheModel cm = new CacheModel();
		cm.type = CacheModel.CACHE_VIDEO;
		cm.id = mNews.getId()+"";
		cm.msg = mNews;
		DetailBarManager detailBarMng = new DetailBarManager(this,this.findViewById(R.id.rl_commentbar),cm);
		detailBarMng.setCommtentCB(new DetailBarManager.OnCommentCallBack() {
			
			@Override
			public void onCommentCallBack(Dialog dlg, final String comment) {
				
				DialogUtil.showProgressDialog(VideoOLActivity.this);
				SituoHttpAjax.ajax(new SituoHttpAjax.SituoAjaxCallBack() {

					@Override
					public StBaseType requestApi() {
						RspResultModel rsp = newService.pubNewComment(mNews.getId(), comment,mAttype);
						return rsp;
					}

					@Override
					public void callBack(StBaseType baseType) {
						DialogUtil.closeProgress(VideoOLActivity.this);
						RspResultModel result = (RspResultModel)baseType;
						if(ComUtil.checkRsp(VideoOLActivity.this, result)){
							if("0".equals(result.getRetcode())){
								DialogUtil.showToast(VideoOLActivity.this, "评论成功");
								int replyCount=Integer.parseInt(mNews.getReplycount())+1;
								tv_right.setText(String.valueOf(replyCount));
								//用户行为采集
								XHSDKUtil.addXHBehavior(VideoOLActivity.this,mAttype+"_"+mNews.getId(), XHConstants.XHTOPIC_COMMENT, mNews.getId());
							}else{
								DialogUtil.showToast(VideoOLActivity.this, "评论失败");
							}
						}
					}
					
				});
			}
		});
		detailBarMng.refreshUI();
	}
	
	private void doCollection(final CacheModel cacheModel){
		CacheModel cm = CacheDataService.getAcction(cacheModel.type, cacheModel.id);
		if(cm==null){
			CacheDataService.addAcction(cacheModel);
			btn_right.setSelected(true);
			DialogUtil.showToast(VideoOLActivity.this, "收藏成功");
			XHSDKUtil.addXHBehavior(VideoOLActivity.this,mAttype+"_"+mNews.getId(), XHConstants.XHTOPIC_ARTICAL_FAV, mNews.getId());
		}
		else{
			CacheDataService.cancelAcction(cacheModel.type, cacheModel.id+"");
			btn_right.setSelected(false);
			XHSDKUtil.addXHBehavior(VideoOLActivity.this,mAttype+"_"+mNews.getId(), XHConstants.XHTOPIC_ARTICAL_FAV_CANCEL, mNews.getId());
			DialogUtil.showToast(VideoOLActivity.this, "取消收藏");
		}
	}

	
	@Override
	protected void onRestart() {
		if(tv_right!=null){
			tv_right.setText(mNews.getReplycount());
		}
		super.onRestart();
	}


	@Override
	protected void onResume() {
		 super.onResume();
		if(tv_right!=null){
			tv_right.setText(mNews.getReplycount());
		}
		if(this.receiver!=null){
	    	IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction(this.receiver.getAcAction());
	        registerReceiver(this.receiver, intentFilter);
	    }
		AnalyticsAgent.onResume(this);//新华sdk
		
	}
}
