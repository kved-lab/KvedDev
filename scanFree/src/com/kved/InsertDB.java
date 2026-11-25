package com.kved;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class InsertDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Connection con=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con=DriverManager.getConnection("jdbc:mysql://192.168.0.1:3306/crawler?user=webuser&password=webuser");
		

		
		
		BufferedReader fichier = new BufferedReader(new FileReader("mots.txt"));   
        String str;
        str = fichier.readLine( );
        while (str != null){
             System.out.println(str);
             str = fichier.readLine( );
		
			Statement sta = con.createStatement();
			int set1 = sta.executeUpdate("INSERT INTO url (url) VALUES('"+str+"')");

        }
        
        } catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

}
