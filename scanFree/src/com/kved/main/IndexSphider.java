package com.kved.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.kved.BusinessObjectMysql;


public class IndexSphider {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		final String host="localhost";
//		final String port="3306";
//		final String table="url";
//		final String password="webuser";
//		final String user="webuser";
		Properties props = new Properties();
		
		try {
			URL propUrl = ClassLoader.getSystemResource("ping.properties");
			props.load(propUrl.openStream());
			String host = props.getProperty("db.host");
			String port = props.getProperty("db.port");
			String table = props.getProperty("db.table");
			String user = props.getProperty("db.user");
			String password = props.getProperty("db.password");
			
			BusinessObjectMysql bo = new BusinessObjectMysql(host,port,table, user,password);
			List list = bo.getURLs();
			System.out.println("Nombre d'element: "+list.size());
			SimplifyContent sc=null;
			boolean go=true;
			for (int i=0;i<list.size();i++){
			//for (int i=0;i<3;i++){

				// Reprise du crawl � esiret.free.fr
				
				try {
					
					System.out.println("http://"+host+"/sphider/admin/spider.php?in=&out=&soption=level&maxlevel=2&reindex=1&url=http://"+(String)list.get(i));
					if (go){
					URL url = new URL("http://"+host+"/sphider/admin/spider.php?in=&out=&soption=level&maxlevel=2&reindex=1&url=http://"+(String)list.get(i));
					 try {
					     
					        // Read all the text returned by the server
					        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
					        String str;
					        while ((str = in.readLine()) != null) {
					            System.out.println(str);
					        }
					        in.close();
					        bo.updateIndex((String)list.get(i));
					    } catch (MalformedURLException e) {
					    	e.printStackTrace();
					    } catch (IOException e) {
					    	e.printStackTrace();
					    }
					    
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
