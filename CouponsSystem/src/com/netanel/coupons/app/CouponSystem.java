package com.netanel.coupons.app;

import com.netanel.coupons.dailytask.DailyCouponExpirationTask;
import com.netanel.coupons.dao.CompanyDAO;
import com.netanel.coupons.dao.CouponDAO;
import com.netanel.coupons.dao.CustomerDAO;
import com.netanel.coupons.dao.db.CompanyDbDAO;
import com.netanel.coupons.dao.db.CouponDbDAO;
import com.netanel.coupons.dao.db.CustomerDbDAO;

/**
 * Coupon System Singleton.
 * This Singleton instantiates all DOA objects, and starts a task to purge expired coupons.  
 */

public class CouponSystem {
	private static CouponSystem instance = null;
	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO couponDao = null;
	private DailyCouponExpirationTask dailyTask = null;
	private Thread dailyTaskThread = null;
	
	//
	// Constructor
	//
	private CouponSystem() {
		compDao = new CompanyDbDAO();
		custDao = new CustomerDbDAO();
		couponDao = new CouponDbDAO();
		dailyTask = new DailyCouponExpirationTask(compDao, custDao, couponDao);
		dailyTaskThread = new Thread(dailyTask);
		dailyTaskThread.start();		
	}
	
	//
	// Functions
	//
	/**
	 * Instantiate CouponSystem singleton.
	 * @return a {@code CouponSystem} object.
	 */
	public static CouponSystem getInstance() {
		if (instance == null) {
			instance = new CouponSystem();
		}
		return instance;
	}

	/**
	 * Gracefully stops the Coupon System. 
	 */
	public void stop() {
		dailyTask.stop();
		dailyTaskThread.interrupt();
	}
	
	/**
	 * @return a {@code CompanyDAO} object.
	 */
	public CompanyDAO getCompDao() {
		return compDao;
	}

	/**
	 * @return {@code CustomerDao} object.
	 */
	public CustomerDAO getCustDao() {
		return custDao;
	}

	/**
	 * @return a {@code CouponDAO} object.
	 */
	public CouponDAO getCouponDao() {
		return couponDao;
	}
	
}
