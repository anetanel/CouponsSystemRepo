package com.netanel.coupons.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.TrayIcon.MessageType;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.UIManager;

import com.netanel.coupons.clients.AdminFacade;
import com.netanel.coupons.clients.ClientType;
import com.netanel.coupons.clients.CompanyFacade;
import com.netanel.coupons.clients.CouponClientFacade;
import com.netanel.coupons.clients.CustomerFacade;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.exception.LoginException;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Customer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class MainWindow {

	private JFrame frmCouponSystemGui;
	private JTextField nameTextField;
	private JPasswordField passField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private CardLayout cl_mainPanel = new CardLayout(0,0);
	private JPanel mainPanel = new JPanel();
	private ClientType clientType = ClientType.CUSTOMER;
	private CouponClientFacade client = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmCouponSystemGui.setVisible(true);
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
		frmCouponSystemGui = new JFrame();
		frmCouponSystemGui.setTitle("Coupon System");
		frmCouponSystemGui.setResizable(false);
		frmCouponSystemGui.setBounds(100, 100, 370, 200);
		frmCouponSystemGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCouponSystemGui.getContentPane().setLayout(new CardLayout(0, 0));
		
		
		
		frmCouponSystemGui.getContentPane().add(mainPanel);
		mainPanel.setLayout(cl_mainPanel);
		
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
		menuBar.add(mnActions);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new MntmLogoutActionListener());
		mnActions.add(mntmLogout);
	}
	
	private class LoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (clientType.equals(ClientType.CUSTOMER)) {
				client = new CustomerFacade(new Customer());
			} else if (clientType.equals(ClientType.COMPANY)){
				try {
					client = new CompanyFacade(nameTextField.getText());
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (clientType.equals(ClientType.ADMIN)) {
				client = new AdminFacade();
			}
			try {
				client.login(nameTextField.getText(), passField.getPassword(), clientType);
				JOptionPane.showMessageDialog(frmCouponSystemGui, "Login ok!");
				frmCouponSystemGui.setBounds(100, 100, 800, 600);
				cl_mainPanel.show(mainPanel, "card_app");
			} catch (LoginException e1) {
				JOptionPane.showMessageDialog(frmCouponSystemGui, e1.getMessage(),"Login Failed!", JOptionPane.WARNING_MESSAGE);
	
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
			cl_mainPanel.show(mainPanel, "card_login");
			frmCouponSystemGui.setBounds(100, 100, 370, 200);
		}
	}

}
