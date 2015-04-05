package com.plugin.commons.ui.news;

import java.util.HashMap;
import java.util.Map;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plugin.R;
import com.plugin.commons.adapter.TabPagerAdapter;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.listener.PageChangeListener;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.service.XinHuaService;
import com.plugin.commons.service.XinHuaServiceImpl;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.viewpagerindicator.TabPageIndicator;

public class NewsGroupFragment extends BaseFragment {
	DingLog log = new DingLog(NewsGroupFragment.class);
	public static final String TAG = "NewsGroupFragment";
	private Map<String,BaseFragment> mMap = new HashMap<String,BaseFragment>();
//	private TextView mTitleTv;
	private ViewPager mViewPager;
	private TabPagerAdapter mPagerAdapter;
	private TabPageIndicator mTopIndicator;
	NewsTypeModel mNewType;
	NewsService newsSvc;
	private String typeId = "";
	XinHuaService xhSv;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gov, container, false);
		return view;
	}
 
	protected void initViews(View view) {
		newsSvc = new NewsServiceImpl();
		xhSv=new XinHuaServiceImpl();
		ViewPager.OnPageChangeListener onpage = new PageChangeListener();
		mNewType = newsSvc.getNewsType(typeId);
		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		mPagerAdapter = new TabPagerAdapter(getFragmentManager(),mNewType,this.mActivity,xhSv);
		CharSequence[] mLabels = new CharSequence[mNewType.getSubtypes().size()];
		for(int i=0;i<mNewType.getSubtypes().size();i++){
			mLabels[i] = mNewType.getSubtypes().get(i).getName();
		}
		mViewPager.setAdapter(mPagerAdapter);
		mTopIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		mTopIndicator.setOnPageChangeListener(onpage);
		mTopIndicator.setViewPager(mViewPager);
	}
	
	protected void initDisplay() {
		mPagerAdapter.notifyDataSetChanged();
		BaseFragment fm = mMap.get("0");
		log.info("fm is null:"+(fm==null));
		if(fm!=null){
			fm.onFrageSelect(0);//第一个页面一进入就刷新
		}
	}


	@Override
	public String getFragmentName() {
		return TAG;
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
