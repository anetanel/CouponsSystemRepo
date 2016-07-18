package com.netanel.coupons.gui;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.CustomerFacade;
import com.netanel.coupons.gui.models.CouponTableModel;
import com.netanel.coupons.jbeans.Coupon;

public class CustomerCtrlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable couponsTable;
	private CustomerFacade customerFcd;
	/**
	 * Create the panel.
	 * @param client 
	 * @throws DAOException 
	 */
	public CustomerCtrlPanel(CustomerFacade client) throws DAOException {
		this.customerFcd = client;
		setName("Customer Control Panel: " + customerFcd.getCustName());

		setLayout(new BorderLayout(0, 0));
		
		JPanel btnPanel = new JPanel();
		add(btnPanel, BorderLayout.NORTH);
		
		JButton btnBuyCoupons = new JButton("Buy Coupons");
		btnPanel.add(btnBuyCoupons);
		
		JPanel tablePanel = new JPanel();
		add(tablePanel, BorderLayout.CENTER);
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		tablePanel.add(scrollPane);
		
		couponsTable = new JTable();
		couponsTable.setRowHeight(40);
		couponsTable.setAutoCreateRowSorter(true);
		CouponTableModel couponTableModel = new CouponTableModel(getCouponsTable(), 
											new String []{"ID","Title", "Start Date", "End Date", "Type", "Message", "Price", "Image"});
		couponsTable.setModel(couponTableModel);
		scrollPane.setViewportView(couponsTable);
	}

	private Object[][] getCouponsTable() throws DAOException {
			Set<Coupon> coupons = customerFcd.getAllCoupons();
			Object[][] table = new Object[coupons.size()][];
			int cnt = 0;
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
			return table;
	}
}

