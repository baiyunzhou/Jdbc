package com.zby.demo;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	private static final String DEFAULT_JDBC_PATH = "src/main/resources/jdbc-mysql.properties";
	private static final String DEFAULT_SQL_PATH = "src/main/resources/sql.properties";
	private static final String DRIVER_CLASS = "driverClass";
	private static final String URL = "url";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	private static final String SHOW_DATABASES = "showDb";
	private static final String SHOW_TABLES = "showTb";
	private static final String SHOW_CREATE_TABLES = "showCreateTb";
	private static final String USE_DATABASE = "useDb";
	private Properties jdbcInfo;
	private Properties sqlInfo;
	private Connection connection;
	private Statement statement;

	public DBUtil() throws Exception {
		this(DEFAULT_JDBC_PATH, DEFAULT_SQL_PATH);
	}

	public DBUtil(String jdbcInfoPath, String sqlInfoPath) throws Exception {
		jdbcInfo = new Properties();
		jdbcInfo.load(new FileInputStream(jdbcInfoPath));
		sqlInfo = new Properties();
		sqlInfo.load(new FileInputStream(sqlInfoPath));
		Class.forName(jdbcInfo.getProperty(DRIVER_CLASS));
		connection = DriverManager.getConnection(jdbcInfo.getProperty(URL), jdbcInfo.getProperty(USER),
				jdbcInfo.getProperty(PASSWORD));
		statement = connection.createStatement();
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public String showDataBases() throws SQLException {
		ResultSet resultSet = statement.executeQuery(sqlInfo.getProperty(SHOW_DATABASES));
		StringBuilder sb = new StringBuilder("显示所有数据库名称：【");
		while (resultSet.next()) {
			sb.append(resultSet.getString(1));
			sb.append(",");
		}
		sb.append("】");
		return sb.toString().replaceAll(",】", "】");
	}

	public String changeDb(String dbName) throws SQLException {
		statement.execute(sqlInfo.getProperty(USE_DATABASE).replace("${dbName}", dbName));
		StringBuilder sb = new StringBuilder("切换至数据库：【");
		sb.append(dbName);
		sb.append("】");
		return sb.toString();
	}

	public String showTables() throws SQLException {
		ResultSet resultSet = statement.executeQuery(sqlInfo.getProperty(SHOW_TABLES));
		StringBuilder sb = new StringBuilder("当前数据库所有表名：【");
		while (resultSet.next()) {
			sb.append(resultSet.getString(1));
			sb.append(",");
		}
		sb.append("】");
		return sb.toString().replaceAll(",】", "】");
	}

	public String showCreateTable(String tbName) throws SQLException {
		ResultSet resultSet = statement
				.executeQuery(sqlInfo.getProperty(SHOW_CREATE_TABLES).replace("${tbName}", tbName));
		StringBuilder sb = new StringBuilder("查看表【");
		sb.append(tbName);
		sb.append("】建表语句为：【");
		while (resultSet.next()) {
			sb.append(resultSet.getString(2));
		}
		sb.append(";】");
		return sb.toString();
	}
}
