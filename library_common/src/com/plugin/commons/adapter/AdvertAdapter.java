package com.plugin.commons.adapter;

import java.util.List;

import com.plugin.commons.model.GovmentInfoModel;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

/**
 * @author zhang
 *	广告插件
 */
public class AdvertAdapter extends PagerAdapter {
	private int count;
	private List<ImageView> imageViews; // 滑动的图片集合
	
	public AdvertAdapter(int count,List<ImageView> imageViews){
		this.count=count;
		this.imageViews=imageViews;
	}
	@Override
	public int getCount() {
		return count;
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
