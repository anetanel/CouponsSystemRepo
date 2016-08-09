package com.netanel.coupons.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.jbeans.Customer;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JPasswordField;

/** 
 * Edit Customer Dialog
 */
public class EditCustomerDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField nameTxtFld;
	private JPasswordField passTxtFld;
	private JLabel lblPassword;
	private AdminFacade admin;
	private Customer customer;


	/**
	 * Create the Edit Customer dialog.
	 * @param owner a {@code JFrame} that owns this dialog (for modality).
	 * @param modal a {@code boolean} value. If {@code true} - the dialog will be modal, otherwise it will not. 
	 * @param admin a {@code AdminFacade} object.
	 * @param customer the {@code Customer} object to be edited.
	 */
	public EditCustomerDialog(Frame owner, boolean modal, AdminFacade admin, Customer customer) {
		super(owner, modal);
		this.admin = admin;
		this.customer = customer;

		// Dialog settings
		setTitle("Edit Customer");
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
			nameTxtFld = new JTextField(customer.getCustName());
			contentPanel.add(nameTxtFld);
			nameTxtFld.setColumns(10);
		}

		{
			lblPassword = new JLabel("Password");
			lblPassword.setEnabled(false);
			contentPanel.add(lblPassword);
		}
		{
			passTxtFld = new JPasswordField();
			passTxtFld.setEnabled(false);
			contentPanel.add(passTxtFld);
			passTxtFld.setColumns(10);
		}
		
		// Checkbox to enable password change
		{
			JCheckBox chckbxChagePassword = new JCheckBox("Chage Password");
			chckbxChagePassword.addChangeListener(new ChckbxChagePasswordChangeListener());
			contentPanel.add(chckbxChagePassword);
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
				updateCustomer();
				JOptionPane.showMessageDialog(null, "New customer details saved", "Detail Saved", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} catch (DAOException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	// Cancel button listener
	private class CancelButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	// Password Checkbox listener 
	private class ChckbxChagePasswordChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JCheckBox chkBx = (JCheckBox) e.getSource();
			if (chkBx.isSelected()) {
				passTxtFld.setEnabled(true);
				lblPassword.setEnabled(true);
			} else {
				passTxtFld.setText(null);
				passTxtFld.setEnabled(false);
				lblPassword.setEnabled(false);
			}
		}
	}
	
	// Update customer
	public void updateCustomer() throws DAOException {
		customer.setCustName(nameTxtFld.getText());
		if (lblPassword.isEnabled()) {
			customer.setPassword(passTxtFld.getPassword());
		}
		admin.updateCustomerDetails(customer);
	}
}

