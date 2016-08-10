package com.netanel.coupons.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.netanel.coupons.crypt.Password;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.Customer;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

/**
 * New Customer Dialog
 */
public class NewCustomerDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField nameTxtFld;
	private JPasswordField passTxtFld;
	private AdminFacade admin;

	
	/**
	 * Create the New Customer dialog
	 * @param owner a {@code JFrame} that owns this dialog (for modality).
	 * @param modal a {@code boolean} value. If {@code true} - the dialog will be modal, otherwise it will not. 
	 * @param admin a {@code AdminFacade} object.
	 */
	public NewCustomerDialog(Frame owner, boolean modal, AdminFacade admin) {
		super(owner, modal);
		this.admin = admin;
		
		// Dialog settings
		setTitle("New Customer");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		
		// Content panel
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 5));
		{
			JLabel lblCustomerName = new JLabel("Customer Name");
			contentPanel.add(lblCustomerName);
		}
		{
			nameTxtFld = new JTextField();
			contentPanel.add(nameTxtFld);
			nameTxtFld.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			contentPanel.add(lblPassword);
		}
		{
			passTxtFld = new JPasswordField();
			contentPanel.add(passTxtFld);
			passTxtFld.setColumns(10);
		}
		
		// Buttons pane
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new OkButtonActionListener());
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new CancelButtonActionListener());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	//
	// Listener Classes
	//
	
	// OK button listener
	private class OkButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				createCustomer();
				JOptionPane.showMessageDialog(null, "New Customer '" + nameTxtFld.getText() + "' created.",
						"New Customer created", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} catch (DAOException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	// Cancel button listener
	private class CancelButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	//
	// Functions
	//
	
	// Create customer
	private void createCustomer() throws DAOException{
		admin.createCustomer(new Customer(nameTxtFld.getText(), new Password(passTxtFld.getPassword()), new HashSet<Coupon>()));
	}
}
