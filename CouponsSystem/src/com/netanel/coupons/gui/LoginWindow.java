package com.netanel.coupons.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.exception.LoginException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.facades.ClientType;
import com.netanel.coupons.facades.CompanyFacade;
import com.netanel.coupons.facades.CouponClientFacade;
import com.netanel.coupons.facades.CustomerFacade;

import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class LoginWindow extends JFrame {

	//
	// Attributes
	//
	private static final long serialVersionUID = 1L;
	private JTextField loginNameTxtFld;
	private JPasswordField passwordField;
	private JRadioButton rdbtnAdmin;
	private final ButtonGroup buttonGroup = new ButtonGroup();

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
					LoginWindow loginFrame = new LoginWindow();
					loginFrame.setVisible(true);
					loginFrame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the Login Window frame.
	 */
	public LoginWindow() {
		// Windows settings
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginWindow.class.getResource("/images/icon.png")));
		setResizable(false);
		setTitle("Coupon System Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 240);
		
		// Content Pane
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Credentials Panel
		JPanel credPanel = new JPanel();
		credPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		credPanel.setBounds(7, 5, 419, 100);
		contentPane.add(credPanel);
		credPanel.setLayout(null);
		
		JLabel lblLoginName = new JLabel("Login Name:");
		lblLoginName.setBounds(67, 20, 84, 14);
		credPanel.add(lblLoginName);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(66, 66, 85, 14);
		credPanel.add(lblPassword);
		
		loginNameTxtFld = new JTextField();
		loginNameTxtFld.setBounds(151, 11, 140, 32);
		credPanel.add(loginNameTxtFld);
		loginNameTxtFld.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(151, 57, 140, 32);
		credPanel.add(passwordField);
		
		// Identity Panel (Company, Customer, Admin)
		JPanel IdentityPanel = new JPanel();
		IdentityPanel.setBorder(new TitledBorder(null, "Login As", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		IdentityPanel.setBounds(7, 111, 419, 56);
		contentPane.add(IdentityPanel);
		
		JRadioButton rdbtnCompany = new JRadioButton("Company");
		rdbtnCompany.setSelected(true);
		rdbtnCompany.setActionCommand("COMPANY");
		IdentityPanel.add(rdbtnCompany);
		buttonGroup.add(rdbtnCompany);
		
		JRadioButton rdbtnCustomer = new JRadioButton("Customer");
		rdbtnCustomer.setActionCommand("CUSTOMER");
		IdentityPanel.add(rdbtnCustomer);
		buttonGroup.add(rdbtnCustomer);
		
		rdbtnAdmin = new JRadioButton("Admin");
		rdbtnAdmin.setActionCommand("ADMIN");
		IdentityPanel.add(rdbtnAdmin);
		buttonGroup.add(rdbtnAdmin);
		
		//Login Button
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new LoginActionListener());
		
		// Secret Admin Backdoor with "Alt+F"
		JButton btnSecret = new JButton("");
		btnSecret.setContentAreaFilled(false);
		btnSecret.setBorderPainted(false);
		btnSecret.setFocusable(false);
		btnSecret.setMnemonic('F');
		btnSecret.addActionListener(new BtnSecretActionListener());
		btnSecret.setBounds(0, 235, -23, -23);
		contentPane.add(btnSecret);
		btnLogin.setBounds(286, 173, 89, 30);
		contentPane.add(btnLogin);
		contentPane.getRootPane().setDefaultButton(btnLogin);
	}
	
	//
	// Listener Classes
	//
	
	// Login Button Listener
	private class LoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			login();
		}
	}
	// Admin Backdoor Listener
	private class BtnSecretActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			secretLogin();
		}
	}
	// Top Control Panel Listener
	// Listens to frame dispose
	private class topCtrlPnlListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			LoginWindow.this.setVisible(true);
		}
	}

	//
	// Functions
	//
	
	// Login function
	private void login() {
		CouponClientFacade client = null;
		ClientType clientType = null;
		String loginName = loginNameTxtFld.getText();
		char[] password = passwordField.getPassword();
		
		// Create facade based on the radio button selection
		switch (buttonGroup.getSelection().getActionCommand()) {
			case "COMPANY":
				client = new CompanyFacade();
				clientType = ClientType.COMPANY;
				break;
			case "CUSTOMER":
				client = new CustomerFacade();
				clientType = ClientType.CUSTOMER;
				break;
			case "ADMIN":
				client = new AdminFacade();
				clientType = ClientType.ADMIN;
				break;
		}
		try {
			// Try login
			client = client.login(loginName, password, clientType);
			JOptionPane.showMessageDialog(null, "Welcome " + loginName + "!",
					"Login Successful", JOptionPane.INFORMATION_MESSAGE);
			// Create top control panel and set visibility
			TopControlPanel topCtrlPnlFrame = new TopControlPanel(client);
			topCtrlPnlFrame.setVisible(true);
			topCtrlPnlFrame.setLocationRelativeTo(null);
			topCtrlPnlFrame.addWindowListener(new topCtrlPnlListener());
			// Hide login window
			LoginWindow.this.setVisible(false);
			passwordField.setText("");
		} catch (LoginException | DAOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Login Failed!",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	// Backdoor login function
	private void secretLogin(){
		rdbtnAdmin.setSelected(true);
		loginNameTxtFld.setText("admin");
		passwordField.setText("1234");
		login();
	}
}
