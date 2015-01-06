package com.plugin.commons.model;

import java.util.ArrayList;
import java.util.List;

public class MyCollectInfoModel {

	List<BaoLiaoInfoModel> baoliaoList = new ArrayList<BaoLiaoInfoModel>();
	List<AskMsgModel> askMsgList = new ArrayList<AskMsgModel>();
	List<NewsInfoModel> newsList = new ArrayList<NewsInfoModel>();
	 
	List<ContentBean> colslist =new ArrayList<ContentBean>();
	 
	public List<BaoLiaoInfoModel> getBaoliaoList() {
		return baoliaoList;
	}
	public void setBaoliaoList(List<BaoLiaoInfoModel> baoliaoList) {
		this.baoliaoList = baoliaoList;
	}
	public List<AskMsgModel> getAskMsgList() {
		return askMsgList;
	}
	public void setAskMsgList(List<AskMsgModel> askMsgList) {
		this.askMsgList = askMsgList;
	}
	public List<NewsInfoModel> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<NewsInfoModel> newsList) {
		this.newsList = newsList;
	}
	public List<ContentBean> getColslist() {
		return colslist;
	}
	public void setColslist(List<ContentBean> colslist) {
		this.colslist = colslist;
	}
	
	public BaoLiaoInfoModel getBaoliaoById(String id){
		for(BaoLiaoInfoModel bl:baoliaoList){
			if(bl.getId().equals(id)){
				return bl;
			}
		}
		return new BaoLiaoInfoModel();
	}
	public AskMsgModel getAskMsgById(String id){
		for(AskMsgModel bl:askMsgList){
			if(bl.getId().equals(id)){
				return bl;
			}
		}
		return new AskMsgModel();
	}
	public NewsInfoModel getNewsById(String id){
		for(NewsInfoModel bl:newsList){
			if(bl.getId().equals(id)){
				return bl;
			}
		}
		return new NewsInfoModel();
	}
}
