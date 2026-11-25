package com.kvedthreadconstant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BusinessObjectMysql {

	private final static String urlMysql="jdbc:mysql://192.168.0.1:3306/crawler?user=webuser&password=webuser";

	public static synchronized void writeUrl(String str, String contentType, String desc, String mails, int responseCode, String keyword, String title,String headerHttp, String content) throws SQLException{

		Connection con=null;
		//Statement sta2=null;
		PreparedStatement sta=null;
		String sql="insert into urlNeuf (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp,content) VALUES(?,?, CURDATE(),?,?, ?, ?, ?, ?,?)";
		//String sql="insert into url (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp) VALUES('"+str+"','"+contentType+"', CURDATE(),'"+desc.replaceAll("'", "''")+"','"+mails+"', "+responseCode+", '"+keyword.replaceAll("'", "''")+"', '"+title.replaceAll("'", "''")+"', '"+headerHttp.replaceAll("'", "''")+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(BusinessObjectMysql.urlMysql);
			sta = con.prepareStatement(sql);
			sta.setString(1, str);
			sta.setString(2, contentType);
			sta.setString(3, desc);
			sta.setString(4, mails);
			sta.setInt(5, responseCode);
			sta.setString(6, keyword);
			sta.setString(7, title);
			sta.setString(8, headerHttp);
			sta.setString(9, content);

			sta.executeUpdate();
			System.out.println(str + " Ecriture OK");
		} catch (Exception e) {
			System.out.println(sql);
			if (sta!=null) sta.close();
			if (con!=null) con.close();
			e.printStackTrace();
		}
		if (sta==null) sta.close();
		if (con==null) con.close();
	}

	public static int getMaxId() throws SQLException{
		int id = 0;
		Connection con=null;
		//Statement sta2=null;
		Statement sta=null;
		String sql="select max(id) from urlNeuf";
		//String sql="insert into url (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp) VALUES('"+str+"','"+contentType+"', CURDATE(),'"+desc.replaceAll("'", "''")+"','"+mails+"', "+responseCode+", '"+keyword.replaceAll("'", "''")+"', '"+title.replaceAll("'", "''")+"', '"+headerHttp.replaceAll("'", "''")+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(BusinessObjectMysql.urlMysql);
			sta = con.createStatement();
			ResultSet result = sta.executeQuery(sql);

			if(result.next()){
				id = result.getInt(1);
			}

		} catch (Exception e) {
			System.out.println(sql);
			if (sta!=null) sta.close();
			if (con!=null) con.close();
			e.printStackTrace();
		}
		sta.close();
		con.close();
		return id;
	}

	public static String getURLbyId(int id, String suffixe) throws SQLException{
		String url = null;
		Connection con=null;
		//Statement sta2=null;
		PreparedStatement sta=null;
		String sql="select url from urlNeuf where id = ?";
		//String sql="insert into urlNeuf (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp) VALUES('"+str+"','"+contentType+"', CURDATE(),'"+desc.replaceAll("'", "''")+"','"+mails+"', "+responseCode+", '"+keyword.replaceAll("'", "''")+"', '"+title.replaceAll("'", "''")+"', '"+headerHttp.replaceAll("'", "''")+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(BusinessObjectMysql.urlMysql);
			sta = con.prepareStatement(sql);
			sta.setInt(1, id);
			ResultSet result = sta.executeQuery();

			if(result.next()){
				url = result.getString(1);
			}

		} catch (Exception e) {
			System.out.println(sql);
			if (sta!=null) sta.close();
			if (con!=null) con.close();
			e.printStackTrace();
		}
		sta.close();
		con.close();
		if (url==null) url="a";
		return url;
	}

	public static synchronized void updateUrl(String url, String content) throws SQLException{

		Connection con=null;
		//Statement sta2=null;
		PreparedStatement sta=null;
		String sql="update urlNeuf set content=? where url=?";
		//String sql="insert into url (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp) VALUES('"+str+"','"+contentType+"', CURDATE(),'"+desc.replaceAll("'", "''")+"','"+mails+"', "+responseCode+", '"+keyword.replaceAll("'", "''")+"', '"+title.replaceAll("'", "''")+"', '"+headerHttp.replaceAll("'", "''")+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(BusinessObjectMysql.urlMysql);
			sta = con.prepareStatement(sql);

			sta.setString(1, content);
			sta.setString(2, url);
			sta.executeUpdate();
			System.out.println(url + " update OK");
		} catch (Exception e) {
			System.out.println(sql);
			if (sta!=null) sta.close();
			if (con!=null) con.close();
			e.printStackTrace();
		}
		if (sta==null) sta.close();
		if (con==null) con.close();
	}

	public static List getURLsSansContent() throws SQLException{
		List list = new ArrayList();

		Connection con=null;
		//Statement sta2=null;
		PreparedStatement sta=null;
		String sql="select url from urlNeuf where httpResponse=200 and content like '%</html>%'";
		//String sql="insert into url (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp) VALUES('"+str+"','"+contentType+"', CURDATE(),'"+desc.replaceAll("'", "''")+"','"+mails+"', "+responseCode+", '"+keyword.replaceAll("'", "''")+"', '"+title.replaceAll("'", "''")+"', '"+headerHttp.replaceAll("'", "''")+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(BusinessObjectMysql.urlMysql);
			sta = con.prepareStatement(sql);
			ResultSet result = sta.executeQuery();

			while(result.next()){
				list.add(result.getString(1));
			}

		} catch (Exception e) {
			System.out.println(sql);
			if (sta!=null) sta.close();
			if (con!=null) con.close();
			e.printStackTrace();
		}
		sta.close();
		con.close();
		return list;
	}

}
