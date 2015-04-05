package com.plugin.commons.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.plugin.commons.helper.FuncUtil;
import com.plugin.commons.model.BaoLiaoInfoModel;
import com.plugin.commons.model.CommentModel;
import com.plugin.commons.model.InvestigateModel;
import com.plugin.commons.model.NumberType;
import com.plugin.commons.model.PetitionModel;
import com.plugin.commons.model.PhotoAndVideoModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.SettingFqaModel;
import com.plugin.commons.model.SpecialtyModel;
import com.plugin.commons.model.SysNoticeModel;
import com.plugin.commons.model.WriterTpyeModel;


/**
 * @author zhang
 * 报料测试数据
 */
public class DisClsTestService {
	static Map<String,String> typeMap=new HashMap<String,String>();
	
	static{
		typeMap.put("举报","1");
		typeMap.put("求助","2");
		typeMap.put("咨询","3");
		typeMap.put("批评","4");
		typeMap.put("批评","4");
		typeMap.put("建议","5");
		typeMap.put("投诉","6");
	}
	public static RspResultModel getBaoliaoList(String type){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<BaoLiaoInfoModel> list = new ArrayList<BaoLiaoInfoModel>();
		for(int i=0;i<8;i++){
			BaoLiaoInfoModel bliao = new BaoLiaoInfoModel();
			bliao.setId(i+"");;
			bliao.setUserphoto("http://myeducs.cn/uploadfile/200905/10/2A133241973.png");
			bliao.setCreatetime("10分钟前");
//			bliao.setReplycount("12");
//			bliao.setViewcount("16");
//			if(i%8==0){
//				bliao.setProvider("馒头"+i);
//				bliao.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
//			}
//			else if(i%8==1){
//				bliao.setProvider("馒头"+i);
//				bliao.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
//			}
//			else if(i%8==2){
//				bliao.setProvider("馒头"+i);
//				bliao.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
//			}
//			else if(i%8==3){
//				bliao.setProvider("馒头"+i);
//				bliao.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
//			}
//			else if(i%8==4){
//				bliao.setProvider("馒头"+i);
//				bliao.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
//			}
//			else if(i%8==5){
//				bliao.setProvider("馒头"+i);
//				bliao.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
//			}
//			else if(i%8==6){
//				bliao.setProvider("馒头"+i);
//				bliao.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
//			}
//			else if(i%8==7){
//				bliao.setProvider("馒头"+i);
//				bliao.setContent("浑南新区卫生局发大水法打发打发打发打发打发大法打发大芬达范德萨法打发大撒");
//			}
			
			list.add(bliao);
			
		}
		rsp.setBaoliao_list(list);
		return rsp;
	}
	
	public static RspResultModel getBaoliaoDetail(){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		 
		List<CommentModel> comments = new ArrayList<CommentModel>();
		for(int i=0;i<6;i++){
			CommentModel cm = new CommentModel();
			cm.setContent("公司人事一般都了解"+i);
			cm.setCreatetime("2014-08-22 14:20:20");
			cm.setUserid(i+"");
			cm.setUsername("浑南用户"+i);
			cm.setUserphoto("http://myeducs.cn/uploadfile/200905/10/2A133241973.png");
			comments.add(cm);
		}
		rsp.setComments(comments);
		
		return rsp;
	}
	
