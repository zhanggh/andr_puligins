package com.plugin.commons.ui.askgov;

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
import com.plugin.commons.widget.TopIndicator;
import com.plugin.commons.widget.TopIndicator.OnTopIndicatorListener;

public class AskMyFragment extends Fragment implements OnTopIndicatorListener {
	DingLog log = new DingLog(AskMyFragment.class);
	private Activity mActivity;
	public int mSelectTab=0;
	private ViewPager mViewPager;
	private TabPagerAdapter mPagerAdapter;
	private TopIndicator mTopIndicator;
	View mView;
	AskMyAskFragment fragment1;
	AskMyAnswerFragment fragment2;
	AskMyAskAcctectionFragment fragment3;
	public static AskMyFragment newInstance() {
		AskMyFragment homeFragment = new AskMyFragment();
		return homeFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
		log.info("acttach:"+(mActivity==null));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_askgov_my, container, false);
		mView = view;
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		log.info("onViewCreated");
		initViews(view);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		log.info("onActivityCreated");
		initDisplay();
		
	}

	private void initViews(View view) {
		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		mPagerAdapter = new TabPagerAdapter(getFragmentManager());
		CharSequence[] mLabels = new CharSequence[] { "我的提问", "我的回答", "我的关注"
		};
		mTopIndicator = (TopIndicator) view.findViewById(R.id.top_indicator);
		mTopIndicator.setOnTopIndicatorListener(this);
		mTopIndicator.setmLabels(mLabels);
		mTopIndicator.refresh();
		
	}
	
	private void initDisplay() {
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.invalidate();
		mPagerAdapter.notifyDataSetChanged();
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
	public void onDestroyView()  
    {  
	  // TODO Auto-generated method stub  
		super.onDestroyView();  
	    for(int i=0;i<mPagerAdapter.getCount();i++){
	    	if(mPagerAdapter.getItem(i)!=null){
	    		getFragmentManager().beginTransaction().remove(mPagerAdapter.getItem(i)).commit();  
	    	}
	    }
	 } 
	
	private class TabPagerAdapter extends FragmentStatePagerAdapter implements
		ViewPager.OnPageChangeListener {
	
		public TabPagerAdapter(FragmentManager fm) {
			super(fm);
			mViewPager.setOnPageChangeListener(this);
		}
		
		@Override
		public Fragment getItem(int position) {
			if(position==0){
				if(fragment1==null){
					fragment1 = (AskMyAskFragment) Fragment
							.instantiate(mActivity,
									AskMyAskFragment.class.getName());
				}
				
				return fragment1;
			}
			else if(position==1){
				if(fragment2==null){
					fragment2 = (AskMyAnswerFragment) Fragment
							.instantiate(mActivity,
									AskMyAnswerFragment.class.getName());
				}
				
				return fragment2;
			}
			else if(position==2){
				if(fragment3==null){
					fragment3 = (AskMyAskAcctectionFragment) Fragment
							.instantiate(mActivity,
									AskMyAskAcctectionFragment.class.getName());
				}
				
				return fragment3;
			}
			else{
				fragment1 = (AskMyAskFragment) Fragment
						.instantiate(mActivity,
								AskMyAskFragment.class.getName());
				return fragment1;
			}
			
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
			
		}
		
		@Override
		public void onPageSelected(int position) {
			mTopIndicator.setTabsDisplay(mActivity, position);
		}
	}
	
	@Override
	public void onIndicatorSelected(int index) {
		mViewPager.setCurrentItem(index);
	}
}
