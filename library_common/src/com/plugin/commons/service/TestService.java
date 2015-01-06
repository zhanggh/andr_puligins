package com.plugin.commons.service;

import java.util.ArrayList;
import java.util.List;

import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.model.CacheModel;
import com.plugin.commons.model.CommentModel;
import com.plugin.commons.model.GovmentInfoModel;
import com.plugin.commons.model.NewPicItemModel;
import com.plugin.commons.model.NewsInfoModel;
import com.plugin.commons.model.RspResultModel;
import com.zq.types.Group;

public class TestService {
	//public static final String URL_HOST = "http://10.47.0.175:8080/lndz/static";
	public static String URL_HOST = "http://113.108.182.3:17580/lndz/static";
	public static RspResultModel getNewList(String type){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		Group<NewsInfoModel> list = new Group<NewsInfoModel>();
		for(int i=0;i<13;i++){
			if(i%3==0){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("国家大学科技城获2014年市科技计划立项支持");
				new1.setReplycount("100");
				new1.setDescition("造就出一座生态依照自然之法、发展遵循绿色之道的山水名城");
				new1.setContent("当今世界，在晋级现代化名城的实力比拼中，生态已经日益成为重要的砝码；造就出一座生态依照自然之法、发展遵循绿色之道的山水名城，已成为牵动众多新城的终极梦想");
				new1.setImg(URL_HOST+"/news/images/2014073109075039418.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/news_mould1.html");
				list.add(new1);
			}
			if(i%3==1){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("沈阳音乐学院违规招生 近百考生成牺牲品");
				new1.setReplycount("100");
				new1.setDescition("造就出一座生态依照自然之法、发展遵循绿色之道的山水名城");
				new1.setContent("当今世界，在晋级现代化名城的实力比拼中，生态已经日益成为重要的砝码；造就出一座生态依照自然之法、发展遵循绿色之道的山水名城，已成为牵动众多新城的终极梦想");
				new1.setImg(URL_HOST+"/news/images/21.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/news_mould2.html");
				list.add(new1);
			}
			if(i%3==2){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("浑南\"挂牌\" 全面开启后全运时代");
				new1.setReplycount("100");
				new1.setDescition("造就出一座生态依照自然之法、发展遵循绿色之道的山水名城");
				new1.setContent("当今世界，在晋级现代化名城的实力比拼中，生态已经日益成为重要的砝码；造就出一座生态依照自然之法、发展遵循绿色之道的山水名城，已成为牵动众多新城的终极梦想");
				new1.setImg(URL_HOST+"/news/images/31.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/news_mould3.html");
				list.add(new1);
			}
			if(i%3==3){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("浑南区开展\"庆八一\"流动图书站进军营活动");
				new1.setReplycount("100");
				new1.setDescition("造就出一座生态依照自然之法、发展遵循绿色之道的山水名城");
				new1.setContent("当今世界，在晋级现代化名城的实力比拼中，生态已经日益成为重要的砝码；造就出一座生态依照自然之法、发展遵循绿色之道的山水名城，已成为牵动众多新城的终极梦想");
				new1.setImg(URL_HOST+"/news/images/61.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/news_mould4.html");
				list.add(new1);
			}
			if(i%3==4){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("\"幸福浑南\"送文化进社区活动拉开帷幕");
				new1.setReplycount("100");
				new1.setDescition("造就出一座生态依照自然之法、发展遵循绿色之道的山水名城");
				new1.setContent("当今世界，在晋级现代化名城的实力比拼中，生态已经日益成为重要的砝码；造就出一座生态依照自然之法、发展遵循绿色之道的山水名城，已成为牵动众多新城的终极梦想");
				new1.setImg(URL_HOST+"/news/images/51.jpg");
				new1.setLocation("本地");
				new1.setUrl(URL_HOST+"/news/news_mould5.html");
				list.add(new1);
			}
			
		}
		rsp.setArticle_list(list);
		return rsp;
	}
	
