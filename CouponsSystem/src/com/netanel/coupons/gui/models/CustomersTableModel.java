package com.netanel.coupons.gui.models;

import javax.swing.table.DefaultTableModel;

public class CustomersTableModel extends DefaultTableModel{

	private static final long serialVersionUID = 1L;

	public CustomersTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	@Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return Long.class;
            case 1:
                return String.class;
            case 2:
            	return Integer.class;
            default:
                return String.class;
        }
    }
}
