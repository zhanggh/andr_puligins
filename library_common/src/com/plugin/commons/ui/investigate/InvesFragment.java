package com.plugin.commons.ui.investigate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.adapter.InvestigateListAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.model.InvestigateModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.DisClsTestService;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.zq.types.StBaseType;

public class InvesFragment extends BaseFragment {
	public DingLog log = new DingLog(InvesFragment.class);
	private static final String TAG = "HomeTabFragment";
	 
	private List<InvestigateModel> invesList = new ArrayList<InvestigateModel>();
	private InvestigateListAdapter mAdapter;
	
	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合

	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点

	private TextView tv_imgtitle;
	private int currentItem = 0; // 当前图片的索引号

	NewsService newsSvc;
	NewsTypeModel mNewType;
	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;
	View mAdView;

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};
	 
	protected void initViews(View view) {
		lv_news = (PullToRefreshListView) view.findViewById(R.id.lv_news);
		View headView = initAdView();
		if(headView!=null){
			lv_news.getRefreshableView().addHeaderView(headView);
		}
		lv_news.getRefreshableView().setHeaderDividersEnabled(false);
		mAdapter = new InvestigateListAdapter(mActivity,invesList);
		lv_news.setAdapter(mAdapter);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				doRefresh();
			}
		});
		
		lv_news.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				Toast.makeText(mActivity, "已无更多加载", Toast.LENGTH_SHORT).show();
			}
		});
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mActivity,InvesDetailActivity.class);
//				intent.putExtra(InvesAllFragment.PARAMS_NEWS,newList.get(arg2-2));
				mActivity.startActivity(intent);
			}
			
		});
	}
	
	protected void initDisplay() {
		//mMsgTv.setText("暂无新闻");
		RspResultModel rsp =DisClsTestService.getAllInves();
		mAdapter.setDataList(rsp.getInveslist());
		mAdapter.notifyDataSetChanged();
		
	}
	
	private View initAdView(){
		LayoutInflater mInflater = LayoutInflater.from(mActivity);
		mAdView = mInflater.inflate(R.layout.view_newhead, null);
		LinearLayout ll_dot = (LinearLayout) mAdView.findViewById(R.id.ll_dot);
		imageResId = new int[] { R.drawable.pk002, R.drawable.pk001, R.drawable.pk002, R.drawable.pk001, R.drawable.pk002 };
		titles = new String[imageResId.length];
		titles[0] = "“幸福浑南”送文化进社区活动拉开帷幕";
		titles[1] = "改革创新--竞聘上岗";
		titles[2] = "辽宁政务公开活动日扩大地理国情普查宣传";
		titles[3] = "南区开展“庆八一”流动图书站进军营活动";
		titles[4] = "沈阳音乐学院违规招生 近百考生成牺牲品";

		imageViews = new ArrayList<ImageView>();
		dots = new ArrayList<View>();
		dots.add(mAdView.findViewById(R.id.v_dot0));
		dots.add(mAdView.findViewById(R.id.v_dot1));
		dots.add(mAdView.findViewById(R.id.v_dot2));
		dots.add(mAdView.findViewById(R.id.v_dot3));
		dots.add(mAdView.findViewById(R.id.v_dot4));
		dots.add(mAdView.findViewById(R.id.v_dot5));
		dots.add(mAdView.findViewById(R.id.v_dot6));
		dots.add(mAdView.findViewById(R.id.v_dot7));
		dots.add(mAdView.findViewById(R.id.v_dot8));
		// 初始化图片资源
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(mActivity);
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.FIT_XY);
			imageViews.add(imageView);
			dots.get(i).setVisibility(View.VISIBLE); 
		}
		//tv_imgtitle = (TextView) mAdView.findViewById(R.id.tv_imgtitle);
		tv_imgtitle.setText(titles[0]);//

		viewPager = (ViewPager) mAdView.findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		return mAdView;
	}
	
	private void doRefresh()
	{
		ComUtil.showListNone(getView(), "努力加载中...", invesList);
		// Do work to refresh the list here.
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				RspResultModel rsp = DisClsTestService.getAllInves();;
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(mActivity, result)){
					invesList = result.getInveslist();
					log.info("newList:"+invesList.size());
					mAdapter.setDataList(invesList);
					mAdapter.notifyDataSetChanged();
				}
				lv_news.onRefreshComplete();
			}
			
		});
		
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}
	
	@Override
	public void onResume() {
		log.info("start image");
		if(mAdView!=null){
			scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			// 当Activity显示出来后，每两秒钟切换一次图片显示
			scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
		}
		
		super.onResume();
	}

	@Override
	public void onPause() {
		// 当Activity不可见的时候停止切换
		log.info("image fm pause");
		if(mAdView!=null){
			scheduledExecutorService.shutdown();
		}
		super.onPause();
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
			return imageResId.length;
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
				System.out.println("imagecurrentItem: " + currentItem);
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
			tv_imgtitle.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	@Override
	public void onFrageSelect(int index) {
		// TODO Auto-generated method stub
		
	}

	public NewsService getNewsSvc() {
		return newsSvc;
	}

	public void setNewsSvc(NewsService newsSvc) {
		this.newsSvc = newsSvc;
	}

	public NewsTypeModel getmNewType() {
		return mNewType;
	}

	public void setmNewType(NewsTypeModel mNewType) {
		this.mNewType = mNewType;
	}
}
