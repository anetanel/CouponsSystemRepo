package com.netanel.coupons.app;

import java.util.concurrent.TimeUnit;

public class Test2 {
	public static void main(String[] args) throws InterruptedException {
	CouponSystem a = CouponSystem.getInstance();
		
	TimeUnit.SECONDS.sleep(6);
	a.stop();
	}

}
