package com.ss.week3day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RestService
 */
@WebServlet({ "/login/username/*" })
public class AuthenticationService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthenticationService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> mockDB = new HashMap<>(); // mock user/password database
		BufferedReader reader = null;

		// populate mock database
		mockDB.put("John", "123");
		mockDB.put("Dennis", "dogman");
		mockDB.put("Jane", "catlady");
		mockDB.put("Barry", "2020");

		// expect to receive text from request
		response.setContentType("text/html");

		// create string from URI
		String path = request.getRequestURI().substring(request.getContextPath().length());

		// continue if URI contains /login/username prefix
		if (path.contains("/login/username")) {

			String pathInfo = request.getPathInfo();

			// parse extra path info as a string to read username
			if (pathInfo == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			} else {
				try {
					String username = pathInfo.replaceAll("/", "");

					// read request body for password
					StringBuffer buff = new StringBuffer();
					String content;
					try {
						reader = request.getReader();
						while ((content = reader.readLine()) != null)
							buff.append(content);
					} catch (Exception e) {
						System.out.println("Failure to read request body");
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}

					// check request body content password against mock db username/password
					// contents
					if (mockDB.get(username).equalsIgnoreCase(buff.toString())) {
						// login success
						response.sendError(HttpServletResponse.SC_ACCEPTED);
					} else {
						// login failure
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					}

					// close BufferedReader
					reader.close();
				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

	}

}
