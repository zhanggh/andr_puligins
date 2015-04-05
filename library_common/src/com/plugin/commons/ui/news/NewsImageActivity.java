package com.plugin.commons.ui.news;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.NewPicItemModel;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.CacheDataService;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.ui.base.BaseActivity;
import com.plugin.commons.ui.fragment.base.GuideScroll;
import com.zq.types.StBaseType;

public class NewsImageActivity extends BaseActivity {
	DingLog log = new DingLog(NewsImageActivity.class);
	private GuideScroll mScrollLayout;
	int mSelectItem=0;
	NewsInfoModel mNews;
	NewsTypeModel mNewType;
	TextView tv_title;
	TextView tv_desc;
	TextView tv_readcount;
	public int pageStart=0;
	List<NewPicItemModel> imageList;
	Button btn_left;
	NewsService newsSvc;
	private List<ImageView> imageViews; // 滑动的图片集合
	FrameLayout fl_scroll;
	ViewPager viewPager;
	private String mAttype;
	private Button btn_right;
	private TextView tv_right;
	private View btn_share;
	private Button btn_fav;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagenew);
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_NEWS)) {
        	mNews = (NewsInfoModel) getIntent().getExtras().getSerializable(CoreContants.PARAMS_NEWS);
		}
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_TYPE)) {
        	mNewType = (NewsTypeModel) getIntent().getExtras().getSerializable(CoreContants.PARAMS_TYPE);
		}
        ComUtil.customeTitle(this,FuncUtil.getPexfStr(mNews.getTitle(), 12, "..."),true);
        initViews();
        request();
    }
    
    private void initViews()
    {
    	newsSvc = new NewsServiceImpl();
    	btn_left = (Button)this.findViewById(R.id.btn_title_left);
    	tv_title = (TextView)this.findViewById(R.id.tv_img_title);
    	tv_desc = (TextView)this.findViewById(R.id.tv_desc);
    	tv_readcount = (TextView)this.findViewById(R.id.tv_readcount);
    	fl_scroll = (FrameLayout)this.findViewById(R.id.fl_scroll);
    	btn_right = (Button)this.findViewById(R.id.btn_title_right);
    	fl_scroll.setBackgroundResource(ComApp.getInstance().appStyle.placeholder_b);
        tv_title.setText(mNews.getTitle());
        btn_share = (Button)this.findViewById(R.id.btn_share);
		btn_share.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_share_selector));
		btn_fav = (Button)this.findViewById(R.id.btn_fav);
		btn_fav.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_fav_selector));
        
        btn_left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
        if(mNewType!=null){
			mAttype = FuncUtil.isEmpty(mNewType.getParentid())? mNewType.getId():mNewType.getParentid();
			if(FuncUtil.isEmpty(mNews.getArttype())){
				mNews.setArttype(mAttype);
			}
		}
		else{
			mAttype = mNews.getArttype();
		}
		
		NewsTypeModel ntype=newsSvc.getNewsType(mAttype);
		if(ntype!=null){
			if(CoreContants.NEWS_COMMENT.equals(newsSvc.getNewsType(mAttype).getOpenreply())){
				btn_right.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_comment_selector));
				btn_right.setVisibility(View.VISIBLE);
				tv_right = (TextView)this.findViewById(R.id.tv_right);
				tv_right.setVisibility(View.VISIBLE);
				tv_right.setText(mNews.getReplycount());
				ComUtil.addComment(NewsImageActivity.this,mNews,newsSvc,mAttype,tv_right,CacheModel.CACHE_IMG_NEWS);
			}else{
				this.findViewById(R.id.rl_commentbar).setVisibility(View.GONE);
				btn_right.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.btn_colection_selector));
				btn_right.setVisibility(View.VISIBLE);
				CacheModel cm = CacheDataService.getAcction(CacheModel.CACHE_ASKNEWS,mNews.getArttype()+mNews.getId());
				btn_right.setSelected(cm!=null);
			}
			btn_right.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(CoreContants.NEWS_COMMENT.equals(newsSvc.getNewsType(mAttype).getOpenreply())){
						Intent intent = new Intent(NewsImageActivity.this,NewsCommentsListActivity.class);
						intent.putExtra(CoreContants.PARAMS_MSG,"0");
						intent.putExtra(CoreContants.PARAMS_MSG_ID,mNews.getId());
						intent.putExtra(CoreContants.PARAMS_TYPE,mAttype);
						startActivity(intent);
					}else{
						CacheModel cm = new CacheModel();
						cm.type = CacheModel.CACHE_IMG_NEWS;
						cm.id = mNews.getArttype()+mNews.getId();
						cm.msg = mNews;
						ComUtil.doCollection(cm,NewsImageActivity.this,mNews,mAttype,btn_right);
					}
				}
			});
		}else{
			btn_right.setVisibility(View.INVISIBLE);
			this.findViewById(R.id.rl_commentbar).setVisibility(View.GONE);
		}
    }
    
    private void showGuideView(){
    	if(!FuncUtil.isEmpty(imageList)){
			imageViews = new ArrayList<ImageView>();
			Bitmap loadImg = BitmapFactory.decodeResource(getResources(),R.drawable.draw_bgblack);
			//imageList.get(0).setImg("http://img04.tooopen.com/images/20130805/tooopen_18234668.jpg");
			//imageList.get(0).setDescition("fdsfse发动机司法局饿哦围绕而我诶我热uwofjwerwe分为沃尔沃而非我热我分为我热我热我维吾尔");
			// 初始化图片资源
			for (int i = 0; i < imageList.size(); i++) {
				ImageView imageView = new ImageView(this);
				imageView.setScaleType(ScaleType.FIT_CENTER);
				imageView.setBackgroundColor(getResources().getColor(R.color.black));
				imageViews.add(imageView);
				ComApp.getInstance().getFinalBitmap().display(imageView, imageList.get(i).getImg(),loadImg,loadImg);
			}
			tv_desc.setText(imageList.get(0).getDescition());
			tv_readcount.setText("("+(mSelectItem+1)+"/"+imageList.size()+")");
			viewPager = (ViewPager) fl_scroll.findViewById(R.id.vp);
			viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
			viewPager.setOnPageChangeListener(new MyPageChangeListener());
    	}
	}
	private void request(){
		DialogUtil.showProgressDialog(NewsImageActivity.this);
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){

			@Override
			public StBaseType requestApi() {
				// TODO Auto-generated method stub
				RspResultModel rsp = newsSvc.getPicNewsDetail(
						FuncUtil.isEmpty(mNewType.getParentid())?mNewType.getId():mNewType.getParentid(), mNews.getId());
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(NewsImageActivity.this);
				// TODO Auto-generated method stub
				RspResultModel rsp = (RspResultModel)baseType;
				if(ComUtil.checkRsp(NewsImageActivity.this, rsp)){
					if(!FuncUtil.isEmpty(rsp.getContent())){
						formatList(rsp.getContent());
						showGuideView();
					}
				}
			}
			
		});
	}
	
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageList.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}
	
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			mSelectItem = position;
			tv_desc.setText(imageList.get(position).getDescition());
			tv_readcount.setText("("+(mSelectItem+1)+"/"+imageList.size()+")");
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}
	
	private void formatList(List<List<String>>content){
		imageList = new ArrayList<NewPicItemModel>();
		for(int i=0;i<content.size();i++){
			NewPicItemModel item = new NewPicItemModel();
			item.setImg(content.get(i).get(0));
			item.setDescition(content.get(i).get(1));
			item.setId(i+"");
			imageList.add(item);
		}
	}
}
