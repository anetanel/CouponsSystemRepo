package com.netanel.coupons.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
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
			//stat.setDate(2, coupon.getStartDate());
			//stat.setDate(3, coupon.getEndDate());
			stat.setTimestamp(2, Timestamp.valueOf(coupon.getStartDate().toString()));
			stat.setTimestamp(3, Timestamp.valueOf(coupon.getEndDate().toString()));
			stat.setInt(4, coupon.getAmount());
			stat.setString(5, coupon.getType().toString());
			stat.setString(6, coupon.getMessage());
			stat.setDouble(7, coupon.getPrice());
			stat.setString(8, coupon.getImage());
			stat.executeUpdate();
			ResultSet rs = stat.getGeneratedKeys();
			rs.next();
			id = rs.getLong(1);
			coupon.setId(id);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void removeCoupon(Coupon coupon) {
		removeCoupon(coupon.getId());		
	}

	@Override
	public void removeCoupon(long id) {
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "DELETE FROM Coupon WHERE ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setLong(1, id);
			stat.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


	@Override
	public void updateCoupon(Coupon coupon) {
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "UPDATE Coupon SET TITLE=?, START_DATE=?, END_DATE=?, AMOUNT=?,"
								+ " TYPE=?, MESSAGE=?, PRICE=?, IMAGE=? WHERE ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, coupon.getTitle());
			//stat.setDate(2, coupon.getStartDate());
			//stat.setDate(3, coupon.getEndDate());
			stat.setTimestamp(2, Timestamp.valueOf(coupon.getStartDate().toString()));
			stat.setTimestamp(3, Timestamp.valueOf(coupon.getEndDate().toString()));
			stat.setInt(4, coupon.getAmount());
			stat.setString(5, coupon.getType().toString());
			stat.setString(6, coupon.getMessage());
			stat.setDouble(7, coupon.getPrice());
			stat.setString(8, coupon.getImage());
			stat.setLong(9, coupon.getId());
			stat.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Coupon getCoupon(long id) {
		Coupon coupon = null;
		String title, message, image;
		Date startDate, endDate;
		int amount;
		CouponType type;
		double price;
		
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "SELECT * FROM Coupon WHERE ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setLong(1, id);
			ResultSet rs = stat.executeQuery();
			rs.next();
			title = rs.getString("TITLE");
			startDate = rs.getDate("START_DATE");
			endDate = rs.getDate("END_DATE");
			amount = rs.getInt("AMOUNT");
			type = CouponType.valueOf(rs.getString("TYPE"));
			message = rs.getString("MESSAGE");
			price = rs.getDouble("PRICE");
			image = rs.getString("IMAGE");
			
			coupon = new Coupon(id, title, startDate, endDate, amount, type, message, price, image);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return coupon;
	}

	@Override
	public Set<Coupon> getAllCoupons() {
		Set<Coupon> coupons = new HashSet<>(); 
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "SELECT ID FROM Coupon";
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery(sqlCmdStr);
			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return coupons;
	}

	@Override
	public Set<Coupon> getCouponByType(CouponType couponType) {
		Set<Coupon> coupons = new HashSet<>();
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "SELECT ID FROM Coupon WHERE TYPE=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setString(1, couponType.toString());
			ResultSet rs = stat.executeQuery(sqlCmdStr);
			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return coupons;
	}

}
