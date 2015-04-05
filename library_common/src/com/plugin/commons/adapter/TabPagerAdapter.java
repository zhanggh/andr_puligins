package com.plugin.commons.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.CryptUtils;
import com.plugin.commons.model.NewsTypeModel;
import com.plugin.commons.petition.PetitionFragment;
import com.plugin.commons.service.XinHuaService;
import com.plugin.commons.ui.fragment.base.FindPeopleFragment;
import com.plugin.commons.ui.fragment.base.WapFragment;
import com.plugin.commons.ui.investigate.InvesFragment;
import com.plugin.commons.ui.news.NewsImageListFragment;
import com.plugin.commons.ui.news.NewsTabFragment;
import com.plugin.commons.ui.news.NewsVideoTabFragment;
import com.plugin.commons.ui.news.SubNewsTabFragment;
import com.plugin.commons.ui.pkbk.ComPhotoFragment;
import com.plugin.commons.ui.pkbk.ComVideoFragment;
import com.plugin.commons.ui.pkbk.TopicPhotoFragment;
import com.plugin.commons.ui.pkbk.TopicVideoFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter{
	private NewsTypeModel mNewType;
	private Activity mActivity;
	private XinHuaService xhSv;
	public TabPagerAdapter(FragmentManager fm,NewsTypeModel mNewType,Activity mActivity,XinHuaService xhSv) {
		super(fm);
		this.mNewType=mNewType;
		this.xhSv=xhSv;
		this.mActivity=mActivity;
	}

	@Override
	public Fragment getItem(int position) {
		
		NewsTypeModel subType = mNewType.getSubtypes().get(position);
		if(CoreContants.NEWS_SUBTYPE_DEVELOPPING.equals(subType.getType())){
			FindPeopleFragment fragment = (FindPeopleFragment) Fragment
			.instantiate(mActivity,
					FindPeopleFragment.class.getName());
			fragment.setMsgName(position+"");
//			mMap.put(position+"", fragment);//内存中保存生成的fragment
			return fragment;
		}else if(CoreContants.NEWS_SUBTYPE_VIDEO.equals(subType.getType())){
			
			if(CoreContants.NEWS_SUBTYPE_PKBK.equals(mNewType.getType())){//拍客播客
				if(subType.getName().equals("主题播")){
					TopicVideoFragment fragment = (TopicVideoFragment) Fragment
					.instantiate(mActivity,
							TopicVideoFragment.class.getName());
					fragment.setMsgName(position+"");
//					mMap.put(position+"", fragment);//内存中保存生成的fragment
					fragment.setmNewType(subType);
					return fragment;
				}else{
					ComVideoFragment fragment = (ComVideoFragment) Fragment
					.instantiate(mActivity,
							ComVideoFragment.class.getName());
					fragment.setMsgName(position+"");
//					mMap.put(position+"", fragment);//内存中保存生成的fragment
					fragment.setmNewType(subType);
					return fragment;
				}
			}else{
				NewsVideoTabFragment fragment = (NewsVideoTabFragment) Fragment
						.instantiate(mActivity,
								NewsVideoTabFragment.class.getName());
				fragment.setMsgName(position+"");
				fragment.setmNewType(subType);
//				mMap.put(position+"", fragment);//内存中保存生成的fragment
				return fragment;
			}
		}else{
			if(CoreContants.NEWS_SUBTYPE_PKBK.equals(mNewType.getType())){//拍客播客
				if(subType.getName().equals("主题拍")){
					TopicPhotoFragment fragment = (TopicPhotoFragment) Fragment
					.instantiate(mActivity,
							TopicPhotoFragment.class.getName());
					fragment.setMsgName(position+"");
					fragment.setmNewType(subType);
//					mMap.put(position+"", fragment);//内存中保存生成的fragment
					return fragment;
				}else{
					ComPhotoFragment fragment = (ComPhotoFragment) Fragment
					.instantiate(mActivity,
							ComPhotoFragment.class.getName());
					fragment.setMsgName(position+"");
					fragment.setmNewType(subType);
//					mMap.put(position+"", fragment);//内存中保存生成的fragment
					return fragment;
				}
			}else if("1".equals(subType.getHassub())){//专题
				SubNewsTabFragment fragment = (SubNewsTabFragment) Fragment
						.instantiate(mActivity,
								SubNewsTabFragment.class.getName());
				fragment.setMsgName(position+"");
				fragment.setmNewType(subType);
//				mMap.put(position+"", fragment);//内存中保存生成的fragment
				return fragment;
			}if(CoreContants.NEWS_SUBTYPE_WAP.equals(subType.getType())){
				WapFragment webFragment = (WapFragment) Fragment
				.instantiate(mActivity,
						WapFragment.class.getName());
				webFragment.setMsgName(position+"");
				
				if("商城".equals(subType.getName())){
		    		if(ComApp.getInstance().isLogin()){
			    		webFragment.setUrl(subType.getOuturl()+"?phone="+ComApp.getInstance().getLoginInfo().getPhone()+"&sign="+CryptUtils.MD5enc("phone="+ComApp.getInstance().getLoginInfo().getPhone()+CoreContants.SHOP_KEY));
			    	}else{
			    		webFragment.setUrl(subType.getOuturl());
			    	}
		    	}else if("新华社发布".equals(subType.getName())||"新华发布".equals(subType.getName())){ 
			    	String url=xhSv.getXinHuaIndex(ComApp.getInstance().appStyle.appid,subType.getOuturl(),ComApp.getInstance().appStyle.xinhuaKey);
			    	webFragment.setUrl(url);
		    	}else if("中国网事".equals(subType.getName())){ 
		    		webFragment.showWebRefreshBar(false);
		    		webFragment.setUrl(subType.getOuturl());
		    	}else{
		    		webFragment.setUrl(subType.getOuturl());
		    	}
//				mMap.put(position+"", webFragment);//内存中保存生成的fragment
				return webFragment;
			}if(CoreContants.NEWS_SUBTYPE_DIAOCHA.equals(subType.getType())){//调查
				InvesFragment fragment = (InvesFragment) Fragment.instantiate(mActivity,InvesFragment.class.getName());
				fragment.setMsgName(position+"");
				fragment.setmNewType(subType);
//				mMap.put(position+"", fragment);//内存中保存生成的fragment
				return fragment;
			}else if(CoreContants.NEWS_SUBTYPE_PIC.equals(subType.getType())){//图片新闻
				NewsImageListFragment fragment = (NewsImageListFragment) Fragment
				.instantiate(mActivity,
						NewsImageListFragment.class.getName());
				fragment.setMsgName(position+"");
				fragment.setmNewType(subType);
		//		mMap.put(position+"", fragment);//内存中保存生成的fragment
				return fragment;
			}if(CoreContants.NEWS_SUBTYPE_LETTER.equals(subType.getType())){//信件，互动交流
				PetitionFragment fragment = (PetitionFragment) Fragment
				.instantiate(mActivity,
						PetitionFragment.class.getName());
				fragment.setMsgName(position+"");
				fragment.setmNewType(subType);
		//		mMap.put(position+"", fragment);//内存中保存生成的fragment
				return fragment;
			}else{//图文
				NewsTabFragment fragment = (NewsTabFragment) Fragment
						.instantiate(mActivity,
								NewsTabFragment.class.getName());
				fragment.setMsgName(position+"");
				if(CoreContants.MENU_CODE_ZHLNZX_TIAN.equals(subType.getId())){
					subType.setType(CoreContants.NEWS_SUBTYPE_WORD_EXT);
		    	}
				fragment.setmNewType(subType);
//				mMap.put(position+"", fragment);//内存中保存生成的fragment
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
			return "    "+mNewType.getSubtypes().get(position).getName()+"    ";
		}
    }
 // 初始化每个页卡选项  
    @Override  
    public Object instantiateItem(ViewGroup arg0, int arg1) {  
        
        return super.instantiateItem(arg0, arg1);  
    }  
      
    @Override  
    public void destroyItem(ViewGroup container, int position, Object object) {  
//        mMap.remove(position+"");//销毁内存中的fragment
        super.destroyItem(container, position, object);  
    }  
}