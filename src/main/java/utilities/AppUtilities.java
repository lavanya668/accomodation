package utilities;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class AppUtilities {

	public AppUtilities() {
	}

	public DBConfiguration setDbConfigFromProps(HttpServletRequest servletRequest) {
		DBConfiguration dbConfiguration = null;
		try {
			Properties properties = new Properties();
			properties.load(servletRequest.getServletContext().getResourceAsStream("db.properties"));
			dbConfiguration = new DBConfiguration(properties.getProperty("db.url"),
					properties.getProperty("db.username"), properties.getProperty("db.password"),
					properties.getProperty("db.driver"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConfiguration;
	}
}
