package com.kvedthreadconstant;

/** Arachnid - Abstract Web spider class
 * To use, derive class from Arachnid,
 * Add handleLink(), handleBadLink(), handleNonHTMLlink(),
 * handleExternalLink(), and handleBadIO() methods
 * Instantiate and call traverse()
 * 
 * Copyright 2002, Robert L. Platt, All rights reserved
 * @author Robert L. Platt 
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;


public abstract class Arachnid {
	private String base;
	private URL baseUrl;
	private HashSet visited;
	private int delay;
	private Map headerHttp;
	private static final String HTML = "text/html";
	public boolean isHTMLfile = true;
	public PageInfo p = null;
	public int responseCode=0;
	public String contentType="";
	HttpURLConnection connection=null;

	/** Constructor */
	public Arachnid(String base) throws MalformedURLException,IOException {
		this.base = base;
		baseUrl = new URL(base);
		visited = new HashSet();
		delay = 2;
		traverse();
	}

	/** Traverse Web site */
	//public void traverse() throws IOException{ traverse(baseUrl); }

	private void traverse()throws IOException 
	{

		try { p = getWebPage(baseUrl); }
		catch(SocketTimeoutException e) {
			if (connection !=null)connection.disconnect();
			sleep(delay);
			throw e;
		}
		catch(IOException e) {
			//handleBadIO(url,parent);
			if (connection !=null)connection.disconnect();
			sleep(delay);
			throw e;
			//return;
		}

		if (p == null) {
			handleBadLink(baseUrl,null);
			sleep(delay);
			return;
		}
		if (p.isValid() == false) {
			if (p.getContentType().equalsIgnoreCase(HTML) == false) 
				handleNonHTMLlink(baseUrl,p);
			else handleBadLink(baseUrl,p);
			sleep(delay);
			return;
		}
		else handleLink(p);

		return;
	}

	/** (Abstract) Handle bad URL */
	protected abstract void handleBadLink(URL url,PageInfo p);

	/** (Abstract) Handle a link; a Web page in the site */
	protected abstract void handleLink(PageInfo p);

	/** (Abstract) Handle a non-HTML link */
	protected abstract void handleNonHTMLlink(URL url, PageInfo p);

	/** (Abstract) Handle an external (outside of Web site) link */
	protected abstract void handleExternalLink(URL url);

	/** (Abstract) Handle an I/O Exception (server problem) */
	protected abstract void handleBadIO(URL url);

	/** Return true if it's OK to visit the link,
	    false if it's not */
	private boolean isOKtoVisit(URL link) {
		// Return false if it's not HTTP protocol
		if (!link.getProtocol().equals("http")) return(false);
		// Return false if it's an external site
		else if (isExternalSite(link)) return(false);
		else if (visited.contains(link)) return(false);
		else return(true);
	}

	private boolean isExternalSite(URL link) {
		// Return true if link host is different from base or
		// if path of link is not a superset of base URL
		if (link.getAuthority() != baseUrl.getAuthority() ||
				(!UrlPathDir(link).startsWith(UrlPathDir(baseUrl)))) return(true);
		else return(false);
	}

	private String UrlPathDir(URL u) {
		String p = u.getPath();
		if (p == null || p.equals("")) return("/");
		int i = p.lastIndexOf("/");
		if (i == -1) return("/");
		else p = p.substring(0,i+1);
		return(p);
	}

	// Populate a PageInfo object from a URL
	private PageInfo getWebPage(URL url) throws IOException,SocketTimeoutException {

		connection = (HttpURLConnection)url.openConnection();

//		connection.setConnectTimeout(1000);
//		connection.setReadTimeout(1000);

		//System.out.println("timeout conn: "+connection.getConnectTimeout());
		headerHttp = connection.getHeaderFields();
		responseCode = connection.getResponseCode();
		contentType = connection.getContentType();
		// Note: contentLength == -1 if NOT KNOWN (i.e. not returned from server)
		int contentLength = connection.getContentLength();
		if (headerHttp.size()!=0){
			p = new PageInfo(url,contentType,contentLength,responseCode);
			System.out.println(url.getHost());
			InputStreamReader rdr =
				new InputStreamReader(connection.getInputStream());
			p.extract(rdr);
			rdr.close();
		}

		connection.disconnect();
		return(p);
	}



	public byte[] getContent(URL url) {
		byte[] buf = null;
		HttpURLConnection connection=null;
		try { 
			connection = (HttpURLConnection)url.openConnection();
			int responseCode = connection.getResponseCode();
			int contentLength = connection.getContentLength();
			// System.out.println("Content length: "+contentLength);
			if (responseCode != HttpURLConnection.HTTP_OK || contentLength <= 0) return(null);
			InputStream in = connection.getInputStream();
			BufferedInputStream bufIn = new BufferedInputStream(in);
			buf = new byte[contentLength];
			// Added code to handle blocked reads
			int bytesToRead = contentLength;
			int flag = 10;
			while(bytesToRead != 0 && flag != 0) {
				int bytesRead = bufIn.read(buf,(contentLength-bytesToRead),bytesToRead);
				bytesToRead = bytesToRead - bytesRead;
				flag--;
				if (flag <= 5) sleep(1);
			}
			in.close();
			connection.disconnect();
			if (flag == 0) return(null);
		}
		catch(Exception e) {
			if (connection!=null) connection.disconnect();
			// System.out.println(e);
			// e.printStackTrace();
			return(null);
		}

		return(buf);
	}

	/** Return base URL (starting point for Web traversal) */
	public URL getBaseUrl() { return(baseUrl); }	

	// Sleep N seconds
	private void sleep(int n) {
		if (n <= 0) return;
		Thread mythread = Thread.currentThread();
		try { mythread.sleep(n*1000); }
		catch(InterruptedException e) { // Ignore
		}
	}

	public Map getHeaderHttp() {
		return headerHttp;
	}

	/**
	 * Returns delay (N second pause after processing EACH web page)
	 * @return int
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * Sets delay (N second pause after processing EACH web page)
	 * @param delay The delay to set
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

}