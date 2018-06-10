package net.dasong.html;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import net.dasong.common.Constants;
import net.dasong.common.PropsReader;
import net.dasong.common.SqlReader;
import net.dasong.common.Utils;
import net.dasong.db.DBInstance;
import net.dasong.db.DbUtil;
import net.dasong.db.SqlBody;

public class HtmlWriter {
	public static void writeItem(String host, SqlBody sqlBody, ResultSet rs) {
		String line = null;

		String title = sqlBody.getTitle();
		String desc = sqlBody.getDesc();

		String htmlName = title.toLowerCase().replaceAll(" ", "_") + ".html";
		String hostDir = Constants.HTML_DIR + "/" + host;
		String htmlFile = hostDir + "/" + htmlName;

		Utils.createDirs(hostDir);

		try {

			BufferedReader br = new BufferedReader(new FileReader(Constants.HTML_DIR + "/module/item.html"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF8"), true);

			while ((line = br.readLine()) != null) {
				// 将占位符替换成对应html内容
				if (line.equals("PLACE_HOLDER_ITEM")) {

					// 获取列数
					ResultSetMetaData rsmd = rs.getMetaData();
					int colCnt = rsmd.getColumnCount();

					pw.println("          <tr>");

					for (int i = 1; i <= colCnt; i++) {
						String label = rsmd.getColumnLabel(i);

						pw.println("            <th>" + label + "</th>");
					}

					pw.println("          </tr>");

					while (rs.next()) {
						pw.println("          <tr>");

						for (int i = 1; i <= colCnt; i++) {
							String value = rs.getString(i);

							pw.println("            <td>" + value + "</td>");
						}

						pw.println("          </tr>");
					}
					DbUtil.closeRs(rs);

				} else if (line.equals("PLACE_HOLDER_ITEM_TITLE")) {
					pw.println(title + (desc.equals("") ? "" : "&nbsp;&nbsp;&nbsp;&nbsp;[" + desc + "]"));
				} else {
					pw.println(line);
				}
			}

			pw.close();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeItemList(String host) {
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(Constants.HTML_DIR + "/module/items.html"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(Constants.HTML_DIR + "/" + host + "/items.html"), "UTF8"), true);

			while ((line = br.readLine()) != null) {
				// 将占位符替换成对应html内容
				if (line.equals("PLACE_HOLDER_ITEM_LIST")) {
					for (String title : Constants.SQL_TITLE_AL) {
						// 每个检查项写一个html文件
						String htmlName = title.toLowerCase().replaceAll(" ", "_") + ".html";
						pw.println("<li><a href='" + htmlName + "' target='contentFrame'>" + title + "</a></li>");
					}
				} else if (line.equals("PLACE_HOLDER_ITEM_LIST_TITLE")) {
					pw.println("All items of " + host);
				} else {
					pw.println(line);
				}
			}

			pw.close();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeHostList() {
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(Constants.HTML_DIR + "/module/hosts.html"));
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream(Constants.HTML_DIR + "/hosts.html"), "UTF8"), true);

			while ((line = br.readLine()) != null) {
				// 将占位符替换成对应html内容
				if (line.equals("PLACE_HOLDER_HOST_LIST")) {
					for (DBInstance dbInstance : Constants.DB_AL) {
						pw.println("<li><a href='" + dbInstance.getIp() + "/items.html' target='itemsFrame'>"
								+ dbInstance.getIp() + "</a></li>");
					}
				} else {
					pw.println(line);
				}
			}

			pw.close();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// 载入参数
		PropsReader.read();
		SqlReader.read();

		HtmlWriter.writeItemList("test");
	}
}
