package org.SUSCOM.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ConnectMSSQLServer {


	/**
	 * Connect this program with MS-SQL
	 * After finishing this program
	 * It will be connected
	 */
	
	private Connection connect = null;

	private Statement statement = null;

	private ConnectInformation dbConnect;


	public ConnectMSSQLServer() {

		dbConnect = new ConnectInformation();

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			try {
				connect = DriverManager.getConnection(dbConnect.getURL(), 
						dbConnect.getUser(), dbConnect.getPassword());
				statement = connect.createStatement();

			} catch (SQLException e) {

				JOptionPane.showMessageDialog(null, "Database Connection Failed");
			}

		} catch(ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Database Connection Failed");
		}
	}
}
