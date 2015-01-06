package com.plugin.commons.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.plugin.commons.ComApp;
import com.plugin.commons.CoreContants;
import com.plugin.commons.helper.DingLog;
import com.plugin.commons.helper.SmartWeatherUrlUtil;
import com.plugin.commons.model.AskMsgModel;
import com.plugin.commons.model.CommentModel;
import com.plugin.commons.model.RspResultModel;
import com.plugin.commons.model.UserInfoModel;
import com.plugin.commons.model.XinHuaModel;


public class ComAppApi {
	static DingLog log = new DingLog(ComAppApi.class);
//	public static final String BASE_URL = "http://121.8.157.114:17580/lndz/server/";
	public static final String BASE_URL = "http://221.180.149.181/lndz/server/";
	

	/**
	 * app启动对接
	 */
	public static final String XINHUA_STARTUP_URL = "http://xhpfm.open.zhongguowangshi.com/open/startad";
	
	//api地址
	public static final String URL_REGISTER = "user/register";
	public static final String URL_LOGIN = "user/login";
	public static final String URL_LOGIN_SMS = "common/smscode";
	public static final String URL_HOME_PAGE= "art/home_page_list";
	public static final String URL_MODIFY_PWD= "user/modify_pwd";
	public static final String URL_MODIFY_USERINFO= "user/modifyinfo";
	public static final String URL_MODIFY_UPLOADUSERPHOTO= "user/uploadphoto";
	public static final String URL_PWD_RESET= "user/reset_pwd";
	public static final String URL_ORG_LIST= "org/list";
	public static final String URL_ORG_DETAIL= "org/detail";
	public static final String URL_ORG_POLITICS_ADD= "politics/add";
	public static final String URL_ORG_POLITICS_LIST= "politics/list";
	public static final String URL_ORG_POLITICS_DETAIL= "politics/detail";
	public static final String URL_ORG_POLITICS_COMMENT= "politics/comment";
	public static final String URL_ORG_POLITICS_MYLIST= "politics/mylist";
	public static final String URL_ORG_POLITICS_MYREPLY= "politics/myreply";
	public static final String URL_ORG_POLITICS_ORG= "politics/org";
	public static final String URL_NETF_BAOLIAO= "reveal/list";
	public static final String URL_MY_BAOLIAO= "reveal/mylist";
	public static final String URL_MYREPLY_BAOLIAO= "reveal/myreply";
	public static final String URL_BAOLIAO_DETAIL= "reveal/detail";
	public static final String URL_BAOLIAO_PUB= "reveal/add";
	public static final String URL_BAOLIAO_COM= "reveal/comment";
	public static final String URL_ART_ARTICLE_LIST= "art/article_list";
	public static final String URL_ART_ARTICLE_VIDEOLIST= "art/vidio_list";
	public static final String URL_NEWS_CM_PUB= "art/reply";
	public static final String URL_NEWS_CM_LIST= "comment/list";
	public static final String URL_ATTYPE_LIST= "art/attype_list";
	public static final String URL_ART_SUBARTICLE_LIST= "art/sub_article_list";
	public static final String URL_ART_PIC_DETAIL= "art/pic_detail";
	public static final String URL_SYS_FEEDBACK= "common/feedback";
	public static final String URL_SYS_APPUPDATE= "appupdate";
	public static final String URL_MY_MYREPLY= "comment/myreply";
	public static final String URL_NEWS_PIC_LIST= "art/pic_list";
	public static final String URL_NEWS_PIC_DETAIL= "art/pic_detail";
	public static final String URL_ART_VIEW= "art/view";//访问统计推送
	public static String sysid="1";
	
	protected static AjaxParams buildBaseParam(){
		UserInfoModel user = ComApp.getInstance().getLoginInfo();
		AjaxParams params = new AjaxParams();
		params.put("user_id", user==null?"":user.getPhone());
		params.put("version", CoreContants.INF_VERSION_CODE);
		params.put("sessionid",user==null?"":user.getSessionid());
		params.put("sign", "");
		params.put("systype","0");
		params.put("sysid",sysid);
		return params;
	}
	
