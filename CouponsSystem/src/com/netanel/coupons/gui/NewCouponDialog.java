package com.netanel.coupons.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.netanel.coupons.app.TestDate.DateLabelFormatter;
import com.netanel.coupons.crypt.Password;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.facades.CompanyFacade;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.Customer;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import com.netanel.coupons.jbeans.CouponType;
import javax.swing.SpinnerNumberModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class NewCouponDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField titleTxtFld, messageTxtFld;
	private CompanyFacade company;
	private JDatePickerImpl startDatePicker, endDatePicker;
	private JSpinner amountSpinner;
	//private JTextField emailTxtField;

	/**
	 * Create the dialog.
	 */
	public NewCouponDialog(Frame owner, boolean modal, CompanyFacade company) {
		super(owner, modal);
		this.company = company;
		setTitle("New Coupon");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 5));
		{
			JLabel lblCouponTitle = new JLabel("Coupon Title");
			contentPanel.add(lblCouponTitle);
		}
		{
			titleTxtFld = new JTextField();
			contentPanel.add(titleTxtFld);
			titleTxtFld.setColumns(10);
		}

		{
			JLabel lblCouponMessage = new JLabel("Coupon Description");
			contentPanel.add(lblCouponMessage);
		}
		{
			messageTxtFld = new JTextField();
			contentPanel.add(messageTxtFld);
			messageTxtFld.setColumns(10);
		}
		
		{
			JLabel lblStartDate = new JLabel("Start Date");
			contentPanel.add(lblStartDate);
		}
		{
			UtilDateModel model = new UtilDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			model.setSelected(true);
			JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
			startDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
			contentPanel.add(startDatePicker);
		}
		
		{
			JLabel lblEndDate = new JLabel("Expiration Date");
			contentPanel.add(lblEndDate);
		}
		{
			UtilDateModel model = new UtilDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			model.setSelected(true);
			JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
			endDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
			contentPanel.add(endDatePicker);
		}
		
		{
			JLabel lblAmount = new JLabel("No. of Coupons");
			contentPanel.add(lblAmount);
		}
		{
			amountSpinner = new JSpinner();
			amountSpinner.setModel(new SpinnerNumberModel(0, 0, 200, 1));
			contentPanel.add(amountSpinner);
		}
		
		{
			JLabel lblCouponType = new JLabel("Type");
			contentPanel.add(lblCouponType);
		}
		{
			JComboBox<CouponType> couponTypeComboBox = new JComboBox<CouponType>();
			couponTypeComboBox.setModel(new DefaultComboBoxModel<CouponType>(CouponType.values()));
			contentPanel.add(couponTypeComboBox);
		}
		
		{
			JLabel lblImage = new JLabel("Upload Image");
			contentPanel.add(lblImage);
		}
		{
			JButton btnBrowse = new JButton("Browse...");
			contentPanel.add(btnBrowse);
		}
		
		{
			JLabel lblImage = new JLabel("Image");
			contentPanel.add(lblImage);
		}
		{
			JLabel lblImageIcon = new JLabel();
			lblImageIcon.setHorizontalAlignment(SwingConstants.CENTER);
			lblImageIcon.setIcon(new ImageIcon(NewCouponDialog.class.getResource("/images/icon.png")));
			contentPanel.add(lblImageIcon);
		}
//		{
//			JLabel lblEmail = new JLabel("Email");
//			contentPanel.add(lblEmail);
//		}
//		{
//			emailTxtField = new JTextField();
//			contentPanel.add(emailTxtField);
//			emailTxtField.setColumns(10);
//		}
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
				createCoupon();
				JOptionPane.showMessageDialog(null, "New Coupon '" + titleTxtFld.getText() + "' created.",
						"New Coupon created", JOptionPane.INFORMATION_MESSAGE);
			//	dispose();
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

	public void createCoupon() throws DAOException{
		Date startDate = (Date) startDatePicker.getModel().getValue();
		Date endDate = (Date) endDatePicker.getModel().getValue();
		
		LocalDate sd = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate ed = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		System.out.println(sd);
		System.out.println(ed);
//		Coupon coupon = null;
//		company.createCoupon(coupon);
	}
	
	public class DateLabelFormatter extends AbstractFormatter {

		private static final long serialVersionUID = 1L;
		private String datePattern = "dd.MM.yyyy";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	    }

	}
}
