package com.zby;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestC3P0 {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = C3P0Util.getConnection();
			ps = conn.prepareStatement("insert into demo(name,age,salary) values('C3P0',25,999.99)");
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			C3P0Util.release(conn, ps);
		}
	}
}
