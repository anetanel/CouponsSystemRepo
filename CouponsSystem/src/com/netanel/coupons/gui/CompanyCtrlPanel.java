package com.netanel.coupons.gui;

import javax.swing.JPanel;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.CompanyFacade;
import com.netanel.coupons.gui.models.CouponTableModel;
import com.netanel.coupons.jbeans.Coupon;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CompanyCtrlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable couponsTable;
	private CompanyFacade companyFcd;

	/**
	 * Create the panel.
	 * @param client
	 * @throws DAOException 
	 */
	public CompanyCtrlPanel(CompanyFacade client) throws DAOException {
		this.companyFcd = client;
		setName("Company Control Panel: " + companyFcd.getCompName());
		setLayout(new BorderLayout(0, 0));
		
		JPanel btnPanel = new JPanel();
		add(btnPanel, BorderLayout.NORTH);
		
		JButton btnNewCoupon = new JButton("New Coupon");
		btnNewCoupon.addActionListener(new BtnNewCouponActionListener());
		btnPanel.add(btnNewCoupon);
		
		JButton btnEditCoupon = new JButton("Edit Coupon");
		btnPanel.add(btnEditCoupon);
		
		JButton btnDeleteCoupon = new JButton("Delete Coupon");
		btnPanel.add(btnDeleteCoupon);
		
		JPanel tablePanel = new JPanel();
		add(tablePanel, BorderLayout.CENTER);
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		tablePanel.add(scrollPane);
		
		couponsTable = new JTable(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		couponsTable.setRowHeight(40);
		couponsTable.setAutoCreateRowSorter(true);
		CouponTableModel couponTableModel = new CouponTableModel(getCouponsTable(), 
											new String []{"ID","Title", "Start Date", "End Date", "Amount", "Type", "Message", "Price", "Image"});
		couponsTable.setModel(couponTableModel);
		scrollPane.setViewportView(couponsTable);

	}
	
	private Object[][] getCouponsTable() throws DAOException {
		Set<Coupon> coupons = companyFcd.getAllCoupons();
		Object[][] table = new Object[coupons.size()][];
		int cnt = 0;
		for (Coupon coupon : coupons) {
			table[cnt] = coupon.getDetails(40);
			cnt++;
		}
		return table;
	}
	private class BtnNewCouponActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			newCoupon();
		}
	}
	public void newCoupon() {
		NewCouponDialog dialog = new NewCouponDialog((JFrame) SwingUtilities.getRoot(CompanyCtrlPanel.this), true,
				companyFcd);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(dialog.getParent());
		dialog.pack();
		dialog.setVisible(true);
		//dialog.addWindowListener(new DialogListener(couponsTable));
		
	}
}
