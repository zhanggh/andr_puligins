package com.plugin.commons.model;

import java.io.InputStream;
import java.io.Serializable;

import com.plugin.commons.helper.FileUtils;
import com.plugin.commons.helper.FuncUtil;
import com.zq.types.StBaseType;

/**
 * 拍客播客的数据模型
 */
public class PhotoAndVideoModel implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8988329042286265562L;
	private String id;//图片或者视频编号 
	private String imgUrl;//拍客图片地址
	private String videoUrl;//播客视频地址
	private String title;//标题
	private String viewCount;//查看次数
	private String likeCount;//点赞次数;
	private String replyCount;//点赞次数;
	private String pubTime;//发布时间;yyyy-mm-dd HH:MM:ss
	private String filePath;//图片或者视频目录
	private InputStream fileStream;//图片或者视频流
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getViewCount() {
		return viewCount;
	}
	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}
	public String getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(String likeCount) {
		this.likeCount = likeCount;
	}
	public String getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public String getFilePath() {
//		if(!FuncUtil.isEmpty(filePath)){
//			return FileUtils.getPathName(filePath);
//		}
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public InputStream getFileStream(InputStream in) {
			in = FileUtils.getStreamFromFile(this.getFilePath());
		return in;
	}
	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}
	 
}
