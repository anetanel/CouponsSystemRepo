package com.netanel.coupons.gui.models;

import java.time.LocalDate;

import javax.swing.table.DefaultTableModel;

import com.netanel.coupons.jbeans.CouponType;

public class CouponTableModel extends DefaultTableModel{

	private static final long serialVersionUID = 1L;

	public CouponTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	@Override
    public Class<?> getColumnClass(int column) {
		return getValueAt(0, column).getClass();
    }
}

//        switch (column) {
//            case 0:
//                return Long.class;
//            case 1:
//                return String.class;
//            case 2:
//                return LocalDate.class;
//            case 3:
//            	return LocalDate.class;
//            case 4:
//            	return CouponType.class;
//            case 5:
//            	return String.class;
//            case 6:
//            	return Double.class;
//            case 7:
//            	return String.class;
//            default:
//                return String.class;
//        }