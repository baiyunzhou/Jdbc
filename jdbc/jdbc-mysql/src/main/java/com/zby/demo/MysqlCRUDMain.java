package com.zby.demo;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MysqlCRUDMain {

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Properties jdbcInfo = new Properties();
		jdbcInfo.load(new FileInputStream("src/main/resources/jdbc-mysql.properties"));
		Connection connection = DriverManager.getConnection("jdbc:mysql://zby:3306", jdbcInfo);
		Statement statement = connection.createStatement();
		// 执行任何sql，有结果集返回，true；否则，false
		statement.execute("create database if not exists zby");
		statement.execute("use zby");
		statement.execute("create table if not exists user(id int primary key auto_increment comment '自增主键',"
				+ "name varchar(50) comment '姓名',age int comment '年龄');");
		ResultSet resultSet = statement.executeQuery("show tables");
		ResultSetMetaData tableMetaData = resultSet.getMetaData();
		int showTableResultColumn = tableMetaData.getColumnCount();
		while (resultSet.next()) {
			for (int i = 1; i < showTableResultColumn + 1; i++) {
				Object string = resultSet.getObject(i);
				System.out.println(string);
			}

		}
		int insert = statement.executeUpdate("insert into user(name,age) values('zby','25')");
		System.out.println(insert);
		ResultSet userResultSet = statement.executeQuery("select * from user");
		while (userResultSet.next()) {
			Object id = userResultSet.getObject(1);
			Object name = userResultSet.getObject(2);
			Object age = userResultSet.getObject(3);
			System.out.println(id);
			System.out.println(name);
			System.out.println(age);
		}
		PreparedStatement prepareStatement = connection.prepareStatement("");
		resultSet.close();
		connection.close();
		int transactionReadUncommitted = Connection.TRANSACTION_READ_UNCOMMITTED;
	}

	private static boolean execute(Statement statement, String sql) throws SQLException {
		return statement.execute(sql);
	}
}