	public static RspResultModel getMyComments(){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<CommentModel> comments = new ArrayList<CommentModel>();
		for(int i=0;i<6;i++){
			CommentModel cm = new CommentModel();
			cm.setOrgcontent("你什么时候去吃饭啊?");
			cm.setOrgid(i+"");
			cm.setContent("公司人事一般都了解"+i);
			cm.setCreatetime("2014-08-22 14:20:20");
			cm.setUserid(i+"");
			cm.setUsername("浑南用户"+i);
			cm.setUserphoto("http://myeducs.cn/uploadfile/200905/10/2A133241973.png");
			comments.add(cm);
		}
		rsp.setComments(comments);
		
		return rsp;
	}
	public static RspResultModel getFqaList(){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<SettingFqaModel> faqList = new ArrayList<SettingFqaModel>();
		for(int i=0;i<6;i++){
			SettingFqaModel fqa=new SettingFqaModel();
			fqa.setContent("以下是设置按钮的右边框和底边框颜色为红色，边框大小为3dp，如下图");
			faqList.add(fqa);
		}
		rsp.setFqa_list(faqList);
		return rsp;
	}
	/**
	 * 信访列表模拟数据
	 * @return
	 */
	public static RspResultModel getPetitionList(){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<PetitionModel> petList = new ArrayList<PetitionModel>();
		for(int i=0;i<16;i++){
			PetitionModel peti=new PetitionModel();
			peti.setContent("以下是设置按钮的右边框和底边框颜色为红色，边框大小为3dp，如下图");
			peti.setTitle("某某地方的公共设施被严重破坏");
			peti.setId(i+"");
			peti.setUserid("18617315452");
			peti.setUsername("张生");
			peti.setStatus(String.valueOf(i%2));
			peti.setCreatetime("2014-08-22 14:20:20");
			petList.add(peti);
			 
		}
		rsp.setPet_List(petList);
		return rsp;
	}
	
	public static RspResultModel getWriterList(String type){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<WriterTpyeModel> writerList = new ArrayList<WriterTpyeModel>();
		if("0".equals(type)){
			for(int i=0;i<2;i++){
				WriterTpyeModel writer=new WriterTpyeModel();
				writer.setId(i+"");
				writer.setType(type);
				if(i%2==0){
					writer.setTypeName("公民");	
				}else if(i%2==1){
					writer.setTypeName("企事业单位");	
				}
				writerList.add(writer);
			}
			rsp.setWriterList(writerList);
		}
		if("1".equals(type)){
			for(int i=0;i<16;i++){
				WriterTpyeModel writer=new WriterTpyeModel();
				writer.setId(i+"");
				writer.setType(type);
				writer.setTypeName("书记"+i);	
				writerList.add(writer);
			}
			rsp.setWriterList(writerList);
		}
		if("2".equals(type)){
			for(int i=0;i<6;i++){
				WriterTpyeModel writer=new WriterTpyeModel();
				writer.setId(i+"");
				writer.setType(type);
				if(i%6==0){
					writer.setTypeName("举报");
					writer.setTycode("1");
				}else if(i%6==1){
					writer.setTypeName("求助");
					writer.setTycode("2");
				}else if(i%6==2){
					writer.setTypeName("咨询");	
					writer.setTycode("3");
				}else if(i%6==3){
					writer.setTypeName("批评");
					writer.setTycode("4");
				}else if(i%6==4){ 
				    writer.setTypeName("建议");
				    writer.setTycode("5");
				}else if(i%6==5){ 
				    writer.setTypeName("投诉");	
				    writer.setTycode("6");
				}
				writerList.add(writer);
			}
			rsp.setWriterList(writerList);
		}
		if("3".equals(type)){
			for(int i=0;i<2;i++){
				WriterTpyeModel writer=new WriterTpyeModel();
				writer.setId(i+"");
				writer.setType(type);
				if(i%2==0){
					writer.setTypeName("提案");	
				}else if(i%2==1){
					writer.setTypeName("社情民意");	
				}
				writerList.add(writer);
			}
			rsp.setWriterList(writerList);
		}
		return rsp;
	}
	
	public static String getQsTypeCode(String typeName){
		
		return typeMap.get(typeName);
	}
	
	public static RspResultModel getPductList(String type){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<SpecialtyModel> specList = new ArrayList<SpecialtyModel>();
		for(int i=0;i<16;i++){
			SpecialtyModel spec=new SpecialtyModel();
			spec.setId(i+"");
			spec.setIntrduce("百合科植物是被子植物的一种，属单子叶植物类。约230属，4000多种，全球分布");
			spec.setPdname("玉竹");
			specList.add(spec);
		}
		//rsp.setSpecList(specList);
		return rsp;
	}
	
	public static RspResultModel getNotices(){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<SysNoticeModel> notice_list = new ArrayList<SysNoticeModel>();
		for(int i=0;i<6;i++){
			SysNoticeModel sysnotice=new SysNoticeModel();
			sysnotice.setContent("以下是设置按钮的右边框和底边框颜色为红色，边框大小为3dp，如下图");
			sysnotice.setId(i+"");
			sysnotice.setCreatetime(FuncUtil.formatTime(new Date(), "yyyy.MM.dd"));
			notice_list.add(sysnotice);
		}
		rsp.setNotice_list(notice_list);
		return rsp;
	}
	
