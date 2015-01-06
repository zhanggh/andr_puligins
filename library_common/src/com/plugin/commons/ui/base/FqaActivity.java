package com.plugin.commons.ui.base;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;

import com.plugin.R;
import com.plugin.commons.adapter.TreeViewAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.widget.TreeView;

public class FqaActivity  extends Activity{
	private LayoutInflater mInflater;
	private TreeView treeView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_fqa);
		ComUtil.customeTitle(this, "常见问题",true);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		mInflater = LayoutInflater.from(this);
		treeView = (TreeView) findViewById(R.id.zh_tree_view);
		treeView.setHeaderView(getLayoutInflater().inflate(
				R.layout.list_head_view, treeView, false));
		treeView.setGroupIndicator(null);
		treeView.setAdapter(new TreeViewAdapter(mInflater,treeView));
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
}