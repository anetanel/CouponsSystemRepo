package com.netanel.coupons.gui.table;

import javax.swing.JTable;

/**
 * A collection of static functions for table handling
 */
public class TableHelper {
	/**
	 * Get the selected ID from a table 
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

	/**
	 * Get all the selected IDs from a table 
	 * @param table a {@code JTable} with an "ID" column.
	 * @return a {@code long[]} array of ID values of the objects in the selected rows.
	 */
	public static long[] getAllSelectedIdsFromTable(JTable table) {
		long[] idsArr = new long[table.getSelectedRowCount()];
		int[] rows = table.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				if (table.getColumnName(j).equals("ID")) {
					idsArr[i] = ((long) table.getValueAt(rows[i], j));
				}
			}
		}
		return idsArr;
	}
}
