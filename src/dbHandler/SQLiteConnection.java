package dbHandler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Appointment;

public class SQLiteConnection {

	static Connection c = null;
	static Statement stmt = null;

	public static void createDB() {

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:appoint.db");

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		System.out.println("Opened database successfully");
	}

	public static void createAppointmentTable() {

		try {

			stmt = c.createStatement();
			//String sqlDel = "drop table	appointment";
			String sql = "CREATE TABLE APPOINTMENT " + "(ID INTEGER PRIMARY KEY NOT NULL," +
			               " DATE NOT NULL, TIME NOT NULL, DESCRIPTION NOT NULL)";
			//dropping the table
			//stmt.executeUpdate(sqlDel);
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			//System.exit(0);
		}

		System.out.println("Table created successfully");
	}

	public static void insertAppointmentTable(Appointment appoint) {

		try {
			
			c.setAutoCommit(false);
			System.out.println("Setting auto commit to false");

			stmt = c.createStatement();
			String sql = "INSERT INTO APPOINTMENT (DATE, TIME, DESCRIPTION) " + "VALUES ('" + appoint.getAppointmentDate()
						+ "' , '" + appoint.getAppointmentTime() + "' , '" + appoint.getDescription() + "' )";
		
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
	}

	public static List<Appointment> getAppointments(String searchText) {

		List<Appointment> appointments = new ArrayList<Appointment>();
		try {
			
			String sql;
			if (searchText == null) {
				sql = "select * from appointment";
			} else
				sql = "select * from appointment where description like '%" + searchText + "%'";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				Appointment appoint = new Appointment();
				int id = rs.getInt("id");
				String date = rs.getString("date");
				String time = rs.getString("time");
				String description = rs.getString("description");

				appoint.setAppointmentDate(date);
				appoint.setAppointmentTime(time);
				appoint.setDescription(description);
				appoint.setAppointmentId(id);

				appointments.add(appoint);
			}
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		System.out.println("Operation done successfully");
		return appointments;
	}

}
