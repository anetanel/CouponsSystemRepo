package com.netanel.coupons.dao;

import java.util.Set;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.CouponType;

public interface CouponDAO {
	public long createCoupon(Coupon coupon);
	
	public void removeCoupon(Coupon coupon);
	public void removeCoupon(long id);
	public void removeCoupon(String couponName);
	
	public void updateCoupon(Coupon coupon);
	
	public Coupon getCoupon(long id);
	
	public Set<Coupon> getAllCoupons();
	
	public Set<Coupon> getCouponByType(CouponType couponType);

}
