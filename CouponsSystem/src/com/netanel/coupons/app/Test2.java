package com.netanel.coupons.app;

import com.netanel.coupons.dao.db.Columns;
import com.netanel.coupons.dao.db.DB;
import com.netanel.coupons.dao.db.Tables;

public class Test2 {
	public static void main(String[] args) {
		System.setProperty("com.mchange.v2.log.MLog", "fallback");
		System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "WARNING");
		System.out.println(DB.foundInDb(Tables.Company, Columns.ID, "2"));
		System.out.println(DB.foundInDb(Tables.Company, Columns.ID, "3"));
		System.out.println(DB.foundInDb(Tables.Company_Coupon, Columns.COMP_ID, Columns.COUPON_ID,"1","2"));
		System.out.println(DB.foundInDb(Tables.Company_Coupon, Columns.COMP_ID, Columns.COUPON_ID,"1","4"));
	}
}
