package com.netanel.coupons.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
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
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class LoginWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField loginNameTxtFld;
	private JPasswordField passwordField;
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
	 * Create the frame.
	 */
	public LoginWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginWindow.class.getResource("/images/icon.png")));
		setResizable(false);
		setTitle("Coupon System Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel CredPanel = new JPanel();
		CredPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		CredPanel.setBounds(7, 5, 419, 100);
		contentPane.add(CredPanel);
		CredPanel.setLayout(null);
		
		JLabel lblLoginName = new JLabel("Login Name:");
		lblLoginName.setBounds(67, 20, 84, 14);
		CredPanel.add(lblLoginName);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(66, 66, 85, 14);
		CredPanel.add(lblPassword);
		
		loginNameTxtFld = new JTextField();
		loginNameTxtFld.setBounds(151, 11, 140, 32);
		CredPanel.add(loginNameTxtFld);
		loginNameTxtFld.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(151, 57, 140, 32);
		CredPanel.add(passwordField);
		
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
		
		JRadioButton rdbtnAdmin = new JRadioButton("Admin");
		rdbtnAdmin.setActionCommand("ADMIN");
		IdentityPanel.add(rdbtnAdmin);
		buttonGroup.add(rdbtnAdmin);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new LoginActionListener());
		btnLogin.setBounds(286, 173, 89, 30);
		contentPane.add(btnLogin);
		contentPane.getRootPane().setDefaultButton(btnLogin);
	}
	
	private class LoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			login();
		}
	}
	
	private void login() {
		CouponClientFacade client = null;
		ClientType clientType = null;
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
			client = client.login(loginNameTxtFld.getText(), passwordField.getPassword(), clientType);
			JOptionPane.showMessageDialog(contentPane, "Welcome " + loginNameTxtFld.getText() + "!",
					"Login Successful", JOptionPane.INFORMATION_MESSAGE);
			TopControlPanel appFrame = new TopControlPanel(client);
			appFrame.setVisible(true);
			appFrame.setLocationRelativeTo(contentPane);
			JFrame rootFrame = (JFrame) SwingUtilities.getRoot(contentPane);
			rootFrame.setVisible(false);
		} catch (LoginException | DAOException e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(), "Login Failed!",
					JOptionPane.WARNING_MESSAGE);
		}
	}
}
