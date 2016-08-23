package com.netanel.coupons.dao;

import java.io.IOException;
import java.util.Set;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.CouponType;

/**
 * Coupon DAO Interface
 */
public interface CouponDAO {
	/**
	 * Create a new Coupon in the underling database (or any other persistence storage), from a given {@code Coupon} object.
	 * @param coupon a {@code Coupon} object.
	 * @return a positive {@code long} value corresponding to the ID of the newly created coupon, or {@code -1} if the operation failed.
	 * @throws DAOException
	 */
	long createCoupon(Coupon coupon) throws DAOException;
	
	/**
	 * Remove a Company from the underling database (or any other persistence storage).
	 * @param coupon a {@code Coupon} object.
	 * @throws DAOException
	 * @throws IOException 
	 */
	void deleteCoupon(Coupon coupon) throws DAOException, IOException;
	
	/**
	 * Remove a Company from the underling database (or any other persistence storage).
	 * @param couponId a {@code long} Coupon ID.
	 * @throws DAOException
	 * @throws IOException 
	 */
	void deleteCoupon(long couponId) throws DAOException, IOException;

	/**
	 * Updates a Coupon in the underling database (or any other persistence storage), from a given {@code Coupon} object.
	 * The fields affected are Title, Start Date, End Date, Amount, Type, Message, Price and Image.
	 * @param coupon a {@code Coupon} object.
	 * @throws DAOException
	 */
	void updateCoupon(Coupon coupon) throws DAOException;
	
	/**
	 * Returns a {@code Coupon} object from the underling database (or any other persistence storage).
	 * @param couponId a {@code long} Coupon ID.
	 * @return a {@code Coupon} object.
	 * @throws DAOException 
	 */
	Coupon getCoupon(long couponId) throws DAOException;
	
	/**
	 * Returns a {@code Set<Coupon>} of all Coupons from the underling database (or any other persistence storage).
	 * @return a {@code Set<Coupon>}.
	 * @throws DAOException 
	 */
	Set<Coupon> getAllCoupons() throws DAOException;
	
	/**
	 * Returns a {@code Set<Coupon>} of all Coupons of a specific {@code CouponType} from the underling database (or any other persistence storage).
	 * @param couponType a {@code CouponType} enum by which to filter the coupons.
	 * @return a {@code Set<Coupon>}.
	 * @throws DAOException 
	 */
	Set<Coupon> getCouponByType(CouponType couponType) throws DAOException;
	
	/**
	 * Returns a {@code Set<Long>} of the IDs of the Customers that correlate to the Coupon in the underling database (or any other persistence storage).
	 * @param coupon a {@code Coupon} object.
	 * @return a {@code Set<Long>}.
	 * @throws DAOException 
	 */
	Set<Long> getCustomersId(Coupon coupon) throws DAOException;

	/**
	 * Delete coupon icon image file
	 * @param couponImage a {@code String} of the path to the image file
	 * @throws IOException
	 */
	void deleteImage(String couponImage) throws IOException;


}
