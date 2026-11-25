package com.kvedthreadconstant;

import java.util.Date;

public class AdressGenerator {

	final int[] ref = {32, 46, 97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122, 50,51,52,53,54,55,56,57,48,49}; 
	final int refLength=ref.length;
	// mot de depart initialisé à la construction par position dans ref
	int[] mot = {0,0,0,0,0,0,0,0,0,0};
	final int motLength=mot.length;
	char[] data = new char[motLength];
	long start = (new Date()).getTime();
	long now = 0;
	int count=0;

	String suffixe=null;

	AdressGenerator(String suffixe){
		this.suffixe =suffixe;
		int[] motChar = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
		String motStr=null;
		try {

			motStr = BusinessObjectMysql.getURLbyId(BusinessObjectMysql.getMaxId(), suffixe);

			if (motStr.indexOf(suffixe)>-1)
				motStr=motStr.substring(0, motStr.indexOf(suffixe));

			System.out.println("Chaine de début = "+motStr);

			//init motChar
			if (motStr.length()>motLength)
				throw new Exception("Erreur mot trop long");

			for (int i = 0; i<motStr.length(); i++)
				motChar[motChar.length-i-1] = motStr.charAt(motStr.length()-i-1); 

			// init mot
			try {
				for (int i=0;i<motChar.length;i++) {
					for (int j=0;j<ref.length;j++){
						if ((int)motChar[i]==ref[j]){
							mot[i]=j;
							break;
						}
						else {
							if (j==ref.length-1)
								throw new Exception("motChar incorrecte, caractere non prevu");
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i=0;i<motLength;i++)
			data[i]= (char)ref[mot[i]];

	}

	public synchronized void incremente(int i){
		//count++;
		//now=(new Date()).getTime();
		if (mot[i]!=refLength-1){
			mot[i]++;
		} else {
			mot[i]=1;
			incremente(i-1);
		}
		data[i]= (char)ref[mot[i]];

		if (!regleOK(data))
			incremente(motLength-1);
		//System.out.println((float)count*1000/(float)(now-start+1));
	
	}

	public String getUrl1() {
		return (new StringBuffer()).append("http://").append(new String(data).trim()).append(suffixe).toString();
	}

	public String getUrl2() {
		return (new StringBuffer()).append("http://").append(suffixe).append(new String(data).trim()).toString();
	}


	private boolean regleOK(char[] url){
		int deb=0;
		if 	(url[url.length-1]=='.')
			return false;
		if 	(url[0]=='.')
			return false;

		for (int i=0;i<url.length;i++)
			if (url[i]!=' '){
				deb =i;
				break;
			}

		for (int i=deb;i<url.length;i++)
			if (url[i]==' '){
				return false;
			}
		return true;
	}

}
