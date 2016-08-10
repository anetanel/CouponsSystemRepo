package com.netanel.coupons.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.netanel.coupons.app.CouponSystem;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.facades.CompanyFacade;
import com.netanel.coupons.facades.CouponClientFacade;
import com.netanel.coupons.facades.CustomerFacade;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Toolkit;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

/**
 * Top control panel frame. Includes the menu bar and a panel for client information.
 */
public class TopControlPanel extends JFrame {

	//
	// Attributes
	//
	private static final long serialVersionUID = 1L;
	private JPanel clientPanel;

	
	/**
	 * Create the top control panel frame.
	 * @param client a {@code CouponClientFacade} of the logged in client.
	 * @throws DAOException
	 */
	public TopControlPanel(CouponClientFacade client) throws DAOException {
		// Window settings
		setIconImage(Toolkit.getDefaultToolkit().getImage(TopControlPanel.class.getResource("/images/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		
		// Set menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnActions = new JMenu("Menu");
		menuBar.add(mnActions);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new MntmLogoutActionListener());
		mnActions.add(mntmLogout);
		
		JSeparator separator = new JSeparator();
		mnActions.add(separator);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new MntmExitActionListener());
		mnActions.add(mntmExit);

		// Select the appropriate control panel to show, based on the facade
		if (client instanceof AdminFacade) {
			clientPanel = new AdminCtrlPanel((AdminFacade)client);
		} else if (client instanceof CompanyFacade) {
			clientPanel = new CompanyCtrlPanel((CompanyFacade) client);
		} else if (client instanceof CustomerFacade) {
			clientPanel = new CustomerCtrlPanel((CustomerFacade) client);
		}
		setContentPane(clientPanel);
		setTitle(clientPanel.getName());
		pack();
	}

	//
	// Listener Classes
	//
	
	// Logout menu item
	private class MntmLogoutActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	// Exit menu item
	private class MntmExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			exit();
		}
	}
	
	//
	// Functions
	//
	private void exit() {
		CouponSystem sys = CouponSystem.getInstance();
		sys.stop();
	}
}
