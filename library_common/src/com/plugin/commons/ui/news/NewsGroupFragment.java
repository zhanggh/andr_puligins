package com.plugin.commons.ui.news;

import java.util.HashMap;
import java.util.Map;

import org.xinhua.analytics.analytics.AnalyticsAgent;

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
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.plugin.commons.ui.fragment.base.FindPeopleFragment;
import com.plugin.commons.ui.fragment.base.WapFragment;
import com.viewpagerindicator.TabPageIndicator;

public class NewsGroupFragment extends BaseFragment {
	DingLog log = new DingLog(NewsGroupFragment.class);
	public static final String TAG = "NewsGroupFragment";
	private Map<String,BaseFragment> mMap = new HashMap<String,BaseFragment>();
	private TextView mTitleTv;
	private ViewPager mViewPager;
	private TabPagerAdapter mPagerAdapter;
	private TabPageIndicator mTopIndicator;
	NewsTypeModel mNewType;
	NewsService newsSvc;
	private String typeId = "";

	public static NewsGroupFragment newInstance() {
		NewsGroupFragment homeFragment = new NewsGroupFragment();
		return homeFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gov, container, false);
		newsSvc = new NewsServiceImpl();
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
				// TODO Auto-generated method stub
				BaseFragment fm = mMap.get(arg0+"");
				if(fm!=null){
					fm.onFrageSelect(arg0);//刷新当前页面
				}
			}
			
		};
		mNewType = newsSvc.getNewsType(typeId);
		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		mPagerAdapter = new TabPagerAdapter(getFragmentManager());
		CharSequence[] mLabels = new CharSequence[mNewType.getSubtypes().size()];
		for(int i=0;i<mNewType.getSubtypes().size();i++){
			mLabels[i] = mNewType.getSubtypes().get(i).getName();
		}
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(onpage);
		mTopIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		mTopIndicator.setOnPageChangeListener(onpage);
		mTopIndicator.setViewPager(mViewPager);
		//mViewPager.get
		//mTopIndicator.setOnTopIndicatorListener(this);
		//mTopIndicator.refresh();
	}
	
	private void initDisplay() {
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.invalidate();
		mPagerAdapter.notifyDataSetChanged();
		BaseFragment fm = mMap.get("0");
		log.info("fm is null:"+(fm==null));
		if(fm!=null){
			fm.onFrageSelect(0);//第一个页面一进入就刷新
		}
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

	private class TabPagerAdapter extends FragmentStatePagerAdapter{

		public TabPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			
			NewsTypeModel subType = mNewType.getSubtypes().get(position);
			if(CoreContants.NEWS_SUBTYPE_VIDEO.equals(subType.getType())){
				NewsVideoTabFragment fragment = (NewsVideoTabFragment) Fragment
						.instantiate(mActivity,
								NewsVideoTabFragment.class.getName());
				fragment.setMsgName(position+"");
				fragment.setmNewType(subType);
				mMap.put(position+"", fragment);//内存中保存生成的fragment
				return fragment;
			}else{
				if("1".equals(subType.getHassub())){
					SubNewsTabFragment fragment = (SubNewsTabFragment) Fragment
							.instantiate(mActivity,
									SubNewsTabFragment.class.getName());
					fragment.setMsgName(position+"");
					fragment.setmNewType(subType);
					mMap.put(position+"", fragment);//内存中保存生成的fragment
					return fragment;
				}if(CoreContants.NEWS_SUBTYPE_WAP.equals(subType.getType())){
					WapFragment fragment = (WapFragment) Fragment
					.instantiate(mActivity,
							WapFragment.class.getName());
					fragment.setMsgName(position+"");
					fragment.setUrl(subType.getOuturl());
					mMap.put(position+"", fragment);//内存中保存生成的fragment
					return fragment;
				}if(CoreContants.NEWS_SUBTYPE_DEVELOPPING.equals(subType.getType())){
					FindPeopleFragment fragment = (FindPeopleFragment) Fragment
					.instantiate(mActivity,
							FindPeopleFragment.class.getName());
					fragment.setMsgName(position+"");
					mMap.put(position+"", fragment);//内存中保存生成的fragment
					return fragment;
				}else{
					NewsTabFragment fragment = (NewsTabFragment) Fragment
							.instantiate(mActivity,
									NewsTabFragment.class.getName());
					fragment.setMsgName(position+"");
					fragment.setmNewType(subType);
					mMap.put(position+"", fragment);//内存中保存生成的fragment
					return fragment;
				}
				
			}
		}

		@Override
		public int getCount() {
			return mNewType.getSubtypes().size();
		}

		
		 @Override
        public CharSequence getPageTitle(int position) {
			if(mNewType.getSubtypes().size()==4){
				 return " "+mNewType.getSubtypes().get(position).getName()+" ";
			}else{
				return mNewType.getSubtypes().get(position).getName();
			}
        }
	 // 初始化每个页卡选项  
        @Override  
        public Object instantiateItem(ViewGroup arg0, int arg1) {  
            // TODO Auto-generated method stub  
            return super.instantiateItem(arg0, arg1);  
        }  
          
        @Override  
        public void destroyItem(ViewGroup container, int position, Object object) {  
            mMap.remove(position+"");//销毁内存中的fragment
            super.destroyItem(container, position, object);  
        }  
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public void onFrageSelect(int idnex){
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onPageStart(getActivity(), "NewsGroupFragment"+typeId);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPageEnd(getActivity(), "NewsGroupFragment"+typeId);
	}
}
