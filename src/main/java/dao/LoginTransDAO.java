package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import model.AppUser;
import utilities.DBConfiguration;

public class LoginTransDAO {

	DBConfiguration dBConfiguration = null;
	Connection sqlConnection = null;

	public LoginTransDAO() {
	}

	public LoginTransDAO(DBConfiguration dBConfiguration) {
		this.dBConfiguration = dBConfiguration;
	}

	/* Login operation */
	public HashMap<String, Object> loginWithAppUSerType(AppUser appUser) {
		HashMap<String, Object> daoResponse = null;
		Connection sqlConnection = null;
		PreparedStatement sqlPreparedStatement = null;
		AppUser appUserResponse = null;
		try {
			/* Try to connect to database */
			sqlConnection = dBConfiguration.connect();
			if (sqlConnection == null)
				throw new Exception("Exception connecting to database");
			daoResponse = new HashMap<String, Object>();

			/* SQL statement to get the data */
			String SQL_LOGIN = "SELECT * FROM app_users where email = ? AND password = ?;";
			sqlPreparedStatement = sqlConnection.prepareStatement(SQL_LOGIN);
			/* Set parameters */
			sqlPreparedStatement.setString(1, appUser.getEmail());
			sqlPreparedStatement.setString(2, appUser.getPassword());
			/* Execute Query */
			ResultSet resultSet = sqlPreparedStatement.executeQuery();

			if (resultSet.next() == false)
				throw new Exception("No records found with given credentials");

			/* Iterate the response and construct the response object */
			do {
				appUserResponse = new AppUser(resultSet.getInt("user_id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"),
						resultSet.getString("user_type"), resultSet.getString("password"),
						resultSet.getString("student_reg_num"));
			} while (resultSet.next());

			/* Set the dao response and return the object */
			daoResponse.put("result", true);
			daoResponse.put("data", appUserResponse);
			daoResponse.put("message", "Login successful");
		} catch (Exception e) {
			/* In case of any exception set the error response and return the object */
			e.printStackTrace();
			daoResponse.put("result", false);
			daoResponse.put("data", null);
			daoResponse.put("message", e.getMessage());
		}
		return daoResponse;
	}

	/* Registration Operation */
	public HashMap<String, Object> registerWithAppUserType(AppUser appUser) {
		HashMap<String, Object> daoResponse = null;
		int registeredAppUserId = 0;
		Connection sqlConnection = null;
		PreparedStatement sqlPreparedStatement = null;
		try {
			/* Try to connect to database */
			sqlConnection = dBConfiguration.connect();
			if (sqlConnection == null)
				throw new Exception("Exception connecting to database");
			daoResponse = new HashMap<String, Object>();

			/* SQL statement to get the data */
			String SQL_REGISTRATION = "INSERT INTO `app_users` (`first_name`, `last_name`, `email`, `user_type`, `password`, `student_reg_num`) VALUES (?, ?, ?, ?, ?,?);";
			sqlPreparedStatement = sqlConnection.prepareStatement(SQL_REGISTRATION);
			/* Set input values in the query */
			sqlPreparedStatement.setString(1, appUser.getFirstName());
			sqlPreparedStatement.setString(2, appUser.getLastName());
			sqlPreparedStatement.setString(3, appUser.getEmail());
			sqlPreparedStatement.setString(4, appUser.getAppUserType());
			sqlPreparedStatement.setString(5, appUser.getPassword());
			sqlPreparedStatement.setString(6, appUser.getStudentRegNum());

			registeredAppUserId = sqlPreparedStatement.executeUpdate();
			if (registeredAppUserId == 0)
				throw new Exception("User Registration Failed");

			/* Set the dao response and return the object */
			daoResponse.put("result", true);
			daoResponse.put("message", "User registered succesfully");
			daoResponse.put("data", registeredAppUserId);

		} catch (Exception e) {
			/* In case of any exception set the error response and return the object */
			e.printStackTrace();
			daoResponse.put("result", false);
			daoResponse.put("data", null);
			daoResponse.put("message", e.getMessage());
		}
		return daoResponse;
	}
}