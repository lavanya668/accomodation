package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.AllocateRoomDTO;
import model.Room;
import utilities.DBConfiguration;

public class RoomTransDAO {

	DBConfiguration dBConfiguration = null;
	Connection sqlConnection = null;

	public RoomTransDAO() {
	}

	public RoomTransDAO(DBConfiguration dBConfiguration) {
		this.dBConfiguration = dBConfiguration;
	}

	public HashMap<String, Object> getRoomsList() {
		List<Room> roomsList = null;
		HashMap<String, Object> daoResponse = null;
		Connection sqlConnection = null;
		PreparedStatement sqlPreparedStatement = null;
		try {
			/* Try to connect to database */
			sqlConnection = dBConfiguration.connect();
			if (sqlConnection == null)
				throw new Exception("Exception connecting to database");
			daoResponse = new HashMap<String, Object>();
			roomsList = new ArrayList<Room>();

			/* SQL statement to get the data */
			String SQL_ROOMS_GET = "SELECT * FROM accommodation_rooms;";
			sqlPreparedStatement = sqlConnection.prepareStatement(SQL_ROOMS_GET);
			/* Execute Query */
			ResultSet resultSet = sqlPreparedStatement.executeQuery();
			if (resultSet.next() == false)
				throw new Exception("No Rooms faound");

			do {
				Room room = new Room(resultSet.getInt("room_id"), resultSet.getString("room_type"),
						resultSet.getString("room_location"), resultSet.getString("room_monthly_charge"),
						resultSet.getString("room_status"), resultSet.getString("payment_status"),
						resultSet.getString("student_reg_id"));
				roomsList.add(room);
			} while (resultSet.next());

			/* Set the dao response and return the object */
			daoResponse.put("result", true);
			daoResponse.put("data", roomsList);
			daoResponse.put("message", "Rooms extracted");
		} catch (Exception e) {
			/* In case of any exception set the error response and return the object */
			e.printStackTrace();
			daoResponse.put("result", false);
			daoResponse.put("data", null);
			daoResponse.put("message", e.getMessage());
		}
		return daoResponse;
	}

	public HashMap<String, Object> getRoomByRoomId(int roomId) {
		HashMap<String, Object> daoResponse = null;
		Connection sqlConnection = null;
		PreparedStatement sqlPreparedStatement = null;
		Room room = null;
		try {
			/* Try to connect to database */
			sqlConnection = dBConfiguration.connect();
			if (sqlConnection == null)
				throw new Exception("Exception connecting to database");
			daoResponse = new HashMap<String, Object>();

			/* SQL statement to get the data */
			String SQL_ROOMS_GET = "SELECT * FROM accommodation_rooms where room_id = ?;";
			sqlPreparedStatement = sqlConnection.prepareStatement(SQL_ROOMS_GET);
			sqlPreparedStatement.setInt(1, roomId);
			/* Execute Query */
			ResultSet resultSet = sqlPreparedStatement.executeQuery();
			if (resultSet.next() == false)
				throw new Exception("No Rooms faound");

			do {
				room = new Room(resultSet.getInt("room_id"), resultSet.getString("room_type"),
						resultSet.getString("room_location"), resultSet.getString("room_monthly_charge"),
						resultSet.getString("room_status"), resultSet.getString("payment_status"),
						resultSet.getString("student_reg_id"));
			} while (resultSet.next());

			/* Set the DAO response and return the object */
			daoResponse.put("result", true);
			daoResponse.put("data", room);
			daoResponse.put("message", "Room found with given room id");
		} catch (Exception e) {
			/* In case of any exception set the error response and return the object */
			e.printStackTrace();
			daoResponse.put("result", false);
			daoResponse.put("data", null);
			daoResponse.put("message", e.getMessage());
		}
		return daoResponse;
	}

	public HashMap<String, Object> addRoomByAdmin(Room room) {
		HashMap<String, Object> daoResponse = null;
		Connection sqlConnection = null;
		PreparedStatement sqlPreparedStatement = null;
		int roomId = 0;
		try {
			/* Try to connect to database */
			sqlConnection = dBConfiguration.connect();
			if (sqlConnection == null)
				throw new Exception("Exception connecting to database");
			daoResponse = new HashMap<String, Object>();

			/* SQL statement to get the data */
			String SQL_ROOMS_ADD = "INSERT INTO `accommodation_rooms` (`room_type`, `room_location`, `room_monthly_charge`, `room_status`, `payment_status`, `student_reg_id`) VALUES (?, ?, ?, ?, ?, ?);";

			sqlPreparedStatement = sqlConnection.prepareStatement(SQL_ROOMS_ADD);
			sqlPreparedStatement.setString(1, room.getRoomType());
			sqlPreparedStatement.setString(2, room.getRoomLocation());
			sqlPreparedStatement.setString(3, room.getRoomMonthlyCharge());
			sqlPreparedStatement.setString(4, room.getRoomStatus());
			sqlPreparedStatement.setString(5, room.getPaymentStatus());
			sqlPreparedStatement.setString(6, room.getStudentRegId());

			/* Execute Query */
			roomId = sqlPreparedStatement.executeUpdate();
			if (roomId == 0)
				throw new Exception("Room insert failed");

			/* Set the DAO response and return the object */
			daoResponse.put("result", true);
			daoResponse.put("data", room);
			daoResponse.put("message", "Room record inserted");
		} catch (Exception e) {
			/* In case of any exception set the error response and return the object */
			e.printStackTrace();
			daoResponse.put("result", false);
			daoResponse.put("data", null);
			daoResponse.put("message", e.getMessage());
		}
		return daoResponse;
	}

