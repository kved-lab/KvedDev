package com.kved;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class Ping {
	final Thread tmain = Thread.currentThread();
	final ThreadGroup urlGroup = new ThreadGroup("urlGroup");
	int maxThread=0;
	String suffixe=null;
	String motStr=null;
	BusinessObjectMysql bo =null;

	private Ping(){
		Properties props=null;
		
		try {
			java.security.Security.setProperty("networkaddress.cache.ttl", "3");
			java.security.Security.setProperty("networkaddress.cache.negative.ttl", "0");
            System.setProperty("networkaddress.cache.ttl", "3");
            System.setProperty("networkaddress.cache.negative.ttl", "0");
            
            System.setProperty("http.agent", "Mozilla/4.0 (compatible; MSIE 6.01; Windows NT 5.0)");
            System.setProperty("http.keepAlive", "true");
            System.setProperty("http.maxConnections", "10");
            
   		
			System.out.println(new Date());
			props = new Properties();
			URL propUrl = ClassLoader.getSystemResource("ping.properties");
			props.load(propUrl.openStream());
			System.out.println("suffixe = "+props.getProperty("suffixe"));
			System.setProperty("sun.net.client.defaultConnectTimeout", props.getProperty("sun.net.client.defaultConnectTimeout"));
			System.setProperty("sun.net.client.defaultReadTimeout", props.getProperty("sun.net.client.defaultReadTimeout"));
			String host = props.getProperty("db.host");
			String port = props.getProperty("db.port");
			String table = props.getProperty("db.table");
			String user = props.getProperty("db.user");
			String password = props.getProperty("db.password");
			System.out.println("Table: "+table);
			System.out.println("DB Host: "+host);
			bo = new BusinessObjectMysql(host,port,table, user, password);
			suffixe=(String)props.get("suffixe");
			motStr=(String)props.get("urlStart");
			if (motStr.equals("") ){
				
				motStr = bo.getURLbyId(bo.getMaxId(suffixe));

				if (motStr.indexOf(suffixe)>-1)
					motStr=motStr.substring(0, motStr.indexOf(suffixe));
				}
			System.out.println("Chaine de d�but = "+motStr);
			maxThread = Integer.decode((String)props.get("maxThread")).intValue();

			System.out.println("maxThread = "+maxThread);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void go() {

		AdressGenerator adrGen = new AdressGenerator(motStr);
		while (true){
			adrGen.incremente (adrGen.motLength-1);
			
				//System.out.println(urlGroup.activeCount() +" "+adrGen.getUrl());
				if (urlGroup.activeCount()>maxThread)
					tmain.suspend();
				try {
					//System.out.println(adrGen.getUrl1()+" trouv� " +urlGroup.activeCount());
					new URLThread(tmain, urlGroup,new StringBuffer().append(adrGen.getUrl()).append(suffixe).toString(), bo);
					//new URLThread(tmain, urlGroup,adrGen.getUrl()+"neuf.fr");
				} catch (Exception e) {
					e.printStackTrace();
				}

				// forme incorecte pour lecture url depuis base
//				StringBuffer urlorange = new StringBuffer();
//				urlorange.append("pagesperso-orange.fr/");
//				urlorange.append(data);

//				if (urlGroup.activeCount()>maxThread)
//				tmain.suspend();
//				try {
//				new URLThread(tmain, urlGroup,urlorange.toString().trim());
//				} catch (Exception e) {
//				e.printStackTrace();
//				}

//				urlneuf = new StringBuffer();
//				urlneuf.append(data);
//				urlneuf.append(".neuf.fr");
//				if (urlGroup.activeCount()>maxThread)
//					tmain.suspend();
//				try {
//					new URLThread(tmain, urlGroup,urlneuf.toString().trim());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}

				// redirection vers page alice 404
//				StringBuffer urlalice = new StringBuffer();
//				urlalice.append(data);
//				urlalice.append(".chez-alice.fr");
//				if (urlGroup.activeCount()>maxThread)
//				tmain.suspend();
//				try {
//				new URLThread(tmain, urlGroup,urlalice.toString().trim());
//				} catch (Exception e) {
//				e.printStackTrace();
//				}

				// redirection vers par 410 Gone
//				StringBuffer urlchez = new StringBuffer();
//				urlchez.append("www.chez.com/");
//				urlchez.append(data);
//				if (urlGroup.activeCount()>maxThread)
//				tmain.suspend();
//				try {
//				new URLThread(tmain, urlGroup,urlchez.toString().trim());
//				} catch (Exception e) {
//				e.printStackTrace();
//				}

//				StringBuffer urlclub = new StringBuffer();
//				urlclub.append(data);
//				urlclub.append(".club.fr");
//				if (urlGroup.activeCount()>maxThread)
//				tmain.suspend();
//				try {
//				new URLThread(tmain, urlGroup,urlclub.toString().trim());
//				} catch (Exception e) {
//				e.printStackTrace();
//				}
			}
		
	}

	public static void main(String[] args) {
		Ping ping = new Ping();
		ping.go();
	}
}
