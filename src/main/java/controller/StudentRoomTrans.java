package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.RoomTransDAO;
import model.AllocateRoomDTO;
import model.Room;
import utilities.AppUtilities;
import utilities.DBConfiguration;
import utilities.ReturnMessage;

@WebServlet(name = "StudentRoom", urlPatterns = "/student/room")
public class StudentRoomTrans extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> roomDaoResponse = null;
	private Gson gson = new Gson();
	private String stringResponse = null;
	private AppUtilities appUtils = new AppUtilities();
	private RoomTransDAO roomTransDAO = null;

	@Override
	protected void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws ServletException, IOException {
		PrintWriter pWriter = servletResponse.getWriter();
		servletResponse.setContentType("application/json");
		servletResponse.setCharacterEncoding("UTF-8");
		try {
			roomTransDAO = setDBPropsAndRetunRoomDAO(servletRequest);
			if (roomTransDAO == null)
				/* Get all available rooms */
				throw new Exception("Failed to read properties file");
			String queryStr = servletRequest.getQueryString();
			if (queryStr == null) {
				roomDaoResponse = roomTransDAO.showAvailableRoomsForStudent();
				if (!(boolean) roomDaoResponse.get("result"))
					throw new Exception((String) roomDaoResponse.get("message"));

				@SuppressWarnings("unchecked")
				List<Room> roomsList = (List<Room>) roomDaoResponse.get("data");
				stringResponse = gson.toJson(roomsList);
			} else {
				/* Show room booked by student */
				String studentRegId = servletRequest.getParameter("stuRegId");
				roomDaoResponse = roomTransDAO.getRoomAllocatedByStudent(studentRegId);
				if ((boolean) roomDaoResponse.get("result")) {
					Room room = (Room) roomDaoResponse.get("data");
					stringResponse = gson.toJson(room);
				}else if((String) roomDaoResponse.get("message") == "No Rooms faound") {
					ReturnMessage message = new ReturnMessage("NoRooms");
					stringResponse = gson.toJson(message);
				}else {
					throw new Exception((String) roomDaoResponse.get("message"));
				}					
			}
			servletResponse.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			e.printStackTrace();
			ReturnMessage message = new ReturnMessage(e.getMessage());
			servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			stringResponse = gson.toJson(message);
		}
		System.out.println("Response: " + stringResponse);
		pWriter.write(stringResponse);
		pWriter.close();
	}

	@Override
	protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws ServletException, IOException {
		PrintWriter pWriter = servletResponse.getWriter();
		servletResponse.setContentType("application/json");
		servletResponse.setCharacterEncoding("UTF-8");
		ReturnMessage message;
		try {
			AllocateRoomDTO allocateRoomDTO = getAllocateRoomDTOFromRequest(servletRequest, gson);
			if (allocateRoomDTO == null)
				throw new Exception("Please pass valid request body");
			roomTransDAO = setDBPropsAndRetunRoomDAO(servletRequest);
			if (roomTransDAO == null)
				throw new Exception("Failed to read properties file");
			roomDaoResponse = roomTransDAO.allocateRoomForStudent(allocateRoomDTO);
			if (!(boolean) roomDaoResponse.get("result"))
				throw new Exception((String) roomDaoResponse.get("message"));

			message = new ReturnMessage("Room updated succesfully");
			servletResponse.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			e.printStackTrace();
			message = new ReturnMessage(e.getMessage());
			servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		stringResponse = gson.toJson(message);
		pWriter.write(stringResponse);
		pWriter.close();
	}

	@Override
	protected void doPut(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws ServletException, IOException {
		PrintWriter pWriter = servletResponse.getWriter();
		servletResponse.setContentType("application/json");
		servletResponse.setCharacterEncoding("UTF-8");
		ReturnMessage message;
		try {
			Room room = getRoomFromRequest(servletRequest, gson);
			if (room == null)
				throw new Exception("Please pass valid request body");
			roomTransDAO = setDBPropsAndRetunRoomDAO(servletRequest);
			if (roomTransDAO == null)
				throw new Exception("Failed to read properties file");
			roomDaoResponse = roomTransDAO.releaseRoomByStudent(room.getRoomId());
			if (!(boolean) roomDaoResponse.get("result"))
				throw new Exception((String) roomDaoResponse.get("message"));

			message = new ReturnMessage("Room updated succesfully");
			servletResponse.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			e.printStackTrace();
			message = new ReturnMessage(e.getMessage());
			servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		stringResponse = gson.toJson(message);
		pWriter.write(stringResponse);
		pWriter.close();
	}

	private RoomTransDAO setDBPropsAndRetunRoomDAO(HttpServletRequest servletRequest) {
		RoomTransDAO roomTransDAO = null;
		try {
			DBConfiguration dBConfig = appUtils.setDbConfigFromProps(servletRequest);
			if (dBConfig == null)
				throw new Exception("Failed to read properties file");
			roomTransDAO = new RoomTransDAO(dBConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomTransDAO;
	}

	private Room getRoomFromRequest(HttpServletRequest servletRequest, Gson gson) {
		StringBuilder strBuilder = new StringBuilder();
		Room room = null;
		String responseLine = null;
		try {
			while ((responseLine = servletRequest.getReader().readLine()) != null) {
				strBuilder.append(responseLine);
			}
			room = (Room) gson.fromJson(strBuilder.toString(), Room.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return room;
	}
	
	private AllocateRoomDTO getAllocateRoomDTOFromRequest(HttpServletRequest servletRequest, Gson gson) {
		StringBuilder strBuilder = new StringBuilder();
		AllocateRoomDTO allocateRoomDTO = null;
		String responseLine = null;
		try {
			while ((responseLine = servletRequest.getReader().readLine()) != null) {
				strBuilder.append(responseLine);
			}
			allocateRoomDTO = (AllocateRoomDTO) gson.fromJson(strBuilder.toString(), AllocateRoomDTO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allocateRoomDTO;
	}
}