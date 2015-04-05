package com.plugin.commons.model;

import java.io.Serializable;
import java.util.List;

import com.zq.types.Group;
import com.zq.types.StBaseType;

public class RspResultModel implements StBaseType ,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7821862584209824258L;
	private String retcode;
	private String retmsg;
	private UserInfoModel user;
	private Group<NewsInfoModel> article_list;
	private Group<NewsInfoModel> headnew_list;
	private List<AskMsgModel> msg_list;
	private String photo;
	private List<GovmentInfoModel> org_list;
	private AskMsgModel msg;
	private BaoLiaoInfoModel blmsg;
	private List<CommentModel>comments;
	private List<CommentModel>comment_list;
	private List<BaoLiaoInfoModel> baoliao_list;
	private List<NewPicItemModel> newPicList;
	private List<NewsTypeModel> attype_list;
	private List<SettingFqaModel> fqa_list;
	private List<PetitionModel> pet_List;
	private List<WriterTpyeModel> writerList;
	private List<SpecialtyModel> specList;
	private List<SysNoticeModel> notice_list;
	private GovmentInfoModel org;
	MyCollectInfoModel colts;//我的所有收藏信息
	private List<List<String>> content;
	private String loadflag;
	private List<List<NewsInfoModel>>home_page_list;
	private AppInfoModel appInfo;
	private List<Reply> myreply_list;//我评论的新闻
	private List<InvestigateModel> inveslist;
	private List<List<PhotoAndVideoModel>> pkbklist;
	
	private SWAreaModel c;//天气区域信息
	private SWFrontcastModel f;//天气预报信息
	private SWObserveModel l;//天气实况信息
	private List<SWIndexModel> i;//天气指数
	private XinHuaModel xhModel;
	
	private List<PhotoAndVideoModel> comphotos;
	private List<AreaModel> area_list;//地区列表
	private List<AppInfoModel> app_list;//地区列表
	/**
	 * 瞬间和new radio
	 */
	private RadioVideoModel shunJRadio;
	
	/**
	 * 号码通返回的数据集合
	 */
	List<NumberType> numbertypes;
	public List<NumberModel> getNumbers() {
		return numbers;
	}
	public void setNumbers(List<NumberModel> numbers) {
		this.numbers = numbers;
	}
	List<NumberModel> numbers;
	
	/**
	 * new radio 和瞬间 
	 */
	private List<RadioVideoModel> radioVideos;
	
	public List<Reply> getMyreply_list() {
		return myreply_list;
	}
	public void setMyreply_list(List<Reply> myreply_list) {
		this.myreply_list = myreply_list;
	}
	public AppInfoModel getAppInfo() {
		return appInfo;
	}
	public void setAppInfo(AppInfoModel appInfo) {
		this.appInfo = appInfo;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Group<NewsInfoModel> getArticle_list() {
		return article_list;
	}
	public void setArticle_list(Group<NewsInfoModel> article_list) {
		this.article_list = article_list;
	}
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getRetmsg() {
		return retmsg;
	}
	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}
	public UserInfoModel getUser() {
		return user;
	}
	public void setUser(UserInfoModel user) {
		this.user = user;
	}
	public List<AskMsgModel> getMsg_list() {
		return msg_list;
	}
	public void setMsg_list(List<AskMsgModel> msg_list) {
		this.msg_list = msg_list;
	}
	public List<GovmentInfoModel> getOrg_list() {
		return org_list;
	}
	public void setOrg_list(List<GovmentInfoModel> org_list) {
		this.org_list = org_list;
	}
	public AskMsgModel getMsg() {
		return msg;
	}
	public void setMsg(AskMsgModel msg) {
		this.msg = msg;
	}
	public List<CommentModel> getComments() {
		return comments;
	}
	public void setComments(List<CommentModel> comments) {
		this.comments = comments;
	}
	 
	public List<BaoLiaoInfoModel> getBaoliao_list() {
		return baoliao_list;
	}
	public void setBaoliao_list(List<BaoLiaoInfoModel> baoliao_list) {
		this.baoliao_list = baoliao_list;
	}
	public BaoLiaoInfoModel getBlmsg() {
		return blmsg;
	}
	public void setBlmsg(BaoLiaoInfoModel blmsg) {
		this.blmsg = blmsg;
	}
	public List<NewPicItemModel> getNewPicList() {
		return newPicList;
	}
	public void setNewPicList(List<NewPicItemModel> newPicList) {
		this.newPicList = newPicList;
	}
	public GovmentInfoModel getOrg() {
		return org;
	}
	public void setOrg(GovmentInfoModel org) {
		this.org = org;
	}
	public List<NewsTypeModel> getAttype_list() {
		return attype_list;
	}
	public void setAttype_list(List<NewsTypeModel> attype_list) {
		this.attype_list = attype_list;
	}
	public List<CommentModel> getComment_list() {
		return comment_list;
	}
	public void setComment_list(List<CommentModel> comment_list) {
		this.comment_list = comment_list;
	}
	public MyCollectInfoModel getColts() {
		return colts;
	}
	public void setColts(MyCollectInfoModel colts) {
		this.colts = colts;
	}
	public List<SettingFqaModel> getFqa_list() {
		return fqa_list;
	}
	public void setFqa_list(List<SettingFqaModel> fqa_list) {
		this.fqa_list = fqa_list;
	}
	public List<PetitionModel> getPet_List() {
		return pet_List;
	}
	public void setPet_List(List<PetitionModel> pet_List) {
		this.pet_List = pet_List;
	}
	public List<WriterTpyeModel> getWriterList() {
		return writerList;
	}
	public void setWriterList(List<WriterTpyeModel> writerList) {
		this.writerList = writerList;
	}
	public Group<NewsInfoModel> getHeadnew_list() {
		return headnew_list;
	}
	public void setHeadnew_list(Group<NewsInfoModel> headnew_list) {
		this.headnew_list = headnew_list;
	}
	public String getLoadflag() {
		return loadflag;
	}
	public void setLoadflag(String loadflag) {
		this.loadflag = loadflag;
	}
	public List<List<NewsInfoModel>> getHome_page_list() {
		return home_page_list;
	}
	public void setHome_page_list(List<List<NewsInfoModel>> home_page_list) {
		this.home_page_list = home_page_list;
	}
	public List<List<String>> getContent() {
		return content;
	}
	public void setContent(List<List<String>> content) {
		this.content = content;
	}
	public List<SpecialtyModel> getSpecList() {
		return specList;
	}
	public void setSpecList(List<SpecialtyModel> specList) {
		this.specList = specList;
	}
	public List<SysNoticeModel> getNotice_list() {
		return notice_list;
	}
	public void setNotice_list(List<SysNoticeModel> notice_list) {
		this.notice_list = notice_list;
	}
	public SWAreaModel getC() {
		return c;
	}
	public void setC(SWAreaModel c) {
		this.c = c;
	}
	public SWFrontcastModel getF() {
		return f;
	}
	public void setF(SWFrontcastModel f) {
		this.f = f;
	}
	public SWObserveModel getL() {
		return l;
	}
	public void setL(SWObserveModel l) {
		this.l = l;
	}
	public List<SWIndexModel> getI() {
		return i;
	}
	public void setI(List<SWIndexModel> i) {
		this.i = i;
	}
	public List<InvestigateModel> getInveslist() {
		return inveslist;
	}
	public void setInveslist(List<InvestigateModel> inveslist) {
		this.inveslist = inveslist;
	}
	
	
	public XinHuaModel getXhModel() {
		return xhModel;
	}
	public void setXhModel(XinHuaModel xhModel) {
		this.xhModel = xhModel;
	}
	public List<List<PhotoAndVideoModel>> getPkbklist() {
		return pkbklist;
	}
	public void setPkbklist(List<List<PhotoAndVideoModel>> pkbklist) {
		this.pkbklist = pkbklist;
	}
	public List<PhotoAndVideoModel> getComphotos() {
		return comphotos;
	}
	public void setComphotos(List<PhotoAndVideoModel> comphotos) {
		this.comphotos = comphotos;
	}	
	public List<RadioVideoModel> getRadioVideos() {
		return radioVideos;
	}
	public void setRadioVideos(List<RadioVideoModel> radioVideos) {
		this.radioVideos = radioVideos;
	}
	public RadioVideoModel getShunJRadio() {
		return shunJRadio;
	}
	public void setShunJRadio(RadioVideoModel shunJRadio) {
		this.shunJRadio = shunJRadio;
	}
	public List<NumberType> getNumbertypes() {
		return numbertypes;
	}
	public void setNumbertypes(List<NumberType> numbertypes) {
		this.numbertypes = numbertypes;
	}
	public List<AreaModel> getArea_list() {
		return area_list;
	}
	public void setArea_list(List<AreaModel> area_list) {
		this.area_list = area_list;
	}
	public List<AppInfoModel> getApp_list() {
		return app_list;
	}
	public void setApp_list(List<AppInfoModel> app_list) {
		this.app_list = app_list;
	}

}
