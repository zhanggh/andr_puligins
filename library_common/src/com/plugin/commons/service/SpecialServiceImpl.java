package com.plugin.commons.service;

import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.RspResultModel;
import com.zq.types.Group;

public class SpecialServiceImpl implements SpecialService{
	public static String URL_HOST = "http://113.108.182.3:17580/lndz/static";
	@Override
	public RspResultModel getSpecialList(String start, String size) {
		// TODO Auto-generated method stub
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		Group<NewsInfoModel> list = new Group<NewsInfoModel>();
		for(int i=0;i<12;i++){
			if(i%9==0){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("林蛙油");
				new1.setReplycount("100");
				new1.setDescition("露营嘉年华火爆来袭");
				new1.setImg(URL_HOST+"/news/images/31.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/news_mould3.html");
				list.add(new1);
			}
			else if(i%9==1){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("玉竹");
				new1.setReplycount("100");
				new1.setDescition("淘宝大学电商精英‘辽宁易玛’培训中心在沈阳浑南国家电子商务示范基地正式开班");
				new1.setImg(URL_HOST+"/news/images/2014073109075039418.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/news_mould1.html");
				list.add(new1);
			}
			else if(i%9==2){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("蜂胶");
				new1.setReplycount("100");
				new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
				new1.setImg(URL_HOST+"/news/images/21.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/news_mould2.html");
				list.add(new1);
			}
			else if(i%9==3){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("白菜");
				new1.setReplycount("100");
				new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
				new1.setImg(URL_HOST+"/news/images/51.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/news_mould5.html");
				list.add(new1);
			}
			else if(i%9==4){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("烧鸭");
				new1.setReplycount("100");
				new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
				new1.setImg(URL_HOST+"/news/images/61.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/news_mould4.html");
				list.add(new1);
			}
			else if(i%9==5){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("烧鹅");
				new1.setReplycount("100");
				new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
				new1.setImg(URL_HOST+"/news/images/zw2.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/zw_mould2.html");
				list.add(new1);
			}
			
			else if(i%9==6){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("烧鸡");
				new1.setReplycount("100");
				new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
				new1.setImg(URL_HOST+"/news/images/z1.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/zw_mould1.html");
				list.add(new1);
			}
			else if(i%9==7){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("鱼刺");
				new1.setReplycount("100");
				new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
				new1.setImg(URL_HOST+"/news/images/zw4.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/zw_mould4.html");
				list.add(new1);
			}
			else if(i%9==8){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("鲍鱼");
				new1.setReplycount("100");
				new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
				new1.setImg(URL_HOST+"/news/images/zw3.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/zw_mould3.html");
				list.add(new1);
			}
			
		}
		
		//http://xinhuaapp-img.img.aliyuncs.com/hunnan/20140714/201407141012531860234.jpg
		rsp.setArticle_list(list);
		return rsp;
	}

	@Override
	public RspResultModel getSpecialDetail(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
