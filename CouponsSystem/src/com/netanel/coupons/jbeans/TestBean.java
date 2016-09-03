package com.netanel.coupons.jbeans;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestBean {

	private Map<String,String> map= new HashMap<>();
	
	public TestBean() {
		// TODO Auto-generated constructor stub
	}
	
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	public Map<String, String> getMap() {
		return map;
	}
}
