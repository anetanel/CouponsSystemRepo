package com.netanel.coupons.gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.util.Set;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.exception.JbeansException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.gui.models.CompanyTableModel;
import com.netanel.coupons.gui.models.CustomersTableModel;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Customer;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class AdminCtrlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable companyTable;
	private AdminFacade admin;
	private JTable customersTable;
	private CompanyTableModel companyTableModel;

	/**
	 * Create the panel.
	 * @throws DAOException 
	 */
	public AdminCtrlPanel(AdminFacade admin) throws DAOException {
		setName("Admin Control Panel");
		this.admin = admin;
		
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		JPanel companyPanel = new JPanel();
		tabbedPane.addTab("Companies", null, companyPanel, null);
		companyPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel companyBtnsPanel = new JPanel();
		companyPanel.add(companyBtnsPanel, BorderLayout.NORTH);
		
		JButton btnNewCompany = new JButton("New Company");
		btnNewCompany.addActionListener(new BtnNewCompanyActionListener());
		companyBtnsPanel.add(btnNewCompany);
		
		JButton btnEditCompany = new JButton("Edit Company");
		companyBtnsPanel.add(btnEditCompany);
		
		JButton btnDeleteCompany = new JButton("Delete Company");
		companyBtnsPanel.add(btnDeleteCompany);
		
		JButton btnRefreshCopmanies = new JButton("Refresh");
		btnRefreshCopmanies.addActionListener(new BtnRefreshCopmaniesActionListener());
		companyBtnsPanel.add(btnRefreshCopmanies);
		
		JPanel companyTablePanel = new JPanel();
		companyPanel.add(companyTablePanel, BorderLayout.CENTER);
		companyTablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane companyTableScrollPane = new JScrollPane();
		companyTablePanel.add(companyTableScrollPane);
		
		companyTable = new JTable();
		companyTable.setAutoCreateRowSorter(true);
		companyTableModel = new CompanyTableModel(getAllCompaniesTable(), new String[]{"ID", "Name", "Email", "Coupons"});
		companyTable.setModel(companyTableModel);
		companyTableScrollPane.setViewportView(companyTable);
		
		JPanel customersPanel = new JPanel();
		tabbedPane.addTab("Customers", null, customersPanel, null);
		customersPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel customersBtnsPanel = new JPanel();
		customersPanel.add(customersBtnsPanel, BorderLayout.NORTH);
		
		JButton btnNewCustomer = new JButton("New Customer");
		customersBtnsPanel.add(btnNewCustomer);
		
		JButton btnEditCustomer = new JButton("Edit Customer");
		customersBtnsPanel.add(btnEditCustomer);
		
		JButton btnDeleteCustomer = new JButton("Delete Customer");
		customersBtnsPanel.add(btnDeleteCustomer);
		
		JButton btnRefreshCustomers = new JButton("Refresh");
		customersBtnsPanel.add(btnRefreshCustomers);
		
		JPanel customersTablePanel = new JPanel();
		customersPanel.add(customersTablePanel, BorderLayout.CENTER);
		customersTablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane customersTableScrollPane = new JScrollPane();
		customersTablePanel.add(customersTableScrollPane);
		
		customersTable = new JTable();
		customersTable.setAutoCreateRowSorter(true);
		CustomersTableModel customersTableModel = new CustomersTableModel(getAllCustomersTable(), new String[]{"ID", "Name", "Coupons"});
		customersTable.setModel(customersTableModel);
		customersTableScrollPane.setViewportView(customersTable);

	}
		
	private Object[][] getAllCompaniesTable() throws DAOException {
		Set<Company> companies = admin.getAllCompanies();
		Object[][] table = new Object[companies.size()][];
		int cnt = 0;
		for (Company company : companies) {
			table[cnt] = company.getDetails();
			cnt++;
		}
		return table;
	}
	
	private Object[][] getAllCustomersTable() throws DAOException {
		Set<Customer> customers = admin.getAllCustomers();
		Object[][] table = new Object[customers.size()][];
		int cnt = 0;
		for (Customer customer : customers) {
			table[cnt] = customer.getDetails();
			cnt++;
		}
		return table;
	}
	private class BtnNewCompanyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			NewCompanyDialog dialog = new NewCompanyDialog((JFrame)SwingUtilities.getRoot(AdminCtrlPanel.this), true, admin);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(dialog.getParent());
			dialog.pack();
			dialog.setVisible(true);
		}
	}
	private class BtnRefreshCopmaniesActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				refreshCompanyTable();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void refreshCompanyTable() throws DAOException {
		companyTableModel = new CompanyTableModel(getAllCompaniesTable(), new String[]{"ID", "Name", "Email", "Coupons"});
		companyTable.setModel(companyTableModel);
		//companyTable.revalidate();
	}
}
