package com.netanel.coupons.jbeans;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.netanel.coupons.web.adapters.LocalDateAdapter;

@XmlRootElement
public class TestBean {
	private String title;
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate localDate;
	
	public TestBean() {	}
	
	public TestBean(String title) {
		this.title = title;
	}
	
	public TestBean(String title, LocalDate localDate) {
		this.title = title;
		this.localDate = localDate;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}
	
	
	public LocalDate getLocalDate() {
		return localDate;
	}
	
}
