package com.netanel.coupons.gui;

import javax.swing.JPanel;

import com.netanel.coupons.exception.CouponException;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.CompanyFacade;
import com.netanel.coupons.gui.table.CouponTableModel;
import com.netanel.coupons.gui.table.TableHelper;
import com.netanel.coupons.jbeans.Coupon;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;

/**
 * Company control panel
 *
 */
public class CompanyCtrlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable couponsTable;
	private CompanyFacade companyFcd;


	/**
	 * Create the Company Control Panel
	 * @param client a {@code CompanyFacade} object of the logged in company.
	 * @throws DAOException
	 */
	public CompanyCtrlPanel(CompanyFacade client) throws DAOException {
		this.companyFcd = client;
		
		setName("Company Control Panel: " + companyFcd.getCompName());
		setLayout(new BorderLayout(0, 0));

		// Buttons panel
		JPanel btnPanel = new JPanel();
		add(btnPanel, BorderLayout.NORTH);

		JButton btnNewCoupon = new JButton("New Coupon");
		btnNewCoupon.setIcon(new ImageIcon(CompanyCtrlPanel.class.getResource("/images/add.png")));
		btnNewCoupon.addActionListener(new BtnNewCouponActionListener());
		btnPanel.add(btnNewCoupon);

		JButton btnEditCoupon = new JButton("Edit Coupon");
		btnEditCoupon.addActionListener(new BtnEditCouponActionListener());
		btnEditCoupon.setIcon(new ImageIcon(CompanyCtrlPanel.class.getResource("/images/edit.png")));
		btnPanel.add(btnEditCoupon);

		JButton btnDeleteCoupon = new JButton("Delete Coupon");
		btnDeleteCoupon.setIcon(new ImageIcon(CompanyCtrlPanel.class.getResource("/images/delete.png")));
		btnDeleteCoupon.addActionListener(new BtnDeleteCouponActionListener());
		btnPanel.add(btnDeleteCoupon);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setIcon(new ImageIcon(CompanyCtrlPanel.class.getResource("/images/refresh.png")));
		btnRefresh.addActionListener(new BtnRefreshActionListener());
		btnPanel.add(btnRefresh);

		// Table panel
		JPanel tablePanel = new JPanel();
		add(tablePanel, BorderLayout.CENTER);
		tablePanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		tablePanel.add(scrollPane);

		// Company's Coupons Table
		couponsTable = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		couponsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		couponsTable.addMouseListener(new TableMouseListener());
		couponsTable.setRowHeight(40);
		couponsTable.setAutoCreateRowSorter(true);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		couponsTable.setDefaultRenderer(Number.class, centerRenderer);
		scrollPane.setViewportView(couponsTable);
		refreshCouponTable();

	}

	//
	// Listener Classes
	//
	
	// New Coupon button listener
	private class BtnNewCouponActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			newCoupon();
		}
	}

	// Delete Coupon button listener
	private class BtnDeleteCouponActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				deleteCoupon();
			} catch (CouponException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// Edit Coupon button listener
	private class BtnEditCouponActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				editCoupon();
			} catch (DAOException | CouponException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// Refresh button listener
	private class BtnRefreshActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				refreshCouponTable();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// Table mouse listener
	// Listens to double click on table row
	private class TableMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				try {
					editCoupon();
				} catch (DAOException | CouponException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	// Dialog listener
	// Listens to frame dispose
	private class DialogListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			try {
				refreshCouponTable();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	//
	// Functions
	//
	
	// Returns an ID-sorted two-dimensional array of company's coupons
	// Used by table model.
	private Object[][] getCouponsTable() throws DAOException {
		Set<Coupon> coupons = companyFcd.getAllCoupons();
		Object[][] table = new Object[coupons.size()][];
		int cnt = 0;
		for (Coupon coupon : coupons) {
			table[cnt] = coupon.getDetails(40);
			cnt++;
		}
		Arrays.sort(table, java.util.Comparator.comparingLong(a -> (Long) a[0]));
		return table;
	}

	// New coupon
	private void newCoupon() {
		NewCouponDialog dialog = new NewCouponDialog((JFrame) SwingUtilities.getRoot(CompanyCtrlPanel.this), true,
				companyFcd);
		setDialogProperties(dialog);
	}

	// Edit coupon
	private void editCoupon() throws DAOException, CouponException {
		// Ignore if no row is selected
		if (couponsTable.getSelectedRow() == -1) {
			return;
		}
		EditCouponDialog dialog = new EditCouponDialog((JFrame) SwingUtilities.getRoot(CompanyCtrlPanel.this), true,
				companyFcd, companyFcd.getCoupon(TableHelper.getSelectedIdFromTable(couponsTable)));
		setDialogProperties(dialog);
	}

	// Set dialog properties
	private void setDialogProperties(JDialog dialog) {
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(dialog.getParent());
			dialog.pack();
			dialog.setVisible(true);
			dialog.addWindowListener(new DialogListener());
	}
	
	private void refreshCouponTable() throws DAOException {
		CouponTableModel couponTableModel = new CouponTableModel(getCouponsTable(), new String[] { "ID", "Title",
				"Start Date", "End Date", "Amount", "Type", "Message", "Price", "Image" });
		couponsTable.setModel(couponTableModel);
	}

	// Delete coupon
	private void deleteCoupon() throws CouponException {
		if (couponsTable.getSelectedRow() == -1) {
			return;
		}
		Coupon coupon = null;
		try {
			coupon = companyFcd.getCoupon(TableHelper.getSelectedIdFromTable(couponsTable));

			int delete = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to delete coupon '" + coupon.getTitle() + "'?", "Delete Coupon",
					JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE);
			if (delete == 0) {
				companyFcd.deleteCoupon(coupon);
				JOptionPane.showMessageDialog(null, "Coupon '" + coupon.getTitle() + "' deleted!",
						"Coupon deleted", JOptionPane.INFORMATION_MESSAGE);
				refreshCouponTable();
			}
		} catch (DAOException | IOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
}
