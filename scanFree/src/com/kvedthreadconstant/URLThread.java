package com.kvedthreadconstant;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;

import com.kved.main.SimplifyContent;


public class URLThread implements Runnable{
	private Thread _t;
	private Thread tcherche;
	private AdressGenerator adrGen=null;

	// Constructeur
	URLThread(Thread tcherche,ThreadGroup group, AdressGenerator adrGen) throws Exception {
		this.adrGen=adrGen;
		this.tcherche = tcherche;
		_t = new Thread(group,this); // instanciation du thread
		_t.start(); // demarrage du thread, la fonction run() est ici lancée
	}
	public void run() {


		while (true){
			try {
				Thread.sleep(100);
				adrGen.incremente(adrGen.motLength-1);
				String str = adrGen.getUrl1();
				
				MySpider arachnid = new MySpider(str);
				System.out.println("Thread "+_t.getName()+": "+str);
				PageInfo pi = arachnid.p;
				
				Map headerHttp = arachnid.getHeaderHttp();
				
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
						SimplifyContent sc= new SimplifyContent(new URL(str));
						content=sc.convert();
						//System.out.println("content "+content);
						//System.out.println("pi "+pi);
					}
				}catch(Throwable t) {

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
					pi.dump();
				}
				if (headerHttp.size()!=0){
					//System.out.println(str);
					BusinessObjectMysql.writeUrl( str.substring(str.indexOf("http://")+7,str.length()),  contentType,  desc,  mails,  responseCode,  keyword,  title, headerHttp.toString(), content);
				}
				

			} catch (IOException e) {
				// raté
//				System.out.println("raté: "+str);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

