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

public class AdminCtrlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable companyTable;
	private AdminFacade admin;
	private JTable customersTable;
	private CouponTableModel companyTableModel;

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
		btnNewCompany.addActionListener(new BtnNewCompanyActionListener());
		companyBtnsPanel.add(btnNewCompany);

		JButton btnEditCompany = new JButton("Edit Company");
		btnEditCompany.addActionListener(new BtnEditCompanyActionListener());
		companyBtnsPanel.add(btnEditCompany);

		JButton btnDeleteCompany = new JButton("Delete Company");
		btnDeleteCompany.addActionListener(new BtnDeleteCompanyActionListener());
		companyBtnsPanel.add(btnDeleteCompany);

		JButton btnRefreshCopmanies = new JButton("Refresh");
		btnRefreshCopmanies.addActionListener(new BtnRefreshCopmaniesActionListener());
		companyBtnsPanel.add(btnRefreshCopmanies);

		JPanel companyTablePanel = new JPanel();
		companyPanel.add(companyTablePanel, BorderLayout.CENTER);
		companyTablePanel.setLayout(new BorderLayout(0, 0));

		JScrollPane companyTableScrollPane = new JScrollPane();
		companyTablePanel.add(companyTableScrollPane);

		companyTable = new JTable(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		companyTable.addMouseListener(new TableMouseListener());
		companyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		companyTable.setAutoCreateRowSorter(true);
		companyTableModel = new CouponTableModel(getAllCompaniesTable(),
				new String[] { "ID", "Name", "Email", "Coupons" });
		companyTable.setModel(companyTableModel);
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

		customersTable = new JTable(){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		customersTable.addMouseListener(new TableMouseListener());
		customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customersTable.setAutoCreateRowSorter(true);
		CouponTableModel customersTableModel = new CouponTableModel(getAllCustomersTable(),
				new String[] { "ID", "Name", "Coupons" });
		customersTable.setModel(customersTableModel);
		customersTable.setDefaultRenderer(Number.class, centerRenderer);
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
		Arrays.sort(table, java.util.Comparator.comparingLong(a -> (Long) a[0]));
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
		Arrays.sort(table, java.util.Comparator.comparingLong(a -> (Long) a[0]));
		return table;
	}

	private class BtnNewCompanyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			NewCompanyDialog dialog = new NewCompanyDialog((JFrame) SwingUtilities.getRoot(AdminCtrlPanel.this), true,
					admin);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(dialog.getParent());
			dialog.pack();
			dialog.setVisible(true);
			dialog.addWindowListener(new DialogListener());
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

	private void refreshCompanyTable() throws DAOException {
		companyTableModel = new CouponTableModel(getAllCompaniesTable(),
				new String[] { "ID", "Name", "Email", "Coupons" });
		companyTable.setModel(companyTableModel);
	}

	private class DialogListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			try {
				refreshCompanyTable();
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
	private class TableMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			 if (e.getClickCount() == 2) {
		           editCompany();
		        }
		}
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
	
	private void editCompany() {
		try {
			EditCompanyDialog dialog = new EditCompanyDialog((JFrame) SwingUtilities.getRoot(AdminCtrlPanel.this),
					true, admin, admin.getCompany(getSelectedIdFromTable(companyTable)));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(dialog.getParent());
			dialog.pack();
			dialog.setVisible(true);
			dialog.addWindowListener(new DialogListener());
		} catch (DAOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
		}
	}
}
