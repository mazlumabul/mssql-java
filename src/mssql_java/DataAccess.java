package mssql_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataAccess {

	public static Connection dbBaglanti() throws ClassNotFoundException,SQLException 
	{

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		/* 
		 Connection dbBaglanti=DriverManager.getConnection("jdbc:sqlserver://DESKTOP-EDCM90U;"+
		"databaseName=master","sa","147858");
		*/
		Connection dbBaglanti=DriverManager.getConnection("jdbc:sqlserver://DESKTOP-EDCM90U;"+
		"databaseName=OGROTO_BM408_VIZE","sa","147858");

		return dbBaglanti;
	}
}
