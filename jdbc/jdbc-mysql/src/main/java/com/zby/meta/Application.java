package com.zby.meta;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author zby
 * @date 2018年6月19日
 * @description 根据数据库表生成实体类
 */
public class Application {

	public static void main(String[] args) throws Exception {
		List<TableInfo> tableInfos = getTableInfos();
		for (TableInfo tableInfo : tableInfos) {
			// System.out.println(tableInfo);
			generatePo(tableInfo, "com.zby.po");
		}
	}

	public static List<TableInfo> getTableInfos() throws Exception {
		List<TableInfo> tableInfos = new ArrayList<>();
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://zby:3306/scsit?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true", "root",
				"123456");
		// 获取数据库元数据
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		// 获取数据库所有表
		ResultSet dbResultSet = databaseMetaData.getTables(null, "%", "%", new String[] { "TABLE" });
		while (dbResultSet.next()) {
			TableInfo tableInfo = new TableInfo();
			String tableName = dbResultSet.getString("TABLE_NAME");
			tableInfo.setName(StringUtil.switchToCamelCase(tableName));
			tableInfo.setComment(getTableComment(connection, tableName));
			ResultSet tbResultSet = databaseMetaData.getColumns(null, "%", tableName, "%"); // 查询表中的所有字段
			List<ColumnInfo> columnInfos = new ArrayList<>();
			tableInfo.setColumnInfos(columnInfos);
			while (tbResultSet.next()) {
				ColumnInfo columnInfo = new ColumnInfo();
				String columnName = tbResultSet.getString("COLUMN_NAME");
				columnInfo.setName(replaceColumn_(columnName));
				String typeName = tbResultSet.getString("TYPE_NAME");
				columnInfo.setMysqlType(typeName);
				columnInfo.setJavaType(convertToJavaType(typeName));
				if ("date".equalsIgnoreCase(typeName) || "datetime".equalsIgnoreCase(typeName)
						|| "datetime".equalsIgnoreCase(typeName) || "timestamp".equalsIgnoreCase(typeName)) {
					tableInfo.setHasDate(true);
				}
				if ("decimal".equalsIgnoreCase(typeName)) {
					tableInfo.setHasBigDecimal(true);
				}
				String remarks = tbResultSet.getString("REMARKS");
				columnInfo.setComment(remarks);
				columnInfos.add(columnInfo);

			}
			ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(null, "%", tableName);
			while (primaryKeys.next()) {
				tableInfo.setPrimaryKey(primaryKeys.getString("COLUMN_NAME"));
			}
			tableInfos.add(tableInfo);
		}
		return tableInfos;
	}

	private static String convertToJavaType(String mysqlType) {
		switch (mysqlType.toLowerCase()) {
		case "int":
		case "tinyint":
		case "smallint":
		case "int unsigned":
			return "Integer";
		case "bigint":
			return "Long";
		case "varchar":
		case "blob":
		case "longtext":
		case "text":
			return "String";
		case "float":
			return "Floar";
		case "double":
			return "Double";
		case "date":
		case "time":
		case "datetime":
		case "timestamp":
			return "Date";
		case "decimal":
			return "BigDecimal";
		default:
			throw new RuntimeException(mysqlType);
		}
	}

	private static String getTableComment(Connection connection, String tableName) throws Exception {
		PreparedStatement prepareStatement = connection.prepareStatement("show create table " + tableName);
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()) {
			String createTable = resultSet.getString(2);
			String[] split = createTable.split("ENGINE");
			if (null != split && split.length == 2) {
				String[] split2 = split[1].split("COMMENT='");
				if (null != split2 && split2.length == 2) {
					return split2[1].replace("'", "");
				}
			}
		}
		return null;
	}

	public static void generatePo(TableInfo tableInfo, String packageName) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("package " + packageName + ";");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("import java.io.Serializable;");
		sb.append("\r\n");
		sb.append("\r\n");
		if (tableInfo.isHasDate()) {
			sb.append("import java.util.Date;");
			sb.append("\r\n");
			sb.append("\r\n");
		}
		if (tableInfo.isHasBigDecimal()) {
			sb.append("import java.math.BigDecimal;");
			sb.append("\r\n");
			sb.append("\r\n");
		}
		sb.append("/**");
		sb.append("\r\n");
		sb.append(" * ");
		sb.append(null == tableInfo.getComment() ? "" : tableInfo.getComment());
		sb.append("\r\n");
		sb.append(" **/");
		sb.append("\r\n");
		sb.append("public class " + StringUtil.upperFirst(tableInfo.getName()) + " implements Serializable {");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("\tprivate static final long serialVersionUID = 1L;");
		sb.append("\r\n");
		sb.append("\r\n");
		List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
		for (ColumnInfo columnInfo : columnInfos) {
			sb.append("\t/**");
			sb.append("\r\n");
			sb.append("\t * ");
			sb.append(null == columnInfo.getComment() ? "" : columnInfo.getComment());
			sb.append("\r\n");
			sb.append("\t **/");
			sb.append("\r\n");
			sb.append("\tprivate " + columnInfo.getJavaType() + " " + columnInfo.getName() + ";");
			sb.append("\r\n");
			sb.append("\r\n");
		}
		for (ColumnInfo columnInfo : columnInfos) {
			sb.append("\tpublic " + columnInfo.getJavaType() + " get" + StringUtil.upperFirst(columnInfo.getName())
					+ "() {");
			sb.append("\r\n");
			sb.append("\t\t");
			sb.append("return " + columnInfo.getName() + ";");
			sb.append("\r\n");
			sb.append("\t" + "}");
			sb.append("\r\n");
			sb.append("\r\n");
			sb.append("\tpublic void set" + StringUtil.upperFirst(columnInfo.getName()) + "(" + columnInfo.getJavaType()
					+ " " + columnInfo.getName() + ") {");
			sb.append("\r\n");
			sb.append("\t\t");
			sb.append("this." + columnInfo.getName() + " = " + columnInfo.getName() + ";");
			sb.append("\r\n");
			sb.append("\t" + "}");
			sb.append("\r\n");
			sb.append("\r\n");
		}
		sb.append("}");
		File path = new File("G:\\workspace2\\JDBC\\src\\main\\java\\" + packageName.replace(".", "\\"));
		if (!path.exists()) {
			path.mkdirs();
		}
		File file = new File(path, StringUtil.upperFirst(tableInfo.getName()) + ".java");
		System.out.println(file.getAbsolutePath());
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(sb.toString());
		fileWriter.close();
	}

	private static String lowerFirst(String str) {
		str = str.substring(0, 1).toLowerCase() + str.substring(1);
		return str;
	}

	private static String replaceColumn_(String str) {
		String[] split = str.split("_");
		if (split.length < 2) {
			return lowerFirst(str);
		}
		String result = "";
		for (String string : split) {
			result += StringUtil.upperFirst(string);
		}
		return lowerFirst(result);
	}
}
