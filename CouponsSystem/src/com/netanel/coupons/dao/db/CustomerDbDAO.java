package com.netanel.coupons.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.netanel.coupons.crypt.Password;
import com.netanel.coupons.crypt.PasswordHash;
import com.netanel.coupons.dao.CustomerDAO;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.Customer;

public class CustomerDbDAO implements CustomerDAO {

	@Override
	public long createCustomer(Customer customer) throws DAOException {
		// Check if ID already exist in DB
		if (DB.foundInDb(Tables.Customer, Columns.ID, String.valueOf(customer.getId()))) {
			throw new DAOException("Customer ID already exist in DB: " + customer.getId());
		}
		// Initialize id to -1
		long id = -1;
		try (Connection con = DB.getConnection()) {
			// Get Password information from the Customer object
			Map<String, String> hashAndSalt = customer.getPassword().getHashAndSalt();
			// SQL command:
			String sqlCmdStr = "INSERT INTO Customer (CUST_NAME, PASSWORD, SALT) VALUES(?,?,?)";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setString(1, customer.getCustName());
			stat.setString(2, hashAndSalt.get("hash"));
			stat.setString(3, hashAndSalt.get("salt"));
			stat.executeUpdate();
			// Result set retrieves the SQL auto-generated ID
			ResultSet rs = stat.getGeneratedKeys();
			rs.next();
			// Set id from SQL auto-generated ID
			id = rs.getLong(1);
			customer.setId(id);
			// Insert all coupons to the Customer_Coupon join table
			for (Coupon coupon : customer.getCoupons()) {
				addCoupon(customer, coupon);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void removeCustomer(long custId) throws DAOException {
		// Check if Customer ID is in DB
		if (!DB.foundInDb(Tables.Customer, Columns.ID, String.valueOf(custId))) {
			throw new DAOException("Customer ID does not exist in DB: " + custId);
		}
		try (Connection con = DB.getConnection()) {
			String sqlCmdStr = "DELETE FROM Customer WHERE ID=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setLong(1, custId);
			stat.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void removeCustomer(Customer customer) throws DAOException {
		removeCustomer(customer.getId());
	}

	@Override
	public void updateCustomer(Customer customer) throws DAOException {
		// Check if Customer ID is in DB
		if (!DB.foundInDb(Tables.Customer, Columns.ID, String.valueOf(String.valueOf(customer.getId())))) {
			throw new DAOException("Customer ID does not exist in DB: " + String.valueOf(customer.getId()));
		}
		try (Connection con = DB.getConnection()) {
			// Get Password information from the Customer object
			Map<String, String> hashAndSalt = customer.getPassword().getHashAndSalt();
			// SQL command:
			String sqlCmdStr = "UPDATE Customer SET CUST_NAME=?, PASSWORD=?, SALT=? WHERE ID=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setString(1, customer.getCustName());
			stat.setString(2, hashAndSalt.get("hash"));
			stat.setString(3, hashAndSalt.get("salt"));
			stat.setLong(4, customer.getId());
			stat.executeUpdate();
			// Insert all coupons to the Customer_Coupon join table
			for (Coupon coupon : customer.getCoupons()) {
				addCoupon(customer, coupon);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Customer getCustomer(long custId) throws DAOException {
		// Check if Customer ID is in DB
		if (!DB.foundInDb(Tables.Customer, Columns.ID, String.valueOf(custId))) {
			throw new DAOException("Customer ID does not exist in DB: " + custId);
		}
		Customer customer = null;
		String custName;
		Password password = null;
		Set<Coupon> coupons = null;

		try (Connection con = DB.getConnection()) {
			// SQL command:
			String sqlCmdStr = "SELECT * FROM Customer WHERE ID=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setLong(1, custId);
			ResultSet rs = stat.executeQuery();

			rs.next();
			custName = rs.getString("CUST_NAME");
			password = new Password(rs.getString("PASSWORD"), rs.getString("SALT"));
			coupons = getCoupons(custId);
			customer = new Customer(custId, custName, password, coupons);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public Customer getCustomer(String custName) throws DAOException {
		// Check if Customer Name is in DB
		if (!DB.foundInDb(Tables.Customer, Columns.CUST_NAME, custName)) {
			throw new DAOException("Customer Name does not exist in DB: " + custName);
		}
		long custId;
		Customer customer = null;
		Password password = null;
		Set<Coupon> coupons = null;

		try (Connection con = DB.getConnection()) {
			// SQL command:
			String sqlCmdStr = "SELECT * FROM Customer WHERE CUST_NAME=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setString(1, custName);
			ResultSet rs = stat.executeQuery();

			rs.next();
			custId = rs.getLong("ID");
			password = new Password(rs.getString("PASSWORD"), rs.getString("SALT"));
			coupons = getCoupons(custId);
			customer = new Customer(custId, custName, password, coupons);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
	}
	
	@Override
	public Set<Customer> getAllCustomers() throws DAOException {
		Set<Customer> customers = new HashSet<>();
		try (Connection con = DB.getConnection()) {
			String sqlCmdStr = "SELECT ID FROM Customer";
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery(sqlCmdStr);
			while (rs.next()) {
				customers.add(getCustomer(rs.getLong(1)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public Set<Coupon> getCoupons(long custId) throws DAOException {
		// Check if Customer ID is in DB
		if (!DB.foundInDb(Tables.Customer, Columns.ID, String.valueOf(custId))) {
			throw new DAOException("Customer ID does not exist in DB: " + custId);
		}

		Set<Coupon> coupons = new HashSet<>();
		CouponDbDAO couponDB = new CouponDbDAO();
		// Get Coupon ID from Customer_Coupon join table
		try (Connection con = DB.getConnection()) {
			String sqlCmdStr = "SELECT COUPON_ID FROM Customer_Coupon WHERE CUST_ID=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setLong(1, custId);
			ResultSet rs = stat.executeQuery();

			// Get Coupon object for each Coupon ID
			while (rs.next()) {
				coupons.add(couponDB.getCoupon(rs.getLong("COUPON_ID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return coupons;
	}

	@Override
	public boolean login(String custName, char[] password) throws DAOException {
		// Check if Customer Name is in DB
		if (!DB.foundInDb(Tables.Customer, Columns.CUST_NAME, custName)) {
			throw new DAOException("Customer Name does not exist in DB: " + custName);
		}
		boolean passwordMatch = false;
		String saltHexStr, hashHexStr;
		try (Connection con = DB.getConnection()) {
			String sqlCmdStr = "SELECT PASSWORD, SALT FROM Customer WHERE CUST_NAME=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setString(1, custName);
			ResultSet rs = stat.executeQuery();
			rs.next();
			hashHexStr = rs.getString("PASSWORD");
			saltHexStr = rs.getString("SALT");

			passwordMatch = PasswordHash.passwordMatches(saltHexStr, hashHexStr, password);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return passwordMatch;
	}

	@Override
	public void addCoupon(Customer customer, Coupon coupon) throws DAOException {
		addCoupon(customer.getId(), coupon);
	}

	@Override
	public void addCoupon(long custId, Coupon coupon) throws DAOException {
		// Check if Customer ID, Coupon ID, and join information exist in DB
		if (!DB.foundInDb(Tables.Customer, Columns.ID, String.valueOf(custId))) {
			throw new DAOException("Customer ID does not exist in DB: " + custId);
		}
		if (!DB.foundInDb(Tables.Coupon, Columns.ID, String.valueOf(coupon.getId()))) {
			throw new DAOException("Coupon ID does not exist in DB: " + coupon.getId());
		}
		if (DB.foundInDb(Tables.Customer_Coupon, Columns.CUST_ID, Columns.COUPON_ID,
				String.valueOf(custId), String.valueOf(coupon.getId()))) {
			throw new DAOException("Coupon ID:" + coupon.getId() + " (" + coupon.getTitle() + ") already associated to Customer ID:" + custId);
		}

		DB.updateJoin(SqlCmd.INSERT, Tables.Customer_Coupon, custId, coupon.getId());
	}
	
	@Override
	public void removeCoupon(long custId, long couponId) throws DAOException {
		// Check if the coupon is associated to the customer in the join table
		boolean joinExist = DB.foundInDb(Tables.Customer_Coupon, Columns.CUST_ID, Columns.COUPON_ID,
				String.valueOf(custId), String.valueOf(couponId));
		if (!joinExist) {
			throw new DAOException("Coupon ID:" + couponId + " is not associated to Customer ID:" + custId);
		}
		DB.updateJoin(SqlCmd.DELETE, Tables.Customer_Coupon, custId, couponId);
	}

	@Override
	public void removeCoupon(Customer customer, Coupon coupon) throws DAOException {
		removeCoupon(customer.getId(), coupon.getId());
	}

}
