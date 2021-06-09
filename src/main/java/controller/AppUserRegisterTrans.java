package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.LoginTransDAO;
import model.AppUser;
import utilities.AppUtilities;
import utilities.DBConfiguration;
import utilities.ReturnMessage;

@WebServlet(name = "UserRegister", urlPatterns = "/appUserRegister")
public class AppUserRegisterTrans extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> appUserDaoResponse = null;
	private Gson gson = new Gson();
	private AppUtilities appUtils = new AppUtilities();
	private LoginTransDAO loginTransDAO = null;
	private String stringResponse = null;

	@Override
	protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws ServletException, IOException {
		PrintWriter pWriter = servletResponse.getWriter();
		servletResponse.setContentType("application/json");
		servletResponse.setCharacterEncoding("UTF-8");
		ReturnMessage message;
		try {
			AppUser appUser = getAppUserFromRequest(servletRequest, gson);
			if (appUser == null)
				throw new Exception("Please pass valid request body");
			loginTransDAO = setDBPropsAndReturnLoginDAO(servletRequest);
			if (loginTransDAO == null)
				throw new Exception("Failed to read properties file");

			appUserDaoResponse = loginTransDAO.registerWithAppUserType(appUser);
			if (!(boolean) appUserDaoResponse.get("result"))
				throw new Exception((String) appUserDaoResponse.get("message"));
			message = new ReturnMessage((String) appUserDaoResponse.get("message"));
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

	private LoginTransDAO setDBPropsAndReturnLoginDAO(HttpServletRequest servletRequest) {
		LoginTransDAO loginTransDAO = null;
		try {
			DBConfiguration dBConfig = appUtils.setDbConfigFromProps(servletRequest);
			if (dBConfig == null)
				throw new Exception("Failed to read properties file");
			loginTransDAO = new LoginTransDAO(dBConfig);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginTransDAO;
	}

	private AppUser getAppUserFromRequest(HttpServletRequest servletRequest, Gson gson) {
		StringBuilder strBuilder = new StringBuilder();
		AppUser appUser = null;
		String responseLine = null;
		try {
			while ((responseLine = servletRequest.getReader().readLine()) != null) {
				strBuilder.append(responseLine);
			}
			appUser = (AppUser) gson.fromJson(strBuilder.toString(), AppUser.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return appUser;
	}

}
