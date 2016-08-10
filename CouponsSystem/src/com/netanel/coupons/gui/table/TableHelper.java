package com.netanel.coupons.gui.table;

import javax.swing.JTable;

public class TableHelper {
	/**
	 * @param table a {@code JTable} with an "ID" column.
	 * @return a {@code long} ID value of the object in the selected row. returns -1 if no row is selected.
	 */
	public static long getSelectedIdFromTable(JTable table) {
		int row = table.getSelectedRow();
		for (int i = 0; i < table.getColumnCount(); i++) {
			if (table.getColumnName(i).equals("ID")) {
				return (long) table.getValueAt(row, i);
			}
		}
		return -1;
	}
}
