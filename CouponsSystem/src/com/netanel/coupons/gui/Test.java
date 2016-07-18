package com.netanel.coupons.gui;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Test {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
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
	public Test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setRowHeight(40);
		table.setModel(new MyModel(
			new Object[][] {
				{0, 0, new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("images/ibm/mf.png")).getImage().getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH))},
			},
			new String[] {
				"New column", "New column", "New column"			
			}
			
		));
		scrollPane.setViewportView(table);
	}
	class MyModel extends DefaultTableModel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public MyModel(Object[][] data, Object[] columnNames) {
			super(data, columnNames);
			// TODO Auto-generated constructor stub
		}

		@Override
	    public Class<?> getColumnClass(int column) {
			return getValueAt(0, column).getClass();
	    }
		
	}

}
