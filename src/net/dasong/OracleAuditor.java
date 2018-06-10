package net.dasong;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.dasong.common.Constants;
import net.dasong.common.PropsReader;
import net.dasong.common.SqlReader;
import net.dasong.common.Utils;
import net.dasong.db.DBInstance;
import net.dasong.db.DbUtil;
import net.dasong.db.SqlBody;
import net.dasong.html.HtmlWriter;

public class OracleAuditor {
	static {
		// 载入参数、sql、提取数据库主机信息
		PropsReader.read();
		SqlReader.readSqlGroup();
		SqlReader.read();
		Utils.extractDBHosts();
	}

	public void usage() {
		System.out.println("java -jar oaudit.jar [all|groupName]\n  groupName:");

		Set<Entry<String, String>> entrySet = Constants.SQL_GROUP_MAP.entrySet();
		Iterator<Entry<String, String>> it = entrySet.iterator();

		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			System.out.println("    " + entry.getKey());
		}

		System.exit(0);
	}

	public void execCheckItem(LinkedHashMap<String, SqlBody> groupSqlHashMap, Connection connection, String ip) {
		Set<Entry<String, SqlBody>> sqlEntrySet = groupSqlHashMap.entrySet();
		Iterator<Entry<String, SqlBody>> sqlIt = sqlEntrySet.iterator();

		while (sqlIt.hasNext()) {
			Entry<String, SqlBody> sqlEntry = sqlIt.next();
			String title = sqlEntry.getKey();
			SqlBody sqlBody = sqlEntry.getValue();

			System.out.println("    Start: " + title);
			Constants.SQL_TITLE_AL.add(title);

			ResultSet rs = DbUtil.getResultSet(connection, sqlBody.getSql());

			if (rs != null) {
				HtmlWriter.writeItem(ip, sqlBody, rs);
			}

			System.out.println("    End  : " + title);
		}
	}

	public static void main(String[] args) throws IOException {
		OracleAuditor oa = new OracleAuditor();

		Constants.SQL_GROUP_IN = new String[] { "all" };

		if (args.length == 1) {
			String groupNameStr = args[0];

			// 参数可以是all或是其它sql组名，如果有组名不存在则报错，多个组名以英文逗号隔开
			if (!groupNameStr.startsWith("all")) {
				Constants.SQL_GROUP_IN = args[0].split(",");

				for (String groupName : Constants.SQL_GROUP_IN) {
					if (!Constants.SQL_GROUP_MAP.containsKey(groupName.trim())) {
						oa.usage();
					}
				}
			}
		} else {
			oa.usage();
		}

		System.out.println("Start...");

		for (DBInstance dbInstance : Constants.DB_AL) {
			// System.out.println(dbInstance.getUrl());

			Constants.SQL_TITLE_AL = new ArrayList<String>();

			System.out.println("  Start " + dbInstance.getIp());

			Connection connection = DbUtil.getConnection(dbInstance.getUrl(), dbInstance.getDbProps());

			for (String groupName : Constants.SQL_GROUP_IN) {
				if (groupName.equals("all")) {
					// 组名 是 all，则执行所有检查项，然后退出循环
					Set<Entry<String, LinkedHashMap<String, SqlBody>>> entrySet = Constants.SQL_MAP.entrySet();

					Iterator<Map.Entry<String, LinkedHashMap<String, SqlBody>>> it = entrySet.iterator();

					while (it.hasNext()) {
						Map.Entry<String, LinkedHashMap<String, SqlBody>> entry = it.next();

						// String gName = entry.getKey();
						LinkedHashMap<String, SqlBody> groupSqlHashMap = entry.getValue();
						oa.execCheckItem(groupSqlHashMap, connection, dbInstance.getIp());
					}

					break;
				} else {
					LinkedHashMap<String, SqlBody> groupSqlHashMap = Constants.SQL_MAP.get(groupName);
					oa.execCheckItem(groupSqlHashMap, connection, dbInstance.getIp());
				}
			}

			HtmlWriter.writeItemList(dbInstance.getIp());

			System.out.println("  End " + dbInstance.getIp());
		}

		HtmlWriter.writeHostList();

		System.out.println("End...");
	}
}
