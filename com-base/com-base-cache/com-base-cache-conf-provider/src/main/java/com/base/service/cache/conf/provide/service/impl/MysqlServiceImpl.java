package com.base.service.cache.conf.provide.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.service.cache.conf.api.service.DbService;
import com.base.service.cache.conf.provide.utils.JdbcUtil;

public class MysqlServiceImpl implements DbService {

	private Connection con;

	public List<Map<String, Object>> execute(String sql) {
		List<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
		Statement stm = null;
		ResultSet rset = null;
		try {
			stm = con.createStatement();
			rset = stm.executeQuery(sql);
			ResultSetMetaData metaData = rset.getMetaData();
			while (rset.next()) {
				arr.add(getValue(rset, metaData));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (rset != null) {
				try {
					rset.close();
					rset = null;
				} catch (SQLException e) {
				}
			}
			if (stm != null) {
				try {
					stm.close();
					stm = null;
				} catch (SQLException e) {
				}
			}
		}
		return arr;
	}

	@Override
	public void close() {
		JdbcUtil.closeConnection(con);
	}

	@Override
	public void init(String driverName, String url, String username, String password) {
		this.con = JdbcUtil.createConnection(driverName, url, username, password);
	}
	
	private Map<String, Object> getValue(ResultSet rset, ResultSetMetaData metaData) {
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
				m.put(metaData.getColumnName(i), rset.getObject(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

}
