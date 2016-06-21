package com.netanel.coupons.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import com.netanel.coupons.dao.CouponDAO;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.CouponType;

public class CouponDbDAO implements CouponDAO {

	@Override
	public long createCoupon(Coupon coupon) {
		long id=-1;
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "INSERT INTO Coupon (TITLE, START_DATE, END_DATE, AMOUNT,"
								+ " TYPE, MESSAGE, PRICE, IMAGE) VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, coupon.getTitle());
			stat.setDate(2, coupon.getStartDate());
			stat.setDate(3, coupon.getEndDate());
			stat.setString(4, hashAndSalt.get("salt"));
			stat.executeUpdate();
			ResultSet rs = stat.getGeneratedKeys();
			rs.next();
			id = rs.getLong(1);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void removeCoupon(Coupon coupon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCoupon(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCoupon(String couponName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Coupon getCoupon(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Coupon> getAllCoupons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Coupon> getCouponByType(CouponType couponType) {
		// TODO Auto-generated method stub
		return null;
	}

}
