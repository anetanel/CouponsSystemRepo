package com.netanel.coupons.gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.util.Set;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.jbeans.Company;
import javax.swing.ListSelectionModel;

public class AdminCtrlPanel extends JPanel {
	private JTable CompanyTable;

	/**
	 * Create the panel.
	 * @throws DAOException 
	 */
	public AdminCtrlPanel() throws DAOException {
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		JPanel CompanyPanel = new JPanel();
		tabbedPane.addTab("Companies", null, CompanyPanel, null);
		CompanyPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel BtnsPanel = new JPanel();
		CompanyPanel.add(BtnsPanel, BorderLayout.NORTH);
		
		JButton btnNewCompany = new JButton("New Company");
		BtnsPanel.add(btnNewCompany);
		
		JButton btnEditCompany = new JButton("Edit Company");
		BtnsPanel.add(btnEditCompany);
		
		JButton btnDeleteCompany = new JButton("Delete Company");
		BtnsPanel.add(btnDeleteCompany);
		
		JButton btnRefresh = new JButton("Refresh");
		BtnsPanel.add(btnRefresh);
		
		JPanel CompanyTablePanel = new JPanel();
		CompanyPanel.add(CompanyTablePanel, BorderLayout.CENTER);
		CompanyTablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane CompanyTableScrollPane = new JScrollPane();
		CompanyTablePanel.add(CompanyTableScrollPane);
		
		CompanyTable = new JTable();
		CompanyTable.setAutoCreateRowSorter(true);
		DefaultTableModel AdminTableModel = new DefaultTableModel(getAllCompaniesTable(), new String[]{"ID", "Name", "Email", "Coupons"});
		CompanyTable.setModel(AdminTableModel);
		CompanyTableScrollPane.setViewportView(CompanyTable);
		
		JPanel CustomersPanel = new JPanel();
		tabbedPane.addTab("Customers", null, CustomersPanel, null);

	}
	
	private String[][] getAllCompaniesTable() throws DAOException {
		Set<Company> companies = new AdminFacade().getAllCompanies();
		String[][] table = new String[companies.size()][];
		int cnt = 0;
		for (Company company : companies) {
			table[cnt] = company.getDetails();
			cnt++;
		}
		return table;
	}

}
