package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfiguration {

	private String jdbcDBUrl;
	private String dBUsername;
	private String dBPassword;
	private String dBDriver;

	public DBConfiguration() {
	}

	public DBConfiguration(String jdbcDBUrl, String dBUsername, String dBPassword, String dBDriver) {
		this.jdbcDBUrl = jdbcDBUrl;
		this.dBUsername = dBUsername;
		this.dBPassword = dBPassword;
		this.dBDriver = dBDriver;
	}

	public Connection connect() throws ClassNotFoundException {
		Connection connection = null;
		try {
			Class.forName(dBDriver);
			connection = DriverManager.getConnection(jdbcDBUrl, dBUsername, dBPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void disconnect(Connection connection) {
		try {
			if (connection != null && !connection.isClosed())
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getJdbcDBUrl() {
		return jdbcDBUrl;
	}

	public void setJdbcDBUrl(String jdbcDBUrl) {
		this.jdbcDBUrl = jdbcDBUrl;
	}

	public String getdBUsername() {
		return dBUsername;
	}

	public void setdBUsername(String dBUsername) {
		this.dBUsername = dBUsername;
	}

	public String getdBPassword() {
		return dBPassword;
	}

	public void setdBPassword(String dBPassword) {
		this.dBPassword = dBPassword;
	}

	public String getdBDriver() {
		return dBDriver;
	}

	public void setdBDriver(String dBDriver) {
		this.dBDriver = dBDriver;
	}

}
