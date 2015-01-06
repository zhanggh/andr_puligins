package com.plugin.commons.ui.news;

import java.util.ArrayList;
import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
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
import com.plugin.commons.model.NewPicItemModel;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.ui.fragment.base.GuideScroll;
import com.zq.types.StBaseType;

public class NewsImageActivity extends Activity {
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagenew);
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_NEWS)) {
        	mNews = (NewsInfoModel) getIntent().getExtras().getSerializable(CoreContants.PARAMS_NEWS);
		}
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_TYPE)) {
        	mNewType = (NewsTypeModel) getIntent().getExtras().getSerializable(CoreContants.PARAMS_TYPE);
		}
        initViews();
        request();
    }
    
    private void initViews()
    {
    	newsSvc = new NewsServiceImpl();
    	btn_left = (Button)this.findViewById(R.id.btn_left);
    	tv_title = (TextView)this.findViewById(R.id.tv_title);
    	tv_desc = (TextView)this.findViewById(R.id.tv_desc);
    	tv_readcount = (TextView)this.findViewById(R.id.tv_readcount);
    	fl_scroll = (FrameLayout)this.findViewById(R.id.fl_scroll);
    	fl_scroll.setBackgroundResource(ComApp.getInstance().appStyle.placeholder_b);
        tv_title.setText(mNews.getTitle());
        btn_left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
    }
    
    private void showGuideView(){
    	if(!FuncUtil.isEmpty(imageList)){
			imageViews = new ArrayList<ImageView>();
			// 初始化图片资源
			for (int i = 0; i < imageList.size(); i++) {
				ImageView imageView = new ImageView(this);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				imageViews.add(imageView);
				ComApp.getInstance().getFinalBitmap().display(imageView, imageList.get(i).getImg(),ComApp.getInstance().getLoadingBig(),ComApp.getInstance().getLoadingBig());
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
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onResume(this);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onResume(this);
	}
}
