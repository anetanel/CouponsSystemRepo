package com.netanel.coupons.dailytask;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import com.netanel.coupons.dao.CompanyDAO;
import com.netanel.coupons.dao.CouponDAO;
import com.netanel.coupons.dao.CustomerDAO;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Coupon;

/**
 * A threaded task that runs in the background and deletes expired coupons.
 *
 */
public class DailyCouponExpirationTask implements Runnable {
	//
	// Attributes
	//
	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO couponDao = null;
	private boolean running = true;
	private final static TimeUnit TIMEUNIT = TimeUnit.HOURS;
	private final static int SLEEPTIME = 24;
	

	//
	// Constructors
	//
	public DailyCouponExpirationTask(CompanyDAO compDao, CustomerDAO custDao, CouponDAO couponDao) {
		this.compDao = compDao;
		this.custDao = custDao;
		this.couponDao = couponDao;
	}

	//
	// Functions
	//
	@Override
	public void run() {
		while (running) {
			try {
				System.out.println(LocalDateTime.now() + " - Daily Task Running...");
				// Loop through every company's coupons and delete expired ones
				for (Company company : compDao.getAllCompanies()) {
					for (Coupon coupon : company.getCoupons()) {
						if (coupon.getEndDate().isBefore(LocalDate.now())) {
							deleteCoupon(company, coupon);
						}
					}
				}
			} catch (DAOException | IOException e) {
				System.out.println(LocalDateTime.now() + " - ERROR: Failed to auto delete coupon:");
				System.out.println(e.getMessage());
			}
			try {
				TIMEUNIT.sleep(SLEEPTIME);
			} catch (InterruptedException e) {
				System.out.println("bye bye");
				System.exit(0);
			}
		}
	}

	// Delete expired coupon
	private void deleteCoupon(Company company, Coupon coupon) throws DAOException, IOException {
		// Remove coupon from company
		compDao.removeCoupon(company.getId(), coupon.getId());
		// Remove coupon from all customers
		for (long custId : couponDao.getCustomersId(coupon)) {
			custDao.removeCoupon(custId, coupon.getId());
		}
		// Delete coupon
		System.out.println(LocalDateTime.now() + " - Deleted expired coupon: " + coupon);
		couponDao.deleteCoupon(coupon);
	}

	/**
	 * Gracefully stops the Daily Task
	 */
	public void stop() {
		running = false;
	}
}
