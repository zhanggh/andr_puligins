package com.plugin.commons.ui.my;

import java.util.HashMap;
import java.util.Map;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.plugin.commons.user.LoginActivity;
import com.plugin.commons.widget.TopIndicator;
import com.plugin.commons.widget.TopIndicator.OnTopIndicatorListener;

public abstract class MyBaseFragmentActivity extends FragmentActivity  implements OnTopIndicatorListener {

	public DingLog log = new DingLog(this.getClass());
	public static final String TAG = "MyFragmentActivity";
	private TextView mTitleTv;
	private Map<String,BaseFragment> mMap = new HashMap<String,BaseFragment>();
	private ViewPager mViewPager;
	private TabPagerAdapter mPagerAdapter;
	private TopIndicator mTopIndicator;
	private CharSequence[] mLabels;
	
	boolean isOnresume = false;
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_baoliao_my);
		ComUtil.customeTitle(this, getResources().getString(R.string.gov_my),true);
		
		// TODO Auto-generated method stub
		if(!ComApp.getInstance().isLogin())
		{
			Intent intent = new Intent(this,LoginActivity.class);
			intent.putExtra(LoginActivity.PARAM_BACK, LoginActivity.PARAM_BACK);
			this.startActivityForResult(intent,100);
		}else{
			initViews();
		}
	}
	
	private void initViews() {
		ViewPager.OnPageChangeListener onpage = new ViewPager.OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageSelected(int arg0) {
				mTopIndicator.setTabsDisplay(MyBaseFragmentActivity.this, arg0);
				BaseFragment fm = mMap.get(arg0+"");
				if(fm!=null){
					fm.onFrageSelect(arg0);//刷新当前页面
				}
			}
			
		};
		
		mViewPager = (ViewPager) this.findViewById(R.id.view_pager);
		mPagerAdapter = new TabPagerAdapter(this.getSupportFragmentManager());
		mLabels = initTapItems();
		
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(onpage);
 
		mTopIndicator = (TopIndicator) this.findViewById(R.id.top_indicator);
		mTopIndicator.setmLabels(mLabels);
		mTopIndicator.setOnTopIndicatorListener(this);
		mTopIndicator.refresh();
		initDisplay();
	}
	
	 

	private void initDisplay() {
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.invalidate();
		mPagerAdapter.notifyDataSetChanged();
	}

	private class TabPagerAdapter extends FragmentStatePagerAdapter{

		public TabPagerAdapter(FragmentManager fm) {
			super(fm);
//			mViewPager.setOnPageChangeListener(this);
		}

		@Override
		public Fragment getItem(int position) {
			BaseFragment fragment = null;
			fragment=mMap.get(position+"");
			if(fragment==null){
				fragment=getSelectItem(position);
 				mMap.put(position+"", fragment);//内存中保存生成的fragment
			}
			
			return fragment;
		}

		@Override
		public int getCount() {
			return mLabels.length;
		}

		 @Override
        public CharSequence getPageTitle(int position) {
            return mLabels[position % mLabels.length];
        }
	 // 初始化每个页卡选项  
        @Override  
        public Object instantiateItem(ViewGroup arg0, int arg1) {  
            // TODO Auto-generated method stub  
        	log.info("初始化:"+arg1);
            return super.instantiateItem(arg0, arg1);  
        }  
          
        @Override  
        public void destroyItem(ViewGroup container, int position, Object object) {  
            System.out.println( "position Destory" + position);  
            mMap.remove(position+"");//销毁内存中的fragment
            super.destroyItem(container, position, object);  
        }  
	}

	@Override
	public void onIndicatorSelected(int index) {
		mViewPager.setCurrentItem(index);
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
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
		if(RESULT_OK==resultCode){
			initViews();
		}else{
			this.finish();
		}
    }  
	
	/**
	 * 初始化tap项目
	 * @return
	 */
	public abstract CharSequence[] initTapItems();
	/**
	 * 获取fragment
	 * @return
	 */
	public abstract BaseFragment getSelectItem(int position);
}