	public RspResultModel register(String phone,String password,String username,String smscode){
		AjaxParams params = buildBaseParam();
		params.put("phone",phone);
		params.put("password",password);
		params.put("username",username);
		params.put("smscode",smscode);
		return doRequest(params,URL_REGISTER);
	}
	
	public RspResultModel login(String phone,String password){
		AjaxParams params = buildBaseParam();
		params.put("phone",phone);
		params.put("password",password);
		return doRequest(params,URL_LOGIN);
	}
	
	public RspResultModel loginSmsCode(String phone,String smstype){
		AjaxParams params = buildBaseParam();
		params.put("phone",phone);
		params.put("smstype",smstype);
		
		return doRequest(params,URL_LOGIN_SMS);
	}
	
	public RspResultModel homePage(String loadflag){
		AjaxParams params = buildBaseParam();
		params.put("loadflag",loadflag);
		return doRequest(params,URL_HOME_PAGE);
	}
	
	public RspResultModel modify_pwd(String phone,String password,String newpassword){
		AjaxParams params = buildBaseParam();
		params.put("phone",phone);
		params.put("password",password);
		params.put("newpassword",newpassword);
		return doRequest(params,URL_MODIFY_PWD);
	}
	
	public RspResultModel modifyinfo(String username,String email){
		AjaxParams params = buildBaseParam();
		params.put("username",username);
		params.put("email",email);
		return doRequest(params,URL_MODIFY_USERINFO);
	}
	
	public RspResultModel uploadUserphoto(String filename,InputStream imageIS){
		AjaxParams params = buildBaseParam();
		params.put("filename",filename);
		params.put("file",imageIS);
		return doRequest(params,URL_MODIFY_UPLOADUSERPHOTO);
	}
	
	protected RspResultModel doRequest(AjaxParams params,String url){
		RspResultModel rsp = null;
		try{
			log.info("url:"+getUrl(url)+"\n请求参数:"+params.getParamString());
			Object obj = ComApp
					.getInstance()
					.getFinalHttp()
					.postSync(
							getUrl(url) ,params);
			String retString = (String)obj;
			log.info("返回:"+retString);
			
			rsp = (RspResultModel) JsonParse
					.static_consume(retString);
		}
		catch(Exception e){
			e.printStackTrace();
			log.info("请求失败:"+e.getMessage());
			rsp = new RspResultModel();
			rsp.setRetcode(CoreContants.RETCODE_ERR);
			rsp.setRetmsg("网络不给力哦~");
		}
		return rsp;
	}
	
	protected static String getUrl(String url){
		return BASE_URL+url;
	}

	/**
	 * 找回密码
	 * @param phone
	 * @param smscode
	 * @return
	 */
	public RspResultModel findpwd(String phone, String smscode, String password) {
		AjaxParams params = buildBaseParam();
		params.put("phone",phone);
		params.put("password",password);
		params.put("smscode",smscode);
		return doRequest(params,URL_PWD_RESET);
	}
	
	/**
	 * 机构列表
	 * @param phone
	 * @param smscode
	 * @return
	 */
	public RspResultModel orgList() {
		AjaxParams params = buildBaseParam();
		return doRequest(params,URL_ORG_LIST);
	}
	
