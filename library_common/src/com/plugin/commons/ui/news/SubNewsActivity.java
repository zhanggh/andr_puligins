package com.plugin.commons.ui.news;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.NewsListAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.CacheDataService;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.ui.base.BaseActivity;
import com.zq.types.StBaseType;

public class SubNewsActivity extends BaseActivity {
	public DingLog log = new DingLog(SubNewsActivity.class);
	private List<NewsInfoModel> newList = new ArrayList<NewsInfoModel>();
	private List<NewsInfoModel> headList = new ArrayList<NewsInfoModel>();
	private ZhKdBaseAdapter<NewsInfoModel> mAdapter;
	private ViewPager viewPager;// android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合
	private List<View> dots; // 图片标题正文的那些点
	private TextView tv_imgtitle;
	private int currentItem = 0; // 当前图片的索引号
	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;
	View mAdView;
	NewsService newsSvc;
	NewsTypeModel mNewType;
	NewsInfoModel mNews;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_pullrefresh);
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_NEWS)) {
        	mNews = (NewsInfoModel) getIntent().getExtras().getSerializable(CoreContants.PARAMS_NEWS);
		}
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CoreContants.PARAMS_TYPE)) {
        	mNewType = (NewsTypeModel) getIntent().getExtras().getSerializable(CoreContants.PARAMS_TYPE);
		}
        ComUtil.customeTitle(this,FuncUtil.getPexfStr(mNews.getTitle(), 12, "..."),true);
        if(CoreContants.APP_LNZX.equals(ComApp.APP_NAME)){//个性化 ╮(╯▽╰)╭
        	mNewType.setName(FuncUtil.getPexfStr(mNews.getTitle(), 12, "..."));
        }
        initViews();
        initDisplay();
    }
    
    

	private void initViews() {
		newsSvc = new NewsServiceImpl();
		lv_news = (PullToRefreshListView) findViewById(R.id.lv_news);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(SubNewsActivity.this, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				log.info("下拉进行刷新");
				doRefresh(true);
			}
		});
		
		lv_news.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				doRefresh(false);
