package com.zby.meta;

import java.util.List;

/**
 * 表信息
 * 
 * @author zby
 *
 */
public class TableInfo {
	/**
	 * 主键
	 */
	private String primaryKey;
	/**
	 * 表名
	 */
	private String name;
	/**
	 * 表注释
	 */
	private String comment;
	/**
	 * 是否有日期类型
	 */
	private boolean hasDate;
	/**
	 * 是否有bigdecimal类型
	 */
	private boolean hasBigDecimal;
	/**
	 * 列信息
	 */
	private List<ColumnInfo> columnInfos;

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isHasDate() {
		return hasDate;
	}

	public void setHasDate(boolean hasDate) {
		this.hasDate = hasDate;
	}

	public boolean isHasBigDecimal() {
		return hasBigDecimal;
	}

	public void setHasBigDecimal(boolean hasBigDecimal) {
		this.hasBigDecimal = hasBigDecimal;
	}

	public List<ColumnInfo> getColumnInfos() {
		return columnInfos;
	}

	public void setColumnInfos(List<ColumnInfo> columnInfos) {
		this.columnInfos = columnInfos;
	}

}
