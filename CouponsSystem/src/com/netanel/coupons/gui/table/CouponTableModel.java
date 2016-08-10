package com.netanel.coupons.gui.table;

import javax.swing.table.DefaultTableModel;

public class CouponTableModel extends DefaultTableModel{

	private static final long serialVersionUID = 1L;

	public CouponTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	@Override
    public Class<?> getColumnClass(int column) {
		if (getRowCount() == 0) {
			return Object.class;
		}
		return getValueAt(0, column).getClass();
    }
}
