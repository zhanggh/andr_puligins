package com.plugin.commons.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.plugin.commons.helper.FuncUtil;
import com.zq.types.StBaseType;

/**
 * app版本信息
 */
public class SWFrontcastDayModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5887648676603503203L;
	public static Map<String,String> SW_NAME_MAP = new HashMap<String,String>();
	private String fa = "";//id
	private String fb = "";//最新版本号
	private String fc = "";//更新文件url
	private String fd = "";//更新日志
	private String fe = "";//更新日志
	private String ff = "";//更新日志
	private String fg = "";//更新日志
	private String fh = "";//更新日志
	private String fi = "";//更新日志
	public String getFa() {
		return fa;
	}
	public void setFa(String fa) {
		this.fa = fa;
	}
	public String getFb() {
		return fb;
	}
	public void setFb(String fb) {
		this.fb = fb;
	}
	public String getFc() {
		return fc;
	}
	public void setFc(String fc) {
		this.fc = fc;
	}
	public String getFd() {
		return fd;
	}
	public void setFd(String fd) {
		this.fd = fd;
	}
	public String getFe() {
		return fe;
	}
	public void setFe(String fe) {
		this.fe = fe;
	}
	public String getFf() {
		return ff;
	}
	public void setFf(String ff) {
		this.ff = ff;
	}
	public String getFg() {
		return fg;
	}
	public void setFg(String fg) {
		this.fg = fg;
	}
	public String getFh() {
		return fh;
	}
	public void setFh(String fh) {
		this.fh = fh;
	}
	public String getFi() {
		return fi;
	}
	public void setFi(String fi) {
		this.fi = fi;
	}
	
	public String getWeatherImg(){
		Date date = new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if(hour>18){
			return "night_"+fb+"";
		}
		else{
			return "day_"+fa+"";
		}
	}
	
	public String getWeatherName(){
		if(SW_NAME_MAP==null||SW_NAME_MAP.isEmpty()){
			SW_NAME_MAP = new HashMap<String,String>();
			SW_NAME_MAP.put("00", "晴");
			SW_NAME_MAP.put("01", "多云");
			SW_NAME_MAP.put("03", "阵雨");
			SW_NAME_MAP.put("04", "雷阵雨");
			SW_NAME_MAP.put("05", "雷阵雨伴有冰雹");
			SW_NAME_MAP.put("06", "雨夹雪");
			SW_NAME_MAP.put("07", "小雨");
			SW_NAME_MAP.put("08", "中雨");
			SW_NAME_MAP.put("09", "大雨");
			SW_NAME_MAP.put("10", "暴雨");
			SW_NAME_MAP.put("11", "大暴雨");
			SW_NAME_MAP.put("12", "特大暴雨");
			SW_NAME_MAP.put("13", "阵雪");
			SW_NAME_MAP.put("14", "小雪");
			SW_NAME_MAP.put("15", "中雪");
			SW_NAME_MAP.put("16", "大雪");
			SW_NAME_MAP.put("17", "暴雪");
			SW_NAME_MAP.put("18", "雾");
			SW_NAME_MAP.put("19", "冻雨");
			SW_NAME_MAP.put("20", "沙尘暴");
			SW_NAME_MAP.put("21", "小到中雨");
			SW_NAME_MAP.put("22", "中到大雨");
			SW_NAME_MAP.put("23", "大到暴雨");
			SW_NAME_MAP.put("24", "暴雨到大暴雨");
			SW_NAME_MAP.put("25", "大暴雨到特大暴雨");
			SW_NAME_MAP.put("26", "小到中雪");
			SW_NAME_MAP.put("27", "中到大雪");
			SW_NAME_MAP.put("28", "大到暴雪");
			SW_NAME_MAP.put("29", "浮尘");
			SW_NAME_MAP.put("30", "扬沙");
			SW_NAME_MAP.put("31", "强沙尘暴");
			SW_NAME_MAP.put("53", "霾");
		}
		String name = "";
		Date date = new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if(hour>18){
			name = SW_NAME_MAP.get(fb);
		}
		else{
			name = SW_NAME_MAP.get(fa);
		}
		return FuncUtil.isEmpty(name)?"未知":name;
		
	}
	
	/**
	 * fa
白天天气现象编号
01
fb
晚上天气现象编号
01
fc
白天天气温度(摄氏度)
11
fd
晚上天气温度(摄氏度)
0
fe
白天风向编号
4
ff
晚上风向编号
4
SmartWeatherAPI WebAPI 版接口使用说明书 <Lite>
7 商业机密请勿泄漏
fg
白天风力编号
1
fh
晚上风力编号
0
fi
日出日落时间(中间用|分割)
06:44|18:21
	 */
	
}
