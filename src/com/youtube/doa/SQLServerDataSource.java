package com.youtube.doa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerDataSource{

	private static final String URL = "jdbc:sqlserver://localhost;database=login;integratedSecurity=true;";
	private static Connection conn;

	public SQLServerDataSource(){

		try{
//			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(driver);
			conn = DriverManager.getConnection(URL);
			
		}catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

	public Connection getConnection(){
		return conn;
	}
}
