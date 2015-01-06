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
import com.plugin.commons.model.NumberModel;
import com.plugin.commons.model.PetitionModel;
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
	
	public static RspResultModel getNum(){
		RspResultModel rsp = new RspResultModel();
		rsp.setRetcode("0");
		rsp.setRetmsg("查询成功");
		List<NumberModel> numlist = new ArrayList<NumberModel>();
		for(int i=0;i<6;i++){
			NumberModel num=new NumberModel();
			num.setId(i+"");
			num.setTelNum("13823423532"+i);
			num.setAddress("广东省广州市天河区体育西107号3楼");
			num.setUsername("中国第一产科医院");
			numlist.add(num);
		}
		rsp.setNumlist(numlist);
		return rsp;
	}

}
