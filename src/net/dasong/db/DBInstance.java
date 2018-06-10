package net.dasong.db;

import java.util.Properties;

public class DBInstance {
	private String userName;
	private String password;
	private String ip;
	private String port;
	private String sid;
	private String role;
	private String url;

	public DBInstance() {
	}

	public DBInstance(String userName, String password, String ip, String port, String sid, String role) {
		this.userName = userName;
		this.password = password;
		this.ip = ip;
		this.port = port;
		this.sid = sid;
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUrl() {
		// jdbc:oracle:thin:@172.16.26.116:1521:rac4
		url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;

		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Properties getDbProps() {
		Properties dbProps = new Properties();

		dbProps.put("user", userName);
		dbProps.put("password", password);

		if (!role.equals("")) {
			dbProps.put("internal_logon", role);
		}

		return dbProps;
	}
}
