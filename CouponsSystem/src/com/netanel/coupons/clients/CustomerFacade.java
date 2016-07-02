package com.netanel.coupons.clients;

import java.util.Set;

import com.netanel.coupons.crypt.Password;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.CouponType;

public class CustomerFacade implements CouponClientFacade{

	public CustomerFacade() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public CouponClientFacade login(String name, Password password, ClientType clientType) {
		// TODO Auto-generated method stub
		return null;
	}

	public void buyCoupon(Coupon coupon){
		
	}
	
	public Set<Coupon> getAllCoupons(){
		Set<Coupon> coupons = null;
		return coupons;
	}
	
	public Set<Coupon> getCouponsByType(CouponType couponType) {
		Set<Coupon> coupons = null;
		return coupons;
	}
	
	public Set<Coupon> getCouponsByPrice(double price){
		Set<Coupon> coupons = null;
		return coupons;
	}
	
}
