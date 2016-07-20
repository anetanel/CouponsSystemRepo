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

public class EditCustomerDialog extends JDialog {


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField nameTxtFld;
	private JPasswordField passTxtFld;
	private JLabel lblPassword;
	private AdminFacade admin;
	//private JTextField emailTxtField;
	private Customer customer;

	/**
	 * Create the dialog.
	 */
	public EditCustomerDialog(Frame owner, boolean modal, AdminFacade admin, Customer customer) {
		super(owner, modal);
		this.admin = admin;
		this.customer = customer;
		
		setTitle("Edit Customer");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
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
//		{
//			JLabel lblEmail = new JLabel("Email");
//			contentPanel.add(lblEmail);
//		}
//		{
//			emailTxtField = new JTextField(company.getEmail());
//			contentPanel.add(emailTxtField);
//			emailTxtField.setColumns(10);
//		}
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
		{
			JCheckBox chckbxChagePassword = new JCheckBox("Chage Password");
			chckbxChagePassword.addChangeListener(new ChckbxChagePasswordChangeListener());
			contentPanel.add(chckbxChagePassword);
		}
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
	
	private class OkButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				updateCustomer();
				JOptionPane.showMessageDialog(null, "New customer details saved",
						"Detail Saved", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} catch (DAOException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class CancelButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	private class ChckbxChagePasswordChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JCheckBox chkBx = (JCheckBox) e.getSource();
			if (chkBx.isSelected()) {
				passTxtFld.setEnabled(true);
				lblPassword.setEnabled(true);
			} else {
				passTxtFld.setEnabled(false);
				lblPassword.setEnabled(false);
			}
		}
	}
	
	public void updateCustomer() throws DAOException {
		customer.setCustName(nameTxtFld.getText());
		//customer.setEmail(emailTxtField.getText());
		if (lblPassword.isEnabled()) {
			customer.setPassword(passTxtFld.getPassword());
		}
		admin.updateCustomerDetails(customer);
	}
}

