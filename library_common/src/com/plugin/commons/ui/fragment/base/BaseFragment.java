package com.plugin.commons.ui.fragment.base;

import java.util.Map;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;

/**
 * Fragment����
 * 
 * @author dewyze
 * 
 */
public abstract class BaseFragment extends Fragment {
	public DingLog log = new DingLog(this.getClass());
	public int pageStart=0;
	protected PullToRefreshListView lv_news;
	protected Activity mActivity;
	protected String mMsgName;
	protected Map reqService;
	protected SituoAjaxCallBack sCallBack;
	public void setMsgName(String msgName) {
		this.mMsgName = msgName;
	}
	 
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
		log.info(getFragmentName(), " onAttach()");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		log.info(getFragmentName(), " onCreate()");
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.info(getFragmentName(), " onCreateView()");
		View view = inflater.inflate(R.layout.listview_pullrefresh, container, false);
		return view;
	}
	 
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		log.info(getFragmentName(), " onViewCreated()");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		log.info(getFragmentName(), " onActivityCreated()");
	}

	@Override
	public void onStart() {
		super.onStart();
		log.info(getFragmentName(), " onStart()");
	}

	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onPageStart(mActivity, "MainScreen");
		log.info(getFragmentName(), " onResume()");
	}

	@Override
	public void onPause() {
		super.onPause();
		AnalyticsAgent.onPageEnd(mActivity, "MainScreen");
		log.info(getFragmentName(), " onPause()");
	}

	@Override
	public void onStop() {
		super.onStop();
		log.info(getFragmentName(), " onStop()");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		log.info(getFragmentName(), " onDestroyView()");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		log.info(getFragmentName(), " onDestroy()");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		log.info(getFragmentName(), " onDetach()");
	}

	/**
	 * fragment name
	 */
	public abstract String getFragmentName();
	
	public abstract void onFrageSelect(int index);

}