	public static RspResultModel getHomeList(String type){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		Group<NewsInfoModel> list = new Group<NewsInfoModel>();
		for(int i=0;i<12;i++){
			if(i%9==0){
				NewsInfoModel new1 = new NewsInfoModel();
				new1.setId(i+"");
				new1.setTitle("浑南\"挂牌\" 全面开启后全运时代");
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
				new1.setTitle("国家大学科技城获2014年市科技计划立项支持");
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
				new1.setTitle("沈阳音乐学院违规招生 近百考生成牺牲品");
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
				new1.setTitle("\"幸福浑南\"送文化进社区活动拉开帷幕");
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
				new1.setTitle("浑南区开展\"庆八一\"流动图书站进军营活动");
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
				new1.setTitle("党的群众路线教育实践活动");
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
				new1.setTitle("辽宁利用\"5•15\"政务公开活动日扩大地理国情普查宣传");
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
				new1.setTitle("沈阳市东陵区、浑南新区、航高基地三区合署办公四周年");
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
				new1.setTitle("体制改革 成就全国活力最强的新型城区");
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
	
	//1--热门 2--动态
	public static RspResultModel getAskList(String type){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<AskMsgModel> list = new ArrayList<AskMsgModel>();
		for(int i=0;i<12;i++){
			AskMsgModel ask = new AskMsgModel();
			ask.setOrgid(i+"");
			ask.setOrgname("机构"+i);
			ask.setUserid(i+"");
			ask.setUsername("浑南用户"+i);
			ask.setReplycount(i+1+"");
			ask.setContent("请问公司办理境外就业手续用户卡，需要提供那些材料？谢谢");
			ask.setCreatetime("10分钟前");
			ask.setId(i+"");;
			ask.setUserphoto("http://myeducs.cn/uploadfile/200905/10/2A133241973.png");
			ask.setOrgphoto("http://myeducs.cn/uploadfile/200905/10/2A133241973.png");
			ask.setViewcount(10+i+"");
			list.add(ask);
		}
		rsp.setMsg_list(list);
		return rsp;
	}
	
	public static RspResultModel getGovment(){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<AskMsgModel> list = new ArrayList<AskMsgModel>();
		for(int i=0;i<12;i++){
			AskMsgModel ask = new AskMsgModel();
			ask.setOrgid(i+"");
			ask.setOrgname("机构"+i);
			ask.setUserid(i+"");
			ask.setUsername("浑南用户"+i);
			ask.setReplycount(i+1+"");
			ask.setContent("请问公司办理境外就业手续用户卡，需要提供那些材料？谢谢");
			ask.setCreatetime("10分钟前");
			ask.setId(i+"");;
			ask.setUserphoto("http://myeducs.cn/uploadfile/200905/10/2A133241973.png");
			ask.setOrgphoto("http://myeducs.cn/uploadfile/200905/10/2A133241973.png");
			ask.setViewcount(10+i+"");
			//ask.setUrl(URL_HOST+"/news/zw_mould1.html");
			list.add(ask);
			
		}
		rsp.setMsg_list(list);
		return rsp;
	}
	
	public static RspResultModel getGovAskDetail(AskMsgModel gov,boolean isAns){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		if(isAns){
			gov.setPhoto("http://photocdn.sohu.com/20120201/Img333385456.jpg");
			gov.setRecontent("1、办证人员与聘用单位签订的聘用合同原件及复印件(一式一份)； \n2、企业法人营业执照副本或事业单位法人证书、民办非企业单位登记证书、社会团体登记证书、外国(地区)、外埠在京设立的非法人分支机构营业执照等原件及复印件(一式一份)； ");
			gov.setReplytime("2014-08-22 14:20:20");
			gov.setReplycount(6+"");
		}
		rsp.setMsg(gov);
		List<CommentModel> comments = new ArrayList<CommentModel>();
		for(int i=0;i<6;i++){
			CommentModel cm = new CommentModel();
			cm.setContent("公司人事一般都了解"+i);
			cm.setCreatetime("10分钟前");
			cm.setUserid(i+"");
			cm.setUsername("浑南用户"+i);
			cm.setUserphoto("http://myeducs.cn/uploadfile/200905/10/2A133241973.png");
			comments.add(cm);
		}
		rsp.setComments(comments);
		
		return rsp;
	}
	
	public static RspResultModel getGovmentList(){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<GovmentInfoModel> list = new ArrayList<GovmentInfoModel>();
		for(int i=0;i<8;i++){
			GovmentInfoModel ask = new GovmentInfoModel();
			ask.setId(i+"");;
			ask.setPhoto("http://myeducs.cn/uploadfile/200905/10/2A133241973.png");
			ask.setUrl(URL_HOST+"/news/zw_mould1.html");
			if(i%8==0){
				ask.setName("卫生局");
				ask.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
			}
			else if(i%8==1){
				ask.setName("交警大队");
				ask.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
			}
			else if(i%8==2){
				ask.setName("行政审批中心");
				ask.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
			}
			else if(i%8==3){
				ask.setName("公安分局");
				ask.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
			}
			else if(i%8==4){
				ask.setName("计划生育办公室");
				ask.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
			}
			else if(i%8==5){
				ask.setName("工商局");
				ask.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
			}
			else if(i%8==6){
				ask.setName("出入境办证大厅");
				ask.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
			}
			else if(i%8==7){
				ask.setName("国土规划局");
				ask.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
			}
			
			list.add(ask);
			
		}
		rsp.setOrg_list(list);
		return rsp;
	}
	
	//1--热门 2--动态
		public static RspResultModel getMyAskAttionList(){
			RspResultModel rsp = new RspResultModel();
			rsp.setRetcode("0");
			rsp.setRetmsg("查询成功");
			List<AskMsgModel> list = new ArrayList<AskMsgModel>();
			Group group = CacheDataService.getAcctionList(CacheModel.CACHE_ASKGOV);
			if(group!=null&&group.size()>0){
				for(int i=0;i<group.size();i++){
					CacheModel cm = (CacheModel)group.get(i);
					list.add((AskMsgModel)cm.msg);
				}
			}
			rsp.setMsg_list(list);
			return rsp;
		}
		
		public static RspResultModel getNewPicList(String newsId){
			RspResultModel rsp = new RspResultModel();
			rsp.setRetcode("0");
			rsp.setRetmsg("查询成功");
			List<NewPicItemModel> list = new ArrayList<NewPicItemModel>();
			for(int i=0;i<9;i++){
				NewPicItemModel new1 = new NewPicItemModel();
				new1.setId(i+"");
				new1.setDescition("");
				new1.setImg("");
				if(i%9==0){
					new1.setDescition("露营嘉年华火爆来袭");
					new1.setImg(URL_HOST+"/news/images/31.jpg");
				}
				else if(i%9==1){
					new1.setDescition("淘宝大学电商精英‘辽宁易玛’培训中心在沈阳浑南国家电子商务示范基地正式开班");
					new1.setImg(URL_HOST+"/news/images/2014073109075039418.jpg");
				}
				else if(i%9==2){
					new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
					new1.setImg(URL_HOST+"/news/images/21.jpg");
				}
				else if(i%9==3){
					new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
					new1.setImg(URL_HOST+"/news/images/51.jpg");
				}
				else if(i%9==4){
					new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
					new1.setImg(URL_HOST+"/news/images/61.jpg");
				}
				else if(i%9==5){
					new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
					new1.setImg(URL_HOST+"/news/images/zw2.jpg");
				}
				
				else if(i%9==6){
					new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
					new1.setImg(URL_HOST+"/news/images/z1.jpg");
				}
				else if(i%9==7){
					new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
					new1.setImg(URL_HOST+"/news/images/zw4.jpg");
				}
				else if(i%9==8){
					new1.setDescition("7月5日，风靡全球的“The Color Run”首次来到浑南新区");
					new1.setImg(URL_HOST+"/news/images/zw3.jpg");
				}
				
				list.add(new1);
			}
			rsp.setNewPicList(list);
			return rsp;
		}
		
		 
}
