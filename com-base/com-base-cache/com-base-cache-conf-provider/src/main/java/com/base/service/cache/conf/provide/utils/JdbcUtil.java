package com.base.service.cache.conf.provide.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {

	public static Connection createConnection(String driverName, String url, String userName, String password) {
		Connection con = null;
		try {
			Class.forName(driverName);
			con = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException(e);
		}
		return con;
	}

	public static void closeConnection(Connection con) {
		try {
			if (null != con && !con.isClosed()) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
