package com.netanel.coupons.gui.models;

import javax.swing.table.DefaultTableModel;

public class CompanyTableModel extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;

	public CompanyTableModel(Object[][] data, Object[] columnNames) {
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
                return String.class;
            case 3:
            	return Integer.class;
            default:
                return String.class;
        }
    }

	

}
