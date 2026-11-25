package com.kvedthreadconstant;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnector extends HttpURLConnection {
	public HttpURLConnector(URL url) {
		super(url);
		
	}
	
	public void connect() {
		
	}
	public void disconnect() {
		
	}
	public boolean usingProxy() {
		return false;
	}
}