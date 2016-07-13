package com.netanel.coupons.app;

import com.netanel.coupons.dailytask.DailyCouponExpirationTask;
import com.netanel.coupons.dao.CompanyDAO;
import com.netanel.coupons.dao.CouponDAO;
import com.netanel.coupons.dao.CustomerDAO;
import com.netanel.coupons.dao.db.CompanyDbDAO;
import com.netanel.coupons.dao.db.CouponDbDAO;
import com.netanel.coupons.dao.db.CustomerDbDAO;

public class CouponSystem {
	public static CouponSystem instance = null;
	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO couponDao = null;
	private DailyCouponExpirationTask dailyTask = null;
	private Thread dailyTaskThread = null;
	
	private CouponSystem() {
		compDao = new CompanyDbDAO();
		custDao = new CustomerDbDAO();
		couponDao = new CouponDbDAO();
		dailyTask = new DailyCouponExpirationTask(compDao, custDao, couponDao);
		dailyTaskThread = new Thread(dailyTask);
		dailyTaskThread.start();		
	}
	
	public static CouponSystem getInstance() {
		if (instance == null) {
			instance = new CouponSystem();
		}
		return instance;
	}

	public void stop() {
		dailyTask.stop();
		dailyTaskThread.interrupt();
	}
	public CompanyDAO getCompDao() {
		return compDao;
	}

	public CustomerDAO getCustDao() {
		return custDao;
	}

	public CouponDAO getCouponDao() {
		return couponDao;
	}
	
}
