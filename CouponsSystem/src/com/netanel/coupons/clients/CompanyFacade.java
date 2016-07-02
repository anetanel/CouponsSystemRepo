package com.netanel.coupons.clients;

import java.time.LocalDate;
import java.util.Set;

import com.netanel.coupons.crypt.Password;
import com.netanel.coupons.jbeans.*;

public class CompanyFacade implements CouponClientFacade{
	
	public CompanyFacade() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CouponClientFacade login(String name, Password password, ClientType clientType) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createCoupon(Coupon coupon){
		
	}
	
	public void deleteCoupon(Coupon coupon){
		
	}
	
	public void updateCoupon(Coupon coupon) {
		
	}
	
	public Company getCompany(){
		Company company = null;
		return company;
	}
	
	public Set<Coupon> getAllCoupons(){
		Set<Coupon> coupons = null;
		return coupons;
	}
	
	public Coupon getCoupon(long CouponId) {
		Coupon coupon = null;
		return coupon;
	}
	
	public Set<Coupon> getCouponsByType(CouponType couponType) {
		Set<Coupon> coupons = null;
		return coupons;
	}
	
	public Set<Coupon> getCouponsByDate(LocalDate endDate) {
		Set<Coupon> coupons = null;
		return coupons;
	}
	
	public Set<Coupon> getCouponsByPrice(double price){
		Set<Coupon> coupons = null;
		return coupons;
	}
}
