package com.netanel.coupons.dao.db;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.netanel.coupons.dao.CouponDAO;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.exception.DaoSQLException;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.CouponType;

/**
 * Coupon SQL Database DAO Class
 */
public class CouponDbDAO implements CouponDAO {

	@Override
	public long createCoupon(Coupon coupon) throws DAOException {
		// Check if ID already exist in DB
		if (DB.foundInDb(Tables.Coupon, Columns.ID, String.valueOf(coupon.getId()))) {
			throw new DAOException("Coupon ID already exist in DB: " + coupon.getId());
		}

		// Initialize id to -1
		long id=-1;
		try (Connection con = DB.getConnection()){
			// SQL command:
			String sqlCmdStr = "INSERT INTO Coupon (TITLE, START_DATE, END_DATE, AMOUNT,"
								+ " TYPE, MESSAGE, PRICE, IMAGE) VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, coupon.getTitle());
			stat.setDate(2, Date.valueOf(coupon.getStartDate()));
			stat.setDate(3, Date.valueOf(coupon.getEndDate()));
			stat.setInt(4, coupon.getAmount());
			stat.setString(5, coupon.getType().toString());
			stat.setString(6, coupon.getMessage());
			stat.setDouble(7, coupon.getPrice());
			stat.setString(8, coupon.getImage());
			stat.executeUpdate();
			// Result set retrieves the SQL auto-generated ID
			ResultSet rs = stat.getGeneratedKeys();
			rs.next();
			// Set id from SQL auto-generated ID
			id = rs.getLong(1);
			coupon.setId(id);
		} catch (SQLException e) {
			throw new DaoSQLException(e.getMessage());
		}
		return id;
	}

	@Override
	public void deleteCoupon(long couponId) throws DAOException, IOException {
		// Check if Coupon ID is in DB
		if (!DB.foundInDb(Tables.Coupon, Columns.ID, String.valueOf(couponId))) {
			throw new DAOException("Coupon ID does not exist in DB: " + couponId);
		}
		
		// Get coupon image for future deletion
		String couponImage = getCoupon(couponId).getImage();
		
		try (Connection con = DB.getConnection()){
			String sqlCmdStr = "DELETE FROM Coupon WHERE ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setLong(1, couponId);
			stat.executeUpdate();
			deleteImage(couponImage);
		} catch (SQLException e) {
			throw new DaoSQLException(e.getMessage());
		}		
	}
	
	@Override
	public void deleteCoupon(Coupon coupon) throws DAOException, IOException {
		deleteCoupon(coupon.getId());		
	}
	
	@Override
	public void deleteImage(String couponImage) throws IOException {
		// Delete image if not default icon
		if (!couponImage.equals(Coupon.DEFAULT_ICON)) {
			Path path = FileSystems.getDefault().getPath(couponImage);
			Files.deleteIfExists(path);
		}
	}
	
	@Override
	public void updateCoupon(Coupon coupon) throws DAOException {
		// Check if Coupon ID is in DB
		if (!DB.foundInDb(Tables.Coupon, Columns.ID, String.valueOf(coupon.getId()))) {
			throw new DAOException("Coupon ID does not exist in DB: " + coupon.getId());
		}
		
		try (Connection con = DB.getConnection()){
			String sqlCmdStr = "UPDATE Coupon SET TITLE=?, START_DATE=?, END_DATE=?, AMOUNT=?,"
								+ " TYPE=?, MESSAGE=?, PRICE=?, IMAGE=? WHERE ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, coupon.getTitle());
			stat.setDate(2, Date.valueOf(coupon.getStartDate()));
			stat.setDate(3, Date.valueOf(coupon.getEndDate()));
			stat.setInt(4, coupon.getAmount());
			stat.setString(5, coupon.getType().toString());
			stat.setString(6, coupon.getMessage());
			stat.setDouble(7, coupon.getPrice());
			stat.setString(8, coupon.getImage());
			stat.setLong(9, coupon.getId());
			stat.executeUpdate();
			
		} catch (SQLException e) {
			throw new DaoSQLException(e.getMessage());
		}
	}

	@Override
	public Coupon getCoupon(long couponId) throws DAOException {
		// Check if Coupon ID is in DB
		if (!DB.foundInDb(Tables.Coupon, Columns.ID, String.valueOf(couponId))) {
			throw new DAOException("Coupon ID does not exist in DB: " + couponId);
		}
				
		Coupon coupon = null;
		String title, message, image;
		Date startDate, endDate;
		int amount;
		CouponType type;
		double price;
		
		try (Connection con = DB.getConnection()){
			String sqlCmdStr = "SELECT * FROM Coupon WHERE ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setLong(1, couponId);
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
			
			coupon = new Coupon(couponId, title, startDate.toLocalDate(),
					endDate.toLocalDate(), amount, type, message, price, image);
		} catch (SQLException e) {
			throw new DaoSQLException(e.getMessage());
		}
		return coupon;
	}

	@Override
	public Set<Coupon> getAllCoupons() throws DAOException {
		Set<Coupon> coupons = new HashSet<>(); 
		try (Connection con = DB.getConnection()){
			String sqlCmdStr = "SELECT ID FROM Coupon";
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery(sqlCmdStr);
			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
		} catch (SQLException e) {
			throw new DaoSQLException(e.getMessage());
		}
		return coupons;
	}

	@Override
	public Set<Coupon> getCouponByType(CouponType couponType) throws DAOException {
		Set<Coupon> coupons = new HashSet<>();
		try (Connection con = DB.getConnection()){
			String sqlCmdStr = "SELECT ID FROM Coupon WHERE TYPE=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setString(1, couponType.toString());
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
		} catch (SQLException e) {
			throw new DaoSQLException(e.getMessage());
		}
		return coupons;
	}

	
	@Override
	public Set<Long> getCustomersId(Coupon coupon) throws DAOException{
		Set<Long> customers = new HashSet<>();
		try (Connection con = DB.getConnection()){
			String sqlCmdStr = "SELECT CUST_ID FROM Customer_Coupon WHERE COUPON_ID=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setLong(1, coupon.getId());
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				customers.add(rs.getLong(1));
			}
		} catch (SQLException e) {
			throw new DaoSQLException(e.getMessage());
		}
		return customers;
	}

}