	public HashMap<String, Object> updateRoomByAdmin(Room room) {
		HashMap<String, Object> daoResponse = null;
		Connection sqlConnection = null;
		PreparedStatement sqlPreparedStatement = null;
		int roomId = 0;
		try {
			/* Try to connect to database */
			sqlConnection = dBConfiguration.connect();
			if (sqlConnection == null)
				throw new Exception("Exception connecting to database");
			daoResponse = new HashMap<String, Object>();

			/* SQL statement to get the data */
			String SQL_ROOM_UPDATE = "UPDATE `accommodation_rooms` SET `room_type` = ?, `room_location` = ?, `room_monthly_charge` = ? WHERE (`room_id` = ?);";

			sqlPreparedStatement = sqlConnection.prepareStatement(SQL_ROOM_UPDATE);
			sqlPreparedStatement.setString(1, room.getRoomType());
			sqlPreparedStatement.setString(2, room.getRoomLocation());
			sqlPreparedStatement.setString(3, room.getRoomMonthlyCharge());
			sqlPreparedStatement.setInt(4, room.getRoomId());

			System.out.println("SQL Statement: " + sqlPreparedStatement);
			System.out.println("Room ID: " + room.getRoomId());
			/* Execute Query */
			roomId = sqlPreparedStatement.executeUpdate();
			if (roomId == 0)
				throw new Exception("Room update failed");

			/* Set the DAO response and return the object */
			daoResponse.put("result", true);
			daoResponse.put("data", roomId);
			daoResponse.put("message", "Room record updated");
		} catch (Exception e) {
			/* In case of any exception set the error response and return the object */
			e.printStackTrace();
			daoResponse.put("result", false);
			daoResponse.put("data", null);
			daoResponse.put("message", e.getMessage());
		}
		return daoResponse;
	}

	public HashMap<String, Object> deleteRoomByAdmin(int roomId) {
		HashMap<String, Object> daoResponse = null;
		Connection sqlConnection = null;
		PreparedStatement sqlPreparedStatement = null;
		try {
			/* Try to connect to database */
			sqlConnection = dBConfiguration.connect();
			if (sqlConnection == null)
				throw new Exception("Exception connecting to database");
			daoResponse = new HashMap<String, Object>();

			/* SQL statement to get the data */
			String SQL_ROOM_DELETE = "DELETE FROM `accommodation_rooms` WHERE (`room_id` = ?);";

			sqlPreparedStatement = sqlConnection.prepareStatement(SQL_ROOM_DELETE);
			sqlPreparedStatement.setInt(1, roomId);

			/* Execute Query */
			roomId = sqlPreparedStatement.executeUpdate();
			if (roomId == 0)
				throw new Exception("Room delete failed");

			/* Set the DAO response and return the object */
			daoResponse.put("result", true);
			daoResponse.put("data", roomId);
			daoResponse.put("message", "Room record updated");
		} catch (Exception e) {
			/* In case of any exception set the error response and return the object */
			e.printStackTrace();
			daoResponse.put("result", false);
			daoResponse.put("data", null);
			daoResponse.put("message", e.getMessage());
		}
		return daoResponse;
	}

	public HashMap<String, Object> showAvailableRoomsForStudent() {
		List<Room> roomsList = null;
		HashMap<String, Object> daoResponse = null;
		Connection sqlConnection = null;
		PreparedStatement sqlPreparedStatement = null;
		try {
			/* Try to connect to database */
			sqlConnection = dBConfiguration.connect();
			if (sqlConnection == null)
				throw new Exception("Exception connecting to database");
			daoResponse = new HashMap<String, Object>();
			roomsList = new ArrayList<Room>();
			/* SQL statement to get the data */
			String SQL_ROOMS_GET = "SELECT * FROM accommodation_rooms WHERE room_status = ?;";
			sqlPreparedStatement = sqlConnection.prepareStatement(SQL_ROOMS_GET);
			sqlPreparedStatement.setString(1, "available");
			/* Execute Query */
			ResultSet resultSet = sqlPreparedStatement.executeQuery();
			if (resultSet.next() == false)
				throw new Exception("No Rooms faound");
			do {
				Room room = new Room(resultSet.getInt("room_id"), resultSet.getString("room_type"),
						resultSet.getString("room_location"), resultSet.getString("room_monthly_charge"),
						resultSet.getString("room_status"));
				roomsList.add(room);
			} while (resultSet.next());

			/* Set the dao response and return the object */
			daoResponse.put("result", true);
			daoResponse.put("data", roomsList);
			daoResponse.put("message", "Rooms extracted");
		} catch (Exception e) {
			/* In case of any exception set the error response and return the object */
			e.printStackTrace();
			daoResponse.put("result", false);
			daoResponse.put("data", null);
			daoResponse.put("message", e.getMessage());
		}
		return daoResponse;
	}

