/**
 * 
 */
package com.github.CynthiaYang88.blackJack;

import java.sql.* ;
//import com.microsoft.sqlserver.jdbc.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;






/**
 * @author Cindi
 *
 */
public class IO {
	private String name = "";
	private double bank = 0;
	private String bankString = "";
	private int afterComma = 0;
	private int counter = 0;

	public void readFromCSV(String fileName) {
		FileReader inputStream = null;

		try {
			inputStream = new FileReader(fileName); // input file

			int c; // ?
			while ((c = inputStream.read()) != -1) { // read and process one character

				if ((char) c == ',') {
					afterComma = counter;
					continue;
				}

				if (afterComma > 0) {
					bankString += (char) c;
				} else {
					name += (char) c;// c
				}

				counter++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public double getBank() {
		bank = Double.parseDouble(bankString);
		return bank;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method to write to server
	 * @param b bankroll balance
	 */
	/*=====================================================================
	File: 	 ConnectDataSource.java
	Summary: This Microsoft JDBC Driver for SQL Server sample application
	         demonstrates how to connect to a SQL Server database by 
		     using a data source object.
	---------------------------------------------------------------------
	This file is part of the Microsoft JDBC Driver for SQL Server Code Samples.
	Copyright (C) Microsoft Corporation.  All rights reserved.
	 
	This source code is intended only as a supplement to Microsoft
	Development Tools and/or on-line documentation.  See these other
	materials for detailed information regarding Microsoft code samples.
	 
	THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF
	ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO
	THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
	PARTICULAR PURPOSE.
	=====================================================================*/
	public void writeToServer(double b) {
		// Want to output to server:
		// String this.name
		// double b
		//System.out.println("bb" + b);
		
		
		/*// Create datasource.
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("DESKTOP-IE524RB/music");
        //ds.setPassword("<password>");
        ds.setServerName("DESKTOP-IE524RB");
        ds.setPortNumber(Integer.parseInt("5432"));
        ds.setDatabaseName("userNameDatabase"); */
		// Create a variable for the connection string.
        
	    // Create a variable for the connection string.
        //String connectionUrl = "jdbc:postgresql://localhost:5432/userNameDatabase;user=opsdb;password=opsdb";
		// "jdbc:postgresql://localhost:5432/userNameDatabase", "opsdb","opsdb" 
		
        String SQL = "insert into user_name values(?, ?)";//"insert into user_name(UserName, bankRoll) values(this.name, b)";
        
        try ( Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/blackJack","postgres","lalala086"); PreparedStatement stmt = con.prepareStatement(SQL);) {
            //String SQL = "insert into user_name values(?, ?)";//"insert into user_name(UserName, bankRoll) values(this.name, b)";
            stmt.setString(1, this.name);
            stmt.setDouble(2, b);
            stmt.addBatch();
            stmt.executeBatch(); // SQL
            
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("databse yayy");
	}

}
