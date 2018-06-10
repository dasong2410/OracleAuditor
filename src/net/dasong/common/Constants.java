package net.dasong.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import net.dasong.db.DBInstance;
import net.dasong.db.SqlBody;

public class Constants {

	// 程序根目录
	public static final String ROOT_DIR = System.getProperty("user.dir");

	// 配置文件目录
	public static final String CFG_DIR = ROOT_DIR + "/cfg";

	// 参数文件
	public static final String PARAM_FILENAME = CFG_DIR + "/params.properties";

	// sql文件
	public static final String SQL_FILENAME = CFG_DIR + "/sql.xml";

	public static final String SQL_FILEDIR = CFG_DIR + "/sql";

	public static ArrayList<String> SQL_TITLE_AL;

	public static LinkedHashMap<String, String> SQL_GROUP_MAP;
	public static String[] SQL_GROUP_IN;

	public static final String HTML_DIR = ROOT_DIR + "/ora_rpt";

	// 数据库连接串列表，英文逗号隔开
	public static String DB_LIST;

	public static ArrayList<DBInstance> DB_AL;

	// sql语句map
	public static LinkedHashMap<String, LinkedHashMap<String, SqlBody>> SQL_MAP;
}
