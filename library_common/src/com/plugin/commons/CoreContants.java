package com.plugin.commons;

/**
 * @author zhang
 *
 */
public class CoreContants extends AppMenuCodes{

	/**成功失败返回码*/
	public static final String RETCODE_SUCC = "0";
	public static final String RETCODE_ERR = "1";
	
	
	/**
	 * 上传拍客返回码
	 */
	public static final int LOGIN_REQ_PHOTO_CODE = 10002;
    
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
     * 拍客列表
     */
    public static final String REQUEST_COM_PHOTOS="REQUEST_COM_PHOTOS";
    /**
     * 播客列表
     */
    public static final String REQUEST_COM_VIDEO="REQUEST_COM_VIDEO";
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
    
    /**
     * 拍客详情
     */
    public static final String REQUEST_PAIKE_DETAIL="REQUEST_PAIKE_DETAIL";
    
    /**
     * 号码通列表
     */
    public static final String REQUEST_NUMBER="REQUEST_NUMBER";
    
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
    public static final String CACHE_NUMBER_TYPES = "CACHE_NUMBER_TYPES";//号码通类型
    public static final String CACHE_NUMBER_LIST = "CACHE_NUMBER_LIST";//号码通列表
 

    
    /**
     * 子栏目类别
     * 0图文  1图片 2视频 3外部WEB  4问政 5报料 6调查，7号码通  9暂时未开通栏目
     */
    public static final String NEWS_SUBTYPE_WORD = "0";//新闻类别--图文
    public static final String NEWS_SUBTYPE_WORD_EXT = "00";//新闻类别--图文,个性化的图文，辽宁政协专用
    public static final String NEWS_SUBTYPE_PIC = "1";//新闻类别--图片
    public static final String NEWS_SUBTYPE_VIDEO = "2";//新闻类别--视频
    public static final String NEWS_SUBTYPE_WAP = "3";//外部web
    public static final String NEWS_SUBTYPE_WENZHENG = "4";//问政
    public static final String NEWS_SUBTYPE_BAOLIAO = "5";//报料
    public static final String NEWS_SUBTYPE_DIAOCHA = "6";//调查
    public static final String NEWS_SUBTYPE_NUMBER = "7";//号码通
    public static final String NEWS_SUBTYPE_LETTER = "8";//信件
    public static final String NEWS_SUBTYPE_DEVELOPPING = "9";//开发中
    public static final String NEWS_SUBTYPE_PKBK = "10";//拍客播客
    /**登录视图传递参数的key*/
	public static final String PARAM_BACK = "back";
	public static final String PARAM_CODE = "code";
	
	
    public static final String NEWS_TYPE_NEWS = "1";//新闻类别--新闻
    public static final String NEWS_TYPE_GOV = "2";//新闻类别--政务
    
	public static final String INF_VERSION_CODE = "11";
	
	/**activity 跳转传递参数时*/
	public final static String PARAMS_NEWS = "news";
	public final static String PARAMS_TYPE = "type";
	public final static String PARAMS_TITLE = "title";
	public final static String PARAMS_CODE = "code";
    
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
	 * 魅力蒙古贞
	 */
	public static final String APP_ZHMLMGZ="zhmlmgz";
	
	/**
	 * 百度定位的app
	 */
	public static final String LOCATION_APP="lnzx,zhlnfb";
	
	
	
	/**
	 * 鹿鸣西丰
	 */
	public static final String APP_LMXF="zhlmxf";
	/**
	 * 智慧浑南
	 */
	public static final String APP_ZHIHUILN="zhihuiln";
	
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
	/**
	 * 天狗对接的key
	 */
	public static final String SHOP_KEY="XinHuaSheTianGou!";
	
