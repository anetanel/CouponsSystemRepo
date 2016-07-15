package com.netanel.coupons.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.facades.CompanyFacade;
import com.netanel.coupons.facades.CouponClientFacade;
import com.netanel.coupons.facades.CustomerFacade;

import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class TopControlPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel clientPanel;

	/**
	 * Create the frame.
	 * @throws DAOException 
	 */
	public TopControlPanel(CouponClientFacade client) throws DAOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnActions = new JMenu("Actions");
		menuBar.add(mnActions);

		if (client instanceof AdminFacade) {
			clientPanel = new AdminCtrlPanel((AdminFacade)client);
		} else if (client instanceof CompanyFacade) {
			clientPanel = new CompanyCtrlPanel((CompanyFacade) client);
		} else if (client instanceof CustomerFacade) {
			clientPanel = new CustomerCtrlPanel((CustomerFacade) client);
		}
		setContentPane(clientPanel);
		pack();
	}

}