	public HashMap<String, Object> getRoomAllocatedByStudent(String studentRegId) {
		HashMap<String, Object> daoResponse = null;
		Connection sqlConnection = null;
		PreparedStatement sqlPreparedStatement = null;
		Room room = null;
		try {
			/* Try to connect to database */
			sqlConnection = dBConfiguration.connect();
			if (sqlConnection == null)
				throw new Exception("Exception connecting to database");
			daoResponse = new HashMap<String, Object>();
			/* SQL statement to get the data */
			String SQL_ROOMS_GET = "SELECT * FROM accommodation_rooms where student_reg_id = ?;";
			sqlPreparedStatement = sqlConnection.prepareStatement(SQL_ROOMS_GET);
			sqlPreparedStatement.setString(1, studentRegId);
			/* Execute Query */
			ResultSet resultSet = sqlPreparedStatement.executeQuery();
			if (resultSet.next() == false)
				throw new Exception("No Rooms faound");
			do {
				room = new Room(resultSet.getInt("room_id"), resultSet.getString("room_type"),
						resultSet.getString("room_location"), resultSet.getString("room_monthly_charge"),
						resultSet.getString("room_status"), resultSet.getString("payment_status"),
						resultSet.getString("student_reg_id"));
			} while (resultSet.next());
			/* Set the dao response and return the object */
			daoResponse.put("result", true);
			daoResponse.put("data", room);
			daoResponse.put("message", "Room extracted");
		} catch (Exception e) {
			/* In case of any exception set the error response and return the object */
			e.printStackTrace();
			daoResponse.put("result", false);
			daoResponse.put("data", null);
			daoResponse.put("message", e.getMessage());
		}
		return daoResponse;
	}
	
	public HashMap<String, Object> releaseRoomByStudent(int roomId) {
		HashMap<String, Object> daoResponse = null;
		Connection sqlConnection = null;
		PreparedStatement sqlPreparedStatement = null;
		int updatedRoomId = 0;
		try {
			/* Try to connect to database */
			sqlConnection = dBConfiguration.connect();
			if (sqlConnection == null)
				throw new Exception("Exception connecting to database");
			daoResponse = new HashMap<String, Object>();

			/* SQL statement to get the data */
			String SQL_ROOM_UPDATE = "UPDATE `accommodation_rooms` SET `student_reg_id` = ?, `room_status` = ?, `payment_status` = ? WHERE (`room_id` = ?);";

			sqlPreparedStatement = sqlConnection.prepareStatement(SQL_ROOM_UPDATE);
			sqlPreparedStatement.setString(1, "");
			sqlPreparedStatement.setString(2, "available");
			sqlPreparedStatement.setString(3, "");
			sqlPreparedStatement.setInt(4, roomId);
			/* Execute Query */
			updatedRoomId = sqlPreparedStatement.executeUpdate();
			if (updatedRoomId == 0)
				throw new Exception("Room update failed");

			/* Set the DAO response and return the object */
			daoResponse.put("result", true);
			daoResponse.put("data", updatedRoomId);
			daoResponse.put("message", "Room record updated");
		} catch (Exception e) {
			/* In case of any exception set the error response and return the object */
			e.printStackTrace();
			daoResponse.put("result", false);
			daoResponse.put("data", null);
			daoResponse.put("message", e.getMessage());
		}
		return daoResponse;
	}
	
	public HashMap<String, Object> allocateRoomForStudent(AllocateRoomDTO allocateRoomDTO) {
		HashMap<String, Object> daoResponse = null;
		Connection sqlConnection = null;
		PreparedStatement sqlPreparedStatement = null;
		int updatedRoomId = 0;
		try {
			/* Try to connect to database */
			sqlConnection = dBConfiguration.connect();
			if (sqlConnection == null)
				throw new Exception("Exception connecting to database");
			daoResponse = new HashMap<String, Object>();

			/* SQL statement to get the data */
			String SQL_ROOM_UPDATE = "UPDATE `accommodation_rooms` SET `student_reg_id` = ?, `room_status` = ?, `payment_status` = ? WHERE (`room_id` = ?);";

			sqlPreparedStatement = sqlConnection.prepareStatement(SQL_ROOM_UPDATE);
			sqlPreparedStatement.setString(1, allocateRoomDTO.getStudentRegNum());
			sqlPreparedStatement.setString(2, "allocated");
			sqlPreparedStatement.setString(3, "paid");
			sqlPreparedStatement.setInt(4, allocateRoomDTO.getRoomId());
			/* Execute Query */
			updatedRoomId = sqlPreparedStatement.executeUpdate();
			if (updatedRoomId == 0)
				throw new Exception("Room update failed");

			/* Set the DAO response and return the object */
			daoResponse.put("result", true);
			daoResponse.put("data", updatedRoomId);
			daoResponse.put("message", "Room record updated");
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
