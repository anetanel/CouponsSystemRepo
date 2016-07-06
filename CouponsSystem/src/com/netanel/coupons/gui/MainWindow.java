package com.netanel.coupons.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.UIManager;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.exception.LoginException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.facades.ClientType;
import com.netanel.coupons.facades.CompanyFacade;
import com.netanel.coupons.facades.CouponClientFacade;
import com.netanel.coupons.facades.CustomerFacade;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;

public class MainWindow {

	private JFrame frmCouponSystemLogin;
	private JTextField nameTextField;
	private JPasswordField passField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private CardLayout clMainPanel = new CardLayout(0, 0);
	private CardLayout clClientPanel = new CardLayout(0, 0);
	private JPanel mainPanel = new JPanel();
	private JPanel clientCardTopPanel = new JPanel();
	private ClientType clientType = ClientType.CUSTOMER;
	private CouponClientFacade client = null;
	private JMenuItem mntmLogout;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Disable connection pool logging to console
		System.setProperty("com.mchange.v2.log.MLog", "fallback");
		System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "WARNING");
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmCouponSystemLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCouponSystemLogin = new JFrame();
		frmCouponSystemLogin.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/icon.png")));
		frmCouponSystemLogin.setTitle("Coupon System Login");
		frmCouponSystemLogin.setResizable(false);
		frmCouponSystemLogin.setSize(370, 200);
		frmCouponSystemLogin.setLocationRelativeTo(null);
		frmCouponSystemLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frmCouponSystemLogin.getContentPane().add(mainPanel);
		mainPanel.setLayout(clMainPanel);

		JPanel loginPanel = new JPanel();
		mainPanel.add(loginPanel, "card_login");
		loginPanel.setLayout(null);

		JLabel NameLoginLabel = new JLabel("Login Name:");
		NameLoginLabel.setBounds(29, 11, 89, 33);
		loginPanel.add(NameLoginLabel);

		JLabel passLabel = new JLabel("Password:");
		passLabel.setBounds(29, 55, 89, 33);
		loginPanel.add(passLabel);

		nameTextField = new JTextField();
		nameTextField.addActionListener(new LoginActionListener());
		nameTextField.setBounds(128, 11, 185, 33);
		nameTextField.setColumns(10);
		loginPanel.add(nameTextField);

		passField = new JPasswordField();
		passField.addActionListener(new LoginActionListener());
		passField.setBounds(128, 55, 185, 33);
		passField.setColumns(10);
		loginPanel.add(passField);

		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new LoginActionListener());
		loginBtn.setBounds(224, 125, 89, 23);
		loginPanel.add(loginBtn);

		JRadioButton rdbtnCustomer = new JRadioButton("Customer");
		rdbtnCustomer.addActionListener(new RdbtnCustomerActionListener());
		rdbtnCustomer.setSelected(true);
		buttonGroup.add(rdbtnCustomer);
		rdbtnCustomer.setBounds(128, 95, 71, 23);
		loginPanel.add(rdbtnCustomer);

		JRadioButton rdbtnCompany = new JRadioButton("Company");
		rdbtnCompany.addActionListener(new RdbtnCompanyActionListener());
		buttonGroup.add(rdbtnCompany);
		rdbtnCompany.setBounds(204, 95, 71, 23);
		loginPanel.add(rdbtnCompany);

		JRadioButton rdbtnAdmin = new JRadioButton("Admin");
		rdbtnAdmin.addActionListener(new RdbtnAdminActionListener());
		buttonGroup.add(rdbtnAdmin);
		rdbtnAdmin.setBounds(277, 95, 71, 23);
		loginPanel.add(rdbtnAdmin);

		JLabel identityLabel = new JLabel("Identity:");
		identityLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		identityLabel.setBounds(29, 99, 63, 14);
		loginPanel.add(identityLabel);

		JPanel appPanel = new JPanel();
		mainPanel.add(appPanel, "card_app");
		appPanel.setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		appPanel.add(menuBar, BorderLayout.NORTH);

		JMenu mnActions = new JMenu("Actions");
		mnActions.setMnemonic('a');
		menuBar.add(mnActions);

		mntmLogout = new JMenuItem("Logout");
		mntmLogout.setHorizontalTextPosition(SwingConstants.CENTER);
		mntmLogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mntmLogout.addActionListener(new MntmLogoutActionListener());
		mnActions.add(mntmLogout);

		clientCardTopPanel = new JPanel();
		appPanel.add(clientCardTopPanel, BorderLayout.CENTER);
		clientCardTopPanel.setLayout(clClientPanel);

		JPanel adminPanel = new JPanel();
		clientCardTopPanel.add(adminPanel, "card_admin");
		adminPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblAdminControlPanel = new JLabel("Admin Control Panel");
		adminPanel.add(lblAdminControlPanel, BorderLayout.NORTH);
		
		JTabbedPane adminTabs = new JTabbedPane(JTabbedPane.TOP);
		adminPanel.add(adminTabs, BorderLayout.CENTER);
		
		JPanel adminCompanyTab = new JPanel();
		adminTabs.addTab("Companies", null, adminCompanyTab, null);
		
		JPanel adminCustomerTab = new JPanel();
		adminTabs.addTab("Customers", null, adminCustomerTab, null);
		
		JPanel adminCouponTab = new JPanel();
		adminTabs.addTab("Coupons", null, adminCouponTab, null);

		JPanel customerPanel = new JPanel();
		clientCardTopPanel.add(customerPanel, "card_customer");
		customerPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblCustomer = new JLabel("Customer");
		customerPanel.add(lblCustomer, BorderLayout.NORTH);

		JPanel companyPanel = new JPanel();
		clientCardTopPanel.add(companyPanel, "card_company");
		companyPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblCompany = new JLabel("Company");
		companyPanel.add(lblCompany, BorderLayout.NORTH);
	}

	private class LoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String clientCardStr = null;
			if (clientType.equals(ClientType.CUSTOMER)) {
				client = new CustomerFacade();
				clientCardStr = "card_customer";
			} else if (clientType.equals(ClientType.COMPANY)) {
				client = new CompanyFacade();
				clientCardStr = "card_company";
			} else if (clientType.equals(ClientType.ADMIN)) {
				client = new AdminFacade();
				clientCardStr = "card_admin";
			}
			try {
				client.login(nameTextField.getText(), passField.getPassword(), clientType);
				JOptionPane.showMessageDialog(frmCouponSystemLogin, "Welcome " + nameTextField.getText() + "!",
						"Login Successful", JOptionPane.INFORMATION_MESSAGE);
				frmCouponSystemLogin.setSize(800, 600);
				frmCouponSystemLogin.setLocationRelativeTo(null);
				frmCouponSystemLogin.setTitle("Coupon System: " + nameTextField.getText());
				clMainPanel.show(mainPanel, "card_app");
				clClientPanel.show(clientCardTopPanel, clientCardStr);

				mntmLogout.setText("Logout: " + nameTextField.getText());
			} catch (LoginException | DAOException e1) {
				JOptionPane.showMessageDialog(frmCouponSystemLogin, e1.getMessage(), "Login Failed!",
						JOptionPane.WARNING_MESSAGE);

			}
		}
	}

	private class RdbtnCompanyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			clientType = ClientType.COMPANY;
		}
	}

	private class RdbtnCustomerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			clientType = ClientType.CUSTOMER;
		}
	}

	private class RdbtnAdminActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			clientType = ClientType.ADMIN;
		}
	}

	private class MntmLogoutActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			client = null;
			passField.setText("");
			clMainPanel.show(mainPanel, "card_login");
			frmCouponSystemLogin.setSize(370, 200);
			frmCouponSystemLogin.setLocationRelativeTo(null);
			frmCouponSystemLogin.setTitle("Coupon System Login");
		}
	}

}
