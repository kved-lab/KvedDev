package com.kved;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BusinessObjectMysql {

	private String urlFree=null;
	private String table=null;
	private String host=null;
	private Connection con=null;
	
	public BusinessObjectMysql(String host, String port, String table, String user, String password){
		this.table=table;
		this.host=host;
		urlFree="jdbc:mysql://"+host+":"+port+"/crawler?user="+user+"&password="+password;
	}
	
	
	public synchronized void writeUrl(String str, String contentType, String desc, String mails, int responseCode, String keyword, String title,String headerHttp, String content) throws SQLException{

		
		//Statement sta2=null;
		PreparedStatement sta=null;
		String sql="insert into "+table+" (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp,content) VALUES(?,?, CURDATE(),?,?, ?, ?, ?, ?,?)";
		//String sql="insert into url (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp) VALUES('"+str+"','"+contentType+"', CURDATE(),'"+desc.replaceAll("'", "''")+"','"+mails+"', "+responseCode+", '"+keyword.replaceAll("'", "''")+"', '"+title.replaceAll("'", "''")+"', '"+headerHttp.replaceAll("'", "''")+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			if (con!=null&&!con.isClosed()){}
				//toto
			else
				con=DriverManager.getConnection(urlFree);

			sta = con.prepareStatement(sql);
			if (str!=null) sta.setString(1, str); else sta.setString(1, ""); 
			if (contentType!=null) sta.setString(2, contentType); else sta.setString(2, "");
			if (desc!=null) sta.setString(3, desc); else sta.setString(3, "");
			if (mails!=null) sta.setString(4, mails); else sta.setString(4, "");
			sta.setInt(5, responseCode); 
			if (keyword!=null) sta.setString(6, keyword); else sta.setString(6, "");
			if (title!=null) sta.setString(7, title); else sta.setString(7, "");
			if (headerHttp!=null) sta.setString(8, headerHttp); else sta.setString(8, "");
			if (content!=null) sta.setString(9, content); else sta.setString(9, "");

			sta.executeUpdate();
			System.out.println(str + " Ecriture OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(" "+e.getCause());
			System.out.println(sql);
			if (sta!=null) sta.close();
			if (con!=null) con.close();
			e.printStackTrace();
		}
		if (sta==null) sta.close();
		if (con==null) con.close();
	}

	public int getMaxId(String suffixe) throws SQLException{
		int id = 0;
		Connection con=null;
		//Statement sta2=null;
		Statement sta=null;
		String sql="select max(id) from "+table+" where url like '%"+suffixe+"'";
		//String sql="insert into url (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp) VALUES('"+str+"','"+contentType+"', CURDATE(),'"+desc.replaceAll("'", "''")+"','"+mails+"', "+responseCode+", '"+keyword.replaceAll("'", "''")+"', '"+title.replaceAll("'", "''")+"', '"+headerHttp.replaceAll("'", "''")+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(urlFree);
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

	public String getURLbyId(int id) throws SQLException{
		String url = "";
		Connection con=null;
		//Statement sta2=null;
		PreparedStatement sta=null;
		String sql="select url from "+table+" where id = ?";
		//String sql="insert into url (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp) VALUES('"+str+"','"+contentType+"', CURDATE(),'"+desc.replaceAll("'", "''")+"','"+mails+"', "+responseCode+", '"+keyword.replaceAll("'", "''")+"', '"+title.replaceAll("'", "''")+"', '"+headerHttp.replaceAll("'", "''")+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(urlFree);
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
		return url;
	}

	public synchronized void updateUrl(String url, String content) throws SQLException{

		Connection con=null;
		//Statement sta2=null;
		PreparedStatement sta=null;
		String sql="update "+table+" set content=? where url=?";
		//String sql="insert into url (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp) VALUES('"+str+"','"+contentType+"', CURDATE(),'"+desc.replaceAll("'", "''")+"','"+mails+"', "+responseCode+", '"+keyword.replaceAll("'", "''")+"', '"+title.replaceAll("'", "''")+"', '"+headerHttp.replaceAll("'", "''")+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(urlFree);
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

	public List getURLsSansContent() throws SQLException{
		List list = new ArrayList();

		Connection con=null;
		//Statement sta2=null;
		PreparedStatement sta=null;
		String sql="select url from "+table+" where httpResponse=200 and content like '%</html>%'";
		//String sql="insert into url (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp) VALUES('"+str+"','"+contentType+"', CURDATE(),'"+desc.replaceAll("'", "''")+"','"+mails+"', "+responseCode+", '"+keyword.replaceAll("'", "''")+"', '"+title.replaceAll("'", "''")+"', '"+headerHttp.replaceAll("'", "''")+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(urlFree);
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
	public List getURLs() throws SQLException{
		List list = new ArrayList();

		Connection con=null;
		//Statement sta2=null;
		PreparedStatement sta=null;
		String sql="select url from url where isIndexed <> 1 order by id" ;
		//String sql="insert into url (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp) VALUES('"+str+"','"+contentType+"', CURDATE(),'"+desc.replaceAll("'", "''")+"','"+mails+"', "+responseCode+", '"+keyword.replaceAll("'", "''")+"', '"+title.replaceAll("'", "''")+"', '"+headerHttp.replaceAll("'", "''")+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(urlFree);
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
	public synchronized void updateIndex(String url) throws SQLException{

		Connection con=null;
		//Statement sta2=null;
		PreparedStatement sta=null;
		String sql="update url set isIndexed=?, dateIndex=now() where url=?";
		//String sql="insert into url (url,contentType,dateScan,description, emails,httpResponse,keyword,title, headerHttp) VALUES('"+str+"','"+contentType+"', CURDATE(),'"+desc.replaceAll("'", "''")+"','"+mails+"', "+responseCode+", '"+keyword.replaceAll("'", "''")+"', '"+title.replaceAll("'", "''")+"', '"+headerHttp.replaceAll("'", "''")+"')";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection(urlFree);
			sta = con.prepareStatement(sql);

			sta.setInt(1, 1);
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
}
