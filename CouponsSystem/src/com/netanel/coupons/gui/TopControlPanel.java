package com.netanel.coupons.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.netanel.coupons.exception.DAOException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class TopControlPanel extends JFrame {

	private JPanel adminPanel, companyPanel, customerPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TopControlPanel frame = new TopControlPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws DAOException 
	 */
	public TopControlPanel() throws DAOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnActions = new JMenu("Actions");
		menuBar.add(mnActions);

		adminPanel = new AdminCtrlPanel();
		setContentPane(adminPanel);
	}

}
