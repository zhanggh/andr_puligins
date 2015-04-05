package com.plugin.commons.model;

import java.io.Serializable;
import java.util.List;

import com.zq.types.StBaseType;


public class NumberType  implements StBaseType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 712386624367943611L;
	private int id;
	private String name;
	private int ordernum;
	private List<NumberType> subtypes;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}
	public List<NumberType> getSubtypes() {
		return subtypes;
	}
	public void setSubtypes(List<NumberType> subtypes) {
		this.subtypes = subtypes;
	}
	
}
