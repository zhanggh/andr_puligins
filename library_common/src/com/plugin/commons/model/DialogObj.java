package com.plugin.commons.model;

public class DialogObj {
	private String code;
	private String name;
	private int drawId;
	private String desc;
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public DialogObj(String code,String name)
	{
		this.code = code;
		this.name = name;
	}
	public DialogObj(String code,String name,int drawId)
	{
		this.code = code;
		this.name = name;
		this.drawId = drawId;
	}
	public DialogObj(String code,String name,int drawId,String desc)
	{
		this.code = code;
		this.name = name;
		this.drawId = drawId;
		this.desc = desc;
	}
	public int getDrawId() {
		return drawId;
	}
	public void setDrawId(int drawId) {
		this.drawId = drawId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
