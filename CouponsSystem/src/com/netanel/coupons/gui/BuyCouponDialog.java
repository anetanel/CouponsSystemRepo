package com.netanel.coupons.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
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
import com.netanel.coupons.gui.models.CouponTableModel;
import com.netanel.coupons.jbeans.Coupon;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BuyCouponDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private CustomerFacade customer = null;
	private JTable couponsTable;

	/**
	 * Create the dialog.
	 * 
	 * @throws DAOException
	 */
	public BuyCouponDialog(Frame owner, boolean modal, CustomerFacade customer) throws DAOException {
		super(owner, modal);
		setTitle("Buy Coupons");
		this.customer = customer;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
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
				refreshCouponTable();
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				couponsTable.setDefaultRenderer(Number.class, centerRenderer);
				scrollPane.setViewportView(couponsTable);
			}
		}

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

	private class BuyButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			buyCoupons();
		}
	}

	private class CancelButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	private void refreshCouponTable() throws DAOException {

		CouponTableModel couponTableModel = new CouponTableModel(getCouponsTable(), new String[] { "ID", "Title",
				"Start Date", "End Date", "Amount", "Type", "Message", "Price", "Image" });
		couponsTable.setModel(couponTableModel);
	}

	private Object[][] getCouponsTable() throws DAOException {
		Set<Coupon> coupons = customer.getAllCoupons();
		Object[][] table = new Object[coupons.size()][];
		int cnt = 0;
		for (Coupon coupon : coupons) {
			table[cnt] = coupon.getDetails(40);
			cnt++;
		}
		return table;
	}

	public void buyCoupons() {
		String boughtCouponsStr = "";
		Set<Coupon> boughtCoupons = new HashSet<>();
		long[] couponsIds = getSelectedIdFromTable();
		for (long couponId : couponsIds) {
			try {
				customer.buyCoupon(couponId);
				boughtCoupons.add(customer.getCoupon(couponId));
			} catch (DAOException | CouponException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Attention!", JOptionPane.WARNING_MESSAGE);
			}
		}
		if (!boughtCoupons.isEmpty()) {
			for (Coupon coupon : boughtCoupons) {
				boughtCouponsStr += coupon.getTitle() + "\n";
			}
			JOptionPane.showMessageDialog(null, "Bought Coupon: \n" + boughtCouponsStr, "Coupon Bought",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
	}

	private long[] getSelectedIdFromTable() {
		long[] idsArr = new long[couponsTable.getSelectedRowCount()];
		int[] rows = couponsTable.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < couponsTable.getColumnCount(); j++) {
				if (couponsTable.getColumnName(j).equals("ID")) {
					idsArr[i] = ((long) couponsTable.getValueAt(rows[i], j));
				}
			}
		}
		return idsArr;
	}
}