	/**
	 * 机构列表
	 * @param phone
	 * @param smscode
	 * @return
	 */
	public RspResultModel orgDetail(String orgId) {
		AjaxParams params = buildBaseParam();
		params.put("id",orgId);
		return doRequest(params,URL_ORG_DETAIL);
	}
	
	
	public RspResultModel politicsAdd(String orgid, String content, String picname1,InputStream pic1,
			String picname2,InputStream pic2,String picname3,InputStream pic3,String audioname,
			InputStream audio,
			String vidioname,
			InputStream vidio) {
		AjaxParams params = buildBaseParam();
		params.put("orgid",orgid);
		params.put("content",content);
		params.put("picname1",picname1);
		params.put("pic1",pic1);
		params.put("picname2",picname2);
		params.put("pic2",pic2);
		params.put("picname3",picname3);
		params.put("pic3",pic3);
		params.put("audioname",audioname);
		params.put("audio",audio);
		params.put("vidioname",vidioname);
		params.put("vidio",vidio);
		return doRequest(params,URL_ORG_POLITICS_ADD);
	}
	public RspResultModel politicsAddExt(AskMsgModel mGov, String content, String picname1,InputStream pic1,
			String picname2,InputStream pic2,String picname3,InputStream pic3,String audioname,
			InputStream audio,
			String vidioname,
			InputStream vidio) {
		AjaxParams params = buildBaseParam();
		params.put("orgid",mGov.getOrgid());
		params.put("content",content);
		params.put("picname1",picname1);
		params.put("pic1",pic1);
		params.put("picname2",picname2);
		params.put("pic2",pic2);
		params.put("picname3",picname3);
		params.put("pic3",pic3);
		params.put("audioname",audioname);
		params.put("audio",audio);
		params.put("vidioname",vidioname);
		params.put("vidio",vidio);
		params.put("usertype",mGov.getUsertype());
		params.put("username",mGov.getUsername());
		params.put("idcard",mGov.getIdcard());
		params.put("phone",mGov.getPhone());
		params.put("address",mGov.getAddress());
		params.put("email",mGov.getEmail());
		params.put("msgtype",mGov.getMsgtype());
		return doRequest(params,URL_ORG_POLITICS_ADD);
	}
	
	public RspResultModel politicsList(String start,String size,String hot) {
		AjaxParams params = buildBaseParam();
		params.put("start",start);
		params.put("size",size);
		params.put("hot",hot);
		return doRequest(params,URL_ORG_POLITICS_LIST);
	}
	
	/**
	 * 详情
	 * @param phone
	 * @param smscode
	 * @return
	 */
	public RspResultModel politicsDetail(String askId) {
		AjaxParams params = buildBaseParam();
		params.put("id",askId);
		return doRequest(params,URL_ORG_POLITICS_DETAIL);
	}
	
	/**
	 * 评论
	 * @param phone
	 * @param smscode
	 * @return
	 */
	public RspResultModel politicsComment(String askId,String content) {
		AjaxParams params = buildBaseParam();
		params.put("id",askId);
		params.put("content",content);
		return doRequest(params,URL_ORG_POLITICS_COMMENT);
	}
	
	public RspResultModel politicsMyAsk(String start,String size) {
		AjaxParams params = buildBaseParam();
		params.put("start",start);
		params.put("size",size);
		return doRequest(params,URL_ORG_POLITICS_MYLIST);
	}
	
	public RspResultModel politicsMyComment(String start,String size) {
		AjaxParams params = buildBaseParam();
		params.put("start",start);
		params.put("size",size);
		return doRequest(params,URL_ORG_POLITICS_MYREPLY);
	}
	
	public RspResultModel politicsOrgAnswer(String start,String size,String orgid) {
		AjaxParams params = buildBaseParam();
		params.put("start",start);
		params.put("size",size);
		params.put("orgid", orgid);
		return doRequest(params,URL_ORG_POLITICS_ORG);
	}
	
	/**
	 * 获取网友爆料列表
	 * @param start
	 * @param size
	 * @return
	 */
	public RspResultModel getNetfBliaoList(String start, String size) {
		AjaxParams params = buildBaseParam();
		params.put("start",start);
		params.put("size",size);
		return doRequest(params,URL_NETF_BAOLIAO);
	}

	/**
	 * 爆料详情
	 * @param id
	 * @return
	 */
	public RspResultModel getBaoliaoDetail(String id) {
		AjaxParams params = buildBaseParam();
		params.put("id",id);
		return doRequest(params,URL_BAOLIAO_DETAIL);
	}

	/**
	 * 我的爆料列表
	 * @param sessionid
	 * @param start
	 * @param size
	 * @return
	 */
	public RspResultModel getMyBaoliaoList(String sessionid, String start,
			String size) {
		AjaxParams params = buildBaseParam();
		params.put("sessionid",sessionid);
		params.put("start",start);
		params.put("size",size);
		return doRequest(params,URL_MY_BAOLIAO);
	}
	public RspResultModel getMyReplyBaoliaoList(String start,
			String size) {
		AjaxParams params = buildBaseParam();
		params.put("start",start);
		params.put("size",size);
		return doRequest(params,URL_MYREPLY_BAOLIAO);
	}

