package com.netanel.coupons.app;

import com.netanel.coupons.dao.db.DB;

public class Test2 {
	public static void main(String[] args) {
//		System.setProperty("com.mchange.v2.log.MLog", "fallback");
//		System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "WARNING");
		System.out.println(DB.foundInDb("Company", "ID", "8"));
	}
}
