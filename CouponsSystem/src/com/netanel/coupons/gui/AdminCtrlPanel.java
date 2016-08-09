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
import com.netanel.coupons.facades.ClientType;
import com.netanel.coupons.gui.models.CouponTableModel;
import com.netanel.coupons.jbeans.Client;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Customer;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

/**
 * Admin control panel 
 *
 */
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
	 * @param admin an {@code AdminFacade} object.
	 * @throws DAOException
	 */
	public AdminCtrlPanel(AdminFacade admin) throws DAOException {
		this.admin = admin;
		
		setName("Admin Control Panel");
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);

		// Companies panel
		JPanel companyPanel = new JPanel();
		tabbedPane.addTab("Companies", null, companyPanel, null);
		companyPanel.setLayout(new BorderLayout(0, 0));
		
		// Companies button panel
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
		
		// Companies table panel
		JPanel companyTablePanel = new JPanel();
		companyPanel.add(companyTablePanel, BorderLayout.CENTER);
		companyTablePanel.setLayout(new BorderLayout(0, 0));

		JScrollPane companyTableScrollPane = new JScrollPane();
		companyTablePanel.add(companyTableScrollPane);

		// Companies table
		companyTable = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		companyTable.addMouseListener(new TableMouseListener(ClientType.COMPANY));
		companyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		companyTable.setAutoCreateRowSorter(true);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		companyTable.setDefaultRenderer(Number.class, centerRenderer);
		companyTableScrollPane.setViewportView(companyTable);
		refreshCompanyTable();

		
		// Customers panel
		JPanel customersPanel = new JPanel();
		tabbedPane.addTab("Customers", null, customersPanel, null);
		customersPanel.setLayout(new BorderLayout(0, 0));

		// Customers button panel
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

		// Customers table panel
		JPanel customersTablePanel = new JPanel();
		customersPanel.add(customersTablePanel, BorderLayout.CENTER);
		customersTablePanel.setLayout(new BorderLayout(0, 0));

		JScrollPane customersTableScrollPane = new JScrollPane();
		customersTablePanel.add(customersTableScrollPane);

		// Customers table
		customersTable = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		customersTable.addMouseListener(new TableMouseListener(ClientType.CUSTOMER));
		customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customersTable.setAutoCreateRowSorter(true);
		customersTable.setDefaultRenderer(Number.class, centerRenderer);
		customersTableScrollPane.setViewportView(customersTable);
		refreshCustomerTable();

	}

	//
	// Listener Classes
	//
	
	// New Company button listener
	private class BtnNewCompanyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			newCompany();
		}
	}

	// Refresh Companies button listener
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


	// Edit Company button listener
	private class BtnEditCompanyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			editCompany();
		}
	}

	// Delete company button listener
	private class BtnDeleteCompanyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			deleteCompany();
		}
	}


	// New Customer button listener
	private class BtnNewCustomerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			newCustomer();
		}
	}

	// Edit Customer button listener
	private class BtnEditCustomerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			editCustomer();
		}
	}

	// Delete Customer button listener
	private class BtnDeleteCustomerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			deleteCustomer();
		}
	}

	// Refresh Customers button listener
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
	
	// Dialog listener
	// Listens to frame dispose
	private class DialogListener extends WindowAdapter {
		ClientType clientType;
		
		public DialogListener(ClientType clientType) {
			this.clientType = clientType; 
		}
		
		@Override
		public void windowClosed(WindowEvent e) {
			try {
				if (clientType.equals(ClientType.COMPANY)) {
					refreshCompanyTable();
				}
				if (clientType.equals(ClientType.CUSTOMER)) {
					refreshCustomerTable();
				}
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	// Table mouse listener
	// Listens to double click on table row
	private class TableMouseListener extends MouseAdapter {
		ClientType clientType;
		
		public TableMouseListener(ClientType clientType) {
			this.clientType = clientType;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				if (clientType.equals(ClientType.COMPANY)) {
					editCompany();
				}
				if (clientType.equals(ClientType.CUSTOMER)) {
					editCustomer();
				}
			}
		}
	}

	//
	// Functions
	//
	
	// Returns an ID-sorted two-dimensional array of all clients (companies or customers).
	// Used by table model.
	private Object[][] getTable(ClientType clientType) throws DAOException {
		Object[][] table = null;
		int cnt = 0;
		Set<? extends Client> clients = null;
		if (clientType.equals(ClientType.COMPANY)) {
			clients = admin.getAllCompanies();
		} else if (clientType.equals(ClientType.CUSTOMER)){
			clients = admin.getAllCustomers();
		}
		table  = new Object[clients.size()][];
		for (Client c : clients) {
			if (c instanceof Customer) {
				table[cnt] = ((Customer) c).getDetails();
			} else if (c instanceof Company) {
				table[cnt] = ((Company) c).getDetails();
			}
			cnt++;
		}
		Arrays.sort(table, java.util.Comparator.comparingLong(a -> (Long) a[0]));
		return table;
		
	}

	// (Re)Loads customers table
	private void refreshCustomerTable() throws DAOException {
		customersTableModel = new CouponTableModel(getTable(ClientType.CUSTOMER), new String[] { "ID", "Name", "Coupons" });
		customersTable.setModel(customersTableModel);	
	}
	// (Re)Loads companies table
	private void refreshCompanyTable() throws DAOException {
		companyTableModel = new CouponTableModel(getTable(ClientType.COMPANY), new String[] { "ID", "Name", "Email", "Coupons" });
		companyTable.setModel(companyTableModel);
	}

	// Delete selected customer
	private void deleteCustomer() {
		// Ignore if no row is selected
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

	// Edit selected customer
	private void editCustomer() {
		// Ignore if no row is selected
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
			dialog.addWindowListener(new DialogListener(ClientType.CUSTOMER));
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
		dialog.addWindowListener(new DialogListener(ClientType.CUSTOMER));
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
		dialog.addWindowListener(new DialogListener(ClientType.COMPANY));
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
			dialog.addWindowListener(new DialogListener(ClientType.COMPANY));
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
		} catch (DAOException | IOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
		}
	}
}