	public static RspResultModel getAllInves(){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<InvestigateModel> inveslist = new ArrayList<InvestigateModel>();
		for(int i=0;i<22;i++){
			InvestigateModel inves=new InvestigateModel();
			inves.setId(i+"");
			inves.setStatus(String.valueOf(i%2));	
			inves.setTitle("七夕浪漫旅游胜地，非侏罗纪公园莫属啊"+i);
			inves.setVoteCount("18"+i+"人已投");
			inves.setType(String.valueOf(i%4));
			inveslist.add(inves);
		}
		rsp.setInveslist(inveslist);
		return rsp;
	}
	
	public static RspResultModel getNumType(){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<List<NumberType>> numlist= new ArrayList<List<NumberType>>();
		List<NumberType> numlist1 = new ArrayList<NumberType>();
		List<NumberType> numlist2 = new ArrayList<NumberType>();
		
			//addExtra(numlist);
			//addExtra2(numlist);
		NumberType type1=new NumberType();
		type1.setId(1);
		type1.setName("政府机关");
		numlist1.add(type1);
		
		NumberType type2=new NumberType();
		type2.setId(2);
		type2.setName("医院机构");
		numlist1.add(type2);
		
		NumberType type3=new NumberType();
		type3.setId(3);
		type3.setName("救援热线");
		numlist1.add(type3);
		
		NumberType type4=new NumberType();
		type4.setId(4);
		type4.setName("社会服务");
		numlist1.add(type4);
		
		NumberType type5=new NumberType();
		type5.setId(5);
		type5.setName("图书馆");
		numlist1.add(type5);
		
		NumberType type6=new NumberType();
		type6.setId(6);
		type6.setName("博物馆");
		numlist1.add(type6);
		
		NumberType type7=new NumberType();
		type7.setId(7);
		type7.setName("铁路航空");
		numlist1.add(type7);
		
		NumberType type8=new NumberType();
		type8.setId(8);
		type8.setName("银行");
		numlist1.add(type8);
		
		NumberType type9=new NumberType();
		type9.setId(9);
		type9.setName("快递物流");
		numlist2.add(type9);
		
		NumberType type10=new NumberType();
		type10.setId(10);
		type10.setName("租车代驾");
		numlist2.add(type10);
		
		NumberType type11=new NumberType();
		type11.setId(11);
		type11.setName("家电维修");
		numlist2.add(type11);
		
		NumberType type12=new NumberType();
		type12.setId(12);
		type12.setName("家政");
		numlist2.add(type12);
		
		NumberType type13=new NumberType();
		type13.setId(13);
		type13.setName("搬家");
		numlist2.add(type13);
		
		NumberType type14=new NumberType();
		type14.setId(14);
		type14.setName("外卖送餐");
		numlist2.add(type14);
		
		NumberType type15=new NumberType();
		type15.setId(15);
		type15.setName("铁路航空");
		numlist2.add(type15);
		
		NumberType type16=new NumberType();
		type16.setId(16);
		type16.setName("生活");
		numlist2.add(type16);
		
		
		numlist.add(numlist1);
		numlist.add(numlist2);		
//		rsp.setNumTypelist(numlist);;
		return rsp;
	}
	/*public static RspResultModel getNum(){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<List<NumberGroupModel>> numlist = new ArrayList<List<NumberGroupModel>>();
		
			addExtra(numlist);
			addExtra2(numlist);
		
		
		rsp.setNumGrouplist(numlist);
		return rsp;
	}

	private static void addExtra(List<List<NumberGroupModel>> numlist) {
		List<NumberGroupModel> nums=new ArrayList<NumberGroupModel>();
		NumberGroupModel senseModel_gov =new NumberGroupModel();
		List<NumberModel> numbers_gov=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("政府机关中国第一产科医院");
			numbers_gov.add(num);
		}
		senseModel_gov.setId("1");
		senseModel_gov.setName("政府机关");
		senseModel_gov.setNumList(numbers_gov);
		nums.add(senseModel_gov);
		
		NumberGroupModel senseModel_hosp =new NumberGroupModel();		
		List<NumberModel> numbers_hosp=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("政府机关中国第一产科医院");
			numbers_hosp.add(num);
		}
		senseModel_hosp.setId("2");
		senseModel_hosp.setName("医院机构");
		senseModel_hosp.setNumList(numbers_hosp);
		nums.add(senseModel_hosp);
		
		NumberGroupModel senseModel_help =new NumberGroupModel();		
		List<NumberModel> numbers_help=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("政府机关中国第一产科医院");
			numbers_hosp.add(num);
		}
		senseModel_help.setId("3");
		senseModel_help.setName("救援热线");
		senseModel_help.setNumList(numbers_help);
		nums.add(senseModel_help);
		
		
		NumberGroupModel senseModel_societyhelp =new NumberGroupModel();		
		List<NumberModel> numbers_societyhelp=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("中国第一产科医院");
			numbers_societyhelp.add(num);
		}
		senseModel_societyhelp.setId("4");
		senseModel_societyhelp.setName("社会服务");
		senseModel_societyhelp.setNumList(numbers_societyhelp);
		nums.add(senseModel_societyhelp);
		
		NumberGroupModel senseModel_libry =new NumberGroupModel();		
		List<NumberModel> numbers_libry=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("中国第一产科医院");
			numbers_libry.add(num);
		}
		senseModel_libry.setId("5");
		senseModel_libry.setName("图书馆");
		senseModel_libry.setNumList(numbers_libry);
		nums.add(senseModel_libry);
		
		NumberGroupModel senseModel_museum =new NumberGroupModel();		
		List<NumberModel> numbers_museum=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("中国第一产科医院");
			numbers_museum.add(num);
		}
		senseModel_museum.setId("6");
		senseModel_museum.setName("博物馆");
		senseModel_museum.setNumList(numbers_museum);
		nums.add(senseModel_museum);
		
		
		NumberGroupModel senseModel_traffic =new NumberGroupModel();		
		List<NumberModel> numbers_traffic=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("中国第一产科医院");
			numbers_traffic.add(num);
		}
		senseModel_traffic.setId("7");
		senseModel_traffic.setName("铁路航空");
		senseModel_traffic.setNumList(numbers_traffic);
		nums.add(senseModel_traffic);			
		
		NumberGroupModel senseModel_bank =new NumberGroupModel();		
		List<NumberModel> numbers_bank=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("中国第一产科医院");
			numbers_bank.add(num);
		}
		senseModel_bank.setId("8");
		senseModel_bank.setName("银行");
		senseModel_bank.setNumList(numbers_bank);
		nums.add(senseModel_bank);			
		
		
		numlist.add(nums);
	}

	private static void addExtra2(List<List<NumberGroupModel>> numlist) {
		List<NumberGroupModel> nums=new ArrayList<NumberGroupModel>();
		NumberGroupModel senseModel_gov =new NumberGroupModel();
		List<NumberModel> numbers_gov=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("政府机关中国第一产科医院");
			numbers_gov.add(num);
		}
		senseModel_gov.setId("9");
		senseModel_gov.setName("快递物流");
		senseModel_gov.setNumList(numbers_gov);
		nums.add(senseModel_gov);
		
		NumberGroupModel senseModel_hosp =new NumberGroupModel();		
		List<NumberModel> numbers_hosp=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("政府机关中国第一产科医院");
			numbers_hosp.add(num);
		}
		senseModel_hosp.setId("10");
		senseModel_hosp.setName("租车代驾");
		senseModel_hosp.setNumList(numbers_hosp);
		nums.add(senseModel_hosp);
		
		NumberGroupModel senseModel_help =new NumberGroupModel();		
		List<NumberModel> numbers_help=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("政府机关中国第一产科医院");
			numbers_hosp.add(num);
		}
		senseModel_help.setId("11");
		senseModel_help.setName("家电维修");
		senseModel_help.setNumList(numbers_help);
		nums.add(senseModel_help);
		
		
		NumberGroupModel senseModel_societyhelp =new NumberGroupModel();		
		List<NumberModel> numbers_societyhelp=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("中国第一产科医院");
			numbers_societyhelp.add(num);
		}
		senseModel_societyhelp.setId("12");
		senseModel_societyhelp.setName("家政");
		senseModel_societyhelp.setNumList(numbers_societyhelp);
		nums.add(senseModel_societyhelp);
		
		NumberGroupModel senseModel_libry =new NumberGroupModel();		
		List<NumberModel> numbers_libry=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("中国第一产科医院");
			numbers_libry.add(num);
		}
		senseModel_libry.setId("13");
		senseModel_libry.setName("搬家");
		senseModel_libry.setNumList(numbers_libry);
		nums.add(senseModel_libry);
		
		NumberGroupModel senseModel_museum =new NumberGroupModel();		
		List<NumberModel> numbers_museum=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("中国第一产科医院");
			numbers_museum.add(num);
		}
		senseModel_museum.setId("14");
		senseModel_museum.setName("外卖送餐");
		senseModel_museum.setNumList(numbers_museum);
		nums.add(senseModel_museum);
		
		
		NumberGroupModel senseModel_traffic =new NumberGroupModel();		
		List<NumberModel> numbers_traffic=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("中国第一产科医院");
			numbers_traffic.add(num);
		}
		senseModel_traffic.setId("15");
		senseModel_traffic.setName("铁路航空");
		senseModel_traffic.setNumList(numbers_traffic);
		nums.add(senseModel_traffic);			
		
		NumberGroupModel senseModel_bank =new NumberGroupModel();		
		List<NumberModel> numbers_bank=new ArrayList<NumberModel>();
		for(int j=0;j<6;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("中国第一产科医院");
			numbers_bank.add(num);
		}
		senseModel_bank.setId("16");
		senseModel_bank.setName("生活");
		senseModel_bank.setNumList(numbers_bank);
		nums.add(senseModel_bank);			
		
		
		numlist.add(nums);
	}
*/
	/**
	 * 拍客播客
	 * @return
	 */
	public static RspResultModel getPkBk(boolean isVideo){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		String type="0";
		if(isVideo){
			type="2";
		}
		List<List<PhotoAndVideoModel>> paklist = new ArrayList<List<PhotoAndVideoModel>>();
		for(int i=0;i<6;i++){
			List<PhotoAndVideoModel> pks=new ArrayList<PhotoAndVideoModel>();
			PhotoAndVideoModel pk=new PhotoAndVideoModel();
			pk.setTitle("去东莞嫖吧"+i);
			pks.add(pk);
			pk=new PhotoAndVideoModel();
			pk.setTitle("去那里威啊"+i);
			pks.add(pk);
			paklist.add(pks);
		}
		rsp.setPkbklist(paklist);
		return rsp;
	}
	
	
	public static RspResultModel getComPhotos(boolean isVideo){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		 
		List<PhotoAndVideoModel> comphotos=new ArrayList<PhotoAndVideoModel>();
		for(int i=0;i<6;i++){
			PhotoAndVideoModel pk=new PhotoAndVideoModel();
			pk.setTitle("去东莞嫖吧"+i);
			 
			comphotos.add(pk);
		}
		rsp.setComphotos(comphotos);
		return rsp;
	}

/*	public static RspResultModel getNumList(String numid) {
		RspResultModel rsp = new RspResultModel();
		List<NumberModel> nums=new ArrayList<NumberModel>();
		for(int j=0;j<8;j++){
			NumberModel num=new NumberModel();
			num.setId(j+"");
			num.setTelNum("13823423532"+j);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("中国第一产科医院");
			nums.add(num);
		}
		rsp.setNumlist(nums);
		return rsp;
	}*/

	/*public static RspResultModel getNum(String numberSenseId) {
	
		RspResultModel rsp = DisClsTestService.getNum();
		List<List<NumberGroupModel>> numlist =rsp.getNumGrouplist();
		for(int i=0;i<numlist.size();i++){
			List<NumberGroupModel> numgroups=numlist.get(i);
			for(int j=0;j<numgroups.size();j++){
				NumberGroupModel numbergroup=numgroups.get(j);
				String numberSense=numbergroup.getId();
				if(numberSenseId.equals(numberSense)){
					List<NumberModel> newNum=numbergroup.getNumList();
					rsp.setNumlist(newNum);
				}
				
			}
		}		
		return rsp;
	}*/
}
