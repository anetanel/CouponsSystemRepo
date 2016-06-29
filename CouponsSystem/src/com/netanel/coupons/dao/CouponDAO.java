package com.netanel.coupons.dao;

import java.util.Set;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.CouponType;

public interface CouponDAO {
	long createCoupon(Coupon coupon);
	
	void removeCoupon(Coupon coupon);
	void removeCoupon(long id);

	void updateCoupon(Coupon coupon);
	
	Coupon getCoupon(long id);
	
	Set<Coupon> getAllCoupons();
	
	Set<Coupon> getCouponByType(CouponType couponType);

}
