package com.plugin.commons.ui.pkbk;

import java.util.ArrayList;
import java.util.List;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.ComPhotListAdapter;
import com.plugin.commons.adapter.ZhKdBaseAdapter;
import com.plugin.commons.helper.ComUtil;
import com.plugin.commons.helper.DialogUtil;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.helper.SituoHttpAjax;
import com.plugin.commons.helper.SituoHttpAjax.SituoAjaxCallBack;
import com.plugin.commons.helper.XHConstants;
import com.plugin.commons.helper.XHSDKUtil;
import com.plugin.commons.model.CommonModel;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.service.CacheDataService;
import com.plugin.commons.service.DisClsTestService;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;
import com.plugin.commons.ui.fragment.base.BaseFragment;
import com.plugin.commons.user.LoginActivity;
import com.zq.types.StBaseType;

public class ComPhotoFragment extends BaseFragment {
	public DingLog log = new DingLog(ComPhotoFragment.class);
	private static final String TAG = "ComPhotoFragment";
	private List<NewsInfoModel> newList = new ArrayList<NewsInfoModel>();
	private ZhKdBaseAdapter<NewsInfoModel> mAdapter;
	NewsService newsSvc;
	DisClsTestService dctest;
	NewsTypeModel mNewType;
	public boolean isVideo=false;
	private ImageView im_tkphoto;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.info(getFragmentName(), " onCreateView()");
		View view = inflater.inflate(R.layout.fragment_photo_list, container, false);
		return view;
	}
	
	
	protected void initViews(View view) {
		newsSvc = new NewsServiceImpl();
		
		
		im_tkphoto=(ImageView) view.findViewById(R.id.im_tkphoto);
		im_tkphoto.setBackgroundDrawable(this.getResources().getDrawable(ComApp.getInstance().appStyle.paike_btn_selector));

		
		lv_news = (PullToRefreshListView) view.findViewById(R.id.lv_news);
		lv_news.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				doRefresh(false,true);
			}
		});
		
		lv_news.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				//Toast.makeText(mActivity, "已无更多加载", Toast.LENGTH_SHORT).show();
				doRefresh(false,false);
			}
		});
	
		lv_news.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Intent intet=new Intent(mActivity,PhotoDetailActivity.class);
//				mActivity.startActivity(intet);
				XHSDKUtil.addXHBehavior(mActivity, mNewType.getParentid()+"_"+newList.get(arg2-1).getId(), XHConstants.XHTOPIC_ARTICAL_CLICK, newList.get(arg2-1).getId());
				ComUtil.goNewsDetail(mActivity, newList.get(arg2-1), mNewType);
			}
			
		});
		
		
		im_tkphoto.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!ComApp.getInstance().isLogin()){
					Intent intent = new Intent(mActivity,LoginActivity.class);
					intent.putExtra(CoreContants.PARAM_BACK, CoreContants.PARAM_BACK);
					mActivity.startActivityForResult(intent, CoreContants.LOGIN_REQ_PHOTO_CODE);
					return ;
				}
				toUploadPhoto();
			}
		});
	}
	
	protected void initDisplay() {
		doRefresh(true, true);
		refreshList();
		if(CacheDataService.isNeedLoad(CoreContants.CACHE_HOME_LIST))
		{
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					Message msg = new Message();
					mHandler.sendMessage(msg);
				}
			}, 200);
			
		}
	}
	
	public void doRefresh(final boolean cache,final boolean isRefresh)
	{
		ComUtil.showListNone(this.getView(), "努力加载中...", newList);
		if(isRefresh){//下拉
			pageStart=0;
		}else{//上拉
			pageStart+=CoreContants.PAGE_SIZE;
		}
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				RspResultModel rsp = newsSvc.getNewsList(cache,String.valueOf(pageStart),String.valueOf(CoreContants.PAGE_SIZE),mNewType.getParentid(), mNewType.getId());
				return rsp;
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(mActivity);
				RspResultModel result = (RspResultModel)baseType;
				if(ComUtil.checkRsp(mActivity, result)){
					if(result.getArticle_list().size()==0){
						pageStart-=CoreContants.PAGE_SIZE;
					}else{
						if(!isRefresh){//如果是上拉，并且返回结果值不为空时，游标递增
							newList.addAll(result.getArticle_list());
						}else{
							newList=result.getArticle_list();
						}
					}
					refreshList();
					CacheDataService.setNeedLoad(CoreContants.CACHE_NEWS_NEWSLIST+mNewType.getParentid()+"_"+mNewType.getId());
				}
				else{
					lv_news.onRefreshComplete();
				}
			}
		});
	}
	
	 private Handler mHandler = new Handler() {

			public void handleMessage(Message msg) {
				synchronized (mActivity) {
					doRefresh(false,true);
				}
			}
	 };
	
	private void refreshList(){
		if(FuncUtil.isEmpty(newList)){
			newList = new ArrayList<NewsInfoModel>();
		}
		if(mAdapter==null){
			mAdapter = new ComPhotListAdapter(mActivity,newList);
			lv_news.setAdapter(mAdapter);
		}
		mAdapter.setDataList(newList);
		mAdapter.notifyDataSetChanged();
		lv_news.onRefreshComplete();
		ComUtil.showListNone(getView(), "暂无数据", newList);
		log.info("是否为空:"+FuncUtil.isEmpty(newList));
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}
	public void onFrageSelect(int idnex){
		
	}
	
	private void toUploadPhoto(){
		CommonModel cmmodel=new CommonModel();
		cmmodel.setType(this.mNewType.getParentid());
		cmmodel.setSubtype(this.mNewType.getId());
		Intent intent=new Intent(mActivity, UploadPhotoActivity.class);
		intent.putExtra(CoreContants.PARAMS_MSG, cmmodel);
		mActivity.startActivity(intent); 
	}
	
	@Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onPageStart(getActivity(), "ComPhotoFragment");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPageEnd(getActivity(), "ComPhotoFragment");
	}

	public NewsTypeModel getmNewType() {
		return mNewType;
	}

	public void setmNewType(NewsTypeModel mNewType) {
		this.mNewType = mNewType;
	}

}
