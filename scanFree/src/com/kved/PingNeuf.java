package com.kved;

public class PingNeuf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadGroup urlGroup = new ThreadGroup("urlGroup");
		Thread tmain = Thread.currentThread();

		try {
			//new URLThread(tmain, urlGroup,"toto.neuf.fr");
			//new URLThread(tmain, urlGroup,"fmispouille.neuf.fr/");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
