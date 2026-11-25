package com.kvedthreadconstant;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

public class Ping {
	final Thread tmain = Thread.currentThread();
	final ThreadGroup urlGroup = new ThreadGroup("urlGroup");
	int maxThread=0;
	String suffixe=null;

	private Ping(){
		Properties props=null;
		try {
			props = new Properties();
			URL propUrl = ClassLoader.getSystemResource("ping.properties");
			props.load(propUrl.openStream());
			System.out.println("suffixe = "+props.getProperty("suffixe"));
			System.setProperty("sun.net.client.defaultConnectTimeout", props.getProperty("sun.net.client.defaultConnectTimeout"));
			System.setProperty("sun.net.client.defaultReadTimeout", props.getProperty("sun.net.client.defaultReadTimeout"));
			//suffixe=(String)props.get("suffixe");
			suffixe=".neuf.fr";
			maxThread = Integer.decode((String)props.get("maxThread")).intValue();

			System.out.println("maxThread = "+maxThread);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void go() {

		AdressGenerator adrGen = new AdressGenerator(suffixe);

		for (int i=0;i<maxThread;i++){
			
			try {
				Thread.sleep(1);
				//System.out.println(urlb.toString()+" trouvé " +urlGroup.activeCount());
				new URLThread(tmain, urlGroup,adrGen);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// forme incorecte pour lecture url depuis base
//			StringBuffer urlorange = new StringBuffer();
//			urlorange.append("pagesperso-orange.fr/");
//			urlorange.append(data);

//			if (urlGroup.activeCount()>maxThread)
//			tmain.suspend();
//			try {
//			new URLThread(tmain, urlGroup,urlorange.toString().trim());
//			} catch (Exception e) {
//			e.printStackTrace();
//			}

//			urlneuf = new StringBuffer();
//			urlneuf.append(data);
//			urlneuf.append(".neuf.fr");
//			if (urlGroup.activeCount()>maxThread)
//			tmain.suspend();
//			try {
//			new URLThread(tmain, urlGroup,urlneuf.toString().trim());
//			} catch (Exception e) {
//			e.printStackTrace();
//			}

			// redirection vers page alice 404
//			StringBuffer urlalice = new StringBuffer();
//			urlalice.append(data);
//			urlalice.append(".chez-alice.fr");
//			if (urlGroup.activeCount()>maxThread)
//			tmain.suspend();
//			try {
//			new URLThread(tmain, urlGroup,urlalice.toString().trim());
//			} catch (Exception e) {
//			e.printStackTrace();
//			}

			// redirection vers par 410 Gone
//			StringBuffer urlchez = new StringBuffer();
//			urlchez.append("www.chez.com/");
//			urlchez.append(data);
//			if (urlGroup.activeCount()>maxThread)
//			tmain.suspend();
//			try {
//			new URLThread(tmain, urlGroup,urlchez.toString().trim());
//			} catch (Exception e) {
//			e.printStackTrace();
//			}

//			StringBuffer urlclub = new StringBuffer();
//			urlclub.append(data);
//			urlclub.append(".club.fr");
//			if (urlGroup.activeCount()>maxThread)
//			tmain.suspend();
//			try {
//			new URLThread(tmain, urlGroup,urlclub.toString().trim());
//			} catch (Exception e) {
//			e.printStackTrace();
//			}

			//		long stop=new Date().getTime();
			//	h++;

//			System.out.println((float)h*1000/(float)(stop-start+1));
		}
		
		System.out.println(maxThread + " threads lancés");
	}

	public static void main(String[] args) {
		Ping ping = new Ping();
		ping.go();
	}
}
