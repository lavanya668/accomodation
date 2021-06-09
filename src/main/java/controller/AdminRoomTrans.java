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
import model.Room;
import utilities.AppUtilities;
import utilities.DBConfiguration;
import utilities.ReturnMessage;

@WebServlet(name = "AdminRoom", urlPatterns = "/admin/room")
public class AdminRoomTrans extends HttpServlet {
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
				throw new Exception("Failed to read properties file");
			/* Get Query string */
			String queryStr = servletRequest.getQueryString();

			if (queryStr == null) {
				/* Get all books */
				roomDaoResponse = roomTransDAO.getRoomsList();
				if (!(boolean) roomDaoResponse.get("result"))
					throw new Exception((String) roomDaoResponse.get("message"));
				@SuppressWarnings("unchecked")
				List<Room> roomsList = (List<Room>) roomDaoResponse.get("data");
				stringResponse = gson.toJson(roomsList);
				/* Get room single record */
			} else {
				int roomId = Integer.parseInt(servletRequest.getParameter("roomId"));
				roomDaoResponse = roomTransDAO.getRoomByRoomId(roomId);
				if (!(boolean) roomDaoResponse.get("result"))
					throw new Exception((String) roomDaoResponse.get("message"));
				Room room = (Room) roomDaoResponse.get("data");
				stringResponse = gson.toJson(room);
			}
			servletResponse.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			e.printStackTrace();
			ReturnMessage message = new ReturnMessage(e.getMessage());
			servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			stringResponse = gson.toJson(message);
		}
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
			Room room = getRoomFromRequest(servletRequest, gson);
			if (room == null)
				throw new Exception("Please pass valid request body");
			roomTransDAO = setDBPropsAndRetunRoomDAO(servletRequest);
			if (roomTransDAO == null)
				throw new Exception("Failed to read properties file");
			roomDaoResponse = roomTransDAO.addRoomByAdmin(room);
			if (!(boolean) roomDaoResponse.get("result"))
				throw new Exception((String) roomDaoResponse.get("message"));

			message = new ReturnMessage("Room added succesfully");
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
			roomDaoResponse = roomTransDAO.updateRoomByAdmin(room);
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
	protected void doDelete(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
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
			roomDaoResponse = roomTransDAO.deleteRoomByAdmin(room.getRoomId());
			if (!(boolean) roomDaoResponse.get("result"))
				throw new Exception((String) roomDaoResponse.get("message"));

			message = new ReturnMessage("Room deleted succesfully");
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

}
