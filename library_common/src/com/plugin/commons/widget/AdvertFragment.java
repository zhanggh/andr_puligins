package com.plugin.commons.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.adapter.AdvertAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class AdvertFragment  extends Fragment {

	public DingLog log = new DingLog(this.getClass());
	private List<NewsInfoModel> headList = new ArrayList<NewsInfoModel>();
	//自动轮播启用开关  
	private ArrayList<ImageView> imageViews;
	NewsTypeModel mNewType;
	private List<View> dots; // 图片标题正文的那些点
	private TextView tv_imgtitle;
	private ViewPager viewPager;

	//当前轮播页
	public int currentItem=0;
	//定时任务
    private ScheduledExecutorService scheduledExecutorService;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		log.info(getFragmentName(), " onCreateView()");
		View view = inflater.inflate(R.layout.view_newhead, container, false);
		
		initAdView(view);
		return view;
	}
	 
	/**
	 * fragment name
	 */
	public String getFragmentName(){
		return null;
	}
	
	public void initAdView(View mAdView){
		
		if(!FuncUtil.isEmpty(headList)){
			imageViews = new ArrayList<ImageView>();
			
			// 初始化图片资源
			
			for (int i = 0; i <headList.size(); i++) {
				ImageView imageView = new ImageView(getActivity());
				//imageView.setImageResource(imageResId[i]);
				imageView.setScaleType(ScaleType.FIT_XY);
				imageViews.add(imageView);
				final int index = i;
				imageView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						NewsInfoModel  newsMd=headList.get(index);
						if(newsMd!=null&&!FuncUtil.isEmpty(newsMd.getUrl())){
							NewsTypeModel type = new NewsTypeModel();
							type.setId(newsMd.getArttype());
							type.setType(newsMd.getNewtype());
							type.setHassub("0");
							ComUtil.goNewsDetail(getActivity(),newsMd,type);
						}
					}
				});
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
			int length=headList.size()>=tempdot.size()?tempdot.size():headList.size();
			for (int i = 0; i <length ; i++) {
				tempdot.get(i).setVisibility(View.VISIBLE);
				dots.add(tempdot.get(i));
			}

			tv_imgtitle = (TextView) mAdView.findViewById(R.id.tv_imgtitle);
			tv_imgtitle.setText("");
			
			AdvertAdapter avadapter=new AdvertAdapter(length,imageViews);
			viewPager = (ViewPager) mAdView.findViewById(R.id.vp);
			viewPager.setAdapter(avadapter);// 设置填充ViewPager页面的适配器
			// 设置一个监听器，当ViewPager中的页面改变时调用
			viewPager.setOnPageChangeListener(new MyPageChangeListener());
			
		}
	
	}	

	public void onStart() {
		super.onStart();
		log.info("start image");
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4, TimeUnit.SECONDS);
	}

	public void onStop() {
		super.onStop();
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
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
			//tv_imgtitle.setText(headList.get(position).getTitle());
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

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
		
	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};

	public List<NewsInfoModel> getHeadList() {
		return headList;
	}

	public void setHeadList(List<NewsInfoModel> headList) {
		this.headList = headList;
	}
}
