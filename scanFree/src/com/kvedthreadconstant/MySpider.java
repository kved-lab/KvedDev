package com.kvedthreadconstant;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


	
public class MySpider extends Arachnid {
	public MySpider(String base) throws MalformedURLException, IOException
	{ super(base); }
	
	protected void handleLink(PageInfo p) {
		String link = p.getUrl().toString();
		String title = p.getTitle();
		if (link == null || title == null || link.length() == 0 || title.length() ==0) return;
		else System.out.println("<li><a href=\""+link+"\">"+title+"</a></li>");
	}
	protected void handleBadLink(URL url, PageInfo p) { }
	protected void handleBadIO(URL url) { }
	protected void handleNonHTMLlink(URL url, PageInfo p) { }
	protected void handleExternalLink(URL url) { }
}