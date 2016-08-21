package com.netanel.coupons.dailytask;

import java.io.IOException;
import java.time.LocalDate;
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
				System.out.println("running...");
				for (Company company : compDao.getAllCompanies()) {
					for (Coupon coupon : company.getCoupons()) {
						if (coupon.getEndDate().isBefore(LocalDate.now())) {
							deleteCoupon(company, coupon);
						}
					}
				}
			} catch (DAOException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				// TimeUnit.HOURS.sleep(24);
				TimeUnit.SECONDS.sleep(30);
			} catch (InterruptedException e) {
				System.out.println("bye bye");
				System.exit(0);
			}
		}
	}

	private void deleteCoupon(Company company, Coupon coupon) throws DAOException, IOException {
		// Remove coupon from company
		compDao.removeCoupon(company.getId(), coupon.getId());
		// Remove coupon from all customers
		for (long custId : couponDao.getCustomersId(coupon)) {
			custDao.removeCoupon(custId, coupon.getId());
		}
		// Delete coupon
		System.out.println("Deleted expired coupon: " + coupon);
		couponDao.deleteCoupon(coupon);
	}

	/**
	 * 
	 */
	public void stop() {
		running = false;
	}
}
