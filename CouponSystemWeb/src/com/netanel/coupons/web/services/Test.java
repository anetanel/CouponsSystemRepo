package com.netanel.coupons.web.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.netanel.coupons.crypt.Password;
import com.netanel.coupons.facades.ClientType;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.CouponType;
import com.netanel.coupons.jbeans.TestBean;

@Path("test")
public class Test {

	public Test() {
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public TestBean login(
					@QueryParam("username") String username,
					@QueryParam("password") String password,
					@QueryParam("clientType") String clientType) {
		HashMap<String, String> map = new HashMap<>();
		map.put("username", username);
		map.put("password", password);
		map.put("clientType", clientType);
		TestBean bean = new TestBean();
		bean.setMap(map);
		return bean;
	}
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getTest() {
		
		return "This is just a test...";
	}
	
	
	@GET
	@Path("/coupon/{couponTitle}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCoupon(@PathParam("couponTitle") String couponTitle) {
		
		Coupon coupon = new Coupon(couponTitle, LocalDate.now(), LocalDate.of(2018, 5, 15), 5, CouponType.CARS, "A car", 1999.89, "");
		
		return coupon;
	}
	
	@GET
	@Path("/company")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany() {
		Coupon coupon1 = new Coupon("My Coupon1", LocalDate.now(), LocalDate.of(2018, 5, 15), 5, CouponType.CARS, "A car1", 1999.89, "");
		Coupon coupon2 = new Coupon("My Coupon2", LocalDate.now(), LocalDate.of(2018, 6, 15), 5, CouponType.CARS, "A car2", 2222.89, "");
		Coupon coupon3 = new Coupon("My Coupon3", LocalDate.now(), LocalDate.of(2018, 7, 15), 5, CouponType.CARS, "A car3", 3333.89, "");
		HashSet<Coupon> coupons = new HashSet<>();
		coupons.add(coupon1);
		coupons.add(coupon2);
		coupons.add(coupon3);
		Company company = new Company("My company", new Password("1234".toCharArray()), "company@company.com", coupons);
		
		return company;
	}
	
//	 @GET
//	  @Produces(MediaType.TEXT_XML)
//	  public String sayXMLHello() {
//	    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
//	  }
}
