package com.plugin.commons.ui.fragment.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xinhua.analytics.analytics.AnalyticsAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.plugin.R;
import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.adapter.NavDrawerListAdapter;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.MenuModel;
import com.plugin.commons.model.NavDrawerItem;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.service.NewsService;
import com.plugin.commons.service.NewsServiceImpl;


@SuppressLint({ "NewApi", "ValidFragment" })
public class MenuFragment extends Fragment implements OnItemClickListener {
	DingLog log = new DingLog(MenuFragment.class);
	//private ListView mDrawerList;
	private String[] mNavMenuTitles;
	private String[] mNavMenuCodes;
	private TypedArray mNavMenuIconsTypeArray;
	private ArrayList<NavDrawerItem> mNavDrawerItems;
	private NavDrawerListAdapter mAdapter;
	private SLMenuListOnItemClickListener mCallback;
	private int selected = -1;
	GridView gv_menu;

	private int arrayMenuNames;
	private int arrayMenuCodes;
	private int arrayMenuIcons;
	
	private View rootView;
	
	public MenuFragment(){
		this.arrayMenuNames=ComApp.getInstance().appStyle.nav_drawer_items;
		this.arrayMenuCodes=ComApp.getInstance().appStyle.nav_code_items;
		this.arrayMenuIcons=ComApp.getInstance().appStyle.nav_drawer_icons;
	}
	
	public MenuFragment(int arrayMenuNames,int arrayMenuCodes,int arrayMenuIcons){
		super();
		this.arrayMenuNames=arrayMenuNames;
		this.arrayMenuCodes=arrayMenuCodes;
		this.arrayMenuIcons=arrayMenuIcons;
	}
	
	@Override
	public void onAttach(Activity activity) {
		try {
			mCallback = (SLMenuListOnItemClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnResolveTelsCompletedListener");
		}
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment_menu, null);
		if(Boolean.parseBoolean(ComApp.getInstance().appStyle.isImgbackgFormenu)){
			rootView.setBackgroundResource(ComApp.getInstance().appStyle.background_left);
		}else{
			rootView.setBackgroundColor(ComApp.getInstance().appStyle.backgroup_reft_color);
		}
		findView(rootView);
		return rootView;
	}

	@SuppressLint("NewApi")
	private void findView(View rootView) {
		initMenuList();
		//mDrawerList = (ListView) rootView.findViewById(R.id.left_menu);  
		gv_menu = (GridView)rootView.findViewById(R.id.gv_menu);
          
        // setting the nav drawer list adapter  
        mAdapter = new NavDrawerListAdapter(getActivity(),  
                        mNavDrawerItems);  
        gv_menu.setAdapter(mAdapter);  
        gv_menu.setOnItemClickListener(this);  
        
        if(selected!=-1){
        	//gv_menu.setItemChecked(selected, true);  
        	gv_menu.setSelection(selected);  
        }else{
        	//gv_menu.setItemChecked(0, true);  
        	gv_menu.setSelection(0);  
        }
	}
	
	private void initMenuList(){
		NewsService newsSvc = null;
		newsSvc = new NewsServiceImpl();
		mNavDrawerItems = new ArrayList<NavDrawerItem>();
		 // nav drawer icons from resources  
        mNavMenuIconsTypeArray = getResources()  
                    .obtainTypedArray(arrayMenuIcons);  
		//注意：后台返回的栏目只能小于或者等于app中配置的栏目总数
		Map<String,Integer> allMenuFromRes=new HashMap<String,Integer >();
		Map<String,Integer> allMenuFromMap=ComApp.getInstance().getMenuMap();
		//后台返回的栏目
		List<NewsTypeModel> menusOnline = newsSvc.getNewsTypes();
		//最终显示在app的栏目
		List<MenuModel> menus=new ArrayList<MenuModel>(); 

		//首页
		MenuModel md=new MenuModel();
		md.setCode(CoreContants.MENU_CODE_HOME);
		md.setName("首页");
		md.setIcon(ComApp.getInstance().appStyle.home_btn_selector);
		menus.add(md);
		
        if(allMenuFromMap.size()>0){//从map中获取app全部menu
    		for(NewsTypeModel menu:menusOnline){
    			log.info(menu.getName()+";"+menu.getId());
    			if(allMenuFromMap.containsKey(menu.getId())){
    				MenuModel mn=new MenuModel();
    				mn.setCode(menu.getId());
    				mn.setName(menu.getName());
    				mn.setIcon(allMenuFromMap.get(menu.getId()));
    				menus.add(mn);
    			}
    		}
        }else{//从array中获取app全部menu
        	mNavMenuTitles = getResources().getStringArray(arrayMenuNames);  
    		mNavMenuCodes = getResources().getStringArray(arrayMenuCodes);  
                  
            int index=0;
            for(String code:mNavMenuCodes){
            	allMenuFromRes.put(code.substring(1), mNavMenuIconsTypeArray.getResourceId(index, -1));
    			index++;
    		}
    		for(NewsTypeModel menu:menusOnline){
    			log.info(menu.getName()+";"+menu.getId());
    			if(allMenuFromRes.containsKey(menu.getId())){
    				MenuModel mn=new MenuModel();
    				mn.setCode(menu.getId());
    				mn.setName(menu.getName());
    				mn.setIcon(allMenuFromRes.get(menu.getId()));
    				menus.add(mn);
    			}
    		}
        }
        //初始化app需要的栏目
        for(MenuModel mn:menus){
        	log.info(mn.getName()+";"+mn.getCode()+";"+mn.getIcon());
        	mNavDrawerItems.add(new NavDrawerItem(mn.getName(),mn.getIcon(),mn.getCode()));  
        }
        mNavMenuIconsTypeArray.recycle();  
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		// update selected item and title, then close the drawer  
		//gv_menu.setItemChecked(position, true);  
		gv_menu.setSelection(position);  
        
        if(mCallback!=null){
        	mCallback.selectItem(mNavDrawerItems.get(position));
        }
        selected = position;
	}

	/**
     * 左侧菜单 点击回调接口
     * @author FX_SKY
     *
     */
    public interface SLMenuListOnItemClickListener{
    	
    	public void selectItem(NavDrawerItem item);
    }
    
    @Override
	public void onResume() {
		super.onResume();
		AnalyticsAgent.onPageStart(getActivity(), "MenuFragment");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AnalyticsAgent.onPageEnd(getActivity(), "MenuFragment");
	}
	
}
