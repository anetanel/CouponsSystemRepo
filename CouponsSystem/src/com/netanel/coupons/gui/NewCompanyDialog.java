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
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Coupon;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NewCompanyDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField nameTxtFld;
	private JTextField passTxtFld;
	private AdminFacade admin;
	private JTextField emailTxtField;

	/**
	 * Create the dialog.
	 */
	public NewCompanyDialog(Frame owner, boolean modal, AdminFacade admin) {
		super(owner, modal);
		this.admin = admin;
		setTitle("New Company");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JLabel lblCompanyName = new JLabel("Company Name");
			contentPanel.add(lblCompanyName);
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
			passTxtFld = new JTextField();
			contentPanel.add(passTxtFld);
			passTxtFld.setColumns(10);
		}
		{
			JLabel lblEmail = new JLabel("Email");
			contentPanel.add(lblEmail);
		}
		{
			emailTxtField = new JTextField();
			contentPanel.add(emailTxtField);
			emailTxtField.setColumns(10);
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
				createCompany();
				JOptionPane.showMessageDialog(null, "New Company '" + nameTxtFld.getText() + "' created.",
						"New Company created", JOptionPane.INFORMATION_MESSAGE);
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

	public void createCompany() throws DAOException{
		admin.createCompany(new Company(nameTxtFld.getText(), new Password(passTxtFld.getText().toCharArray()), emailTxtField.getText(), new HashSet<Coupon>()));
	}
}
