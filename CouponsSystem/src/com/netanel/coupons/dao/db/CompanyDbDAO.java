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
import com.netanel.coupons.dao.CompanyDAO;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Coupon;

public class CompanyDbDAO implements CompanyDAO {

	@Override
	public long createCompany(Company company) {
		long id=-1;
		try (Connection con = DB.connectDB()){
			Map<String,String> hashAndSalt = company.getPassword().getHashAndSalt();
			String sqlCmdStr = "INSERT INTO Company (COMP_NAME, PASSWORD, EMAIL, SALT) VALUES(?,?,?,?)";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, company.getCompName());
			stat.setString(2, hashAndSalt.get("hash"));
			stat.setString(3, company.getEmail());
			stat.setString(4, hashAndSalt.get("salt"));
			stat.executeUpdate();
			ResultSet rs = stat.getGeneratedKeys();
			rs.next();
			id = rs.getLong(1);
			company.setId(id);		
			for (Coupon coupon : company.getCoupons()) {
				addCoupon(company, coupon);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void removeCompany(Company company) {
		removeCompany(company.getCompName());
	}
	
	@Override
	public void removeCompany(long compId) {
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "DELETE FROM Company WHERE ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setLong(1, compId);
			stat.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void removeCompany(String compName) {
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "DELETE FROM Company WHERE COMP_NAME=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, compName);
			stat.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	
	@Override
	public void updateCompany(Company company) {
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "UPDATE Company SET COMP_NAME=?, EMAIL=? WHERE ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, company.getCompName());
			stat.setString(2, company.getEmail());
			stat.setLong(3, company.getId());
			stat.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Company getCompany(long compId) {
		Company company = null;
		String compName, email;
		Password password = null;
		Set<Coupon> coupons = null;
		
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "SELECT * FROM Company WHERE ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setLong(1, compId);
			ResultSet rs = stat.executeQuery();
			rs.next();
			compName = rs.getString("COMP_NAME");
			password = new Password(rs.getString("PASSWORD"), rs.getString("SALT"));
			email = rs.getString("EMAIL");
			coupons = getCoupons(compId);
			company = new Company(compId, compName, password, email, coupons);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}

	@Override
	public Set<Company> getAllCompanies() {
		Set<Company> companies = new HashSet<>(); 
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "SELECT ID FROM Company";
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery(sqlCmdStr);
			while (rs.next()) {
				companies.add(getCompany(rs.getLong(1)));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companies;
	}

	@Override
	public Set<Coupon> getCoupons(long compId) {
		Set<Coupon> coupons = new HashSet<>();
		CouponDbDAO couponDB = new CouponDbDAO();
		
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "SELECT COUPON_ID FROM Company_Coupon WHERE COMP_ID=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setLong(1, compId);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				coupons.add(couponDB.getCoupon(rs.getLong("COUPON_ID")));
			}	
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return coupons;
	}

	@Override
	public boolean login(String compName, char[] password) {
		boolean passwordMatch = false;
		String saltHexStr, hashHexStr;
		try (Connection con = DB.connectDB()){
			String sqlCmdStr = "SELECT PASSWORD, SALT FROM Company WHERE COMP_NAME=?";
			PreparedStatement stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, compName);
			ResultSet rs = stat.executeQuery();
			rs.next();
			hashHexStr = rs.getString("PASSWORD");
			saltHexStr = rs.getString("SALT");
			
			passwordMatch = PasswordHash.passwordMatches(saltHexStr, hashHexStr, password);
						
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return passwordMatch;
	}
	
	@Override
	public void addCoupon(Company company, Coupon coupon) {
		try {
			DB.updateJoin("Company_Coupon", company.getId(), coupon.getId());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
