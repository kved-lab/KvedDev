package com.kved.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.xbill.DNS.Address;

public class Tset {

	/**
	 * @param args
	 */
	
		public static void main ( String[] args )
	      {
	      try
	         {
	         InetAddress.getByName( "kssved.free.fr"  );
System.out.println("ok");
	       
	         }
	      catch ( UnknownHostException e )
	         {
	         System.out.println( "unknown host" );
	         }

	      }
		

	}


