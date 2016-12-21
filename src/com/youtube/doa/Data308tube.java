package com.youtube.doa;

import javax.naming.*;
import javax.sql.*;

public class Data308tube{

	private static DataSource Data308tube = null;
	private static Context context = null;

	public static DataSource Data308tubeConn() throws Exception{
		// 2 private variables to prevent tampering
		// methods are going to call this method to get db connection for data

		if(Data308tube != null){
			return Data308tube;
		}

		try{
			if(context == null){
				context = new InitialContext();
			}

			// looking up from weblogic setup
			Data308tube = (DataSource) context.lookup("308tubeData");

		}catch(Exception e){
			e.printStackTrace();
		}
		return Data308tube;
	}

	public static void main(String[] args){
		// TODO Auto-generated method stub

	}

}
