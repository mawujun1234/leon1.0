package com.mawujun.repository.sql;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class RcdSet implements Iterable<Rcd>, Serializable {
	private static final long serialVersionUID = 4036642022369447596L;
	private ArrayList<Rcd> records = new ArrayList<Rcd>();
	private HashMap<String, Integer> columnMap = new HashMap<String, Integer>();
	private ArrayList<String> columns = new ArrayList<String>();
	
	public int getColumnIndex(String name)
	{
		return columnMap.get(name.toUpperCase());
	}

	protected RcdSet(ResultSet rs) {
		try {
			ResultSetMetaData meta = rs.getMetaData();
			int ct = meta.getColumnCount();
			for (int i = 1; i <= ct; i++) {
				String name = meta.getColumnLabel(i);
				name=name.toUpperCase();
				columnMap.put(name, i-1);
				columns.add(name);
			}
			while (rs.next()) {
				Object[] data = new Object[ct];

				for (int i = 1; i <= ct; i++) {
					data[i-1] = rs.getObject(i);
				}
				Rcd r = new Rcd(this,data);
				this.records.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public int size()
	{
		return records.size();
	}

	public Iterator<Rcd> iterator() {
		return records.iterator();
	}

	public Rcd get(int i) {
		return records.get(i);
	}

}
