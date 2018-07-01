package com.zby;

import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import com.zby.entity.User;

public class MybatisMain {

	public static void main(String[] args) throws Exception {
		// InputStream inputStream = new
		// FileInputStream("src/main/resources/mybatis.xml");
		InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
		Properties props = new Properties();
		props.setProperty("***MySQL***", "mysql");
		XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, "default", props);
		Configuration configuration = parser.parse();
		SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = sqlSession.selectOne("selectByPrimaryKey", 1);
		System.out.println(user);
		sqlSession.close();
	}

}