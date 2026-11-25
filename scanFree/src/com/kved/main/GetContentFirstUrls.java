package com.kved.main;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import com.kved.BusinessObjectMysql;

public class GetContentFirstUrls {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			BusinessObjectMysql bo = new BusinessObjectMysql("192.168.0.4","3306","url", "webuser", "webuser");
			List list = bo.getURLsSansContent();
			System.out.println("Nombre d'�l�ment: "+list.size());
			SimplifyContent sc=null;
			for (int i=0;i<list.size();i++){

				try {
					//System.out.println(list.get(i));
					sc= new SimplifyContent(new URL("http://"+(String)list.get(i)));
					bo.updateUrl((String)list.get(i), sc.convert());
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
