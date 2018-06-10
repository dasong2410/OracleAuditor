package net.dasong.common;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.dasong.db.SqlBody;

public class SqlReader {
	public static void readSqlGroup() {
		Constants.SQL_GROUP_MAP = new LinkedHashMap<String, String>();

		File xmlDir = new File(Constants.SQL_FILEDIR);
		String[] xmlFilenames = xmlDir.list(new XMLFilenameFilter());

		for (String xmlFilename : xmlFilenames) {
			String groupName = xmlFilename.substring(xmlFilename.indexOf("-") + 1, xmlFilename.length() - 4)
					.toLowerCase();

			// System.out.println(groupName);

			Constants.SQL_GROUP_MAP.put(groupName, xmlFilename);
		}
	}

	public static void read() {
		// TODO Auto-generated method stub
		String title;
		String desc;
		String sql;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			LinkedHashMap<String, LinkedHashMap<String, SqlBody>> sqlMap = new LinkedHashMap<String, LinkedHashMap<String, SqlBody>>();

			DocumentBuilder db = dbf.newDocumentBuilder();

			Set<Entry<String, String>> entrySet = Constants.SQL_GROUP_MAP.entrySet();
			Iterator<Entry<String, String>> it = entrySet.iterator();

			while (it.hasNext()) {
				LinkedHashMap<String, SqlBody> sqlGroupMap = new LinkedHashMap<String, SqlBody>();

				Entry<String, String> entry = it.next();
				String key = entry.getKey();
				String val = entry.getValue();

				Document doc = db.parse(Constants.SQL_FILEDIR + "/" + val);

				NodeList nodeList = doc.getElementsByTagName("sql");
				int nodeCnt = nodeList.getLength();

				// 读取sql语句，并存储
				for (int i = 0; i < nodeCnt; i++) {
					title = nodeList.item(i).getAttributes().getNamedItem("title").getNodeValue();
					desc = nodeList.item(i).getAttributes().getNamedItem("desc").getNodeValue();
					sql = nodeList.item(i).getFirstChild().getNodeValue();

					SqlBody sqlBody = new SqlBody(title, desc, sql);

					sqlGroupMap.put(title, sqlBody);
					sqlMap.put(key, sqlGroupMap);
				}
			}

			Constants.SQL_MAP = sqlMap;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 载入参数
		PropsReader.read();
		SqlReader.read();

		Set<?> entrySet = Constants.SQL_MAP.entrySet();

		Iterator<?> it = entrySet.iterator();

		while (it.hasNext()) {
			Map.Entry<?, ?> entry = (Entry<?, ?>) it.next();

			System.out.println(entry.getKey());

			System.out.println(entry.getValue());
		}

		SqlReader.readSqlGroup();
	}

}
