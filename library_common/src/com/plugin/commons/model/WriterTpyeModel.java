package com.plugin.commons.model;

import java.io.Serializable;

import com.zq.types.StBaseType;

public class WriterTpyeModel implements StBaseType,Serializable{
	private static final long serialVersionUID = 7518967270068118396L;

	private String id;//id
	 
	private String type;// 
	private String tycode;// 
	private String typeName;//类型名称

	public String getTycode() {
		return tycode;
	}

	public void setTycode(String tycode) {
		this.tycode = tycode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
