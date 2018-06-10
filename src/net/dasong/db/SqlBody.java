package net.dasong.db;

public class SqlBody {
	private String title;
	private String desc;
	private String sql;

	public SqlBody() {
	}

	public SqlBody(String title, String desc, String sql) {
		this.title = title;
		this.desc = desc;
		this.sql = sql;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

}
