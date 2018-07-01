package com.zby.demo;

public class DBUtilTest {

	public static void main(String[] args) throws Exception {
		DBUtil dbUtil = new DBUtil();
		System.out.println(dbUtil.showDataBases());
		System.out.println(dbUtil.changeDb("zby"));
		System.out.println(dbUtil.showTables());
		System.out.println(dbUtil.showCreateTable("demo"));
	}

}
