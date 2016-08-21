package com.netanel.coupons.gui;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.CustomerFacade;
import com.netanel.coupons.gui.table.CouponTableModel;
import com.netanel.coupons.jbeans.Coupon;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;

/**
 * Customer control panel
 */
public class CustomerCtrlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable couponsTable;
	private CustomerFacade customerFcd;

	/**
	 * Create the Customer Control Panel
	 * @param client a {@code CustomerFacade} object of the logged in company.
	 * @throws DAOException
	 */
	public CustomerCtrlPanel(CustomerFacade client) throws DAOException {
		this.customerFcd = client;
		setName("Customer Control Panel: " + customerFcd.getCustName());

		setLayout(new BorderLayout(0, 0));
		
		// Buttons panel
		JPanel btnPanel = new JPanel();
		add(btnPanel, BorderLayout.NORTH);
		
		JButton btnBuyCoupons = new JButton("Buy Coupons");
		btnBuyCoupons.setIcon(new ImageIcon(CustomerCtrlPanel.class.getResource("/images/add.png")));
		btnBuyCoupons.addActionListener(new BtnBuyCouponsActionListener());
		btnPanel.add(btnBuyCoupons);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setIcon(new ImageIcon(CustomerCtrlPanel.class.getResource("/images/refresh.png")));
		btnRefresh.addActionListener(new BtnRefreshActionListener());
		btnPanel.add(btnRefresh);
		
		// Table panel
		JPanel tablePanel = new JPanel();
		add(tablePanel, BorderLayout.CENTER);
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		tablePanel.add(scrollPane);
		
		//Customer's Coupons Table
		couponsTable = new JTable(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		couponsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
	
	// Buy coupon button listener
	private class BtnBuyCouponsActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				buyCoupon();
			} catch (DAOException e1) {
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
	
	// Returns an ID-sorted two-dimensional array of customer's coupons
	// Used by table model.
	private Object[][] getCouponsTable() throws DAOException {
			Set<Coupon> coupons = customerFcd.getMyCoupons();
			Object[][] table = new Object[coupons.size()][];
			int cnt = 0;
			// Populate array with coupons details.
			// Skip the 5th element (Amount).
			for (Coupon coupon : coupons) {
				Object[] couponDetails = new Object[coupon.getDetails(40).length -1];
				int j = 0;
				for (int i = 0; i < coupon.getDetails(40).length; i++) {
					if (i == 4) {
						continue;
					}
					couponDetails[j] = coupon.getDetails(40)[i];
					j++;
				}
				table[cnt] = couponDetails;
				cnt++;
			}
			Arrays.sort(table, java.util.Comparator.comparingLong(a -> (Long) a[0]));
			return table;
	}
	
	// Buy coupon
	private void buyCoupon() throws DAOException {
		BuyCouponDialog dialog = new BuyCouponDialog((JFrame) SwingUtilities.getRoot(CustomerCtrlPanel.this), true,
				customerFcd);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(dialog.getParent());
		dialog.pack();
		dialog.setVisible(true);
		dialog.addWindowListener(new DialogListener());
		
	}
	
	// (Re)Loads coupons table 
	private void refreshCouponTable() throws DAOException {

		CouponTableModel couponTableModel = new CouponTableModel(getCouponsTable(), new String[] { "ID", "Title",
				"Start Date", "End Date", "Type", "Message", "Price", "Image" });
		couponsTable.setModel(couponTableModel);
	}
}

