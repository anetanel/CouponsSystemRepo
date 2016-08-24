package com.netanel.coupons.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.CompanyFacade;
import com.netanel.coupons.jbeans.Coupon;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import com.netanel.coupons.jbeans.CouponType;
import javax.swing.SpinnerNumberModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

/**
 * Edit Coupon Dialog
 */
public class EditCouponDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField titleTxtFld, messageTxtFld;
	private CompanyFacade company;
	private JDatePickerImpl startDatePicker, endDatePicker;
	private JSpinner amountSpinner, priceSpinner;
	private JComboBox<CouponType> couponTypeComboBox;
	private Coupon coupon = null;
	private File sourceIcon; 
	private JLabel lblImageIcon;


	/**
	 * Create the Edit coupon dialog
	 * @param owner a {@code JFrame} that owns this dialog (for modality).
	 * @param modal a {@code boolean} value. If {@code true} - the dialog will be modal, otherwise it will not. 
	 * @param company a {@code CompanyFacade} object of the logged in company.
	 * @param coupon a {@code Coupon} object to be edited.
	 */
	public EditCouponDialog(Frame owner, boolean modal, CompanyFacade company, Coupon coupon) {
		super(owner, modal);
		this.company = company;
		this.coupon = coupon;
		
		// Dialog settings
		setTitle("Edit Coupon");
		setBounds(100, 100, 450, 300);
		
		// Content panel
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 5));
		{
			JLabel lblCouponTitle = new JLabel("Coupon Title");
			contentPanel.add(lblCouponTitle);
		}
		{
			titleTxtFld = new JTextField(coupon.getTitle());
			contentPanel.add(titleTxtFld);
			titleTxtFld.setColumns(10);
		}

		{
			JLabel lblCouponMessage = new JLabel("Coupon Description");
			contentPanel.add(lblCouponMessage);
		}
		{
			messageTxtFld = new JTextField(coupon.getMessage());
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
			model.setDate(coupon.getStartDate().getYear(), coupon.getStartDate().getMonthValue(), coupon.getStartDate().getDayOfMonth());
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
			model.setDate(coupon.getEndDate().getYear(), coupon.getEndDate().getMonthValue(), coupon.getEndDate().getDayOfMonth());
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
			amountSpinner.setModel(new SpinnerNumberModel(coupon.getAmount(), 0, 200, 1));
			contentPanel.add(amountSpinner);
		}
		
		{
			JLabel lblCouponType = new JLabel("Type");
			contentPanel.add(lblCouponType);
		}
		{
			couponTypeComboBox = new JComboBox<CouponType>();
			DefaultComboBoxModel<CouponType> comboModel = new DefaultComboBoxModel<CouponType>(CouponType.values());
			couponTypeComboBox.setModel(comboModel);
			for (int i=0; i < comboModel.getSize(); i++) {
				if (coupon.getType().equals(comboModel.getElementAt(i))) {
					couponTypeComboBox.setSelectedIndex(i);
					break;
				}
			}
			contentPanel.add(couponTypeComboBox);
		}
		
		{
			JLabel lblPrice = new JLabel("Price");
			contentPanel.add(lblPrice);
		}
		{
			priceSpinner = new JSpinner();
			priceSpinner.setModel(new SpinnerNumberModel(coupon.getPrice(), 0.0, 99999.0, 0.1));
			contentPanel.add(priceSpinner);
		}
		
		{
			JLabel lblImage = new JLabel("Upload Image");
			contentPanel.add(lblImage);
		}
		{
			JButton btnBrowse = new JButton("Browse...");
			btnBrowse.addActionListener(new BtnBrowseActionListener());
			contentPanel.add(btnBrowse);
		}
		
		{
			JLabel lblImage = new JLabel("Image");
			contentPanel.add(lblImage);
		}
		{
			lblImageIcon = new JLabel();
			lblImageIcon.setHorizontalAlignment(SwingConstants.CENTER);
			lblImageIcon.setIcon(new ImageIcon(coupon.getIcon().getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
			contentPanel.add(lblImageIcon);
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
				updateCoupon();
				JOptionPane.showMessageDialog(null, "Coupon '" + titleTxtFld.getText() + "' was updated.",
						"Coupon Updated", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} catch (DAOException | IOException e1) {
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
	
	// Browse button listener
	private class BtnBrowseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			sourceIcon = selectIcon();
			if (sourceIcon != null) {
				lblImageIcon.setIcon(new ImageIcon(new ImageIcon(sourceIcon.getAbsolutePath()).getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
			}
		}
	}

	// Date Formatter class
	// Needed for the Date Picker
	private class DateLabelFormatter extends AbstractFormatter {
	
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

	//
	// Functions
	//
	
	// Update coupon
	private void updateCoupon() throws DAOException, IOException{
		String destIconPath = coupon.getImage();
		Date startDate = (Date) startDatePicker.getModel().getValue();
		Date endDate = (Date) endDatePicker.getModel().getValue();
		LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (sourceIcon != null) {

				Path source = sourceIcon.toPath();
				destIconPath = "icons/" + company.getCompName() + "_" + titleTxtFld.getText() + sourceIcon.getName().substring(sourceIcon.getName().lastIndexOf("."));
				Path dest = FileSystems.getDefault().getPath(destIconPath);
				Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);

		}
		coupon = new Coupon(coupon.getId(), titleTxtFld.getText(), localStartDate, localEndDate, (int) amountSpinner.getValue(), (CouponType) couponTypeComboBox.getSelectedItem(), messageTxtFld.getText()
				, (double) priceSpinner.getValue(), destIconPath);
		company.updateCoupon(coupon);
	}
	
	// Opens a file chooser and returns a file to be used a icon
	private File selectIcon() {
		JFileChooser fc = new JFileChooser();
		FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
		fc.setFileFilter(imageFilter);
		int returnVal = fc.showOpenDialog(null);
		if (returnVal != 0) {
			return null;
		}
		File file = fc.getSelectedFile();
		// Limit file size to 256kb
		if (file.length() > 262144) {
			JOptionPane.showMessageDialog(null, "File size is limited to 256 KB!",
					"File too large", JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		return file;
	}
}