	/**
	 * 发布爆料
	 * @param blmodel
	 * @return
	 */
	public RspResultModel pubBaoliaoInfo(String content, String picname1,InputStream pic1,
			String picname2,InputStream pic2,String picname3,InputStream pic3,String audioname,
			InputStream audio,
			String vidioname,
			InputStream vidio) {
		AjaxParams params = buildBaseParam();
		 
		params.put("content",content);
		params.put("picname1",picname1);
		params.put("pic1",pic1);
		params.put("picname2",picname2);
		params.put("pic2",pic2);
		params.put("picname3",picname3);
		params.put("pic3",pic3);
		params.put("audioname",audioname);
		params.put("audio",audio);
		params.put("vidioname",vidioname);
		params.put("vidio",vidio);
		return doRequest(params,URL_BAOLIAO_PUB);
	}

	/**
	 * 评论爆料
	 * @param cm
	 * @param sessionid 
	 * @return
	 */
	public RspResultModel commentBaoliao(CommentModel cm, String sessionid) {
		AjaxParams params = buildBaseParam();
		params.put("sessionid",sessionid);
		params.put("id",cm.getId()+"");
		params.put("content",cm.getContent());
		return doRequest(params,URL_BAOLIAO_COM);
	}
	
	/**
	 * 新闻列表
	 * @param sessionid
	 * @param start
	 * @param size
	 * @return
	 */
	public RspResultModel getNewsList(String attype,
			String subattype,
			String start,
			String size) {
		AjaxParams params = buildBaseParam();
		params.put("attype",attype);
		params.put("subattype",subattype);
		params.put("size",size);
		params.put("start",start);
		return doRequest(params,URL_ART_ARTICLE_LIST);
	}
	
	/**
	 * 新闻列表
	 * @param sessionid
	 * @param start
	 * @param size
	 * @return
	 */
	public RspResultModel getNewsVideoList(
			String start,
			String size) {
		AjaxParams params = buildBaseParam();
		params.put("size",size);
		params.put("start",start);
		return doRequest(params,URL_ART_ARTICLE_VIDEOLIST);
	}
	//URL_ART_ARTICLE_VIDEOLIST
	/**
	 * 新闻评论
	 * @param articleid
	 * @param replycontent
	 * @return
	 */
	public RspResultModel pubNewComment(String articleid, String replycontent,String attype) {
		AjaxParams params = buildBaseParam();
		params.put("articleid",articleid);
		params.put("replycontent",replycontent);
		params.put("attype",attype);
		return doRequest(params,URL_NEWS_CM_PUB);
	}

	/**
	 * 评论列表
	 * @param type
	 * @return
	 */
	public RspResultModel commentsList(String id,String type,String start,String size,String attype) {
		 
		AjaxParams params = buildBaseParam();
		params.put("id",id);
		params.put("type",type);
		params.put("start",start);
		params.put("size",size);
		params.put("attype",attype);
		return doRequest(params,URL_NEWS_CM_LIST);
	}
	
	public RspResultModel artTypeList() {
		 
		AjaxParams params = buildBaseParam();
		return doRequest(params,URL_ATTYPE_LIST);
	}
	
	public RspResultModel getSubNewsList(String attype,
			String subattype,
			String start,
			String size,
			String pid) {
		AjaxParams params = buildBaseParam();
		params.put("attype",attype);
		params.put("subattype",subattype);
		params.put("size",size);
		params.put("start",start);
		params.put("pid",pid);
		
		return doRequest(params,URL_ART_SUBARTICLE_LIST);
	}
	public RspResultModel getPicArtDetail(String attype,
			String id) {
		AjaxParams params = buildBaseParam();
		params.put("attype",attype);
		params.put("id",id);
		return doRequest(params,URL_ART_PIC_DETAIL);
	}
	/**
	 * 用户意见反馈
	 * @param content
	 * @return
	 */
	public RspResultModel userFeedback(String content) {
		AjaxParams params = buildBaseParam();
		params.put("content",content);
		return doRequest(params,URL_SYS_FEEDBACK);
	}
	/**
	 * 获取最新app
	 * @param content
	 * @return
	 */
	public RspResultModel sysAppupdate(String nowversion) {
		AjaxParams params = buildBaseParam();
		params.put("nowversion",nowversion);
		return doRequest(params,URL_SYS_APPUPDATE);
	}