//				Toast.makeText(SubNewsActivity.this, "已无更多加载", Toast.LENGTH_SHORT).show();
			}
		});
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mNewType.setHassub("0");
				ComUtil.goNewsDetail(SubNewsActivity.this, newList.get(arg2-(mAdView==null?1:2)), mNewType);
			}
			
		});
	}
	
	private void initDisplay() {
		//mMsgTv.setText("暂无新闻");
		RspResultModel rsp  = newsSvc.getSubNewsList(true, "0", "20",mNewType.getId(), "0",mNews.getId());
		if(ComUtil.checkRsp(SubNewsActivity.this, rsp,false)){
			headList = rsp.getHeadnew_list();
			if(rsp.getArticle_list().size()>0){
				newList=rsp.getArticle_list();
				refreshList();
				CacheDataService.setNeedLoad(CoreContants.CACHE_NEWS_NEWSLIST+mNewType.getParentid()+"_"+mNewType.getId());
			}else{
				doRefresh(true);
			}
		}else{
			doRefresh(true);
		}
		ComUtil.showListNone(findViewById(R.id.ll_root), "暂无数据", newList,headList);
	}
	
	private void refreshList(){
		if(!FuncUtil.isEmpty(headList)&&mAdView==null){
			View headView = initAdView();
			if(headView!=null){
				lv_news.getRefreshableView().addHeaderView(headView);
			}
			lv_news.getRefreshableView().setHeaderDividersEnabled(false);
		}
		mAdapter = new NewsListAdapter(SubNewsActivity.this,newList);
		mAdapter.setDataList(newList);
		lv_news.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		lv_news.onRefreshComplete();
		log.info("是否为空:"+FuncUtil.isEmpty(newList));
		ComUtil.showListNone(findViewById(R.id.ll_root), "暂无数据", newList);
	}
	
	private View initAdView(){
		if(!FuncUtil.isEmpty(headList)){
			LayoutInflater mInflater = LayoutInflater.from(SubNewsActivity.this);
			mAdView = mInflater.inflate(R.layout.view_newhead, null);

			imageViews = new ArrayList<ImageView>();

			// 初始化图片资源
			for (int i = 0; i < headList.size(); i++) {
				ImageView imageView = new ImageView(SubNewsActivity.this);
				//imageView.setImageResource(imageResId[i]);
				imageView.setScaleType(ScaleType.FIT_XY);
				imageViews.add(imageView);
				ComApp.getInstance().getFinalBitmap().display(imageView, headList.get(i).getImg(),ComApp.getInstance().getLoadingBig(),ComApp.getInstance().getLoadingBig());
			}
			List<View> tempdot = new ArrayList<View>();
			tempdot.add(mAdView.findViewById(R.id.v_dot0));
			tempdot.add(mAdView.findViewById(R.id.v_dot1));
			tempdot.add(mAdView.findViewById(R.id.v_dot2));
			tempdot.add(mAdView.findViewById(R.id.v_dot3));
			tempdot.add(mAdView.findViewById(R.id.v_dot4));
			tempdot.add(mAdView.findViewById(R.id.v_dot5));
			tempdot.add(mAdView.findViewById(R.id.v_dot6));
			tempdot.add(mAdView.findViewById(R.id.v_dot7));
			tempdot.add(mAdView.findViewById(R.id.v_dot8));
			dots = new ArrayList<View>();
			for (int i = 0; i < headList.size(); i++) {
				tempdot.get(i).setVisibility(View.VISIBLE);
				dots.add(tempdot.get(i));
			}

			tv_imgtitle = (TextView) mAdView.findViewById(R.id.tv_imgtitle);
			tv_imgtitle.setText(headList.get(0).getTitle());//

			viewPager = (ViewPager) mAdView.findViewById(R.id.vp);
			viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
			// 设置一个监听器，当ViewPager中的页面改变时调用
			viewPager.setOnPageChangeListener(new MyPageChangeListener());
			return mAdView;
		}
		return null;
	}
	
	public void doRefresh(final boolean isRefresh)
	{
		ComUtil.showListNone(this.getEmptyView(), "努力加载中...", newList);
		if(isRefresh){//下拉
			pageStart=0;
		}else{//上拉
			pageStart+=CoreContants.PAGE_SIZE;
		}
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				RspResultModel rsp  = newsSvc.getSubNewsList(false, String.valueOf(pageStart),String.valueOf(CoreContants.PAGE_SIZE),mNewType.getParentid(), mNewType.getId(),mNews.getId());
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(SubNewsActivity.this);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(SubNewsActivity.this, result)){
//					newList = result.getArticle_list();
					headList = result.getHeadnew_list();
					log.info(newList.size()+";size");
					if(result.getArticle_list().size()==0){
						pageStart=CoreContants.PAGE_SIZE;
					}else{
						if(!isRefresh){//如果是上拉，并且返回结果值不为空时，游标递增
							newList.addAll(result.getArticle_list());
						}else{
							newList=result.getArticle_list();
						}
					}
				}
				refreshList();
				
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(mAdView!=null){
			scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			// 当Activity显示出来后，每两秒钟切换一次图片显示
			scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
		}
		lv_news.setRefreshing(false);
		AnalyticsAgent.onResume(this);
	}

	@Override
	public void onPause() {
		// 当Activity不可见的时候停止切换
		if(mAdView!=null){
			scheduledExecutorService.shutdown();
		}
		super.onPause();
		AnalyticsAgent.onPause(this);
	}
	
	/**
	 * 填充ViewPager页面的适配器
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return headList.size();
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
	
	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				//System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}
	
	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_imgtitle.setText(headList.get(position).getTitle());
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
}
