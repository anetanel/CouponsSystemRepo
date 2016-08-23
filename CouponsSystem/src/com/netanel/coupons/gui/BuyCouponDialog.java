package com.netanel.coupons.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.netanel.coupons.exception.CouponException;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.CustomerFacade;
import com.netanel.coupons.gui.table.CouponTableModel;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.gui.table.*;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Buy Coupon Dialog
 */
public class BuyCouponDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private CustomerFacade customer = null;
	private JTable couponsTable;

	
	/**
	 * Create the Buy Coupon dialog
	 * @param owner a {@code JFrame} that owns this dialog (for modality).
	 * @param modal a {@code boolean} value. If {@code true} - the dialog will be modal, otherwise it will not.
	 * @param customer the {@code Customer} object of the customer that attempts the purchase.
	 * @throws DAOException
	 */
	public BuyCouponDialog(Frame owner, boolean modal, CustomerFacade customer) throws DAOException {
		super(owner, modal);
		
		// Dialog settings
		setTitle("Buy Coupons");
		this.customer = customer;
		setBounds(100, 100, 450, 300);
		
		// Content panel
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		// Table panel
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				couponsTable = new JTable() {
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int row, int column) {
						return false;
					};
				};
				couponsTable.setRowHeight(40);
				couponsTable.setAutoCreateRowSorter(true);
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				couponsTable.setDefaultRenderer(Number.class, centerRenderer);
				scrollPane.setViewportView(couponsTable);
				refreshCouponTable();
			}
		}

		// Buttons panel
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton BuyButton = new JButton("Buy");
				BuyButton.addActionListener(new BuyButtonActionListener());
				BuyButton.setActionCommand("Buy");
				buttonPane.add(BuyButton);
				getRootPane().setDefaultButton(BuyButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new CancelButtonActionListener());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	//
	// Listener Classes
	//

	// Buy button listener
	private class BuyButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			buyCoupons();
		}
	}

	// Cancel button listener
	private class CancelButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	
	//
	// Functions
	//
	
	// (Re)Loads coupons table
	private void refreshCouponTable() throws DAOException {

		CouponTableModel couponTableModel = new CouponTableModel(getCouponsTable(), new String[] { "ID", "Title",
				"Start Date", "End Date", "Amount", "Type", "Message", "Price", "Image" });
		couponsTable.setModel(couponTableModel);
	}

	// Returns an ID-sorted two-dimensional array of all coupons
	// Used by table model.
	private Object[][] getCouponsTable() throws DAOException {
		Set<Coupon> coupons = customer.getAllCoupons();
		Object[][] table = new Object[coupons.size()][];
		int cnt = 0;
		for (Coupon coupon : coupons) {
			table[cnt] = coupon.getDetails(40);
			cnt++;
		}
		Arrays.sort(table, java.util.Comparator.comparingLong(a -> (Long) a[0]));
		return table;
	}

	// Buy selected coupons
	private void buyCoupons() {
		String boughtCouponsStr = "";
		Set<Coupon> boughtCoupons = new HashSet<>();
		long[] couponsIds = TableHelper.getAllSelectedIdsFromTable(couponsTable);
		// Attempt to buy each selected coupon
		for (long couponId : couponsIds) {
			try {
				customer.buyCoupon(couponId);
				boughtCoupons.add(customer.getCoupon(couponId));
			} catch (DAOException | CouponException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Attention!", JOptionPane.WARNING_MESSAGE);
			}
		}
		// Show bought coupons
		if (!boughtCoupons.isEmpty()) {
			for (Coupon coupon : boughtCoupons) {
				boughtCouponsStr += coupon.getTitle() + "\n";
			}
			JOptionPane.showMessageDialog(null, "Bought Coupon: \n" + boughtCouponsStr, "Coupon Bought",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
	}

}
