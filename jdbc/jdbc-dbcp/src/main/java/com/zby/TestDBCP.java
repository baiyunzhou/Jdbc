package com.zby;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestDBCP {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBCPUtil.getConnection();
			ps = conn.prepareStatement("insert into demo(name,age,salary) values('DBCP',25,999.99)");
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.release(conn, ps);
		}
	}
}
