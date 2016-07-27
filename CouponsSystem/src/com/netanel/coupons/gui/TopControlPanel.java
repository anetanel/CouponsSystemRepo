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

public class TopControlPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel clientPanel;
	private JFrame loginWindow;

	/**
	 * Create the frame.
	 * @throws DAOException 
	 */
	public TopControlPanel(CouponClientFacade client, JFrame loginWindow) throws DAOException {
		this.loginWindow = loginWindow;
		setIconImage(Toolkit.getDefaultToolkit().getImage(TopControlPanel.class.getResource("/images/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		
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

	public JPanel getClientPanel() {
		return clientPanel;
	}
	private class MntmLogoutActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			logout();
		}
	}
	private class MntmExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			exit();
		}
	}
	private void logout() {
		dispose();
		loginWindow.setVisible(true);
	}
	private void exit() {
		CouponSystem sys = CouponSystem.getInstance();
		sys.stop();
	}
}
