package net.dasong.common;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dasong.db.DBInstance;

public class Utils {
	public static void extractDBHosts() {
		if (Constants.DB_LIST == null || Constants.DB_LIST.equals("")) {
			PropsReader.read();
		}

		Constants.DB_AL = new ArrayList<DBInstance>();

		Pattern pattern = Pattern.compile("(.+)/(.+)@(.+):(.+)/([^ .]+)( as sysdba)?");

		String[] dbList = Constants.DB_LIST.split(",");

		for (String db : dbList) {
			// System.out.println(db);

			Matcher matcher = pattern.matcher(db);

			if (matcher.matches() && matcher.groupCount() == 6) {
				// sys/sys@172.16.4.141:1521/ora11g as sysdba

				String userName = matcher.group(1);
				String password = matcher.group(2);
				String ip = matcher.group(3);
				String port = matcher.group(4);
				String sid = matcher.group(5);
				String role = matcher.group(6);
				// role = (role != null && !role.equals("")) ? "sysdba" :
				// "sysoper";

				role = (role != null && !role.equals("")) ? role.trim().toLowerCase().replace("as ", "") : "";

				DBInstance dbInstance = new DBInstance(userName, password, ip, port, sid, role);
				Constants.DB_AL.add(dbInstance);
			} else {
				System.err.println("数据库连接串不合法，参考格式：username/password@ip:1521/sid");
				System.exit(1);
			}
		}
	}

	public static void createDirs(String dirs) {
		File hostDir = new File(dirs);

		if (!hostDir.isDirectory()) {
			hostDir.mkdirs();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Utils.extractDBHosts();

		for (DBInstance dbInstance : Constants.DB_AL) {
			System.out.println(dbInstance.getUrl());
		}
	}

}
