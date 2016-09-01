package com.netanel.coupons.web.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.netanel.coupons.jbeans.TestBean;

@Path("test")
public class Test {

	public Test() {
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public TestBean getTest() {
		TestBean tb = new TestBean();
		tb.setElement1("this is element 1");
		tb.setElement2("this is element 2");
		tb.setElement3("this is element 3");
		return tb;
	}
	
//	 @GET
//	  @Produces(MediaType.TEXT_XML)
//	  public String sayXMLHello() {
//	    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
//	  }
}
