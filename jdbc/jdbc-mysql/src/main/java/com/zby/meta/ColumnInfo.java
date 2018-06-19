package com.zby.meta;

/**
 * 列信息
 * 
 * @author zby
 *
 */
public class ColumnInfo {
	/**
	 * 列名称
	 */
	private String name;
	/**
	 * mysql列类型
	 */
	private String mysqlType;
	/**
	 * java类型
	 */
	private String javaType;
	/**
	 * 列注释
	 */
	private String comment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMysqlType() {
		return mysqlType;
	}

	public void setMysqlType(String mysqlType) {
		this.mysqlType = mysqlType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