	public static final String[][] MIME_MapTable={   
            //{后缀名，MIME类型}   
            {".3gp",    "video/3gpp"},   
            {".apk",    "application/vnd.android.package-archive"},   
            {".asf",    "video/x-ms-asf"},   
            {".avi",    "video/x-msvideo"},   
            {".bin",    "application/octet-stream"},   
            {".bmp",    "image/bmp"},   
            {".c",  "text/plain"},   
            {".class",  "application/octet-stream"},   
            {".conf",   "text/plain"},   
            {".cpp",    "text/plain"},   
            {".doc",    "application/msword"},   
            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},   
            {".xls",    "application/vnd.ms-excel"},    
            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},   
            {".exe",    "application/octet-stream"},   
            {".gif",    "image/gif"},   
            {".gtar",   "application/x-gtar"},   
            {".gz", "application/x-gzip"},   
            {".h",  "text/plain"},   
            {".htm",    "text/html"},   
            {".html",   "text/html"},   
            {".jar",    "application/java-archive"},   
            {".java",   "text/plain"},   
            {".jpeg",   "image/jpeg"},   
            {".jpg",    "image/jpeg"},   
            {".js", "application/x-javascript"},   
            {".log",    "text/plain"},   
            {".m3u",    "audio/x-mpegurl"},   
            {".m4a",    "audio/mp4a-latm"},   
            {".m4b",    "audio/mp4a-latm"},   
            {".m4p",    "audio/mp4a-latm"},   
            {".m4u",    "video/vnd.mpegurl"},   
            {".m4v",    "video/x-m4v"},    
            {".mov",    "video/quicktime"},   
            {".mp2",    "audio/x-mpeg"},   
            {".mp3",    "audio/x-mpeg"},   
            {".mp4",    "video/mp4"},   
            {".mpc",    "application/vnd.mpohun.certificate"},          
            {".mpe",    "video/mpeg"},     
            {".mpeg",   "video/mpeg"},     
            {".mpg",    "video/mpeg"},     
            {".mpg4",   "video/mp4"},      
            {".mpga",   "audio/mpeg"},   
            {".msg",    "application/vnd.ms-outlook"},   
            {".ogg",    "audio/ogg"},   
            {".pdf",    "application/pdf"},   
            {".png",    "image/png"},   
            {".pps",    "application/vnd.ms-powerpoint"},   
            {".ppt",    "application/vnd.ms-powerpoint"},   
            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"},   
            {".prop",   "text/plain"},   
            {".rc", "text/plain"},   
            {".rmvb",   "audio/x-pn-realaudio"},   
            {".rtf",    "application/rtf"},   
            {".sh", "text/plain"},   
            {".tar",    "application/x-tar"},      
            {".tgz",    "application/x-compressed"},    
            {".txt",    "text/plain"},   
            {".wav",    "audio/x-wav"},   
            {".wma",    "audio/x-ms-wma"},   
            {".wmv",    "audio/x-ms-wmv"},   
            {".wps",    "application/vnd.ms-works"},   
            {".xml",    "text/plain"},   
            {".z",  "application/x-compress"},   
            {".zip",    "application/x-zip-compressed"},   
            {"",        "*/*"}     
        };
	
	
	/**
	 * 瞬间-获取栏目
	 */
	public static final String SHUNJIAN_LIST_TYPES_URL="http://xhfocusworld.ddupw.cn/v1/getcolumn.aspx";
	/**
	 * 瞬间-获取某一栏目下的所有资源
	 */
	public static final String SHUNJIAN_LIST_DETAIL_URL="http://xhfocusworld.ddupw.cn/v1/res.aspx";
	/**
	 * new radio-获取栏目
	 */
	public static final String NEWRADIO_LIST_TYPES_URL="http://xhnewradio.ddupw.cn/v1/colunm.aspx";
	/**
	 * new radio-获取某一栏目下的所有资源
	 */
	public static final String NEWRADIO_LIST_DETAIL_URL="http://xhnewradio.ddupw.cn/v1/res.aspx";


	public static final int REQUEST_CODE_IMAGE = 1001;//从相册中获取相片
	public static final int REQUEST_CODE_CAMERL = 1000;//调用相机拍照
	
	public static final int REQUEST_CODE_TAKE_VIDEO = 1002;//调用摄像机
	public static final int REQUEST_CODE_VIDEO = 1003;//从库中获取视频
	
	public static final String REQUEST_APP_AREA = "1";//地区推荐
	public static final String REQUEST_APP_HOT = "2";//热门推荐
	public static final String REQUEST_APP_INDUSTRY = "3";//地区推荐
	
	
	/**
	 * 下载状态 0未下载，1下载中，2下载完毕
	 */
	public static final int DOWNLOAD_STATUS_0 = 0;
	public static final int DOWNLOAD_STATUS_1 = 1;
	public static final int DOWNLOAD_STATUS_2 = 2;
}
