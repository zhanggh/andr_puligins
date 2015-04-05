package com.plugin.commons.ui.baoliao;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.plugin.commons.widget.TopIndicator;
import com.plugin.commons.widget.TopIndicator.OnTopIndicatorListener;

public class MyBaoliaoFragment extends BaseFragment implements OnTopIndicatorListener {

	public static final String TAG = "MyBaoliaoFragment";
	private Activity mActivity;
	private TextView mTitleTv;
	private ViewPager mViewPager;
	private TabPagerAdapter mPagerAdapter;
	private TopIndicator mTopIndicator;
	DingLog log = new DingLog(MyBaoliaoFragment.class);
	  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_baoliao_my, container, false);
		return view;
	}

	 
	protected void initViews(View view) {
		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		mPagerAdapter = new TabPagerAdapter(getFragmentManager());
		CharSequence[] mLabels = new CharSequence[] { "我的报料", "我的回答","我的收藏"};
		mTopIndicator = (TopIndicator) view.findViewById(R.id.top_indicator);
		mTopIndicator.setmLabels(mLabels);
		mTopIndicator.setOnTopIndicatorListener(this);
		mTopIndicator.refresh();
	}
	
	protected void initDisplay() {
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.invalidate();
		mPagerAdapter.notifyDataSetChanged();
	}

	 
	@Override
	public String getFragmentName() {
		return TAG;
	}

	private class TabPagerAdapter extends FragmentStatePagerAdapter implements
			ViewPager.OnPageChangeListener {

		private int posindex=0;

		public TabPagerAdapter(FragmentManager fm) {
			super(fm);
			mViewPager.setOnPageChangeListener(this);
		}
 
		@Override
		public Fragment getItem(int position) {
			log.info("getItem position:"+position);
			Fragment fragment = null;
//			position=posindex;
			if(position==0){
				if(fragment==null)
					fragment = (MyBaoliaoTabFragment) Fragment
					.instantiate(mActivity,
						MyBaoliaoTabFragment.class.getName());
			}else if(position==1){
				if(fragment==null)
					fragment = (MyBlReplyTabFragment) Fragment
					.instantiate(mActivity,
						MyBlReplyTabFragment.class.getName());
			}else if(position==2){
				if(fragment==null)
					fragment = (MyBlCollectTabFragment) Fragment
					.instantiate(mActivity,
						MyBlCollectTabFragment.class.getName());
			}else {
				if(fragment==null)
					fragment = (MyBaoliaoTabFragment) Fragment
					.instantiate(mActivity,
						MyBaoliaoTabFragment.class.getName());
			}
//			fragment.setMsgName("message name " + position);
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			 
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			log.info("onPageScrolled position:"+position);
			
		}

		@Override
		public void onPageSelected(int position) {
			log.info("onPageSelected position:"+position);
			this.posindex=position;
			mTopIndicator.setTabsDisplay(mActivity, position);
		}
	}

	@Override
	public void onIndicatorSelected(int index) {
		log.info("onIndicatorSelected index:"+index);
		mViewPager.setCurrentItem(index);
	}

	@Override
	public void onFrageSelect(int index) {
		// TODO Auto-generated method stub
		
	}
}
