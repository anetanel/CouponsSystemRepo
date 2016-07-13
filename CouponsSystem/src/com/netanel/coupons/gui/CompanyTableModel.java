package com.netanel.coupons.gui;

import java.util.Set;

import javax.swing.table.AbstractTableModel;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.jbeans.Company;

public class CompanyTableModel extends AbstractTableModel{

	private Set<Company> companies = null;
	public CompanyTableModel(AdminFacade admin) throws DAOException {
		this.companies = admin.getAllCompanies();
	}

	@Override
	public int getRowCount() {
		return companies.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return super.getColumnName(column);
	}
}
