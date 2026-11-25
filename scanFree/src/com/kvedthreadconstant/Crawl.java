package com.kvedthreadconstant;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class Crawl {
	private Connection con=null;
	private Statement sta=null;
	Crawl(){

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection("jdbc:mysql://192.168.0.1:3306/crawler?user=webuser&password=webuser");
			System.out.println(System.getProperty("sun.net.client.defaultConnectTimeout")); 
			System.out.println(System.getProperty("sun.net.client.defaultReadTimeout")); 
			
			System.setProperty("sun.net.client.defaultConnectTimeout", "500"); 
			System.setProperty("sun.net.client.defaultReadTimeout", "500"); 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void go(){
		
		//String str="www.tvmag.com/accueil.jspx";

		try {
			sta = con.createStatement();
			sta.executeQuery ("SELECT url,id FROM url where httpResponse is null and id>19784");
			ResultSet rs = sta.getResultSet ( );
			int count = 0;
			while (rs.next ( ) ) // loop through rows of result set
			{
				count ++;
				String str = rs.getString(1);
				int id = rs.getInt(2);


				updateBdd(str,id);



			}
			rs.close();
			sta.close();
			con.close();

		} catch (Exception e) {
			try {
				sta.close();
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public void updateBdd(String str, int id) throws Exception {
		long date = (new Date()).getTime();
		System.out.println("-------"+str);
		System.out.println(date);

		MySpider arachnid = new MySpider("http://"+str);
		PageInfo pi = arachnid.p;

		String headerHttp = arachnid.getHeaderHttp().toString();
		System.out.println(headerHttp);
		String mails="";
		String desc ="";
		String title = "";
		String keyword="";
		String contentType=arachnid.contentType;
		int responseCode=arachnid.responseCode;
		if ( pi!=null && pi.isValid()){
			if (pi.getMails() != null) {

				for (int i=0; i<pi.getMails().length; ++i) {
					mails+=pi.getMails()[i];
					if (i<(pi.getMails().length-1)) mails+=", ";
				}
			}
			desc = pi.getDescription();
			if (desc==null) desc="";
			title = pi.getTitle();
			if (title==null) title="";
			keyword=pi.getkeywords();
			if (keyword==null) keyword="";
			contentType=pi.getContentType();
			responseCode = pi.getResponseCode();
			
			pi.dump();

		} 
		Statement sta2 = con.createStatement();
		String sql ="update url set contentType='"+contentType+"', dateScan=CURDATE(),description='"+desc.replaceAll("'", "''")+"',emails='"+mails+"', httpResponse="+responseCode+", keyword='"+keyword.replaceAll("'", "''")+"', title='"+title.replaceAll("'", "''")+"', headerHttp='"+headerHttp.replaceAll("\'", " '").replaceAll("'", "''")+"' where id="+id;
		System.out.println(sql);
		try {
			sta2.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sta2.close();

	}
	
		
	
	
	public static void main(String[] args) {
		Crawl crawl = new Crawl();
		crawl.go();
	}
}