package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dbHandler.SQLiteConnection;
import model.Appointment;
import repository.IAppointmentRepo;
import repositoryImpl.AppointmentRepoImpl;

/**
 * Servlet implementation class appointServlet
 */
@WebServlet("/appointServlet")
public class appointServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    IAppointmentRepo appointRepo = new AppointmentRepoImpl();
	/**
	 * Default constructor.
	 */
	public appointServlet() {
		// TODO Auto-generated constructor stub
		SQLiteConnection.createDB();
		SQLiteConnection.createAppointmentTable();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
        String searchTxt = request.getParameter("searchText");
		mapper.writeValue(response.getOutputStream(), appointRepo.getAppointments(searchTxt));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Appointment appoint = new Appointment();
		
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		String description = request.getParameter("description");

		
		appoint.setAppointmentDate(date);
		appoint.setAppointmentTime(time);
		appoint.setDescription(description);
		
		appointRepo.insertAppointment(appoint);
		
		RequestDispatcher view = request.getRequestDispatcher("/index.html");
	    view.forward(request, response);

	}

}