	/**
	 * 获取我评论的新闻
	 * @param content
	 * @return
	 */
	public RspResultModel getMyCmNewList(String start,String size) {
		AjaxParams params = buildBaseParam();
		params.put("start",start);
		params.put("size",size);
		return doRequest(params,URL_MY_MYREPLY);
	}
	
	
	public RspResultModel smartWeather(String areaid, String type, String date) {
		RspResultModel rsp = null;
		try{
			String url = SmartWeatherUrlUtil.getInterfaceURL(areaid, type, date);
			log.info("url:"+url);
			Object obj = ComApp
					.getInstance()
					.getFinalHttp()
					.getSync(
							url,null);
			String retString = (String)obj;
			log.info("返回:"+retString);
			if(retString.contains("1")){//如果包含了1,表示数据正常返回
				rsp = (RspResultModel) JsonParse
						.static_consume(retString);
				rsp.setRetcode(CoreContants.RETCODE_SUCC);
			}
			else{
				rsp = new RspResultModel();
				rsp.setRetcode(CoreContants.RETCODE_ERR);
				rsp.setRetmsg("获取天气数据失败~");
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			log.info("请求失败:"+e.getMessage());
			rsp = new RspResultModel();
			rsp.setRetcode(CoreContants.RETCODE_ERR);
			rsp.setRetmsg("网络不给力哦~");
		}
		return rsp;
	}
	
	/**
	 * 获取启动页面
	 * @param content
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public XinHuaModel getStartUpPage(String clientWidth,String clientHeight,String paramId,String timeStamp,String randomNum,String myEncrypt) throws HttpException, IOException {
		AjaxParams params = new AjaxParams();
		params.put("clientWidth",clientWidth);
		params.put("clientHeight",clientHeight);
		params.put("paramId",paramId);
		params.put("timeStamp",timeStamp);
		params.put("randomNum",randomNum);
		params.put("myEncrypt",myEncrypt);
		List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
		data.add(new BasicNameValuePair("clientHeight",clientHeight));
		data.add(new BasicNameValuePair("clientWidth",clientWidth));
		data.add(new BasicNameValuePair("paramId",paramId));
		data.add(new BasicNameValuePair("timeStamp",timeStamp));
		data.add(new BasicNameValuePair("randomNum",randomNum));
		data.add(new BasicNameValuePair("myEncrypt",myEncrypt));
//		return doXinhuaRequest(params,XINHUA_STARTUP_URL);
		return doHttpReq(data,XINHUA_STARTUP_URL);
	}
	
	
	

	
	public XinHuaModel doHttpReq(List<BasicNameValuePair> nameValuePairs,String url){
		String strResponse = null;
		//新建HttpClient对象  
        HttpClient httpclient = new DefaultHttpClient();
        //创建POST连接
        HttpPost httppost = new HttpPost(url);
        try {
//            //使用PSOT方式，必须用NameValuePair数组传递参数
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//            nameValuePairs.add(new BasicNameValuePair("id", "12345"));
//            nameValuePairs.add(new BasicNameValuePair("stringdata","hps is Cool!"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            strResponse= EntityUtils.toString(response.getEntity());  
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		log.info("返回:"+strResponse);
		
		XinHuaModel rsp = (XinHuaModel) JsonParse
				.parsXinHua(strResponse);
		return rsp;
		
	}
	

	public RspResultModel newsPicDetail(String id) {
		 
		AjaxParams params = buildBaseParam();
		params.put("id",id);
		return doRequest(params,URL_NEWS_PIC_DETAIL);
	}
	
	public RspResultModel newsPicList(String start,String size) {
		AjaxParams params = buildBaseParam();
		params.put("start",start);
		params.put("size",size);
		return doRequest(params,URL_NEWS_PIC_LIST);
	}
	/**
	 * 推送图文累计访问数
	 * @param start
	 * @param size
	 * @return
	 */
	public RspResultModel pushArtViewTimes(String viewstr) {
		AjaxParams params = buildBaseParam();
		params.put("viewstr",viewstr);
		return doRequest(params,URL_ART_VIEW);
	}
}
