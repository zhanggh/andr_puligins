package com.plugin.commons;

/**
 * @author zhang
 *
 */
public class CoreContants extends AppMenuCodes{

	/**成功失败返回码*/
	public static final String RETCODE_SUCC = "0";
	public static final String RETCODE_ERR = "1";
    
	public static final boolean DEBUG = false;
	/**
	 * 每页item数目大小
	 */
	public static final int PAGE_SIZE = 20;
	
	
    /**smart weather**/
    public static final String SW_APPID = "c408b1bc7145b9f8";// 
    public static final String SW_PKEY = "082850_SmartWeatherAPI_1b67cac";// 
    public static final String SW_URL = "http://open.weather.com.cn/data/?";//
    
    public static final String SMS_TYPE_0 = "0";//注册验证码
    public static final String SMS_TYPE_1 = "1";//重置密码
    
    public static final String ACTIVITY_COSE = "1";//关闭activity
    public static final String ACTIVITY_RETTRUN = "2";//返回
    
    /**通知类型-系统通知**/
    public static final String NOTIFY_TYPE_SYS = "0";
    /**通知类型-新闻通知**/
    public static final String NOTIFY_TYPE_NEWS = "1";
 
    //请求类型
    
    /**
     * 评论列表
     */
    public static final String REQUEST_COMMENT="REQUEST_COMMENT";
    /**
     * 信件列表
     */
    public static final String REQUEST_LETTER="REQUEST_LETTER";
    /**
     * 我的-信件列表
     */
    public static final String REQUEST_MY_LETTER="REQUEST_MY_LETTER";
   
    /**
     * 我的-休闲旅游
     */
    public static final String REQUEST_ART_27="REQUEST_ART_27";
    
    
    /**
     * 我的-评论
     */
    public static final String REQUEST_MY_COMMENT="REQUEST_MY_COMMENT";
    
    /**
     * 我的-通知
     */
    public static final String REQUEST_NOTICE="REQUEST_NOTICE";
    
    
    /** 缓存*/
    
    public static final String CACHE_USER = "userinfo";
    
    public static final String CACHE_ACCTION = "attenction";//关注
    public static final String CACHE_ASK_GOVLIST = "ask_govlist";//问政机构列表
    public static final String CACHE_ASK_ASKLIST = "ask_asklist";//问政列表
    public static final String CACHE_ASK_MYASK = "CACHE_ASK_MYASK";//我的问政列表
    public static final String CACHE_ASK_MYANSWER = "CACHE_ASK_MYANSWER";//我的问政回答列表
    public static final String CACHE_ASK_ORGANSWER = "CACHE_ASK_ORGANSWER";//机构回答列表
    public static final String CACHE_NEWS_NEWSLIST = "CACHE_NEWS_NEWSLIST";//新闻列表
    public static final String CACHE_NEWS_ARTTYPE = "CACHE_NEWS_ARTTYPE";//新闻大类列表
    public static final String CACHE_NEWS_COMMENTLIST = "cache_news_commentlist";//新闻评论列表
    public static final String CACHE_BAOLIAO_COMMENTLIST = "cache_baoliao_commentlist";//爆料评论列表
    public static final String CACHE_BAOLIAO_NETFLIST = "cache_baoliao_netflist";//网友爆料评论列表
    public static final String CACHE_BAOLIAO_MYLIST = "cache_baoliao_mylist";//我的爆料评论列表
    public static final String CACHE_BAOLIAO_MYREPLY = "cache_baoliao_myreply";//我的爆料评论列表
    public static final String CACHE_BAOLIAO_DETAIL = "cache_baoliao_detail";//爆料详情
    public static final String CACHE_MY_ASKGOV = "cache_my_askgov";//我的-问政
    public static final String CACHE_HOME_LIST = "CACHE_HOME_LIST";//首页
    public static final String CACHE_NOTIFY_LIST = "CACHE_NOTIFY_LIST";//通知
    public static final String CACHE_MYREPLY_LIST = "CACHE_MYREPLY_LIST";//我评论的新闻
    public static final String CACHE_AREA = "CACHE_AREA";//地区缓存
    public static final String CACHE_ART_VIEW = "CACHE_ART_VIEW";//图文访问此时缓存
 
    
    /**
     * 子栏目类别
     * 0图文  1图片 2视频 3外部WEB  4问政 5报料 6调查，7号码通  9暂时未开通栏目
     */
    public static final String NEWS_SUBTYPE_WORD = "0";//新闻类别--图文
    public static final String NEWS_SUBTYPE_PIC = "1";//新闻类别--图片
    public static final String NEWS_SUBTYPE_VIDEO = "2";//新闻类别--视频
    public static final String NEWS_SUBTYPE_WAP = "3";//外部web
    public static final String NEWS_SUBTYPE_WENZHENG = "4";//问政
    public static final String NEWS_SUBTYPE_BAOLIAO = "5";//报料
    public static final String NEWS_SUBTYPE_DIAOCHA = "6";//调查
    public static final String NEWS_SUBTYPE_NUMBER = "7";//号码通
    public static final String NEWS_SUBTYPE_DEVELOPPING = "9";//开发中
    public static final String NEWS_SUBTYPE_TTIP = "trip";//新闻类别--旅游联盟
    /**登录视图传递参数的key*/
	public static final String PARAM_BACK = "back";
	public static final String PARAM_CODE = "code";
	
	
    public static final String NEWS_TYPE_NEWS = "1";//新闻类别--新闻
    public static final String NEWS_TYPE_GOV = "2";//新闻类别--政务
    
	public static final String INF_VERSION_CODE = "11";
	
	/**activity 跳转传递参数时*/
	public final static String PARAMS_NEWS = "news";
	public final static String PARAMS_TYPE = "type";
    
	public static final String PARAMS_MSG = "params_msg";
	public static String PARAMS_MSG_ID = "msg_id";
	public static final String URL_PARAM="url";
	
	/**
	 * 用于判断文章是否需要评论还是仅仅提供收藏
	 * 0用于表示需要评论+收藏
	 */
	public static final String NEWS_COMMENT="1";
	/**
	 * 用于判断文章是否需要评论还是仅仅提供收藏
	 * 1 用于收藏
	 */
	public static final String NEWS_COLLECTION="0";
	
	
	/**
	 * 辽宁政协 不需要忘记密码和注册的标识
	 */
	public static final String APP_LNZX="lnzx";
	
	/**
	 * 请求吗
	 */
	public static final int REQ_CODE_1000=1000;
	
	/**
	 * 访问统计action
	 */
	public static final String ACTION_OCLICK_TIMES="com.artc.oclick.times";
	
	/**
	 * 图文累计访问次数为最大值时，触发上送请求
	 */
	public static final int MAXREQ_TIMES=10;
	/**
	 * 每次启动app的时候上送
	 */
	public static final String PUSH_TYPE="APP_STARTUP";
	
	
}
