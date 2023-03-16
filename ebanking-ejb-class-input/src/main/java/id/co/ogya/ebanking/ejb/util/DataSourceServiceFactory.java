package id.co.ogya.ebanking.ejb.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DataSourceServiceFactory{
	
	private Connection conn;
	public Connection getConnection() throws SQLException, Exception {
		ServiceFactory serviceFactory = new ServiceFactory();
		DataSource dataSource = (DataSource) serviceFactory.getService("training-OracleDS");
		conn = dataSource.getConnection();
		return conn;
	
	}
	public void closeConnection() throws SQLException, Exception {
		if (conn != null){
			conn.close();
		}
	}

}
