package com.netanel.coupons.dao.db;

import java.nio.file.Files;
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
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.exception.JbeansException;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Coupon;

public class CompanyDbDAO implements CompanyDAO {

	@Override
	public long createCompany(Company company) throws DAOException {
		// Check if ID or COMP_NAME already exist in DB
		if (DB.foundInDb(Tables.Company, Columns.ID, String.valueOf(company.getId()))) {
			throw new DAOException("Company ID already exist in DB: " + company.getId());
		}
		if (DB.foundInDb(Tables.Company, Columns.COMP_NAME, String.valueOf(company.getCompName()))) {
			throw new DAOException("Company Name already exist in DB: " + company.getCompName());
		}

		// Initialize id to -1
		long id = -1;
		try (Connection con = DB.getConnection()) {
			// Get Password information from the Company object
			Map<String, String> hashAndSalt = company.getPassword().getHashAndSalt();
			// SQL command:
			String sqlCmdStr = "INSERT INTO Company (COMP_NAME, PASSWORD, EMAIL, SALT) VALUES(?,?,?,?)";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setString(1, company.getCompName());
			stat.setString(2, hashAndSalt.get("hash"));
			stat.setString(3, company.getEmail());
			stat.setString(4, hashAndSalt.get("salt"));
			stat.executeUpdate();
			// Result set retrieves the SQL auto-generated ID
			ResultSet rs = stat.getGeneratedKeys();
			rs.next();
			// Set id from SQL auto-generated ID
			id = rs.getLong(1);
			company.setId(id);
			// Insert all coupons to the Company_Coupon join table
			for (Coupon coupon : company.getCoupons()) {
				addCoupon(company, coupon);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void removeCompany(long compId) throws DAOException {
		// Check if Company ID is in DB
		if (!DB.foundInDb(Tables.Company, Columns.ID, String.valueOf(compId))) {
			throw new DAOException("Company ID does not exist in DB: " + compId);
		}
		try (Connection con = DB.getConnection()) {
			String sqlCmdStr = "DELETE FROM Company WHERE ID=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setLong(1, compId);
			stat.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void removeCompany(Company company) throws DAOException {
		removeCompany(company.getId());
	}

	@Override
	public void updateCompany(Company company) throws DAOException {
		// Check if Company ID is in DB, and if new Company Name is not taken
		if (!DB.foundInDb(Tables.Company, Columns.ID, String.valueOf(company.getId()))) {
			throw new DAOException("Company ID does not exist in DB: " + company.getId());
		}

		boolean nameFound = DB.foundInDb(Tables.Company, Columns.COMP_NAME, company.getCompName());
		boolean idAndNameFound = DB.foundInDb(Tables.Company, Columns.ID, Columns.COMP_NAME,
				String.valueOf(company.getId()), company.getCompName());
		if (nameFound && !idAndNameFound) {
			throw new DAOException("Company Name already exist: " + company.getCompName());
		}

		try (Connection con = DB.getConnection()) {
			// Get Password information from the Company object
			Map<String, String> hashAndSalt = company.getPassword().getHashAndSalt();
			// SQL command:
			String sqlCmdStr = "UPDATE Company SET COMP_NAME=?, EMAIL=?, PASSWORD=?, SALT=? WHERE ID=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setString(1, company.getCompName());
			stat.setString(2, company.getEmail());
			stat.setString(3, hashAndSalt.get("hash"));
			stat.setString(4, hashAndSalt.get("salt"));
			stat.setLong(5, company.getId());
			stat.executeUpdate();
			// // Insert all coupons to the Company_Coupon join table
			// // TODO: what about removing coupons?
			// for (Coupon coupon : company.getCoupons()) {
			// addCoupon(company, coupon);
			// }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Company getCompany(long compId) throws DAOException {
		// Check if Company ID is in DB
		if (!DB.foundInDb(Tables.Company, Columns.ID, String.valueOf(compId))) {
			throw new DAOException("Company ID does not exist in DB: " + compId);
		}
		Company company = null;
		String compName, email;
		Password password = null;
		Set<Coupon> coupons = null;

		try (Connection con = DB.getConnection()) {
			// SQL command:
			String sqlCmdStr = "SELECT * FROM Company WHERE ID=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setLong(1, compId);
			ResultSet rs = stat.executeQuery();

			rs.next();
			compName = rs.getString("COMP_NAME");
			password = new Password(rs.getString("PASSWORD"), rs.getString("SALT"));
			email = rs.getString("EMAIL");
			coupons = getCoupons(compId);
			company = new Company(compId, compName, password, email, coupons);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}

	@Override
	public Company getCompany(String compName) throws DAOException {
		// Check if Company Name is in DB
		if (!DB.foundInDb(Tables.Company, Columns.COMP_NAME, compName)) {
			throw new DAOException("Company Name does not exist in DB: " + compName);
		}
		long compId;
		Company company = null;
		String email;
		Password password = null;
		Set<Coupon> coupons = null;

		try (Connection con = DB.getConnection()) {
			// SQL command:
			String sqlCmdStr = "SELECT * FROM Company WHERE COMP_NAME=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setString(1, compName);
			ResultSet rs = stat.executeQuery();

			rs.next();
			compId = rs.getLong("ID");
			password = new Password(rs.getString("PASSWORD"), rs.getString("SALT"));
			email = rs.getString("EMAIL");
			coupons = getCoupons(compId);
			company = new Company(compId, compName, password, email, coupons);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}

	@Override
	public Set<Company> getAllCompanies() throws DAOException {
		Set<Company> companies = new HashSet<>();
		try (Connection con = DB.getConnection()) {
			String sqlCmdStr = "SELECT ID FROM Company";
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery(sqlCmdStr);

			while (rs.next()) {
				companies.add(getCompany(rs.getLong(1)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companies;
	}

	@Override
	public Set<Coupon> getCoupons(long compId) throws DAOException {
		// Check if Company ID is in DB
		if (!DB.foundInDb(Tables.Company, Columns.ID, String.valueOf(compId))) {
			throw new DAOException("Company ID does not exist in DB: " + compId);
		}

		Set<Coupon> coupons = new HashSet<>();
		CouponDbDAO couponDB = new CouponDbDAO();
		// Get Coupon ID from Company_Coupon join table
		try (Connection con = DB.getConnection()) {
			String sqlCmdStr = "SELECT COUPON_ID FROM Company_Coupon WHERE COMP_ID=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setLong(1, compId);
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
	public boolean login(String compName, char[] password) throws DAOException {
		// Check if Company Name is in DB
		if (!DB.foundInDb(Tables.Company, Columns.COMP_NAME, compName)) {
			throw new DAOException("Company Name does not exist in DB: " + compName);
		}
		boolean passwordMatch = false;
		String saltHexStr, hashHexStr;
		try (Connection con = DB.getConnection()) {
			// Get hashed password and salt
			String sqlCmdStr = "SELECT PASSWORD, SALT FROM Company WHERE COMP_NAME=?";
			PreparedStatement stat = con.prepareStatement(sqlCmdStr);
			stat.setString(1, compName);
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
	public void addCoupon(Company company, Coupon coupon) throws DAOException {
		addCoupon(company.getId(), coupon);
	}

	@Override
	public void addCoupon(long compId, Coupon coupon) throws DAOException {
		// TODO: can be more efficient? maybe one query..
		// Check if Company ID, Coupon ID, and join information exist in DB
		if (!DB.foundInDb(Tables.Company, Columns.ID, String.valueOf(compId))) {
			throw new DAOException("Company ID does not exist in DB: " + compId);
		}
		if (!DB.foundInDb(Tables.Coupon, Columns.ID, String.valueOf(coupon.getId()))) {
			throw new DAOException("Coupon ID does not exist in DB: " + coupon.getId());
		}
		if (DB.foundInDb(Tables.Company_Coupon, Columns.COMP_ID, Columns.COUPON_ID, String.valueOf(compId),
				String.valueOf(coupon.getId()))) {
			throw new DAOException("Coupon " + coupon.getId() + " already associated to Company " + compId);
		}

		DB.updateJoin(SqlCmd.INSERT, Tables.Company_Coupon, compId, coupon.getId());
	}

	@Override
	public void removeCoupon(long compId, long couponId) throws DAOException {
		// Check if the coupon is associated to the company in the join table
		boolean joinExist = DB.foundInDb(Tables.Company_Coupon, Columns.COMP_ID, Columns.COUPON_ID,
				String.valueOf(compId), String.valueOf(couponId));
		if (!joinExist) {
			throw new DAOException("Coupon " + couponId + " is not associated to Company " + compId);
		}
		DB.updateJoin(SqlCmd.DELETE, Tables.Company_Coupon, compId, couponId);
		
	}

	@Override
	public void removeCoupon(Company company, Coupon coupon) throws DAOException {
		removeCoupon(company.getId(), coupon.getId());
	}

}
