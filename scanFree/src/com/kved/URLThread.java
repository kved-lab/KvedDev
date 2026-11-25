package com.kved;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.kved.main.SimplifyContent;


public class URLThread implements Runnable{
	
	private static final Logger logger = Logger.getLogger(URLThread.class);
	private Thread _t;
	private Thread tcherche;
	private String str=null;
	private BusinessObjectMysql bo=null;

	// Constructeur
	public URLThread(Thread tcherche,ThreadGroup group, String str,BusinessObjectMysql bo) throws Exception {
		this.bo=bo;
		this.str=str;
		this.tcherche = tcherche;
		_t = new Thread(group,this); // instanciation du thread
		_t.start(); // demarrage du thread, la fonction run() est ici lanc�e
	}
	public void run() {
		//		try {
		//		Thread.sleep(10);
		//		} catch (InterruptedException e1) {
		//		e1.printStackTrace();
		//		}
		try {
			// lev�e d'excpetion si host non trouv�
			//InetAddress.getByName(str);
			// si host trouv�
			MySpider arachnid = new MySpider("http://"+str);
			PageInfo pi = arachnid.p;

			Map headerHttp = arachnid.getHeaderHttp();
			//System.out.println("D�but thread "+str);
			//System.out.println(headerHttp);
			String mails="";
			String desc ="";
			String title = "";
			String keyword="";
			String content="";
			String contentType=arachnid.contentType;
			int responseCode=arachnid.responseCode;

			try {
				if (responseCode==200){
					SimplifyContent sc= new SimplifyContent(new URL("http://"+str));
					content=sc.convert();
					//System.out.println("content "+content);
					//System.out.println("pi "+pi);
				}
			}catch(Throwable t) {
				logger.log(Level.DEBUG, t.getCause());
			}
			if (  pi!=null && pi.isValid()){
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
				System.out.println(new Date());
				pi.dump();
			}
			if (responseCode==200 ){
				//System.out.println(str);
				bo.writeUrl( str,  contentType,  desc,  mails,  responseCode,  keyword,  title, headerHttp.toString(), content);
			}

		} catch (UnknownHostException e) {
			logger.log(Level.DEBUG, e.getCause());
			//	rat�
		} catch (IOException e) {
			// rat�
			logger.log(Level.DEBUG, "raté: "+str);
		}
		catch (SQLException e) {
			logger.log(Level.DEBUG, e.getCause());
			e.printStackTrace();
		} finally {

			if (tcherche!=null) tcherche.resume();
			logger.log(Level.DEBUG,"fin thread "+str);
			_t.interrupt();
		}
	}
}

