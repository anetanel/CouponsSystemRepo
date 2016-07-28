package com.netanel.coupons.gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Set;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.gui.models.CouponTableModel;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Customer;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class AdminCtrlPanel extends JPanel {

	//
	// Attributes
	//
	private static final long serialVersionUID = 1L;
	private JTable companyTable;
	private AdminFacade admin;
	private JTable customersTable;
	private CouponTableModel companyTableModel;
	private CouponTableModel customersTableModel;

	/**
	 * Create the panel.
	 * 
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
		btnNewCompany.setIcon(new ImageIcon(AdminCtrlPanel.class.getResource("/images/add.png")));
		btnNewCompany.addActionListener(new BtnNewCompanyActionListener());
		companyBtnsPanel.add(btnNewCompany);

		JButton btnEditCompany = new JButton("Edit Company");
		btnEditCompany.setIcon(new ImageIcon(AdminCtrlPanel.class.getResource("/images/edit.png")));
		btnEditCompany.addActionListener(new BtnEditCompanyActionListener());
		companyBtnsPanel.add(btnEditCompany);

		JButton btnDeleteCompany = new JButton("Delete Company");
		btnDeleteCompany.setIcon(new ImageIcon(AdminCtrlPanel.class.getResource("/images/delete.png")));
		btnDeleteCompany.addActionListener(new BtnDeleteCompanyActionListener());
		companyBtnsPanel.add(btnDeleteCompany);

		JButton btnRefreshCopmanies = new JButton("Refresh");
		btnRefreshCopmanies.setIcon(new ImageIcon(AdminCtrlPanel.class.getResource("/images/refresh.png")));
		btnRefreshCopmanies.addActionListener(new BtnRefreshCopmaniesActionListener());
		companyBtnsPanel.add(btnRefreshCopmanies);

		JPanel companyTablePanel = new JPanel();
		companyPanel.add(companyTablePanel, BorderLayout.CENTER);
		companyTablePanel.setLayout(new BorderLayout(0, 0));

		JScrollPane companyTableScrollPane = new JScrollPane();
		companyTablePanel.add(companyTableScrollPane);

		companyTable = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		companyTable.addMouseListener(new TableMouseListener(companyTable));
		companyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		companyTable.setAutoCreateRowSorter(true);
		refreshCompanyTable();
//		companyTableModel = new CouponTableModel(getAllCompaniesTable(),
//				new String[] { "ID", "Name", "Email", "Coupons" });
//		companyTable.setModel(companyTableModel);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		companyTable.setDefaultRenderer(Number.class, centerRenderer);
		companyTableScrollPane.setViewportView(companyTable);

		JPanel customersPanel = new JPanel();
		tabbedPane.addTab("Customers", null, customersPanel, null);
		customersPanel.setLayout(new BorderLayout(0, 0));

		JPanel customersBtnsPanel = new JPanel();
		customersPanel.add(customersBtnsPanel, BorderLayout.NORTH);

		JButton btnNewCustomer = new JButton("New Customer");
		btnNewCustomer.setIcon(new ImageIcon(AdminCtrlPanel.class.getResource("/images/add.png")));
		btnNewCustomer.addActionListener(new BtnNewCustomerActionListener());
		customersBtnsPanel.add(btnNewCustomer);

		JButton btnEditCustomer = new JButton("Edit Customer");
		btnEditCustomer.setIcon(new ImageIcon(AdminCtrlPanel.class.getResource("/images/edit.png")));
		btnEditCustomer.addActionListener(new BtnEditCustomerActionListener());
		customersBtnsPanel.add(btnEditCustomer);

		JButton btnDeleteCustomer = new JButton("Delete Customer");
		btnDeleteCustomer.setIcon(new ImageIcon(AdminCtrlPanel.class.getResource("/images/delete.png")));
		btnDeleteCustomer.addActionListener(new BtnDeleteCustomerActionListener());
		customersBtnsPanel.add(btnDeleteCustomer);

		JButton btnRefreshCustomers = new JButton("Refresh");
		btnRefreshCustomers.setIcon(new ImageIcon(AdminCtrlPanel.class.getResource("/images/refresh.png")));
		btnRefreshCustomers.addActionListener(new BtnRefreshCustomersActionListener());
		customersBtnsPanel.add(btnRefreshCustomers);

		JPanel customersTablePanel = new JPanel();
		customersPanel.add(customersTablePanel, BorderLayout.CENTER);
		customersTablePanel.setLayout(new BorderLayout(0, 0));

		JScrollPane customersTableScrollPane = new JScrollPane();
		customersTablePanel.add(customersTableScrollPane);

		customersTable = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		customersTable.addMouseListener(new TableMouseListener(customersTable));
		customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customersTable.setAutoCreateRowSorter(true);
		refreshCustomerTable();
//		customersTableModel = new CouponTableModel(getAllCustomersTable(), new String[] { "ID", "Name", "Coupons" });
//		customersTable.setModel(customersTableModel);
		customersTable.setDefaultRenderer(Number.class, centerRenderer);
		customersTableScrollPane.setViewportView(customersTable);

	}

	//
	// Listener Classes
	//
	private class BtnNewCompanyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			newCompany();
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

	private class DialogListener extends WindowAdapter {
		JTable table;

		public DialogListener(JTable table) {
			this.table = table;
		}

		@Override
		public void windowClosed(WindowEvent e) {
			try {
				if (table.equals(companyTable)) {
					refreshCompanyTable();
				}
				if (table.equals(customersTable)) {
					refreshCustomerTable();
				}
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class BtnEditCompanyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			editCompany();
		}
	}

	private class BtnDeleteCompanyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			deleteCompany();
		}
	}

	private class TableMouseListener extends MouseAdapter {
		JTable table;

		public TableMouseListener(JTable table) {
			this.table = table;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				if (table.equals(companyTable)) {
					editCompany();
				}
				if (table.equals(customersTable)) {
					editCustomer();
				}
			}
		}
	}

	private class BtnNewCustomerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			newCustomer();
		}
	}

	private class BtnEditCustomerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			editCustomer();
		}
	}

	private class BtnDeleteCustomerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			deleteCustomer();
		}
	}

	private class BtnRefreshCustomersActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				refreshCustomerTable();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	//
	// Functions
	//
	private Object[][] getAllCompaniesTable() throws DAOException {
		Set<Company> companies = admin.getAllCompanies();
		Object[][] table = new Object[companies.size()][];
		int cnt = 0;
		for (Company company : companies) {
			table[cnt] = company.getDetails();
			cnt++;
		}
		Arrays.sort(table, java.util.Comparator.comparingLong(a -> (Long) a[0]));
		return table;
	}

	private void refreshCustomerTable() throws DAOException {
		customersTableModel = new CouponTableModel(getAllCustomersTable(), new String[] { "ID", "Name", "Coupons" });
		customersTable.setModel(customersTableModel);

	}

	private void deleteCustomer() {
		if (customersTable.getSelectedRow() == -1) {
			return;
		}
		Customer customer = null;
		try {
			customer = admin.getCustomer(getSelectedIdFromTable(customersTable));

			int delete = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to delete customer '" + customer.getCustName() + "'?", "Delete Customer",
					JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE);
			if (delete == 0) {
				admin.deleteCustomer(customer);
				JOptionPane.showMessageDialog(null, "Customer '" + customer.getCustName() + "' deleted!",
						"Customer deleted", JOptionPane.INFORMATION_MESSAGE);
				refreshCustomerTable();
			}
		} catch (DAOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
		}

	}

	private void editCustomer() {
		if (customersTable.getSelectedRow() == -1) {
			return;
		}
		try {
			EditCustomerDialog dialog = new EditCustomerDialog((JFrame) SwingUtilities.getRoot(AdminCtrlPanel.this),
					true, admin, admin.getCustomer(getSelectedIdFromTable(customersTable)));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(dialog.getParent());
			dialog.pack();
			dialog.setVisible(true);
			dialog.addWindowListener(new DialogListener(customersTable));
		} catch (DAOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void newCustomer() {
		NewCustomerDialog dialog = new NewCustomerDialog((JFrame) SwingUtilities.getRoot(AdminCtrlPanel.this), true,
				admin);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(dialog.getParent());
		dialog.pack();
		dialog.setVisible(true);
		dialog.addWindowListener(new DialogListener(customersTable));
	}

	private Object[][] getAllCustomersTable() throws DAOException {
		Set<Customer> customers = admin.getAllCustomers();
		Object[][] table = new Object[customers.size()][];
		int cnt = 0;
		for (Customer customer : customers) {
			table[cnt] = customer.getDetails();
			cnt++;
		}
		Arrays.sort(table, java.util.Comparator.comparingLong(a -> (Long) a[0]));
		return table;
	}

	private void refreshCompanyTable() throws DAOException {
		companyTableModel = new CouponTableModel(getAllCompaniesTable(),
				new String[] { "ID", "Name", "Email", "Coupons" });
		companyTable.setModel(companyTableModel);
	}

	private long getSelectedIdFromTable(JTable table) {
		int row = table.getSelectedRow();
		for (int i = 0; i < table.getColumnCount(); i++) {
			if (table.getColumnName(i).equals("ID")) {
				return (long) table.getValueAt(row, i);
			}
		}
		return -1;
	}

	private void newCompany() {
		NewCompanyDialog dialog = new NewCompanyDialog((JFrame) SwingUtilities.getRoot(AdminCtrlPanel.this), true,
				admin);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(dialog.getParent());
		dialog.pack();
		dialog.setVisible(true);
		dialog.addWindowListener(new DialogListener(companyTable));
	}

	private void editCompany() {
		if (companyTable.getSelectedRow() == -1) {
			return;
		}
		try {
			EditCompanyDialog dialog = new EditCompanyDialog((JFrame) SwingUtilities.getRoot(AdminCtrlPanel.this), true,
					admin, admin.getCompany(getSelectedIdFromTable(companyTable)));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(dialog.getParent());
			dialog.pack();
			dialog.setVisible(true);
			dialog.addWindowListener(new DialogListener(companyTable));
		} catch (DAOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void deleteCompany() {
		if (companyTable.getSelectedRow() == -1) {
			return;
		}
		Company company = null;
		try {
			company = admin.getCompany(getSelectedIdFromTable(companyTable));

			int delete = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to delete company '" + company.getCompName() + "'?", "Delete Company",
					JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE);
			if (delete == 0) {
				admin.deleteCompany(company);
				JOptionPane.showMessageDialog(null, "Company '" + company.getCompName() + "' deleted!",
						"Company deleted", JOptionPane.INFORMATION_MESSAGE);
				refreshCompanyTable();
			}
		} catch (DAOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
		}
	}
}
