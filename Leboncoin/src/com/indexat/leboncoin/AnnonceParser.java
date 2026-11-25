package com.indexat.leboncoin;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class AnnonceParser {

	protected int cpt=0;
	protected int maxcpt=30;
	StringBuffer result = new StringBuffer();

	public static void main(String[] args) throws IOException {

		AnnonceParser p = new AnnonceParser();
		String url = "http://www.leboncoin.fr/motos/offres/ile_de_france/paris/?f=a&th=1";
		String query = "cbf";

		System.out.println(p.report(url, query));

	}


	public String report(String url, String query) {
		//Validate.isTrue(args.length == 1, "usage: supply url to fetch");
		//String url = args[0];
		
		
		if (cpt==0)
			url = url.concat("&q="+query);



		//print("Fetching %s...", url);
		
		Document doc;
		try {
			doc = Jsoup.connect(url).get();

			//doc = new Cleaner(Whitelist.simpleText()).clean(doc);
			doc.outputSettings().escapeMode(EscapeMode.base);

			Elements lbc = doc.select("div[class=lbc]");
			Elements page = doc.select("li[class*=page]");

			for (int i=0;i<lbc.size();i++) {



				Elements titre = lbc.select("div[class*=title]");
				Elements date = lbc.select("div[class*=date]");
				Elements price = lbc.select("div[class*=price]");
				Elements placement = lbc.select("div[class*=placement]");
				Elements image = lbc.select("div[class*=image-and-nb]");
				


				Element titrei;
				Element datei;
				Element pricei;
				Element placementi;
				Element imagei;
				try {
					titrei = titre.get(i);
					datei = date.get(i);
					pricei = price.get(i);
					placementi = placement.get(i);
					imagei = image.get(i);
					
					System.out.println(titrei.text()+";"+datei.text()+";"+pricei.text()+";<a href=\""+lbc.get(i).parent().attr("href")+"\">détail</a><br>");
					result.append(titrei.text()+";"+datei.text()+";"+pricei.text()+";<a href=\""+lbc.get(i).parent().attr("href")+"\">détail</a><br>");
					

				} catch (Exception e) {


				}
			}

			cpt++;
			if (page.size()!=0 & lbc.size()!=0 & cpt<maxcpt)


				report(url.substring(0, url.lastIndexOf("?")).concat("?o="+cpt+"&q="+query), query);

		} catch (IOException e) {
			e.printStackTrace();
		}
if (result.length()!=0)
		return result.toString();
else
	return "No result";
		
	}

	private static void print(String msg, Object... args) {
		
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width-1) + ".";
		else
			return s;
	}



}
