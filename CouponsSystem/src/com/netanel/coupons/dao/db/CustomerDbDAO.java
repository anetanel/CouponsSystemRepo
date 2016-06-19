package com.netanel.coupons.dao.db;

import java.security.NoSuchAlgorithmException;
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
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.Customer;

public class CustomerDbDAO implements CustomerDAO {

	@Override
	public long createCustomer(Customer customer) {
		long id=-1;
		try (Connection con = DB.connectDB()){
			Map<String,String> hashAndSalt = customer.getPassword().getHashAndSalt();
			String sqlCmdStr = "INSERT INTO Customer (CUST_NAME, PASSWORD, SALT) VALUES(?,?,?)";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, customer.getCustName());
			stat.setString(2, hashAndSalt.get("hash"));
			stat.setString(3, hashAndSalt.get("salt"));
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
	public void removeCustomer(Customer customer) {
			removeCustomer(customer.getCustName());		
	}

	@Override
	public void removeCustomer(long id) {
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "DELETE FROM Customer WHERE ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setLong(1, id);
			stat.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void removeCustomer(String custName) {
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "DELETE FROM Customer WHERE CUST_NAME=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, custName);
			stat.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void updateCustomer(Customer customer) {
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "UPDATE Company SET CUST_NAME=? WHERE ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, customer.getCustName());
			stat.setLong(2, customer.getId());
			stat.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Customer getCustomer(long id) {
		Customer customer = null;
		String custName;
		Password password = null;
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "SELECT * FROM Customer WHERE ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setLong(1, id);
			ResultSet rs = stat.executeQuery();
			rs.next();
			custName = rs.getString("CUST_NAME");
			password = new Password(rs.getString("PASSWORD"), rs.getString("SALT"));
			customer = new Customer(id, custName, password);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public Set<Customer> getAllCustomers() {
		Set<Customer> customers = new HashSet<>(); 
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "SELECT ID FROM Customer";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sqlCmdStr);
			while (rs.next()) {
				customers.add(getCustomer(rs.getLong(1)));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public Set<Coupon> getCoupons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(String custName, char[] password) {
		boolean passwordMatch = false;
		String saltHexStr, hashHexStr;
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "SELECT PASSWORD, SALT FROM Customer WHERE CUST_NAME=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, custName);
			ResultSet rs = stat.executeQuery();
			rs.next();
			hashHexStr = rs.getString("PASSWORD");
			saltHexStr = rs.getString("SALT");
			
			passwordMatch = PasswordHash.passwordMatches(saltHexStr, hashHexStr, password);
						
		} catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return passwordMatch;
	}

}
