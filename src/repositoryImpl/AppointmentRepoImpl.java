package repositoryImpl;

import java.util.List;

import dbHandler.SQLiteConnection;
import model.Appointment;
import repository.IAppointmentRepo;

public class AppointmentRepoImpl implements IAppointmentRepo {

	
	
	@Override
	public void insertAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		SQLiteConnection.insertAppointmentTable(appointment);
		
	}

	@Override
	public List<Appointment> getAppointments(String searchText) {
		// TODO Auto-generated method stub
		return SQLiteConnection.getAppointments(searchText);
	}

}
