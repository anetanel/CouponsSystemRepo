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
import com.netanel.coupons.exception.LoginException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

public class MainWindow {

	private JFrame frmCouponSystemGui;
	private JTextField customerNameTextField;
	private JPasswordField customerPassField;
	private JTextField companyNameTextField;
	private JPasswordField companyPassField;
	private JTextField adminNameTextField;
	private JPasswordField adminPassField;

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
		frmCouponSystemGui.setBounds(100, 100, 450, 200);
		frmCouponSystemGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCouponSystemGui.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		CardLayout cl = new CardLayout(0,0);
		frmCouponSystemGui.getContentPane().add(panel, "login");
		panel.setLayout(cl);
		
		JTabbedPane loginTabsPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(loginTabsPane, "name_961269142384174");
		loginTabsPane.setToolTipText("");
		
		JPanel customerLoginPanel = new JPanel();
		loginTabsPane.addTab("Customers Login", null, customerLoginPanel, null);
		customerLoginPanel.setLayout(null);
				
		JLabel customerNameLoginLabel = new JLabel("Customer Name:");
		customerNameLoginLabel.setBounds(29, 11, 89, 33);
		customerLoginPanel.add(customerNameLoginLabel);
		
		JLabel customerPassLabel = new JLabel("Password:");
		customerPassLabel.setBounds(29, 55, 89, 33);
		customerLoginPanel.add(customerPassLabel);
		
		customerNameTextField = new JTextField();
		customerNameTextField.setBounds(128, 11, 185, 33);
		customerLoginPanel.add(customerNameTextField);
		customerNameTextField.setColumns(10);
		
		customerPassField = new JPasswordField();
		customerPassField.setBounds(128, 55, 185, 33);
		customerLoginPanel.add(customerPassField);
		customerPassField.setColumns(10);
		
		JButton customerLoginBtn = new JButton("Login");
		customerLoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		customerLoginBtn.setBounds(224, 99, 89, 23);
		customerLoginPanel.add(customerLoginBtn);
		
		JPanel CompanyLoginPanel = new JPanel();
		loginTabsPane.addTab("Company Login", null, CompanyLoginPanel, null);
		CompanyLoginPanel.setLayout(null);
		
		JLabel companyNameLoginLabel = new JLabel("Company Name:");
		companyNameLoginLabel.setBounds(29, 11, 89, 33);
		CompanyLoginPanel.add(companyNameLoginLabel);
		
		JLabel companyPassLabel = new JLabel("Password:");
		companyPassLabel.setBounds(29, 55, 89, 33);
		CompanyLoginPanel.add(companyPassLabel);
		
		companyNameTextField = new JTextField();
		companyNameTextField.setColumns(10);
		companyNameTextField.setBounds(128, 11, 185, 33);
		CompanyLoginPanel.add(companyNameTextField);
		
		companyPassField = new JPasswordField();
		companyPassField.setColumns(10);
		companyPassField.setBounds(128, 55, 185, 33);
		CompanyLoginPanel.add(companyPassField);
		
		JButton companyLoginBtn = new JButton("Login");
		companyLoginBtn.setBounds(224, 99, 89, 23);
		CompanyLoginPanel.add(companyLoginBtn);
		
		JPanel adminLoginPanel = new JPanel();
		loginTabsPane.addTab("Admin Login", null, adminLoginPanel, null);
		adminLoginPanel.setLayout(null);
		
		JLabel adminNameLoginLabel = new JLabel("Admin Name:");
		adminNameLoginLabel.setBounds(29, 11, 89, 33);
		adminLoginPanel.add(adminNameLoginLabel);
		
		JLabel adminPassLabel = new JLabel("Password:");
		adminPassLabel.setBounds(29, 55, 89, 33);
		adminLoginPanel.add(adminPassLabel);
		
		adminNameTextField = new JTextField();
		adminNameTextField.setBounds(128, 11, 185, 33);
		adminNameTextField.setColumns(10);
		adminLoginPanel.add(adminNameTextField);
		
		adminPassField = new JPasswordField();
		adminPassField.setBounds(128, 55, 185, 33);
		adminPassField.setColumns(10);
		adminLoginPanel.add(adminPassField);
		
		JButton adminLoginBtn = new JButton("Login");
		
		adminLoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminFacade admin = new AdminFacade();
				try {
					admin.login(adminNameTextField.getText(), adminPassField.getPassword(), ClientType.ADMIN);
					JOptionPane.showMessageDialog(frmCouponSystemGui, "Login ok!");
					frmCouponSystemGui.setBounds(100, 100, 800, 600);
					cl.show(panel, "app");
				} catch (LoginException e1) {
					JOptionPane.showMessageDialog(frmCouponSystemGui, e1.getMessage(),"Login Failed!", JOptionPane.WARNING_MESSAGE);
		
				}
				
			}
		});
		adminLoginBtn.setBounds(224, 99, 89, 23);
		adminLoginPanel.add(adminLoginBtn);	
		
		JPanel appPanel = new JPanel();
		panel.add(appPanel, "app");
	}
}
