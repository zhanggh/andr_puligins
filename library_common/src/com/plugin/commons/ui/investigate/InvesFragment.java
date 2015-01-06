package com.plugin.commons.ui.investigate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.plugin.commons.widget.TopIndicator;
import com.plugin.commons.widget.TopIndicator.OnTopIndicatorListener;

@SuppressLint("NewApi")
public class InvesFragment extends BaseFragment implements OnTopIndicatorListener {
	private ViewPager mViewPager;
	private TabPagerAdapter mPagerAdapter;
	private TopIndicator mTopIndicator;
	DingLog log = new DingLog(InvesFragment.class);
	private Activity mActivity;
	public static final String TAG = "InvesFragment";

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_investigate, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initDisplay();
	}
	
	private void initViews(View view) {
		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		mPagerAdapter = new TabPagerAdapter(this.getFragmentManager());
		CharSequence[] mLabels = new CharSequence[] { "全部", "社会热点","生活消费","体验娱乐","趣味其他"};
		mTopIndicator = (TopIndicator) view.findViewById(R.id.top_indicator);
		mTopIndicator.setmLabels(mLabels);
		mTopIndicator.setOnTopIndicatorListener(this);
		mTopIndicator.refresh();
	}
	
	private void initDisplay() {
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.invalidate();
		mPagerAdapter.notifyDataSetChanged();
	}
	
	private class TabPagerAdapter extends FragmentStatePagerAdapter implements
			ViewPager.OnPageChangeListener {


		public TabPagerAdapter(FragmentManager fm) {
			super(fm);
			mViewPager.setOnPageChangeListener(this);
		}

		 
		@Override
		public Fragment getItem(int position) {
			log.info("getItem position:" + position);
			Fragment fragment = null;
			 
			fragment = (InvesAllFragment) Fragment
			.instantiate(mActivity,
					InvesAllFragment.class.getName());
			return fragment;
		}

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			log.info("onPageScrolled position:" + position);

		}

		@Override
		public void onPageSelected(int position) {
			log.info("onPageSelected position:" + position);
			mTopIndicator.setTabsDisplay(mActivity, position);
		}
	}

	@Override
	public void onIndicatorSelected(int index) {
		log.info("onIndicatorSelected index:" + index);
		mViewPager.setCurrentItem(index);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

	@Override
	public void onFrageSelect(int index) {
		// TODO Auto-generated method stub
		
	}

}
