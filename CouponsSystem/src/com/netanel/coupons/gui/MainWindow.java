package com.netanel.coupons.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class MainWindow {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("");
		frame.getContentPane().add(tabbedPane, BorderLayout.NORTH);
		
		JPanel customerLoginPanel = new JPanel();
		tabbedPane.addTab("Customers Login", null, customerLoginPanel, null);
		customerLoginPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel loginLabel = new JLabel("Customer Name:");
		customerLoginPanel.add(loginLabel);
		
		textField = new JTextField();
		customerLoginPanel.add(textField);
		textField.setColumns(10);
		
		JLabel passLabel = new JLabel("Password:");
		customerLoginPanel.add(passLabel);
		
		textField_1 = new JTextField();
		customerLoginPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JPanel CompanyLoginPanel = new JPanel();
		tabbedPane.addTab("Company Login", null, CompanyLoginPanel, null);
		
		JPanel adminLoginPanel = new JPanel();
		tabbedPane.addTab("Admin Login", null, adminLoginPanel, null);
	}
}
