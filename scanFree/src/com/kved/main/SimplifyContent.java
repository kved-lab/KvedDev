package com.kved.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class SimplifyContent {
	public StringBuffer strFile = new StringBuffer();
	public SimplifyContent(String str){
		this.strFile=new StringBuffer(str);
	}
	public SimplifyContent(StringBuffer str){
		this.strFile=str;
	}
	public SimplifyContent(URL url){
		this.strFile = this.getCOntent2(url);
	}

	public String convert ()  {
		delTagAndContent(strFile,"script",0);
		delTagAndContent(strFile,"style",0);
		delTag(strFile, 0);
		return conversion(strFile.toString());
	}

	public String conversion (String s)  {
		String [] caractereSpecial = {
				"Ã©", "Ã¨", "Ã ",
				"&eacute;", "&egrave;", "&euml;", "&agrave;", "&acirc;",
				"&ucirc;", "&ugrave;", "&ecirc;", "&icirc;", "&ocirc;",
				"&ccedil;", "&iuml;", "&trade;", "&copy;",
				"&Eacute;", "&Egrave;", "&Euml;", "&Agrave;", "&Acirc;",
				"&Ucirc;", "&Ugrave;", "&Ecirc;", "&Icirc;", "&Ocirc;",
				"&Ccedil;", "&Iuml;", "&reg;", "&euro;",
				"&nbspc;", "&#39","&nbsp;","  ","\n\n"
		};
		String [] equivalent = {
				"é", "è", "à",
				"é", "è", "ë", "à", "â",
				"û", "ù", "ê", "î", "ô",
				"ç", "ï", "™", "©",
				"É", "È", "Ë", "À", "Â",
				"Û", "Ù", "Ê", "Î", "Ô",
				"Ç", "Ï", "®", "€",
				" ", "'","","",""
		};
		int length = caractereSpecial.length;
		for (int i=0; i<length; i++) 
			s = s.replaceAll(caractereSpecial[i], equivalent[i]);
		return s;
	}

	public  StringBuffer getCOntent2(URL baseUrl){
		
		StringBuffer res=new StringBuffer();
		BufferedReader fichier=null;
		try {
			fichier = new BufferedReader(
					new InputStreamReader(
							baseUrl.openStream()));
			String str;

			str = fichier.readLine( );
			res.append(str);
			while (str != null){
				str = fichier.readLine( );
				res.append("\n");
				if (str !=null) res.append(str);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (fichier!=null) fichier.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public void delTagAndContent(StringBuffer strFile,String tagName,int start){
		if (start>strFile.length()-2) return;
		int deb= strFile.indexOf("<"+tagName,start);
		int fin= strFile.indexOf("/>",deb)+2;
		//if (fin>deb) System.out.println("------"+strFile.substring(deb, fin));

		int fin2=deb+1;
		if (fin>deb && deb!=-1 
				&& fin!=-1 
				&& strFile.substring(deb, fin-1).indexOf('>')==-1){
			strFile.delete(deb, fin);
			fin2=deb;
		}
		if (deb!=-1 && fin!=-1&& fin2<strFile.length()) delTagAndContent(strFile, tagName,fin2);

		deb= strFile.indexOf("<"+tagName);
		fin= strFile.indexOf("</"+tagName+">")+tagName.length()+3;
		if (deb!=-1 && fin!=-1 && fin>deb){
			strFile.delete(deb, fin);
			delTagAndContent(strFile, tagName,deb);
		}
	}

	public void delTag(StringBuffer strFile,int start){
		if (start>strFile.length()-7) return;
		int deb= strFile.indexOf("<",start);
		int fin= strFile.indexOf(">",deb)+1;

		if (deb!=-1 && fin!=-1 && fin>deb && fin<strFile.length()){
			if (!strFile.substring(deb+1, deb+4).toLowerCase().startsWith("img") && !strFile.substring(deb+1, deb+3).toLowerCase().startsWith("a ")&& !strFile.substring(deb+1, deb+5).toLowerCase().startsWith("meta") && !strFile.substring(deb+1, deb+6).toLowerCase().startsWith("title") && !strFile.substring(deb+1, deb+7).toLowerCase().startsWith("/title")){ 
				strFile.delete(deb, fin);
				delTag(strFile, deb);
			}else {
				delTag(strFile, fin);
			}
		}
	}
}
